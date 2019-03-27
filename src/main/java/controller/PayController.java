package controller;

import com.alibaba.fastjson.JSON;
import dto.Result;
import entity.Merchandise;
import entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.PayUtils;
import utils.PropertyUtil;
import utils.XMLUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class PayController {

    @PostMapping("/createtransaction")
    @ResponseBody
    public Result<Orders> getWXPay(@RequestBody Orders orders)
    {
        Result<Orders> baseResult = new Result<>();

        Double price = Double.parseDouble(orders.getOrder_end_price());
        if (price <= 0) // 防止抓包修改订单金额造成损失
        {
            baseResult.setMsg("付款金额错误!");
            baseResult.setSuccess(false);
            return baseResult;
        }
        try {
            SortedMap<Object, Object> parameters = PayUtils.getWXPrePayID(); // 获取预付单，此处已做封装，需要工具类

            Merchandise merchandise = new Merchandise(); // 商品对象
            //merchandise.
//            merchandise.setId(orders.getProductId());
//            travelFly = travelFlyMapper.selectById(travelFly);
//            travelFly.setBusinesser(businesserMapper.selectByPrimaryKey(travelFly.getBusinesserId()));
            orders.setMerchandise(merchandise);
            parameters.put("body", "艺派课程-" + merchandise.getMe_Name());

            parameters.put("spbill_create_ip", "39.98.46.197");
            parameters.put("out_trade_no", orders.getOrder_id() + PayUtils.getDateStr()); // 订单id这里我的订单id生成规则是订单id+时间
            parameters.put("total_fee", "1"); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
            // parameters.put("total_fee", orders.getOrderAmount()*100+""); // 上线后，将此代码放开

            // 设置签名
            String sign = PayUtils.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            // 封装请求参数结束
            String requestXML = PayUtils.getRequestXml(parameters); // 获取xml结果
//            logger.debug("封装请求参数是：" + requestXML);
            // 调用统一下单接口
            String result = PayUtils.httpsRequest(PropertyUtil.getInstance().getProperty("WxPay.payURL"), "POST",
                    requestXML);
//            logger.debug("调用统一下单接口：" + result);
            SortedMap<Object, Object> parMap = PayUtils.startWXPay(result);
//            logger.debug("最终的map是：" + parMap.toString());
            if (parMap != null)
            {
                orders.setPayInfo(JSON.toJSONString(parMap));
                baseResult.setData(orders);
            } else
            {
                baseResult.setMsg("支付出现异常，请稍后重试!");
                baseResult.setSuccess(false);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            baseResult.setMsg("程序异常!");
            baseResult.setSuccess(false);
        }
        return baseResult;
    }



    @RequestMapping("/wxnotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException
    {
        String result = PayUtils.reciverWx(request); // 接收到异步的参数
        Map<String, String> m = new HashMap<String, String>();// 解析xml成map
        if (m != null && !"".equals(m))
        {
            m = XMLUtil.parseXml(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext())
        {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue)
            {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 判断签名是否正确
        String resXml = "";
        if (PayUtils.isTenpaySign("UTF-8", packageParams))
        {
            if ("SUCCESS".equals((String) packageParams.get("return_code")))
            {
                // 如果返回成功
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                // String transaction_id = (String)
                // packageParams.get("transaction_id"); // 微信支付订单号
                // 查询订单 根据订单号查询订单
                String orderId = out_trade_no.substring(0, out_trade_no.length() - PayUtils.TIME.length());
// TODO               Orders orders = ordersMapper.selectByPrimaryKey(Integer.parseInt(orderId));
                Orders orders = new Orders();

                // 验证商户ID 和 价格 以防止篡改金额
                if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id) && orders != null
                    // &&
                    // total_fee.trim().toString().equals(orders.getOrderAmount())
                    // // 实际项目中将此注释删掉，以保证支付金额相等
                )
                {
                    /** 这里是我项目里的消费状态
                     * 1.待付款=0 2.付款完成=1
                     * 3.消费成功=2
                     * 4.取消=-1
                     * 5.发起退款=-2
                     * 6.退款成功=-3
                     * 7.退款失败=3（由于商户拒绝退款或其他原因导致退款失败）
                     */
                    // TODO insertWxNotice(packageParams);
                    //orders.setPayWay("1"); // 变更支付方式为wx
                    //orders.setOrderState("1"); // 订单状态为已付款

                    //ordersMapper.updateByPrimaryKeySelective(orders); // 变更数据库中该订单状态
                    // ordersMapper.updatePayStatus(Integer.parseInt(orderId));
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else
                {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                }
            } else // 如果微信返回支付失败，将错误信息返回给微信
            {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
            }
        } else
        {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }

        // 处理业务完毕，将业务结果通知给微信
        // ------------------------------
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
