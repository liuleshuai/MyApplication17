package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 67017 on 2017/11/15.
 */

public class PathView4 extends View {
    private Path path;
    private int edge = 10;
    private Paint mpaint;

    public PathView4(Context context) {
        this(context, null);
    }

    public PathView4(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.BLACK);
        mpaint.setStrokeCap(Paint.Cap.ROUND);


        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.addCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - edge, Path.Direction.CW);
        canvas.drawPath(path, mpaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int defaultWidth = 100;
        int defaultHeight = 100;
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, defaultHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defaultHeight);
        } else {
            widthSpecSize = Math.min(widthSpecSize, heightSpecSize);
            heightSpecSize = Math.min(widthSpecSize, heightSpecSize);
            setMeasuredDimension(widthSpecSize, heightSpecSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
