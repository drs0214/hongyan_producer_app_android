package com.aerozhonghuan.hongyan.producer.modules.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.utils.AppUtil;
import com.aerozhonghuan.hongyan.producer.widget.CustomDialog;


/**
 * 描述:我的
 * 作者:zhangyonghui
 * 创建日期：2017/6/20 0020 on 上午 11:28
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG = "MineFragment";
    private View rootView;
    private Button btn_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_my, null);
            initView();
        }
        return rootView;
    }

    private void initView() {
        btn_out = (Button) rootView.findViewById(R.id.btn_out);
        btn_out.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }

    public String getAppVersionName() {
        return AppUtil.getAppVersionName(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_out:
                loginOut();
                break;
        }
    }

    private void loginOut() {
        final CustomDialog dialog = new CustomDialog(getContext(), "是否退出当前用户?", "退出", "取消");
        dialog.setOnDiaLogListener(new CustomDialog.OnDialogListener() {
            @Override
            public void dialogPositiveListener() {
                //注销
                UserInfoManager.logout(getContext());
            }

            @Override
            public void dialogNegativeListener() {

            }
        });
        dialog.showDialog();
    }
}
