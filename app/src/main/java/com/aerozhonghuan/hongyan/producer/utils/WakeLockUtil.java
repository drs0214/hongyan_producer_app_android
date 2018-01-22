package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by zhangyunfei on 16/12/29.
 */

public class WakeLockUtil {
    private static final String TAG = "WakeLockUtil";
    private PowerManager.WakeLock wakeLock;

    public void acquireWakeLock(Context context) {
        if (wakeLock == null) {
            Log.d(TAG, "Acquiring wake lock");
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, this.getClass().getCanonicalName());
//            wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, this.getClass().getCanonicalName());
            wakeLock.acquire();
        }

    }


    public void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            Log.d(TAG, "Release wake lock");
            wakeLock.release();
            wakeLock = null;
        }
    }

}
