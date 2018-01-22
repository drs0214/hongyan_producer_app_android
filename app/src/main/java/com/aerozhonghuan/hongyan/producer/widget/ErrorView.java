package com.aerozhonghuan.hongyan.producer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;


/**
 * 操作失败 view
 * Created by zhangyf on 2016-07-25
 */
public class ErrorView extends LinearLayout {

    private View contentView;
    private TextView text1;
    private ImageView imageview1;
    private Button btn_retry;

    public ErrorView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        contentView = layoutInflater.inflate(R.layout.error_view, null);
        imageview1 = (ImageView) contentView.findViewById(R.id.imageview1);
        text1 = (TextView) contentView.findViewById(R.id.text1);
        btn_retry = (Button) contentView.findViewById(R.id.btn_retry);
        onInitView(imageview1, text1, btn_retry, contentView);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(contentView, lp);
    }

    /**
     * 初始化子页面
     *
     * @param imageview1
     * @param text1
     * @param btn_retry
     * @param contentView
     */
    protected void onInitView(ImageView imageview1, TextView text1, Button btn_retry, View contentView) {

    }

    /**
     * 显示 网络失败 提示语
     */
    public void setNoNetworkState() {
        post(new Runnable() {
            @Override
            public void run() {
                imageview1.setImageResource(R.drawable.ic_no_network);
                text1.setText(R.string.alert_no_network);
                btn_retry.setText(R.string.retry);
            }
        });
    }


    /**
     * 显示默认 提示语
     */
    public void setNomalState() {
        post(new Runnable() {
            @Override
            public void run() {
                imageview1.setImageResource(R.drawable.ic_load_failue);
                text1.setText(R.string.alert_load_data_failure);
                btn_retry.setText(R.string.retry);
            }
        });
    }

    public void setText(String text) {
        text1.setText(text);
    }

    /**
     * 设置 按钮的文字
     *
     * @param text
     */
    public void setButtontext(final String text) {
        post(new Runnable() {
            @Override
            public void run() {
                btn_retry.setText(text);
            }
        });
    }

    public void setImage(final int imageID) {
        post(new Runnable() {
            @Override
            public void run() {
                imageview1.setImageResource(imageID);
            }
        });
    }

    /**
     * 确定/重试 按钮的点击事件
     *
     * @param onRetryButtonClick
     */
    public void setOnConfirmButtonClick(OnClickListener onRetryButtonClick) {
        btn_retry.setOnClickListener(onRetryButtonClick);
    }
}
