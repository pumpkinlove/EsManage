package com.miaxis.esmanage.view;

import com.miaxis.esmanage.entity.Escort;

public interface IEscortDetailView extends IBaseView {

    void updateEscortInfo(Escort escort);

    void updateEscortPhoto(String photoUrl);

    void updateFinger(int position, String finger, boolean getFingerSuccess);

    void setEditable(boolean editable);

    void onSaveSuccess();

}
