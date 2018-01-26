package com.aerozhonghuan.hongyan.producer.modules.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.Constants;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.modules.common.AboutActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserBean;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoDao;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.QueryTaskNumAndScoreTotalEvent;
import com.aerozhonghuan.hongyan.producer.modules.user.beans.AccountUpdateEvent;
import com.aerozhonghuan.hongyan.producer.utils.AppUtil;
import com.aerozhonghuan.hongyan.producer.utils.NetUtils;
import com.aerozhonghuan.hongyan.producer.utils.SimpleSettings;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述:我的
 * 作者:zhangyonghui
 * 创建日期：2017/6/20 0020 on 上午 11:28
 */

public class MineFragment extends BaseFragment {
    private View rootView;
    private LayoutInflater mLayoutInflater;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_my, null);

        }
        return rootView;
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


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    private interface OnItemClick {
        void onItemClick(String title);
    }


}
