package com.example.a67017.myapplication.retrofit;

import com.example.a67017.myapplication.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LiuKuo at 2018/1/24
 */

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;
    private Retrofit retrofit;

    private RetrofitServiceManager(String baseUrl) {
        /****************** 两种打印方法******************/
        // 打印log
/*        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.LOG_DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }*/

        MyInterceptor interceptor = new MyInterceptor.MyBuilder()
                .addHeaderParams("device_system", "android").build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor);
//                .addInterceptor(loggingInterceptor);
        //添加拦截
        if (BuildConfig.LOG_DEBUG) {
            builder.addInterceptor(new LoggingInterceptor());
        }

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private static class SingletonHolder {
        private static RetrofitServiceManager instance;

        public static RetrofitServiceManager getInstance(String baseUrl) {
            return instance = new RetrofitServiceManager(baseUrl);
        }
    }

    public static RetrofitServiceManager getInstance(String baseUrl) {
        return SingletonHolder.getInstance(baseUrl);
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T creat(Class<T> service) {
        return retrofit.create(service);
    }
}
