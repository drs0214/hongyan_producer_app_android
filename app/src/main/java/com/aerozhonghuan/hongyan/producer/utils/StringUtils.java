package com.aerozhonghuan.hongyan.producer.utils;

import android.text.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by zhangyunfei on 2017/8/16.
 */

public class StringUtils {

    /**
     * @param str
     * @param defaultStr
     * @return
     */
    public static String ifEmpty(String str, String defaultStr) {
        return TextUtils.isEmpty(str) ? defaultStr : str;
    }

    public static String ifEmpty(String str, String defaultStr, String newStr) {
        return TextUtils.isEmpty(str) ? defaultStr : newStr;
    }

    public static String getJsonStr(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.substring(1, str.length() - 1);
            Map docType = new HashMap();
            StringTokenizer entrys = new StringTokenizer(str, ",");
            StringTokenizer item;
            while (entrys.hasMoreTokens()) {
                String s = entrys.nextToken();
                item = new StringTokenizer(s, ":");
                docType.put(item.nextToken(), item.hasMoreTokens() ? item.nextToken() : "");
            }
            return new JSONObject(docType).toString();
        } else {
            return "";
        }

    }
}
