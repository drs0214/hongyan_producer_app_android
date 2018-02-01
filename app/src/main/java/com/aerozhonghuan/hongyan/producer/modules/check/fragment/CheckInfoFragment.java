package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.BindActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HistoryRecordActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.StartCheckActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CarInfo;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.StartCheckStateBean;
import com.aerozhonghuan.hongyan.producer.modules.check.logic.CheckHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.aerozhonghuan.rxretrofitlibrary.RxApiManager;

import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/27 1:21
 * @des:
 */
public class CheckInfoFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    String type;
    Button bt_history,bt_start_check,bt_bind;
    TitleBarView titlebarview1;
    private String vhcle;
    private CheckHttpLoader checkHttpLoader;
    private ProgressDialogIndicator progressDialogIndicator;
    private static final String TAG = "CheckInfoFragment";
    private LinearLayout ll_content;
    private TextView tv_engineLockCtscDesc,tv_engineLockIsActive,tv_isbindcar,tv_vhcle,tv_vhvin,tv_ecuvin,tv_deviceno,tv_firstTestUsername,tv_firstTest,tv_secondTestUsername,tv_secondTest;

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
            rootView = inflater.inflate(R.layout.fragment_check_info, null);
            checkHttpLoader = new CheckHttpLoader();
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        ll_content = (LinearLayout)rootView.findViewById(R.id.ll_content);
        bt_history= (Button) rootView.findViewById(R.id.bt_history);
        bt_start_check= (Button) rootView.findViewById(R.id.bt_start_check);
        bt_bind= (Button) rootView.findViewById(R.id.bt_bind);
        tv_engineLockCtscDesc = (TextView)rootView.findViewById(R.id.tv_engineLockCtscDesc);
        tv_engineLockIsActive = (TextView)rootView.findViewById(R.id.tv_engineLockIsActive);
        tv_isbindcar = (TextView)rootView.findViewById(R.id.tv_isbindcar);
        tv_vhcle = (TextView)rootView.findViewById(R.id.tv_vhcle);
        tv_vhvin = (TextView)rootView.findViewById(R.id.tv_vhvin);
        tv_ecuvin = (TextView)rootView.findViewById(R.id.tv_ecuvin);
        tv_deviceno = (TextView)rootView.findViewById(R.id.tv_deviceno);
        tv_firstTestUsername = (TextView)rootView.findViewById(R.id.tv_firstTestUsername);
        tv_firstTest = (TextView)rootView.findViewById(R.id.tv_firstTest);
        tv_secondTestUsername = (TextView)rootView.findViewById(R.id.tv_secondTestUsername);
        tv_secondTest = (TextView)rootView.findViewById(R.id.tv_secondTest);
        titlebarview1 = getTitlebar();
        if(type!=null){
            if (Constents.CHECK_TYPE_FIRSTCHECK.equals(type)){
                titlebarview1.setTitle("初检");
                bt_start_check.setText("开始初检");
            }else{
                titlebarview1.setTitle("复检");
                bt_start_check.setText("开始复检");
            }
        }

    }

    private void initData() {
        Subscription subscription = checkHttpLoader.getCarInfo(vhcle).subscribe(new MySubscriber<CarInfo>(getContext(), progressDialogIndicator) {
            @Override
            public void onNext(CarInfo carInfo) {
                setView(carInfo);
            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    // 显示布局控件
    private void setView(CarInfo carInfo) {
        ll_content.setVisibility(View.VISIBLE);
        if (carInfo.vehicle != null && PermissionsManager.isShowDeviceBind()) {
            bt_bind.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(carInfo.vehicle.deviceId)) {
                bt_bind.setText("绑定");
                tv_isbindcar.setText("否");
            } else {
                bt_bind.setText("解绑");
                tv_isbindcar.setText("是");
            }
        }
        if (carInfo.vehicle != null) {
            tv_vhcle.setText(carInfo.vehicle.vhcle);
            tv_vhvin.setText(carInfo.vehicle.vhvin);
            tv_ecuvin.setText(carInfo.vehicle.ecuvin);
            tv_deviceno.setText(carInfo.vehicle.deviceNo);
            tv_firstTestUsername.setText(carInfo.vehicle.firstTestUsername);
            setTestResult(tv_firstTest,carInfo.vehicle.firstTest);
            tv_secondTestUsername.setText(carInfo.vehicle.secondTestUsername);
            setTestResult(tv_secondTest,carInfo.vehicle.secondTest);
        }
        if (carInfo.vehicleInfo != null) {
            tv_engineLockCtscDesc.setText(carInfo.vehicleInfo.engineLockCtscDesc);
            tv_engineLockIsActive.setText(carInfo.vehicleInfo.engineLockIsActive?"是":"否");
        }
    }

    private void setTestResult(TextView tv, int firstTest) {
        switch (firstTest) {
            case 0:
                tv.setText("未测试");
                break;
            case 1:
                tv.setText("未通过");
                break;
            case 2:
                tv.setText("通过");
                break;
            case 3:
                tv.setText("强制通过");
                break;

        }
    }

    private void setListen() {
        bt_history.setOnClickListener(this);
        bt_start_check.setOnClickListener(this);
        bt_bind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Bundle bundle = new Bundle();
        bundle.putString("type",type);
        bundle.putString("vhcle",vhcle);
        switch (v.getId()){
            case R.id.bt_history:
                startActivity(new Intent(getActivity(), HistoryRecordActivity.class).putExtras(bundle));
                break;
            case R.id.bt_start_check:
                // TODO: 2018/1/30 检查是否能够进行初检  start接口
                checkHttpLoader.startCheck(vhcle, type).subscribe(new MySubscriber<StartCheckStateBean>(getContext(),progressDialogIndicator){
                    @Override
                    public void onNext(StartCheckStateBean startCheckStateBean) {
                        if (startCheckStateBean.success) {
                            bundle.putString("inspectionId", startCheckStateBean.inspectionId);
                            bundle.putInt("interval", startCheckStateBean.interval == 0 ? 5000 : startCheckStateBean.interval);
                            startActivity(new Intent(getActivity(), StartCheckActivity.class).putExtras(bundle));
                        } else {
                            alert(startCheckStateBean.messsage);
                        }
                    }
                });

                break;
            case R.id.bt_bind:
                startActivity(new Intent(getActivity(), BindActivity.class).putExtras(bundle));
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxApiManager.get().cancel(TAG);
    }
}
