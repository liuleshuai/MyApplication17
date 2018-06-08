package com.example.a67017.myapplication.tool;

import com.example.a67017.myapplication.MyApplication;
import com.example.a67017.myapplication.dao.GdUser;
import com.example.a67017.myapplication.dao.GdUserDao;

import java.util.List;

/**
 * Created by LiuKuo at 2018/6/6
 */

public class GdUtil {
    private static volatile GdUtil instance;

    private static GdUtil getInstance() {
        if (instance == null) {
            synchronized (GdUtil.class) {
                if (instance == null) {
                    instance = new GdUtil();
                }
            }
        }
        return instance;
    }

//    public static DaoSession getDaoSession() {
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), Constant.DB_NAME, null);
//        SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
//        return daoMaster.newSession();
//    }

    public static void insert(GdUser user) {
        MyApplication.getDaoInstant().getGdUserDao().insert(user);
    }

    public static void delete(GdUser user) {
        MyApplication.getDaoInstant().getGdUserDao().delete(user);
    }

    public static void update(GdUser user) {
        MyApplication.getDaoInstant().getGdUserDao().update(user);
    }

    public static List<GdUser> queryAll() {
        return MyApplication.getDaoInstant().getGdUserDao().loadAll();
    }

    public static List<GdUser> query(String name) {
        return MyApplication.getDaoInstant().getGdUserDao().queryBuilder()
                .where(GdUserDao.Properties.Name.eq(name)).list();
    }
}
