package com.aerozhonghuan.hongyan.producer.framework.base;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.aerozhonghuan.hongyan.producer.utils.AppUtil;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.oknet2.RequestParaInterceptor;

import java.io.File;
import java.util.Map;

/**
 * 请求参数 拦截器
 * Created by zhangyunfei on 17/6/19.
 */

public class CustomRequestParaInterceptor implements RequestParaInterceptor {
    private static final String TAG = "RequestParaInterceptor";

    /**
     * @param injectPara 参数 有可能为空
     * @param headers
     */
    @Override
    public void onProcessCommonRequestPara(String url, @Nullable Map<String, String> injectPara, Map<String, String> headers) {
        LogUtil.i(TAG, "## 进入公共方法拦截器 onProcessCommonRequestPara");

        if (isUserCenterHost(url) && headers != null) {
            headers.put("device-id", AppUtil.getDeviceId(MyAppliation.getApplication()));
            headers.put("device-type", "android");
            headers.put("app-version", AppUtil.getAppVersionName(MyAppliation.getApplication()));
        }

        //为业务服务器地址,附加特殊参数
        if (isBusinessHost(url) && injectPara != null) {
            //对于已登录用户，每个带参数的http请求附加token参数
            if (MyAppliation.getApplication().getUserInfo() != null) {
                String token = MyAppliation.getApplication().getUserInfo().getToken();
                injectPara.put("token", token);
            }
            injectPara.put("deviceType", DeviceType.DEVICE_ANDROID);
        }
    }

    @Override
    public void onProcessUploadFileRequestPara(String url, @Nullable Map<String, String> paras, Map<String, File> fileParas, Map<String, String> headers) {
        LogUtil.i(TAG, "## 进入公共方法拦截器 onProcessUploadFileRequestPara");
    }

    /**
     * 判断url是否是访问 用户中心服务器
     *
     * @param url
     * @return
     */
    private boolean isUserCenterHost(String url) {
        return !TextUtils.isEmpty(url) && url.startsWith(URLs.HOST_USER_CENTER);
    }

    /**
     * 判断url是否是访问 业务服务器
     *
     * @param url
     * @return
     */
    private boolean isBusinessHost(String url) {
        return !TextUtils.isEmpty(url) && url.startsWith(URLs.HOST_BUSINESS);
    }
}
