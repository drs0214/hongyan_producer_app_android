package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.foundation.utils.LocalStorage;
import com.aerozhonghuan.hongyan.producer.framework.base.MyApplication;
import com.aerozhonghuan.hongyan.producer.modules.common.ActivityDispatcher;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserBean;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserInfo;

/**
 * Created by zhangyunfei on 17/6/23.
 */

public class UserInfoManager {
    private static final String TAG = UserInfoManager.class.getSimpleName();
    private static UserInfo userInfoCached;

    private UserInfoManager() {
    }

    public static void saveReminder(String userId, int count) {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        localStorage.putInt(userId, count);
    }

    public static int getReminder(String userId) {
        LocalStorage localStorage = new LocalStorage(MyApplication.getApplication());
        int count = localStorage.getInt(userId);
        return count;
    }

    /**
     * 当前 是否已经登录了用户
     *
     * @return
     */
    public static boolean isUserAuthenticated() {
        userInfoCached = UserInfoDao.getCurrentUserBaseInfo();
        LogUtil.d(TAG, "invoke:: isUserAuthenticated = " + (userInfoCached != null));
        return userInfoCached != null;
    }

    public static void saveCurrentUser(UserInfo userInfo) {
        LogUtil.d(TAG, "invoke:: saveCurrentUser = " + userInfo);
        if (userInfo == null)
            return;
        UserInfoDao.saveUserBaseInfo(userInfo);
    }

    public static void clearCurrentUser() {
        UserInfoDao.clearCurrentUser();
    }

    /**
     * 获得当前已经登录的用户信息
     *
     * @return
     */

    public static UserInfo getCurrentUserBaseInfo() {
        if (userInfoCached != null) {
            return userInfoCached;
        }
        UserInfoDao dao = new UserInfoDao();
        userInfoCached = dao.getCurrentUserBaseInfo();
        return userInfoCached == null ? new UserInfo() : userInfoCached;
    }

    /**
     * 获得 当前用户的详情信息
     *
     * @return
     */
    public static UserBean getCurrentUserDetailInfo() {
        return UserInfoDao.getCurrentUserBean() == null ? new UserBean() : UserInfoDao.getCurrentUserBean();
    }

    /**
     * 退出登录 当前用户
     */
    public static void logout(Context context) {
        LogUtil.d(TAG, "准备 退出登录");
        UserInfo currentUser = getCurrentUserBaseInfo();
        if (currentUser == null) return;
        String token = currentUser.getToken();
        if (TextUtils.isEmpty(token))
            return;
        UserInfoManager.clearCurrentUser();

        // TODO: 2018/1/25 退出登录 
        userInfoCached = null;
        getCurrentUserBaseInfo();

        Intent intent = ActivityDispatcher.getIntent_LoginOnLogout(context);
        context.startActivity(intent);
    }

    /**
     * 获得当前车辆的车牌号
     *
     * @return
     */
    public static String getCurrentCarNumber() {
        UserBean userBean = UserInfoDao.getCurrentUserBean();
        if (userBean == null) return null;
        return userBean.getCarNo();
    }

    /**
     * 获得当前用户的手机号
     *
     * @return
     */
    public static String getCurrentUserPhone() {
        UserBean userBean = UserInfoDao.getCurrentUserBean();
        if (userBean == null) return null;
        return userBean.getPhone();
    }

    /**
     * 保存用户信息，详细信息
     *
     * @param userBean
     */
    public static void saveUserDetail(UserBean userBean) {
//        UserBeanDao dao = DaoContext.getDaoContext().getDaoSession().getUserBeanDao();
//        dao.deleteAll();
//        long rowid = dao.insert(userBean);
//        LogUtil.i(TAG, "保存用户信息成功 rowid = " + rowid);
        UserInfoDao.saveUserBean(userBean);
    }
}
