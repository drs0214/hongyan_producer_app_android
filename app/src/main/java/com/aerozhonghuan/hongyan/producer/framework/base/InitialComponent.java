package com.aerozhonghuan.hongyan.producer.framework.base;

import android.app.Activity;

/**
 * 组件的初始化
 * Created by zhangyunfei on 17/7/22.
 */

public interface InitialComponent {

    void onInit(Activity context);

    void clearup(Activity activity);
}
