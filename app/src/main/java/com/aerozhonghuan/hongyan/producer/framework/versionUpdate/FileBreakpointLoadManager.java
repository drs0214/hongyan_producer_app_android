package com.aerozhonghuan.hongyan.producer.framework.versionUpdate;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.framework.base.URLs;
import com.aerozhonghuan.hongyan.producer.utils.SimpleSettings;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.CommonMessage;
import com.aerozhonghuan.oknet2.RequestBuilder;
import com.aerozhonghuan.oknet2.breakpoint.FileBreakpointLoader;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by zhangyh on 2016/12/2 0031.
 * 断点续传管理类
 */

public class FileBreakpointLoadManager {
    private static final String TAG = "UpdateApp";
    private Context context;
    private AppInfo info;

    public FileBreakpointLoadManager(Context context) {
        this.context = context;
    }

    /**
     * 开始升级
     *
     * @param info
     */
    public void startUpdate(AppInfo info) {
        this.info = info;
        downloadAPP(info);
    }

    /**
     * 跳转到BreakpointService中进行后台下载
     *
     * @param appInfo
     */
    private void downloadAPP(AppInfo appInfo) {
        LogUtil.d(TAG, "开启BreakpointLoadService");
        Intent updateIntent = new Intent(context, BreakpointLoadService.class);
        updateIntent.putExtra("app_info", appInfo);
        context.startService(updateIntent);
    }

    /**
     * 检测APP版本,需要一个监听,如果检测到需要版本升级,则触发监听
     *
     * @param onCheckAppVersionLinstener
     */
    public void checkAppVersion(final OnCheckAppVersionLinstener onCheckAppVersionLinstener) {
        LogUtil.d(TAG, "开始查询最新信息");
        TypeToken<List<AppInfo>> typeToken = new TypeToken<List<AppInfo>>() {};
        RequestBuilder.with(context)
                .URL(String.format("%s?package_name=%s&ck=%s", UpdateAPPConstants.APPUPDATE_URL, context.getPackageName(), BuildConfig.WEDRIVE_CK))
                .useGetMethod()
                .onSuccess(new CommonCallback<List<AppInfo>>(typeToken) {
                    @Override
                    public void onSuccess(List<AppInfo> messsageBodyObject, CommonMessage commonMessage, String allResponseString) {
                        AppInfo appInfo = messsageBodyObject.get(0);
                        LogUtil.d(TAG, appInfo.toString());
                        if (appInfo != null && hasNewAppVersion(appInfo, context)) {
                            onCheckAppVersionLinstener.prepareUpdate(appInfo);
                        } else {
                            SimpleSettings.clearApkDir();
                            onCheckAppVersionLinstener.noNewVersions();
                        }
                    }

                    @Override
                    public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                        onCheckAppVersionLinstener.error();
                        return true;
                    }
                }).excute();
    }


    /**
     * 货车导航下载
     */
    public void uploadNavitruck(final OnCheckAppVersionLinstener onCheckAppVersionLinstener) {
        LogUtil.d(TAG, "开始查询最新信息");
        TypeToken<List<AppInfo>> typeToken = new TypeToken<List<AppInfo>>() {
        };
        RequestBuilder.with(context)
                .URL(URLs.NAVITRUCK_UPLOAD)
                .useGetMethod()
                .onSuccess(new CommonCallback<List<AppInfo>>(typeToken) {
                    @Override
                    public void onSuccess(List<AppInfo> messsageBodyObject, CommonMessage commonMessage, String allResponseString) {
                        AppInfo appInfo = messsageBodyObject.get(0);
                        LogUtil.d(TAG, appInfo.toString());
                        if (appInfo != null) {
                            onCheckAppVersionLinstener.prepareUpdate(appInfo);
                        } else {
                            onCheckAppVersionLinstener.noNewVersions();
                        }
                    }

                    @Override
                    public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                        onCheckAppVersionLinstener.error();
                        return true;
                    }
                }).excute();
    }

    /**
     * 检测是否是新版本
     *
     * @param info
     * @param context
     * @return
     */
    private boolean hasNewAppVersion(AppInfo info, Context context) {
        int versionCode = -1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (Exception e) {
        }
        return (info.getVersion_no() > versionCode && versionCode != -1) ? true : false;
    }

    public void continueUpdate(Context context) {
        FileBreakpointLoader.continueLoad(context);
    }


    public interface OnCheckAppVersionLinstener {
        void prepareUpdate(AppInfo info);

        void noNewVersions();

        void error();
    }
}
