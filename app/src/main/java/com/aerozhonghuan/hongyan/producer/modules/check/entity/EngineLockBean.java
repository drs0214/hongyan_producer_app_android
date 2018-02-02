package com.aerozhonghuan.hongyan.producer.modules.check.entity;

import java.io.Serializable;

/**
 * Created by zhangyonghui on 2018/2/2.
 */

public class EngineLockBean implements Serializable {
    public boolean success;
    public String message;
    public int cid;

    @Override
    public String toString() {
        return "EngineLockBean{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", cid=" + cid +
                '}';
    }
}
