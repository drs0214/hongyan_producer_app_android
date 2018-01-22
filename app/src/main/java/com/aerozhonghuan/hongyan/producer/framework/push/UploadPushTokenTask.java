package com.aerozhonghuan.hongyan.producer.framework.push;

import android.os.Handler;

import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.CommonMessage;
import com.aerozhonghuan.oknet2.OkNetCall;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangyunfei on 2017/8/4.
 */

public class UploadPushTokenTask {
    private static final String TAG = "UploadPushTokenTask";
    private static final int MAX_RETRY = 3;//最大重试次数
    private static final long RETRY_INTEVAL = 5000;

    private Handler handler;
    private OkNetCall okNetCall;
    private AtomicInteger mAtomicInteger;
    private static UploadPushTokenTask instance;

    private UploadPushTokenTask() {
        handler = new Handler();
    }

    public static UploadPushTokenTask getInstance() {
        if (instance == null)
            instance = new UploadPushTokenTask();
        return instance;
    }


    /**
     * 发送 push token给服务端，会尝试3次
     */
    public void sendPushTokenToServer(String pushToken) {
        LogUtil.d(TAG, "#sendPushTokenToServer");
        if (mAtomicInteger != null) return;
        //说明 未执行任务
        mAtomicInteger = new AtomicInteger(MAX_RETRY);
        requestPushTokenToServer(pushToken);
    }

    private void requestPushTokenToServer(final String pushToken) {
        if (okNetCall != null) {
            okNetCall.cancel();
        }
        LogUtil.d(TAG, "准备上报 push token 剩余尝试次数" + mAtomicInteger.get());
        okNetCall = PushWebRequest.bindPushToken(MyAppliation.getApplication(), pushToken, new CommonCallback<Object>() {
            @Override
            public void onSuccess(Object messsageBodyObject, CommonMessage commonMessage, String allResponseString) {
                LogUtil.d(TAG, "上报 push token 成功");
                if (mAtomicInteger != null) {
                    mAtomicInteger.set(0);
                    mAtomicInteger = null;
                }
            }

            @Override
            public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                LogUtil.d(TAG, "上报 push token 失败");
                if (mAtomicInteger == null) return true;
                if (mAtomicInteger.get() > 0) {
                    mAtomicInteger.decrementAndGet();
                    if (handler != null)
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                requestPushTokenToServer(pushToken);
                            }
                        }, RETRY_INTEVAL);
                } else {
                    handler = null;
                    LogUtil.d(TAG, "上报 push token 无剩余次数，终止" + mAtomicInteger.get());
                    return true;
                }
                return true;
            }
        });
    }
}
