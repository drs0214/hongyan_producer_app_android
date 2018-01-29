package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by zhangyonghui on 2018/1/28.
 */

public class TelephonyUtils {
    public static String getOperator(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telephonyManager.getSimOperator();
        String str;
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                str = "中国移动";
            } else if (operator.equals("46001")) {
                str = "中国联通";
            } else if (operator.equals("46003")) {
                str = "中国电信";
            } else {
                str = "无记录";
            }
        } else {
            str = "无记录";
        }
        return str;
    }
}
