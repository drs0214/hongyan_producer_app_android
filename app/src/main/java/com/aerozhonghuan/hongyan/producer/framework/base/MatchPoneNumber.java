package com.aerozhonghuan.hongyan.producer.framework.base;

/**
 * Created by dell on 2017/9/25.
 * 手机号格式验证
 */

public class MatchPoneNumber {
    public static boolean isPhoneNumber(String number) {
        boolean match = true;
        if (!number.startsWith("1") || number.length() != 11) {
            match = false;
        }
        return match;
    }
}
