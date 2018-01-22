package com.aerozhonghuan.hongyan.producer.modules.common.logic;

import android.text.TextUtils;

import com.aerozhonghuan.hongyan.producer.dao.BaseDao;
import com.aerozhonghuan.hongyan.producer.dao.DaoContext;
import com.aerozhonghuan.hongyan.producer.dao.beans.UserBean;
import com.aerozhonghuan.foundation.utils.LocalStorage;
import com.aerozhonghuan.hongyan.producer.framework.base.MyAppliation;
import com.aerozhonghuan.hongyan.producer.modules.common.entity.UserInfo;
import com.aerozhonghuan.foundation.log.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.greendao.database.Database;

import java.util.List;

/**
 * 用户信息操作类
 * Created by zhangyunfei on 17/6/23.
 */

public class UserInfoDao extends BaseDao {

    private static final Gson gson = new Gson();
    private static final String KEY_USER_INFO = "userInfo";

    public static void saveUserBaseInfo(UserInfo userInfo) {

        LocalStorage localStorage = new LocalStorage(MyAppliation.getApplication());
        String json = gson.toJson(userInfo);
        localStorage.putString(KEY_USER_INFO, json);

//        DaoSession daoSession = getDaoSession();
//        Entity1Dao entity1Dao = daoSession.getEntity1Dao();
//        entity1Dao.save(null);
    }

    public static UserInfo getCurrentUserBaseInfo() {
        LocalStorage localStorage = new LocalStorage(MyAppliation.getApplication());
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

    public void release() {

    }

    /**
     * 清除 基本信息和详情信息
     */
    public static void clearCurrentUser() {
        LocalStorage localStorage = new LocalStorage(MyAppliation.getApplication());
        localStorage.remove(KEY_USER_INFO);

        DaoContext.getDaoContext().getDaoSession().getUserBeanDao().deleteAll();
    }

    /**
     * 获得用户 详细信息
     *
     * @return
     */
    public static UserBean getCurrentUserDetailInfo() {
        List<UserBean> userlist = DaoContext.getDaoContext().getDaoSession().getUserBeanDao().loadAll();
        if (userlist != null && userlist.size() > 0) {
            return userlist.get(0);
        }
        return null;
    }

    /**
     * 更新详细信息
     *
     * @param userbean
     */
    public static void saveUserDetailInfo(UserBean userbean) {
        long id = -1;
        Database db = DaoContext.getDaoContext().getDaoSession().getDatabase();
        db.beginTransaction();
        DaoContext.getDaoContext().getDaoSession().getUserBeanDao().deleteAll();
        id = DaoContext.getDaoContext().getDaoSession().getUserBeanDao().insert(userbean);
        if (id != -1) {
            LogUtil.i("info", "保存用户信息成功 rowid = " + id);
            db.setTransactionSuccessful();
        }

        db.endTransaction();
    }
}
