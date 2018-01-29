package com.aerozhonghuan.hongyan.producer.modules.home.entity;

import java.io.Serializable;

/**
 * Created by zhangyonghui on 2018/1/28.
 */

public class AppInfo implements Serializable {
    public String appName;
    public String version;
    public String versionDate;

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", version='" + version + '\'' +
                ", versionDate='" + versionDate + '\'' +
                '}';
    }
}
