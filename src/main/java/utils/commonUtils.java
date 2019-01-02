package utils;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.UUID;


public class commonUtils {

    /**
     * 生成验证码
     * @return
     */
    public static int getVerifyCode(){

        Random r = new Random();

        return r.nextInt(900000)+100000;

    }


    public static void sendVerifyCode(String phoneNumber,int verifyCode){

        //TODO 此处通过第三方接口给手机号码发送生成的验证码
    }


    public static String createUUID() {
        int machineId = 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if(hashCodeV < 0) {//有可能是负数
            hashCodeV = - hashCodeV;
        }
        return machineId + String.format("%015d", hashCodeV);
    }


}
