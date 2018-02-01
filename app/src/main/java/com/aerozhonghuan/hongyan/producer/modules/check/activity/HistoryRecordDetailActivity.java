package com.aerozhonghuan.hongyan.producer.modules.check.activity;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.fragment.HistoryRecordDetailFragment;

/**
 * @author: drs
 * @time: 2018/1/27 2:05
 * @des:
 */
public class HistoryRecordDetailActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            HistoryRecordDetailFragment fragment = new HistoryRecordDetailFragment();
            if (getIntent() != null && getIntent().getExtras() != null) {
                showFragment(fragment, false, new Bundle(getIntent().getExtras()));
            }
        }
    }
}
