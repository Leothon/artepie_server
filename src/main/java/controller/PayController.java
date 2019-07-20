package controller;

import com.alibaba.fastjson.JSON;
import dao.GetDataDao;
import dao.SendDataDao;
import dto.Result;
import entity.Merchandise;
import entity.Orders;
import org.apache.ibatis.annotations.Param;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SendDataService;
import utils.PayUtils;
import utils.PropertyUtil;
import utils.XMLUtil;
import utils.commonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class PayController {


    @Autowired
    SendDataDao sendDataDao;
    @Autowired
    GetDataDao getDataDao;

    @Autowired
    SendDataService sendDataService;
    @PostMapping("/createtransaction")
    @ResponseBody
    public Result<Orders> getWXPay(@RequestBody Orders orders) {

        Result<Orders> baseResult = new Result<>();

        Double price = Double.parseDouble(orders.getOrder_end_price());
        if (price <= 0) // 防止抓包修改订单金额造成损失
        {
            baseResult.setMsg("付款金额错误!");
            baseResult.setSuccess(false);
            return baseResult;
        }
        try {

            //PayUtils.getWXPrePayID();
            SortedMap<Object, Object> parameters = new TreeMap<Object, Object>(); // 获取预付单，此处已做封装，需要工具类
            parameters.put("appid", PropertyUtil.getInstance().getProperty("WxPay.appid"));
            parameters.put("mch_id", PropertyUtil.getInstance().getProperty("WxPay.mchid"));
            parameters.put("nonce_str", PayUtils.CreateNoncestr());

            parameters.put("body", "artepieclass-" + orders.getOrder_number());
            parameters.put("out_trade_no", orders.getOrder_number()); // 订单id这里我的订单id生成规则是订单id+时间
            parameters.put("fee_type", "CNY");
            //parameters.put("total_fee", "1"); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
            String tempFee = String.valueOf(Double.parseDouble(orders.getOrder_end_price())*100);
            parameters.put("total_fee", tempFee.substring(0,tempFee.indexOf(".")) ); // 上线后，将此代码放开
            parameters.put("spbill_create_ip", orders.getLocal_ip());
            parameters.put("notify_url", PropertyUtil.getInstance().getProperty("WxPay.notifyurl"));
            parameters.put("trade_type", "APP");
            String sign = PayUtils.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            // 封装请求参数结束
            String requestXML = PayUtils.getRequestXml(parameters); // 获取xml结果
            System.out.println("封装请求参数是：" + requestXML);
            // 调用统一下单接口
            String result = PayUtils.httpsRequest(PropertyUtil.getInstance().getProperty("WxPay.payURL"), "POST", requestXML);
            System.out.println("调用统一下单接口：" + result);
            SortedMap<Object, Object> parMap = PayUtils.startWXPay(result);
            System.out.println("最终的map是：" + parMap.toString());
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
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = PayUtils.reciverWx(request); // 接收到异步的参数

        System.out.println("微信返回的结果" + result);
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

        if (PayUtils.isTenpaySign("UTF-8", packageParams)) {
            System.out.println("看到就是签名正确");
            if ("SUCCESS".equals((String) packageParams.get("return_code"))) {

                System.out.println("看到就是返回成功");
                // 如果返回成功
                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
                String total_fee = (String) packageParams.get("total_fee");
                String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号
                // 查询订单 根据订单号查询订单
                String pay_type = "wechat";
                String pay_status = "支付成功";
                String bank_type = (String) packageParams.get("bank_type");
                String transaction_end_time = (String) packageParams.get("time_end");
                Orders orders = getDataDao.getOrders(out_trade_no);


                // 验证商户ID 和 价格 以防止篡改金额

                String tempPrice = String.valueOf((Double.parseDouble(orders.getOrder_end_price())*100));
                String price = tempPrice.substring(0,tempPrice.indexOf("."));
                System.out.println("本地订单价格" + price);
                System.out.println("返回的价格" + total_fee);
                if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id) && orders != null
                     && total_fee.trim().toString().equals(price)){
                    /** 这里是我项目里的消费状态
                     * 1.待付款=0 2.付款完成=1
                     * 3.消费成功=2
                     * 4.取消=-1
                     * 5.发起退款=-2
                     * 6.退款成功=-3
                     * 7.退款失败=3（由于商户拒绝退款或其他原因导致退款失败）
                     */

                    sendDataDao.updateTransaction(orders.getOrder_id(),pay_type,pay_status,bank_type,transaction_id,transaction_end_time);
                    String classBuyId = "buy" + commonUtils.createUUID();
                    sendDataDao.insertClassBuyInfo(classBuyId,orders.getOrder_class_id(),orders.getOrder_user_id(),transaction_end_time);
                    sendDataDao.updateUserBalance(getDataDao.getUserIdByClassId(orders.getOrder_class_id()),price);
                    if (getDataDao.isFav(orders.getOrder_user_id(),orders.getOrder_class_id()) != 0){
                        String favId = "fav" + commonUtils.createUUID();
                        sendDataService.addFav(orders.getOrder_user_id(),orders.getOrder_class_id(),favId);
                    }

                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    System.out.println("返给微信" + resXml);
                } else {

                    sendDataDao.updateTransaction(orders.getOrder_id(),pay_type,"支付失败","","",null);
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                    System.out.println("返给微信" + resXml);
                }
            } else // 如果微信返回支付失败，将错误信息返回给微信
            {

                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
                System.out.println("返给微信" + resXml);
            }
        } else
        {

            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
            System.out.println("返给微信" + resXml);
        }


        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
