package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.aerozhonghuan.foundation.utils.LocalStorage;
import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;

import java.io.File;

/**
 * 基础的本地存储 类
 * Created by zhangyunfei on 16/11/21.
 */
public class SimpleSettings extends LocalStorage {


    public SimpleSettings(Context context) {
        super(context, "SimpleSettings");
    }


    /**
     * 获得 当前app的基本存储目录
     *
     * @return
     */
    public static File getBaseDir() {
        File dir_sdcard = Environment.getExternalStorageDirectory();
        String packageName = MyAppliation.getApplication().getPackageName();
        File dir_base = new File(dir_sdcard, packageName);
        if (!dir_base.exists()) {
            dir_base.mkdirs();
        } else {
            if (!dir_base.isDirectory()) {
                dir_base.delete();
            }
            dir_base.mkdirs();
        }
        return dir_base;
    }

    /**
     * 获得临时文件夹
     *
     * @return
     */
    public static File getCacheDir() {
        File temp = new File(getBaseDir(), "cache");
        if (!temp.exists())
            temp.mkdirs();
        return temp;
    }

    /**
     * 清理所有缓存文件夹
     */
    public static void clearCacheDir() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                FileUtils.deleteAllChildFiles(getCacheDir());
                return null;
            }
        }.execute();
    }

    /**
     * 清理更新文件夹
     */
    public static void clearApkDir() {
        final File temp = new File(getCacheDir(), "apk");
        if (temp.exists()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    FileUtils.deleteAllChildFiles(temp);
                    return null;
                }
            }.execute();
        }
    }

    public static File getLogDir() {
        File dir = new File(getBaseDir(), "logs");
        if (!dir.exists())
            dir.mkdirs();
        return dir;
    }

    public boolean existSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }
}
