package com.example.a67017.myapplication.retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LiuKuo at 2018/1/25
 */

public class MyInterceptor implements Interceptor {
    private Map<String, String> params = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        //新的请求
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body());
        //添加公共参数,添加到header中
        if (params.size() > 0) {
            for (Map.Entry<String, String> temp : params.entrySet()) {
                requestBuilder.addHeader(temp.getKey(), temp.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class MyBuilder {
        MyInterceptor myInterceptor;

        MyBuilder() {
            myInterceptor = new MyInterceptor();
        }

        MyBuilder addHeaderParams(String key, String value) {
            myInterceptor.params.put(key, value);
            return this;
        }

        public MyBuilder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public MyBuilder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public MyBuilder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public MyBuilder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public MyInterceptor build() {
            return myInterceptor;
        }
    }
}
