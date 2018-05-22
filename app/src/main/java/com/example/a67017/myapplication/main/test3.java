package com.example.a67017.myapplication.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 67017 on 2017/12/26.
 */

public class Test3 {
    public static void main(String[] args) throws ParseException {
        String ss = "2017-3-08";
        String a = getFormatDate(ss, "yyyyMMdd");
        System.out.println(a);

/*        Stack<String> aa = new Stack<>();
        aa.push("111");
        aa.push("w222");
        for (String temp : aa) {
            System.out.println(temp);
        }*/
    }

    public static String getFormatDate(String text, String formatType) {
        // 格式必须和text保持一致
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);
        Date date = null;
        try {
            date = sdf1.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat(formatType, Locale.CHINESE);
        String b = sdf2.format(date);
        return b;
    }
}
