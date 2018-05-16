package com.miaxis.esmanage.model;

import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;

import java.util.List;

import io.reactivex.Observable;

public interface ICompanyModel extends IBaseModel {


    Observable<ResponseEntity> verifyCompany(Config config);
    List<Company> loadAllCompany();
    void saveCompany(List<Company> companyList);
    Observable<ResponseEntity<Company>> downloadCompany(String account, String pwd, Config config);

}
