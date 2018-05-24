package com.miaxis.esmanage.presenter;

import android.content.Context;

public interface ILoginPresenter extends IBasePresenter {

    void checkConfig();

    void doLogin(String account, String pwd);

    void getVersion(Context context);

}