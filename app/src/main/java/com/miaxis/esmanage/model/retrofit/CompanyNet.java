package com.miaxis.esmanage.model.retrofit;


import com.miaxis.esmanage.entity.Company;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/5/3.
 */

public interface CompanyNet {
    @GET("yygl/api/verifyComp")
    Observable<ResponseEntity> verifyComp(@Query("compCode") String compCode);
    @GET("yygl/api/downComp")
    Observable<ResponseEntity<Company>> downComp(@Query("account") String account, @Query("pwd") String pwd, @Query("compCode") String compCode);
}
