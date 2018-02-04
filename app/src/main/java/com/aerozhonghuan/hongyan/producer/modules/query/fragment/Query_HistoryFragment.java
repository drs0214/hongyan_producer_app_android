package com.aerozhonghuan.hongyan.producer.modules.query.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.query.activity.Query_Transport_Scan_HistoryActivity;
import com.aerozhonghuan.hongyan.producer.modules.query.adapter.QueryResultAdapter;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.OperationTypeBean;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.Query_Constans;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.Query_ResultBean;
import com.aerozhonghuan.hongyan.producer.modules.query.logic.QueryHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.query.view.OperationTime;
import com.aerozhonghuan.hongyan.producer.modules.query.view.OperationType;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/2/3 13:31
 * @des:
 */
public class Query_HistoryFragment extends TitlebarFragment implements View.OnClickListener {

    private View rootView;
    private OperationType mOperationType;
    private OperationTime mOperationTime;
    LinearLayout ll_operation_type, ll_operation_time;
    TextView tv_operation_time, tv_operation_type;
    View view_single, view_many;
    View line;
    ArrayList<Query_ResultBean> listData = new ArrayList<>();
    QueryResultAdapter adapter;
    ListView listview;
    QueryHttpLoader queryHttpLoader = new QueryHttpLoader();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_query_transport_scan_history, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        line = rootView.findViewById(R.id.line);
        ll_operation_time = (LinearLayout) rootView.findViewById(R.id.ll_operation_time);
        ll_operation_type = (LinearLayout) rootView.findViewById(R.id.ll_operation_type);
        tv_operation_time = (TextView) rootView.findViewById(R.id.tv_operation_time);
        tv_operation_type = (TextView) rootView.findViewById(R.id.tv_operation_type);
        view_single = (View) rootView.findViewById(R.id.view_single);
        view_many = (View) rootView.findViewById(R.id.view_many);
        listview = (ListView) rootView.findViewById(R.id.listview);

        adapter = new QueryResultAdapter(getContext(), listData);
        listview.setAdapter(adapter);
    }

    private void initData() {
        loadquery();
    }

    private void loadquery() {
        Map<String, String> querymap = new HashMap();
        querymap.put("action", Query_Constans.type);
        querymap.put("start", Query_Constans.start_time);
        querymap.put("end", Query_Constans.end_time);
        Subscription subscription = queryHttpLoader.query(querymap).subscribe(new MySubscriber<List<Query_ResultBean>>(getContext()) {
            @Override
            public void onNext(List<Query_ResultBean> query_resultBeans) {
                if (!query_resultBeans.isEmpty()) {
                    if (listData != null) {
                        listData.clear();
                    }
                    listData.addAll(query_resultBeans);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setListen() {
        ll_operation_time.setOnClickListener(this);
        ll_operation_type.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //操作类型筛选
            case R.id.ll_operation_type:
                Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
                Subscription subscription = transport_scanHttpLoader.actions().subscribe(new MySubscriber<List<TransportScanDetailBean.ActionsBean>>(getContext()) {
                    @Override
                    public void onNext(List<TransportScanDetailBean.ActionsBean> reslistdata) {
                        if (!reslistdata.isEmpty()) {
                            if (mOperationType == null) {
                                mOperationType = new OperationType(getActivity(), reslistdata, tv_operation_type);//去重
                            }
                            if (mOperationType != null && !mOperationType.isShowing()) {
                                mOperationType.showoperationtypePopup(line, tv_operation_type);
                            } else {
                                mOperationType.dismiss();
                            }
                            mOperationType.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    tv_operation_type.setTextColor(getContext().getResources().getColor(R.color.text_tj));
                                    if (Query_Constans.isok_type) {
                                        Query_Constans.isok_type = false;
                                        loadquery();
                                    }

                                    Log.e("", Query_Constans.type);
                                }
                            });
                        }
                    }
                });

                break;
            //操作时间筛选
            case R.id.ll_operation_time:
                // 价格点击监听
                if (mOperationTime==null){
                    mOperationTime = new OperationTime(getActivity(), tv_operation_time);
                }
                tv_operation_time.setTextColor(getContext().getResources().getColor(R.color.chujian_blue));
                mOperationTime.showoperationtimePopup(line);
                mOperationTime.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        tv_operation_time.setTextColor(getContext().getResources().getColor(R.color.text_tj));
                        if (Query_Constans.isok_time) {
                            Query_Constans.isok_time = false;
                            loadquery();
                        }
                    }
                });
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Query_Constans.isok_type=false;
        Query_Constans.isok_time=false;
        Query_Constans.type="";
        Query_Constans.start_time="";
        Query_Constans.end_time="";
    }
}
