package com.aerozhonghuan.hongyan.producer.modules.query.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.home.MainActivity;
import com.aerozhonghuan.hongyan.producer.modules.query.adapter.PopupAdapter;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.OperationTypeBean;
import com.aerozhonghuan.hongyan.producer.modules.query.view.time.ScreenInfo;
import com.aerozhonghuan.hongyan.producer.modules.query.view.time.WheelMain;

import java.util.Calendar;
import java.util.List;


public class OperationTime extends PopupWindow {

    private View contentView;
    private TextView tv_start_time;
    private TextView reset,ok;
    private TextView tv_end_time;
    LayoutInflater inflater1;
    WheelMain wheelMain;
    int year, month, day, hour, min;
    public OperationTime(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_operation_time, null);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        inflater1 = LayoutInflater.from(context);
        tv_start_time = (TextView) contentView.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) contentView.findViewById(R.id.tv_end_time);
        reset = (TextView) contentView.findViewById(R.id.tv_reset);
        ok = (TextView) contentView.findViewById(R.id.tv_ok);
       tv_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View timepickerview = inflater1.inflate(
                        R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(context);

                wheelMain = new WheelMain(timepickerview, 0);
                wheelMain.screenheight = screenInfo.getHeight();
                wheelMain.initDateTimePicker(year, month, day, hour, min);

                new AlertDialog.Builder(context)
                        .setTitle("选择时间")
                        .setView(timepickerview)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        tv_start_time.setText(wheelMain.getTime());
                                    }
                                }).setNegativeButton("取消", null).show();

            }
        });
        tv_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View timepickerview = inflater1.inflate(
                        R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(context);

                wheelMain = new WheelMain(timepickerview, 0);
                wheelMain.screenheight = screenInfo.getHeight();
                wheelMain.initDateTimePicker(year, month, day, hour, min);

                new AlertDialog.Builder(context)
                        .setTitle("选择时间")
                        .setView(timepickerview)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        tv_end_time.setText(wheelMain.getTime());
                                    }
                                }).setNegativeButton("取消", null).show();

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "重置", Toast.LENGTH_SHORT).show();
                OperationTime.this.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationTime.this.dismiss();
                Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
            }
        });
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(contentView);
        this.setWidth(w);
        this.setHeight(h);
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();

    }

    public void showoperationtimePopup(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

}
