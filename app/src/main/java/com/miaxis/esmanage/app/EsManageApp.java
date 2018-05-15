package com.miaxis.esmanage.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;

import com.miaxis.esmanage.model.local.greenDao.GreenDaoContext;
import com.miaxis.esmanage.model.local.greenDao.gen.DaoMaster;
import com.miaxis.esmanage.model.local.greenDao.gen.DaoSession;

import java.util.HashMap;
import java.util.Map;

public class EsManageApp extends Application {

    private static EsManageApp app;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        initDbHelp();
    }

    public static EsManageApp getInstance() {
        return app;
    }

    public void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), "EsManage.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        daoSession.clear();
        return daoSession;
    }

}
