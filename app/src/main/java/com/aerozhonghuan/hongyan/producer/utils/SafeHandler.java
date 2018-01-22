package com.aerozhonghuan.hongyan.producer.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 安全的 handler
 * Created by zhangyunfei on 17/2/14.
 */
public class SafeHandler<T> extends Handler {
    private WeakReference<T> innerObject;

    public SafeHandler(T var1) {
        this.innerObject = new WeakReference<T>(var1);
    }

    public SafeHandler(T var2, Looper var1) {
        super(var1);
        this.innerObject = new WeakReference<T>(var2);
    }

    protected T getInnerObject() {
        return this.innerObject == null ? null : this.innerObject.get();
    }

    public void handleMessage(Message var1) {
        if (this.getInnerObject() != null) {
            super.handleMessage(var1);
        }
    }

    public void clear() {
        if (innerObject != null)
            innerObject.clear();
    }
}
