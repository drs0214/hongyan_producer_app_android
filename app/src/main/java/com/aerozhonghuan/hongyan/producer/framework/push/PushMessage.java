package com.aerozhonghuan.hongyan.producer.framework.push;

import java.io.Serializable;

/**
 * 推送消息
 * Created by zhangyunfei on 17/7/6.
 */

public class PushMessage implements Serializable{

    public int messageCode;
    public String messageStatus;
    public String title;
    public String content;
    public String extra;
    public long sevTime;
    public int isUserVisible;
    public int pushShowType;
    public String messageId;

    public boolean isUserVisible() {
        return isUserVisible == 2;
    }
}
