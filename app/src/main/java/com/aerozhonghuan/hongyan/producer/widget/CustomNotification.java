package com.aerozhonghuan.hongyan.producer.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.push.NotificationBroadcastReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 描述:通知类
 * 作者:zhangyonghui
 * 创建日期：2017/7/28 0028 on 下午 12:47
 */

public class CustomNotification {
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Context context;

    public CustomNotification(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
        builder.setWhen(System.currentTimeMillis())
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true);
    }

    private PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, new Intent(), flags);
        return pendingIntent;
    }

    public CustomNotification setTitle(String title) {
        builder.setContentTitle(title);
        return this;
    }

    public CustomNotification setContent(String content) {
        builder.setContentText(content);
        return this;
    }

    public CustomNotification setJumpIntent(Intent intent, String messageId, int pushShowType) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentClick = new Intent(context, NotificationBroadcastReceiver.class);
        intentClick.putExtra("intent", pendingIntent);
        intentClick.putExtra("messageId", messageId);
        intentClick.putExtra("pushShowType", pushShowType);
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(context, 0, intentClick, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntentClick);
        return this;
    }

    public void showNotify(int notifyId) {
        notificationManager.notify(notifyId, builder.build());
    }
}
