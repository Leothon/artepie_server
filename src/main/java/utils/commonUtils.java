package utils;

import entity.Bill;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.beans.Encoder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class commonUtils {


    public static String computeBalance(String oldBalance,String classPrice){


        DecimalFormat df = new DecimalFormat("#.00");
        return  df.format(Float.valueOf(oldBalance) + Float.valueOf(classPrice));
    }


    public static String computeLastCoin(String removePrice,String totalCoin){
        Float removeCoinCount = Float.valueOf(removePrice)*100;
        String endPrice = String.valueOf(Float.valueOf(totalCoin) - removeCoinCount);

        return endPrice.substring(0,endPrice.indexOf("."));
    }

    public static String computeLastBalance(String removePrice,String oldBalance){

        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(Float.valueOf(oldBalance) - Float.valueOf(removePrice));
    }

    public static String computeAuthorPrice(String classPrice){
        DecimalFormat df = new DecimalFormat("#.00");

        return df.format(Float.valueOf(classPrice)*0.88);
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


    public static void main(String args[]){


        List<RegisterEntity> registerEntities = MoreUtils.getExcel("D:/data.xlsx");


        for (int i = 0;i < registerEntities.size();i ++){

            String username = registerEntities.get(i).getUsername();
            String userphone = registerEntities.get(i).getUserPhone();
            String time = "2019-09-02";
            String info = sendGet("https://www.artepie.com/artepie/createmoreuserwithtime",username,userphone,time);
            System.out.println(info);
        }

    }



    public static String sendGet(String urls, String username,String userphone,String time) {
        String result = "";
        //BufferedReader in = null;

        try {
            String nameE = URLEncoder.encode(username,"UTF-8");
            String urlNameString = urls + "?username=" + nameE + "&userphone=" + userphone + "&time=" + time;
            URL url = new URL(urlNameString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //PrintWriter out = null;

            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            while ((str = br.readLine()) != null) {
                str=new String(str.getBytes(),"UTF-8");//解决中文乱码问题
                result = str;
            }
            is.close();
            conn.disconnect();
            System.out.println("完整结束");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
