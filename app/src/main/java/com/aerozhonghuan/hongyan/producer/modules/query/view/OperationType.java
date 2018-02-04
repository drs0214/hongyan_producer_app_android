package com.aerozhonghuan.hongyan.producer.modules.query.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.modules.query.adapter.PopupAdapter;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.OperationTypeBean;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.Query_Constans;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;


public class OperationType extends PopupWindow {

    private View contentView;
    private GridView grid;
    private TextView reset;
    private Activity context;
    private TextView ok;
    private PopupAdapter adapter;
    private List<TransportScanDetailBean.ActionsBean> listData=new ArrayList<>();
    TransportScanDetailBean.ActionsBean mOperationTypeBean;
    TextView tv_operation_type;
    public OperationType(final Activity context, final List<TransportScanDetailBean.ActionsBean> data, final TextView tv_operation_type) {
        this.context = context;
        this.listData = data;
        this.tv_operation_type=tv_operation_type;
        initView(context);
        initData(context);
        setListen(context, listData, tv_operation_type);
    }

    private void initData(final Activity context) {
    }

    private void setListen(final Activity context, final List<TransportScanDetailBean.ActionsBean> data, final TextView tv_operation_type) {


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
             mOperationTypeBean = data.get(position);
                    data.get(position).setChecked(!data.get(position).isChecked());
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        continue;
                    }
                    data.get(i).setChecked(false);
                }
                adapter.notifyDataSetChanged(data);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < listData.size(); i++) {
                    listData.get(i).setChecked(false);
                }
                adapter.notifyDataSetChanged(listData);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_operation_type.setTextColor(context.getResources().getColor(R.color.text_tj));
                if(mOperationTypeBean.isChecked()){
                    Query_Constans.type=mOperationTypeBean.getName();
                }else{
                    Query_Constans.type="";
                }
                Query_Constans.isok_type=true;
                Toast.makeText(context, mOperationTypeBean==null?"确定": Query_Constans.type, Toast.LENGTH_SHORT).show();
                OperationType.this.dismiss();
            }
        });
    }

    private void initView(Activity context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_operation_type, null);
        grid = (GridView) contentView.findViewById(R.id.grid);
        reset = (TextView) contentView.findViewById(R.id.tv_reset);
        ok = (TextView) contentView.findViewById(R.id.tv_ok);
        adapter = new PopupAdapter(context);
        grid.setAdapter(adapter);
        tv_operation_type.setTextColor(context.getResources().getColor(R.color.chujian_blue));

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

    public void showoperationtypePopup(View parent,TextView tv_operation_type) {
        if (!this.isShowing()) {
            adapter.notifyDataSetChanged(listData);
            this.showAsDropDown(parent);
            tv_operation_type.setTextColor(context.getResources().getColor(R.color.chujian_blue));
        } else {
            tv_operation_type.setTextColor(context.getResources().getColor(R.color.text_tj));
            this.dismiss();
        }
    }

}
