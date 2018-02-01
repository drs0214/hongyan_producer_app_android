package com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 15:12
 * @des:
 */
public class ManyScanAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<TransportScanDetailBean.ActionsBean> manyscanlist = new ArrayList<TransportScanDetailBean.ActionsBean>();

    public ManyScanAdapter(Context context, ArrayList<TransportScanDetailBean.ActionsBean> manyscanlist) {
        this.mContext = context;
        this.manyscanlist = manyscanlist;
    }

    @Override
    public int getCount() {
        return manyscanlist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_many_scan, null);
            holder.tv_name = (Button) convertView.findViewById(R.id.tv_name);
            holder.ll_root = (LinearLayout) convertView.findViewById(R.id.ll_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TransportScanDetailBean.ActionsBean manyScanBean = manyscanlist.get(position);
        holder.tv_name.setText(manyScanBean == null ? "" : manyScanBean.getLabel());
        if (manyScanBean.isEnabled()) {
            holder.tv_name.setBackgroundResource(R.drawable.transport_scan_bg_selector_blue);
            holder.tv_name.setTextColor(mContext.getResources().getColorStateList(R.drawable.transport_scan_textcolor_selector_blue));
        }else{
            holder.tv_name.setBackgroundResource(R.drawable.transport_scan_bg_selector_gray);
            holder.tv_name.setTextColor(mContext.getResources().getColorStateList(R.drawable.transport_scan_textcolor_selector_gray));
        }

        return convertView;
    }

    private static class ViewHolder {
        LinearLayout ll_root;
        Button tv_name;
    }
}
