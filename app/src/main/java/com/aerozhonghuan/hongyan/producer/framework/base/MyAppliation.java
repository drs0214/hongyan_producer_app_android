package com.aerozhonghuan.hongyan.producer.framework.base;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.dao.DaoContext;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.foundation.umeng.ShareHelper;
import com.aerozhonghuan.foundation.umeng.UmengAgent;
import com.aerozhonghuan.hongyan.producer.framework.hybrid.HybridHttpProxy;
import com.aerozhonghuan.hongyan.producer.framework.logback.LogConfigurator;
import com.aerozhonghuan.hongyan.producer.framework.logback.LogbackAppender;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserInfo;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.utils.ProcessUtil;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hybrid.HybridContext;
import com.aerozhonghuan.oknet2.LogInterceptor;
import com.aerozhonghuan.oknet2.OknetConfig;
import com.umeng.socialize.PlatformConfig;

/**
 * MyAppliation
 * Created by zhangyunfei on 17/6/19.
 */

public class MyAppliation extends MultiDexApplication {
    private static final String TAG = "MyAppliation";
    private static MyAppliation instance;

    public static MyAppliation getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initGlobalSettings();

        LogUtil.d(TAG, "初始化云信 完成");

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
        OknetConfig.DEBUG = BuildConfig.DEBUG;
        EventBusManager.DEBUG = false;
        initLogback();//初始化日志框架

        initUmengStatistic();
        LogUtil.d(TAG, "初始化uemng统计框架 完成");

        initOknet();//初始化网络
        LogUtil.d(TAG, "初始化网络框架 完成");

        initDatabase();//初始化数据库
        LogUtil.d(TAG, "初始化DAO框架 完成");

        LogUtil.d(TAG, "初始化 Hybrid HTTP 代理对象 完成");
        HybridContext.setHttpProxy(new HybridHttpProxy());

        initUmengShare();
        LogUtil.d(TAG, "初始化 分享 完成");

        //LeakCanary
//        LeakCanaryUtil.install(this);//安装内存泄漏检测工具

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

    private void initDatabase() {
        DaoContext.onApplicationStart(this);
    }

    private void initOknet() {
        //配置okhttp 缓存位置
        OknetConfig.setExternalCacheDir(getExternalCacheDir());
        //OknetConfig.setRequestParaInterceptor(new CustomRequestParaInterceptor1());
        OknetConfig.setRequestParaInterceptor(new CustomRequestParaInterceptor());
        OknetConfig.setDefaultExceptionHandler(new CustomDefalutExceptionHandler());
        OknetConfig.setLogInterceptor(new LogInterceptor() {
            @Override
            public void d(String tag, String msg) {
                Log.i(tag, msg);
            }

            @Override
            public void i(String tag, String msg) {
                Log.d(tag, msg);
            }

            @Override
            public void e(String tag, String msg) {
                Log.e(tag, msg);
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 获得当前用户
     *
     * @return
     */
    public UserInfo getUserInfo() {
        return UserInfoManager.getCurrentUserBaseInfo();
    }

}
