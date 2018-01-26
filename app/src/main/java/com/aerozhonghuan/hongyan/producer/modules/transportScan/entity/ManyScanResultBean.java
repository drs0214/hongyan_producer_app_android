package com.aerozhonghuan.hongyan.producer.modules.transportScan.entity;

import java.io.Serializable;

/**
 * @author: drs
 * @time: 2018/1/26 15:27
 * @des:
 */
public class ManyScanResultBean implements Serializable{
    public String bianhao;
    public String dipanhao;
    public String saomiaotype;
    public String scanresult;
    public String failyuanyin;

    public ManyScanResultBean(String bianhao, String dipanhao, String saomiaotype, String scanresult, String failyuanyin) {
        this.bianhao = bianhao;
        this.dipanhao = dipanhao;
        this.saomiaotype = saomiaotype;
        this.scanresult = scanresult;
        this.failyuanyin = failyuanyin;
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

    public String getSaomiaotype() {
        return saomiaotype;
    }

    public void setSaomiaotype(String saomiaotype) {
        this.saomiaotype = saomiaotype;
    }

    public String getScanresult() {
        return scanresult;
    }

    public void setScanresult(String scanresult) {
        this.scanresult = scanresult;
    }

    public String getFailyuanyin() {
        return failyuanyin;
    }

    public void setFailyuanyin(String failyuanyin) {
        this.failyuanyin = failyuanyin;
    }
}
