package com.aerozhonghuan.hongyan.producer.modules.common.entity;

import java.io.Serializable;

/**
 * Created by zhangyonghui on 2018/1/25.
 */

public class SessionBean implements Serializable {
    public SessionInfo session;

    public class SessionInfo implements Serializable{
        public String name;
        public String value;
    }
}
