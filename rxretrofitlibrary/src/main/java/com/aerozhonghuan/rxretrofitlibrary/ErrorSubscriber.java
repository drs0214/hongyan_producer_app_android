package com.aerozhonghuan.rxretrofitlibrary;

import com.aerozhonghuan.rxretrofitlibrary.log.LoggerUtil;

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
            LoggerUtil.d("非apiException异常::"+e.toString());
            onError(new ApiException(e,1234));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);
}
