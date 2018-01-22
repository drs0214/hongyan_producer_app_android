package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.R;

/**
 * app 的一些基础操作方法
 * Created by zhangyunfei on 17/6/24.
 */

public class AppUtil {

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 获得 app 版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            // int versionCode = packageInfo.versionCode;
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得 app 的 编译版本号 全名称
     *
     * @param context
     * @return
     */
    public static String getAppBuildVersion(Context context) {
        String appVersionName = getAppVersionName(context);
        if (BuildConfig.DEBUG) {
            appVersionName = String.format("%s build %s", appVersionName, AppUtil.getBuildNumber());
        }
        return appVersionName;
    }

    /**
     * 获得编译编号 number
     *
     * @return
     */
    public static int getBuildNumber() {
        return BuildConfig.BUILD_NUMBER;
    }

    /**
     * 获得渠道
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        return getAppMetaDataByKey(context, "UMENG_CHANNEL");
    }


    private static String getAppMetaDataByKey(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            Object val = ai.metaData.get(key);
            if (val instanceof Integer) {
                long longValue = ((Integer) val).longValue();
                String value = String.valueOf(longValue);
                return value;
            } else if (val instanceof String) {
                String value = String.valueOf(val);
                return value;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getAppVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getAppName(Context context) {
        return context.getResources().getString(R.string.app_name);
    }
}
