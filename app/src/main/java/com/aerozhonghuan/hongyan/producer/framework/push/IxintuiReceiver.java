package com.aerozhonghuan.hongyan.producer.framework.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.ixintui.pushsdk.SdkConstants;

/**
 * 爱心推push
 * Created by zhangyunfei on 17/7/4.
 */

public class IxintuiReceiver extends BroadcastReceiver {
    private static final String TAG = "PUSH";

    /*** 推送消息接收器 ***/
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        // 透传消息
        if (action.equals(SdkConstants.MESSAGE_ACTION)) {
            String msg = intent.getStringExtra(SdkConstants.MESSAGE);
            String extra = intent.getStringExtra(SdkConstants.ADDITION);
            print("message received, msg is: " + msg + "extra: " + extra);
            // 处理透传内容
            PushContext.getInstance().onReceiveMessage(context,msg, extra);
        }
        // SDK API的异步返回结果
        else if (action.equals(SdkConstants.RESULT_ACTION)) {
            // API 名称
            String cmd = intent.getStringExtra(SdkConstants.COMMAND);
            // 返回值，0为成功，否则失败
            int code = intent.getIntExtra(SdkConstants.CODE, 0);
            if (code != 0) {
                // 错误信息
                String error = intent.getStringExtra(SdkConstants.ERROR);
                print("command is: " + cmd + " result error: " + error);
            } else {
                print("command is: " + cmd + "result OK");
            }
            // 附加结果，比如添加成功的tag， 比如推送是否暂停等
            String extra = intent.getStringExtra(SdkConstants.ADDITION);
            if (extra != null) {
                print("result extra: " + extra);
            }
            //触发事件
            PushContext.getInstance().onPushCommondCallback(context,cmd, code, extra);
        }
        // 通知点击事件
        else if (action.equals(SdkConstants.NOTIFICATION_CLICK_ACTION)) {
            String msg = intent.getStringExtra(SdkConstants.MESSAGE);
            print("notification click received, msg is: " + msg);
        }
    }

    private void print(String str) {
        LogUtil.d(TAG, str);
    }

}
