package com.aerozhonghuan.hongyan.producer.modules.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aerozhonghuan.hongyan.producer.R;
import com.aerozhonghuan.hongyan.producer.utils.PicassoScaleTransformation;
import com.aerozhonghuan.hongyan.producer.utils.WindowUtil;
import com.aerozhonghuan.hongyan.producer.widget.PhotoViewPager;
import com.aerozhonghuan.hongyan.producer.widget.RecyclableImageView;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * 图片轮播Activity
 */
public class PhotoDialogActivity extends Activity {

    public static final int FLAG_NEWSUBSCRIBE = 101;
    public static final int FLAG_SUBSCRIBE_INFO = 102;
    public static final int FLAG_FORUM = 103;
    private static final String TAG = "PhotoDialogActivity";
    private int currentPosition;
    private String[] imageUrls;
    private PhotoAdapter adapter;
    private PhotoViewPager vp_photo;
    private int flag;
    private LinearLayout ll_dots;
    private ArrayList<ImageView> dotsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
        initData();
    }

    private void initView() {
        vp_photo = (PhotoViewPager) findViewById(R.id.vp_photo);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
    }

    private void initData() {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        imageUrls = intent.getStringArrayExtra("imageUrls");
        flag = intent.getIntExtra("flag", 0);
        adapter = new PhotoAdapter();
        if (imageUrls.length > 1) {
            initDots();
            relevancePoint();
        }
        vp_photo.setAdapter(adapter);
        vp_photo.setCurrentItem(currentPosition, false);
        vp_photo.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
            }
        });
    }

    /**
     * 初始化小圆点
     */
    private void initDots() {
        int windowWeight = WindowUtil.getWindowWeight(this);
        dotsList = new ArrayList<>();
        for (int i = 0; i < imageUrls.length; i++) {
            // 画出圆点
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(R.drawable.dots_focus);
            } else {
                imageView.setImageResource(R.drawable.dots_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(windowWeight / 25, windowWeight / 25);
            params.setMargins(5, 0, 5, 0);
            dotsList.add(imageView);
            ll_dots.addView(imageView, params);
        }
    }

    // 关联底部圆点
    public void relevancePoint() {
        vp_photo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsList.size(); i++) {
                    if (i == position % imageUrls.length) {
                        dotsList.get(i).setImageResource(R.drawable.dots_focus);
                    } else {
                        dotsList.get(i).setImageResource(R.drawable.dots_normal);
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
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "photoActivity::onDestroy");
        Runtime.getRuntime().gc();

    }

    private class PhotoAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            RecyclableImageView item = (RecyclableImageView) object;
            item.setImageBitmap(null);
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String url = imageUrls[position];
            RecyclableImageView photoView = new RecyclableImageView(PhotoDialogActivity.this);
            if (flag == FLAG_NEWSUBSCRIBE) {
                Picasso.with(PhotoDialogActivity.this)
                        .load(new File(url))
                        .config(Bitmap.Config.RGB_565)
                        .memoryPolicy(NO_CACHE, NO_STORE)
                        .error(R.drawable.img_error)
                        .transform(new PicassoScaleTransformation())
                        .into(photoView);
            } else if (flag == FLAG_SUBSCRIBE_INFO || flag == FLAG_FORUM) {
                Picasso.with(PhotoDialogActivity.this)
                        .load(url)
                        .config(Bitmap.Config.RGB_565)
                        .memoryPolicy(NO_CACHE, NO_STORE)
                        .error(R.drawable.img_error)
                        .transform(new PicassoScaleTransformation())
                        .into(photoView);
            }

            container.addView(photoView);
            return photoView;
        }
    }
}
