package com.aerozhonghuan.hongyan.producer.modules.transportScan.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/1/31 17:23
 * @des:
 */
public class Transport_Scan_OrderBean implements Serializable{

    /**
     * agent : 重庆安吉物流有限公司
     * customerName : 丹东金路通汽车销售服务有限公司
     * departure : 重庆市经开区黄茅坪b04号 401122 重庆市 cn 340
     * destination : 东港市前阳镇石佛村李家堡120号2号楼 118300 辽宁省 cn 0
     * deviceNo : 64696253987
     * orderNo : 0100170260
     * planShipTime : 2014-11-28 10:47:00
     * vhcle : 0070231880
     */

    public String agent;
    public String customerName;
    public String departure;
    public String destination;
    public String deviceNo;
    public String orderNo;
    public String planShipTime;
    public String vhcle;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPlanShipTime() {
        return planShipTime;
    }

    public void setPlanShipTime(String planShipTime) {
        this.planShipTime = planShipTime;
    }

    public String getVhcle() {
        return vhcle;
    }

    public void setVhcle(String vhcle) {
        this.vhcle = vhcle;
    }
}
