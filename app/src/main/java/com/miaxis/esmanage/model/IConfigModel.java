package com.miaxis.esmanage.model;

import com.miaxis.esmanage.entity.Config;

public interface IConfigModel extends IBaseModel {

    Config loadConfig();

    boolean saveConfig(Config config);

}
