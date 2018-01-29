package com.aerozhonghuan.hongyan.producer.modules.transportScan.activity;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment.TransportStartFragment;

/**
 * @author: drs
 * @time: 2018/1/29 15:25
 * @des:运输开始
 */
public class TransportStartActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setTitle("运输扫描");
        if (savedInstanceState == null) {
            TransportStartFragment fragment = new TransportStartFragment();
            showFragment(fragment, false);
        }
    }
}