package com.aerozhonghuan.hongyan.producer.modules.query.logic;

import com.aerozhonghuan.hongyan.producer.http.HttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.Query_ResultBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.DoActionBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.Transport_Scan_OrderBean;

import java.util.List;
import java.util.Map;

import rx.Observable;


public class QueryHttpLoader extends HttpLoader {

    public Observable<List<Query_ResultBean>> query(Map<String, String> querymap){
        return observe(apiService.query(querymap));
    }


}
