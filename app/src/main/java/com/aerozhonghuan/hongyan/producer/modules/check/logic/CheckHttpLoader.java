package com.aerozhonghuan.hongyan.producer.modules.check.logic;

import com.aerozhonghuan.hongyan.producer.http.HttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CarInfo;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.History_RecordBean;

import java.util.List;

import rx.Observable;

/**
 * Created by zhangyonghui on 2018/1/25.
 */

public class CheckHttpLoader extends HttpLoader {

    public Observable<CarInfo> getCarInfo(String vhcle){
        return observe(apiService.getCarInfo(vhcle));
    }

    public Observable<List<History_RecordBean>> getInspectioniHistory(String vhcle){
        return observe(apiService.getInspectioniHistory(vhcle));
    }

}
