package com.aerozhonghuan.hongyan.producer.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/8/1 0001 on 下午 1:35
 */

public class WindowUtil {
    public static int getWindowWeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = (int) (dm.widthPixels / dm.density);
        return screenWidth;
    }
}
