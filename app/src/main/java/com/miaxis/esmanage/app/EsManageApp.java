package com.miaxis.esmanage.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.miaxis.esmanage.model.local.greenDao.GreenDaoContext;
import com.miaxis.esmanage.model.local.greenDao.gen.DaoMaster;
import com.miaxis.esmanage.model.local.greenDao.gen.DaoSession;

import java.util.HashMap;
import java.util.Map;

public class EsManageApp extends Application {

    private static EsManageApp app;
    private DaoSession daoSession;
    private String curUserCode;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Fresco.initialize(this);
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

    public String getCurUserCode() {
        return curUserCode;
    }

    public void setCurUserCode(String curUserCode) {
        this.curUserCode = curUserCode;
    }
}
