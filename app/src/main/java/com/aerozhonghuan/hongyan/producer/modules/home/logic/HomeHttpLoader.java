package com.aerozhonghuan.hongyan.producer.modules.home.logic;

import com.aerozhonghuan.hongyan.producer.http.HttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsBean;

import rx.Observable;

/**
 * Created by zhangyonghui on 2018/1/25.
 */

public class HomeHttpLoader extends HttpLoader {
    public Observable<PermissionsBean> getAuthorization(){
        return observe(apiService.getAuthorization());
    }

}
