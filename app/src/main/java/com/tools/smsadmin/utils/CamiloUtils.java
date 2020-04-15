package com.tools.smsadmin.utils;


import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * @author w（C）
 * describe
 */
public class CamiloUtils {

    public static void product() {

    }

    public static String getNetTime() {
        URL url = null;//取得资源对象
        String date = "";
        try {
            url = new URL("http://www.baidu.com");
            //url = new URL("http://www.ntsc.ac.cn");//中国科学院国家授时中心
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            date = formatter.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            date = e.getMessage();
            return date;
        }

        return date;
    }


    /**
     * @param length 每个写入的长度
     * @return 生成的字符串
     */
    public static String getRandomStr(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        String currentDay = DateUtils.getCurrentDate();
        for (int i = 0; i < 100; i++) {
            stringBuilder.append(getRandom(length) + " " + currentDay);
            if (i != 99) {
                stringBuilder.append("\n");
            }
        }

        return stringBuilder.toString();
    }


    private static String getRandom(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }


}


