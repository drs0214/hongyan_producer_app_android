package com.aerozhonghuan.hongyan.producer.framework.base;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.foundation.umeng.ShareHelper;
import com.aerozhonghuan.foundation.umeng.UmengAgent;
import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.framework.logback.LogConfigurator;
import com.aerozhonghuan.hongyan.producer.framework.logback.LogbackAppender;
import com.aerozhonghuan.hongyan.producer.utils.ProcessUtil;
import com.aerozhonghuan.rxretrofitlibrary.HttpConfig;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.PlatformConfig;
import com.zh.location.drs.amaplibrary.Utils;

/**
 * MyApplication
 * Created by zhangyunfei on 17/6/19.
 */

public class MyApplication extends MultiDexApplication {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private static final String TAG = "MyApplication";
    private static MyApplication instance;
    public static AMapLocation mlocation;
    public static MyApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGlobalSettings();
        //默认异常处理
        CrashExceptionHandler.setDefaultUncaughtExceptionHandler(this);
        //开发阶段的特殊配置
        justRunOnDevelop();

        initLocation();
        locationClient.startLocation();
    }

    /**
     * 开发阶段的特殊配置
     * 强制关闭 某些 外部sdk注册的 UncaughtExceptionHandler
     */
    private void justRunOnDevelop() {
        //如果app编译为开发者模式，则关闭umeng统计，开启umeng debug
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            LogUtil.d(TAG, "umeng统计，关闭捕获异常");
            UmengAgent.setCatchUncaughtExceptions(false);
            UmengAgent.setDebugMode(true);

            //阻止友盟捕获异常导致app重启
            Thread.setDefaultUncaughtExceptionHandler(null);
            Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler != null) {
                String str = defaultUncaughtExceptionHandler.getClass().toString();
                LogUtil.d(TAG, "发现某些 外部sdk注册的UncaughtExceptionHandler： " + str);
            }
        }
    }


    /**
     * 初始全局设定,针对模块的设定不应该写在这里
     * 在单个进程仅初始化一次。防止 多进程应用时，this application 创建多次
     */
    private void initGlobalSettings() {
        String processName = ProcessUtil.getProcessName(android.os.Process.myPid());
        if (!getPackageName().equals(processName)) {//检查当前是否是同包名相同的进程，如果是，则为主进程，执行设定
            return;
        }
        LogUtil.d(TAG, "################################");
        LogUtil.d(TAG, "程序启动");
        //日志基础功能优先处理
        LogUtil.isEnable = BuildConfig.DEBUG;
        EventBusManager.DEBUG = false;
        initLogback();//初始化日志框架
        LeakCanary.install(this);
        initUmengStatistic();
        LogUtil.d(TAG, "初始化uemng统计框架 完成");

        initHttp();
        LogUtil.d(TAG, "初始化http框架 完成");

       /* LogUtil.d(TAG, "初始化 Hybrid HTTP 代理对象 完成");
        HybridContext.setHttpProxy(new HybridHttpProxy());*/

        initUmengShare();
        LogUtil.d(TAG, "初始化 分享 完成");

    }

    private void initHttp() {
        HttpConfig.init(this, BuildConfig.DEBUG, BuildConfig.HOST_BUSINESS, HttpConfig.USEJSON);
        HttpConfig.setRequestParaInterceptor( new RequestParaInterceptor.Builder().build());
        HttpConfig.setHeaderParamInterceptor(new HeaderParamInterceptor.Builder().build());
    }

    private void initUmengShare() {
        PlatformConfig.setWeixin("wxd97c9f2e015892f3", "52b376899aaf4714a4e40e902a1f5aa5");
        ShareHelper.setDebug(false);
        ShareHelper.initContext(this);
    }

    private void initUmengStatistic() {
        UmengAgent.init();
        //如果app编译为开发者模式，则关闭umeng统计，开启umeng debug
        if (BuildConfig.DEBUG) {
            LogUtil.d(TAG, "umeng统计，关闭捕获异常");
            UmengAgent.setDebugMode(true);
        }
    }

    private void initLogback() {
        if (LogUtil.isEnable) {
            LogConfigurator.confifure();
            LogUtil.getAppenderGroup().clear();
            LogUtil.getAppenderGroup().addChildLogger(new LogbackAppender());
            LogUtil.d(TAG, "初始化日志框架 完成");
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

        locationClient.startLocation();
    }
    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        locationOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        locationOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        locationOption.setInterval(5000);//可选，设置定位间隔。默认为2秒
        locationOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        locationOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        locationOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        locationOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        locationOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        return locationOption;
    }
    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                mlocation=location;
                if(location.getErrorCode() == 0) {
                    Log.e("drs","省份===="+location.getProvince());
                    Log.e("drs","城市===="+location.getCity());
                    Log.e("drs","区县===="+location.getDistrict());
                    Log.e("drs","街道地址或者详细地址===="+location.getAddress());
                    Log.e("drs","经度===="+location.getLongitude());
                    Log.e("drs","纬度===="+location.getLatitude());
                    Log.e("drs","定位时间===="+ Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss"));
                    if (location.getLocationType()==1){
                        Log.e("drs","定位方式====GPS");
                    }else if (location.getLocationType()==5){
                        Log.e("drs","定位方式====Wifi");
                    }else if (location.getLocationType()==6){
                        Log.e("drs","定位方式====基站");
                    }else{
                        Log.e("drs","定位方式====前次离线缓存");
                    }
                    Log.e("drs","定位精准度===="+location.getAccuracy()+"米");

                }
            }
        }
    };

}
