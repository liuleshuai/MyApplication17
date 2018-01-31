package com.example.a67017.myapplication.retrofit;

import com.example.a67017.myapplication.bean.MovieEntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiuKuo at 2018/1/23
 */

public interface MovieRetrofit {
    @GET("top250")
    Call<MovieEntity> getTop250(@Query("start") int start, @Query("count") int count);//正常返回Call对象

    @FormUrlEncoded
    @POST("top250")
    Call<MovieEntity> getTopMovie(@Field("start") int start, @Field("count") int count);

    @GET("top250")
    Observable<MovieEntity> getTopRx(@Query("start") int start, @Query("count") int count);
}
