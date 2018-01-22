package com.aerozhonghuan.hongyan.producer.modules.home.entity;

import java.io.Serializable;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/6/21 0021 on 上午 1:08
 */

public class HomeBannerInfo implements Serializable {
    private String imgPath;
    private String bannerLink;
    private String bannerName;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBannerLink() {
        return bannerLink;
    }

    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }
}
