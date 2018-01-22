package com.aerozhonghuan.hongyan.producer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.aerozhonghuan.hongyan.producer.R;


/**
 * 创建日期：2017/6/20.
 * 描述: 底部导航栏button控件
 * 更改颜色
 * 添加消息数量
 * 添加小红点提示
 * 默认:如果有消息或者小红点,选中后取消
 * 作者:zhangyonghui
 */
public class TabButton extends View {

    //初始显示的图标
    private Bitmap mBitmap;
    //选中之后显示的图标
    private Bitmap mClickBitmap;
    //未选中的颜色
    private int mColor = 0xFFAAAAAA;
    //选中之后的颜色
    private int mClickColor = 0xFF3F9FE0;
    //圆形消息的颜色
    private int mColor_message = 0xffff4310;
    //字体大小
    private float mTextSize;
    //显示的文本
    private String mText = "";
    //选中图标的透明度，0f为未选中，1f为选中
    private float mAlpha = 0f;
    //画图位置
    private Rect mBitmapRect;
    private Rect mTextRect;
    //文本的画笔
    private Paint mTextPaint;
    //记录消息数量
    private int mMessageNumber = 0;
    //是否选中
    private boolean isSelected = false;
    //是否画小圆点
    private boolean isRedPoint = false;
    private int iconWidth;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.TabButton);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.TabButton_image:
                    BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(attr);
                    mBitmap = drawable.getBitmap();
                    break;
                case R.styleable.TabButton_clickimage:
                    BitmapDrawable clickdrawable = (BitmapDrawable) typedArray.getDrawable(attr);
                    mClickBitmap = clickdrawable.getBitmap();
                    break;
                case R.styleable.TabButton_clickcolor:
                    mClickColor = typedArray.getColor(attr, 0xFF3F9FE0);
                    break;
                case R.styleable.TabButton_text:
                    mText = typedArray.getString(attr);
                    break;
                case R.styleable.TabButton_text_size:
                    mTextSize = typedArray.getDimension(attr, 12);
                    break;
            }
        }
        typedArray.recycle();
        mTextRect = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        mTextPaint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int padding = dp2px(getContext(), 8);
        iconWidth = Math.min(getMeasuredWidth() - padding * 2, getMeasuredHeight() - padding * 2 - mTextRect.height());
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - (mTextRect.height() + iconWidth) / 2;
        mBitmapRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isSelected) {
            drawText(canvas, mColor);
            drawBitmap(canvas, mBitmap);
        } else {
            drawText(canvas, mClickColor);
            drawBitmap(canvas, mClickBitmap);
        }
        if (mMessageNumber > 0) {
            drawMessages(canvas);
        }
        if (isRedPoint) {
            drawRedPoint(canvas);
        }
    }

    /**
     * 绘制文本,直接绘制颜色
     *
     * @param canvas
     * @param color
     */
    private void drawText(Canvas canvas, int color) {
        mTextPaint.setColor(color);
        int x = getMeasuredWidth() / 2 - mTextRect.width() / 2;
        int y = mBitmapRect.bottom + mTextRect.height();
        canvas.drawText(mText, x, y, mTextPaint);
        canvas.translate(0, -5);
    }

    /**
     * 画图标
     *
     * @param canvas
     * @param bitmap
     */
    private void drawBitmap(Canvas canvas, Bitmap bitmap) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawBitmap(bitmap, null, mBitmapRect, null);
    }

    /**
     * 画消息数量
     *
     * @param canvas
     */
    private void drawMessages(Canvas canvas) {
        //数字画笔内容大小等创建
        Paint textPaint = new Paint();
        Rect textRect = new Rect();
        String text = mMessageNumber > 99 ? "99+" : mMessageNumber + "";
        int textSize = 0;
        if (text.length() == 1) {
            textSize = dp2px(getContext(), 12);
        } else if (text.length() == 2) {
            textSize = dp2px(getContext(), 10);
        } else {
            textSize = dp2px(getContext(), 9);
        }
        textPaint.setColor(0xDDFFFFFF);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setTypeface(Typeface.MONOSPACE);
        textPaint.getTextBounds(text, 0, text.length(), textRect);
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        //画圆
        int width = dp2px(getContext(), 15);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor_message);
        RectF messageRectF = new RectF(mBitmapRect.right - (width * 2) / 3, mBitmapRect.top - width / 4, mBitmapRect.right + width / 3, mBitmapRect.top + (width * 3) / 4);
        canvas.drawOval(messageRectF, paint);
        //画数字
        float x = messageRectF.right - messageRectF.width() / 2f;
        float y = messageRectF.bottom - messageRectF.height() / 2f - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;
        canvas.drawText(text, x, y, textPaint);
    }

    /**
     * 画小圆点提示
     *
     * @param canvas
     */
    private void drawRedPoint(Canvas canvas) {
        //画圆
        int width = dp2px(getContext(), 8);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor_message);
        RectF messageRectF = new RectF(mBitmapRect.right - width / 2, mBitmapRect.top - width / 3, mBitmapRect.right + width / 2, mBitmapRect.top + (width * 2) / 3);
        canvas.drawOval(messageRectF, paint);
    }

    /**
     * 消息数量变化并刷新
     *
     * @param number
     */
    public void addMessageNumber(int number) {
        mMessageNumber += number;
        invalidateView();
    }

    /**
     * 设置消息数量
     */
    public void setMessageNumber(int number) {
        mMessageNumber = number;
        invalidateView();
    }

    /**
     * 设置小红点
     */
    public void showRedPoint(boolean isShowRedPoint) {
        isRedPoint = isShowRedPoint;
        invalidateView();
    }

    /**
     *
     */
    public void setSelect(boolean flag) {
        isSelected = flag;
//        if (isSelected) {
//            mMessageNumber = 0;
//            isRedPoint = false;
//        }
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }
}