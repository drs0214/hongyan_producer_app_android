package com.aerozhonghuan.hongyan.producer.modules.user.logic;

import com.aerozhonghuan.hongyan.producer.http.HttpLoader;

import rx.Observable;

/**
 * Created by zhangyonghui on 2018/1/25.
 */

public class LoginHttpLoader extends HttpLoader {
    public Observable<String> getSession(String userName, String password){
        return observe(apiService.getSession(userName,password));
    }
}
