package com.aerozhonghuan.hongyan.producer.modules.check.activity;

import android.os.Bundle;
import android.view.View;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.fragment.CheckInfoFragment;

/**
 * @author: drs
 * @time: 2018/1/27 1:20
 * @des:
 */
public class CheckInfoActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setVisibility(View.GONE);
        if (savedInstanceState == null) {
            CheckInfoFragment fragment = new CheckInfoFragment();
            if (getIntent() != null && getIntent().getExtras() != null) {
                showFragment(fragment, false, new Bundle(getIntent().getExtras()));
            }
        }
    }
}