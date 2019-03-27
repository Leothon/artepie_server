package utils;

import java.security.MessageDigest;

public class MD5Util {


    public final static String MD5Encode(String string,String ty){

        //使用md5之后，转为十六进制
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        try {
            byte[] btInput = string.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(btInput);
            byte[] md = messageDigest.digest();

            int j = md.length;
            char str[] = new char[j*2];
            int k = 0;
            for (int i = 0;i < j;i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch (Exception e){
            return null;
        }
    }
}
