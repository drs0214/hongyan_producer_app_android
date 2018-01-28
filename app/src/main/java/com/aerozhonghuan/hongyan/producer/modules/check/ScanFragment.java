package com.aerozhonghuan.hongyan.producer.modules.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HandInputActivity;

/**
 * Created by zhangyonghui on 2018/1/22.
 * 扫描页面,默认红外扫描,可以选择摄像头和手动输入
 */

public class ScanFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    LinearLayout ll_camera_scan, ll_hand_input;
    EditText et_num;
    String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type") ) {
            type = getArguments().getString("type");
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
        et_num= (EditText) rootView.findViewById(R.id.et_num);
        et_num.setInputType(InputType.TYPE_NULL);
        et_num.requestFocus();
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
                if (type != null && "初检".equals(type)) {

                } else {

                }
                break;
            case R.id.ll_hand_input:
                if (type != null && "初检".equals(type)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type","初检");
                    startActivity(new Intent(getActivity(), HandInputActivity.class).putExtras(bundle));

                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type","复检");
                    startActivity(new Intent(getContext(), HandInputActivity.class).putExtras(bundle));
                }
                break;
        }
    }
}
