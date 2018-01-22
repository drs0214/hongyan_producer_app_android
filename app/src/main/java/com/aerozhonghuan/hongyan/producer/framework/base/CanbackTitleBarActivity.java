package com.aerozhonghuan.hongyan.producer.framework.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;
import com.aerozhonghuan.foundation.base.BaseFragment;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/7/7 0007 on 上午 11:32
 */

public class CanbackTitleBarActivity extends com.aerozhonghuan.foundation.base.BaseActivity {
    private BackHandledInterface backHandledInterface;
    private TitleBarView titleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titlebar_activity);
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
    }

    protected TitleBarView getTitleBarView() {
        return titleBarView;
    }

    protected void setTitleBarView(TitleBarView view) {
        titleBarView = view;
    }

    public void popBackStack() {
        if (getSupportFragmentManager() == null) return;
        getSupportFragmentManager().popBackStack();
    }

    // 给fragment添加返回监听事件
    @Override
    public void onBackPressed() {
        if (backHandledInterface != null) {
            boolean isFragmentBack = backHandledInterface.onFragmentPressedBack();
            if (!isFragmentBack) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected void setBackHandledInterface(BackHandledInterface backHandledInterface) {
        this.backHandledInterface = backHandledInterface;
    }

    public interface BackHandledInterface {
        boolean onFragmentPressedBack();
    }

}
