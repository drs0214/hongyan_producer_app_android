package com.aerozhonghuan.hongyan.producer.modules.home.entity;

import java.io.Serializable;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/6/20 0020 on 下午 2:28
 */

public class HomeGridItemBean implements Serializable {
    private int imgId;
    private String text;
    private boolean isShowRedPoint;

    public HomeGridItemBean() {
    }

    public HomeGridItemBean(int imgId, String text) {
        this.imgId = imgId;
        this.text = text;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShowRedPoint() {
        return isShowRedPoint;
    }

    public void setShowRedPoint(boolean showRedPoint) {
        isShowRedPoint = showRedPoint;
    }
}
