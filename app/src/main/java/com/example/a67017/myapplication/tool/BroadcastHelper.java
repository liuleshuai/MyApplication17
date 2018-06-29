package com.example.a67017.myapplication.tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 广播工具类
 * Created by LiuKuo at 2018/6/29
 */

public class BroadcastHelper {
    private Context mContext;
    private static volatile BroadcastHelper instance;
    private Map<String, BroadcastReceiver> receiverMap;

    public BroadcastHelper(Context mContext) {
        this.mContext = mContext;
        receiverMap = new HashMap<>();
    }

    public static BroadcastHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (BroadcastHelper.class) {
                if (instance == null) {
                    instance = new BroadcastHelper(context);
                }
            }
        }
        return instance;
    }

    public void addAction(String action, BroadcastReceiver receiver) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            mContext.registerReceiver(receiver, filter);
            receiverMap.put(action, receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBroadcast(String action) {
        sendBroadcast(action, "");
    }

    public void sendBroadcast(String action, String s) {
        try {
            Intent intent = new Intent();
            intent.setAction(action);
            intent.putExtra("result", s);
            mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy(String action) {
        if (receiverMap != null) {
            BroadcastReceiver receiver = receiverMap.get(action);
            if (receiver != null) {
                mContext.unregisterReceiver(receiver);
            }
        }

    }
}
