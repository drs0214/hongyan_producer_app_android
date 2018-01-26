package com.aerozhonghuan.hongyan.producer.framework.base;

import android.text.TextUtils;

import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangyonghui on 2018/1/23.
 */

public class HeaderParamInterceptor implements Interceptor {
    Map<String, String> headerParamsMap = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        if (headerParamsMap != null && headerParamsMap.size() > 0) {
            Set<String> keys = headerParamsMap.keySet();
            for (String headerKey : keys) {
                requestBuilder.addHeader(headerKey, headerParamsMap.get(headerKey)).build();
            }
        }
        if (!TextUtils.isEmpty(UserInfoManager.getCookieSession())) {
            requestBuilder.addHeader("Cookie", UserInfoManager.getCookieSession());
        }
        request = requestBuilder.build();
        return chain.proceed(request);
    }

    public static class Builder {

        HeaderParamInterceptor interceptor;

        public Builder() {
            interceptor = new HeaderParamInterceptor();
        }

        public HeaderParamInterceptor.Builder addHeaderParam(String key, String value) {
            interceptor.headerParamsMap.put(key, value);
            return this;
        }

        public HeaderParamInterceptor.Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.headerParamsMap.putAll(headerParamsMap);
            return this;
        }

        public HeaderParamInterceptor build() {
            return interceptor;
        }

    }
}
