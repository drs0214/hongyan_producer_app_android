package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.aerozhonghuan.hongyan.producer.dao.DaoContext;
import com.aerozhonghuan.hongyan.producer.dao.beans.UserBean;
import com.aerozhonghuan.hongyan.producer.dao.db.UserBeanDao;
import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;
import com.aerozhonghuan.hongyan.producer.modules.common.ActivityDispatcher;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserInfo;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.aerozhonghuan.oknet2.CommonCallback;
import com.aerozhonghuan.oknet2.CommonMessage;

/**
 * Created by zhangyunfei on 17/6/23.
 */

public class UserInfoManager {
    private static final String TAG = UserInfoManager.class.getSimpleName();
    private static UserInfo userInfoCached;

    private UserInfoManager() {
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
        return userInfoCached;
    }

    /**
     * 获得 当前用户的详情信息
     *
     * @return
     */
    public static UserBean getCurrentUserDetailInfo() {
        UserInfoDao dao = new UserInfoDao();
        return dao.getCurrentUserDetailInfo();
    }

    /**
     * 退出登录 当前用户
     */
    public static void logout(Context context) {
        LogUtil.d(TAG, "准备 退出登录");
        UserInfo currentUser = getCurrentUserBaseInfo();
        if (currentUser == null) return;
        String token = currentUser.getToken();
        UserInfoManager.clearCurrentUser();
// 清除通知栏通知，防止点击异常
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        //将注销状态通知给服务端
        UserInfoWebRequest.logout(MyAppliation.getApplication(), token, new CommonCallback<Object>() {
            @Override
            public void onSuccess(Object messsageBodyObject, CommonMessage commonMessage, String allResponseString) {
                LogUtil.d(TAG, "UserInfoWebRequest.logout 成功");
            }

            @Override
            public boolean onFailure(int httpCode, Exception exception, CommonMessage responseMessage, String allResponseString) {
                LogUtil.d(TAG, "UserInfoWebRequest.logout 失败");
                return super.onFailure(httpCode, exception, responseMessage, allResponseString);
            }
        });
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
        UserBean userBean = UserInfoDao.getCurrentUserDetailInfo();
        if (userBean == null) return null;
        return userBean.getCarNo();
    }

    /**
     * 获得当前用户的手机号
     *
     * @return
     */
    public static String getCurrentUserPhone() {
        UserBean userBean = UserInfoDao.getCurrentUserDetailInfo();
        if (userBean == null) return null;
        return userBean.getPhone();
    }

    /**
     * 保存用户信息，详细信息
     *
     * @param userBean
     */
    public static boolean saveUserDetail(UserBean userBean) {
        UserBeanDao dao = DaoContext.getDaoContext().getDaoSession().getUserBeanDao();
        dao.deleteAll();
        long rowid = dao.insert(userBean);
        LogUtil.i(TAG, "保存用户信息成功 rowid = " + rowid);
        return rowid > 0;
    }
}
