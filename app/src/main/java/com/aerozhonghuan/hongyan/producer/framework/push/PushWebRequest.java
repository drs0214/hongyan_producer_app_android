package com.aerozhonghuan.hongyan.producer.framework.push;

import android.content.Context;

import com.aerozhonghuan.hongyan.producer.framework.base.URLs;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.OkNetCall;
import com.aerozhonghuan.oknet2.RequestBuilder;

/**
 * 推送相关的网络请求
 * Created by zhangyunfei on 17/7/5.
 */

public class PushWebRequest {
    private static final String TAG = "PushWebRequest";

    /**
     * 向服务端上报 push token
     *  @param context
     * @param pushToken
     * @param callback
     */
    public static OkNetCall bindPushToken(Context context, String pushToken, CommonCallback<Object> callback) {

        return RequestBuilder.with(context).URL(URLs.PUSH_BIND_TOKEN)
                .para("appType", "2")
                .para("pushToken", pushToken)
                .onSuccess(callback).excute();
    }
}
