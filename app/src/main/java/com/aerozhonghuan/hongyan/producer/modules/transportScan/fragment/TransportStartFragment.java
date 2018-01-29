package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: drs
 * @time: 2018/1/29 15:26
 * @des:
 */
public class TransportStartFragment extends TitlebarFragment {
    private View rootView;
    Spinner sp_number;
    private List<String> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_transport_start, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }
    private void initData() {
        list = new ArrayList<String>();
        list.add("032626465");
        list.add("125465062");
        list.add("154888528");
        list.add("031548195");
        list.add("597826123");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_number.setAdapter(adapter);
    }

    private void initView() {
        sp_number=(Spinner)rootView.findViewById(R.id.sp_number);

    }
    private void setListen() {
    }
}
