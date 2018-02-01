package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;

/**
 * @author: drs
 * @time: 2018/1/27 2:56
 * @des:
 */
public class HistoryRecordDetailFragment extends TitlebarFragment{
    private View rootView;
    private LinearLayout ll_check_lockcar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2018/1/31 获取参数
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_historyrecord_detail, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void initView() {
        ll_check_lockcar = (LinearLayout) rootView.findViewById(R.id.ll_check_lockcar);
        if (PermissionsManager.isShowInspectionCheck()) {
            ll_check_lockcar.setVisibility(View.VISIBLE);
        }
        if (PermissionsManager.isShowInspectionForceCheck()) {
            ll_check_lockcar.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {

    }

    private void setListen() {

    }
}