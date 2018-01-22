package com.aerozhonghuan.hongyan.producer.dao;

import android.content.Context;
import android.util.Log;

import com.aerozhonghuan.hongyan.producer.dao.db.DaoMaster;

import org.greenrobot.greendao.database.Database;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 数据库的 open helper类
 * <p>
 * 数据库升级实现步骤：
 * 假设：旧版本为1，我们为某个表新增了字段后版本为2
 * 1. 修改 build.gradle中的 编译自动生成脚本的配置 数据库版本号 schemaVersion = 2
 * 2. 在 MyGreenDaoOpenHelper 类中 集成 Migration 类，实现它，完成具体修改表的sql语句 alert table xxxxxxxxx
 * 3. 在 将要升级的脚本添加到集合中 , 在 MyGreenDaoOpenHelper 的 成员变量 ALL_MIGRATIONS 中添加。要按顺序
 * <p>
 * 完成以上步骤即可，数据库升级时会触发 MyGreenDaoOpenHelper.onUpgrade 方法，会按版本号执行版本检测
 * <p>
 * Created by zhangyunfei on 2017/8/1.
 */

public class MyGreenDaoOpenHelper extends DaoMaster.DevOpenHelper {
    public static final String DB_NAME = "mydb.db";
    private static final SortedMap<Integer, Migration> ALL_MIGRATIONS = new TreeMap<>();//可升级的操作类
    private static final String TAG = MyGreenDaoOpenHelper.class.getSimpleName();

    static {
        //将要升级的脚本添加到集合中
        //按顺序排列(version从小到大)，key等于 from version ,即 从哪个数据库version开始, value等于记录升级的操作
        //必须 按顺序!
        //必须 按顺序!!
        //必须 按顺序!!!
        ALL_MIGRATIONS.put(1, new V1Migration());
    }

    public MyGreenDaoOpenHelper(Context context) {
        super(context, DB_NAME, null);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(Database sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrade from" + oldVersion + "to" + newVersion);
        //获得子集 从 >= oldVersion 到 < newVersion;
        SortedMap<Integer, Migration> migrations = ALL_MIGRATIONS.subMap(oldVersion, newVersion);
        for (final Integer version : migrations.keySet()) {
            ALL_MIGRATIONS.get(version).migrate(version, sqLiteDatabase);
        }
    }

    /**
     * 具体的升级操作
     */
    public interface Migration {

        void migrate(Integer version, Database db);

    }


    /**
     * 从 版本1 升级 的具体实现类
     */
    public static class V1Migration implements Migration {

        @Override
        public void migrate(Integer version, Database db) {
            db.execSQL("ALTER TABLE UserMessage ADD COLUMN createdDate INT");//增加了一个字段 createdDate
        }
    }
}
