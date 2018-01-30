package com.aerozhonghuan.hongyan.producer.framework.base;

import android.content.Context;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.rxretrofitlibrary.ApiException;
import com.aerozhonghuan.rxretrofitlibrary.ErrorSubscriber;
import com.aerozhonghuan.rxretrofitlibrary.ExceptionEngine;

import java.lang.ref.WeakReference;

/**
 * Created by zhangyonghui on 2018/1/26.
 * 自定义观察者
 */

public class MySubscriber<T> extends ErrorSubscriber<T> {

    private WeakReference<Context> activityWeakReference;
    private ProgressDialogIndicator progressDialogIndicator;
    public MySubscriber(Context context,ProgressDialogIndicator progressDialogIndicator) {
        super();
        this.activityWeakReference = new WeakReference<>(context);
        this.progressDialogIndicator = progressDialogIndicator;
    }

    public MySubscriber(Context context){
        super();
        this.activityWeakReference = new WeakReference<>(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (progressDialogIndicator != null) {
            progressDialogIndicator.onProgressStart();
        }
    }

    @Override
    protected void onError(ApiException ex) {
        if (progressDialogIndicator != null) {
            progressDialogIndicator.onProgressEnd();
        }
        Toast.makeText(activityWeakReference.get().getApplicationContext(), ex.message, Toast.LENGTH_SHORT).show();
        switch (ex.code){
            case ExceptionEngine.UNAUTHORIZED:
                UserInfoManager.logout(activityWeakReference.get());
                break;
        }
        clear();
    }

    @Override
    public void onCompleted() {
        if (progressDialogIndicator != null) {
            progressDialogIndicator.onProgressEnd();
        }
        clear();
    }

    @Override
    public void onNext(T t) {

    }

    public void clear(){
        if (activityWeakReference != null && activityWeakReference.get() != null)
            activityWeakReference.clear();
        if (progressDialogIndicator != null)
            progressDialogIndicator = null;
    }
}


