package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.text.TextUtils;

import com.aerozhonghuan.foundation.utils.LocalStorage;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by zhangyonghui
 */

public class UserInfoDao {

    private static final Gson gson = new Gson();
    private static final String KEY_USER_INFO = "userInfo";
    private static final String KEY_USER_BEAN = "userBean";

    public static void saveUserBaseInfo(UserInfo userInfo) {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        String json = gson.toJson(userInfo);
        localStorage.putString(KEY_USER_INFO, json);
    }

    public static void saveUserBean(UserBean userbean) {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        String json = gson.toJson(userbean);
        localStorage.putString(KEY_USER_BEAN, json);
    }

    public static UserInfo getCurrentUserBaseInfo() {
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

    public static UserBean getCurrentUserBean() {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        String userBeanStr = localStorage.getString(KEY_USER_BEAN);
        if (TextUtils.isEmpty(userBeanStr)) {
            return null;
        }
        UserBean userBean = null;
        try {
            userBean = gson.fromJson(userBeanStr, UserBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return userBean;
    }

    public void release() {

    }

    /**
     * 清除 基本信息和详情信息
     */
    public static void clearCurrentUser() {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        localStorage.remove(KEY_USER_INFO);
        localStorage.remove(KEY_USER_BEAN);
    }
}
