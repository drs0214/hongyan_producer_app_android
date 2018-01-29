package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.BindActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HistoryRecordActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.StartCheckActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CarInfo;
import com.aerozhonghuan.hongyan.producer.modules.check.logic.CheckHttpLoader;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type") && getArguments().containsKey("vhcle")) {
            type = getArguments().getString("type");
            vhcle = getArguments().getString("vhcle");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_check_info, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        bt_history= (Button) rootView.findViewById(R.id.bt_history);
        bt_start_check= (Button) rootView.findViewById(R.id.bt_start_check);
        bt_bind= (Button) rootView.findViewById(R.id.bt_bind);
        titlebarview1 = getTitlebar();
        if (!PermissionsManager.isShowDeviceBind()) {
            bt_bind.setVisibility(View.GONE);
        }
        if(type!=null){
            titlebarview1.setTitle(type);
            if ("初检".equals(type)){
                bt_start_check.setText("开始初检");
            }else{
                bt_start_check.setText("开始复检");
            }
        }

    }

    private void initData() {
        checkHttpLoader = new CheckHttpLoader();
        Subscription subscription = checkHttpLoader.getCarInfo(vhcle).subscribe(new MySubscriber<CarInfo>(getContext(), progressDialogIndicator) {
            @Override
            public void onNext(CarInfo carInfo) {
                LogUtil.d(TAG, carInfo.toString());
                setView();
            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    // 显示布局控件
    private void setView() {

    }

    private void setListen() {
        bt_history.setOnClickListener(this);
        bt_start_check.setOnClickListener(this);
        bt_bind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("type",type);
        bundle.putString("vhcle",vhcle);
        switch (v.getId()){
            case R.id.bt_history:
                startActivity(new Intent(getActivity(), HistoryRecordActivity.class).putExtras(bundle));
                break;
            case R.id.bt_start_check:
                startActivity(new Intent(getActivity(), StartCheckActivity .class).putExtras(bundle));
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
