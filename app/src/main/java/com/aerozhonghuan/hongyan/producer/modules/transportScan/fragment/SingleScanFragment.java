package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.check.activity.HandInputActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.activity.TransportInfoActivity;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.zh.drs.zxinglibrary.android.CaptureActivity;
import com.zh.drs.zxinglibrary.bean.ZxingConfig;
import com.zh.drs.zxinglibrary.common.Constant;

import static android.app.Activity.RESULT_OK;

/**
 * @author: drs
 * @time: 2018/1/26 13:21
 * @des:
 */
public class SingleScanFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private TitleBarView titleBar;
    EditText et_num;
    LinearLayout ll_hand_input, ll_camera_scan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_single_scan, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void setListen() {
        ll_hand_input.setOnClickListener(this);
        ll_camera_scan.setOnClickListener(this);
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
                    startActivity(new Intent(getActivity(), TransportInfoActivity.class));
                    et_num.setText("");
                }

            }
        });
    }

    private void initData() {

    }

    private void initView() {
        et_num = (EditText) rootView.findViewById(R.id.et_num);
        ll_hand_input = (LinearLayout) rootView.findViewById(R.id.ll_hand_input);
        ll_camera_scan = (LinearLayout) rootView.findViewById(R.id.ll_camera_scan);
        et_num.setInputType(InputType.TYPE_NULL);
        et_num.requestFocus();
        if (PermissionsManager.isShowTransportInputScan()) {
            ll_hand_input.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_hand_input:
                Bundle bundle = new Bundle();
                bundle.putString("hand_input_type", Constents.HAND_INPUT_TYPE);//1运输扫描
                startActivity(new Intent(getActivity(), HandInputActivity.class).putExtras(bundle));
                break;
            case R.id.ll_camera_scan:
                scanpermission();
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

    private int REQUEST_CODE_SCAN = 111;

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
                Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
                Log.e("drs","二维码："+content);
                Bundle bundle=new Bundle();
                bundle.putString("vhcle",content);
                startActivity(new Intent(getActivity(), TransportInfoActivity.class).putExtras(bundle));
            }
        }
    }
}
