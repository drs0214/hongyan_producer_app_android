package com.aerozhonghuan.hongyan.producer.modules.check.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyonghui on 2018/1/31.
 * 检测状态实体类
 */

public class CheckStatusBean implements Serializable {
    public VehicleInfo vehicleInfo;
    public List<CommandHistoryList> commandHistoryList;

    public class VehicleInfo{
        //0=未定位，1=已定位
        public int locateStatus;
        public String province;
        public String city;
        public String district;
        public String street;
        //锁车状态文本描述
        public String engineLockStatusText;
        //锁车功能是否激活，true=已激活，false=未激活
        public boolean engineLockIsActive;
        //制动信号
        public int breakingSignal;
        //车门信号
        public int doorSignal;
        //左转灯信号
        public int leftTurnSignal;
        //右转灯信号
        public int rightTurnSignal;
        //远光灯信号
        public int highBeamLightSignal;
        //近光灯信号
        public int lowBeamLightSignal;
        //雾灯信号
        public int fogLightSignal;
        //喇叭信号
        public int hornSignal;
        //acc开关信号
        public int accSignal;

        @Override
        public String toString() {
            return "VehicleInfo{" +
                    "locateStatus=" + locateStatus +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", street='" + street + '\'' +
                    ", engineLockStatusText='" + engineLockStatusText + '\'' +
                    ", engineLockIsActive=" + engineLockIsActive +
                    ", breakingSignal=" + breakingSignal +
                    ", doorSignal=" + doorSignal +
                    ", leftTurnSignal=" + leftTurnSignal +
                    ", rightTurnSignal=" + rightTurnSignal +
                    ", highBeamLightSignal=" + highBeamLightSignal +
                    ", lowBeamLightSignal=" + lowBeamLightSignal +
                    ", fogLightSignal=" + fogLightSignal +
                    ", hornSignal=" + hornSignal +
                    ", accSignal=" + accSignal +
                    '}';
        }
    }
    public class CommandHistoryList{
        //命令历史记录id
        public int id;
        //状态 0:等待;1:离线;2:已向网络发送;3:应答-成功;4:应答-失败;5: 应答-消息有误;6:应答-不支持'
        public int phase;

        @Override
        public String toString() {
            return "CommandHistoryList{" +
                    "id=" + id +
                    ", phase=" + phase +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CheckStatusBean{" +
                "vehicleInfo=" + vehicleInfo +
                ", commandHistoryList=" + commandHistoryList +
                '}';
    }
}
