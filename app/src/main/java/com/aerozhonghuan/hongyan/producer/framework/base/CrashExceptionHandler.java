package com.aerozhonghuan.hongyan.producer.framework.base;

import android.content.Context;

import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.foundation.umeng.UmengAgent;
import com.aerozhonghuan.hongyan.producer.utils.EnvironmentInfoUtils;
import com.aerozhonghuan.hongyan.producer.utils.FileUtils;
import com.aerozhonghuan.hongyan.producer.utils.ProcessUtil;
import com.aerozhonghuan.hongyan.producer.utils.SimpleSettings;
import com.aerozhonghuan.foundation.log.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by zhangyunfei on 17/7/22.
 */

public class CrashExceptionHandler {

    private static final String TAG = CrashExceptionHandler.class.getSimpleName();

    public static void setDefaultUncaughtExceptionHandler(final Context context) {
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                e.printStackTrace(new PrintStream(baos));
                String errorText = baos.toString();
                StringBuilder sb = new StringBuilder(errorText);
                new EnvironmentInfoUtils().print(context, sb);

                String processName = ProcessUtil.getProcessName(android.os.Process.myPid());
                sb.append("\n崩溃进程名称:").append(processName);
                sb.append("\n崩溃线程名称:").append(Thread.currentThread().getName());
                errorText = sb.toString();
                LogUtil.e(TAG, "自定义捕获崩溃异常: " + errorText);

                //保存到本地崩溃日志
                File file1 = makeNewCrashFile();
                FileUtils.writeAllText(file1, errorText);

                if (!BuildConfig.BUILD_TYPE.equals("debug")) {
                    UmengAgent.reportError(MyAppliation.getApplication(), errorText);
                }

                //调用默认处理
                if (defaultUncaughtExceptionHandler != null)
                    defaultUncaughtExceptionHandler.uncaughtException(t, e);
            }
        });
    }

    private static File makeNewCrashFile() {
        String fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
        return new File(SimpleSettings.getLogDir(), String.format("crash_%s.txt", fileName));
    }
}
