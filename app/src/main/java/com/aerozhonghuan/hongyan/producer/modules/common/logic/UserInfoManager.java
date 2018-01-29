package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.foundation.utils.LocalStorage;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.modules.common.ActivityDispatcher;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.PermissionsManager;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by zhangyonghui
 */

public class UserInfoManager {

    private static final String KEY_COOKIE_SESSION = "JSESSIONID";
    private static final String KEY_AUTHORIZATION = "PERMISSIONS";
    private static final String KEY_USER_INFO = "userInfo";
    private static final String TAG = "UserInfoManager";
    private static final Gson gson = new Gson();
    /**
     * 当前 是否已经登录了用户
     *
     * @return
     */
    public static boolean isUserAuthenticated() {
        return !TextUtils.isEmpty(UserInfoManager.getCookieSession());
    }


    public static void saveCookieSession(String session) {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        localStorage.putString(KEY_COOKIE_SESSION, session);
    }

    public static String getCookieSession() {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        String session = localStorage.getString(KEY_COOKIE_SESSION);
        if (TextUtils.isEmpty(session)) {
            return null;
        }
        return session;
    }

    public static void saveUserInfo(UserInfo userInfo) {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        String json = gson.toJson(userInfo);
        localStorage.putString(KEY_USER_INFO, json);
    }

    public static UserInfo getCurrentUserInfo() {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        String userInfoStr = localStorage.getString(KEY_USER_INFO);
        if (TextUtils.isEmpty(userInfoStr)) {
            return null;
        }
        UserInfo userinfo = null;
        try {
            userinfo = gson.fromJson(userInfoStr, UserInfo.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return userinfo;
    }


    public static void saveAuthorization(String permissions) {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        localStorage.putString(KEY_AUTHORIZATION, permissions);
    }

    public static String getAuthorization() {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        String permissions = localStorage.getString(KEY_AUTHORIZATION);
        if (TextUtils.isEmpty(permissions)) {
            return null;
        }
        return permissions;
    }

    /**
     * 清除 基本信息和详情信息
     */
    public static void clearCurrentUser() {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        localStorage.remove(KEY_COOKIE_SESSION);
        localStorage.remove(KEY_AUTHORIZATION);
        localStorage.remove(KEY_USER_INFO);
    }

    /**
     * 退出登录 当前用户
     */
    public static void logout(Context context) {
        LogUtil.d(TAG, "准备 退出登录");
        String session = getCookieSession();
        if (TextUtils.isEmpty(session))
            return;
        UserInfoManager.clearCurrentUser();
        PermissionsManager.clearPermissions();
        // TODO: 2018/1/25 退出登录

        Intent intent = ActivityDispatcher.getIntent_LoginOnLogout(context);
        context.startActivity(intent);
    }
}
