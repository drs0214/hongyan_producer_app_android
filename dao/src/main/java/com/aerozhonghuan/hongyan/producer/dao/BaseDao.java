package com.aerozhonghuan.hongyan.producer.dao;

import com.aerozhonghuan.hongyan.producer.dao.db.DaoSession;

/**
 * 基础的dao基础类
 * Created by zhangyunfei on 17/6/23.
 */

public class BaseDao {

    public DaoContext getDaoContext() {
        return DaoContext.getDaoContext();
    }

    public DaoSession getDaoSession() {
        return getDaoContext().getDaoSession();
    }

    public DaoSession newSession() {
        return getDaoContext().newSession();
    }

    public void clear() {

    }


}
