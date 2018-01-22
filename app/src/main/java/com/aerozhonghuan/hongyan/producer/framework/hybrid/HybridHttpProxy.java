package com.aerozhonghuan.hongyan.producer.framework.hybrid;

import android.text.TextUtils;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hybrid.net.HttpCallback;
import com.aerozhonghuan.hybrid.net.IHttpProxy;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.CommonMessage;
import com.aerozhonghuan.oknet2.HttpGetUtil;

import java.util.HashMap;

/**
 * Created by zhangyunfei on 17/7/12.
 */

public class HybridHttpProxy implements IHttpProxy {
    public static final String TAG = "HybridHttpProxy";

    @Override
    public void sendRequest(String url, HashMap<String, String> para, final HttpCallback httpCallback) {
        if (TextUtils.isEmpty(url)) return;
        HttpGetUtil.get(url, new CommonCallback<String>() {

            @Override
            public void onSuccess(String jsonResult, CommonMessage commonMessage, String allResponseString) {
                LogUtil.d(TAG, "#onSuccess " + jsonResult);
                if (httpCallback != null)
                    httpCallback.onSuccess(jsonResult);
            }

            @Override
            public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                LogUtil.d(TAG, "#onFailure " + exception);
                if (httpCallback != null)
                    httpCallback.onFailure(httpCode, exception == null ? "网络请求异常" + httpCode : exception.getMessage());
                return true;
            }
        });
    }

}
