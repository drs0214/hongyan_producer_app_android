package com.aerozhonghuan.hongyan.producer.framework.push;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aerozhonghuan.hongyan.producer.modules.common.SplashActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.modules.home.MainActivity;
import com.aerozhonghuan.hongyan.producer.utils.StringUtils;
import com.aerozhonghuan.hongyan.producer.utils.SystemUtil;
import com.aerozhonghuan.hongyan.producer.widget.CustomNotification;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息处理器
 * Created by zhangyunfei on 17/7/4.
 */
public class PushMessageHandler {
    private static final String TAG = "PushMessageHandler";
    private static Gson GSON = new Gson();
    private static boolean isActivityRunning = true;
    private static PushMessage pushMessage;

    public static void handleMessage(Context context, String msg, String extra) {
        print(String.format("收到透传消息,msg = %s, extra = %s", msg, extra));
        //在这里处理收到的推送消息
        try {
            CustomNotification customNotification;
            JSONObject jsonExtra = null;
            pushMessage = GSON.fromJson(msg, PushMessage.class);
            if (!TextUtils.isEmpty(pushMessage.extra)) {
                String str = StringUtils.getJsonStr(pushMessage.extra);
                jsonExtra = new JSONObject(str);
            }
            //如果 对用户可见，存放在消息库中
            if (pushMessage.isUserVisible()) {
                customNotification = new CustomNotification(context)
                        .setTitle(pushMessage.title)
                        .setContent(pushMessage.content);
            } else {
                // 不可见消息处理
                return;
            }
            // 如果程序没有正在运行,则不显示通知,只进行本地记录,用户打开应用后可在消息中操作
            if (!SystemUtil.isAppRunning(context)) {
                isActivityRunning = false;
            } else {
                isActivityRunning = true;
            }
            if (!UserInfoManager.isUserAuthenticated()) {
                return;
            }
            else {
                if (isActivityRunning) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("tab", "1");
                    jumpIntent(context, customNotification, MainActivity.class, params);
                } else {
                    jumpIntent(context, customNotification, null, null);
                }
            }
            customNotification.showNotify(pushMessage.messageCode);
        } catch (JsonSyntaxException | JSONException ex) {
            ex.printStackTrace();
        }
    }

    private static void jumpIntent(Context context, CustomNotification customNotification, Class<?> cls, Map<String, Object> params) {
        if (cls != null) {
            Intent intent = new Intent(context, cls);
            if (params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    if (params.get(key) instanceof String) {
                        intent.putExtra(key, (String) params.get(key));
                    } else if (params.get(key) instanceof Integer) {
                        intent.putExtra(key, (Integer) params.get(key));
                    }
                }
            }
            if (isActivityRunning) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                customNotification.setJumpIntent(intent, pushMessage.messageId, pushMessage.pushShowType);
            } else {
                Intent intent2 = new Intent(context, SplashActivity.class);
                if (cls != null) {
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                    intent2.putExtra("notify_intent", pendingIntent);
                }
                customNotification.setJumpIntent(intent2, pushMessage.messageId, pushMessage.pushShowType);
            }
        }
        // 如果不传入跳转activity 且 app没有运行的话打开应用(防止多次创建Mainactivity引发问题)
        else if (cls == null && !isActivityRunning) {
            Intent intent2 = new Intent(context, SplashActivity.class);
            customNotification.setJumpIntent(intent2, pushMessage.messageId, pushMessage.pushShowType);
        }
    }

    private static void print(String str) {
        LogUtil.d(TAG, str);
    }
}


/*

{\"messageStatus\":\"1\",\"extra\":\"\",\"messageCode\":\"1\",\"title\":\"ddd标题\",\"sevTime\":1499304843042,\"content\":\"sdkfjdsklfjdslf1234567899\"}

* */