package com.aerozhonghuan.hongyan.producer.modules.query.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/2/2 22:15
 * @des:
 */
public class Query_ResultBean implements Serializable{

    /**
     * actionDate : 2017-01-10T13:40:56
     * actionText : ??
     * createUsername : ??
     * vhcle : 0070248680
     * vhvin : LZ2574899HD561217
     */

    public String actionDate;
    public String actionText;
    public String createUsername;
    public String vhcle;
    public String vhvin;

    public String getActionDate() {
        return actionDate;
    }

    public void setActionDate(String actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getVhcle() {
        return vhcle;
    }

    public void setVhcle(String vhcle) {
        this.vhcle = vhcle;
    }

    public String getVhvin() {
        return vhvin;
    }

    public void setVhvin(String vhvin) {
        this.vhvin = vhvin;
    }
}
