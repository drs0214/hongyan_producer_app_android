package com.aerozhonghuan.hongyan.producer.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * Created by zhangyunfei on 17/7/19.
 */

public class ProcessUtil {

    /**
     * 根据进程 ID 获取进程名
     *
     * @param pid
     * @return
     */
    public static String getProcessName(int pid) {
        ActivityManager am = (ActivityManager) MyAppliation.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        if (processInfoList == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
