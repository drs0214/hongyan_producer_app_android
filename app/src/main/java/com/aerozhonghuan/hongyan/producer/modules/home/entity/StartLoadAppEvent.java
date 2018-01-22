package com.aerozhonghuan.hongyan.producer.modules.home.entity;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * 开始下载导航
 * Created by zhangyonghui on 17/8/7.
 */

public class StartLoadAppEvent extends EventBusEvent {
    private boolean isStart;

    public StartLoadAppEvent(boolean isStart) {
        this.isStart = isStart;
    }

    public boolean isStart() {
        return isStart;
    }
}
