package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止左右滑动
 * Created by LiuKuo at 2018/5/22
 */

public class CustomeViewPager extends ViewPager {
    /**
     * true:可以滑动；false:不能滑动
     */
    private boolean isScoll;

    public CustomeViewPager(Context context) {
        this(context, null);
    }

    public CustomeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isScoll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isScoll && super.onTouchEvent(ev);
    }

    public void setScoll(boolean scoll) {
        isScoll = scoll;
    }
}
