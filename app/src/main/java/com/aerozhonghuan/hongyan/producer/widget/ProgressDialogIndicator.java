package com.aerozhonghuan.hongyan.producer.widget;

import android.content.Context;

import com.aerozhonghuan.oknet2.ProgressIndicator;

/**
 * 进度指示
 * Created by zhangyunfei on 17/6/23.
 */

public class ProgressDialogIndicator implements ProgressIndicator {
    DialogProgress2 progressDialog;

    public ProgressDialogIndicator(Context context) {
        progressDialog = new DialogProgress2(context);
    }

    @Override
    public void onProgressStart() {
        if (progressDialog != null)
            progressDialog.show();
    }

    @Override
    public void onProgressEnd() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void setProgressMessage(String msg) {
        if (progressDialog != null) {
            progressDialog.setMessage(msg);
        }
    }

    public void release() {
        if (progressDialog != null)
            progressDialog.release();
    }
}
