package com.aerozhonghuan.hongyan.producer.widget;

import android.app.TimePickerDialog;
import android.content.Context;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/8/7 0007 on 下午 4:55
 */

public class CustomTimePickerDialog extends TimePickerDialog {
    public CustomTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }

    public CustomTimePickerDialog(Context context, int themeResId, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, themeResId, listener, hourOfDay, minute, is24HourView);
    }

    @Override
    protected void onStop() {
//          注释这里，onstop就不会调用回调方法
//          super.onStop();
    }
}
