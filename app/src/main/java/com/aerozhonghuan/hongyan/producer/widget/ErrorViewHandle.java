package com.aerozhonghuan.hongyan.producer.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.aerozhonghuan.hongyan.producer.R;


/**
 * 错误视图 的持有者，辅助类
 * Created by zhangyunfei on 2017/7/27.
 */

public class ErrorViewHandle {

    private ErrorView errorView;
    private View toggleView;

    private ErrorViewHandle(ViewGroup parentView, View toggleView) {
        Context context = toggleView.getContext();
        errorView = (ErrorView) parentView.findViewById(R.id.error_view1);
        if (errorView == null) {
            errorView = onCreateErrorView(context);
            errorView.setVisibility(View.GONE);

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            parentView.addView(errorView, params);
        }
    }

    public static ErrorViewHandle create(ViewGroup parentView, View toggleView) {
        if (toggleView == null) throw new NullPointerException();
        if (parentView == null) throw new NullPointerException();
        ErrorViewHandle errorViewHandle = new ErrorViewHandle(parentView, toggleView);
        return errorViewHandle;
    }

    /**
     * 当创建errorview 时
     *
     * @return
     */
    protected ErrorView onCreateErrorView(Context context) {
        ErrorView errorView1 = new ErrorView(context);
        errorView1.setId(R.id.error_view1);
        return errorView1;
    }

    public void relase() {
        errorView = null;
        toggleView = null;
    }

    public void dismissErrorView() {
        if (errorView != null)
            errorView.setVisibility(View.GONE);
        if (toggleView != null)
            toggleView.setVisibility(View.VISIBLE);
    }


    public void showErrorView() {
        if (errorView != null)
            errorView.setVisibility(View.VISIBLE);
        if (toggleView != null)
            toggleView.setVisibility(View.GONE);
    }

    public ErrorView getErrorView() {
        return errorView;
    }

    public View getToggleView() {
        return toggleView;
    }
}
