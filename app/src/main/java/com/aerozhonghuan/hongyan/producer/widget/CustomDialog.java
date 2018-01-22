package com.aerozhonghuan.hongyan.producer.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;

/**
 * 描述:自定义封装的对话框
 * 作者:zhangyonghui
 * 创建日期：2017/6/27 0027 on 下午 3:02
 */

public class CustomDialog {
    private String dialogTitle;
    private String dialogMessage;
    private String positiveText;
    private String negativeText;
    private View dialogView;
    private OnDialogListener onDialogListener;
    private Dialog dialog;
    private LayoutInflater inflater;

    // 带有自定义view的构造器
    public CustomDialog(Context context, String dialogTitle, View view, String positiveText, String negativeText) {
        dialog = new Dialog(context, R.style.myDialog);
        inflater = LayoutInflater.from(context);
        this.dialogTitle = dialogTitle;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.dialogView = view;
    }

    // 不带自定义view的构造器
    public CustomDialog(Context context, String dialogTitle, String dialogMessage, String positiveText, String negativeText) {
        dialog = new Dialog(context, R.style.myDialog);
        inflater = LayoutInflater.from(context);
        this.dialogTitle = dialogTitle;
        this.dialogMessage = dialogMessage;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    // 不带自定义view的构造器
    public CustomDialog(Context context, String dialogMessage, String positiveText, String negativeText) {
        dialog = new Dialog(context, R.style.myDialog);
        inflater = LayoutInflater.from(context);
        this.dialogMessage = dialogMessage;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    public CustomDialog showDialog() {
        View view = inflater.inflate(R.layout.layout_customdialog, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView btn_negative = (TextView) view.findViewById(R.id.btn_negative);
        TextView btn_positive = (TextView) view.findViewById(R.id.btn_positive);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        LinearLayout ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        if (!TextUtils.isEmpty(dialogTitle)) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(dialogTitle);
        } else {
            tv_title.setVisibility(View.GONE);
        }
        if (dialogMessage != null) {
            tv_content.setText(dialogMessage);
        } else {
            tv_content.setVisibility(View.GONE);
            ll_content.addView(dialogView);
        }
        if (TextUtils.isEmpty(positiveText) && TextUtils.isEmpty(negativeText)) {
            btn_positive.setVisibility(View.GONE);
            btn_negative.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(positiveText) && TextUtils.isEmpty(negativeText)) {
            btn_positive.setText(positiveText);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn_positive.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            btn_positive.setLayoutParams(layoutParams);
            btn_negative.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(negativeText) && TextUtils.isEmpty(positiveText)) {
            btn_negative.setText(negativeText);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn_negative.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            btn_negative.setLayoutParams(layoutParams);
            btn_positive.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(negativeText) && !TextUtils.isEmpty(positiveText)) {
            btn_negative.setText(negativeText);
            btn_positive.setText(positiveText);
        }

        btn_positive.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (onDialogListener != null) {
                    onDialogListener.dialogPositiveListener();
                }
            }
        });
        btn_negative.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (onDialogListener != null) {
                    onDialogListener.dialogNegativeListener();
                }
            }
        });
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        if (!dialog.isShowing())
            dialog.show();
        return this;
    }

    // 注册监听器方法
    public CustomDialog setOnDiaLogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
        return this;//把当前对象返回,用于链式编程
    }

    public void release() {
        if (dialog != null) {
            dialog.cancel();
        }
        dialog = null;
    }

    // 定义一个监听器接口
    public interface OnDialogListener {
        //customView　这个参数需要注意就是如果没有自定义view,那么它则为null
        void dialogPositiveListener();

        void dialogNegativeListener();
    }
}
