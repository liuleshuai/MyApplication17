package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 67017 on 2017/11/15.
 */

public class PathView3 extends View {
    private Paint paint;
    private Path path;
    private Paint mPaint, mPaint1, mPaint2, mPaint3, mPaint4;
    private Path rectPath, rectPath1, rectPath2, rectPath3, rectPath4;
    private float stop, stop1, stop2, stop3, stop4;
    private float total;
    private int edge = 4;
    private PathMeasure pathMeasure;
    private float[] pos, tan;
    private float[] pos1, tan1;
    private float[] pos2, tan2;
    private float[] pos3, tan3;
    private float[] pos4, tan4;

    private String text;
    private TextPaint textPaint;

    /**
     * 为0时，竖直向下，90时水平向右
     */
    private float startAngle = 180;

    public PathView3(Context context) {
        this(context, null);
    }

    public PathView3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        pos = new float[2];
        tan = new float[2];
        pos1 = new float[2];
        tan1 = new float[2];
        pos2 = new float[2];
        tan2 = new float[2];
        pos3 = new float[2];
        tan3 = new float[2];
        pos4 = new float[2];
        tan4 = new float[2];
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(edge);
        paint.setColor(Color.parseColor("#cccccc"));
        paint.setStrokeCap(Paint.Cap.ROUND);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        mPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setColor(Color.RED);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(Color.BLACK);
        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setStyle(Paint.Style.FILL);
        mPaint3.setColor(Color.BLUE);
        mPaint4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint4.setStyle(Paint.Style.FILL);
        mPaint4.setColor(Color.GRAY);

        path = new Path();
        rectPath = new Path();
        rectPath1 = new Path();
        rectPath2 = new Path();
        rectPath3 = new Path();
        rectPath4 = new Path();

        pathMeasure = new PathMeasure();

        text = "我是刘阔";
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.addCircle(0, 0, getWidth() / 2 - 50, Path.Direction.CW);
        pathMeasure.setPath(path, true);
        total = pathMeasure.getLength() / 2;
        pathMeasure.getPosTan(stop, pos, tan);
        pathMeasure.getPosTan(stop1, pos1, tan1);
        pathMeasure.getPosTan(stop2, pos2, tan2);
        pathMeasure.getPosTan(stop3, pos3, tan3);
        pathMeasure.getPosTan(stop4, pos4, tan4);
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
        float degrees1 = (float) (Math.atan2(tan1[1], tan1[0]) * 180 / Math.PI);
        float degrees2 = (float) (Math.atan2(tan2[1], tan2[0]) * 180 / Math.PI);
        float degrees3 = (float) (Math.atan2(tan3[1], tan3[0]) * 180 / Math.PI);
        float degrees4 = (float) (Math.atan2(tan4[1], tan4[0]) * 180 / Math.PI);

        rectPath.reset();
        rectPath1.reset();
        rectPath2.reset();
        rectPath3.reset();
        rectPath4.reset();

        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);

        canvas.save();
//        canvas.drawPath(path, paint);
        canvas.rotate(degrees - startAngle, 0, 0);
        rectPath.addCircle(pos[0], pos[1], 20, Path.Direction.CW);
        canvas.drawPath(rectPath, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(degrees1 - startAngle, 0, 0);
        rectPath1.addCircle(pos1[0], pos1[1], 18, Path.Direction.CW);
        canvas.drawPath(rectPath1, mPaint1);
        canvas.restore();

        canvas.save();
        canvas.rotate(degrees2 - startAngle, 0, 0);
        rectPath2.addCircle(pos2[0], pos2[1], 16, Path.Direction.CW);
        canvas.drawPath(rectPath2, mPaint2);
        canvas.restore();

        canvas.save();
        canvas.rotate(degrees3 - startAngle, 0, 0);
        rectPath3.addCircle(pos3[0], pos3[1], 14, Path.Direction.CW);
        canvas.drawPath(rectPath3, mPaint3);
        canvas.restore();

        canvas.save();
        canvas.rotate(degrees4 - startAngle, 0, 0);
        rectPath4.addCircle(pos4[0], pos4[1], 12, Path.Direction.CW);
        canvas.drawPath(rectPath4, mPaint4);
        canvas.restore();

        canvas.restore();

        StaticLayout staticLayout = new StaticLayout(text, 0, text.length(), textPaint, 70, Layout.Alignment.ALIGN_CENTER, 1, 0, false);
        canvas.save();
        // 左上角坐标，和drawText的基线不同
        canvas.translate(getWidth() / 2 - staticLayout.getWidth() / 2, getHeight() / 2 - staticLayout.getHeight() / 2);
        staticLayout.draw(canvas);
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

    public float getStop() {
        return stop;
    }

    public void setStop(float stop) {
        this.stop = stop * total;
        invalidate();
    }

    public float getStop1() {
        return stop1;
    }

    public void setStop1(float stop1) {
        this.stop1 = stop1 * total;
        invalidate();
    }

    public float getStop2() {
        return stop2;
    }

    public void setStop2(float stop2) {
        this.stop2 = stop2 * total;
        invalidate();
    }

    public float getStop3() {
        return stop3;
    }

    public void setStop3(float stop3) {
        this.stop3 = stop3 * total;
        invalidate();
    }

    public float getStop4() {
        return stop4;
    }

    public void setStop4(float stop4) {
        this.stop4 = stop4 * total;
        invalidate();
    }
}
