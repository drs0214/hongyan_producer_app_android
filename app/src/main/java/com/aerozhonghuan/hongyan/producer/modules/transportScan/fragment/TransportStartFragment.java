package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.Constants;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.activity.TransportInfoActivity;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.DoActionBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.OperationResultBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.Transport_Scan_OrderBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;
import com.aerozhonghuan.hongyan.producer.utils.TelephonyUtils;
import com.aerozhonghuan.hongyan.producer.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/29 15:26
 * @des:
 */
public class TransportStartFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    private TextView tv_car_in_num, tv_terminal_num, tv_plan_startcar_time, tv_start_address, tv_destination;
    Spinner sp_number;
    Button bt_ok;
    private List<String> list;
    String vhcle, action;
    ArrayAdapter<String> adapter;

    List<Transport_Scan_OrderBean> listdata = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("vhcle")) {
            vhcle = getArguments().getString("vhcle");
        }
        if (getArguments() != null && getArguments().containsKey("action")) {
            action = getArguments().getString("action");
        }
    }

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
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_number.setAdapter(adapter);
        Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
        Subscription subscription = transport_scanHttpLoader.transportOrders(vhcle).subscribe(new MySubscriber<List<Transport_Scan_OrderBean>>(getContext()) {
            @Override
            public void onNext(List<Transport_Scan_OrderBean> reslistdata) {
                Log.e("drs", "");
                if (!reslistdata.isEmpty()) {
                    for (int i = 0; i < reslistdata.size(); i++) {
                        String orderNo = reslistdata.get(i).getOrderNo();
                        list.add(orderNo);
                        adapter.notifyDataSetChanged();
                    }
                    listdata.addAll(reslistdata);
                }

            }
        });

    }

    private void initView() {
        sp_number = (Spinner) rootView.findViewById(R.id.sp_number);
        bt_ok = (Button) rootView.findViewById(R.id.bt_ok);
        tv_car_in_num = (TextView) rootView.findViewById(R.id.tv_car_in_num);
        tv_terminal_num = (TextView) rootView.findViewById(R.id.tv_terminal_num);
        tv_plan_startcar_time = (TextView) rootView.findViewById(R.id.tv_plan_startcar_time);
        tv_start_address = (TextView) rootView.findViewById(R.id.tv_start_address);
        tv_destination = (TextView) rootView.findViewById(R.id.tv_destination);

    }

    private void setListen() {
        bt_ok.setOnClickListener(this);
        sp_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //                private TextView tv_car_in_num,tv_terminal_num,tv_plan_startcar_time,tv_start_address,tv_destination;
                Transport_Scan_OrderBean transport_scan_orderBean = listdata.get(position);
                tv_car_in_num.setText(transport_scan_orderBean.getVhcle() == null ? "" : transport_scan_orderBean.getVhcle());
                tv_terminal_num.setText(transport_scan_orderBean.getDeviceNo() == null ? "" : transport_scan_orderBean.getDeviceNo());
                tv_plan_startcar_time.setText(transport_scan_orderBean.getPlanShipTime() == null ? "" : transport_scan_orderBean.getPlanShipTime());
                tv_start_address.setText(transport_scan_orderBean.getDeparture() == null ? "" : transport_scan_orderBean.getDeparture());
                tv_destination.setText(transport_scan_orderBean.getDestination() == null ? "" : transport_scan_orderBean.getDestination());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok:
                Map<String, String> paramsmap = new HashMap<>();
                paramsmap.put("vhcle", vhcle);
                paramsmap.put("action", action);
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
                Subscription subscription = transport_scanHttpLoader.doAction(paramsmap).subscribe(new MySubscriber<DoActionBean>(getContext()) {
                    @Override
                    public void onNext(DoActionBean doActionBean) {
                        if (doActionBean.isSuccess()) {
                            alert("操作成功");
//                            Bundle bundle=new Bundle();
//                            bundle.putString("issuccess","1");
//                            bundle.putString("vhcle",vhcle);
//                            bundle.putString("msg","操作成功");
//                            startActivity(new Intent(getActivity(), TransportInfoActivity.class).putExtras(bundle));
                            EventBusManager.post(new OperationResultBean(true,"操作成功"));
                            getActivity().finish();
                        } else {
                            alert("操作失败");
                            alert(doActionBean.getMessage());
//                            Bundle bundle=new Bundle();
//                            bundle.putString("issuccess","2");
//                            bundle.putString("vhcle",vhcle);
//                            bundle.putString("msg",doActionBean.getMessage());
//                            startActivity(new Intent(getActivity(), TransportInfoActivity.class).putExtras(bundle));
                            EventBusManager.post(new OperationResultBean(false,"操作失败"));
                            getActivity().finish();
                        }
                    }
                });
                break;
        }
    }
}
