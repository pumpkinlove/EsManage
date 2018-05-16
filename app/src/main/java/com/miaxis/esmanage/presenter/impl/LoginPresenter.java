package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.ICompanyModel;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.impl.CompanyModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.presenter.ILoginPresenter;
import com.miaxis.esmanage.view.ILoginView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private IConfigModel configModel;
    private ICompanyModel companyModel;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        configModel = new ConfigModel();
        companyModel = new CompanyModel();
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

    @SuppressLint("CheckResult")
    @Override
    public void doLogin(final String account, final String pwd) {
        loginView.showLoading("正在登录...");
        Observable
                .create(new ObservableOnSubscribe<Config>() {
                    @Override
                    public void subscribe(ObservableEmitter<Config> e) throws Exception {
                        Config config = configModel.loadConfig();
                        e.onNext(config);
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Config, ObservableSource<ResponseEntity<Company>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<Company>> apply(Config config) throws Exception {
                        return companyModel.downloadCompany(account, pwd, config);
                    }
                })
                .doOnNext(new Consumer<ResponseEntity<Company>>() {
                    @Override
                    public void accept(ResponseEntity<Company> resp) throws Exception {
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            List<Company> companyList = resp.getListData();
                            companyModel.saveCompany(companyList);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<Company>>() {
                    @Override
                    public void accept(ResponseEntity<Company> resp) throws Exception {
                        loginView.hideLoading();
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            loginView.onLoginSuccess();
                        } else if (TextUtils.equals(Constant.FAILURE, resp.getCode())) {
                            loginView.alert("下载押运公司信息失败！\r\n" + resp.getMessage());
                        } else {
                            loginView.alert("下载押运公司信息失败！");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.hideLoading();
                        loginView.alert("下载押运公司信息失败！\r\n" + throwable.getMessage());
                    }
                });

    }
}
