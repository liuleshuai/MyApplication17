package com.example.mylibrary.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author 67017
 * <p>
 * 每次清空（null和空都是整个页面），path存储着就的路径，整个重新绘制。
 */
public class DrawBoardView extends SurfaceViewTemplate {
    Path mPath = new Path();

    public DrawBoardView(Context context) {
        super(context);
    }

    public DrawBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    int x0;
    int y0;
    int x1;
    int y1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x0 = (int) event.getX();
                y0 = (int) event.getY();
                mPath.moveTo(x0, y0);
                break;
            case MotionEvent.ACTION_MOVE:
                x1 = (int) event.getX();
                y1 = (int) event.getY();
                mPath.quadTo(x0, y0, (x0 + x1) / 2, (y0 + y1) / 2);
                x0 = x1;
                y0 = y1;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void run() {
        long startTime;
        long endTime;
        while (mIsDrawing) {
            startTime = System.currentTimeMillis();
            draw();
            endTime = System.currentTimeMillis();
//            取值范围一般是50~100ms
            if (endTime - startTime < 50) {
                try {
                    Thread.sleep((50 - (endTime - startTime))); //睡满直至100ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void draw() {
        try {
            mCanvas = mHolder.lockCanvas(null); //获得当前canvas对象，整个页面
            mCanvas.drawColor(Color.BLACK);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE); //绘制一定宽度的实线
            paint.setStrokeWidth(8);
            mCanvas.drawPath(mPath, paint);
            //draw something
        } catch (Exception e) {

        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas); //对画布内容进行提交
            }
        }
    }

}