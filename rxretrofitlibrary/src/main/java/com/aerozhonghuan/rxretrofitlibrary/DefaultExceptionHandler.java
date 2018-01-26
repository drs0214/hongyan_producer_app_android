package com.aerozhonghuan.rxretrofitlibrary;

/**
 * HTTP  异常处理器
 * Created by zhangyunfei on 15/12/25.
 */
public interface DefaultExceptionHandler {
    ApiException handleException(Throwable throwable);
}
