package com.aerozhonghuan.hongyan.producer.framework.push;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aerozhonghuan.hongyan.producer.framework.base.URLs;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.CommonMessage;
import com.aerozhonghuan.oknet2.RequestBuilder;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            PendingIntent pendingIntent = intent.getParcelableExtra("intent");
            pendingIntent.send();
            String messageId = intent.getStringExtra("messageId");
            int pushShowType = intent.getIntExtra("pushShowType", 1);
            setMessageRead(context, messageId, pushShowType);
            LogUtil.d("SSSS", "设置消息已读");
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private void setMessageRead(Context context, String messageId, int pushShowType) {
        RequestBuilder
                .with(context)
                .URL(URLs.MESSAGE_SET_MESSAGEREAD)
                .para("messageId", messageId)
                .para("messageType", pushShowType)
                .para("appType", 2)
                .onSuccess(new CommonCallback<String>(String.class) {
                    @Override
                    public void onSuccess(String messsageBodyObject, CommonMessage commonMessage, String allResponseString) {

                    }

                    @Override
                    public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                        return true;
                    }
                }).excute();

    }
}
