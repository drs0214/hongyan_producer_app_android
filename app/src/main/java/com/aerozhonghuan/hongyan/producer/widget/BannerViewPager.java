package com.aerozhonghuan.hongyan.producer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.modules.home.entity.HomeBannerInfo;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * 描述:自定义轮播ViewPager(首页)
 * 无限轮播,点击监听,手指按住停止轮播
 * 作者:zhangyonghui
 * 创建日期：2017/6/21 0021 on 下午 1:30
 */

public class BannerViewPager extends ViewPager {
    private List<HomeBannerInfo> bannerInfoList;
    private OnItemClickListener onItemClickListener;
    private boolean isRunning = false;//是否自动轮播的标志，默认不自动轮播
    private Runnable runnable;
    private int downTime = 0;//按下时间
    //按下的XY坐标
    private int downX = 0;
    private int downY = 0;
    private BannerAdapter adapter;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 设置数据和适配器
    public void setImageUrlsAndAdapter(List<HomeBannerInfo> bannerInfoList) {
        this.bannerInfoList = bannerInfoList;
        adapter = new BannerAdapter();
        setAdapter(adapter);
        // 设置当前的一个条目值
        setCurrentItem(bannerInfoList.size() * 50);
        // 设置切换动画
//        setPageTransformer(true, new DepthPageTransformer());
        //设置滑动速度
        setPagerScrollSpeed();
    }

    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    // 开始轮播
    public void startRoll() {
        isRunning = true;
        this.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    setCurrentItem(getCurrentItem() + 1);
                    postDelayed(this, 4000);
                }
            }
        }, 4000);
    }

    // 停止轮播
    public void stopRoll() {
        isRunning = false;
        if (runnable != null) {
            this.getHandler().removeCallbacks(runnable);
        }
    }

    // 关联底部圆点
    public void relevancePoint(final ArrayList<ImageView> dotsList) {
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsList.size(); i++) {
                    if (i == position % bannerInfoList.size()) {
                        dotsList.get(i).setImageResource(R.drawable.img_select);
                    } else {
                        dotsList.get(i).setImageResource(R.drawable.img_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                downTime = (int) System.currentTimeMillis();
                //停止轮播
                stopRoll();
                break;

            // 设置点击监听
            case MotionEvent.ACTION_UP:
                int upX = (int) ev.getX();
                int upY = (int) ev.getY();
                int disX = Math.abs(upX - downX);
                int disY = Math.abs(upY - downY);
                int upTime = (int) System.currentTimeMillis();
                if (upTime - downTime < 500 && disX - disY < 5) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getCurrentItem() % bannerInfoList.size());//当前位置就是显示的条目
                    }
                }
                //开启轮播
                startRoll();
                break;

            case MotionEvent.ACTION_CANCEL:
                startRoll();
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    //当控件挂载到页面上会调用此方法
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    //当控件从页面上移除的时候会调用此方法
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRoll();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 利用反射更改滑动速度
     */
    private void setPagerScrollSpeed() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            ViewPagerScroller scroller = new ViewPagerScroller(getContext());
            mScroller.set(this, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * 首页轮播图adapter
     */
    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            // 拉伸图片
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext())
                    .load(bannerInfoList.get(position % bannerInfoList.size()).getImgPath())
                    .placeholder(R.mipmap.img_vp_nomal)
                    .error(R.mipmap.img_vp_nomal)
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    /**
     * viewpager切换动画
     */
    private class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.9f;
        private static final float MIN_ALPHA = 0.6f;

        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            Log.e("TAG", view + " , " + position + "");

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * 自定义viewpager滑动速度类
     */
    private class ViewPagerScroller extends Scroller {
        private int mScrollDuration = 800;// 滑动速度

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }
    }
}
