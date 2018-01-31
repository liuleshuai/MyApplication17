package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author 67017
 * @date 2017/11/15
 */

public class PathView5 extends View {
    private Paint paint;
    private Path path;
    private Path path1;
    private int edge = 20;
    private float gearRadius;
    private Paint mpaint;
    private PathMeasure pathMeasure;
    private int[] colors;
    private float[] stops;
    private float gearHeight;

    public PathView5(Context context) {
        this(context, null);
    }

    public PathView5(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(Color.RED);
        mpaint.setStrokeCap(Paint.Cap.ROUND);
        mpaint.setStrokeWidth(edge);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);

        path = new Path();
        path1 = new Path();
        pathMeasure = new PathMeasure();

        colors = new int[]{0x00ff0000, 0x00ff0000, 0xffff0000};
        stops = new float[]{0f, 0.9f, 1f};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path1.reset();
        path.reset();
        path1.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - edge - gearRadius / 2, Path.Direction.CW);
        path.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - edge / 2, Path.Direction.CW);
        pathMeasure.setPath(path1, true);
        float circleLength = pathMeasure.getLength();
        float dashWidth = circleLength / 20;
        // 不加1f，虚线出现粘连的情况
        paint.setPathEffect(new DashPathEffect(new float[]{dashWidth / 2, dashWidth / 2}, 1f));
        paint.setStrokeWidth(gearRadius);
        RadialGradient radialGradient = new RadialGradient(getWidth() / 2, getHeight() / 2, getWidth() / 2 - edge - gearRadius / 2, colors, stops, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);

        canvas.drawPath(path, mpaint);
        if (gearRadius != 0) {
            canvas.drawPath(path1, paint);
        }
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
            widthSpecSize = widthSpecSize < heightSpecSize ? widthSpecSize : heightSpecSize;
            heightSpecSize = widthSpecSize < heightSpecSize ? heightSpecSize : heightSpecSize;
            setMeasuredDimension(widthSpecSize, heightSpecSize);

        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 齿轮高度
        gearHeight = getWidth() / 2 / 7;
        Log.d("LK", gearHeight + "");
    }

    public float getGearRadius() {
        return gearRadius;
    }

    public void setGearRadius(float radius) {
        this.gearRadius = gearHeight * radius;
        invalidate();
    }
}
