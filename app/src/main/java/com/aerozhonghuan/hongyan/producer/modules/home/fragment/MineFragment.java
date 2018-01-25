package com.aerozhonghuan.hongyan.producer.modules.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.foundation.eventbus.EventBusManager;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.Constants;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.modules.common.AboutActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserBean;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoDao;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.QueryTaskNumAndScoreTotalEvent;
import com.aerozhonghuan.hongyan.producer.modules.user.beans.AccountUpdateEvent;
import com.aerozhonghuan.hongyan.producer.utils.AppUtil;
import com.aerozhonghuan.hongyan.producer.utils.NetUtils;
import com.aerozhonghuan.hongyan.producer.utils.SimpleSettings;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述:我的
 * 作者:zhangyonghui
 * 创建日期：2017/6/20 0020 on 上午 11:28
 */

public class MineFragment extends BaseFragment {
    public static final String MENU_ITEM_MY_CAR = "我的车辆";
    public static final String MENU_ITEM_UPDATE = "版本更新";
    public static final String MENU_ITEM_CERTIFICATION = "资料认证";
    public static final String MENU_ITEM_ABOUTUS = "关于我们";
    public static final String MENU_ITEM_SUBSCRIBE = "我的预约";
    public static final String MENU_ITEM_CLEARCACHE = "清除缓存";
    public static final String MENU_ITEM_MYCOUPON = "我的优惠券";
    private static final String TAG = "MineFragment";
    private View rootView;
    private ViewGroup section1;
    private ViewGroup section2;
    private LayoutInflater mLayoutInflater;
    private List<MenuItem> sectionList1;
    private List<MenuItem> sectionList2;
    //    private TitleBarView titlebarview1;
    private TextView textview_name;
    private TextView textview_phone;
    private View row_userinfo;
    private OnItemClick mOnItemClick = new OnItemClick() {
        @Override
        public void onItemClick(String title) {
            if (MENU_ITEM_UPDATE.equals(title)) {
                if (!NetUtils.isConnected(MyApplication.getApplication())) {
                    alert("网络异常,请稍后查询");
                } else {

                }
            } else if (MENU_ITEM_ABOUTUS.equals(title)) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            } else if (MENU_ITEM_CERTIFICATION.equals(title)) {
                alert(Constants.NOT_OPEN_MOUDLE);
            } else if (MENU_ITEM_CLEARCACHE.equals(title)) {
                SimpleSettings.clearCacheDir();
                alert("正在清除缓存...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        alert("清除成功");
                    }
                }, 1000);
            }
        }
    };
    private ImageView imageview_photo;
    private LinearLayout ll_jifenstore;
    private LinearLayout ll_jifentask;
    private TextView tv_jifen_tasknum;
    private TextView tv_jifen_scoretotal;

    private void buildSectionViews(Context context, List<MenuItem> datasource, ViewGroup viewGroup, final OnItemClick onItemClick) {
        if (context == null) throw new NullPointerException();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        MyAdapter adapter1 = new MyAdapter(datasource, context);

        for (int i = 0; i < adapter1.getCount(); i++) {
            View convertView = null;
            convertView = adapter1.getView(i, convertView, null);
            viewGroup.addView(convertView);
            View line = layoutInflater.inflate(R.layout.split_line, null);
            viewGroup.addView(line);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null && v.getTag() instanceof ViewHolder) {
                        ViewHolder viewHolder = (ViewHolder) v.getTag();
                        if (onItemClick != null) {
                            onItemClick.onItemClick(viewHolder.textViewTitle.getText().toString());
                        }
                    }
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mine_fragment, null);
            section1 = (ViewGroup) rootView.findViewById(R.id.section1);
            section2 = (ViewGroup) rootView.findViewById(R.id.section2);
            row_userinfo = rootView.findViewById(R.id.row_userinfo);
            row_userinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018/1/22 个人详情
                }
            });
            ll_jifenstore = (LinearLayout) rootView.findViewById(R.id.ll_jifenstore);
            ll_jifenstore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    alert(Constants.NOT_OPEN_MOUDLE);
                }
            });
            ll_jifentask = (LinearLayout) rootView.findViewById(R.id.ll_jifentask);
            ll_jifentask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            textview_name = (TextView) rootView.findViewById(R.id.textview_name);
            textview_phone = (TextView) rootView.findViewById(R.id.textview_phone);

            textview_name.setText("未设置");
            textview_phone.setText("");
            imageview_photo = (ImageView) rootView.findViewById(R.id.imageview_pic);
            setCache();

            sectionList1 = new ArrayList<>();
            sectionList1.add(new MenuItem(R.drawable.ic_my_car, MENU_ITEM_MY_CAR, ""));
            sectionList1.add(new MenuItem(R.drawable.ic_ziliao_renzheng, MENU_ITEM_CERTIFICATION, ""));
            sectionList1.add(new MenuItem(R.drawable.ic_my_booking, MENU_ITEM_SUBSCRIBE, ""));
            sectionList1.add(new MenuItem(R.drawable.ic_my_cards, MENU_ITEM_MYCOUPON, ""));

            sectionList2 = new ArrayList<>();
            String version = getAppVersionName();
            sectionList2.add(new MenuItem(R.drawable.ic_app_version, MENU_ITEM_UPDATE, version));
            sectionList2.add(new MenuItem(R.drawable.ic_clear_cache, MENU_ITEM_CLEARCACHE, ""));
            sectionList2.add(new MenuItem(R.drawable.ic_about_us, MENU_ITEM_ABOUTUS, ""));

            buildSectionViews(getContext(), sectionList1, section1, mOnItemClick);
            buildSectionViews(getContext(), sectionList2, section2, mOnItemClick);
            tv_jifen_tasknum = (TextView) rootView.findViewById(R.id.tv_jifen_tasknum);
            tv_jifen_scoretotal = (TextView) rootView.findViewById(R.id.tv_jifen_scoretotal);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }


    private void setCache() {
        UserBean userbean = UserInfoDao.getCurrentUserBean();
        if (userbean != null) {
            if (!TextUtils.isEmpty(userbean.getName())) {
                textview_name.setText(userbean.getName());
            } else {
                textview_name.setText("未设置");
            }

            if (!TextUtils.isEmpty(userbean.getPhone()))
                textview_phone.setText(userbean.getPhone());
        }
        if (UserInfoManager.getCurrentUserBaseInfo() == null) return;

    }

    public String getAppVersionName() {
        return AppUtil.getAppVersionName(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBusManager.register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusManager.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadSuccess(AccountUpdateEvent event) {
        Log.i(TAG, "刷新 mine");
        UserBean userbean = UserInfoDao.getCurrentUserBean();
        if (event.getKey().equals(AccountUpdateEvent.UPDATE_PHONE)) {
            String phone = event.getValue();
            textview_phone.setText(phone);
        } else if (event.getKey().equals(AccountUpdateEvent.UPDATE_NAME)) {
            textview_name.setText(event.getValue());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    @SuppressWarnings("unused")
    public void onQueryTaskNumAndScoreTotal(QueryTaskNumAndScoreTotalEvent event) {
        if (event.getTaskNum() != 0) {
            tv_jifen_tasknum.setVisibility(View.VISIBLE);
            tv_jifen_tasknum.setText(String.valueOf(event.getTaskNum()));
        } else {
            tv_jifen_tasknum.setVisibility(View.GONE);
        }
        tv_jifen_scoretotal.setText(String.valueOf(event.getScoreTotal()));
    }

    private interface OnItemClick {
        void onItemClick(String title);
    }

    private static class MenuItem {
        int iconID;
        String title;
        String desc;

        MenuItem(int iconID, String title, String desc) {
            this.iconID = iconID;
            this.title = title;
            this.desc = desc;
        }
    }

    private static class ViewHolder {
        ImageView imageview_pic;
        TextView textViewTitle;
        TextView textview_desc;
    }

    private class MyAdapter extends BaseAdapter {
        private List<MenuItem> items;
        private LayoutInflater mLayoutInflater;

        public MyAdapter(List<MenuItem> datasource, Context context) {
            this.mLayoutInflater = LayoutInflater.from(context);
            items = datasource;
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public MenuItem getItem(int position) {
            return items == null ? null : items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = (ViewGroup) mLayoutInflater.inflate(R.layout.mine_fragment_item, null);
                viewHolder = new ViewHolder();
                viewHolder.imageview_pic = (ImageView) convertView.findViewById(R.id.imageview_pic);

                viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.textview_title);
                viewHolder.textview_desc = (TextView) convertView.findViewById(R.id.textview_desc);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            MenuItem item = getItem(position);
            if (item.iconID != 0)
                viewHolder.imageview_pic.setImageResource(item.iconID);
            viewHolder.textViewTitle.setText(item.title);
            viewHolder.textview_desc.setText(item.desc);
            return convertView;
        }
    }
}
