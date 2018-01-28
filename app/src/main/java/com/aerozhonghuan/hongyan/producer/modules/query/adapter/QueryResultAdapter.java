package com.aerozhonghuan.hongyan.producer.modules.query.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.QueryResultBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 16:05
 * @des:
 */
public class QueryResultAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<QueryResultBean> manyscanlist=new ArrayList<QueryResultBean>();

    public QueryResultAdapter(Context context, ArrayList<QueryResultBean> manyscanlist) {
        this.mContext = context;
        this.manyscanlist = manyscanlist;
    }

    @Override
    public int getCount() {
        return manyscanlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_query_result, null);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.tv_chassisnumber = (TextView) convertView.findViewById(R.id.tv_chassisnumber);
            holder.tv_operationtime = (TextView) convertView.findViewById(R.id.tv_operationtime);
            holder.tv_operationtype = (TextView) convertView.findViewById(R.id.tv_operationtype);
            holder.tv_department = (TextView) convertView.findViewById(R.id.tv_department);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        QueryResultBean QueryResultBean = manyscanlist.get(position);
        holder.tv_number.setText(QueryResultBean==null?"":QueryResultBean.getBianhao());
        holder.tv_chassisnumber.setText(QueryResultBean==null?"":QueryResultBean.getDipanhao());
        holder.tv_operationtime.setText(QueryResultBean==null?"":QueryResultBean.getOperation_time());
        holder.tv_operationtype.setText(QueryResultBean==null?"":QueryResultBean.getOperation_type());
        holder.tv_department.setText(QueryResultBean==null?"":QueryResultBean.getTv_department());
        return convertView;
    }

    private static class ViewHolder {

        TextView tv_number,tv_chassisnumber,tv_operationtime,tv_operationtype,tv_department;
    }
}
