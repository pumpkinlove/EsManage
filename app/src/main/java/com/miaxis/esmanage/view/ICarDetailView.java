package com.miaxis.esmanage.view;

import com.miaxis.esmanage.entity.Car;

public interface ICarDetailView extends IBaseView {

    void showCarInfo(Car car);

    void onSaveSuccess();

    void onDelSuccess();

    void updateCarPhoto(String photoUrl);

    void setEditable(boolean editable);

    void showRfid(String rfid);

}
