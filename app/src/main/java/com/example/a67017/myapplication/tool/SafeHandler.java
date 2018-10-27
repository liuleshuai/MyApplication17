package com.example.a67017.myapplication.tool;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 没有毛用
 *
 * @author 67017
 * @date 2017/11/17
 */

public class SafeHandler extends Handler {
    private static WeakReference<Activity> mActivity;
    private static DisposeHandler disposeHandler;

    public SafeHandler(Activity activity, DisposeHandler dispose) {
        // 弱引用
        mActivity = new WeakReference<>(activity);
        disposeHandler = dispose;
    }

    public SafeHandler(Looper looper, Activity activity, DisposeHandler dispose) {
        super(looper);
        // 弱引用
        mActivity = new WeakReference<>(activity);
        disposeHandler = dispose;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mActivity != null) {
            disposeHandler.disposeMessage(msg);
        }
    }

    public interface DisposeHandler {
        /**
         * 处理handleMessage中的回调
         */
        void disposeMessage(Message msg);
    }
}
