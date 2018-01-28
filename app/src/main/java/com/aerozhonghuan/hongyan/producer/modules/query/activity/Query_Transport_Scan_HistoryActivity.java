package com.aerozhonghuan.hongyan.producer.modules.query.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.TitlebarActivity;
import com.aerozhonghuan.hongyan.producer.modules.query.entity.OperationTypeBean;
import com.aerozhonghuan.hongyan.producer.modules.query.fragment.Operation_TimeFragment;
import com.aerozhonghuan.hongyan.producer.modules.query.fragment.Operation_TypeFragment;
import com.aerozhonghuan.hongyan.producer.modules.query.view.OperationTime;
import com.aerozhonghuan.hongyan.producer.modules.query.view.OperationType;
import com.aerozhonghuan.hongyan.producer.widget.TitleBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: drs
 * @time: 2018/1/27 16:16
 * @des:
 */
public class Query_Transport_Scan_HistoryActivity extends TitlebarActivity implements View.OnClickListener {
    private TitleBarView titleBar;
    FrameLayout fl_content;
    private OperationType mOperationType;
    private OperationTime mOperationTime;
    LinearLayout ll_single_scan,ll_many_scan,ll_operation_type,ll_operation_time;
    TextView tv_single_scan,tv_many_scan;
    View view_single,view_many;
    Operation_TimeFragment singleScanFragment;
    Operation_TypeFragment manyScanFragment;
    View line;
    private List<OperationTypeBean> data = new ArrayList<OperationTypeBean>();
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
        line=findViewById(R.id.line);
        fl_content= (FrameLayout) findViewById(R.id.fl_content);
        ll_single_scan= (LinearLayout) findViewById(R.id.ll_single_scan);
        ll_many_scan= (LinearLayout) findViewById(R.id.ll_many_scan);
        ll_operation_time= (LinearLayout) findViewById(R.id.ll_operation_time);
        ll_operation_type= (LinearLayout) findViewById(R.id.ll_operation_type);
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
        OperationTypeBean vo1 = new OperationTypeBean();
        vo1.setStr1("下线");
        vo1.setStr2("下线");
        OperationTypeBean vo2 = new OperationTypeBean();
        vo2.setChecked(true);
        vo2.setStr1("质检");
        vo2.setStr2("质检");
        OperationTypeBean vo3 = new OperationTypeBean();
        vo3.setStr1("入库");
        vo3.setStr2("入库");
        OperationTypeBean vo4 = new OperationTypeBean();
        vo4.setStr1("发车");
        vo4.setStr2("发车");
        OperationTypeBean vo5 = new OperationTypeBean();
        vo5.setStr1("改装开始");
        vo5.setStr2("改装开始");
        OperationTypeBean vo6 = new OperationTypeBean();
        vo6.setStr1("接车");
        vo6.setStr2("接车");
        data.add(vo1);
        data.add(vo2);
        data.add(vo3);
        data.add(vo4);
        data.add(vo5);
        data.add(vo6);
    }

    private void setListen() {
        ll_single_scan.setOnClickListener(this);
        ll_many_scan.setOnClickListener(this);
        ll_operation_time.setOnClickListener(this);
        ll_operation_type.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //单辆扫描
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
            //操作类型筛选
            case R.id.ll_operation_type:
                // 价格点击监听
                mOperationType = new OperationType(Query_Transport_Scan_HistoryActivity.this, data);
                mOperationType.showoperationtypePopup(line, data);
                break;
            //操作时间筛选
            case R.id.ll_operation_time:
                // 价格点击监听
                mOperationTime = new OperationTime(Query_Transport_Scan_HistoryActivity.this);
                mOperationTime.showoperationtimePopup(line);
                break;

        }
    }
}
