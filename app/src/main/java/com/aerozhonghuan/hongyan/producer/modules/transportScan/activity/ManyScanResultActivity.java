package com.aerozhonghuan.hongyan.producer.modules.transportScan.activity;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment.ManyScanResultFragment;

/**
 * @author: drs
 * @time: 2018/1/26 15:55
 * @des:
 */
public class ManyScanResultActivity extends TitlebarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setTitle("运输扫描");
        if (savedInstanceState == null) {
            ManyScanResultFragment fragment = new ManyScanResultFragment();
            showFragment(fragment, false);
        }
    }
}
