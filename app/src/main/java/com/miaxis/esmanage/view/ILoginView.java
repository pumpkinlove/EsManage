package com.miaxis.esmanage.view;

public interface ILoginView extends IBaseView {

    void onLoginSuccess();
    void onLoginFail(String failMessage);
    void showConfig();
    void hideConfig();
    void showLogin();
    void hideLogin();

}
