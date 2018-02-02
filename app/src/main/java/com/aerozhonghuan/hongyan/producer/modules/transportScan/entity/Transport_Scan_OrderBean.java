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
     * departure : 重庆市经开区黄茅坪b04号 401122 重庆市 cn 340
     * destination : 四川泸州市纳溪区蓝安路三段 646300 泸州市 cn 230
     * orderNo : 0100170121
     * planShipTime : 2014-11-26T10:33:00
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
