package com.aerozhonghuan.hongyan.producer.utils;

/**
 * 电话号码
 * Created by zhangyunfei on 17/6/29.
 */

public class PhoneNumberFormat {
    /**
     * 是否是有效的电话号码
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        char[] chars = phoneNumber.toCharArray();
        char[] CHAR_PHONE = new char[]{'+', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        boolean isAChar = false;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            isAChar = false;
            for (char item : CHAR_PHONE) {
                if (c == item) {
                    isAChar = true;
                    break;
                }
            }
            if (!isAChar) {
                break;
            }
        }
        return isAChar;
    }

}
