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
import com.aerozhonghuan.hongyan.producer.modules.check.CheckActivity;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * @author: drs
 * @time: 2018/1/27 2:56
 * @des:
 */
public class StartCheckFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    String type,check_history_detail;
    TitleBarView titlebarview1;
    Button bt_back,bt_end_check,bt_pass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type")) {
            type = getArguments().getString("type");
        }
        if (getArguments() != null && getArguments().containsKey("check_history_detail")) {
            check_history_detail = getArguments().getString("check_history_detail");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_start_check, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        titlebarview1 = (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        if (type != null) {
            titlebarview1.setTitle(type);
        }
        bt_back= (Button) rootView.findViewById(R.id.bt_back);
        bt_end_check= (Button) rootView.findViewById(R.id.bt_end_check);
        bt_pass= (Button) rootView.findViewById(R.id.bt_pass);


    }

    private void initData() {
        if(check_history_detail!=null){
            bt_back.setVisibility(View.GONE);
            bt_end_check.setVisibility(View.GONE);
            bt_pass.setVisibility(View.GONE);
        }
        if(type!=null){
            titlebarview1.setTitle(type);
            if ("初检".equals(type)){
                bt_back.setText("返回"+type+"扫描首页");
                bt_end_check.setText("结束"+type);
                bt_pass.setVisibility(View.GONE);
            }else{
                bt_back.setText("返回"+type+"扫描首页");
                bt_end_check.setText("结束"+type);
                bt_pass.setVisibility(View.VISIBLE);
            }
        }

    }

    private void setListen() {
        bt_back.setOnClickListener(this);
        bt_end_check.setOnClickListener(this);
        bt_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_back:
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                startActivity(new Intent(getActivity(), CheckActivity.class).putExtras(bundle));
                getActivity().finish();
                break;
            case R.id.bt_end_check:
                alert("结束"+type);
                break;
            case R.id.bt_pass:
                break;
        }

    }
}