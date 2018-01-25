package com.aerozhonghuan.hongyan.producer.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hongyan.producer.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 环境信息 打印
 * Created by Administrator on 2016/9/20 0020.
 */
public class EnvironmentInfoUtils {
    private final String TAG = "EnvironmentInfoUtils";
    private NetworkInfo networkInfo;
    private TelephonyManager telephonyManager;

    /**
     * 获得基本信息
     *
     * @param context
     * @return
     */
    public HashMap<String, String> getBasicMsg(Context context) {
        if (context == null)
            throw new NullPointerException();
        HashMap<String, String> environmentMsgMap = new LinkedHashMap<>();
        putScreenMsg(environmentMsgMap, context);
        putMemoryMsg(environmentMsgMap, context);
        putExternalMemoryMsg(environmentMsgMap, context);
        putReaviewMsg(environmentMsgMap, context);
        putAndroidMsg(environmentMsgMap, context);
        putAppMsg(environmentMsgMap, context);
        putUrlsMsg(environmentMsgMap, context);
        putOperatorName(environmentMsgMap, context);
        putNetworkMsg(environmentMsgMap, context);
        return environmentMsgMap;
    }

    /**
     * 打印
     */
    public void print(Activity context) {
        if (context == null)
            throw new NullPointerException();
        HashMap<String, String> basicMsg = getBasicMsg(context);
        Set<String> keys = basicMsg.keySet();
        LogUtil.d(TAG, "##############################");
        for (String key : keys) {
            LogUtil.d(TAG, String.format("## %s: %s", key, basicMsg.get(key)));
        }
        LogUtil.d(TAG, "##############################");
    }

    public void print(Context context, StringBuilder stringBuilder) {
        if (context == null)
            throw new NullPointerException();
        HashMap<String, String> basicMsg = getBasicMsg(context);
        Set<String> keys = basicMsg.keySet();
        stringBuilder.append("##############################").append("\r\n");
        for (String key : keys) {
            stringBuilder.append(String.format("## %s: %s", key, basicMsg.get(key))).append("\r\n");
        }
        stringBuilder.append("##############################").append("\r\n");
    }

    /**
     * 获取屏幕分辨率和密度
     */
    private void putScreenMsg(HashMap<String, String> environmentMsgMap, Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;
            int height = dm.heightPixels;
            int dpi = dm.densityDpi;// 屏幕密度dpi（120 / 160 / 240）
            float density = dm.density;// 屏幕密度（0.75 / 1.0 / 1.5）
            int screenWidth = (int) (width / density);//屏幕宽度(dp)
            int screenHeight = (int) (height / density);//屏幕高度(dp)
            environmentMsgMap.put("屏幕宽度", String.valueOf(width));
            environmentMsgMap.put("屏幕高度", String.valueOf(height));
            environmentMsgMap.put("屏幕DPI", String.valueOf(dpi));
            environmentMsgMap.put("屏幕密度", String.valueOf(density));
            environmentMsgMap.put("屏幕高度dp", String.valueOf(screenHeight));
            environmentMsgMap.put("屏幕宽度dp", String.valueOf(screenWidth));
        }
    }

    /**
     * 获取运行内存情况
     */
    private void putMemoryMsg(HashMap<String, String> environmentMsgMap, Context context) {
        //获取总运行内存
        // /proc/meminfo读出的内核信息进行解释
        String path = "/proc/meminfo";
        String content = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path), 8);
            String line;
            if ((line = br.readLine()) != null) {
                content = line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // beginIndex
        int begin = content.indexOf(':');
        // endIndex
        int end = content.indexOf('k');
        // 截取字符串信息
        content = content.substring(begin + 1, end).trim();
        long totalMem = Long.parseLong(content) * 1024;
        environmentMsgMap.put("内存总大小", Formatter.formatFileSize(context, totalMem));

        //获取可用内存
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        environmentMsgMap.put("内存可用大小", Formatter.formatFileSize(context, memoryInfo.availMem));
    }

    /**
     * 获取储存情况
     */
    private void putExternalMemoryMsg(HashMap<String, String> environmentMsgMap, Context context) {
        File path = Environment.getExternalStorageDirectory(); //获取SDCard根目录
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long availableBlocks = stat.getAvailableBlocks();
        environmentMsgMap.put("储存总大小", Formatter.formatFileSize(context, totalBlocks * blockSize));
        environmentMsgMap.put("储存可用大小", Formatter.formatFileSize(context, availableBlocks * blockSize));
    }

    /**
     * 获取固件信息
     */
    private void putReaviewMsg(HashMap<String, String> environmentMsgMap, Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            environmentMsgMap.put("手机IMEI", imei);

            //获取手机型号
            String model = Build.MODEL;
            environmentMsgMap.put("手机型号", model);
            //获取手机品牌
            String brand = Build.BRAND;
            environmentMsgMap.put("手机品牌", brand);
        } catch (SecurityException ex) {
            ex.printStackTrace();
            LogUtil.d(TAG, "获得权限失败," + ex.getMessage());
        }
    }

    /**
     * 获取Android信息
     */
    private void putAndroidMsg(HashMap<String, String> environmentMsgMap, Context context) {
        String release = Build.VERSION.RELEASE;
        environmentMsgMap.put("Android系统版本", release);
    }

    /**
     * 获取app信息
     */
    private void putAppMsg(HashMap<String, String> environmentMsgMap, Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            environmentMsgMap.put("当前App版本号", versionCode + "");
            environmentMsgMap.put("当前App版本名称", versionName);
            environmentMsgMap.put("当前App build 版本名称", AppUtil.getAppBuildVersion(context));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取URL信息
     */
    private void putUrlsMsg(HashMap<String, String> environmentMsgMap, Context context) {
//        environmentMsgMap.put("权限接口地址", Urls.PERMISSION_BASE_URL);
//        environmentMsgMap.put("微信服务地址", Urls.WEIXIN_BASE_URL);
//        environmentMsgMap.put("OTA基础地址", Urls.OTA_BASE_URL);
        environmentMsgMap.put("url基础地址", BuildConfig.HOST_BUSINESS);
    }

    /**
     * 运营商
     */
    private void putOperatorName(HashMap<String, String> environmentMsgMap, Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telephonyManager.getSimOperator();
        String str;
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                str = "中国移动";
            } else if (operator.equals("46001")) {
                str = "中国联通";
            } else if (operator.equals("46003")) {
                str = "中国电信";
            } else {
                str = "无记录";
            }
        } else {
            str = "无记录";
        }
        environmentMsgMap.put("网络运营商", str);
    }

    /**
     * 获取网络信息
     */
    private void putNetworkMsg(HashMap<String, String> environmentMsgMap, Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        String strNetworkType = "";
        if (networkInfo != null && networkInfo.isAvailable()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
            }
        } else {
            strNetworkType = "无网络";
        }
        environmentMsgMap.put("当前网络", strNetworkType);
    }
}
