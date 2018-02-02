package com.aerozhonghuan.hongyan.producer.modules.check.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarFragment;
import com.aerozhonghuan.hongyan.producer.modules.check.CheckActivity;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.CheckStatusBean;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.EngineLockBean;
import com.aerozhonghuan.hongyan.producer.modules.check.logic.CheckHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.aerozhonghuan.rxretrofitlibrary.ApiException;
import com.aerozhonghuan.rxretrofitlibrary.HttpManager;
import com.aerozhonghuan.rxretrofitlibrary.RxApiManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/27 2:56
 * @des:
 */
public class StartCheckFragment extends TitlebarFragment implements View.OnClickListener {
    private View rootView;
    private String type, inspectionId;
    private TitleBarView titlebarview1;
    private Button btn_backscan,btn_finishcheck,btn_forcepass,btn_lock_open,btn_lock_close,btn_lock_lockcar,btn_lock_unlockcar;
    private ImageView img_result,img_lock_open,img_lock_close,img_lock_lockcar,img_lock_unlockcar,img_switch_brake,img_switch_cardoor,img_switch_leftlight, img_switch_rightlight;
    private Map<Integer,Button> lockButtons = new HashMap<>();
    private List<ImageView> lockImageViews = new ArrayList<>();
    private List<ImageView> switchImageViews = new ArrayList<>();
    private LinearLayout ll_check_lockcar;
    private int interval;
    // 请求参数集合
//    private Map<String, String> params = new HashMap<>();
    private List<Integer> cids = new ArrayList<>();
    private static final String TAG = "StartCheckFragment";
    private CheckHttpLoader checkHttpLoader;
    private ProgressDialogIndicator progressDialogIndicator;
    private boolean isCheckFinish;
    private String forceReason;
    private ProgressBar pb_checking;
    private TextView tv_result;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("type") && getArguments().containsKey("inspectionId") && getArguments().containsKey("interval")) {
            type = getArguments().getString("type");
            inspectionId = getArguments().getString("inspectionId");
            interval = getArguments().getInt("interval",5000);
            // 更改网络请求超时
            HttpManager.getInstance().setTimeOut(interval);
            LogUtil.d(TAG,"收到参数:检测记录id:"+inspectionId+"   请求间隔时长:"+interval);
        } else {
            alert("数据异常");
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_start_check, null);
            initView();
            initData();
        }
        return rootView;
    }

    private void initView() {
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        titlebarview1 = getTitlebar();
        ll_check_lockcar = (LinearLayout) rootView.findViewById(R.id.ll_check_lockcar);
        if (PermissionsManager.isShowInspectionCheck()) {
            ll_check_lockcar.setVisibility(View.VISIBLE);
        }
        if (PermissionsManager.isShowInspectionForceCheck()) {
            ll_check_lockcar.setVisibility(View.VISIBLE);
        }
        btn_backscan= (Button) rootView.findViewById(R.id.btn_backscan);
        btn_finishcheck= (Button) rootView.findViewById(R.id.btn_finishcheck);
        btn_forcepass= (Button) rootView.findViewById(R.id.btn_forcepass);

        if (ll_check_lockcar.getVisibility() == View.VISIBLE) {
            btn_lock_open= (Button) rootView.findViewById(R.id.btn_lock_open);
            btn_lock_close= (Button) rootView.findViewById(R.id.btn_lock_close);
            btn_lock_lockcar= (Button) rootView.findViewById(R.id.btn_lock_lockcar);
            btn_lock_unlockcar= (Button) rootView.findViewById(R.id.btn_lock_unlockcar);
            img_lock_open= (ImageView) rootView.findViewById(R.id.img_lock_open);
            img_lock_close= (ImageView) rootView.findViewById(R.id.img_lock_close);
            img_lock_lockcar= (ImageView) rootView.findViewById(R.id.img_lock_lockcar);
            img_lock_unlockcar= (ImageView) rootView.findViewById(R.id.img_lock_unlockcar);
            lockButtons.put(4, btn_lock_open);
            lockButtons.put(1,btn_lock_close);
            lockButtons.put(2,btn_lock_lockcar);
            lockButtons.put(3,btn_lock_unlockcar);
            lockImageViews.add(img_lock_open);
            lockImageViews.add(img_lock_close);
            lockImageViews.add(img_lock_lockcar);
            lockImageViews.add(img_lock_unlockcar);
        }

        img_switch_brake= (ImageView) rootView.findViewById(R.id.img_switch_brake);
        img_switch_cardoor= (ImageView) rootView.findViewById(R.id.img_switch_cardoor);
        img_switch_leftlight= (ImageView) rootView.findViewById(R.id.img_switch_leftlight);
        img_switch_rightlight= (ImageView) rootView.findViewById(R.id.img_switch_rightlight);
        img_result= (ImageView) rootView.findViewById(R.id.img_result);
        switchImageViews.add(img_switch_brake);
        switchImageViews.add(img_switch_cardoor);
        switchImageViews.add(img_switch_leftlight);
        switchImageViews.add(img_switch_rightlight);
        pb_checking = (ProgressBar)rootView.findViewById(R.id.pb_checking);
        tv_result = (TextView)rootView.findViewById(R.id.tv_result);

    }

    private void initData() {
        if (TextUtils.isEmpty(type)) {
            alert("数据异常");
            return;
        }
        if (Constents.CHECK_TYPE_FIRSTCHECK.equals(type)){
            titlebarview1.setTitle("初检");
            btn_backscan.setText("返回初检扫描首页");
            btn_finishcheck.setText("结束初检");
            btn_forcepass.setVisibility(View.GONE);
        }else{
            titlebarview1.setTitle("复检");
            btn_backscan.setText("返回复检扫描首页");
            btn_finishcheck.setText("结束复检");
            if (PermissionsManager.isShowInspectionForcepass()) {
                btn_forcepass.setVisibility(View.VISIBLE);
            }
        }
        btn_backscan.setOnClickListener(this);
        btn_finishcheck.setOnClickListener(this);
        btn_forcepass.setOnClickListener(this);
        checkHttpLoader = new CheckHttpLoader();
        if (lockButtons.size() > 0) {
            for (final Map.Entry<Integer, Button> entry : lockButtons.entrySet()) {
                entry.getValue().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entry.getValue().setEnabled(false);
                        requestEngineLock(entry.getKey(),entry.getValue());
                    }
                });
            }
        }
        requestLastStatus();
    }

    private void requestEngineLock(int action, final Button button) {
        Subscription subscription = checkHttpLoader.engineLock(inspectionId, action).subscribe(new MySubscriber<EngineLockBean>(getContext()) {
            @Override
            public void onNext(EngineLockBean engineLockBean) {
                if (engineLockBean == null) {
                    return;
                }
                cids.add(engineLockBean.cid);
            }

            @Override
            protected void onError(ApiException ex) {
                super.onError(ex);
                button.setEnabled(true);
            }
        });
        RxApiManager.get().add(TAG, subscription);
    }

    private void requestLastStatus() {
        Subscription subscription = checkHttpLoader.getLastStatus(inspectionId, cids).subscribe(new MySubscriber<CheckStatusBean>(getContext()) {
            @Override
            public void onNext(CheckStatusBean checkStatusBean) {
                LogUtil.d(TAG, checkStatusBean.toString());
                if (!isCheckFinish) {
                    rootView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!isCheckFinish) {
                                requestLastStatus();
                            }
                        }
                    }, 5000);
                }
            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_backscan:
                Bundle bundle = new Bundle();
                bundle.putString("type",type);
                startActivity(new Intent(getActivity(), CheckActivity.class).putExtras(bundle));
                getActivity().finish();
                break;
            case R.id.btn_finishcheck:
                isCheckFinish = true;
                finishCheck();
                break;
            case R.id.btn_forcepass:
                break;
        }

    }

    private void finishCheck() {
        Subscription subscription = checkHttpLoader.finishCheck(inspectionId).subscribe(new MySubscriber<ResponseBody>(getContext(), progressDialogIndicator) {
            @Override
            public void onNext(ResponseBody responseBody) {
                pb_checking.setVisibility(View.GONE);
                img_result.setVisibility(View.VISIBLE);
                btn_backscan.setEnabled(true);
                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");
                    if (success) {
                        img_result.setImageResource(R.drawable.first_checkfail);
                        tv_result.setText(String.format("%s结果: 通过", TextUtils.equals(type, Constents.CHECK_TYPE_FIRSTCHECK) ? "初检" : "复检"));
                        tv_result.setTextColor(getResources().getColor(R.color.red));
                        if (TextUtils.equals(type,Constents.CHECK_TYPE_SECONDCHECK)) {
                            btn_forcepass.setEnabled(false);
                        }
                    } else {
                        tv_result.setTextColor(getResources().getColor(R.color.pass_result));
                        img_result.setImageResource(R.drawable.first_checkfail);
                        tv_result.setText(String.format("%s结果: %s", TextUtils.equals(type, Constents.CHECK_TYPE_FIRSTCHECK) ? "初检" : "复检",message));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    private void forceFinishCheck() {
        Subscription subscription = checkHttpLoader.forceFinishCheck(inspectionId, true, forceReason).subscribe(new MySubscriber<ResponseBody>(getContext(), progressDialogIndicator) {
            @Override
            public void onNext(ResponseBody responseBody) {

            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxApiManager.get().cancel(TAG);
    }
}