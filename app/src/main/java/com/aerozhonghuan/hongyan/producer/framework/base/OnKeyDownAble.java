package com.aerozhonghuan.hongyan.producer.framework.base;

import android.view.KeyEvent;

/**
 * 指示可以处理 onKeyDown
 * Created by zhangyunfei on 17/7/21.
 */

public interface OnKeyDownAble {

    boolean onKeyDown(int keyCode, KeyEvent event);

}
