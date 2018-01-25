package com.aerozhonghuan.hongyan.producer.modules.common;//package com.mapbar.android.obd.view.common;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.aerozhonghuan.foundation.base.SingleActivity;
import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.utils.EnvironmentInfoUtils;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends SingleActivity {
    private static final String TAG = "SplashActivity";
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 111;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        this.getWindow().setAttributes(params);
        setContentView(R.layout.splash_view);
        mHandler = new Handler();

//        hideNavigation();

        checkMyAppPermission();

    }

    private void initApp() {
        //打印环境变量信息
        if (BuildConfig.DEBUG) {
            new EnvironmentInfoUtils().print(this);
        }

        if (UserInfoManager.isUserAuthenticated()) {

        }
//        CustomServiceConfigurator.init(this);//初始化云信
//        LogUtil.d(TAG, "初始化云信 完成");

        onInitFirstView();
    }

    private void checkMyAppPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                alertNoPermission();

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                return;
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_PHONE_STATE
                        }, MY_PERMISSIONS_REQUEST_STORAGE);

                // MY_PERMISSIONS_REQUEST_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                return;
            }
        }
        initApp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initApp();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    alertNoPermission();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void alertNoPermission() {
        if (mHandler == null) return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setMessage("无法获得应用所需的权限，请重新检查权限后继续")
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //请求权限
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_STORAGE);

                            }
                        })
                        .setNegativeButton("离开", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false).create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
    }

    /**
     * 隐藏导航栏虚拟键盘
     */
    private void hideNavigation() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        FrameLayout content = (FrameLayout) view.findViewById(android.R.id.content);
        content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /**
     * 初始化
     */
    public void onInitFirstView() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserInfoManager.isUserAuthenticated()) {
                    goPage_MainActivity();
                    finish();
                } else {
                    goPage_Login();
                    finish();
                }
            }

        }, 1000);
    }

    private void goPage_Login() {
        Intent intent = ActivityDispatcher.getIntent_LoginOnLogout(getContext());
        startActivity(intent);
    }

    private void goPage_MainActivity() {
        Intent intent = ActivityDispatcher.getIntent_MainActivity(getContext());
        intent.putExtra("IS_FIRST", true);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("notify_intent")) {
            intent.putExtra("notify_intent", getIntent().getParcelableExtra("notify_intent"));
        }
        startActivity(intent);
        overridePendingTransition(0, 0);//阻止任何动画
    }

    @Override
    protected void onDestroy() {
        mHandler = null;
        super.onDestroy();
    }
}
