package com.aerozhonghuan.rxretrofitlibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import rx.Subscription;

/**
 * Created by zhangyonghui on 2018/1/27.
 * 控制取消请求的类
 */

public class RxApiManager implements RxRequstManager {

    private static RxApiManager sInstance = null;

    private HashMap<Object, List<Subscription>> maps;

    public static RxApiManager get() {

        if (sInstance == null) {
            synchronized (RxApiManager.class) {
                if (sInstance == null) {
                    sInstance = new RxApiManager();
                }
            }
        }
        return sInstance;
    }

    private RxApiManager() {
        maps = new HashMap<>();
    }

    @Override
    public void add(Object tag, Subscription subscription) {
        if (maps.get(tag) != null) {
            List<Subscription> subscriptions = maps.get(tag);
            subscriptions.add(subscription);
            maps.put(tag, subscriptions);
        } else {
            List<Subscription> subscriptions = new ArrayList<>();
            subscriptions.add(subscription);
            maps.put(tag, subscriptions);
        }
    }

    @Override
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    @Override
    public void removeAll(){
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    @Override
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        List<Subscription> subscriptions = maps.get(tag);
        for (Subscription subscription :
                subscriptions) {
            if (subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
        maps.remove(tag);
    }

    @Override
    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
