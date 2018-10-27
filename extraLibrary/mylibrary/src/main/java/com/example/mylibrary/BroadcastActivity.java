package com.example.mylibrary;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 广播中请求网络
 * 注意：不能在子线程更新UI，Toast也不行！！！！
 *
 * @author 67017
 */
@Route(path = "/libraryActivity/broadcast")
public class BroadcastActivity extends AppCompatActivity {

    private static final String ACTION_TEST = "com.example.mylibrary.test";
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private CustomHandler handler = new CustomHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syj_activity_broadcast);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    private void init() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("LK", "11111");
                requestHttp();
            }
        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TEST);
        localBroadcastManager = LocalBroadcastManager.getInstance(BroadcastActivity.this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(ACTION_TEST);
                localBroadcastManager.sendBroadcast(intent);
            }
        });

    }

    public void requestHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2000, TimeUnit.MILLISECONDS);
        builder.readTimeout(2000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(2000, TimeUnit.MILLISECONDS);

        Request request = new Request.Builder()
                .url("http://www.wanandroid.com/tools/mockapi/2748/lk")
                .build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Message message = Message.obtain();
                message.obj = json;
                handler.sendMessage(message);
            }
        });
    }

    private static void doWhat(Context context, String json) {
        Toast.makeText(context, json, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    public static class CustomHandler extends Handler {
        private WeakReference<Activity> activity;

        public CustomHandler(Activity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            String json = (String) msg.obj;
            doWhat(activity.get(), json);
        }

    }
}
