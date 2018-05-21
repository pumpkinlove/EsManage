package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.IEscortModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.model.impl.EscortModel;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.presenter.IEscortDetailPresenter;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.view.IEscortDetailView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class EscortDetailPresenter implements IEscortDetailPresenter {

    private IEscortDetailView detailView;
    private IEscortModel escortModel;
    private IConfigModel configModel;

    public EscortDetailPresenter(IEscortDetailView detailView) {
        this.detailView = detailView;
        escortModel = new EscortModel();
        configModel = new ConfigModel();
    }

    @SuppressLint("CheckResult")
    @Override
    public void addEscort(Escort escort) {
        Observable
                .just(escort)
                .doOnNext(new Consumer<Escort>() {
                    @Override
                    public void accept(Escort escort) throws Exception {
                        detailView.showLoading("正在保存押运员信息...");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Escort, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(Escort escort) throws Exception {
                        Config config = configModel.loadConfig();
                        return escortModel.addEscort(escort, config);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity resp) throws Exception {
                        detailView.hideLoading();
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            detailView.onSaveSuccess();
                        } else if (TextUtils.equals(Constant.FAILURE, resp.getCode())) {
                            detailView.alert("保存失败！\r\n" + resp.getMessage());
                        } else {
                            detailView.alert("保存失败！");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        detailView.hideLoading();
                        detailView.alert("保存失败！\r\n" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void delEscort(Escort escort) {

    }

    @Override
    public void modEscort(Escort escort) {

    }
}
