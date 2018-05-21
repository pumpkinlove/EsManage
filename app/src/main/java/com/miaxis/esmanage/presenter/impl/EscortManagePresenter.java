package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

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
import com.miaxis.esmanage.presenter.IEscortManagePresenter;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.view.IEscortListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
@SuppressLint("CheckResult")
public class EscortManagePresenter implements IEscortManagePresenter {

    private IEscortListView escortListView;
    private ICompanyModel companyModel;
    private IEscortModel escortModel;
    private IConfigModel configModel;

    public EscortManagePresenter(IEscortListView escortListView) {
        this.escortListView = escortListView;
        companyModel = new CompanyModel();
        escortModel = new EscortModel();
        configModel = new ConfigModel();
    }

    @Override
    public void loadCompany1() {
        Observable
                .create(new ObservableOnSubscribe<List<Company>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<Company>> e) throws Exception {
                        List<Company> allComps = companyModel.findAllLocalComps();
                        List<Company> rootComps = new ArrayList<>();
                        int rootLevel = Integer.MAX_VALUE;
                        for (Company c : allComps) {
                            if (c.getNodeLevel() < rootLevel) {
                                rootLevel = c.getNodeLevel();
                            }
                        }
                        for (Company c : allComps) {
                            if (c.getNodeLevel() == rootLevel) {
                                rootComps.add(c);
                            }
                        }
                        e.onNext(rootComps);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Company>>() {
                    @Override
                    public void accept(List<Company> companyList) throws Exception {
                        escortListView.showCompanySpinner1(companyList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        escortListView.alert("加载第一级押运公司信息错误！\r\n" + throwable.getMessage());
                    }
                });

    }

    @Override
    public void loadCompany2(String parentCompCode) {
        Observable
                .just(parentCompCode)
                .map(new Function<String, List<Company>>() {
                    @Override
                    public List<Company> apply(String parentCompCode) throws Exception {
                        return companyModel.findLocalCompsByParentCode(parentCompCode);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Company>>() {
                    @Override
                    public void accept(List<Company> companyList) throws Exception {
                        escortListView.showCompanySpinner2(companyList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        escortListView.alert("加载第二级押运公司信息错误！\r\n" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void loadCompany3(String parentCompCode) {
        Observable
                .just(parentCompCode)
                .map(new Function<String, List<Company>>() {
                    @Override
                    public List<Company> apply(String parentCompCode) throws Exception {
                        return companyModel.findLocalCompsByParentCode(parentCompCode);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Company>>() {
                    @Override
                    public void accept(List<Company> companyList) throws Exception {
                        escortListView.showCompanySpinner3(companyList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        escortListView.alert("加载第三级押运公司信息错误！\r\n" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void loadEscortsByCompanyId(int compId) {
        // TODO: 2018/5/20 0020 若网络加载失败，加载本地数据库数据
        Observable
                .just(compId)
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<ResponseEntity<Escort>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<Escort>> apply(Integer compId) throws Exception {
                        String sjc;
                        Config config = configModel.loadConfig();
                        List<Escort> escorts = escortModel.findLocalEscortsByCompId(compId);
                        if (escorts == null || escorts.size() == 0) {
                            sjc = "";
                        } else {
                            sjc = escorts.get(0).getOpdate();
                        }
                        return escortModel.downEscortByCompId(compId, sjc, config);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseEntity<Escort>>() {
                    @Override
                    public void accept(ResponseEntity<Escort> resp) throws Exception {
                        escortListView.hideLoading();
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            escortListView.showEscortList(resp.getListData());
                        } else if (TextUtils.equals(Constant.FAILURE, resp.getCode())) {
                            escortListView.alert("加载押运员列表失败！\r\n" + resp.getMessage());
                        } else {
                            escortListView.alert("加载押运员列表失败！\r\n");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        escortListView.hideLoading();
                        escortListView.alert("加载押运员列表失败！\r\n" + throwable.getMessage());
                    }
                });

    }

    @Override
    public void toAddEscort(Company company) {

    }

    @Override
    public void toModEscort(Escort escort) {

    }

    @Override
    public void delEscort(Escort escort) {

    }
}
