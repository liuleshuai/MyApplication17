package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by LiuKuo at 2018/2/23
 */

public class MyWebView extends WebView {
    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        final int widthSpec = width - getPaddingLeft() - getPaddingRight();
        final int heightSpec = height - getPaddingTop() - getPaddingBottom();
        setMeasuredDimension(widthSpec, heightSpec);
    }
}
