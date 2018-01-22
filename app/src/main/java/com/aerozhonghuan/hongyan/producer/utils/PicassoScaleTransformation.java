package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.WindowManager;

import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;
import com.squareup.picasso.Transformation;

/**
 * 描述:picasso加载等比例缩放,减少内存消耗
 * 作者:zhangyonghui
 * 创建日期：2017/7/18 0018 on 下午 1:21
 */

public class PicassoScaleTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = ((WindowManager) MyAppliation.getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        if (source.getWidth() == 0) {
            return source;
        }

        //如果图片小于设置的宽度，则返回原图
        if (source.getWidth() < targetWidth) {
            return source;
        } else {
            //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            } else {
                return source;
            }
        }
    }

    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }
}
