package com.example.a67017.myapplication.customeview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by 67017 on 2017/11/15.
 */

public class PathView extends View {
    private Paint paint;
    private Path circlePath;
    private Path path;
    private Path dstPath;
    private float stop;
    private float start;
    private float total;
    private int edge = 10;
    private PathMeasure pathMeasure;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(edge);
        paint.setColor(Color.BLACK);
        paint.setStrokeCap(Paint.Cap.ROUND);

        circlePath = new Path();
        circlePath.addCircle(0, 0, edge, Path.Direction.CW);
        paint.setPathEffect(new PathDashPathEffect(circlePath, 30, 0, PathDashPathEffect.Style.TRANSLATE));

        path = new Path();
        dstPath = new Path();
        pathMeasure = new PathMeasure();
        path.addCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2 - 10, Path.Direction.CW);
        pathMeasure.setPath(path, true);
        total = pathMeasure.getLength();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                stop = total * fraction;
                start = (float) (stop - (0.5 - Math.abs(fraction - 0.5)) * total);
                invalidate();
            }
        });
        animator.setRepeatCount(Animation.INFINITE);
        animator.setDuration(1000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dstPath.reset();
        pathMeasure.getSegment(start, stop, dstPath, true);
        canvas.drawPath(dstPath, paint);
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
        init();
    }
}
