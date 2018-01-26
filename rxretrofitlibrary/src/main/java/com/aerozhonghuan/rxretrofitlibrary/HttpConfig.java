package com.aerozhonghuan.rxretrofitlibrary;

import android.app.Application;

import com.aerozhonghuan.rxretrofitlibrary.log.LoggerUtil;

import java.io.File;

import okhttp3.Interceptor;

/**
 * HttpConfig
 * <p>
 * Created by zhangyunfei on 16/3/11.
 */
public class HttpConfig {
    private static File externalCacheDir;
    private static boolean isEnableGzipRequest = false;
    private static Application application;
    private static boolean debug;
    private static String BASE_URL;
    public static boolean isPostJson;
    public static final boolean USEJSON = true;
    public static final boolean DONTUSEJSON = false;


    public static void init(Application app, boolean debug, String base_url, boolean isUseJson){
        setApplication(app);
        setDebug(debug);
        LoggerUtil.init(debug);
        setBaseUrl(base_url);
        isPostJson = isUseJson;
    }

    public static void init(Application app, boolean debug, String base_url){
        setApplication(app);
        setDebug(debug);
        LoggerUtil.init(debug);
        setBaseUrl(base_url);
        isPostJson = false;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        HttpConfig.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        HttpConfig.debug = debug;
    }

    public static File getExternalCacheDir() {
        return externalCacheDir;
    }

    /**
     * 设置 缓存目录
     *
     * @param externalCacheDir externalCacheDir
     */
    public static void setExternalCacheDir(File externalCacheDir) {
        HttpConfig.externalCacheDir = externalCacheDir;
    }

    public static void setRequestParaInterceptor(Interceptor mMyRequestParaInterceptor) {
        HttpManager.setRequestParaInterceptor(mMyRequestParaInterceptor);
    }

    public static void setHeaderParamInterceptor(Interceptor headerParamInterceptor) {
        HttpManager.setHeaderParamInterceptor(headerParamInterceptor);
    }

    /**
     * 设置 默认异常处理器
     *
     * @param defaultExceptionHandler defaultExceptionHandler
     */
    public static void setDefaultExceptionHandler(DefaultExceptionHandler defaultExceptionHandler) {
//        HttpResponseFunc.setDefaultExceptionHandler(defaultExceptionHandler);
    }

    public static boolean isEnableGzipRequest() {
        return isEnableGzipRequest;
    }

    public static void isEnableGzipRequest(boolean isEnable) {
        isEnableGzipRequest = isEnable;
    }
}
