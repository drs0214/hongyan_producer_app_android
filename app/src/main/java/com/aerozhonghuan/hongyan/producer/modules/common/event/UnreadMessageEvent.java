package com.aerozhonghuan.hongyan.producer.modules.common.event;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * 异常提醒
 * Created by zhangyunfei on 17/6/23.
 */

public class UnreadMessageEvent extends EventBusEvent {
    boolean isHiddenPoint;

    public UnreadMessageEvent(boolean isHiddenPoint) {
        this.isHiddenPoint = isHiddenPoint;
    }

    public boolean isHiddenPoint() {
        return isHiddenPoint;
    }
}
