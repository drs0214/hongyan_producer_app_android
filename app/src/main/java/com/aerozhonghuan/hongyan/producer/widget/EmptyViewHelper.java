package com.aerozhonghuan.hongyan.producer.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;

/**
 * 设置 emptyview
 * Created by zhangyunfei on 17/7/5.
 */

public class EmptyViewHelper {


    public static View setEmptyViewForListView(Context context, ListView listview1, int layoutid) {
        if (listview1.getEmptyView() != null) return listview1.getEmptyView();
        View empty_view_1 = LayoutInflater.from(context).inflate(layoutid, null);
        empty_view_1.setVisibility(View.GONE);
        ViewGroup viewGroup = (ViewGroup) listview1.getParent();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewGroup.addView(empty_view_1, layoutParams);
        listview1.setEmptyView(empty_view_1);

        empty_view_1.setId(R.id.empty_view1);
        return empty_view_1;
    }

    public static View setEmptyViewForListView(Context context, ListView listview1) {
        return setEmptyViewForListView(context, listview1, R.layout.empty_view_1);
    }

    public static TextView setTextEmptyViewForListView(Context context, ListView listview1, String text) {
        View view = setEmptyViewForListView(context, listview1, R.layout.empty_view_1);
        if (view instanceof TextView) {
            TextView view1 = (TextView) view;
            if (!TextUtils.isEmpty(text)) {
                view1.setText(text);
            }
            return view1;
        } else {
            return null;
        }
    }

}
