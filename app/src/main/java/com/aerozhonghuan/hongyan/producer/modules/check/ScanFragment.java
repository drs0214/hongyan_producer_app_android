package com.aerozhonghuan.hongyan.producer.modules.check;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;

/**
 * Created by zhangyonghui on 2018/1/22.
 * 扫描页面,默认红外扫描,可以选择摄像头和手动输入
 */

public class ScanFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    LinearLayout ll_camera_scan, ll_hand_input;
    String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("stationInfo") && getArguments().containsKey("stationId")) {
            type= getArguments().getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_single_scan, null);
        }
        initView();
        initData();
        setListen();
        return rootView;
    }


    private void initView() {
        ll_camera_scan = (LinearLayout) rootView.findViewById(R.id.ll_camera_scan);
        ll_hand_input = (LinearLayout) rootView.findViewById(R.id.ll_hand_input);
    }

    private void initData() {

    }

    private void setListen() {
        ll_camera_scan.setOnClickListener(this);
        ll_hand_input.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_camera_scan:
                if (type!=null&&"初检".equals(type)){

                }else{

                }
                break;
            case R.id.ll_hand_input:
                if (type!=null&&"复检".equals(type)){

                }else{

                }
                break;
        }
    }
}
