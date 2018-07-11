package com.example.mylibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

@Route(path = "/libraryActivity/broadcast")
public class BroadCastActivity extends AppCompatActivity {

    private static final String ACTION_TEST = "com.lk.test";
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syj_activity_broad_cast);
        localBroadcastManager = LocalBroadcastManager.getInstance(BroadCastActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (broadcastReceiver != null) {
            localBroadcastManager.unregisterReceiver(broadcastReceiver);
        }
    }

    private void init() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(BroadCastActivity.this, "收到广播了！！！！", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TEST);
        localBroadcastManager.registerReceiver(broadcastReceiver, filter);
    }

    public void sendBroadCast(View view) {
        Intent intent = new Intent();
        intent.setAction(ACTION_TEST);
        localBroadcastManager.sendBroadcast(intent);
    }
}
