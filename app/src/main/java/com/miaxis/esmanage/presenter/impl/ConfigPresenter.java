package com.miaxis.esmanage.presenter.impl;

import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.presenter.IConfigPresenter;
import com.miaxis.esmanage.view.IConfigView;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ConfigPresenter implements IConfigPresenter {

    private IConfigView configView;
    private IConfigModel configModel;

    public ConfigPresenter(IConfigView configView) {
        this.configView = configView;
        configModel = new ConfigModel();
    }

    @Override
    public void saveConfig(String ip, String port, String orgCode) {
        Config config = new Config(ip, port, orgCode);
        Disposable d = Observable
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
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        configView.hideLoading();
                        if (aBoolean) {

                        } else {
                            configView.alert("保存设置失败！");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        configView.hideLoading();
                        configView.alert("保存设置失败！");
                    }
                });


    }
}
