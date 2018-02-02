package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.CheckInfoActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.activity.TransportInfoActivity;

/**
 * @author: drs
 * @time: 2018/1/27 1:00
 * @des:
 */
public class HandInputFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    String type;
    Button bt_ok;
    EditText et_hand_input;
    String hand_input_type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type")) {
            type = getArguments().getString("type");
        }
        if (getArguments() != null && getArguments().containsKey("hand_input_type")) {
            hand_input_type = getArguments().getString("hand_input_type");//3从运输扫描模块进入的
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_handinput, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        bt_ok = (Button) rootView.findViewById(R.id.bt_ok);
        et_hand_input = (EditText) rootView.findViewById(R.id.et_hand_input);
    }

    private void initData() {
    }

    private void setListen() {
        bt_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                if (hand_input_type != null && Constents.HAND_INPUT_TYPE.equals(hand_input_type)) {
                    Bundle bundle=new Bundle();
                    bundle.putString("vhcle",et_hand_input.getText().toString());
                    startActivity(new Intent(getActivity(), TransportInfoActivity.class).putExtras(bundle));
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", type);
                    startActivity(new Intent(getActivity(), CheckInfoActivity.class).putExtras(bundle));
                }

                break;

        }
    }
}
