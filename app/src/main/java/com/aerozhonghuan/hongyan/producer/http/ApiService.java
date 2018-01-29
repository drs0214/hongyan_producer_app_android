package com.aerozhonghuan.hongyan.producer.http;

import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.SessionBean;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.AppInfo;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.PhoneInfo;

import retrofit2.http.Body;
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
    /**
     * 登录接口
     * 注意:post请求需添加@FormUrlEncoded
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("auth/v1/session")
    Observable<SessionBean> getSession(@Field("username") String username, @Field("password") String password);

    /**
     * 获取用户权限
     * @return
     */
    @GET("auth/v1/authorization")
    Observable<PermissionsBean> getAuthorization();

    /**
     * 上传手机信息
     * 注意:使用@Body不能添加@FormUrlEncoded
     * @param info
     * @return
     */
    @POST("app/mobileInfo/v1/upload")
    Observable<String> uploadPhoneInfo(@Body PhoneInfo info);

    /**
     * 获取最新版本信息
     * @return
     */
    @GET("app/version/v1/scts/last")
    Observable<AppInfo> getAppInfo();
}
