package com.aerozhonghuan.hongyan.producer.framework.versionUpdate;

import android.os.Build;

import com.aerozhonghuan.hongyan.producer.utils.SimpleSettings;

/**
 * Created by zhangyh on 2016/12/2 0031.
 */

public class UpdateAPPConstants {
    //http://wdservice.mapbar.com/appstorewsapi/checkexistlist/23?package_name=com.mapbar.obd&ck=a7dc3b0377b14a6cb96ed3d18b5ed117
    public static final String APPUPDATE_URL = "http://wdservice.mapbar.com/appstorewsapi/checkexistlist/" + Build.VERSION.SDK_INT;
    public static final String UPDATE_FOLDER = SimpleSettings.getCacheDir().getPath() + "/apk";
    public static final int UPDATE_NAV = 102;
    public static final int UPDATE_APP = 101;

    public static int file_flag;
}
