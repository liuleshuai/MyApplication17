package com.example.a67017.myapplication.customeview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author qijian
 * @date 16/3/21
 */
public class WaterMoveView extends View {
    private Paint mPaint, cPaint;
    private Path mPath, nPath, dPath;
    private int mItemWaveLength = 400;
    private int dx, dy, dx2;
    private Bitmap mBitmap;
    private int width, height;
    private int edge;
    private Canvas mCanvas;
    private int waveHeight;
    private int paddingEdge = 5;
    private int textSize = 3;

    public WaterMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        nPath = new Path();
        dPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        dy = 0;

        // 最外圆
        cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cPaint.setColor(Color.WHITE);
        cPaint.setStyle(Paint.Style.STROKE);
        cPaint.setStrokeWidth(textSize);
        edge = 5;
        waveHeight = 25;
        startAnim();
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        canvas.drawCircle(width / 2, height / 2, width / 2 - edge, cPaint);
        mPath.reset();
        nPath.reset();
        dPath.reset();
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        int originY = 2 * height / 3;
        int halfWaveLen = mItemWaveLength / 2;
        //不向下移动
        mPath.moveTo(-mItemWaveLength + dx, originY);
        nPath.moveTo(-mItemWaveLength + dx2, originY);
        /*
        //实现向下移动动画
        mPath.moveTo(-mItemWaveLength+dx,originY+dy);
        dy += 1;
        */
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -waveHeight, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, waveHeight, halfWaveLen, 0);
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            nPath.rQuadTo(halfWaveLen / 2, waveHeight, halfWaveLen, 0);
            nPath.rQuadTo(halfWaveLen / 2, -waveHeight, halfWaveLen, 0);
        }
        nPath.lineTo(getWidth(), getHeight());
        nPath.lineTo(0, getHeight());
        nPath.close();

        /*      反向
        nPath.moveTo(mItemWaveLength + getWidth() - dx2, originY);
        for (int i = getWidth() + mItemWaveLength; i >= -mItemWaveLength; i -= mItemWaveLength) {
            nPath.rQuadTo(-halfWaveLen / 2, waveHeight, -halfWaveLen, 0);
            nPath.rQuadTo(-halfWaveLen / 2, -waveHeight, -halfWaveLen, 0);
        }
        nPath.lineTo(0, getHeight());
        nPath.lineTo(getWidth(), getHeight());
        nPath.close();
        */

        mCanvas.save();
        dPath.addCircle(width / 2, height / 2, width / 2 - edge - paddingEdge, Path.Direction.CW);
        mCanvas.clipPath(dPath);
        // 清空画布
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawPaint(mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));

        mPaint.setColor(Color.parseColor("#33ffffff"));
        mCanvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.parseColor("#88ffffff"));
        mCanvas.drawPath(nPath, mPaint);
        mCanvas.restore();

        canvas.drawBitmap(mBitmap, 0, 0, null);
        mPaint.setXfermode(null);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setStartDelay(500);
        animator.start();

        final ValueAnimator animator2 = ValueAnimator.ofInt(0, mItemWaveLength);
        animator2.setDuration(2000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx2 = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator2.start();
    }
}
