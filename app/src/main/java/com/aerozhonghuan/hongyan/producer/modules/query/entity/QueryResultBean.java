package com.aerozhonghuan.hongyan.producer.modules.query.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/1/26 15:27
 * @des:
 */
public class QueryResultBean implements Serializable{
    public String bianhao;
    public String dipanhao;
    public String operation_time;
    public String operation_type;
    public String tv_department;

    public QueryResultBean(String bianhao, String dipanhao, String operation_time, String operation_type, String tv_department) {
        this.bianhao = bianhao;
        this.dipanhao = dipanhao;
        this.operation_time = operation_time;
        this.operation_type = operation_type;
        this.tv_department = tv_department;
    }

    public String getTv_department() {
        return tv_department;
    }

    public void setTv_department(String tv_department) {
        this.tv_department = tv_department;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getOperation_time() {
        return operation_time;
    }

    public void setOperation_time(String operation_time) {
        this.operation_time = operation_time;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public String getDipanhao() {
        return dipanhao;
    }

    public void setDipanhao(String dipanhao) {
        this.dipanhao = dipanhao;
    }




}
