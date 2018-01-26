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
import com.aerozhonghuan.hongyan.producer.modules.check.activity.BindActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HandInputActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HistoryRecordActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.StartCheckActivity;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * @author: drs
 * @time: 2018/1/27 1:21
 * @des:
 */
public class CheckInfoFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    String type;
    Button bt_history,bt_start_check,bt_bind;
    TitleBarView titlebarview1;
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
            rootView = inflater.inflate(R.layout.fragment_check_info, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        bt_history= (Button) rootView.findViewById(R.id.bt_history);
        bt_start_check= (Button) rootView.findViewById(R.id.bt_start_check);
        bt_bind= (Button) rootView.findViewById(R.id.bt_bind);
        titlebarview1= (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        if(type!=null){
            titlebarview1.setTitle(type);
            if ("初检".equals(type)){
                bt_start_check.setText("开始初检");
            }else{
                bt_start_check.setText("开始复检");
            }
        }

    }

    private void initData() {
    }

    private void setListen() {
        bt_history.setOnClickListener(this);
        bt_start_check.setOnClickListener(this);
        bt_bind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_history:
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                startActivity(new Intent(getActivity(), HistoryRecordActivity.class).putExtras(bundle));
                break;
            case R.id.bt_start_check:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type",type);
                startActivity(new Intent(getActivity(), StartCheckActivity .class).putExtras(bundle1));
                break;
            case R.id.bt_bind:
                Bundle bundle2 = new Bundle();
                bundle2.putString("type",type);
                startActivity(new Intent(getActivity(), BindActivity.class).putExtras(bundle2));
                break;

        }
    }
}
