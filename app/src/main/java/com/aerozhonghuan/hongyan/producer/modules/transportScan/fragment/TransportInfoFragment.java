package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.activity.TransportStartActivity;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanAdapter;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/27 1:39
 * @des:
 */
public class TransportInfoFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    String type, vhcle;
    TitleBarView titlebarview1;
    GridView gridview;
    ArrayList<TransportScanDetailBean.ActionsBean> manyscanlist = new ArrayList<TransportScanDetailBean.ActionsBean>();
    TextView tv_chassis_number,tv_car_in_num,tv_terminal_num,tv_last_time_operation_type,tv_last_time_operation_time,tv_last_time_operation_persion,tv_ecuvin;
    ManyScanAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type")) {
            type = getArguments().getString("type");
        }
        if (getArguments() != null && getArguments().containsKey("vhcle")) {
            vhcle = getArguments().getString("vhcle");
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
        titlebarview1 = (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        titlebarview1.setTitle("运输扫描");
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        tv_chassis_number = (TextView) rootView.findViewById(R.id.tv_chassis_number);
        tv_car_in_num = (TextView) rootView.findViewById(R.id.tv_car_in_num);
        tv_terminal_num = (TextView) rootView.findViewById(R.id.tv_terminal_num);
        tv_last_time_operation_type = (TextView) rootView.findViewById(R.id.tv_last_time_operation_type);
        tv_last_time_operation_time = (TextView) rootView.findViewById(R.id.tv_last_time_operation_time);
        tv_last_time_operation_persion = (TextView) rootView.findViewById(R.id.tv_last_time_operation_persion);
        tv_ecuvin = (TextView) rootView.findViewById(R.id.tv_ecuvin);



    }

    private void initData() {
        adapter = new ManyScanAdapter(getContext(), manyscanlist);
        gridview.setAdapter(adapter);
//        TransportScanDetailBean.ActionsBean bean = new TransportScanDetailBean.ActionsBean();
//        bean.setName("下线");
//        manyscanlist.add(bean);
//
//        bean = new ActionsBean();
//        bean.setName("质检");
//        manyscanlist.add(bean);
//
//        bean = new ActionsBean();
//        bean.setName("入库");
//        manyscanlist.add(bean);
//        bean = new ActionsBean();
//        bean.setName("运输开始");
//        manyscanlist.add(bean);
//
//        bean = new ActionsBean();
//        bean.setName("运输结束");
//        manyscanlist.add(bean);
//
//        bean = new ActionsBean();
//        bean.setName("改装开始");
//        manyscanlist.add(bean);
//        bean = new ActionsBean();
//        bean.setName("改装开始");
//        manyscanlist.add(bean);
//
//        bean = new ActionsBean();
//        bean.setName("交付");
//        manyscanlist.add(bean);
//
//        bean = new ActionsBean();
//        bean.setName("盘点");
//        manyscanlist.add(bean);

        Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
        Subscription subscription = transport_scanHttpLoader.getVehicleAndActions(vhcle).subscribe(new MySubscriber<TransportScanDetailBean>(getContext()) {
            @Override
            public void onNext(TransportScanDetailBean transportScanDetailBean) {
                TransportScanDetailBean.VehicleBean vehicleBean = transportScanDetailBean.getVehicle();
//                  TextView tv_chassis_number,tv_car_in_num,tv_terminal_num,tv_last_time_operation_type,tv_last_time_operation_time,tv_last_time_operation_persion,tv_ecuvin;
                tv_chassis_number.setText(vehicleBean.vhvin);
                tv_car_in_num.setText(vehicleBean.vhcle);
                tv_terminal_num.setText(vehicleBean.deviceNo);
                tv_last_time_operation_type.setText(vehicleBean.statusText);
                tv_last_time_operation_time.setText(vehicleBean.actionDate);
                tv_last_time_operation_persion.setText(vehicleBean.createUsername);
                tv_ecuvin.setText(vehicleBean.ecuvin);
                List<TransportScanDetailBean.ActionsBean> actionslist = transportScanDetailBean.getActions();
                if(!actionslist.isEmpty()){
                    manyscanlist.addAll(actionslist);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    private void setListen() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransportScanDetailBean.ActionsBean bean = manyscanlist.get(position);
                if (bean != null && bean.getLabel() != null && "运输开始".equals(bean.getLabel())) {
                    startActivity(new Intent(getContext(), TransportStartActivity.class));
                } else {
                    alert(manyscanlist.get(position).getLabel());
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
