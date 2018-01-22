package com.aerozhonghuan.hongyan.producer.framework.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.foundation.base.BaseActivity;
import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;


/**
 * 带有 titlebar 的容器activity
 * Created by zhangyunfei on 16/12/7.
 */
public class TitlebarActivity extends BaseActivity {

    private TitleBarView titleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int contentViewId = onInitContentView();
        if (contentViewId == 0)
            setContentView(R.layout.titlebar_activity);
        else
            setContentView(contentViewId);
        initTitlebarView();
    }

    /**
     * 重载 初始化 setContentView，如果子类不覆盖这个方法，则默认使用 titlebar_activity
     *
     * @return
     */
    protected int onInitContentView() {
        return 0;
    }

    /**
     * 初始化 TitlebarView
     */
    protected void initTitlebarView() {
        titleBarView = (TitleBarView) findViewById(R.id.titlebarview1);
        if (titleBarView != null)
            titleBarView.setTitle(getTitle().toString());
    }

    public void showFragment(BaseFragment fragment) {
        showFragment(fragment, true);
    }

    public void showFragment(BaseFragment fragment, boolean isAddToBackStack) {
        showFragment(fragment, isAddToBackStack, isAddToBackStack, null);
    }

    public void showFragment(BaseFragment fragment, boolean isAddToBackStack, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        showFragment(fragment, isAddToBackStack, isAddToBackStack, null);
    }

    public void showFragment(BaseFragment fragment, boolean isAddToBackStack, boolean isPlayAnimations) {
        showFragment(fragment, isAddToBackStack, isPlayAnimations, null);
    }

    /**
     * @param fragment         需要replace的fragment
     * @param isAddToBackStack 是否添加回退栈,可以点击返回回到上一个fragment
     * @param bundle           传值
     */
    public void showFragment(BaseFragment fragment, boolean isAddToBackStack, boolean isPlayAnimations, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        if (isPlayAnimations) {
            fragmentTransaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
        }
        fragmentTransaction.replace(R.id.container1, fragment, fragment.getClass().getSimpleName());
        if (isAddToBackStack)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        titleBarView = null;
//        LeakCanaryUtil.getRefWatcher().watch(this);
    }

    protected TitleBarView getTitleBarView() {
        return titleBarView;
    }

    public void popBackStack() {
        if (getSupportFragmentManager() == null) return;
        getSupportFragmentManager().popBackStack();
    }
}
