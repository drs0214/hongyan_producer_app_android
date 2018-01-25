package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.content.Context;

import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserBean;

/**
 * 用户信息
 * Created by zhangyunfei on 17/6/29.
 */

public class UserInfoWebRequest {
    private static final String TAG = "UserInfoWebRequest";

    //获取用户
    public static void getUserInfoFromWeb(final Context context, final OnGetUserInfoCallback onGetUserInfoCallback) {
        if (UserInfoManager.getCurrentUserBaseInfo() == null) return;

        // TODO: 2018/1/25 更新用户信息
    }

    public interface OnGetUserInfoCallback {
        void onSuccess(UserBean userBean);

        void onFailure(String err);
    }
}
