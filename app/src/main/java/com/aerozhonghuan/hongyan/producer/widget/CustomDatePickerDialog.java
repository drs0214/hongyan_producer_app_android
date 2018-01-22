package com.aerozhonghuan.hongyan.producer.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/8/7 0007 on 下午 5:02
 */

public class CustomDatePickerDialog extends DatePickerDialog {

    public CustomDatePickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    public CustomDatePickerDialog(@NonNull Context context, @StyleRes int themeResId, @Nullable OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }

    @Override
    protected void onStop() {
        // super.onStop();
    }
}
