package com.aerozhonghuan.hongyan.producer.modules.transportScan.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/1/31 17:23
 * @des:
 */
public class Transport_Scan_OrderBean implements Serializable{

    /**
     * agent : ??????????
     * customerName : ???????????????
     * departure : ?????????b04? 401122 ??? cn 340
     * destination : ????????????120?2?? 118300 ??? cn 0
     * orderNo : 0100170260
     * planShipTime : 2014-11-28T10:47:00
     */

    public String agent;
    public String customerName;
    public String departure;
    public String destination;
    public String orderNo;
    public String planShipTime;

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
}
