package com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 15:12
 * @des:
 */
public class ManyScanAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ManyScanBean> manyscanlist=new ArrayList<ManyScanBean>();

    public ManyScanAdapter(Context context,  ArrayList<ManyScanBean> manyscanlist) {
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
            holder=new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_many_scan, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        ManyScanBean manyScanBean = manyscanlist.get(position);
        holder.tv_name.setText(manyScanBean==null?"":manyScanBean.getName());
        return convertView;
    }

    private static class ViewHolder {

        TextView tv_name;
    }
}
