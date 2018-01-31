package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.activity.TransportInfoActivity;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanResultAdapter;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanResultBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.Transport_Scan_OrderBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/26 15:57
 * @des:
 */
public class ManyScanResultFragment extends TitlebarFragment {
    private View rootView;
    ListView listview;
    ArrayList<Transport_Scan_OrderBean> manyscanresultlist=new ArrayList<Transport_Scan_OrderBean>();
    ManyScanResultAdapter adapter;
    EditText et_num;
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
        Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
        Subscription subscription = transport_scanHttpLoader.transportOrders("0070231766").subscribe(new MySubscriber<List<Transport_Scan_OrderBean>>(getContext()) {
            @Override
            public void onNext(List<Transport_Scan_OrderBean> reslistdata) {
                Log.e("drs","");
                if(!reslistdata.isEmpty()){
                    //                                manyscanlist.addAll(reslistdata);
                    //                                adapter.notifyDataSetChanged();
                }

            }
        });

    }

    private void initView() {
        et_num= (EditText) rootView.findViewById(R.id.et_num);
        et_num.setInputType(InputType.TYPE_NULL);
        et_num.requestFocus();
       /* for (int i = 0; i < 10; i++) {
            ManyScanResultBean manyScanResultBean=new ManyScanResultBean("05595949","HD54548787","交付","成功","操作不匹配");
            manyscanresultlist.add(manyScanResultBean);
        }*/


    }
    private void setListen() {

        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    et_num.setText("");
                   /* ManyScanResultBean manyScanResultBean=new ManyScanResultBean("05595949","HD54548787","交付","成功","操作不匹配");
                    manyscanresultlist.add(manyScanResultBean);
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();
                    }*/
                    Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
                    Subscription subscription = transport_scanHttpLoader.transportOrders("0070231766").subscribe(new MySubscriber<List<Transport_Scan_OrderBean>>(getContext()) {
                        @Override
                        public void onNext(List<Transport_Scan_OrderBean> reslistdata) {
                            Log.e("drs","");
                            if(!reslistdata.isEmpty()){
//                                manyscanlist.addAll(reslistdata);
//                                adapter.notifyDataSetChanged();
                            }

                        }
                    });

                }

            }
        });
//        adapter=new ManyScanResultAdapter(getContext(),manyscanresultlist);
//        listview.setAdapter(adapter);
    }
}
