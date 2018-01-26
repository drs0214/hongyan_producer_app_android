package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * @author: drs
 * @time: 2018/1/27 1:39
 * @des:
 */
public class BindFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    String type;
    TitleBarView titlebarview1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type")) {
            type = getArguments().getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_handbind, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        titlebarview1= (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        titlebarview1.setTitle("手工绑定");
    }

    private void initData() {
    }

    private void setListen() {
    }

    @Override
    public void onClick(View v) {

    }
}
