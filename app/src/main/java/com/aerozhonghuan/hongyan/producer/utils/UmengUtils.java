package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;

import com.aerozhonghuan.foundation.umeng.UmengAgent;

/**
 * Created by zhangyonghui on 2017/12/11.
 */

public class UmengUtils {
    public static void onEvent(Context context, String eventId, String eventName) {
        UmengAgent.onEvent(context, eventId, eventName);
    }
}
