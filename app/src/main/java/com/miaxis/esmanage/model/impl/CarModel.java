package com.miaxis.esmanage.model.impl;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.ICarModel;
import com.miaxis.esmanage.model.local.greenDao.gen.CarDao;
import com.miaxis.esmanage.model.retrofit.CarNet;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarModel implements ICarModel {

    private CarDao carDao;

    public CarModel() {
        carDao = EsManageApp.getInstance().getDaoSession().getCarDao();
    }

    @Override
    public List<Car> findLocalCarsByCompId(int compId) {
        return carDao.queryBuilder().where(CarDao.Properties.Compid.eq(compId)).list();
    }

    @Override
    public Observable<ResponseEntity<Car>> downCarByCompId(Integer compId, Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CarNet carNet = retrofit.create(CarNet.class);
        return carNet.downCarByCompId(compId);
    }

    @Override
    public Observable<ResponseEntity> delCar(Car car, Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CarNet carNet = retrofit.create(CarNet.class);
        return carNet.delCar(new Gson().toJson(car));
    }

    @Override
    public Observable<ResponseEntity> addCar(Car car, Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CarNet carNet = retrofit.create(CarNet.class);
        // TODO: 2018/5/21 0021
        return carNet.addCar(new Gson().toJson(car), null);
    }
}
