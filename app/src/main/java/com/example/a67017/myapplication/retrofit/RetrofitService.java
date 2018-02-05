package com.example.a67017.myapplication.retrofit;

import com.example.a67017.myapplication.bean.MovieEntity;
import com.example.a67017.myapplication.bean.TestBean;
import com.example.a67017.myapplication.bean.WeatherEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by LiuKuo at 2018/1/24
 */

public interface RetrofitService {
    //获取豆瓣Top250 榜单
    @GET("top250")
    Observable<MovieEntity> getTop250(@Query("start") int start, @Query("count") int count);

    //天气
    @FormUrlEncoded
    @POST("now")
    Observable<String> getWeather(@Field("location") String location, @Field("key") String key);

    @GET("now")
    Observable<WeatherEntity> getHeFengWeather(@Query("location") String location, @Query("key") String key);

    //测试
    @GET("test")
    Observable<TestBean> getInfor();
}
