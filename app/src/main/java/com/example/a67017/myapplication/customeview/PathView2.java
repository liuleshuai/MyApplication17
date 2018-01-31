package com.example.a67017.myapplication.customeview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by 67017 on 2017/11/15.
 */

public class PathView2 extends View {
    private Paint paint;
    private Paint mPaint;
    private Path path;
    private Path rectPath;
    private float stop;
    private float start;
    private float total;
    private int edge = 4;
    private PathMeasure pathMeasure;
    private float[] pos, tan;
    private Matrix mMatrix;

    public PathView2(Context context) {
        this(context, null);
    }

    public PathView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        pos = new float[2];
        tan = new float[2];
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(edge);
        paint.setColor(Color.parseColor("#cccccc"));
        paint.setStrokeCap(Paint.Cap.ROUND);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        path = new Path();
        rectPath = new Path();


        pathMeasure = new PathMeasure();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                stop = total * fraction;
                invalidate();
            }
        });
        animator.setRepeatCount(Animation.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.addCircle(0, 0, getWidth() / 2 - 10, Path.Direction.CW);
        pathMeasure.setPath(path, true);
        total = pathMeasure.getLength();
        pathMeasure.getPosTan(stop, pos, tan);
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
        rectPath.reset();
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawPath(path, paint);
        canvas.rotate(degrees, 0, 0);
        rectPath.addRect(pos[0] - 10, pos[1] - 10, pos[0] + 10, pos[1] + 10, Path.Direction.CW);
        canvas.drawPath(rectPath, mPaint);
        canvas.restore();
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
