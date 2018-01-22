package com.aerozhonghuan.hongyan.producer.modules.common;

import android.os.Bundle;

/**
 * Created by dell on 2017/7/24.
 */
public class ServiceClauseFragment extends WebviewFragment {
    String url = "file:///android_asset/service.html";
    @Override
    protected void onInitData(Bundle bundle) {

        getWebView().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadURL(url);
            }
        }, 100);

    }
}
