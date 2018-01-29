package com.aerozhonghuan.hongyan.producer.modules.check;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;

/**
 * 检查页面,包括初检和复检,由于流程一致,通过标识符判断初检和复检
 */
public class CheckActivity extends TitlebarActivity {

    private ScanFragment scanFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            scanFragment = new ScanFragment();
            if (getIntent() != null && getIntent().getExtras() != null) {
                showFragment(scanFragment, false, new Bundle(getIntent().getExtras()));
            }
        }
    }
}
