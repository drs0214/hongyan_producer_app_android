package com.aerozhonghuan.hongyan.producer.framework.base;

import android.support.multidex.MultiDexApplication;

import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.foundation.umeng.ShareHelper;
import com.aerozhonghuan.foundation.umeng.UmengAgent;
import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.framework.logback.LogConfigurator;
import com.aerozhonghuan.hongyan.producer.framework.logback.LogbackAppender;
import com.aerozhonghuan.hongyan.producer.utils.ProcessUtil;
import com.aerozhonghuan.rxretrofitlibrary.HttpConfig;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.socialize.PlatformConfig;

/**
 * MyApplication
 * Created by zhangyunfei on 17/6/19.
 */

public class MyApplication extends MultiDexApplication {
    private static final String TAG = "MyApplication";
    private static MyApplication instance;

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

}
