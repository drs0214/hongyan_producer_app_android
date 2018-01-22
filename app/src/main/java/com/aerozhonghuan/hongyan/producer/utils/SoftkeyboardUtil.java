package com.aerozhonghuan.hongyan.producer.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘的控制
 * Created by zhangyunfei on 17/3/6.
 */

public class SoftkeyboardUtil {

    public static void hideSoftkeyboard(Context context, View view1) {
        if (view1 == null) return;
        if (context == null) return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view1.getWindowToken(), 0); //强制隐藏键盘
    }

    public static void showSoftkeyboard(final Context context, final View view1) {
        if (view1 == null) return;
        if (context == null) return;
        view1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view1 == null || context == null)
                    return;
                if (context instanceof Activity) {
                    if (((Activity) context).isFinishing()) {
                        return;
                    }
                }
                view1.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view1, InputMethodManager.SHOW_FORCED);
            }
        }, 600);
    }
}
