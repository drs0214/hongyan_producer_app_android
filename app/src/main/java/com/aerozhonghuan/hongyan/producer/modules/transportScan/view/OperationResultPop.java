package com.aerozhonghuan.hongyan.producer.modules.transportScan.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;

/**
 * @author: drs
 * @time: 2018/2/1 20:26
 * @des:
 */
public class OperationResultPop extends PopupWindow{
    private View conentView;
    private ImageView iv_operation_result;
    private Activity context;
    TextView tv_result;
    String result;
    boolean issuccess;
    public OperationResultPop(final Activity context,String result,boolean issuccess) {
        super(context);
        this.context = context;
        this.result = result;
        this.issuccess=issuccess;
        this.initPopupWindow();

    }

    private void initPopupWindow() {
        //使用view来引入布局
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_operation_result, null);
        tv_result= (TextView) conentView.findViewById(R.id.tv_result);
        iv_operation_result= (ImageView) conentView.findViewById(R.id.iv_operation_result);
        if (issuccess){
            iv_operation_result.setImageResource(R.drawable.operation_success);
        }else{
            iv_operation_result.setImageResource(R.drawable.ic_camera);
        }
        tv_result.setText(result);
        //获取popupwindow的高度与宽度
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(h);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!isOutsideTouchable()) {
                    View mView = getContentView();
                    if (null != mView)
                        mView.dispatchTouchEvent(event);
                }
                return isFocusable() && !isOutsideTouchable();
            }
        });
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果，设置动画，一会会讲解
        //        this.setAnimationStyle(R.style.AnimationPreview);
        //布局控件初始化与监听设置
     /*   LinearLayout llayout_remind = (LinearLayout) conentView
                .findViewById(R.id.llayout_remind);
        LinearLayout llayout_history = (LinearLayout) conentView
                .findViewById(R.id.llayout_history);
        llayout_remind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });

        llayout_history.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });*/
    }
    /**
     * 显示popupWindow的方式设置，当然可以有别的方式。
     *一会会列出其他方法
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }
    /**
     * 第一种
     * 显示在控件的下右方
     *
     * @param parent parent
     */
    public void showAtDropDownRight(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] + parent.getWidth() - OperationResultPop.this.getWidth(), location[1] + parent.getHeight());
        }
    }
    /**  第二种
     * 显示在控件的下中方
     *
     * @param parent parent
     */
    public void showAtDropDownCenter(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0] / 2 + parent.getWidth() / 2 - OperationResultPop.this.getWidth() / 6, location[1] + parent.getHeight());
        }
    }
    /**   第三种
     * 显示在控件的下左方6
     *
     * @param parent parent
     */
    public void showAtDropDownLeft(View parent) {
        if (parent.getVisibility() == View.GONE) {
            this.showAtLocation(parent, 0, 0, 0);
        } else {
            // x y
            int[] location = new int[2];
            //获取在整个屏幕内的绝对坐标
            parent.getLocationOnScreen(location);
            this.showAtLocation(parent, 0, location[0], location[1] + parent.getHeight());
        }
    }
    /**   第四种
     * 屏幕正中间
     *
     * @param parent parent
     */
    public void showpop_center(View parent) {
        //            this.showAsDropDown(parent, 0, 0, Gravity.CENTER);
//                this.showAtLocation( parent , Gravity.LEFT | Gravity.TOP , parent.getWidth()/2 - this.getWidth()/2, parent.getHeight()/2 - this.getHeight()/2);
        this.showAtLocation( parent , Gravity.CENTER , 0,0);
    }
}
