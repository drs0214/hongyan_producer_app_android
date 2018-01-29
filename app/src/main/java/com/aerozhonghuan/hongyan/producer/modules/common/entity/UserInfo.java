package com.aerozhonghuan.hongyan.producer.modules.common.entity;

import java.io.Serializable;

/**
 * 用户基本信息
 * 备注： UserInfo 是授权时的基本信息，UserBean记录了用户的详情信息
 * Created by dell on 2017/6/21.
 */

public class UserInfo implements Serializable {
    public UserInfo() {
    }

    public UserInfo(String userName) {
        this.userName = userName;
    }

    //    用户名称
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
