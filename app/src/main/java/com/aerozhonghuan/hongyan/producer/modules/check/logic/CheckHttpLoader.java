package com.aerozhonghuan.hongyan.producer.modules.check.logic;

import com.aerozhonghuan.hongyan.producer.http.HttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CarInfo;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CheckStatusBean;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.InspectioniHistory;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.StartCheckStateBean;

import java.util.Map;

import rx.Observable;

/**
 * Created by zhangyonghui on 2018/1/25.
 * 检查网络请求封装类
 */

public class CheckHttpLoader extends HttpLoader {

    public Observable<CarInfo> getCarInfo(String vhcle){
        return observe(apiService.getCarInfo(vhcle));
    }

    public Observable<InspectioniHistory> getInspectioniHistory(String vhcle){
        return observe(apiService.getInspectioniHistory(vhcle));
    }

    public Observable<StartCheckStateBean> startCheck(String vhcle, String type){
        return observe(apiService.startCheck(vhcle, type));
    }

    public Observable<CheckStatusBean> getLastStatus(Map<String, String> map){
        return observe(apiService.getLastStatus(map));
    }

}
