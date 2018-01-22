package com.aerozhonghuan.hongyan.producer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.aerozhonghuan.hongyan.producer.R;

/**
 * 描述:
 * 作者:zhangyonghui
 * 创建日期：2017/7/21 0021 on 下午 12:41
 */

public class CouponDisPlayView extends LinearLayout {

    private Paint mPaint;
    /**
     * 圆间距
     */
    private float gap = 8;
    /**
     * 半径
     */
    private float radius = 10;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;

    private boolean isShowLeftSawtooth;
    private boolean isShowRightSawtooth;
    private boolean isShowTopSawtooth;
    private boolean isShowBlowSawtooth;


    public CouponDisPlayView(Context context) {
        super(context);
    }

    public CouponDisPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CouponDisPlayView);
        isShowLeftSawtooth = typedArray.getBoolean(R.styleable.CouponDisPlayView_leftSawtooth, false);
        isShowRightSawtooth = typedArray.getBoolean(R.styleable.CouponDisPlayView_rightSawtooth, false);
        isShowTopSawtooth = typedArray.getBoolean(R.styleable.CouponDisPlayView_topSawtooth, false);
        isShowBlowSawtooth = typedArray.getBoolean(R.styleable.CouponDisPlayView_blowSawtooth, false);
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(getResources().getColor(R.color.colorBackgroud));
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isShowBlowSawtooth || isShowTopSawtooth) {
            if (remain == 0) {
                remain = (int) (w - gap) % (2 * radius + gap);
            }
            circleNum = (int) ((w - gap) / (2 * radius + gap));
        } else {
            if (remain == 0) {
                remain = (int) (h - gap) % (2 * radius + gap);
            }
            circleNum = (int) ((h - gap) / (2 * radius + gap));
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleNum; i++) {
            float x = gap + radius + remain / 2 + ((gap + radius * 2) * i);
            if (isShowTopSawtooth) {
                canvas.drawCircle(x, 0, radius, mPaint);
            }
            if (isShowBlowSawtooth) {
                canvas.drawCircle(x, getHeight(), radius, mPaint);
            }
            if (isShowLeftSawtooth) {
                canvas.drawCircle(0, x, radius, mPaint);
            }
            if (isShowRightSawtooth) {
                canvas.drawCircle(getWidth(), x, radius, mPaint);
            }
        }
    }
}
