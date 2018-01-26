package com.aerozhonghuan.hongyan.producer.modules.check.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.check.entity.History_RecordBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 16:05
 * @des:
 */
public class History_RecordAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<History_RecordBean> manyscanlist=new ArrayList<History_RecordBean>();

    public History_RecordAdapter(Context context, ArrayList<History_RecordBean> manyscanlist) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_history_record, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_ispass = (TextView) convertView.findViewById(R.id.tv_ispass);
            holder.iv_ispass = (ImageView) convertView.findViewById(R.id.iv_ispass);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        History_RecordBean history_RecordBean = manyscanlist.get(position);
        holder.tv_name.setText(history_RecordBean==null?"":history_RecordBean.getName());
        holder.tv_data.setText(history_RecordBean==null?"":history_RecordBean.getData());
        holder.tv_state.setText(history_RecordBean==null?"":history_RecordBean.getCheck_state());
        if(history_RecordBean.isIspass()){
            holder.tv_ispass.setText("通过");
            holder.iv_ispass.setImageResource(R.drawable.pass);
        }else{
            holder.tv_ispass.setText("不通过");
            holder.iv_ispass.setImageResource(R.drawable.fail);
        }

        return convertView;
    }

    private static class ViewHolder {

        TextView tv_name,tv_data,tv_state,tv_ispass;
        ImageView iv_ispass;
    }
}
