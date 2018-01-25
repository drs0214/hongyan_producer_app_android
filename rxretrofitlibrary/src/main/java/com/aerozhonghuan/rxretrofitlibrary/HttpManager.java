package com.aerozhonghuan.rxretrofitlibrary;

import com.aerozhonghuan.rxretrofitlibrary.HeaderParamInterceptor;
import com.aerozhonghuan.rxretrofitlibrary.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.®
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static RequestParaInterceptor sMyRequestParaInterceptor;
    private static HeaderParamInterceptor sMyHeaderParamInterceptor;
    private static DefaultExceptionHandler sDefaultExceptionHandler;
    private Retrofit mRetrofit;

    //构造方法私有
    private HttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间
        // 添加公共参数拦截器
        if (sMyHeaderParamInterceptor != null) {
            builder.addInterceptor(sMyRequestParaInterceptor);
        }
        if (sMyRequestParaInterceptor != null) {
            builder.addInterceptor(sMyHeaderParamInterceptor);
        }
        // 添加日志拦截器
        if (HttpConfig.isDebug()) {
            builder.addInterceptor(new LogInterceptor());
        }
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpConfig.getBaseUrl())
                .build();
    }

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    /**
     * 参数拦截器
     *
     * @param requestParaInterceptor requestParaInterceptor
     */
    public static void setRequestParaInterceptor(RequestParaInterceptor requestParaInterceptor) {
        sMyRequestParaInterceptor = requestParaInterceptor;
    }

    /**
     * 请求头拦截器
     *
     * @param headerParamInterceptor requestParaInterceptor
     */
    public static void setHeaderParamInterceptor(HeaderParamInterceptor headerParamInterceptor) {
        sMyHeaderParamInterceptor = headerParamInterceptor;
    }

    /**
     * 默认异常处理器
     *
     * @param defaultExceptionHandler defaultExceptionHandler
     */
    public static void setDefaultExceptionHandler(DefaultExceptionHandler defaultExceptionHandler) {
        sDefaultExceptionHandler = defaultExceptionHandler;
    }
}
