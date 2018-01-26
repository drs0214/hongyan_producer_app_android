package com.aerozhonghuan.hongyan.producer.modules.transportScan;

import android.os.Bundle;
import android.view.View;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 *  运输扫描页
 *  该页布局头部采用TabLayout, 单辆扫描fragment可共用扫描页
 */
public class TransportScanActivity extends TitlebarActivity {
    private TitleBarView titleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_scan);
        initView();
        initData();
        setListen();
    }

    private void initView() {
        titleBar = (TitleBarView)findViewById(R.id.titlebarview1);
        titleBar.setTitle(getResources().getString(R.string.transport_scan));
    }
    private void initData() {
    }

    private void setListen() {
    }
}
