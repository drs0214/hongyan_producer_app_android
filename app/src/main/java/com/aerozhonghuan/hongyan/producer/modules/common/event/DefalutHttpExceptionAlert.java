package com.aerozhonghuan.hongyan.producer.modules.common.event;

import com.aerozhonghuan.foundation.eventbus.EventBusEvent;

/**
 * 异常提醒
 * Created by zhangyunfei on 17/6/23.
 */

public class DefalutHttpExceptionAlert extends EventBusEvent {
    String errorMsg;

    public DefalutHttpExceptionAlert(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
