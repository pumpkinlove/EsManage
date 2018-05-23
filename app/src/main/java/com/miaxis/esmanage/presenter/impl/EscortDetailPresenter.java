package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.device.Device;
import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.model.ICompanyModel;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.IEscortModel;
import com.miaxis.esmanage.model.impl.CompanyModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.model.impl.EscortModel;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.presenter.IEscortDetailPresenter;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.view.IEscortDetailView;

import java.util.Iterator;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
@SuppressLint("CheckResult")
public class EscortDetailPresenter implements IEscortDetailPresenter {

    private IEscortDetailView detailView;
    private IEscortModel escortModel;
    private IConfigModel configModel;
    private ICompanyModel companyModel;

    public EscortDetailPresenter(IEscortDetailView detailView) {
        this.detailView = detailView;
        escortModel = new EscortModel();
        configModel = new ConfigModel();
        companyModel = new CompanyModel();
    }

    @Override
    public void addEscort(final Escort escort) {
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
//                .doOnNext(new Consumer<ResponseEntity>() {
//                    @Override
//                    public void accept(ResponseEntity resp) throws Exception {
//                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
//                            escortModel.saveEscortLocal(escort);
//                        }
//                    }
//                })
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
    public void delEscort(final Escort escort) {
        Observable
                .just(escort)
                .doOnNext(new Consumer<Escort>() {
                    @Override
                    public void accept(Escort escort) throws Exception {
                        detailView.showLoading("正在删除押运员信息...");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Escort, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(Escort escort) throws Exception {
                        Config config = configModel.loadConfig();
                        return escortModel.delEscort(escort, config);
                    }
                })
                .doOnNext(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity resp) throws Exception {
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            escortModel.delEscortLocal(escort);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity resp) throws Exception {
                        detailView.hideLoading();
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            detailView.onDelSuccess();
                        } else if (TextUtils.equals(Constant.FAILURE, resp.getCode())) {
                            detailView.alert("删除失败！\r\n" + resp.getMessage());
                        } else {
                            detailView.alert("删除失败！");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        detailView.hideLoading();
                        detailView.alert("删除失败！\r\n" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void modEscort(final Escort escort) {
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
                        return escortModel.modEscort(escort, config);
                    }
                })
//                .doOnNext(new Consumer<ResponseEntity>() {
//                    @Override
//                    public void accept(ResponseEntity resp) throws Exception {
//                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
//                            escortModel.saveEscortLocal(escort);
//                        }
//                    }
//                })
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
    public void findEscortComp(Escort escort) {
        Observable
                .just(escort)
                .doOnNext(new Consumer<Escort>() {
                    @Override
                    public void accept(Escort escort) throws Exception {
                        Company company = companyModel.findCompById(Integer.valueOf(escort.getComid()));
                        escort.setCompname(company.getCompname());
                        escort.setComno(company.getCompno());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Escort>() {
                    @Override
                    public void accept(Escort escort) throws Exception {
                        detailView.updateEscortInfo(escort);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        detailView.alert("获取公司信息失败！");
                    }
                });
    }


}
