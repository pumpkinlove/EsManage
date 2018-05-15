package com.miaxis.esmanage.model.impl;

import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.local.greenDao.gen.ConfigDao;
import com.miaxis.esmanage.model.local.greenDao.gen.DaoSession;

public class ConfigModel implements IConfigModel {

    private ConfigDao configDao;

    public ConfigModel() {
        configDao = EsManageApp.getInstance().getDaoSession().getConfigDao();
    }

    @Override
    public Config loadConfig() {
        return configDao.loadByRowId(1L);
    }

    @Override
    public boolean saveConfig(Config config) {
        long id =  configDao.insertOrReplace(config);
        if (id == 1L) {
            return true;
        } else {
            return false;
        }
    }
}
