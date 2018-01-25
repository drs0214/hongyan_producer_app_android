package com.aerozhonghuan.rxretrofitlibrary;

/**
 * Created by zhangyonghui on 2018/1/23.
 * 封装业务异常
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
