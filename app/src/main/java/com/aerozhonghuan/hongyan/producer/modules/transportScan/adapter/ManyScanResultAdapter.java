package com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.DoActionBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanResultBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanResultBean;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.Transport_Scan_OrderBean;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/1/26 16:05
 * @des:
 */
public class ManyScanResultAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<DoActionBean> manyscanlist=new ArrayList<DoActionBean>();

    public ManyScanResultAdapter(Context context,  ArrayList<DoActionBean> manyscanlist) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_transport_scan_result, null);
            holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holder.tv_chassisnumber = (TextView) convertView.findViewById(R.id.tv_chassisnumber);
            holder.tv_scan_type = (TextView) convertView.findViewById(R.id.tv_scan_type);
            holder.tv_scan_result = (TextView) convertView.findViewById(R.id.tv_scan_result);
            holder.fail_cause = (TextView) convertView.findViewById(R.id.fail_cause);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        DoActionBean doActionBean = manyscanlist.get(position);
        holder.tv_number.setText(doActionBean.getVhcle()==null?"":doActionBean.getVhcle());
        holder.tv_chassisnumber.setText(doActionBean.getVhvin8()==null?"":doActionBean.getVhvin8());
        holder.tv_scan_type.setText(doActionBean.getActionText()==null?"":doActionBean.getActionText());
        if (doActionBean.isSuccess()){
            holder.tv_scan_result.setText("成功");
        }else{
            holder.tv_scan_result.setText("失败");
        }
        holder.fail_cause.setText(doActionBean.getMessage()==null?"":doActionBean.getMessage());
        return convertView;
    }

    private static class ViewHolder {

        TextView tv_number,tv_chassisnumber,tv_scan_type,tv_scan_result,fail_cause;
    }
}
