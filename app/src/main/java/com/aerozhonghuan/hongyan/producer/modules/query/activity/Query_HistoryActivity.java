package com.aerozhonghuan.hongyan.producer.modules.query.activity;

import android.os.Bundle;

import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.query.fragment.Query_HistoryFragment;

/**
 * @author: drs
 * @time: 2018/2/3 13:30
 * @des:
 */
public class Query_HistoryActivity extends TitlebarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Query_HistoryFragment fragment = new Query_HistoryFragment();
                showFragment(fragment, false);
        }
    }
}
