package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by LiuKuo at 2018/1/16
 */

public class MyDragScrollView extends ScrollView {

    private boolean stopScroll = false;

    public MyDragScrollView(Context context) {
        this(context, null);
    }

    public MyDragScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDragScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (stopScroll) {
            return true;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t == 0) {
            VideoLayout.setScrollEnable(true);
        } else {
            VideoLayout.setScrollEnable(false);
        }
    }

    public void setStopScroll(boolean stopScroll) {
        this.stopScroll = stopScroll;
    }
}
