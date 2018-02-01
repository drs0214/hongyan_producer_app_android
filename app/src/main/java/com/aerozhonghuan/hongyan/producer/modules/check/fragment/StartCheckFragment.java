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

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.CheckActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CheckStatusBean;
import com.aerozhonghuan.hongyan.producer.modules.check.logic.CheckHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.aerozhonghuan.rxretrofitlibrary.HttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: drs
 * @time: 2018/1/27 2:56
 * @des:
 */
public class StartCheckFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    private String type, inspectionId;
    private TitleBarView titlebarview1;
    private Button bt_back,bt_end_check,bt_pass;
    private LinearLayout ll_check_lockcar;
    private int interval;
    // 请求参数集合
    private Map<String, String> params = new HashMap<>();
    private static final String TAG = "StartCheckFragment";
    private CheckHttpLoader checkHttpLoader;
    private ProgressDialogIndicator progressDialogIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type") && getArguments().containsKey("inspectionId") && getArguments().containsKey("interval")) {
            type = getArguments().getString("type");
            inspectionId = getArguments().getString("inspectionId");
            interval = getArguments().getInt("interval",5000);
            // 更改网络请求超时
            HttpManager.getInstance().setTimeOut(interval);
            LogUtil.d(TAG,"收到参数:检测记录id:"+inspectionId+"   请求间隔时长:"+interval);
            params.put("inspectionId", inspectionId);
        } else {
            alert("数据异常");
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_start_check, null);
            initView();
            initData();
        }
        return rootView;
    }

    private void initView() {
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        titlebarview1 = getTitlebar();
        ll_check_lockcar = (LinearLayout) rootView.findViewById(R.id.ll_check_lockcar);
        if (PermissionsManager.isShowInspectionCheck()) {
            ll_check_lockcar.setVisibility(View.VISIBLE);
        }
        if (PermissionsManager.isShowInspectionForceCheck()) {
            ll_check_lockcar.setVisibility(View.VISIBLE);
        }
        bt_back= (Button) rootView.findViewById(R.id.bt_back);
        bt_end_check= (Button) rootView.findViewById(R.id.bt_end_check);
        bt_pass= (Button) rootView.findViewById(R.id.bt_pass);
    }

    private void initData() {
        if (TextUtils.isEmpty(type)) {
            alert("数据异常");
            return;
        }
        if (Constents.CHECK_TYPE_FIRSTCHECK.equals(type)){
            titlebarview1.setTitle("初检");
            bt_back.setText("返回初检扫描首页");
            bt_end_check.setText("结束初检");
            bt_pass.setVisibility(View.GONE);
        }else{
            titlebarview1.setTitle("复检");
            bt_back.setText("返回复检扫描首页");
            bt_end_check.setText("结束复检");
            if (PermissionsManager.isShowInspectionForcepass()) {
                bt_pass.setVisibility(View.VISIBLE);
            }
        }
        bt_back.setOnClickListener(this);
        bt_end_check.setOnClickListener(this);
        bt_pass.setOnClickListener(this);
        checkHttpLoader = new CheckHttpLoader();
        requestLastStatus();
    }

    private void requestLastStatus() {
        checkHttpLoader.getLastStatus(params).subscribe(new MySubscriber<CheckStatusBean>(getContext(),progressDialogIndicator){
            @Override
            public void onNext(CheckStatusBean checkStatusBean) {
                LogUtil.d(TAG,checkStatusBean.toString());

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_back:
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                startActivity(new Intent(getActivity(), CheckActivity.class).putExtras(bundle));
                getActivity().finish();
                break;
            case R.id.bt_end_check:
                alert("结束"+type);
                break;
            case R.id.bt_pass:
                break;
        }

    }
}