package com.miaxis.esmanage.presenter;

import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.entity.Company;

public interface ICarManagePresenter {

    // TODO: 2018/5/20 0020 合并三层加载方法
    void loadCompany1();
    void loadCompany2(String parentCompCode);
    void loadCompany3(String parentCompCode);

    void loadCarByCompanyId(int compId);

    void toAddCar(Company company);
    void toModCar(Car car);
    void delCar(Car car);

}