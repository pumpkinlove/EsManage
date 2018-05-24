package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.device.Device;
import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.model.ICarModel;
import com.miaxis.esmanage.model.ICompanyModel;
import com.miaxis.esmanage.model.IConfigModel;
import com.miaxis.esmanage.model.impl.CarModel;
import com.miaxis.esmanage.model.impl.CompanyModel;
import com.miaxis.esmanage.model.impl.ConfigModel;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.presenter.ICarDetailPresenter;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.view.ICarDetailView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
@SuppressLint("CheckResult")
public class CarDetailPresenter implements ICarDetailPresenter {

    private ICarDetailView detailView;
    private ICarModel carModel;
    private IConfigModel configModel;
    private ICompanyModel companyModel;

    public CarDetailPresenter(ICarDetailView detailView) {
        this.detailView = detailView;
        carModel = new CarModel();
        configModel = new ConfigModel();
        companyModel = new CompanyModel();

    }

    @Override
    public void addCar(Car car) {
        Observable
                .just(car)
                .doOnNext(new Consumer<Car>() {
                    @Override
                    public void accept(Car car) throws Exception {
                        detailView.showLoading("正在保存车辆信息...");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Car, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(Car car) throws Exception {
                        if (TextUtils.isEmpty(car.getCarphoto())) {
                            throw new Exception("车辆照片不能为空！");
                        }
                        Config config = configModel.loadConfig();
                        return carModel.addCar(car, config);
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
    public void delCar(final Car car) {
        Observable
                .just(car)
                .doOnNext(new Consumer<Car>() {
                    @Override
                    public void accept(Car car) throws Exception {
                        detailView.showLoading("正在删除车辆信息...");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Car, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(Car car) throws Exception {
                        Config config = configModel.loadConfig();
                        return carModel.delCar(car, config);
                    }
                })
                .doOnNext(new Consumer<ResponseEntity>() {
                    @Override
                    public void accept(ResponseEntity resp) throws Exception {
                        if (TextUtils.equals(Constant.SUCCESS, resp.getCode())) {
                            carModel.delCarFromLocal(car);
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
    public void modCar(final Car car) {
        Observable
                .just(car)
                .doOnNext(new Consumer<Car>() {
                    @Override
                    public void accept(Car car) throws Exception {
                        detailView.showLoading("正在保存车辆信息...");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Function<Car, ObservableSource<ResponseEntity>>() {
                    @Override
                    public ObservableSource<ResponseEntity> apply(Car car) throws Exception {
                        if (TextUtils.isEmpty(car.getCarphoto())) {
                            throw new Exception("车辆照片不能为空！");
                        }
                        Config config = configModel.loadConfig();
                        return carModel.modCar(car, config);
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
    public void getRfid() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        byte[] message = new byte[200];
                        Device.openFinger(message);
                        Device.openRfid(message);
                        detailView.showLoading("正在扫描RFID...");
                        e.onNext("");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        Thread.sleep(1000);
                        while (true) {
                            try {
                                Thread.sleep(100);
                            }
                            catch (InterruptedException e) {
                            }
                            byte[] tids = new byte[20000];
                            byte[] epcids = new byte[20000];
                            byte[] message = new byte[200];
                            int result = Device.getRfid(1000, tids, epcids, message);
                            if (result == -3) {
                                throw new Exception("取消扫描！");
                            }
                            if (result != 0) {
                                continue;
                            }
                            String rfids = new String(epcids).trim();
                            String[] rfidArr = rfids.split(",");
                            if (rfidArr.length == 0) {
                                continue;
                            }
                            return rfidArr[0];
                        }
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        detailView.hideLoading();
                        detailView.showRfid(s);
                        Device.closeFinger(new byte[200]);
                        Device.closeRfid(new byte[200]);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String errInfo = throwable.getMessage();
                        if (TextUtils.isEmpty(errInfo)) {
                            detailView.alert("扫描RFID失败！");
                        } else if (TextUtils.equals(errInfo, "取消扫描！")) {
                            Thread.sleep(1000);         //延迟一秒，设备关闭需要时间，防止短时间内再次点击获取RFID
                        } else {
                            detailView.alert("扫描RFID失败！\r\n" + throwable.getMessage());
                        }
                        detailView.hideLoading();
                    }
                });
    }

    @Override
    public void findCarComp(final Car car) {
        Observable
                .just(car)
                .doOnNext(new Consumer<Car>() {
                    @Override
                    public void accept(Car car) throws Exception {
                        Company company = companyModel.findCompById(Integer.valueOf(car.getCompid()));
                        car.setCompname(company.getCompname());
                        car.setCompno(company.getCompno());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Car>() {
                    @Override
                    public void accept(Car car) throws Exception {
                        detailView.showCarInfo(car);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        detailView.alert("获取公司信息失败！");
                    }
                });
    }
}
