package com.aerozhonghuan.hongyan.producer.modules.query.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.query.adapter.PopupAdapter;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.OperationTypeBean;

import java.util.List;


public class OperationType extends PopupWindow {

    private View contentView;
    private GridView grid;
    private TextView reset;
    private TextView ok;
    private PopupAdapter adapter;
    private List<OperationTypeBean> data;
    OperationTypeBean mOperationTypeBean;
    public OperationType(final Activity context, final List<OperationTypeBean> data) {
        this.data = data;
        adapter = new PopupAdapter(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popup_operation_type, null);
        grid = (GridView) contentView.findViewById(R.id.grid);
        reset = (TextView) contentView.findViewById(R.id.tv_reset);
        ok = (TextView) contentView.findViewById(R.id.tv_ok);
        grid.setAdapter(adapter);
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
                Toast.makeText(context, data.get(position).getStr2(), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged(data);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "重置", Toast.LENGTH_SHORT).show();
                OperationType.this.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OperationType.this.dismiss();
                Toast.makeText(context, mOperationTypeBean==null?"确定":mOperationTypeBean.getStr2(), Toast.LENGTH_SHORT).show();
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

    public void showoperationtypePopup(View parent, final List<OperationTypeBean> data) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
            adapter.notifyDataSetChanged(data);
        } else {
            this.dismiss();
        }
    }

}
