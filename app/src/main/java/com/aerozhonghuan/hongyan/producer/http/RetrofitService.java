package com.aerozhonghuan.hongyan.producer.http;

import com.aerozhonghuan.hongyan.producer.modules.user.beans.ResWanShanInfo;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author: drs
 * @time: 2018/1/17 14:17
 * @des:
 */
public interface RetrofitService {
    @POST("tocapp/modifyUserInfnOfHeavy")
//        Observable<ResWanShanInfo> postWanShaninfo(@Field("name") String name, @Field("pwd") String pwd,@Field("token") String token);
        Observable<ResWanShanInfo> postWanShaninfo(@Body Map<String, String> options);
}
