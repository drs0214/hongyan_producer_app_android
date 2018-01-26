package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.CheckInfoActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HandInputActivity;

/**
 * @author: drs
 * @time: 2018/1/27 1:00
 * @des:
 */
public class HandInputFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    String type;
    Button bt_ok;
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
            rootView = inflater.inflate(R.layout.fragment_handinput, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        bt_ok= (Button) rootView.findViewById(R.id.bt_ok);
    }

    private void initData() {
    }

    private void setListen() {
        bt_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_ok:
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                startActivity(new Intent(getActivity(), CheckInfoActivity.class).putExtras(bundle));
                break;

        }
    }
}
