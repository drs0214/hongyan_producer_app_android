package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.check.CheckActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HandInputActivity;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * @author: drs
 * @time: 2018/1/26 13:21
 * @des:
 */
public class SingleScanFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private TitleBarView titleBar;
    EditText et_num;
    LinearLayout ll_hand_input;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_single_scan, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void setListen() {
        ll_hand_input.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        et_num = (EditText) rootView.findViewById(R.id.et_num);
        ll_hand_input = (LinearLayout) rootView.findViewById(R.id.ll_hand_input);
        et_num.setInputType(InputType.TYPE_NULL);
        et_num.requestFocus();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_hand_input:
                Bundle bundle = new Bundle();
                bundle.putString("transport_scan","1");//1运输扫描
                startActivity(new Intent(getActivity(), HandInputActivity.class).putExtras(bundle));
                break;
        }
    }
}
