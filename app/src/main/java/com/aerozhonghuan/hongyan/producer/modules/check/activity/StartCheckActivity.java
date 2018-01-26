package com.aerozhonghuan.hongyan.producer.modules.check.activity;

import android.os.Bundle;
import android.view.View;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.fragment.StartCheckFragment;

/**
 * @author: drs
 * @time: 2018/1/27 2:56
 * @des:
 */
public class StartCheckActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setVisibility(View.GONE);
        if (savedInstanceState == null) {
            StartCheckFragment fragment = new StartCheckFragment();
            if (getIntent() != null && getIntent().getExtras() != null) {
                showFragment(fragment, false, new Bundle(getIntent().getExtras()));
            }
        }
    }
}