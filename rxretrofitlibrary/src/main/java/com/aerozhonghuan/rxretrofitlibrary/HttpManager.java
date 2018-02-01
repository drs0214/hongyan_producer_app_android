package com.aerozhonghuan.rxretrofitlibrary;

import com.aerozhonghuan.rxretrofitlibrary.log.LoggerUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
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
    private static int DEFAULT_TIME_OUT = 10000;//超时时间 5s
    private static Interceptor sMyRequestParaInterceptor;
    private static Interceptor sMyHeaderParamInterceptor;

    private Retrofit mRetrofit;

    //构造方法私有
    private HttpManager() {
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(getHttpBuilder().build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpConfig.getBaseUrl())
                .build();
    }

    private OkHttpClient.Builder getHttpBuilder(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        LoggerUtil.d("httpmanager中超时时长::"+DEFAULT_TIME_OUT);
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS);//连接超时时间        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_TIME_OUT,TimeUnit.MILLISECONDS);//读操作超时时间
        // 添加公共参数拦截器
        if (sMyHeaderParamInterceptor != null) {
            builder.addInterceptor(sMyHeaderParamInterceptor);
        }
        if (sMyRequestParaInterceptor != null) {
            builder.addInterceptor(sMyRequestParaInterceptor);
        }
        // 添加日志拦截器
        if (HttpConfig.isDebug()) {
            builder.addInterceptor(new LogInterceptor());
        }
        return builder;
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
    public static void setRequestParaInterceptor(Interceptor requestParaInterceptor) {
        sMyRequestParaInterceptor = requestParaInterceptor;
    }

    /**
     * 请求头拦截器
     *
     * @param headerParamInterceptor requestParaInterceptor
     */
    public static void setHeaderParamInterceptor(Interceptor headerParamInterceptor) {
        sMyHeaderParamInterceptor = headerParamInterceptor;
    }

    public void setTimeOut(int interval) {
        DEFAULT_TIME_OUT = interval;
        mRetrofit = new Retrofit.Builder()
                .client(getHttpBuilder().build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HttpConfig.getBaseUrl())
                .build();
    }
}
