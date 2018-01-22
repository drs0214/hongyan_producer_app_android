package com.aerozhonghuan.hongyan.producer.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.foundation.mvp.IMvpView;


/**
 * 标题栏 view
 * Created by zhangyf on 2016-07-25
 */
public class TitleBarView extends RelativeLayout implements IMvpView {

    private View contentView;
    private TextView textview_title;
    private ImageView btn_back;

    private OnClickListener backbuttonClickListener;
    private TextView tv_title_right;
    private ImageView iv_title_right;
    private ImageView iv_title_rightmore;
    private EditText et_title;

    public TitleBarView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        contentView = layoutInflater.inflate(R.layout.titlebar_view, null);
        btn_back = (ImageView) contentView.findViewById(R.id.btn_back);
        textview_title = (TextView) contentView.findViewById(R.id.textview_title);
        tv_title_right = (TextView) contentView.findViewById(R.id.tv_title_right);
        iv_title_right = (ImageView) contentView.findViewById(R.id.iv_title_right);
        iv_title_rightmore = (ImageView) contentView.findViewById(R.id.iv_title_rightmore);
        et_title = (EditText) contentView.findViewById(R.id.et_title);
        contentView.setBackgroundColor(getResources().getColor(R.color.transparent));
        setBackgroundColor(getResources().getColor(R.color.titlebar_bg));
        enableBackButton(true);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(contentView, lp);
    }

    public void setTitle(String text) {
        String t = text;
        if (!TextUtils.isEmpty(t) && t.length() > 8) {
            t = t.substring(0, 8);
            t += "...";
        }
        textview_title.setText(t);
    }

    public void setTitle(int id) {
        textview_title.setText(id);
    }

    public void setTitleVisibility(boolean visibility) {
        this.textview_title.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void setEditTextVisibility(boolean visibility) {
        this.et_title.setVisibility(visibility ? View.VISIBLE : View.GONE);
        if (visibility) {
            et_title.requestFocus();
        }
    }

    public String getEditText() {
        return et_title.getText().toString();
    }

    public void setEditText(String str) {
        et_title.setText(str);
    }

    /**
     * 启用返回按钮
     */
    public void enableBackButton(boolean isEnable) {
        if (isEnable) {
            btn_back.setVisibility(View.VISIBLE);
            btn_back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //如果注册了处理事件
                    if (backbuttonClickListener != null) {
                        backbuttonClickListener.onClick(v);
                        return;
                    }
                    //否则提供默认的处理方式
                    if (getContext() instanceof Activity) {
                        Activity act = (Activity) getContext();
                        act.onBackPressed();
                    }
                }
            });
        } else {
            btn_back.setVisibility(View.GONE);
        }
    }

    /**
     * 显示右侧文字
     */
    public void showRightText(String rightText, View.OnClickListener rightTextClickListener) {
        tv_title_right.setText(rightText);
        tv_title_right.setOnClickListener(rightTextClickListener);
        tv_title_right.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏右侧文字
     */
    public void hideRightText() {
        tv_title_right.setVisibility(View.GONE);
    }

    /**
     * 显示右侧按钮
     */
    public void setRightImageButton(int imageResID, View.OnClickListener rightTextClickListener) {
        iv_title_right.setVisibility(View.GONE);
        iv_title_right.setImageResource(imageResID);
        iv_title_right.setOnClickListener(rightTextClickListener);
        iv_title_right.setVisibility(View.VISIBLE);
    }

    public void setRightTextVisibility(boolean visibility) {
        this.tv_title_right.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示右侧文字带箭头
     */
    public void showRightTextWithArrow(String rightText, View.OnClickListener rightTextClickListener) {
        tv_title_right.setText(rightText);
        Drawable img_right = getResources().getDrawable(R.drawable.ic_right);
        img_right.setBounds(0, 2, img_right.getMinimumWidth(), img_right.getMinimumHeight());
        tv_title_right.setCompoundDrawables(null, null, img_right, null);
        tv_title_right.setOnClickListener(rightTextClickListener);
        tv_title_right.setVisibility(View.VISIBLE);
    }

    public void setRightTextWithArrowVisibility(boolean visibility) {
        this.tv_title_right.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示右侧图片按钮(一张)
     *
     * @param resId
     * @param onClickListener
     */
    public void showOneRightImageButton(int resId, View.OnClickListener onClickListener) {
        iv_title_right.setImageResource(resId);
        iv_title_right.setOnClickListener(onClickListener);
        iv_title_right.setVisibility(View.VISIBLE);
    }

    public void setOneRightImageVisibility(boolean visibility) {
        this.iv_title_right.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示右侧图片按钮(一张)
     *
     * @param leftResId
     * @param rightResId
     * @param onLeftClickListener
     * @param onRightClickListener
     */
    public void showTwoRightImageButton(int leftResId, int rightResId, View.OnClickListener onLeftClickListener, View.OnClickListener onRightClickListener) {
        iv_title_rightmore.setImageResource(leftResId);
        iv_title_rightmore.setOnClickListener(onLeftClickListener);
        iv_title_rightmore.setVisibility(View.VISIBLE);
        iv_title_right.setImageResource(rightResId);
        iv_title_right.setOnClickListener(onRightClickListener);
        iv_title_right.setVisibility(View.VISIBLE);
    }

    public void setTwoRightImageVisibility(boolean visibility) {
        this.iv_title_rightmore.setVisibility(visibility ? View.VISIBLE : View.GONE);
        this.iv_title_right.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置 返回按钮的监听事件
     *
     * @param onClickListener
     */
    public void setOnBackButtonClickListener(OnClickListener onClickListener) {
        this.backbuttonClickListener = onClickListener;
    }

    @Override
    public void alert(String msg) {

    }

    @Override
    public void alert(int sourceID) {

    }
}
