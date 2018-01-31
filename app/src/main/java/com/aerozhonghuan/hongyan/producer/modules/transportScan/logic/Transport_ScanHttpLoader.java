package com.aerozhonghuan.hongyan.producer.modules.transportScan.logic;

import com.aerozhonghuan.hongyan.producer.http.HttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CarInfo;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.History_RecordBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;

import java.util.List;

import rx.Observable;

/**
 * Created by zhangyonghui on 2018/1/25.
 */

public class Transport_ScanHttpLoader extends HttpLoader {

    public Observable<TransportScanDetailBean> getVehicleAndActions(String vhcle){
        return observe(apiService.getVehicleAndActions(vhcle));
    }
    public Observable<List<TransportScanDetailBean.ActionsBean>> actions(){
        return observe(apiService.actions());
    }


}
