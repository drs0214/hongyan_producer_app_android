package com.aerozhonghuan.hongyan.producer.modules.home;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.aerozhonghuan.foundation.base.BaseActivity;
import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.hongyan.producer.BuildConfig;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.common.event.DefalutHttpExceptionAlert;
import com.aerozhonghuan.hongyan.producer.modules.common.event.ReloadMessageEvent;
import com.aerozhonghuan.hongyan.producer.modules.home.fragment.HomeFragment;
import com.aerozhonghuan.hongyan.producer.modules.home.fragment.MineFragment;
import com.aerozhonghuan.hongyan.producer.modules.home.fragment.SearchFragment;
import com.aerozhonghuan.hongyan.producer.utils.EnvironmentInfoUtils;
import com.aerozhonghuan.hongyan.producer.widget.TabButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBusManager.register(this);
        //打印环境变量信息
        if (BuildConfig.DEBUG) {
            new EnvironmentInfoUtils().print(this);
        }
        initView();
        initEvent();
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
    protected void onStart() {
        super.onStart();
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

    @Override
    protected void onResume() {
        super.onResume();
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

    /**
     * 默认 http异常提醒
     *
     * @param defalutHttpExceptionAlert
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    @SuppressWarnings("unused")
    public void onEvent(DefalutHttpExceptionAlert defalutHttpExceptionAlert) {
        if (isFinishing())
            return;
        if (defalutHttpExceptionAlert != null)
            alert(defalutHttpExceptionAlert.getErrorMsg());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusManager.unregister(this);
        Log.d(TAG, "onDestroy: Mainactivty");
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
}
