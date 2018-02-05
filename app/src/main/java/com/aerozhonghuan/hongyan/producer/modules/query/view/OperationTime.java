package com.aerozhonghuan.hongyan.producer.modules.query.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.aerozhonghuan.hongyan.producer.modules.query.entity.Query_Constans;
import com.aerozhonghuan.hongyan.producer.modules.query.view.time.ScreenInfo;
import com.aerozhonghuan.hongyan.producer.modules.query.view.time.WheelMain;
import com.aerozhonghuan.hongyan.producer.utils.TimeUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


public class OperationTime extends PopupWindow {

    private View contentView;
    private TextView tv_start_time;
    private TextView reset,ok;
    private TextView tv_end_time;
    TextView tv_operation_time;
    LayoutInflater inflater1;
    WheelMain wheelMain;
    int year, month, day, hour, min;
    Activity context;
     String starttime="";
     String endtime="";
    public OperationTime(final Activity context, final TextView tv_operation_time) {
        this.context = context;
        this.tv_operation_time = tv_operation_time;
        tv_operation_time.setTextColor(context.getResources().getColor(R.color.chujian_blue));
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
        ok.setBackgroundResource(R.color.chujian_blue);
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
                                        try {
                                            long l=TimeUtil.stringToLong(wheelMain.getTime(),"yyyy-MM-dd HH:mm");
                                             starttime = TimeUtil.getDate_yyyyMMddTHHmmss(l);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
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
                                        try {
                                            long l=TimeUtil.stringToLong(wheelMain.getTime(),"yyyy-MM-dd HH:mm");
                                             endtime = TimeUtil.getDate_yyyyMMddTHHmmss(l);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).setNegativeButton("取消", null).show();

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_start_time.setText("");
                tv_end_time.setText("");
                starttime="";
                endtime="";
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, tv_start_time.getText().toString()+tv_end_time.getText().toString(), Toast.LENGTH_SHORT).show();
                tv_operation_time.setTextColor(context.getResources().getColor(R.color.text_tj));
                Query_Constans.start_time=starttime;
                Query_Constans.end_time=endtime;
                Query_Constans.isok_time=true;
                OperationTime.this.dismiss();

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
        this.setOutsideTouchable(false);
        this.update();
    }

    public void showoperationtimePopup(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
        } else {
            tv_operation_time.setTextColor(context.getResources().getColor(R.color.text_tj));
            this.dismiss();
        }
    }

}
