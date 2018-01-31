package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 兼容拖动和点击的SwitchCompat
 * 需要调用setSwitchListener进行回调事件处理
 * 注意：不要在额外设置其他的监听事件
 *
 * @author 刘阔
 * @date 2017/12/21
 */

public class CustomSwitchCompat extends SwitchCompat {

    public CustomSwitchCompat(Context context) {
        this(context, null);
    }

    public CustomSwitchCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private SwitchListener mListener;

    private float x1, x2;
    private long time1 = 0, time2 = 0;

    /**
     * 设置监听
     */
    public void setSwitchListener(SwitchListener listener) {
        mListener = listener;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mListener == null) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                time1 = System.currentTimeMillis();
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                time2 = System.currentTimeMillis();
                x2 = event.getX();
                if (Math.abs(x2 - x1) > 20) {
                    // 滑动
                    mListener.onClick();
                } else if (time2 - time1 < 500) {
                    // 单击
                    mListener.onClick();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public interface SwitchListener {
        /**
         * 回调
         */
        void onClick();
    }
}
