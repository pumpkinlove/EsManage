package com.miaxis.esmanage.view;

import com.miaxis.esmanage.entity.Config;

public interface IConfigView extends IBaseView {

    void showConfig(Config config);
    void onConfigSaveSuccess();

}
