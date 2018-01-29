package com.aerozhonghuan.rxretrofitlibrary;

import rx.Subscription;

/**
 * Created by zhangyonghui on 2018/1/27.
 */

public interface RxRequstManager<T> {
    void add(T tag, Subscription subscription);
    void remove(T tag);
    void removeAll();
    void cancel(T tag);
    void cancelAll();
}
