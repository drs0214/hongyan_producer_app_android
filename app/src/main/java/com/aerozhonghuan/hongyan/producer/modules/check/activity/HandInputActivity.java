package com.aerozhonghuan.hongyan.producer.modules.check.activity;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment.ManyScanResultFragment;

/**
 * @author: drs
 * @time: 2018/1/26 19:23
 * @des:
 */
public class HandInputActivity extends TitlebarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setTitle("初检");
        if (savedInstanceState == null) {
            ManyScanResultFragment fragment = new ManyScanResultFragment();
            showFragment(fragment, false);
        }
    }
}
