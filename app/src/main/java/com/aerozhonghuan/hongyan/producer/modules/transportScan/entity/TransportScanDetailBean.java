package com.aerozhonghuan.hongyan.producer.modules.transportScan.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author: drs
 * @time: 2018/1/30 15:02
 * @des:
 */
public class TransportScanDetailBean implements Serializable{

    /**
     * actions : [{"description":"运输开始扫描动作","enabled":true,"label":"发车","name":"da:tp:start"},{"description":"盘库扫描动作","enabled":true,"label":"盘库","name":"da:stock"},{"description":"交付扫描动作","enabled":false,"label":"交付","name":"da:dn:finish"}]
     * vehicle : {"actionDate":"2017-01-07T12:30:54","createUsername":"赵云","deviceNo":"64680848570","ecuvin":"LZFF25P4XHD561205","statusText":"运输结束","vhcle":"0070102911","vhvin":"LZFF25P4XHD561205"}
     */

    public VehicleBean vehicle;
    public List<ActionsBean> actions;

    public VehicleBean getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleBean vehicle) {
        this.vehicle = vehicle;
    }

    public List<ActionsBean> getActions() {
        return actions;
    }

    public void setActions(List<ActionsBean> actions) {
        this.actions = actions;
    }

    public static class VehicleBean {
        /**
         * actionDate : 2017-01-07T12:30:54
         * createUsername : 赵云
         * deviceNo : 64680848570
         * ecuvin : LZFF25P4XHD561205
         * statusText : 运输结束
         * vhcle : 0070102911
         * vhvin : LZFF25P4XHD561205
         */

        public String actionDate;
        public String createUsername;
        public String deviceNo;
        public String ecuvin;
        public String statusText;
        public String vhcle;
        public String vhvin;

        public String getActionDate() {
            return actionDate;
        }

        public void setActionDate(String actionDate) {
            this.actionDate = actionDate;
        }

        public String getCreateUsername() {
            return createUsername;
        }

        public void setCreateUsername(String createUsername) {
            this.createUsername = createUsername;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public String getEcuvin() {
            return ecuvin;
        }

        public void setEcuvin(String ecuvin) {
            this.ecuvin = ecuvin;
        }

        public String getStatusText() {
            return statusText;
        }

        public void setStatusText(String statusText) {
            this.statusText = statusText;
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

    public static class ActionsBean {
        /**
         * description : 运输开始扫描动作
         * enabled : true
         * label : 发车
         * name : da:tp:start
         */

        public String description;
        public boolean enabled;
        public boolean checked;
        public String label;
        public String name;

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
