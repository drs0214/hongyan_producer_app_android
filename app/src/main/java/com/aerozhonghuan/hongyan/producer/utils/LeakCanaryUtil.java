//package com.aerozhonghuan.driverapp.utils;
//
//import android.app.Application;
//
//import com.aerozhonghuan.driverapp.BuildConfig;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
//
///**
// * LeakCanary 检测内存泄漏
// * Created by zhangyunfei on 2017/7/28.
// */
//
//public class LeakCanaryUtil {
//    private static RefWatcher mRefWatcher;
//
//    private LeakCanaryUtil() {
//    }
//
//    public static void install(Application application) {
//        mRefWatcher = "debug".equals(BuildConfig.BUILD_TYPE) ? LeakCanary.install(application) : RefWatcher.DISABLED;
//    }
//
//    public static RefWatcher getRefWatcher() {
//        return mRefWatcher;
//    }
//}
