package com.aerozhonghuan.hongyan.producer.modules.home;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.aerozhonghuan.foundation.base.BaseActivity;
import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;
import com.aerozhonghuan.hongyan.producer.modules.common.event.ReloadMessageEvent;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.AppInfo;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.PhoneInfo;
import com.aerozhonghuan.hongyan.producer.modules.home.fragment.HomeFragment;
import com.aerozhonghuan.hongyan.producer.modules.home.fragment.MineFragment;
import com.aerozhonghuan.hongyan.producer.modules.home.fragment.SearchFragment;
import com.aerozhonghuan.hongyan.producer.modules.home.logic.HomeHttpLoader;
import com.aerozhonghuan.hongyan.producer.utils.LocalCache;
import com.aerozhonghuan.hongyan.producer.utils.TelephonyUtils;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.hongyan.producer.widget.TabButton;
import com.aerozhonghuan.rxretrofitlibrary.ApiException;
import com.aerozhonghuan.rxretrofitlibrary.RxApiManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscription;


/**
 * 描述: MainActivity show/hide fragment
 * tab为自定义TabButton,可以设置消息数量,红点提示
 * 作者:zhangyonghui
 * 创建日期：2017/6/20  on 上午 11:27
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, IMainView {

    private static final String TAG = "MainActivity";
    private static final int PAGE_HOME = 0;
    private static final int PAGE_MESSAGE = 1;
    private static final int PAGE_MINE = 2;
    // 第二次按返回键推出程序的时间间隔，4秒
    private final int mInterval = 1000;
    private List<TabButton> buttonList = new ArrayList<>();
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private BaseFragment currentFragment;
    private long badgeNum;
    // 记录首次按退出键时的时间
    private long mExitTime = 0;
    private HomeHttpLoader homeHttpLoader;
    private ProgressDialogIndicator progressDialogIndicator;
    private LocalCache localCache;
    private final String KEY_UPLOADPHONEINFO_FLAG = "last_uploadphoneinfo_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        EventBusManager.register(this);
        //打印环境变量信息
        /*if (BuildConfig.DEBUG) {
            new EnvironmentInfoUtils().print(getApplicationContext());
        }*/
        progressDialogIndicator = new ProgressDialogIndicator(this);
        homeHttpLoader = new HomeHttpLoader();
        getUserAuthorization();
        checkIsUploadPhoneInfo();
        checkAppUpdate();
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("notify_intent")) {
            try {
                PendingIntent pendingIntent = getIntent().getParcelableExtra("notify_intent");
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras().containsKey("tab")) {
            int tab = Integer.valueOf(intent.getStringExtra("tab"));
            if (currentFragment != fragmentList.get(Integer.valueOf(intent.getStringExtra("tab")))) {
                select(tab);
                showFragment2(fragmentList.get(tab));
            } else {
                EventBusManager.post(new ReloadMessageEvent());
            }
        }
    }

    private void initView() {
        buttonList.add((TabButton) findViewById(R.id.tab_home));
        buttonList.add((TabButton) findViewById(R.id.tab_msg));
        buttonList.add((TabButton) findViewById(R.id.tab_user));
        fragmentList.add(new HomeFragment());
        fragmentList.add(new SearchFragment());
        fragmentList.add(new MineFragment());
        // 设置首页默认选中
        buttonList.get(PAGE_HOME).setSelect(true);
        showFragment2(fragmentList.get(PAGE_HOME));
    }

    private void initEvent() {
        // 为每一个标签添加事件和tag
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setOnClickListener(this);
            buttonList.get(i).setTag(i);
        }
    }

    // 获取用户权限
    public void getUserAuthorization() {
        Subscription subscription = homeHttpLoader.getAuthorization().subscribe(new MySubscriber<PermissionsBean>(this, progressDialogIndicator) {

            @Override
            public void onNext(PermissionsBean permissionsBean) {
                if (permissionsBean != null && permissionsBean.permissions != null) {
                    PermissionsManager.setPermissions(permissionsBean);
                    initView();
                    initEvent();
                } else {
                    alert("数据异常");
                    UserInfoManager.logout(getContext());
                }
            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    // 上报手机信息,每天上报一次
    private void checkIsUploadPhoneInfo(){
        localCache = new LocalCache(getApplicationContext());
        long lastTime = localCache.getLong(KEY_UPLOADPHONEINFO_FLAG);
        if ((lastTime == 0 || lastTime + (24 * 60 * 60 * 1000) < System.currentTimeMillis())) {
            uploadPhoneInfo();
        }
    }

    private void uploadPhoneInfo() {
        PhoneInfo phoneInfo = new PhoneInfo();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneInfo.imei = telephonyManager.getDeviceId();
        phoneInfo.imsi = telephonyManager.getSubscriberId();
        phoneInfo.mobileNumber = telephonyManager.getLine1Number();
        phoneInfo.operator = TelephonyUtils.getOperator(getApplicationContext());
        phoneInfo.mobileBrand = Build.BRAND;
        phoneInfo.mobileType = Build.MODEL;
        phoneInfo.androidVersion = Build.VERSION.RELEASE;
        phoneInfo.sdkVersion = Build.VERSION.SDK;
        phoneInfo.appVersion = BuildConfig.VERSION_NAME;
        phoneInfo.username = UserInfoManager.getCurrentUserInfo().getUserName();
        Subscription subscription = homeHttpLoader.uploadPhoneInfo(phoneInfo).subscribe(new MySubscriber<ResponseBody>(this){
            @Override
            protected void onError(ApiException ex) {
                LogUtil.d(TAG,"上传手机信息异常:"+ex.message);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                localCache.putLong(KEY_UPLOADPHONEINFO_FLAG, System.currentTimeMillis());
            }
        });
        RxApiManager.get().add(TAG, subscription);
    }

    // 获取应用更新
    private void checkAppUpdate() {
        Subscription subscription = homeHttpLoader.getAppInfo().subscribe(new MySubscriber<AppInfo>(this) {
            @Override
            protected void onError(ApiException ex) {

            }

            @Override
            public void onNext(AppInfo appInfo) {
                LogUtil.d(TAG,"检查更新::"+appInfo.toString());

            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    @Override
    public void onClick(View v) {
        Integer tag = (Integer) v.getTag();
        // 点击当前标签获取其tag,并设置选中
        select(tag);
        showFragment2(fragmentList.get(tag));
    }

    /**
     * 设置当前标签选中
     *
     * @param number
     */
    public void select(int number) {
        for (TabButton btn : buttonList) {
            btn.setSelect(false);
        }
        buttonList.get(number).setSelect(true);
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment2(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragmentList.indexOf(fragment) > fragmentList.indexOf(currentFragment) && currentFragment != null) {
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
        } else if (fragmentList.indexOf(fragment) < fragmentList.indexOf(currentFragment) && currentFragment != null) {
            transaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_right_out);
        }

        if (currentFragment == null) {
            transaction.add(R.id.fragment_content, fragment);
        }
        //如果之前没有添加过
        else if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.fragment_content, fragment);
        } else {
            transaction.hide(currentFragment).show(fragment);
        }
        //全局变量，记录当前显示的fragment
        currentFragment = fragment;
        transaction.commit();
    }

    @Override
    public int getCurrentTab() {
        if (currentFragment instanceof HomeFragment) {
            return TAB_HOMEFRAGMENT;
        } else if (currentFragment instanceof SearchFragment) {
            return TAB_SEARCHFRAGMENT;
        } else if (currentFragment instanceof MineFragment) {
            return TAB_MINEFRAGMENT;
        } else {
            return 0;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击返回按钮，退出系统
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > mInterval) {
                alert("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotificationManager.cancelAll();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
                    for (ActivityManager.AppTask appTask : appTasks) {
                        appTask.finishAndRemoveTask();
                    }
                } else {
                    finish();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }, 1000);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBusManager.unregister(this);
        RxApiManager.get().cancel(TAG);
        Log.d(TAG, "onDestroy: Mainactivty");
    }
}
