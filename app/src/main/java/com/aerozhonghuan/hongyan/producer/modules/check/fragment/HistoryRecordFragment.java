package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HistoryRecordDetailActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.adapter.History_RecordAdapter;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.History_RecordBean;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.InspectioniHistory;
import com.aerozhonghuan.hongyan.producer.modules.check.logic.CheckHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/27 2:06
 * @des:
 */
public class HistoryRecordFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    String type;
    TitleBarView titlebarview1;
    ListView listview;
    History_RecordAdapter adapter;
    ArrayList<History_RecordBean> history_record_list=new ArrayList<History_RecordBean>();
    private String vhcle;
    private ProgressDialogIndicator progressDialogIndicator;
    private CheckHttpLoader checkHttpLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type")) {
            type = getArguments().getString("type");
        }
        if (getArguments() != null  && getArguments().containsKey("vhcle")) {
            vhcle = getArguments().getString("vhcle");
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
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        titlebarview1= getTitlebar();
        if(type!=null){
            if (Constents.CHECK_TYPE_FIRSTCHECK.equals(type)){
                titlebarview1.setTitle("初检");
            }else{
                titlebarview1.setTitle("复检");
            }
        }
        listview=  (ListView) rootView.findViewById(R.id.listview);
    }

    private void initData() {
        /*for (int i = 0; i <10 ; i++) {
            History_RecordBean bean=new History_RecordBean("张三","2018-1-27  10:30:50","初检",true);
            history_record_list.add(bean);
        }*/
        checkHttpLoader = new CheckHttpLoader();
        checkHttpLoader.getInspectioniHistory(vhcle).subscribe(new MySubscriber<InspectioniHistory>(getContext(), progressDialogIndicator){
            @Override
            public void onNext(InspectioniHistory inspectioniHistory) {
                if (inspectioniHistory == null || inspectioniHistory.inspectioniHistory == null || inspectioniHistory.inspectioniHistory.size() == 0) {
                    alert("暂无历史记录");
                } else {
                    history_record_list.addAll(inspectioniHistory.inspectioniHistory);
                }
            }
        });
    }

    private void setListen() {
        adapter=new History_RecordAdapter(getContext(),history_record_list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();

                startActivity(new Intent(getActivity(), HistoryRecordDetailActivity.class).putExtras(bundle));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
