package utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import entity.TokenValid;
import net.minidev.json.JSONObject;
import org.springframework.test.context.TestExecutionListeners;

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



//    public static void  main(String [] args){
////        //String token = tokenUtils.getToken("19861986198619861986");
////
////        TokenValid tokenValid = tokenUtils.ValidToken("eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxOTg2MDAwMDAwMDAwMDAwMDAwMSIsInN0YSI6MTU0NjI0MDIwODY3MywiZXhwIjoxNTYxNzkyMjA4NjczfQ.um79TgWI7e2za8YkKuJ6OpZewNZY5a3LVfGXmhhdk-U");
////        //TokenValid tokenValid = tokenUtils.ValidToken("eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiI1Njk5MTAwMDAwMDA5OTQxNDEyNCIsInN0YSI6MTU2MDkzMjc3Mzg0MiwiZXhwIjoxNTc2NDg0NzczODQyfQ.ySvlLHZgIf1iKfaioxdU3l2LLm4rjZr5Np_VrYpmMdI");
////
////        String uuid = tokenValid.getUid();
////        if (tokenValid.isExpired()){
////            System.out.println("过期" + uuid);
////        }else {
////            System.out.println("没过期");
////        }
//
//
//
//        System.out.println(String.valueOf(Double.parseDouble("25.99")*100));
//    }
}
