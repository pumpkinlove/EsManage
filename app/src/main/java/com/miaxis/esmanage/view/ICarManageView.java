package com.miaxis.esmanage.view;

import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.entity.Company;

import java.util.List;

public interface ICarManageView extends IBaseView {

    void showCompanySpinner1(List<Company> companyList);
    void showCompanySpinner2(List<Company> companyList);
    void showCompanySpinner3(List<Company> companyList);

    void hideCompanySpinner2();
    void hideCompanySpinner3();


    void showCartList(List<Car> carList);

}