package com.aerozhonghuan.hongyan.producer.modules.common.entity;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 用户基本信息
 * 备注： UserInfo 是授权时的基本信息，UserBean记录了用户的详情信息
 * Created by dell on 2017/6/21.
 */

public class UserInfo implements Serializable {
    //    用户id(电话、邮箱之类)
    private String userId;
    //    用户名称
    private String userName;
    //    用户类型
    private String roleCode;
    //    token
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String str = gson.toJson(this);
        return str;
    }
}
