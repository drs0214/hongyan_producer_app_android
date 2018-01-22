//package com.aerozhonghuan.driverapp.widget;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.aerozhonghuan.driverapp.R;
//
//
///**
// * 网络失败时的 view
// * Created by zhangyf on 2016-07-25
// */
//public class NoNetworkErrorView extends ErrorView {
//
//    public NoNetworkErrorView(Context context) {
//        super(context);
//    }
//
//    public NoNetworkErrorView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public NoNetworkErrorView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    @Override
//    protected void onInitView(ImageView imageview1, TextView text1, Button btn_retry, View contentView) {
//        imageview1.setImageResource(R.drawable.ic_no_network);
//        text1.setText(R.string.alert_no_network);
//        super.onInitView(imageview1, text1, btn_retry, contentView);
//    }
//}
