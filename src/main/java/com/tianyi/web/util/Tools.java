package com.tianyi.web.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 雪 峰
 */
public class Tools {

    public static String getCharacterAndNumber(int length) {
        String val = "";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum))
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum))
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        return val;
    }

    public static String getRandomNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifySpecialCharacters( String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }



    public  static double getDecimalFour(double num){
        DecimalFormat dec = new DecimalFormat("0.0000");
        return  Double.parseDouble(dec.format(num)) ;
    }

    public static double toDoulbe(Long number){
        if(number != null){
            return  Double.parseDouble(String.valueOf(number));
        }
        return 0L;
    }
}
