package com.aerozhonghuan.hongyan.producer.modules.common;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;

/**
 * Created by dell on 2017/6/28.
 * 关于我们
 */

public class AboutActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTitleBarView().setTitle("关于我们");
        if (savedInstanceState == null) {
            AboutFragment fragment = new AboutFragment();
            showFragment(fragment, false);
        }
    }
}
