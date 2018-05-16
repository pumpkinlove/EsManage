package com.miaxis.esmanage.presenter;

public interface IConfigPresenter {

    void loadConfig();
    void saveConfig(String ip, String port, String orgCode);

}
