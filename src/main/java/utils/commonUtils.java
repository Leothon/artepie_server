package utils;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static String getTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    public static String getTimeRange(String nowTime,String calTime){


        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");


        int days = 0;
        try {
            java.util.Date now = simpleFormat.parse(nowTime);
            java.util.Date cal = simpleFormat.parse(calTime);
            String fromDate = simpleFormat.format(cal);
            String toDate = simpleFormat.format(now);
            long from = simpleFormat.parse(fromDate).getTime();
            long to = simpleFormat.parse(toDate).getTime();
            days = (int) ((to - from)/(1000 * 60 * 60 * 24));
        }catch (Exception e){
            e.printStackTrace();
        }


        return Integer.toString(days);

    }

    public static void stringToFile(String article,String articleId){
        try {



            File file = new File("/image/" + articleId + ".txt");
            //File file = new File("D:/" + articleId + ".txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            BufferedWriter writer=new BufferedWriter(write);
            writer.write(article);
            writer.close();

//            FileWriter fw = new FileWriter(file.getAbsoluteFile());
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(article);
//            bw.close();
            file.renameTo(new File("/image/" + articleId + ".html"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String args[]){
//        stringToFile("aawwwwa","sdws");
//    }


}
