package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanResultAdapter;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanResultBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 15:57
 * @des:
 */
public class ManyScanResultFragment extends TitlebarFragment {
    private View rootView;
    ListView listview;
    ArrayList<ManyScanResultBean> manyscanresultlist=new ArrayList<ManyScanResultBean>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.transport_scan_result, null);
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
            ManyScanResultBean manyScanResultBean=new ManyScanResultBean("05595949","HD54548787","交付","成功","操作不匹配");
            manyscanresultlist.add(manyScanResultBean);
        }


    }
    private void setListen() {
        ManyScanResultAdapter adapter=new ManyScanResultAdapter(getContext(),manyscanresultlist);
        listview.setAdapter(adapter);
    }
}
