package com.miaxis.esmanage.presenter;

import com.miaxis.esmanage.entity.Escort;

public interface IEscortDetailPresenter extends IBasePresenter {

    void addEscort(Escort escort);

    void delEscort(Escort escort);

    void modEscort(Escort escort);

}
