package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.ICompanyModel;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.impl.CompanyModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.presenter.IConfigPresenter;
import com.miaxis.esmanage.view.IConfigView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ConfigPresenter implements IConfigPresenter {

    private IConfigView configView;
    private IConfigModel configModel;
    private ICompanyModel iCompanyModel;

    public ConfigPresenter(IConfigView configView) {
        this.configView = configView;
        configModel = new ConfigModel();
        iCompanyModel = new CompanyModel();
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadConfig() {
        Observable
                .create(new ObservableOnSubscribe<Config>() {
                    @Override
                    public void subscribe(ObservableEmitter<Config> e) throws Exception {
                        Config c = configModel.loadConfig();
                        if (c == null) {
                            e.onError(new Exception("未保存设置"));
                        } else {
                            e.onNext(c);
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configView.showConfig(config);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveConfig(String ip, String port, String orgCode) {
        final Config config = new Config(ip, port, orgCode);
        config.setId(1L);
        Observable
                .just(config)
                .doOnNext(new Consumer<Config>() {
                    @Override
                    public void accept(Config config) throws Exception {
                        configView.showLoading("正在保存设置...");
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<Config, Boolean>() {
                    @Override
                    public Boolean apply(Config config) throws Exception {
                        return configModel.saveConfig(config);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean saveSuccess) throws Exception {
                        configView.hideLoading();
                        if (saveSuccess) {
                            configView.showLoading("保存设置成功！\r\n开始验证机构编号...");
                        } else {
                            throw new Exception("保存设置失败！");
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, Observable<ResponseEntity>>() {
                    @Override
                    public Observable apply(Boolean aBoolean) throws Exception {
                        return iCompanyModel.verifyCompany(config);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity responseEntity) throws Exception {
                        configView.hideLoading();
                        if (TextUtils.equals(Constant.SUCCESS, responseEntity.getCode())) {
                            configView.onConfigSaveSuccess();
                        } else if (TextUtils.equals(Constant.FAILURE, responseEntity.getCode())) {
                            configView.alert("错误！\r\n" + responseEntity.getMessage());
                        } else {
                            configView.alert("错误！\r\n连接错误或机构号未存在" );
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        configView.hideLoading();
                        configView.alert("错误！\r\n" + throwable.getMessage());
                    }
                });

    }
}
