package com.aerozhonghuan.hongyan.producer.modules.transportScan.fragment;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.GridView;

        import com.aerozhonghuan.foundation.base.BaseFragment;
        import com.aerozhonghuan.hongyan.producer.R;
        import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
        import com.aerozhonghuan.hongyan.producer.modules.transportScan.activity.ManyScanResultActivity;
        import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.Authorization_actionsAdapter;
        import com.aerozhonghuan.hongyan.producer.modules.transportScan.adapter.ManyScanAdapter;
        import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.ManyScanBean;
        import com.aerozhonghuan.hongyan.producer.modules.transportScan.entity.TransportScanDetailBean;
        import com.aerozhonghuan.hongyan.producer.modules.transportScan.logic.Transport_ScanHttpLoader;
        import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;

        import java.util.ArrayList;
        import java.util.List;

        import rx.Subscription;

/**
 * @author: drs
 * @time: 2018/1/26 13:21
 * @des:
 */
public class ManyScanFragment  extends BaseFragment {
    private View rootView;
    Authorization_actionsAdapter adapter;
    GridView gridview;
    ArrayList<TransportScanDetailBean.ActionsBean> manyscanlist=new ArrayList<TransportScanDetailBean.ActionsBean>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_many_scan, null);
            initView();
            initData();
            setListen();
        }
        return rootView;
    }

    private void setListen() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransportScanDetailBean.ActionsBean manyScanBean = manyscanlist.get(position);
                startActivity(new Intent(getContext(),ManyScanResultActivity.class));
            }
        });
    }

    private void initData() {
      /*  ManyScanBean bean=new ManyScanBean();
        bean.setName("下线");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("质检");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("入库");
        manyscanlist.add(bean);
        bean=new ManyScanBean();
        bean.setName("运输开始");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("运输结束");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("改装开始");
        manyscanlist.add(bean);
        bean=new ManyScanBean();
        bean.setName("改装开始");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("交付");
        manyscanlist.add(bean);

        bean=new ManyScanBean();
        bean.setName("盘点");
        manyscanlist.add(bean);*/
        Transport_ScanHttpLoader transport_scanHttpLoader = new Transport_ScanHttpLoader();
        Subscription subscription = transport_scanHttpLoader.actions().subscribe(new MySubscriber<List<TransportScanDetailBean.ActionsBean>>(getContext(),progressDialogIndicator) {
            @Override
            public void onNext(List<TransportScanDetailBean.ActionsBean> reslistdata) {
                if(!reslistdata.isEmpty()){
                    manyscanlist.addAll(reslistdata);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }
    private ProgressDialogIndicator progressDialogIndicator;
    private void initView() {
        progressDialogIndicator = new ProgressDialogIndicator(getContext());
        gridview=(GridView) rootView.findViewById(R.id.gridview);
        adapter=new Authorization_actionsAdapter(getContext(),manyscanlist);
        gridview.setAdapter(adapter);
    }
}
