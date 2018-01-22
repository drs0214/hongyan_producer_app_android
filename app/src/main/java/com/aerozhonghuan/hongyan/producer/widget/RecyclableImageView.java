package com.aerozhonghuan.hongyan.producer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import uk.co.senab.photoview.PhotoView;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/7/18 0018 on 上午 1:59
 */

public class RecyclableImageView extends PhotoView {
    public RecyclableImageView(Context context) {
        super(context);
    }

    public RecyclableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

}
