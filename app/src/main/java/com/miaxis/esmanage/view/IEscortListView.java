package com.miaxis.esmanage.view;

import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Escort;

import java.util.List;

public interface IEscortListView extends IBaseView {

    void showCompanySpinner1(List<Company> companyList);
    void showCompanySpinner2(List<Company> companyList);
    void showCompanySpinner3(List<Company> companyList);

    void hideCompanySpinner2();
    void hideCompanySpinner3();


    void showEscortList(List<Escort> escortList);

}