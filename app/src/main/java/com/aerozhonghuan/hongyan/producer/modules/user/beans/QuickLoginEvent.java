package com.aerozhonghuan.hongyan.producer.modules.user.beans;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * Created by dell on 2017/6/27.
 */

public class QuickLoginEvent extends EventBusEvent {
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
