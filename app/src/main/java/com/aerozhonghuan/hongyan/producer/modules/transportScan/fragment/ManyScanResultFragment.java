package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

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
import com.aerozhonghuan.hongyan.producer.framework.base.Constants;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanResultAdapter;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.DoActionBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.Transport_Scan_OrderBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.view.OperationResultPop;
import com.aerozhonghuan.hongyan.producer.utils.TelephonyUtils;
import com.aerozhonghuan.hongyan.producer.utils.TimeUtil;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/26 15:57
 * @des:
 */
public class ManyScanResultFragment extends TitlebarFragment {
    private View rootView;
    ListView listview;
    ArrayList<DoActionBean> listData = new ArrayList<DoActionBean>();
    ManyScanResultAdapter adapter;
    EditText et_num;
    String action;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("action")) {
            action = getArguments().getString("action");
        }
    }

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
        adapter=new ManyScanResultAdapter(getContext(),listData);
        listview.setAdapter(adapter);
    }

    private void initView() {
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        listview = (ListView) rootView.findViewById(R.id.listview);
        et_num = (EditText) rootView.findViewById(R.id.et_num);
        et_num.setInputType(InputType.TYPE_NULL);
        et_num.requestFocus();
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
                if (!TextUtils.isEmpty(s.toString())) {
                    doAction(s.toString());
                    et_num.setText("");
                }

            }
        });
    }

    Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
    private ProgressDialogIndicator progressDialogIndicator;

    private void doAction(String vhcle) {
        Map<String, String> paramsmap = new HashMap<>();
        paramsmap.put("vhcle", vhcle);
        if (action != null) {
            paramsmap.put("action", action);
        }
        if (MyApplication.mlocation != null) {
            paramsmap.put("lon", MyApplication.mlocation.getLongitude() + "");
            paramsmap.put("lat", MyApplication.mlocation.getLatitude() + "");
            paramsmap.put("coorType", Constants.COORTYPE);
            paramsmap.put("locationTime", TimeUtil.getDate_yyyyMMddTHHmmss(MyApplication.mlocation.getTime()));
            String locationType = "";
            if (MyApplication.mlocation.getLocationType() == 1) {
                locationType = "GPS";
            } else if (MyApplication.mlocation.getLocationType() == 5) {
                locationType = "Wifi";
            } else if (MyApplication.mlocation.getLocationType() == 6) {
                locationType = "基站";
            } else {
                locationType = "前次离线缓存";
            }
            paramsmap.put("locationType", locationType);
            paramsmap.put("radius", MyApplication.mlocation.getAccuracy() + "");
        }
        paramsmap.put("operator", TelephonyUtils.getOperator_letter(getContext()));
        Subscription subscription = transport_scanHttpLoader.doAction(paramsmap).subscribe(new MySubscriber<DoActionBean>(getContext(), progressDialogIndicator) {
            @Override
            public void onNext(DoActionBean doActionBean) {
                listData.add(doActionBean);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
