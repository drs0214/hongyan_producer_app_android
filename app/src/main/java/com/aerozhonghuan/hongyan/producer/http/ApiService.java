package com.aerozhonghuan.hongyan.producer.http;

import com.aerozhonghuan.hongyan.producer.modules.check.entity.CarInfo;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CheckStatusBean;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.InspectioniHistory;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.StartCheckStateBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.SessionBean;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.AppInfo;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.PhoneInfo;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.Transport_Scan_OrderBean;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
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
    Observable<ResponseBody> uploadPhoneInfo(@Body PhoneInfo info);

    /**
     * 获取最新版本信息
     * @return
     */
    @GET("app/version/v1/scts/last")
    Observable<AppInfo> getAppInfo();


    /**
     * 获取车辆信息
     * @return
     */
    @GET("vehicle/inspection/v1/get")
    Observable<CarInfo> getCarInfo(@Query("vhcle") String vhcle);


    /**
     * 获取车辆信息
     * @return
     */
    @GET("vehicle/inspection/v1/history")
    Observable<InspectioniHistory> getInspectioniHistory(@Query("vhcle") String vhcle);

    /**
     * 开始检测,如果返回成功进入检测页面
     * @param vhcle
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("vehicle/inspection/v1/start")
    Observable<StartCheckStateBean> startCheck(@Field("vhcle") String vhcle, @Field("type") String type);


    @GET("vehicle/inspection/v1/lastStatus")
    Observable<CheckStatusBean> getLastStatus(@QueryMap Map<String, String> map);

    /**
     * 获得车辆信息与操作按钮
     * @return
     */
    @GET("delivery/v1/getVehicleAndActions")
    Observable<TransportScanDetailBean> getVehicleAndActions(@Query("vhcle") String vhcle);
    /**
     * 获得车辆信息与操作按钮
     * @return
     */
    @GET("delivery/v1/transportOrders")
    Observable<List<Transport_Scan_OrderBean>>transportOrders(@Query("vhcle") String vhcle);
    /**
     * 获得用户已授权的动作列表
     * @return
     */
    @GET("delivery/v1/actions")
    Observable<List<TransportScanDetailBean.ActionsBean>> actions();

}
