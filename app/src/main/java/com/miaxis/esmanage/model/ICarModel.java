package com.miaxis.esmanage.model;

import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;

import java.util.List;

import io.reactivex.Observable;

public interface ICarModel extends IBaseModel {

    List<Car> findLocalCarsByCompId(int compId);

    Observable<ResponseEntity<Car>> downCarByCompId(Integer compId, Config config);
    Observable<ResponseEntity> delCar(Car car, Config config);
    Observable<ResponseEntity> addCar(Car car, Config config);

}
