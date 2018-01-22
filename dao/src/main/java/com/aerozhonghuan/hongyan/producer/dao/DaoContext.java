package com.aerozhonghuan.hongyan.producer.dao;

/**
 * Created by zhangyunfei on 17/6/19.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aerozhonghuan.hongyan.producer.dao.db.DaoMaster;
import com.aerozhonghuan.hongyan.producer.dao.db.DaoSession;

/**
 * 数据访问类的上下文操作类
 */
public class DaoContext {
    private static DaoContext daoContext;

    private DaoSession daoSession;
    private DaoMaster daoMaster;

    public static DaoContext getDaoContext() {
        if (daoContext == null) {
            daoContext = new DaoContext();
        }
        return daoContext;
    }

    /**
     * 当程序app 启动后
     */
    public static void onApplicationStart(Context context) {
        DaoContext.getDaoContext().setupDatabase(context);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase(Context context) {
        //创建数据库shop.db"
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        MyGreenDaoOpenHelper helper = new MyGreenDaoOpenHelper(context);

        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public DaoSession newSession() {
        return daoMaster.newSession();
    }
}
