package com.miaxis.esmanage.model.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Car;
import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.model.ICarModel;
import com.miaxis.esmanage.model.local.greenDao.gen.CarDao;
import com.miaxis.esmanage.model.retrofit.CarNet;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;
import com.miaxis.esmanage.util.CommonUtil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    public Observable<ResponseEntity> delCar(Car car, Config config) throws UnsupportedEncodingException {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CarNet carNet = retrofit.create(CarNet.class);
        encodeCar(car);
        String jsonCar = new Gson().toJson(car);
        decodeCar(car);
        return carNet.delCar(jsonCar);
    }

    @Override
    public Observable<ResponseEntity> addCar(Car car, Config config) throws UnsupportedEncodingException {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CarNet carNet = retrofit.create(CarNet.class);
        File file = new File(car.getCarphoto());
        RequestBody requestBody = RequestBody.create(MediaType.parse(CommonUtil.getMimeType(car.getCarphoto())), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        encodeCar(car);
        String jsonCar = new Gson().toJson(car);
        decodeCar(car);
        return carNet.addCar(jsonCar, part);
    }

    @Override
    public Observable<ResponseEntity> modCar(Car car, Config config) throws UnsupportedEncodingException {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //适配RxJava2.0, RxJava1.x则为RxJavaCallAdapterFactory.create()
                .baseUrl("http://" + config.getIp() + ":" + config.getPort())
                .build();
        CarNet carNet = retrofit.create(CarNet.class);
        File file = new File(car.getCarphoto());
        encodeCar(car);
        String jsonCar = new Gson().toJson(car);
        decodeCar(car);
        if (file.exists()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse(CommonUtil.getMimeType(car.getCarphoto())), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            return carNet.modCar(jsonCar, part);
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);//表单类型
            return carNet.modCar(jsonCar, builder.addFormDataPart("t", "").build().part(0));
        }
    }

    @Override
    public void delCarFromLocal(Car car) {
        carDao.delete(car);
    }

    @Override
    public void saveCarLocal(Car car) {
        carDao.insertOrReplace(car);
    }

    @Override
    public void saveCarListLocal(List<Car> carList) {
        carDao.insertOrReplaceInTx(carList);
    }

    private void encodeCar(Car car) throws UnsupportedEncodingException {
        if (!TextUtils.isEmpty(car.getPlateno())) {
            car.setPlateno(URLEncoder.encode(car.getPlateno(), "utf-8"));
        }
        if (!TextUtils.isEmpty(car.getCompname())) {
            car.setCompname(URLEncoder.encode(car.getCompname(), "utf-8"));
        }
        if (!TextUtils.isEmpty(car.getOpusername())) {
            car.setOpusername(URLEncoder.encode(car.getOpusername(), "utf-8"));
        }
        if (!TextUtils.isEmpty(car.getRemark())) {
            car.setRemark(URLEncoder.encode(car.getRemark(), "utf-8"));
        }
    }

    private void decodeCar(Car car) throws UnsupportedEncodingException {
        if (!TextUtils.isEmpty(car.getPlateno())) {
            car.setPlateno(URLDecoder.decode(car.getPlateno(), "utf-8"));
        }
        if (!TextUtils.isEmpty(car.getCompname())) {
            car.setCompname(URLDecoder.decode(car.getCompname(), "utf-8"));
        }
        if (!TextUtils.isEmpty(car.getOpusername())) {
            car.setOpusername(URLDecoder.decode(car.getOpusername(), "utf-8"));
        }
        if (!TextUtils.isEmpty(car.getRemark())) {
            car.setRemark(URLDecoder.decode(car.getRemark(), "utf-8"));
        }
    }

}
