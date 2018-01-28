package com.aerozhonghuan.hongyan.producer.modules.transportScan.activity;

import android.os.Bundle;
import android.view.View;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.fragment.BindFragment;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment.TransportInfoFragment;

/**
 * @author: drs
 * @time: 2018/1/27 1:39
 * @des:
 */
public class TransportInfoActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setVisibility(View.GONE);
        if (savedInstanceState == null) {
            TransportInfoFragment fragment = new TransportInfoFragment();
                showFragment(fragment, false);
        }
    }
}