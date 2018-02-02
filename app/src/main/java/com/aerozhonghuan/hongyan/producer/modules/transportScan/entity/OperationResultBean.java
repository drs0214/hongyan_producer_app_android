package com.aerozhonghuan.hongyan.producer.modules.transportScan.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/2/2 20:59
 * @des:
 */
public class OperationResultBean implements Serializable{
    private String msg;
    private boolean issuccess;

    public OperationResultBean(boolean issuccess,String msg) {
        this.msg = msg;
        this.issuccess = issuccess;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isIssuccess() {
        return issuccess;
    }

    public void setIssuccess(boolean issuccess) {
        this.issuccess = issuccess;
    }
}
