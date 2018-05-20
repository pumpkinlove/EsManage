package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.ICarModel;
import com.miaxis.esmanage.model.ICompanyModel;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.impl.CarModel;
import com.miaxis.esmanage.model.impl.CompanyModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.presenter.ICarManagePresenter;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.view.ICarManageView;

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
public class CarManagePresenter implements ICarManagePresenter {

    private ICarManageView carManageView;
    private ICompanyModel companyModel;
    private ICarModel carModel;
    private IConfigModel configModel;

    public CarManagePresenter(ICarManageView carManageView) {
        this.carManageView = carManageView;
        companyModel = new CompanyModel();
        carModel = new CarModel();
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
                        carManageView.showCompanySpinner1(companyList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        carManageView.alert("加载第一级押运公司信息错误！\r\n" + throwable.getMessage());
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
                        carManageView.showCompanySpinner2(companyList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        carManageView.alert("加载第二级押运公司信息错误！\r\n" + throwable.getMessage());
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
                        carManageView.showCompanySpinner3(companyList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        carManageView.alert("加载第三级押运公司信息错误！\r\n" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void loadCarByCompanyId(int compId) {
        // TODO: 2018/5/20 0020 若网络加载失败，加载本地数据库数据
        Observable
                .just(compId)
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<ResponseEntity<Car>>>() {
                    @Override
                    public ObservableSource<ResponseEntity<Car>> apply(Integer compId) throws Exception {
                        Config config = configModel.loadConfig();
                        // TODO: 2018/5/20 0020 时间戳哪里来的
                        return carModel.downCarByCompId(compId,config);
                    }
                })
                .subscribe(new Consumer<ResponseEntity<Car>>() {
                    @Override
                    public void accept(ResponseEntity<Car> resp) throws Exception {
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            carManageView.showCartList(resp.getListData());
                        } else if (TextUtils.equals(Constant.FAILURE, resp.getCode())) {
                            carManageView.alert("加载押运员列表失败！\r\n" + resp.getMessage());
                        } else {
                            carManageView.alert("加载押运员列表失败！\r\n");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        carManageView.alert("加载押运员列表失败！\r\n" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void toAddCar(Company company) {

    }

    @Override
    public void toModCar(Car car) {

    }

    @Override
    public void delCar(Car car) {

    }


}
