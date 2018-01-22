package com.aerozhonghuan.hongyan.producer.modules.firstcheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;

public class FirstCheckActivity extends TitlebarActivity {

    private FirstCheckFragment firstCheckFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            firstCheckFragment = new FirstCheckFragment();
            showFragment(firstCheckFragment, false);
        }

    }
}
