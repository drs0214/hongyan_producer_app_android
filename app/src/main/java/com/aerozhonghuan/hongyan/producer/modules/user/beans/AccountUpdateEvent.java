package com.aerozhonghuan.hongyan.producer.modules.user.beans;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * Created by dell on 2017/6/27.
 */

public class AccountUpdateEvent extends EventBusEvent {
    private String key;
    private String value;
    public static final String UPDATE_PHONE = "update_phone";
    public static final String UPDATE_NAME = "update_name";
    public static final String UPDATE_IMG = "update_img";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
