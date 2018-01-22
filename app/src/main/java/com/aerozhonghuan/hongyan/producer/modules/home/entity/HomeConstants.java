package com.aerozhonghuan.hongyan.producer.modules.home.entity;


import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.umeng.UmengEvents;
import com.aerozhonghuan.hongyan.producer.modules.firstcheck.FirstCheckActivity;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/6/21 0021 on 下午 3:36
 */

public class HomeConstants {
    public static final int[] HOME_GRID_ITEM_IMAGES = new int[]{R.mipmap.ic_home_item_tripanalyze, R.mipmap.ic_analis_safe, R.mipmap.ic_home_item_faultdiagnose,
            R.mipmap.ic_home_item_reservation};
    public static final String[] HOME_GRID_ITEM_NAMES = new String[]{"行程分析", "安全分析", "故障诊断", "服务预约"};
    public static final Class[] HOME_GRID_ITEM_INTENTACTIVITY = new Class[]{FirstCheckActivity.class, FirstCheckActivity.class, FirstCheckActivity.class,
            FirstCheckActivity.class};
    public static final String[] UMENG_EVENTS = new String[]{UmengEvents.SHOUYE_1, UmengEvents.SHOUYE_2, UmengEvents.SHOUYE_3, UmengEvents.SHOUYE_4};
    public static final int ANALYSIS_POSITION = 0;
    public static final int SAFE_ANALYSIS_POSITION = 1;
    public static final int FAULT_POSITION = 2;
    public static final int SUBSCRIBE_POSITION = 3;

    private HomeConstants() {
    }

}
