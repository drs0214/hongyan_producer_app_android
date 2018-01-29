package com.aerozhonghuan.hongyan.producer.modules.check;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.CheckInfoActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HandInputActivity;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.zh.drs.zxinglibrary.android.CaptureActivity;
import com.zh.drs.zxinglibrary.bean.ZxingConfig;
import com.zh.drs.zxinglibrary.common.Constant;

import static android.app.Activity.RESULT_OK;

/**
 * Created by zhangyonghui on 2018/1/22.
 * 扫描页面,默认红外扫描,可以选择摄像头和手动输入
 */

public class ScanFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    LinearLayout ll_camera_scan, ll_hand_input;
    EditText et_num;
    String type;
    private int REQUEST_CODE_SCAN = 111;
    private TitleBarView titlebar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type") ) {
            type = getArguments().getString("type");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_single_scan, null);
        }
        initView();
        initData();
        setListen();
        return rootView;
    }


    private void initView() {
        titlebar = getTitlebar();
        titlebar.setTitle(type);
        ll_camera_scan = (LinearLayout) rootView.findViewById(R.id.ll_camera_scan);
        ll_hand_input = (LinearLayout) rootView.findViewById(R.id.ll_hand_input);
        et_num= (EditText) rootView.findViewById(R.id.et_num);
        et_num.setInputType(InputType.TYPE_NULL);
        et_num.requestFocus();
    }

    private void initData() {

    }

    private void setListen() {
        ll_camera_scan.setOnClickListener(this);
        ll_hand_input.setOnClickListener(this);
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    jumpcheckinfo(s.toString());
                    et_num.setText("");
                }

            }
        });
    }

    private void jumpcheckinfo(String vhcle) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("vhcle", vhcle);
        startActivity(new Intent(getActivity(), CheckInfoActivity.class).putExtras(bundle));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_camera_scan:
                if (type != null && "初检".equals(type)) {
                    scanpermission();

                } else {
                    scanpermission();
                }
                break;
            case R.id.ll_hand_input:
                if (type != null && "初检".equals(type)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type","初检");
                    startActivity(new Intent(getActivity(), HandInputActivity.class).putExtras(bundle));

                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type","复检");
                    startActivity(new Intent(getContext(), HandInputActivity.class).putExtras(bundle));
                }
                break;
        }
    }

    private void scanpermission() {
        /**
         * android6.0运行时权限,检测
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0以上

            int checkPermission = getContext().checkSelfPermission(Manifest.permission.CAMERA);

            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);//后面的1为请求码
                return;
            } else {
                initCamera();//zxing二维码扫描，需要摄像头权限
            }
        } else {//6.0以下
            initCamera(); //zxing二维码扫描，需要摄像头权限

        }
    }
    private void initCamera() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);

                                        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                                        * 也可以不传这个参数
                                        * 不传的话  默认都为默认不震动  其他都为true
                                        * */

        ZxingConfig config = new ZxingConfig();
        config.setPlayBeep(true);
        config.setShake(true);
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);

        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {//请求码

            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//允许
                    // Permission Granted
                    initCamera();//zxing二维码扫描，需要摄像头权限
                } else {//禁止
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                et_num.setText(content);
                alert(content);
                jumpcheckinfo(content);
                //                result.setText("扫描结果为：" + content);
            }
        }
    }
}
