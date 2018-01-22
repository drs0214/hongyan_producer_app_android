package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.content.Context;

import com.aerozhonghuan.hongyan.producer.framework.base.URLs;
import com.aerozhonghuan.hongyan.producer.modules.common.event.UploadFileBean;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.RequestBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/7/19 0019 on 下午 2:53
 */

public class UploadFileWebRequest {
    /**
     * 同步上传文件
     *
     * @param context
     * @param file
     * @param callback
     */
    public static void syncUpLoadFile(Context context, File file, CommonCallback<UploadFileBean> callback) {
        TypeToken<UploadFileBean> type = new TypeToken<UploadFileBean>() {
        };
        if (callback != null) callback.setResponseType(type);
        RequestBuilder.with(context)
                .URL(URLs.SUBSCRIBE_POSTFILE)
                .useSynchronous()
                .file("file", file)
                .onSuccess(callback)
                .excute();
    }

    /**
     * 异步上传文件
     *
     * @param context
     * @param file
     * @param callback
     */
    public static void asyncUpLoadFile(Context context, File file, CommonCallback<UploadFileBean> callback) {
        TypeToken<UploadFileBean> type = new TypeToken<UploadFileBean>() {
        };
        if (callback != null) callback.setResponseType(type);
        RequestBuilder.with(context)
                .URL(URLs.SUBSCRIBE_POSTFILE)
                .file("file", file)
                .onSuccess(callback)
                .excute();
    }
}
