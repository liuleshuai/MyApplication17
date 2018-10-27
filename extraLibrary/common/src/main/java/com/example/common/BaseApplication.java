package com.example.common;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.common.dao.DaoMaster;
import com.example.common.dao.DaoSession;

/**
 * Created by LiuKuo at 2018/2/2
 */

public class BaseApplication extends Application {
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "lk.db", null);
        SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
