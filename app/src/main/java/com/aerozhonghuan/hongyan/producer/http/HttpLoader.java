package com.aerozhonghuan.hongyan.producer.http;

import com.aerozhonghuan.rxretrofitlibrary.HttpManager;
import com.aerozhonghuan.rxretrofitlibrary.HttpResponseFunc;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 将一些重复的操作提出来，放到父类以免Loader 里每个接口都有重复代码
 * Created by zhangyonghui on 2018/1/23.
 */

public class HttpLoader {
    protected ApiService apiService ;

    public HttpLoader(){
        apiService = HttpManager.getInstance().create(ApiService.class);
    }

    /**
     *
     * @param observable
     * @param <T>
     * @return
     */
    protected  <T> Observable<T> observe(Observable<T> observable){
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .map(new ServerResponseFunc<T>())
                // 为拦截onError事件的拦截器
                .onErrorResumeNext(new HttpResponseFunc<T>());
    }
}


