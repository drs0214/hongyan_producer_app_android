package com.aerozhonghuan.hongyan.producer.modules.common.event;

import java.io.File;
import java.io.Serializable;

/**
 * 描述:上传文件返回实体类
 * 作者:zhangyonghui
 * 创建日期：2017/7/11 0011 on 上午 11:19
 */

public class UploadFileBean implements Serializable {
    private String fullPath;
    private String size;
    private String id;

    // 本地使用字段
    private File nativeFile;

    public UploadFileBean(File nativeFile) {
        this.nativeFile = nativeFile;
    }

    public UploadFileBean() {
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File getNativeFile() {
        return nativeFile;
    }

    public void setNativeFile(File nativeFile) {
        this.nativeFile = nativeFile;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UploadFileBean{" +
                "fullPath='" + fullPath + '\'' +
                ", size='" + size + '\'' +
                ", id='" + id + '\'' +
                ", nativeFile='" + nativeFile + '\'' +
                '}';
    }
}
