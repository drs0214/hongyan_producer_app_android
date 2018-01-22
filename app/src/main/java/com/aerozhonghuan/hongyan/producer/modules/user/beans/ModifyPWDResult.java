package com.aerozhonghuan.hongyan.producer.modules.user.beans;

import java.io.Serializable;

/**
 * Created by dell on 2017/6/26.
 */

public class ModifyPWDResult implements Serializable {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
