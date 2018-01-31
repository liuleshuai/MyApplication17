package com.example.a67017.myapplication.tool;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 67017
 * @date 2017/11/16
 */

public class MyOkHttp {

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static OkHttpClient client;

    /**
     * Get方法
     *
     * @param url
     * @param listener
     */
    public static void getHttp(URL url, final ResponseListener listener) {
        client = new OkHttpClient();
        /**
         * 在这里直接设置连接超时.读取超时，写入超时
         */
        OkHttpClient.Builder builder = client.newBuilder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        client = builder.build();

        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailed(request, listener, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callBackSuccess(response, listener);
                } else {
                    callBackError(response, listener);
                }
            }
        });
    }

    /**
     * Post方法
     *
     * @param url
     * @param listener
     */
    public static void postHttp(URL url, final ResponseListener listener) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("type", "1");
        FormBody formBody = builder.build();
        final Request request = new Request.Builder().url(url).post(formBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackFailed(request, listener, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callBackSuccess(response, listener);
                } else {
                    callBackError(response, listener);
                }
            }
        });
    }

    private static void callBackSuccess(Response response, final ResponseListener listener) throws IOException {
        final String json = response.body().string();
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.success(json);
            }
        });
    }

    private static void callBackFailed(final Request request, final ResponseListener listener, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.failed(request, e);
            }
        });
    }

    private static void callBackError(final Response response, final ResponseListener listener) throws IOException {
        final String json = response.body().string();
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.error(json, response.code());
            }
        });
    }

    public interface ResponseListener {
        void success(String json);

        void failed(Request request, IOException e);

        void error(String json, int code);
    }

    public static void cancel() {
        client.dispatcher().cancelAll();
    }
}
