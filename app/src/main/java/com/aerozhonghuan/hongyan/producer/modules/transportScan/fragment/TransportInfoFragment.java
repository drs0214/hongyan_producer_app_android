package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.Constants;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.activity.TransportStartActivity;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanAdapter;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.DoActionBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.view.OperationResultPop;
import com.aerozhonghuan.hongyan.producer.utils.TelephonyUtils;
import com.aerozhonghuan.hongyan.producer.utils.TimeUtil;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.zh.location.drs.amaplibrary.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/27 1:39
 * @des:
 */
public class TransportInfoFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    LinearLayout root_view;
    String type, vhcle;
    GridView gridview;
    ArrayList<TransportScanDetailBean.ActionsBean> manyscanlist = new ArrayList<TransportScanDetailBean.ActionsBean>();
    TextView tv_chassis_number, tv_car_in_num, tv_terminal_num, tv_last_time_operation_type, tv_last_time_operation_time, tv_last_time_operation_persion, tv_ecuvin;
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
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        root_view = (LinearLayout) rootView.findViewById(R.id.root_view);
        gridview = (GridView) rootView.findViewById(R.id.gridview);
        tv_chassis_number = (TextView) rootView.findViewById(R.id.tv_chassis_number);
        tv_car_in_num = (TextView) rootView.findViewById(R.id.tv_car_in_num);
        tv_terminal_num = (TextView) rootView.findViewById(R.id.tv_terminal_num);
        tv_last_time_operation_type = (TextView) rootView.findViewById(R.id.tv_last_time_operation_type);
        tv_last_time_operation_time = (TextView) rootView.findViewById(R.id.tv_last_time_operation_time);
        tv_last_time_operation_persion = (TextView) rootView.findViewById(R.id.tv_last_time_operation_persion);
        tv_ecuvin = (TextView) rootView.findViewById(R.id.tv_ecuvin);


    }
    private ProgressDialogIndicator progressDialogIndicator;
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
        Subscription subscription = transport_scanHttpLoader.getVehicleAndActions(vhcle).subscribe(new MySubscriber<TransportScanDetailBean>(getContext(),progressDialogIndicator) {
            @Override
            public void onNext(TransportScanDetailBean transportScanDetailBean) {
                vehicleBean = transportScanDetailBean.getVehicle();
                //                  TextView tv_chassis_number,tv_car_in_num,tv_terminal_num,tv_last_time_operation_type,tv_last_time_operation_time,tv_last_time_operation_persion,tv_ecuvin;
                tv_chassis_number.setText(vehicleBean.vhvin == null ? "" : vehicleBean.vhvin);
                tv_car_in_num.setText(vehicleBean.vhcle == null ? "" : vehicleBean.vhcle);
                tv_terminal_num.setText(vehicleBean.deviceNo == null ? "" : vehicleBean.deviceNo);
                tv_last_time_operation_type.setText(vehicleBean.statusText == null ? "" : vehicleBean.statusText);
                tv_last_time_operation_time.setText(vehicleBean.actionDate == null ? "" : vehicleBean.actionDate);
                tv_last_time_operation_persion.setText(vehicleBean.createUsername == null ? "" : vehicleBean.createUsername);
                tv_ecuvin.setText(vehicleBean.ecuvin == null ? "" : vehicleBean.ecuvin);
                actionslist = transportScanDetailBean.getActions();
                if (!actionslist.isEmpty()) {
                    manyscanlist.addAll(actionslist);
                    adapter.notifyDataSetChanged();
                }
                TransportScanDetailBean.ActionsBean bean=new TransportScanDetailBean.ActionsBean();
                bean.setLabel("撤销");
                bean.setEnabled(true);
                bean.setName("撤销");
                manyscanlist.add(bean);

            }
        });
    }

    TransportScanDetailBean.VehicleBean vehicleBean;
    List<TransportScanDetailBean.ActionsBean> actionslist;
    Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
    private void setListen() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!manyscanlist.isEmpty()&&"撤销".equals(manyscanlist.get(position).getLabel())){
                    if(vehicleBean.getVhcle()!=null){
                        undoAction();
                    }

                }else{
                    doAction(position);
                }

            }
        });
    }

    private void doAction(int position) {
        TransportScanDetailBean.ActionsBean bean = manyscanlist.get(position);
        if (bean != null && bean.getName() != null && "da:tp:start".equals(bean.getName())) {
            Bundle bundle=new Bundle();
            bundle.putString("vhcle",vehicleBean.getVhcle());
            bundle.putString("action", "da:tp:start");
            startActivity(new Intent(getContext(), TransportStartActivity.class),bundle);
        } else {

            Map<String, String> paramsmap = new HashMap<>();
            paramsmap.put("vhcle", vehicleBean.getVhcle());
            if (!actionslist.isEmpty()) {
                paramsmap.put("action", actionslist.get(position).getName());
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
                paramsmap.put("radius", MyApplication.mlocation.getAccuracy()+"");
            }
            paramsmap.put("operator", TelephonyUtils.getOperator_letter(getContext()));
            Subscription subscription = transport_scanHttpLoader.doAction(paramsmap).subscribe(new MySubscriber<DoActionBean>(getContext(),progressDialogIndicator) {
                @Override
                public void onNext(DoActionBean doActionBean) {
                    String result="";
                    if (doActionBean.isSuccess()) {
                        alert("操作成功");
                        result="操作成功";
                        OperationResultPop pop=new OperationResultPop(getActivity(),result);
                        pop.showAtDropDownCenter(root_view);

                    } else {
                        alert("操作失败");
                        result="操作失败";
                        alert(doActionBean.getMessage());
                        OperationResultPop pop=new OperationResultPop(getActivity(),result);
                        pop.showpop_center(root_view);
                    }
                }
            });
        }
    }

    private void undoAction() {
        Subscription subscription = transport_scanHttpLoader.undoAction(vehicleBean.getVhcle()).subscribe(new MySubscriber<DoActionBean>(getContext(),progressDialogIndicator) {
            @Override
            public void onNext(DoActionBean doActionBean) {
                String result="";
                if (doActionBean.isSuccess()) {
                    alert("操作成功");
                    result="操作成功";
                    OperationResultPop pop=new OperationResultPop(getActivity(),result);
                    pop.showAtDropDownCenter(root_view);

                } else {
                    alert("操作失败");
                    result="操作失败";
                    alert(doActionBean.getMessage());
                    OperationResultPop pop=new OperationResultPop(getActivity(),result);
                    pop.showpop_center(root_view);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
