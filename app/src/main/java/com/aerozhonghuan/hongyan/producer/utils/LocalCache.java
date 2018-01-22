package com.aerozhonghuan.hongyan.producer.utils;

import android.content.Context;

import com.aerozhonghuan.foundation.utils.LocalStorage;

/**
 * 本地缓存
 * Created by zhangyunfei on 17/6/30.
 */

public class LocalCache extends LocalStorage {
    public LocalCache(Context context) {
        super(context,"local_cache");
    }

}
