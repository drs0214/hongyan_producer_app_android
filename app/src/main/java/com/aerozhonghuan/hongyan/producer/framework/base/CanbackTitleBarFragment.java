package com.aerozhonghuan.hongyan.producer.framework.base;

import android.app.Activity;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/7/7 0007 on 上午 11:34
 */

public class CanbackTitleBarFragment extends BaseFragment implements CanbackTitleBarActivity.BackHandledInterface {

    protected CanbackTitleBarActivity titlebarActivity;

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof CanbackTitleBarActivity) {
            titlebarActivity = (CanbackTitleBarActivity) activity;
        }
        super.onAttach(activity);
    }

    public CanbackTitleBarActivity getTitlebarActivity() {
        return titlebarActivity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        titlebarActivity = null;
    }

    protected TitleBarView getTitlebar() {
        if (titlebarActivity == null) return null;
        return titlebarActivity.getTitleBarView();
    }

    @Override
    public void onStart() {
        super.onStart();
        getTitlebarActivity().setBackHandledInterface(this);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public boolean onFragmentPressedBack() {
        return false;
    }
}
