package com.miaxis.esmanage.model.impl;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.model.IEscortModel;
import com.miaxis.esmanage.model.local.greenDao.gen.EscortDao;
import com.miaxis.esmanage.model.retrofit.EscortNet;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.util.CommonUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EscortModel implements IEscortModel {

    private EscortDao escortDao;

    public EscortModel() {
        escortDao = EsManageApp.getInstance().getDaoSession().getEscortDao();
    }

    @Override
    public List<Escort> findLocalEscortsByCompId(int compId) {
        return escortDao.queryBuilder().where(EscortDao.Properties.Comid.eq(compId)).orderDesc(EscortDao.Properties.Opdate).list();
    }

    @Override
    public Observable<ResponseEntity<Escort>> downEscortByCompId(Integer compId, String sjc, Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        EscortNet escortNet = retrofit.create(EscortNet.class);
        return escortNet.downEscortByCompId(compId, sjc);
    }

    @Override
    public Observable<ResponseEntity> delEscort(Escort escort, Config config) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        EscortNet escortNet = retrofit.create(EscortNet.class);
        // TODO: 2018/5/20 0020 获取opUser
        return escortNet.delEscort(escort.getEscode(), "");
    }

    @Override
    public Observable<ResponseEntity> addEscort(Escort escort, Config config) throws UnsupportedEncodingException {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        EscortNet escortNet = retrofit.create(EscortNet.class);
        File file = new File(escort.getPhotoUrl());
        RequestBody requestBody = RequestBody.create(MediaType.parse(CommonUtil.getMimeType(escort.getPhotoUrl())), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        return escortNet.addEscort(new Gson().toJson(escort), part);
    }

    @Override
    public void saveEscortListLocal(List<Escort> escortList) {
        escortDao.insertOrReplaceInTx(escortList);
    }

    @Override
    public void saveEscortLocal(Escort escort) {
        escortDao.insertOrReplace(escort);
    }

    @Override
    public void delEscortLocal(Escort escort) {
        escortDao.delete(escort);
    }
}
