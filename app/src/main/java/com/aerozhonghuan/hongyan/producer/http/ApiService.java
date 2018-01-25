package com.aerozhonghuan.hongyan.producer.http;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhangyonghui on 2018/1/24.
 * 公用接口类
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("auth/v1/session")
    Observable<String> getSession(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("app/mobileInfo/v1/upload")
    Observable<String> getGank(@Field("imei") String imei, @Field("imsi") String imsi, @Field("mobileNumber") String mobileNumber, @Field("operator") String operator, @Field("mobileType") String mobileType, @Field("androidVersion") String androidVersion, @Field("sdkVersion") String sdkVersion, @Field("appVersion") String appVersion, @Field("username") String username, @Field("oem") String oem, @Field("appName") String appName);


}
