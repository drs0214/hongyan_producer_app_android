package com.aerozhonghuan.rxretrofitlibrary;

import rx.Subscriber;

/**
 * Created by zhangyonghui on 2018/1/26.
 */

public abstract class ErrorSubscriber<T> extends Subscriber<T> {

    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            onError((ApiException)e);
        }else{
            onError(new ApiException(e,123));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);
}
