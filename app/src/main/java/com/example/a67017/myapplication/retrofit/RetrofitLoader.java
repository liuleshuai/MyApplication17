package com.example.a67017.myapplication.retrofit;

import com.example.a67017.myapplication.bean.MovieEntity;
import com.example.a67017.myapplication.bean.TestBean;
import com.example.a67017.myapplication.bean.WeatherEntity;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
        Observable observable = observe(retrofitService.getTop250(start, count)).map(new Func1<MovieEntity, List<MovieEntity.SubjectsBean>>() {
            @Override
            public List<MovieEntity.SubjectsBean> call(MovieEntity movieEntity) {
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
        Observable observable = observe(retrofitService.getInfor());
        return observable;
    }

    public <T> Observable<T> observe(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T> Observable.Transformer<T, T> toMain() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
