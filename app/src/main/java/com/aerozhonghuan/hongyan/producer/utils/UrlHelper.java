package com.aerozhonghuan.hongyan.producer.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhangyunfei on 2017/7/25.
 */

public class UrlHelper {

    public static String UrlEncode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }
}
