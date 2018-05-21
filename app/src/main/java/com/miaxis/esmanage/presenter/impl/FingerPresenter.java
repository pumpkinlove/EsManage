package com.miaxis.esmanage.presenter.impl;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.device.Device;
import com.miaxis.esmanage.presenter.IFingerPresenter;
import com.miaxis.esmanage.view.IFingerView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FingerPresenter implements IFingerPresenter {

    private IFingerView fingerView;
    private byte[][] tz = new byte[4][513];
    private byte[] mb = new byte[513];

    public FingerPresenter(IFingerView fingerView) {
        this.fingerView = fingerView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getFinger() {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        byte[] message = new byte[200];
                        int re = Device.openFinger(message);
                        if (re == 0) {
                            re = Device.openRfid(message);
                            if (re != 0) {
                                throw new Exception(new String(message, "GBK"));
                            } else {
                                Thread.sleep(1000);
                            }
                        } else {
                            throw new Exception(new String(message, "GBK"));
                        }
                        fingerView.showLoading("请按手指...（第 1 次）");
                        e.onNext(1);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, byte[]>() {
                    @Override
                    public byte[] apply(Integer integer) throws Exception {
                        return getFingerImageAndExtractFeature(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] bytes) throws Exception {
                        fingerView.updateImage(bytes);
                        fingerView.showLoading("请按手指...（第 2 次）");
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<byte[], byte[]>() {
                    @Override
                    public byte[] apply(byte[] bytes) throws Exception {
                        return getFingerImageAndExtractFeature(1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] image) throws Exception {
                        fingerView.updateImage(image);
                        fingerView.showLoading("请按手指...（第 3 次）");
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<byte[], byte[]>() {
                    @Override
                    public byte[] apply(byte[] bytes) throws Exception {
                        return getFingerImageAndExtractFeature(2);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] image) throws Exception {
                        fingerView.updateImage(image);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] image) throws Exception {
                        byte[] message = new byte[200];
                        int re = Device.FeatureToTemp(tz[0], tz[1], tz[2], mb, message);
                        if (re != 0) {
                            throw new Exception(new String(message, "GBK"));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] image) throws Exception {
                        fingerView.showLoading("请按手指...（第 4 次）");
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<byte[], byte[]>() {
                    @Override
                    public byte[] apply(byte[] bytes) throws Exception {
                        return getFingerImageAndExtractFeature(3);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] image) throws Exception {
                        fingerView.updateImage(image);
                    }
                })
                .map(new Function<byte[], String>() {
                    @Override
                    public String apply(byte[] bytes) throws Exception {
                        int re = Device.verifyFinger(new String(mb).trim(), new String(tz[3]).trim(), 3);
                        if (re == 0) {
                            return new String(mb);
                        } else {
                            throw new Exception("指纹校验失败！");
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String mb) throws Exception {
                        byte[] message = new byte[200];
                        Device.closeFinger(message);
                        Device.closeRfid(message);
                        fingerView.onGetFinger(mb);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String errInfo = throwable.getMessage().trim();
                        if (TextUtils.isEmpty(errInfo)) {
                            fingerView.alert("采集指纹失败！");
                        } else {
                            fingerView.alert(errInfo);
                        }
                        byte[] message = new byte[200];
                        Device.closeFinger(message);
                        Device.closeRfid(message);
                    }
                });

    }

    private byte[] getFingerImageAndExtractFeature(int i) throws Exception {
        byte[] image = new byte[2000 + 152 * 200];
        byte[] message = new byte[200];
        int re = Device.getImage(10000, image, message);
        if (re == 0) {
            re = Device.ImageToFeature(image, tz[i], message);
            if (re == 0) {
                return image;
            }
        }
        throw new Exception(new String(message, "GBK"));
    }

}
