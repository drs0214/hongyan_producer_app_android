package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * @author: drs
 * @time: 2018/1/26 13:21
 * @des:
 */
public class SingleScanFragment  extends BaseFragment {
    private View rootView;
    private TitleBarView titleBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_single_scan, null);
            initView();
            initData();
        }
        return rootView;
    }

    private void initData() {

    }

    private void initView() {

    }
}
