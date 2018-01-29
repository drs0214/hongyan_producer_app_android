package com.aerozhonghuan.hongyan.producer.modules.query.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HandInputActivity;
import com.aerozhonghuan.hongyan.producer.modules.query.adapter.QueryResultAdapter;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.QueryResultBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanResultAdapter;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanResultBean;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/27 16:18
 * @des:
 */
public class Operation_TimeFragment extends TitlebarFragment {
    private View rootView;
    ListView listview;
    ArrayList<QueryResultBean> queryresultlist=new ArrayList<QueryResultBean>();
    QueryResultAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.query_transport_scan_result, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }
    private void initData() {
        listview=  (ListView) rootView.findViewById(R.id.listview);

    }

    private void initView() {
        for (int i = 0; i < 10; i++) {
            QueryResultBean queryResultBean=new QueryResultBean("05595949","HD54548787","2018-1-26 10:30:56","下线","车联网部");
            queryresultlist.add(queryResultBean);
        }


    }
    private void setListen() {
        adapter=new QueryResultAdapter(getContext(),queryresultlist);
        listview.setAdapter(adapter);
    }
}
