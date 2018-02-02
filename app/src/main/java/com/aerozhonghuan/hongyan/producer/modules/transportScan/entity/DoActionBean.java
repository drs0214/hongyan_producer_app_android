package com.aerozhonghuan.hongyan.producer.modules.transportScan.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/2/1 10:48
 * @des:
 */
public class DoActionBean implements Serializable{

    /**
     * success : false
     * message : ????[???]??????[??]
     * vhcle : 0070231768
     * vhvin8 : GD321011
     * actionText : ??
     */

    public boolean success;
    public String message;
    public String vhcle;
    public String vhvin8;
    public String actionText;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVhcle() {
        return vhcle;
    }

    public void setVhcle(String vhcle) {
        this.vhcle = vhcle;
    }

    public String getVhvin8() {
        return vhvin8;
    }

    public void setVhvin8(String vhvin8) {
        this.vhvin8 = vhvin8;
    }

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }
}
