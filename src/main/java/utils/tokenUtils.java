package utils;

import Config.AlipayConfig;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import dto.Result;
import entity.TokenValid;
import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class tokenUtils {



    private static final byte[] secret = "autogynephiliaperfectismleothonw".getBytes();


    //生成一个token
    public static String creatToken(Map<String,Object> payloadMap) throws JOSEException {

        //3.先建立一个头部Header
        /**
         * JWSHeader参数：1.加密算法法则,2.类型，3.。。。。。。。
         * 一般只需要传入加密算法法则就可以。
         * 这里则采用HS256
         *
         * JWSAlgorithm类里面有所有的加密算法法则，直接调用。
         */
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        //建立一个载荷Payload
        Payload payload = new Payload(new JSONObject(payloadMap));

        //将头部和载荷结合在一起
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        //建立一个密匙

        JWSSigner jwsSigner = new MACSigner(secret);

        //签名
        jwsObject.sign(jwsSigner);

        //生成token
        return jwsObject.serialize();
    }



    //解析一个token

    public static Map<String,Object> valid(String token) throws ParseException, JOSEException {

//        解析token

        JWSObject jwsObject = JWSObject.parse(token);

        //获取到载荷
        Payload payload = jwsObject.getPayload();

        //建立一个解锁密匙
        JWSVerifier jwsVerifier = new MACVerifier(secret);

        Map<String, Object> resultMap = new HashMap<>();
        //判断token
        if (jwsObject.verify(jwsVerifier)) {
            resultMap.put("Result", 0);
            //载荷的数据解析成json对象。
            JSONObject jsonObject = payload.toJSONObject();
            resultMap.put("data", jsonObject);

            //判断token是否过期
            if (jsonObject.containsKey("exp")) {
                Long expTime = (Long)jsonObject.get("exp");
                Long nowTime = new Date().getTime();
                //判断是否过期
                if (nowTime > expTime) {
                    //已经过期
                    System.out.println(expTime + "   " + nowTime);
                    //resultMap.clear();
                    resultMap.put("Result", 2);

                }
            }
        }else {
            resultMap.put("Result", 1);
        }
        return resultMap;

    }

    //生成token的业务逻辑
    public static String getToken(String uid) {
        //获取生成token

        Map<String, Object> map = new HashMap<>();

        //建立载荷，这些数据根据业务，自己定义。
        map.put("uid", uid);
        //生成时间
        map.put("sta", new Date().getTime());
        //过期时间
        map.put("exp", new Date().getTime() + 6L*30L*24L*60L*60L*1000L);
        try {
            String token = creatToken(map);
            System.out.println("token="+token);
            return token;
        } catch (JOSEException e) {
            System.out.println("生成token失败");
            e.printStackTrace();
        }
        return null;

    }

    //处理解析的业务逻辑
    public static TokenValid ValidToken(String token) {
        //解析token
        TokenValid tokenValid = new TokenValid();
        try {
            if (token != null) {

                Map<String, Object> validMap = valid(token);

                int i = (int) validMap.get("Result");

                JSONObject jsonObject = (JSONObject) validMap.get("data");


                tokenValid.setUid(jsonObject.get("uid").toString());

                tokenValid.setSta(jsonObject.get("sta").toString());

                tokenValid.setExp(jsonObject.get("exp").toString());
                if (i == 0) {
                    tokenValid.setExpired(false);


                } else if (i == 2) {
                    tokenValid.setExpired(true);

                }

                return tokenValid;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return null;
    }



    public static void  main(String [] args){


//        JsonParser parse = new JsonParser();
//        JsonObject pay_response = (JsonObject) parse.parse("{\"alipay_trade_app_pay_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2019012063113262\",\"auth_app_id\":\"2019012063113262\",\"charset\":\"utf-8\",\"timestamp\":\"2019-07-22 20:55:14\",\"out_trade_no\":\"100000051237123820190722205503\",\"total_amount\":\"0.01\",\"trade_no\":\"2019072222001412850540662708\",\"seller_id\":\"2088431241820052\"},\"sign\":\"E6uq9vj6EoctF1NWCdqrg7wvJNmFtOVuxwZMxYUYhdPA35Y0ktuKE+YcE5NBls3uEAEXb2Xh1H+oyzZJdmwOAEZ5PiRg95bg8k9SqHbjAvZs4NKaWMSXP0SNI6bgkKPbWnsSXHSQvs18zMqpXmpjNw0g/kx0if0RuXQkHdXT2PsuZ8qR5FL23isFtKAtL6D9/vI+PrChq0WWLhMWdwCLe30vuu2sNh9j2GwAkBVgxUCnFulSb0/I7AQ4z5Iub2Ch0V2DsKb4B3Jh/C6li1NPSStj5F4Nhxp08yfxnf4DbBrQiuCbsVmktKJr4XQTqKWIl4hJqbOWRRq+fygeWI0MsA==\",\"sign_type\":\"RSA2\"}");
//        String content = pay_response.get("alipay_trade_app_pay_response").toString();
//        String sign = pay_response.get("sign").toString();
//        String sign_type = pay_response.get("sign_type").toString();
//        System.out.println(content);
//        System.out.println(sign);
//        System.out.println(sign_type);
//        try {
//            boolean flag = AlipaySignature.rsaCheck(content,sign, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.CHARSET,"RSA2");
//            System.out.println(flag);
//            if (flag) {
//
//
//
//                System.out.println("成功");
//
//            }else {
//                System.out.println("失败");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        System.out.println(AlipayConfig.PRIVATE_KEY.length() + "");

    }
}
