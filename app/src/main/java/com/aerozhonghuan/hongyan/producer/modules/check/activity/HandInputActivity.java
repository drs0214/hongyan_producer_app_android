package com.aerozhonghuan.hongyan.producer.modules.check.activity;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.fragment.HandInputFragment;
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
        getTitleBarView().setTitle("手工绑定");
        if (savedInstanceState == null) {
            HandInputFragment fragment = new HandInputFragment();
            if (getIntent() != null && getIntent().getExtras() != null) {
                showFragment(fragment, false, new Bundle(getIntent().getExtras()));
            }
        }
    }
}
