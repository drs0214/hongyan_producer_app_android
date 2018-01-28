package com.aerozhonghuan.hongyan.producer.modules.query.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.query.fragment.Operation_TimeFragment;
import com.aerozhonghuan.hongyan.producer.modules.query.fragment.Operation_TypeFragment;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment.ManyScanFragment;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment.SingleScanFragment;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

/**
 * @author: drs
 * @time: 2018/1/27 16:16
 * @des:
 */
public class Query_Transport_Scan_HistoryActivity extends TitlebarActivity implements View.OnClickListener {
    private TitleBarView titleBar;
    FrameLayout fl_content;
    LinearLayout ll_single_scan,ll_many_scan;
    TextView tv_single_scan,tv_many_scan;
    View view_single,view_many;
    Operation_TimeFragment singleScanFragment;
    Operation_TypeFragment manyScanFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_transport_scan_history);
        initView();
        initData();
        setListen();
    }

    private void initView() {
        titleBar = (TitleBarView)findViewById(R.id.titlebarview1);
        titleBar.setTitle(getResources().getString(R.string.transport_scan));
        fl_content= (FrameLayout) findViewById(R.id.fl_content);
        ll_single_scan= (LinearLayout) findViewById(R.id.ll_single_scan);
        ll_many_scan= (LinearLayout) findViewById(R.id.ll_many_scan);
        tv_single_scan= (TextView) findViewById(R.id.tv_single_scan);
        tv_many_scan= (TextView) findViewById(R.id.tv_many_scan);
        view_single= (View) findViewById(R.id.view_single);
        view_many= (View) findViewById(R.id.view_many);
    }

    private void initData() {
        if (singleScanFragment==null){
            singleScanFragment=new Operation_TimeFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, singleScanFragment).commit();
    }

    private void setListen() {
        ll_single_scan.setOnClickListener(this);
        ll_many_scan.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //单量扫描
            case R.id.ll_single_scan:
                if (singleScanFragment==null){
                    singleScanFragment=new Operation_TimeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, singleScanFragment).commit();
                tv_single_scan.setTextColor(getResources().getColor(R.color.chujian_blue));
                tv_many_scan.setTextColor(getResources().getColor(R.color.text_tj));
                view_single.setBackgroundResource(R.color.chujian_blue);
                view_many.setBackgroundResource(R.color.white);

                break;
            //批量扫描
            case R.id.ll_many_scan:
                if (manyScanFragment==null){
                    manyScanFragment=new Operation_TypeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, manyScanFragment).commit();
                tv_many_scan.setTextColor(getResources().getColor(R.color.chujian_blue));
                tv_single_scan.setTextColor(getResources().getColor(R.color.text_tj));
                view_many.setBackgroundResource(R.color.chujian_blue);
                view_single.setBackgroundResource(R.color.white);
                break;

        }
    }
}
