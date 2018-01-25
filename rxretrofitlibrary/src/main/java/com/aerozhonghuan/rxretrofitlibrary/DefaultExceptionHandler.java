package com.aerozhonghuan.rxretrofitlibrary;

import android.content.Context;

/**
 * HTTP  异常处理器
 * Created by zhangyunfei on 15/12/25.
 */
public interface DefaultExceptionHandler {
    void handleException(Context context, int httpCode, Exception ex1, ServerResponse serverResponse, String responseString);
}
