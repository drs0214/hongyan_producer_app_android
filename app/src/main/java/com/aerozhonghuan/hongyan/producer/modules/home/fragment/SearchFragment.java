package com.aerozhonghuan.hongyan.producer.modules.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * Created by dell on 2017/10/21.
 */

public class SearchFragment extends BaseFragment {
    private View rootView;
    private TitleBarView titleBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_search, null);
            initView();
            initData();
        }
        return rootView;
    }

    private void initData() {

    }

    private void initView() {
        titleBar = (TitleBarView) rootView.findViewById(R.id.titlebarview1);
        titleBar.enableBackButton(false);
        titleBar.setTitle(getResources().getString(R.string.string_tab_search));
    }
}
