package com.aerozhonghuan.rxretrofitlibrary;

import com.aerozhonghuan.rxretrofitlibrary.HttpConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 发送请求时，拦截 发送的请求参数
 * Created by zhangyunfei on 16/3/14.
 */
public class RequestParaInterceptor implements Interceptor{
    Map<String, String> paramsMap = new HashMap<>();
    private static final String TAG = "okhttp";
    private RequestParaInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        RequestBody body = request.body();

        if (request.method().equals("GET") && paramsMap.size() > 0) {
            request = injectParamsIntoUrl(request.url().newBuilder(), requestBuilder, paramsMap);
        } else if (request.method().equals("POST") && paramsMap.size() > 0) {
            if (body != null) {
                if (body instanceof FormBody) {
                    addParamsToFormBody((FormBody)body, requestBuilder);
                } else if (body instanceof MultipartBody) {
                    addParamsToMultipartBody((MultipartBody) body, requestBuilder);
                }
            }
            request = requestBuilder.build();
        }
        return chain.proceed(request);
    }

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param body
     * @param requestBuilder
     * @return
     */
    private void addParamsToMultipartBody(MultipartBody body, Request.Builder requestBuilder) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            builder.addFormDataPart(entry.getKey(), entry.getValue());
        }
        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }
        requestBuilder.post(builder.build());
    }

    /**
     * 为FormBody类型请求体添加参数
     *
     * @return
     */
    private void addParamsToFormBody(FormBody body, Request.Builder requestBuilder) {
        Map<String, String> formMap = new HashMap<>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            formMap.put(entry.getKey(), entry.getValue());
        }
        for (int i = 0; i < body.size(); i++) {
            formMap.put(body.name(i), body.value(i));
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (HttpConfig.isPostJson) {
            Gson gson = new Gson();
            String jsonParams = gson.toJson(formMap);
            requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), jsonParams));
        } else {
            for (Map.Entry<String, String> entry : formMap.entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
            body = formBodyBuilder.build();
            requestBuilder.post(body);
        }
    }

    // func to inject params into url
    private Request injectParamsIntoUrl(HttpUrl.Builder httpUrlBuilder, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        Iterator iterator = paramsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        requestBuilder.url(httpUrlBuilder.build());
        return requestBuilder.build();
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public static class Builder {

        RequestParaInterceptor interceptor;

        public Builder() {
            interceptor = new RequestParaInterceptor();
        }

        public Builder addParam(String key, String value) {
            interceptor.paramsMap.put(key, value);
            return this;
        }

        public Builder addParamsMap(Map<String, String> paramsMap) {
            interceptor.paramsMap.putAll(paramsMap);
            return this;
        }

        public RequestParaInterceptor build() {
            return interceptor;
        }

    }
}
