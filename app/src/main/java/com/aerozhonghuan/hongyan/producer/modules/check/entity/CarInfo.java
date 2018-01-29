package com.aerozhonghuan.hongyan.producer.modules.check.entity;

import java.io.Serializable;

/**
 * Created by zhangyonghui on 2018/1/29.
 * 车辆信息
 */

public class CarInfo implements Serializable {
    //车辆基本信息结构
    public Vehicle vehicle;
    //车辆位置信息结构
    public VehicleInfo vehicleInfo;

    class Vehicle{
        //车辆内部编号
        public String vhcle;
        //17位底盘号
        public String vhvin;
        //8位底盘号
        public String vhvin8;
        //终端数据库唯一id
        public String deviceId;
        //终端自定义编号
        public String deviceNo;
        //终端sim卡号
        public String simNo;
        //初检结果，0: 未测试; 1:未通过; 2:通过; 3:强制通过
        public String firstTest;
        //初检时间
        public String firstTestDate;
        //初检人姓名
        public String firstTestUsername;
        //复检结果，0: 未测试; 1:未通过; 2:通过; 3:强制通过
        public String secondTest;
        //复检时间
        public String secondTestDate;
        //复检人姓名
        public String secondTestUsername;
        //锁车功能特性值
        public String engineLockCtscVal;
        //锁车功能特性描述
        public String engineLockCtscDesc;

        @Override
        public String toString() {
            return "Vehicle{" +
                    "vhcle='" + vhcle + '\'' +
                    ", vhvin='" + vhvin + '\'' +
                    ", vhvin8='" + vhvin8 + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", deviceNo='" + deviceNo + '\'' +
                    ", simNo='" + simNo + '\'' +
                    ", firstTest='" + firstTest + '\'' +
                    ", firstTestDate='" + firstTestDate + '\'' +
                    ", firstTestUsername='" + firstTestUsername + '\'' +
                    ", secondTest='" + secondTest + '\'' +
                    ", secondTestDate='" + secondTestDate + '\'' +
                    ", secondTestUsername='" + secondTestUsername + '\'' +
                    ", engineLockCtscVal='" + engineLockCtscVal + '\'' +
                    ", engineLockCtscDesc='" + engineLockCtscDesc + '\'' +
                    '}';
        }
    }

    class VehicleInfo{
        //省
        public String province;
        //城市
        public String city;
        //区域
        public String district;
        //街道
        public String street;
        //锁车功能是否开启
        public String engineLockIsActive;

        @Override
        public String toString() {
            return "VehicleInfo{" +
                    "province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", street='" + street + '\'' +
                    ", engineLockIsActive='" + engineLockIsActive + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "vehicle=" + vehicle +
                ", vehicleInfo=" + vehicleInfo +
                '}';
    }
}
