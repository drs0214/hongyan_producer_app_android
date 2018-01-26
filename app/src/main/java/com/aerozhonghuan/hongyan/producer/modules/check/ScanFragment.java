package com.aerozhonghuan.hongyan.producer.modules.check;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;

/**
 * Created by zhangyonghui on 2018/1/22.
 * 扫描页面,默认红外扫描,可以选择摄像头和手动输入
 */

public class ScanFragment extends TitlebarFragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_single_scan,null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
