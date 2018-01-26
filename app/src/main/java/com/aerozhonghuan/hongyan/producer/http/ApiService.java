package com.aerozhonghuan.hongyan.producer.http;

import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.SessionBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhangyonghui on 2018/1/24.
 * 公用接口类
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("auth/v1/session")
    Observable<SessionBean> getSession(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("app/mobileInfo/v1/upload")
    Observable<String> getGank(@Field("imei") String imei, @Field("imsi") String imsi, @Field("mobileNumber") String mobileNumber, @Field("operator") String operator, @Field("mobileType") String mobileType, @Field("androidVersion") String androidVersion, @Field("sdkVersion") String sdkVersion, @Field("appVersion") String appVersion, @Field("username") String username, @Field("oem") String oem, @Field("appName") String appName);

    @GET("auth/v1/authorization")
    Observable<PermissionsBean> getAuthorization();
}
