package com.aerozhonghuan.hongyan.producer.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.aerozhonghuan.hongyan.producer.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 进行截屏工具类
 * 
 */
public class ScreenShotUtils {
	/**
	 * 进行截取屏幕
	 * 
	 * @param pActivity
	 * @return bitmap
	 */
	public static Bitmap takeScreenShot(View view) {
		Bitmap bitmap = null;
		// 设置是否可以进行绘图缓存
		view.setDrawingCacheEnabled(true);
		// 如果绘图缓存无法，强制构建绘图缓存
		view.buildDrawingCache();
		// 返回这个缓存视图
		bitmap = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		// 测量屏幕宽和高
		view.getWindowVisibleDisplayFrame(frame);

		int width = view.getWidth();
		int height = view.getHeight();
		// 根据坐标点和需要的宽和高创建bitmap
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
		return bitmap;
	}

	/**
	 * 截图
	 * 
	 * @param scrollView
	 * @return 截图并且保存sdcard成功返回true，否则返回false
	 */
	public static boolean shotBitmap(ScrollView scrollView,String filePath) {

		int h = 0;
		Bitmap bitmap = null;
		// 获取listView实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			scrollView.getChildAt(i).setBackgroundResource(R.drawable.add_device_bg);
		}
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		// 测试输出
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
			if (null != out) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			return false;
		}
		return true;

		// return ScreenShotUtils.savePic(takeScreenShot(view),
		// com.mapbar.android.obd.AppSettings.SHARE_PIC_PATH);
	}

	
	/**
	 * 是否开启常亮模式
	 * @param b
	 * @param mContext
	 */
	public static void isBright(Boolean b, Context mContext){
		if(b){
			((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}else {
			((Activity)mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}
}
