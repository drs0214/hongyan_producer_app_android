package com.aerozhonghuan.rxretrofitlibrary;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zhangyonghui on 2018/1/24.
 */

public class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
    @Override
    public Observable<T> call(Throwable throwable) {
        //ExceptionEngine为处理异常的驱动器
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
