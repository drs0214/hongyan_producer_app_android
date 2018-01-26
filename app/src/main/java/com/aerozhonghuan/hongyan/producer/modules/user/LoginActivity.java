package com.aerozhonghuan.hongyan.producer.modules.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.Matchs;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.ActivityDispatcher;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.SessionBean;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.modules.user.logic.LoginHttpLoader;
import com.aerozhonghuan.hongyan.producer.utils.NetUtils;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.aerozhonghuan.rxretrofitlibrary.ApiException;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;

public class LoginActivity extends TitlebarActivity {

    private Button btn_login;
    private LoginHttpLoader loginHttpLoader;
    private TextView et_account;
    private TextView et_pwd;
    private static final String TAG = "LoginActivity";
    private TitleBarView titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginHttpLoader = new LoginHttpLoader();
        initView();

    }

    private void initView() {
        titleBar = (TitleBarView) findViewById(R.id.titlebarview1);
        titleBar.enableBackButton(false);
        titleBar.setTitle(getResources().getString(R.string.title_activity_login));
        et_account = (TextView)findViewById(R.id.et_account);
        et_pwd = (TextView)findViewById(R.id.et_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        boolean isConnected = NetUtils.isConnected(getApplicationContext());
        if (!isConnected) {
            alert("当前无网络连接");
            return;
        }
        String username = et_account.getText().toString();
        String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(username)) {
            alert("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            alert("请输入密码");
            return;
        }
        if (pwd.length() < 4 || pwd.length() > 20) {
            alert("请输入4-20位半角字符，建议数字、字母、符号组合");
            return;
        }
        if (!Matchs.matchPassword(pwd)) {
            alert("请输入6-20位半角字符，建议数字、字母、符号组合");
            return;
        }
        btn_login.setClickable(false);
        loginHttpLoader.getSession(username, pwd)
                .subscribe(new MySubscriber<SessionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        btn_login.setClickable(true);
                        LogUtil.d(TAG,"错误信息:"+e.message);
                    }

                    @Override
                    public void onNext(SessionBean sessionBean) {
                        String cookie = String.format("%s=%s",sessionBean.session.name,sessionBean.session.value);
                        UserInfoManager.saveCookieSession(cookie);
                        Intent intent = ActivityDispatcher.getIntent_MainActivity(getContext());
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
