package com.aerozhonghuan.hongyan.producer.modules.check.activity;

import android.os.Bundle;
import android.view.View;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.fragment.BindFragment;

/**
 * @author: drs
 * @time: 2018/1/27 1:39
 * @des:
 */
public class BindActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setVisibility(View.GONE);
        if (savedInstanceState == null) {
            BindFragment fragment = new BindFragment();
            if (getIntent() != null && getIntent().getExtras() != null) {
                showFragment(fragment, false, new Bundle(getIntent().getExtras()));
            }
        }
    }
}