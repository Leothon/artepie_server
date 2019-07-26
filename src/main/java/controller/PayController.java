package controller;

import Config.AlipayConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;

import com.google.gson.JsonObject;
import dao.GetDataDao;
import dao.SendDataDao;
import dao.UserDao;
import dto.Result;
import entity.AlipayBean;
import entity.Merchandise;
import entity.Orders;
import entity.User;
import org.apache.ibatis.annotations.Param;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.SendDataService;
import utils.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    UserDao userDao;

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

            parameters.put("body", "artepieclass-" + orders.getOrder_name());
            parameters.put("out_trade_no", orders.getOrder_number()); // 订单id这里我的订单id生成规则是订单id+时间
            parameters.put("fee_type", "CNY");
            //parameters.put("total_fee", "1"); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
            String tempFee = String.valueOf(Double.parseDouble(orders.getOrder_end_price()) * 100);
            parameters.put("total_fee", tempFee.substring(0, tempFee.indexOf("."))); // 上线后，将此代码放开
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
            if (parMap != null) {
                orders.setPayInfo(JSON.toJSONString(parMap));
                baseResult.setData(orders);
            } else {
                baseResult.setMsg("支付出现异常，请稍后重试!");
                baseResult.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            baseResult.setMsg("程序异常!");
            baseResult.setSuccess(false);
        }
        return baseResult;
    }


    @PostMapping("/verifyalipaytransaction")
    @ResponseBody
    public Result<String> getAliPay(@RequestBody AlipayBean alipayBean) {


        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.ALIPAY_URL, AlipayConfig.APP_ID,
                AlipayConfig.PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGN_TYPE);
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(alipayBean.getBody());
        model.setSubject(alipayBean.getSubject());
        model.setOutTradeNo(alipayBean.getOut_trade_no());// outtradeno 生存订单
        model.setTimeoutExpress("60m");
        model.setTotalAmount(alipayBean.getTotal_amount());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(AlipayConfig.CALLBACK_URL);//异步回调url

// 这里和普通的接口调用不同，使用的是sdkExecute
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return new Result<>(true, response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        // System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。


        return new Result<>(false, "出错");
    }


    @RequestMapping("/alipaynotifyend")
    @ResponseBody
    public Result<String> alipayNotify(@RequestParam("orderinfo") String orderInfo) {


        JSONObject outJson = JSONObject.parseObject(orderInfo);

        String content = outJson.getString("alipay_trade_app_pay_response");
        String sign = outJson.getString("sign");
        try {
            boolean flag = AlipaySignature.rsaCheck(content, sign, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

            if (flag) {


                return new Result<>(true, "成功");


            } else {
                return new Result<>(false, "验签失败");
            }
        } catch (Exception e) {
            return new Result<>(false, "失败");
        }


    }

    @RequestMapping("/alipaynotify")
    @ResponseBody
    public void alipayNotifyAsny(HttpServletRequest request, HttpServletResponse response) throws IOException {


        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            System.out.println("前" + name + ":" + valueStr);
            // 乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            //System.out.println("后" + name + ":" +  valueStr);
            params.put(name, valueStr);
        }

//        VHKbZT359Ce0bh08Brhs4hxMte4v6HInDWTIIK5FEe5+jtIDcbgKbM4YpzuP5zgsIsQgXAaXAQz4I9goD0BGXuirqfr1XaC7c5NizZyeyJfRCx4YseUa5GAUVY2/Z60GIwARRMrCv0C9osGCPpBwCB5iozSCaWH+Ik/ZvV0odF5hHoJeQkNQMDTL/5K5PFyhmdE5Da1FLAeKiIM0dZyPyPUCN5FUS/rB8kgKFDVAvMuDfDP9FLkdUJE5BbN712qAkAN1u7ihFtNkLcMUTB9oskAuFnZfD8pcIZeUfuIDmEFeut7DOmaL//X1d6rhL90dFEDahxJy3NedFQaTEf06IQ==


        // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);

            if (flag) {

                String trade_status = params.get("trade_status");
                String out_trade_no = params.get("out_trade_no");
                String trade_no = params.get("trade_no");
                String price = params.get("total_amount");
                Orders orders = getDataDao.getOrders(out_trade_no);
                if ("TRADE_SUCCESS".equals(trade_status)) { // 交易支付成功的执行相关业务逻辑


                    String endTime = commonUtils.getTime();
                    sendDataDao.updateTransaction(orders.getOrder_id(), "alipay", "已支付", "无", "", endTime);
                    String classBuyId = "buy" + commonUtils.createUUID();
                    sendDataDao.insertClassBuyInfo(classBuyId, orders.getOrder_class_id(), orders.getOrder_user_id(), endTime);
                    String authorPrice = commonUtils.computeAuthorPrice(orders.getOrder_class_price());
                    String authorId = getDataDao.getUserIdByClassId(orders.getOrder_class_id());
                    String authorBalance = userDao.getUserInfo(authorId).getUser_balance();
                    String authorEndBalance = commonUtils.computeBalance(authorBalance, authorPrice);
                    sendDataDao.updateUserBalance(authorId, authorEndBalance);
                    User user = userDao.getUserInfo(orders.getOrder_user_id());
                    String lastCoin = commonUtils.computeLastCoin(orders.getOrder_discount(), user.getUser_art_coin());
                    sendDataDao.updateCoin(user.getUser_id(), lastCoin);
                    if (getDataDao.isFav(orders.getOrder_user_id(), orders.getOrder_class_id()) != 0) {
                        String favId = "fav" + commonUtils.createUUID();
                        sendDataService.addFav(orders.getOrder_user_id(), orders.getOrder_class_id(), favId);
                    }
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write("success".getBytes());
                    out.flush();
                    out.close();
                } else if ("TRADE_CLOSED".equals(trade_status)) { // 未付款交易超时关闭,或支付完成后全额退款,执行相关业务逻辑
                    sendDataDao.updateTransaction(orders.getOrder_id(), "alipay", "支付失败", "无", "", null);

                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                    out.write("failure".getBytes());
                    out.flush();
                    out.close();
                }
            } else {
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write("failure".getBytes());
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @RequestMapping("/wxnotify")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = PayUtils.reciverWx(request); // 接收到异步的参数

        System.out.println("微信返回的结果" + result);
        Map<String, String> m = new HashMap<String, String>();// 解析xml成map
        if (m != null && !"".equals(m)) {
            m = XMLUtil.parseXml(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
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
                String pay_status = "已支付";
                String bank_type = (String) packageParams.get("bank_type");
                String transaction_end_time = (String) packageParams.get("time_end");
                Orders orders = getDataDao.getOrders(out_trade_no);


                // 验证商户ID 和 价格 以防止篡改金额

                String tempPrice = String.valueOf((Double.parseDouble(orders.getOrder_end_price()) * 100));
                String price = tempPrice.substring(0, tempPrice.indexOf("."));
                if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id) && orders != null
                        && total_fee.trim().toString().equals(price)) {
                    /** 这里是我项目里的消费状态
                     * 1.待付款=0 2.付款完成=1
                     * 3.消费成功=2
                     * 4.取消=-1
                     * 5.发起退款=-2
                     * 6.退款成功=-3
                     * 7.退款失败=3（由于商户拒绝退款或其他原因导致退款失败）
                     */

                    sendDataDao.updateTransaction(orders.getOrder_id(), pay_type, pay_status, bank_type, transaction_id, transaction_end_time);
                    String classBuyId = "buy" + commonUtils.createUUID();
                    sendDataDao.insertClassBuyInfo(classBuyId, orders.getOrder_class_id(), orders.getOrder_user_id(), transaction_end_time);
                    String authorPrice = commonUtils.computeAuthorPrice(orders.getOrder_class_price());
                    String authorId = getDataDao.getUserIdByClassId(orders.getOrder_class_id());
                    String authorBalance = userDao.getUserInfo(authorId).getUser_balance();
                    String authorEndBalance = commonUtils.computeBalance(authorBalance, authorPrice);
                    sendDataDao.updateUserBalance(authorId, authorEndBalance);
                    User user = userDao.getUserInfo(orders.getOrder_user_id());
                    String lastCoin = commonUtils.computeLastCoin(orders.getOrder_discount(), user.getUser_art_coin());
                    sendDataDao.updateCoin(user.getUser_id(), lastCoin);
                    if (getDataDao.isFav(orders.getOrder_user_id(), orders.getOrder_class_id()) != 0) {
                        String favId = "fav" + commonUtils.createUUID();
                        sendDataService.addFav(orders.getOrder_user_id(), orders.getOrder_class_id(), favId);
                    }

                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    System.out.println("返给微信" + resXml);
                } else {

                    sendDataDao.updateTransaction(orders.getOrder_id(), pay_type, "支付失败", "", "", null);
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
        } else {

            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
            System.out.println("返给微信" + resXml);
        }


        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }


    @PostMapping("/getcash")
    @ResponseBody
    public Result<String> getCash(@RequestParam("cashinfo") String cashInfo,@RequestParam("token") String token) {

        String uuid = tokenUtils.ValidToken(token).getUid();

        String numberOrder = "c" + commonUtils.createUUID();
        String endInfo = "{ \"out_biz_no\":\"" + numberOrder + "\"," + cashInfo;

        System.out.println("未合成订单" + cashInfo);
        System.out.println("合成订单" + endInfo);
        JSONObject outJson = JSONObject.parseObject(endInfo);

        String amount = outJson.getString("amount");

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.ALIPAY_URL, AlipayConfig.APP_ID,
                AlipayConfig.PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGN_TYPE);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent(endInfo);
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);

            if (response.isSuccess()) {

                String newBalance = commonUtils.computeLastBalance(amount,userDao.getUserInfo(uuid).getUser_balance());

                sendDataDao.updateUserBalance(uuid,newBalance);

                return new Result<>(true,"订单号" + response.getOrderId() + "账单号" + response.getOutBizNo() + "提现日期" + response.getPayDate());
            } else {
                return new Result<>(false,"提现失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new Result<>(false,"失败");




    }
}
