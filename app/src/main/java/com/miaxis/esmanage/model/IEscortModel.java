package com.miaxis.esmanage.model;

import com.miaxis.esmanage.entity.Config;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.model.retrofit.ResponseEntity;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import io.reactivex.Observable;

public interface IEscortModel extends IBaseModel {

    List<Escort> findLocalEscortsByCompId(int compId);

    Observable<ResponseEntity<Escort>> downEscortByCompId(Integer compId, String sjc, Config config);
    Observable<ResponseEntity> delEscort(Escort escort, Config config);
    Observable<ResponseEntity> addEscort(Escort escort, Config config) throws UnsupportedEncodingException;

}
