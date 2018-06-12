package com.example.a67017.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.a67017.myapplication.callback.ActivityLifecycleListener;
import com.example.a67017.myapplication.dao.DaoMaster;
import com.example.a67017.myapplication.dao.DaoSession;
import com.example.common.BaseApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * @author 67017
 */

public class MyApplication extends BaseApplication {
    private RefWatcher refWatcher;
    private static MyApplication instance;
    private String tag = "LK";
    public static boolean illegal = true;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initARouter();
        initLogger();
        SophixManager.getInstance().queryAndLoadNewPatch();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
        // 判断App是否运行在前端
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
        setupDatabase();
    }

    /**
     * 这两行必须写在init之前，否则这些配置在init过程中将无效
     */
    private void initARouter() {
        if (BuildConfig.LOG_DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }

    /**
     * 初始化Logger
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(tag)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.LOG_DEBUG;
            }
        });
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        init();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private void init() {
        String appVersion = getVersion(this);
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)    //用户自定义aes秘钥, 会对补丁包采用对称加密
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.d("LK", "补丁加载成功" + info);
                            Toast.makeText(instance,"补丁加载成功",Toast.LENGTH_LONG).show();
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            Log.d("LK", "重新启动" + info);
                            Toast.makeText(instance,"重新启动",Toast.LENGTH_LONG).show();
//                            Process.killProcess(Process.myPid());
                        } else {
                            Toast.makeText(instance,"其他",Toast.LENGTH_LONG).show();
                            Log.d("LK", "其他" + info);
                        }
                    }
                }).initialize();
    }

    public String getVersion(Context context) {
        String appVersion = "1.0.0";
        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
