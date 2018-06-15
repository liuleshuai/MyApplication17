package com.example.a67017.myapplication.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.a67017.myapplication.R;
import com.example.a67017.myapplication.bean.MovieEntity;
import com.example.a67017.myapplication.retrofit.MovieRetrofit;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */

public class Fragment13 extends Fragment {

    Unbinder unbinder;

    private final String BASE_URL = "https://api.douban.com/v2/movie/";
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.bar_layout)
    AppBarLayout barLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private Retrofit retrofit;
    private Disposable disposable;

    public Fragment13() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment13, container, false);
        unbinder = ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#000000"));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        useRetrofit();
//        useRetrofitRx();
        useRetrofitRxOkHttp();
    }

    private void useRetrofitRxOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(10, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(10, TimeUnit.SECONDS);//读操作超时时间

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MovieRetrofit movieRetrofit = retrofit.create(MovieRetrofit.class);
//        Observable<MovieEntity> observable = movieRetrofit.getTopRx(0, 20);

        // 使用subscribeWith，disposable可以避免请求网络时突然退出导致的异常。
        // 在onDestroyView中进行取消订阅。
        disposable = movieRetrofit.getTopRx(0, 20).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieEntity>() {
                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        if (movieEntity != null) {
                            /******    Fastjson   ********/
                            String s = JSON.toJSONString(movieEntity);
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                        new Observer<MovieEntity>() {
//                    private Disposable mDisposable;
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mDisposable = d;
//                    }
//
//                    @Override
//                    public void onNext(MovieEntity movieEntity) {
//                        if (movieEntity != null) {
//                            /******    Fastjson   ********/
//                            String s = JSON.toJSONString(movieEntity);
//                            textView.setText(s);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mDisposable.dispose();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        mDisposable.dispose();
//                    }
//                });
    }

    private void useRetrofitRxjava2() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MovieRetrofit movieRetrofit = retrofit.create(MovieRetrofit.class);
//        Observable<MovieEntity> observable = movieRetrofit.getTopRx(0, 20);

        movieRetrofit.getTopRx(0, 20).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieEntity>() {
                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(MovieEntity movieEntity) {
                        if (movieEntity != null) {
                            /******    Fastjson   ********/
                            String s = JSON.toJSONString(movieEntity);
                            textView.setText(s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDisposable.dispose();
                    }

                    @Override
                    public void onComplete() {
                        mDisposable.dispose();
                    }
                });
    }

    private void useRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieRetrofit movieRetrofit = retrofit.create(MovieRetrofit.class);
        Call<MovieEntity> call = movieRetrofit.getTop250(0, 10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                if (response != null) {
                    /******    Fastjson   ********/
                    String s = JSON.toJSONString(response.body());
                    textView.setText(s);
                }
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
