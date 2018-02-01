package com.aerozhonghuan.hongyan.producer.modules.check.entity;

import java.io.Serializable;

/**
 * Created by zhangyonghui on 2018/1/30.
 */

public class StartCheckStateBean implements Serializable {
    // true=执行成功进入检测页面, false=执行失败,吐司message
    public boolean success;
    // 消息
    public String messsage;
    // 检测记录数据库唯一id
    public String inspectionId;
    // 请求车辆最新信息api的频率，单位ms;若返回空或0，使用默认频率5000ms
    public int interval;

    @Override
    public String toString() {
        return "StartCheckStateBean{" +
                "success=" + success +
                ", messsage='" + messsage + '\'' +
                ", inspectionId='" + inspectionId + '\'' +
                ", interval=" + interval +
                '}';
    }
}
