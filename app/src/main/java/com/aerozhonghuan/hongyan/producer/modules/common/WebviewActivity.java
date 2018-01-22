package com.aerozhonghuan.hongyan.producer.modules.common;

import android.os.Bundle;
import android.view.KeyEvent;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;

/**
 * 标准的 webview 页面,接收url 和 title
 */
public class WebviewActivity extends TitlebarActivity {
    private WebviewFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            fragment = new WebviewFragment();
            showFragment(fragment, false, getIntent() == null ? null : getIntent().getExtras());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragment != null && fragment.isAdded()) {
            return fragment.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
