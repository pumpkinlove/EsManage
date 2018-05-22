package com.miaxis.esmanage.presenter;

import com.miaxis.esmanage.entity.Car;

public interface ICarDetailPresenter extends IBasePresenter {

    void addCar(Car car);

    void delCar(Car car);

    void modCar(Car car);

    void getRfid();

    void findCarComp(Car car);

}
