package com.miaxis.esmanage.model.retrofit;

import com.miaxis.esmanage.entity.Escort;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by 一非 on 2018/5/4.
 */

public interface EscortNet {
    @GET("yygl/api/downEscortByCompId")
    Observable<ResponseEntity<Escort>> downEscortByCompId(@Query("compId") Integer compId, @Query("sjc") String sjc);
    @GET("yygl/api/delEscort")
    Observable<ResponseEntity> delEscort(@Query("escortno") String escortno, @Query("opUser") String opUser);

    @Multipart
    @POST("yygl/api/addEscort")
    Observable<ResponseEntity> addEscort(@Query("jsonEscorter") String jsonEscorter, @Part MultipartBody.Part file);
}
