package com.aerozhonghuan.hongyan.producer.modules.check.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/1/27 2:14
 * @des:
 */
public class History_RecordBean implements Serializable {
    private String name;
    private String data;
    private String check_state;
    private boolean ispass;

    public History_RecordBean(String name, String data, String check_state, boolean ispass) {
        this.name = name;
        this.data = data;
        this.check_state = check_state;
        this.ispass = ispass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCheck_state() {
        return check_state;
    }

    public void setCheck_state(String check_state) {
        this.check_state = check_state;
    }

    public boolean isIspass() {
        return ispass;
    }

    public void setIspass(boolean ispass) {
        this.ispass = ispass;
    }
}
