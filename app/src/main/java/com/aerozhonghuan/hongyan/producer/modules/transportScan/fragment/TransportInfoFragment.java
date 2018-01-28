package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanAdapter;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanBean;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/27 1:39
 * @des:
 */
public class TransportInfoFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    String type;
    TitleBarView titlebarview1;
    GridView gridview;
    ArrayList<ManyScanBean> manyscanlist=new ArrayList<ManyScanBean>();
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
            rootView = inflater.inflate(R.layout.fragment_transport_info, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        titlebarview1= (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        titlebarview1.setTitle("运输扫描");
        gridview=(GridView) rootView.findViewById(R.id.gridview);
        ManyScanAdapter adapter=new ManyScanAdapter(getContext(),manyscanlist);
        gridview.setAdapter(adapter);
    }

    private void initData() {
        ManyScanBean bean=new ManyScanBean();
        bean.setName("下线");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("质检");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("入库");
        manyscanlist.add(bean);
        bean=new ManyScanBean();
        bean.setName("运输开始");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("运输结束");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("改装开始");
        manyscanlist.add(bean);
        bean=new ManyScanBean();
        bean.setName("改装开始");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("交付");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("盘点");
        manyscanlist.add(bean);
    }

    private void setListen() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alert(manyscanlist.get(position).getName());
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
