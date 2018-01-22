package com.aerozhonghuan.hongyan.producer.modules.common;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;

/**
 * Created by dell on 2017/7/24.
 */

public class ServiceClauseActivity extends TitlebarActivity {
    private ServiceClauseFragment mServiceClauseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            mServiceClauseFragment = new ServiceClauseFragment();
            showFragment(mServiceClauseFragment, false);
        }
    }
}
