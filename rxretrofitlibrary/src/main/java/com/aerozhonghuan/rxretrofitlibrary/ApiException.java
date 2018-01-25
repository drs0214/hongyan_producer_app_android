package com.aerozhonghuan.rxretrofitlibrary;
/**
 * Created by zhangyonghui on 2018/1/24.
 * 封装网络请求异常
 */

public class ApiException extends Exception {
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
