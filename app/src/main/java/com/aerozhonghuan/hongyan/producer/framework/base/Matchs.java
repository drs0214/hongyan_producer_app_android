package com.aerozhonghuan.hongyan.producer.framework.base;

import java.util.regex.Pattern;

/**
 * Created by dell on 2017/11/10.
 */

public class Matchs {
    public static boolean matchPassword(String pwd) {
        String pattern = "^[0-9a-zA-Z\\u0000-\\u00FF]+$";
        boolean isMatch = Pattern.matches(pattern, pwd);
        return isMatch;
    }

    public static boolean isPhoneNumber(String number) {
        boolean match = true;
        if (!number.startsWith("1") || number.length() != 11) {
            match = false;
        }
        return match;
    }
}
