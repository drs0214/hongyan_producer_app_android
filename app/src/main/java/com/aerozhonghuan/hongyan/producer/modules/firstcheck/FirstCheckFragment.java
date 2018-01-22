package com.aerozhonghuan.hongyan.producer.modules.firstcheck;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;

/**
 * Created by zhangyonghui on 2018/1/22.
 */

public class FirstCheckFragment extends TitlebarFragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_first_check,null);
        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
