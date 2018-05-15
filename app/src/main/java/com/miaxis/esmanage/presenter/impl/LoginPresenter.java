package com.miaxis.esmanage.presenter.impl;

import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.presenter.ILoginPresenter;
import com.miaxis.esmanage.view.ILoginView;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private IConfigModel configModel;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        configModel = new ConfigModel();
    }

    @Override
    public void checkConfig() {
        Config config = configModel.loadConfig();
        if (config == null) {
            loginView.showConfig();
            loginView.hideLogin();
        } else {
            loginView.hideConfig();
            loginView.showLogin();
        }
    }

    @Override
    public void doLogin() {

    }
}
