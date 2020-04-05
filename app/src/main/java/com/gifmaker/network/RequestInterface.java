package com.gifmaker.network;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestInterface {
    @GET("trending?api_key=1p9R1LLeEY1YFj9ZkGLcWPXSIJ25s4dH&limit=70&rating=G")
    Observable<com.gifmaker.Model.Response> getALLCategory();

    @GET("search?api_key=1p9R1LLeEY1YFj9ZkGLcWPXSIJ25s4dH&limit=50&rating=G")
    Observable<com.gifmaker.Model.Response> getSearchGify(@Query("q") String q);


}
