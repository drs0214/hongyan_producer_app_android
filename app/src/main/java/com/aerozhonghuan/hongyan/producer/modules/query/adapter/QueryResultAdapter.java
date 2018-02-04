package com.aerozhonghuan.hongyan.producer.modules.query.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.QueryResultBean;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.Query_ResultBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 16:05
 * @des:
 */
public class QueryResultAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Query_ResultBean> manyscanlist=new ArrayList<Query_ResultBean>();

    public QueryResultAdapter(Context context, ArrayList<Query_ResultBean> manyscanlist) {
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
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_operationtime = (TextView) convertView.findViewById(R.id.tv_operationtime);
            holder.tv_operationtype = (TextView) convertView.findViewById(R.id.tv_operationtype);
            holder.tv_department = (TextView) convertView.findViewById(R.id.tv_department);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        Query_ResultBean query_resultBean = manyscanlist.get(position);
        holder.tv_number.setText(query_resultBean.getVhcle()==null?"":query_resultBean.getVhcle());
        holder.tv_chassisnumber.setText(query_resultBean.getVhvin()==null?"":query_resultBean.getVhvin());
        holder.tv_name.setText(query_resultBean.getCreateUsername()==null?"":query_resultBean.getCreateUsername());
        holder.tv_operationtime.setText(query_resultBean.getActionDate()==null?"":query_resultBean.getActionDate());
        holder.tv_operationtype.setText(query_resultBean.getActionText()==null?"":query_resultBean.getActionText());
//        holder.tv_department.setText(query_resultBean==null?"":query_resultBean.getTv_department());//操作人所属部门
        return convertView;
    }

    private static class ViewHolder {

        TextView tv_number,tv_chassisnumber,tv_name,tv_operationtime,tv_operationtype,tv_department;
    }
}
