package com.aerozhonghuan.hongyan.producer.framework.versionUpdate;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/6/27 0027 on 上午 9:13
 */

public class DeleteOrStopLoadEvent extends EventBusEvent {
    String msg;

    public DeleteOrStopLoadEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
