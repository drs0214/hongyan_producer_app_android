package com.aerozhonghuan.hongyan.producer.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * 防止内存泄漏，释放 dialog和context的关联
 * Created by zhangyf on 2015/1/4.
 */
public class DialogProgress2 {
    private static final String TAG = "DialogProgress2";
    private Handler mHandler;

    /**
     * Route 模块共用进度对话框
     */
    private ProgressDialog mProgressDialog = null;


    public DialogProgress2(Context aContext) {
        if (mProgressDialog == null
                || !mProgressDialog.getContext().equals(aContext)) {
            if (aContext == null) {
                return;
            }
            mProgressDialog = new ProgressDialog(aContext);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("加载中...");
            mProgressDialog.setCancelable(true);
        }
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 模块共用进度对话框
     */
    public void show() {
//        LogUtil.d(TAG,"#showChooseImageDialog");
        if (mHandler == null) return;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mProgressDialog.isShowing()) {
                    try {
                        mProgressDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 200);
    }

    public void setMessage(String msg) {
        mProgressDialog.setMessage(msg);
    }

    public void dismiss() {
//        LogUtil.d(TAG,"#dismiss");
        if (mHandler == null) return;
        mHandler.removeCallbacksAndMessages(null);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null || !mProgressDialog.isShowing()) {
                    return;
                } else {
                    try {
                        mProgressDialog.cancel();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }
        });
    }

    public void release() {
        if (mProgressDialog != null) {
            dismiss();
        }
        mProgressDialog = null;
        mHandler = null;
    }
}
