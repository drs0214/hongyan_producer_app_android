package com.aerozhonghuan.hongyan.producer.modules.home.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerozhonghuan.foundation.base.BaseFragment;
import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.framework.base.MySubscriber;
import com.aerozhonghuan.hongyan.producer.modules.check.CheckActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.Constents;
import com.aerozhonghuan.hongyan.producer.modules.common.WebviewActivity;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;
import com.aerozhonghuan.hongyan.producer.modules.common.logic.UserInfoManager;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.HomeBannerInfo;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.HomeConstants;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.HomeGridItemBean;
import com.aerozhonghuan.hongyan.producer.modules.home.logic.HomeHttpLoader;
import com.aerozhonghuan.hongyan.producer.modules.transportScan.TransportScanActivity;
import com.aerozhonghuan.hongyan.producer.utils.PicassoScaleTransformation;
import com.aerozhonghuan.hongyan.producer.utils.WindowUtil;
import com.aerozhonghuan.hongyan.producer.widget.BannerViewPager;
import com.aerozhonghuan.hongyan.producer.widget.ProgressDialogIndicator;
import com.aerozhonghuan.rxretrofitlibrary.RxApiManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;


/**
 * 描述: HomeFragment
 * 作者:zhangyonghui
 * 创建日期：2017/6/20  on 上午 11:27
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private static final int GIRD_COLUMN_NUM = 4;
    private BannerViewPager vp_banner;
    private List<HomeGridItemBean> homeGridItemBeanList;
    private View rootView;
    private HomeAdapter adapter;
    //    private TitleBarView titleBar;
    private List<HomeBannerInfo> bannerInfoList;
    private LinearLayout ll_dots,ll_transport_scan,ll_first_check,ll_second_check,ll_check,ll_transport;
    private ArrayList<ImageView> dotsList;
    private ImageView img_oneornoBanner;
    private HomeHttpLoader homeHttpLoader;
    private ProgressDialogIndicator progressDialogIndicator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, null);
            progressDialogIndicator = new ProgressDialogIndicator(getContext());
            homeHttpLoader = new HomeHttpLoader();
            initView();
            getUserAuthorization();
        }
        return rootView;
    }

    // 获取用户权限
    public void getUserAuthorization() {
        Subscription subscription = homeHttpLoader.getAuthorization().subscribe(new MySubscriber<PermissionsBean>(getContext(), progressDialogIndicator) {

            @Override
            public void onNext(PermissionsBean permissionsBean) {
                if (permissionsBean != null && permissionsBean.permissions != null) {
                    PermissionsManager.setPermissions(permissionsBean);
                    initData();
                } else {
                    alert("数据异常");
                    UserInfoManager.logout(getContext());
                }
            }
        });
        RxApiManager.get().add(TAG,subscription);
    }

    private void initView() {
        vp_banner = (BannerViewPager) rootView.findViewById(R.id.vp_banner);
        ll_dots = (LinearLayout) rootView.findViewById(R.id.ll_dots);
        ll_check = (LinearLayout) rootView.findViewById(R.id.ll_check);
        ll_transport = (LinearLayout) rootView.findViewById(R.id.ll_transport);
        ll_transport_scan = (LinearLayout) rootView.findViewById(R.id.ll_transport_scan);
        ll_first_check = (LinearLayout) rootView.findViewById(R.id.ll_first_check);
        ll_second_check = (LinearLayout) rootView.findViewById(R.id.ll_second_check);
        img_oneornoBanner = (ImageView) rootView.findViewById(R.id.img_oneornoBanner);
    }

    private void initData() {
        if (PermissionsManager.isShowInspectionView()) {
            ll_check.setVisibility(View.VISIBLE);
        }
        if (PermissionsManager.isShowTransportView()) {
            ll_transport.setVisibility(View.VISIBLE);
        }
        if (PermissionsManager.isShowInspectionFirstCheck()) {
            ll_first_check.setVisibility(View.VISIBLE);
        }
        if (PermissionsManager.isShowInspectionSecondCheck()) {
            ll_second_check.setVisibility(View.VISIBLE);
        }
        ll_transport_scan.setOnClickListener(this);
        ll_first_check.setOnClickListener(this);
        ll_second_check.setOnClickListener(this);
        homeGridItemBeanList = new ArrayList<>();
        for (int i = 0; i < HomeConstants.HOME_GRID_ITEM_IMAGES.length; i++) {
            homeGridItemBeanList.add(new HomeGridItemBean(HomeConstants.HOME_GRID_ITEM_IMAGES[i], HomeConstants.HOME_GRID_ITEM_NAMES[i]));
        }
        setViewPager();
        // TODO: 2018/1/24 请求banner数据
        /*RequestBuilder
                .with(getContext())
                .URL(URLs.HOME_BANNER)
                .para("appType", "2")
                .onSuccess(new CommonCallback<List<HomeBannerInfo>>(typeToken) {
                    @Override
                    public void onSuccess(List<HomeBannerInfo> messsageBodyObject, CommonMessage commonMessage, String allResponseString) {
                        bannerInfoList = messsageBodyObject;
                        setViewPager();
                    }

                    @Override
                    public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                        return true;
                    }
                }).excute();*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_transport_scan:
                startActivity(new Intent(getActivity(), TransportScanActivity.class));
                break;
            case R.id.ll_first_check:
                Bundle bundle = new Bundle();
                bundle.putString("type", Constents.CHECK_TYPE_FIRSTCHECK);
                startActivity(new Intent(getActivity(), CheckActivity.class).putExtras(bundle));
                break;
            case R.id.ll_second_check:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type",Constents.CHECK_TYPE_SECONDCHECK);
                startActivity(new Intent(getActivity(), CheckActivity.class).putExtras(bundle1));
                break;
        }

    }

    /**
     * 设置首页轮播图
     */
    private void setViewPager() {
        // 判断,可能有bannerInfoList为空或者返回数量为1或0的情况
        if (bannerInfoList != null && bannerInfoList.size() > 1) {
            img_oneornoBanner.setVisibility(View.GONE);
            vp_banner.setVisibility(View.VISIBLE);
            initDots();
            vp_banner.setImageUrlsAndAdapter(bannerInfoList);
            vp_banner.relevancePoint(dotsList);
            vp_banner.setOnItemClickListener(new BannerViewPager.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    startActivity(new Intent(getActivity(), WebviewActivity.class).putExtra("title", bannerInfoList.get(position).getBannerName()).putExtra("url", bannerInfoList.get(position).getBannerLink()));
                }
            });
            vp_banner.startRoll();
        } else {
            vp_banner.setVisibility(View.GONE);
            img_oneornoBanner.setVisibility(View.VISIBLE);
            img_oneornoBanner.setAdjustViewBounds(true);
            img_oneornoBanner.setScaleType(ImageView.ScaleType.FIT_XY);
            if (bannerInfoList == null || bannerInfoList.size() == 0) {
                Picasso.with(getContext())
                        .load(R.mipmap.img_vp_nomal)
                        .into(img_oneornoBanner);
            } else {
                Picasso.with(getContext())
                        .load(bannerInfoList.get(0).getImgPath())
                        .placeholder(R.mipmap.img_vp_nomal)
                        .error(R.mipmap.img_vp_nomal)
                        .config(Bitmap.Config.RGB_565)
                        .memoryPolicy(NO_CACHE, NO_STORE)
                        .transform(new PicassoScaleTransformation())
                        .into(img_oneornoBanner);
                img_oneornoBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), WebviewActivity.class).putExtra("title", bannerInfoList.get(0).getBannerName()).putExtra("url", bannerInfoList.get(0).getBannerLink()));
                    }
                });
            }
        }
    }


    /**
     * 显示隐藏fragment时控制轮播
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            vp_banner.stopRoll();
        } else {
            vp_banner.startRoll();
        }
    }

    /**
     * 初始化小圆点
     */
    private void initDots() {
        int windowWeight = WindowUtil.getWindowWeight(getActivity());
        dotsList = new ArrayList<>();
        for (int i = 0; i < bannerInfoList.size(); i++) {
            // 画出圆点
            ImageView imageView = new ImageView(getContext());
            if (i == 0) {
                imageView.setImageResource(R.drawable.img_select);
            } else {
                imageView.setImageResource(R.drawable.img_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(windowWeight / 25, windowWeight / 25);
            params.setMargins(5, 0, 5, 0);
            dotsList.add(imageView);
            ll_dots.addView(imageView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxApiManager.get().cancel(TAG);
    }

    /**
     * 首页九宫格点击item监听接口
     */
    private interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    /**
     * home页九宫格adapter
     */
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        private OnItemClickLitener mOnItemClickLitener;
        private boolean isItemShowOver = false;
        private boolean delayEnterAnimation = true;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_home_grid, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }
            holder.tv_item.setText(homeGridItemBeanList.get(position).getText());
            holder.iv_item.setImageResource(homeGridItemBeanList.get(position).getImgId());
            if (homeGridItemBeanList.get(position).isShowRedPoint()) {
                holder.iv_point.setVisibility(View.VISIBLE);
            } else {
                holder.iv_point.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return homeGridItemBeanList.size();
        }

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv_item;
            ImageView iv_item;
            ImageView iv_point;

            public MyViewHolder(View view) {
                super(view);
                iv_item = (ImageView) view.findViewById(R.id.iv_item);
                tv_item = (TextView) view.findViewById(R.id.tv_item);
                iv_point = (ImageView) view.findViewById(R.id.iv_point);
            }
        }
    }
}
