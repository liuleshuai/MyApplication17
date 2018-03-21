package com.example.a67017.myapplication.retrofit;

import com.example.a67017.myapplication.bean.MovieEntity;
import com.example.a67017.myapplication.bean.TestBean;
import com.example.a67017.myapplication.bean.WeatherEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LiuKuo at 2018/1/24
 */

public class RetrofitLoader {
    private RetrofitService retrofitService;

    public RetrofitLoader(String baseUrl) {
        retrofitService = RetrofitServiceManager.getInstance(baseUrl).creat(RetrofitService.class);
    }

    /**
     * 获取电影列表
     *
     * @param start
     * @param count
     * @return
     */
    public Observable<List<MovieEntity.SubjectsBean>> getMovie(int start, int count) {
        Observable observable = observe(retrofitService.getTop250(start, count))
                .map(new Function<MovieEntity, List<MovieEntity.SubjectsBean>>() {
            @Override
            public List<MovieEntity.SubjectsBean> apply(@NonNull MovieEntity movieEntity) throws Exception {
                return movieEntity.getSubjects();
            }
        });
        return observable;
    }

    public Observable<WeatherEntity> getHeWeather(String location, String key) {
//        Observable observable = observe(retrofitService.getHeFengWeather(location, key));
        Observable observable = retrofitService.getHeFengWeather(location, key).compose(this.toMain());
        return observable;
    }

    public Observable<TestBean> getInfor() {
        Observable observable = retrofitService.getInfor().compose(this.toMain());
        return observable;
    }

    public <T> Observable<T> observe(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> ObservableTransformer<T, T> toMain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
