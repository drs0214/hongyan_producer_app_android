package com.aerozhonghuan.hongyan.producer.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangyunfei on 17/6/29.
 */

public class CarNumberFormat {

    /**
     * 验证车牌号 是否合法
     *
     * @param carNumber
     * @return
     */
    public static boolean isValid(String carNumber) {
        boolean isok = true;
        //待补充
        if (TextUtils.isEmpty(carNumber))
            return false;
        // 创建 Pattern 对象
//        final String pattern = "^[\\u4e00-\\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}$";
        final String pattern = "^[\\u4e00-\\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9_\\u4e00-\\u9fa5]{5}$";
        return match(pattern, carNumber);
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
