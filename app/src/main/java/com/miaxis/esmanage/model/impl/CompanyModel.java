package com.miaxis.esmanage.model.impl;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.ICompanyModel;
import com.miaxis.esmanage.model.local.greenDao.gen.CompanyDao;
import com.miaxis.esmanage.model.retrofit.CompanyNet;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyModel implements ICompanyModel {

    private CompanyDao companyDao;

    public CompanyModel() {
        companyDao = EsManageApp.getInstance().getDaoSession().getCompanyDao();
    }

    public Observable<ResponseEntity> verifyCompany(Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CompanyNet companyNet = retrofit.create(CompanyNet.class);
        return companyNet.verifyComp(config.getOrgCode());
    }

    @Override
    public List<Company> loadAllCompany() {
        return companyDao.loadAll();
    }

    @Override
    public void saveCompany(List<Company> companyList) {
        companyDao.insertOrReplaceInTx(companyList);
    }

    @Override
    public Observable<ResponseEntity<Company>> downloadCompany(String account, String pwd, Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CompanyNet companyNet = retrofit.create(CompanyNet.class);
        return companyNet.downComp(account, pwd, config.getOrgCode());
    }

}