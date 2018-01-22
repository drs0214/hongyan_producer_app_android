package com.aerozhonghuan.hongyan.producer.modules.common;

import android.content.Context;
import android.content.Intent;

import com.aerozhonghuan.hongyan.producer.modules.home.MainActivity;
import com.aerozhonghuan.hongyan.producer.modules.user.LoginActivity;

/**
 * 构建 inent
 * Created by zhangyunfei on 17/7/22.
 */

public class ActivityDispatcher {

    public static Intent getIntent_MainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    /**
     * 获得 登录页面的inent，当退出登录时
     * @param context
     * @return
     */
    public static Intent getIntent_LoginOnLogout(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        return intent;
    }
}
