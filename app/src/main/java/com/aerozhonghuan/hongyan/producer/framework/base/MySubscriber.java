package com.aerozhonghuan.hongyan.producer.framework.base;

import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.hongyan.producer.modules.common.event.DefalutHttpExceptionAlert;
import com.aerozhonghuan.rxretrofitlibrary.ApiException;
import com.aerozhonghuan.rxretrofitlibrary.ErrorSubscriber;
import com.aerozhonghuan.rxretrofitlibrary.log.LoggerUtil;

/**
 * Created by zhangyonghui on 2018/1/26.
 */

public class MySubscriber<T> extends ErrorSubscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
        LoggerUtil.d("开始访问网络请求");
    }

    @Override
    protected void onError(ApiException ex) {
        LoggerUtil.d("onError::"+ex.message);
        EventBusManager.post(new DefalutHttpExceptionAlert(ex.message));
        /*if (ex.code == ExceptionEngine.UNAUTHORIZED) {
            UserInfoManager.logout(context);
        }*/
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {

    }
}


