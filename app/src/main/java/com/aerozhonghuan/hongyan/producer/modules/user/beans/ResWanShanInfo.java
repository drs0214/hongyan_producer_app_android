package com.aerozhonghuan.hongyan.producer.modules.user.beans;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/1/17 14:24
 * @des:
 */
public class ResWanShanInfo implements Serializable {
    public String message;
    public String resultCode;
    public String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
