package com.aerozhonghuan.hongyan.producer.framework.base;

import android.app.Activity;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;


/**
 * 承载到 TitlebarActivity的 fragment
 * Created by zhangyunfei on 16/12/14.
 */
public class TitlebarFragment extends BaseFragment {

    protected TitlebarActivity titlebarActivity;

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof TitlebarActivity) {
            titlebarActivity = (TitlebarActivity) activity;
        }
        super.onAttach(activity);
    }

    public TitlebarActivity getTitlebarActivity() {
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
}
