package com.example.common.tools;

import com.example.common.BaseApplication;
import com.example.common.dao.DaoSession;
import com.example.common.dao.GdUser;
import com.example.common.dao.GdUserDao;

import java.util.List;

public class GdUserUtil {

    private static GdUserDao getDao() {
        DaoSession session = BaseApplication.getDaoSession();
        GdUserDao dao = session.getGdUserDao();
        return dao;
    }

    public static List<GdUser> queryAllData() {
        return getDao().loadAll();
    }

    public static List<GdUser> queryData(String name) {
        return getDao().queryBuilder().where(GdUserDao.Properties.Name.eq(name)).list();
    }

    public static void insertData(GdUser user) {
        getDao().insert(user);
    }

    public static void removeData(GdUser user) {
        getDao().delete(user);
    }

    public static void removeData(Long key) {
        getDao().deleteByKey(key);
    }

    public static void updateData(GdUser user) {
        getDao().update(user);
    }
}
