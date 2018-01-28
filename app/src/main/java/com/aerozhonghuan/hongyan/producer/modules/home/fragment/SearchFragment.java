package com.aerozhonghuan.hongyan.producer.modules.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.query.activity.Query_Transport_Scan_HistoryActivity;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * Created by dell on 2017/10/21.
 */

public class SearchFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private TitleBarView titleBar;
    LinearLayout ll_query;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_transport_scan_history, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void setListen() {
        ll_query.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        titleBar = (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        titleBar.enableBackButton(false);
        titleBar.setTitle(getResources().getString(R.string.string_tab_search));
        ll_query= (LinearLayout) rootView.findViewById(R.id.ll_query);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_query:
                startActivity(new Intent(getContext(), Query_Transport_Scan_HistoryActivity.class));
                break;
        }
    }
}
