package com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 15:12
 * @des:
 */
public class Authorization_actionsAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<TransportScanDetailBean.ActionsBean> manyscanlist = new ArrayList<TransportScanDetailBean.ActionsBean>();

    public Authorization_actionsAdapter(Context context, ArrayList<TransportScanDetailBean.ActionsBean> manyscanlist) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_authorization_action, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ll_root = (LinearLayout) convertView.findViewById(R.id.ll_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TransportScanDetailBean.ActionsBean manyScanBean = manyscanlist.get(position);
        holder.tv_name.setText(manyScanBean == null ? "" : manyScanBean.getLabel());
        return convertView;
    }

    private static class ViewHolder {
        LinearLayout ll_root;
        TextView tv_name;
    }
}
