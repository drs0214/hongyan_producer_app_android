package com.aerozhonghuan.hongyan.producer.modules.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.user.logic.LoginHttpLoader;
import com.aerozhonghuan.rxretrofitlibrary.ProgressSubscriber;

public class LoginActivity extends TitlebarActivity {

    private Button btn_login;
    private LoginHttpLoader loginHttpLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginHttpLoader = new LoginHttpLoader();
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginHttpLoader.getSession("apptest", "apptest")
                        .subscribe(new ProgressSubscriber<String>(){

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                            }

                            @Override
                            public void onNext(String s) {
                                // TODO: 2018/1/25 缓存cookie 并且更新header拦截器
                            }
                        });
            }
        });
    }
}
