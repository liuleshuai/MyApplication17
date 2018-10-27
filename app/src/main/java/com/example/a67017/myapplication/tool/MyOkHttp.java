package com.example.a67017.myapplication.tool;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 67017
 * @date 2017/11/16
 */

public class MyOkHttp {

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static MyOkHttp myOkHttp;
    private OkHttpClient client;

    public MyOkHttp() {
        /**
         * 在这里直接设置连接超时.读取超时，写入超时
         */
//        client = new OkHttpClient();
//        OkHttpClient.Builder builder = client.newBuilder();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        client = builder.build();
    }

    public static MyOkHttp getInstance() {
        if (myOkHttp == null) {
            synchronized (MyOkHttp.class) {
                if (myOkHttp == null) {
                    myOkHttp = new MyOkHttp();
                }
            }
        }
        return myOkHttp;
    }

    /**
     * Get方法
     *
     * @param url
     * @param listener
     */
    public void getHttp(URL url, final ResponseListener listener) {
        // .get()可写可不写，默认就是GET方法
        final Request request = new Request.Builder().url(url).get().build();
        requestHttp(request, listener);
    }

    /**
     * Post方法
     *
     * @param url
     * @param listener
     */
    public void postHttp(URL url, String data, final ResponseListener listener) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("type", "1");
        FormBody formBody = builder.build();

        final Request request = new Request.Builder().url(url).post(formBody).build();
        requestHttp(request, listener);
    }

    public void postImage(String url, File file, ResponseListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("aFile", file.getName(),
//                RequestBody.create(MediaType.parse("image/png"), file));
//        builder.addPart(body);
        builder.addFormDataPart("img", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        builder.addFormDataPart("type", "1");
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        requestHttp(request, listener);
    }


    private void requestHttp(final Request request, final ResponseListener listener) {
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

    private void callBackSuccess(Response response, final ResponseListener listener) throws IOException {
        final String json = response.body().string();
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.success(json);
            }
        });
    }

    private void callBackFailed(final Request request, final ResponseListener listener, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.failed(request, e);
            }
        });
    }

    private void callBackError(final Response response, final ResponseListener listener) throws IOException {
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

    public void cancel() {
        client.dispatcher().cancelAll();
    }
}
