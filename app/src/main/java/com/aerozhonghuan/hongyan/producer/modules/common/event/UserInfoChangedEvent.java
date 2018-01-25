package com.aerozhonghuan.hongyan.producer.modules.common.event;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserBean;

/**
 * 用户信息发生变化
 * Created by zhangyunfei on 17/6/29.
 */

public class UserInfoChangedEvent extends EventBusEvent {
    private UserBean userBean;

    public UserInfoChangedEvent(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }
}
