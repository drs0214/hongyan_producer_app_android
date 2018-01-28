package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.StartCheckActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.adapter.History_RecordAdapter;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.History_RecordBean;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/27 2:06
 * @des:
 */
public class HistoryRecordFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    String type;
    TitleBarView titlebarview1;
    ListView listview;
    History_RecordAdapter adapter;
    ArrayList<History_RecordBean> history_record_list=new ArrayList<History_RecordBean>();
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
            rootView = inflater.inflate(R.layout.fragment_history_record, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        titlebarview1= (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        if(type!=null){
            titlebarview1.setTitle(type);
        }
        listview=  (ListView) rootView.findViewById(R.id.listview);
    }

    private void initData() {
        for (int i = 0; i <10 ; i++) {
            History_RecordBean bean=new History_RecordBean("张三","2018-1-27  10:30:50","初检",true);
            history_record_list.add(bean);
        }
    }

    private void setListen() {
         adapter=new History_RecordAdapter(getContext(),history_record_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("check_history_detail","1");//1是查核查历史详情
                startActivity(new Intent(getActivity(), StartCheckActivity.class).putExtras(bundle));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
