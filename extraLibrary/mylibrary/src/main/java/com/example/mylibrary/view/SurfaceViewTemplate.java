package com.example.mylibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable{
    //SurfaceHolder
    protected SurfaceHolder mHolder;
    //Canvas:用于绘图
    protected Canvas mCanvas;
    //子线程标志位
    protected boolean mIsDrawing;

    public SurfaceViewTemplate(Context context){
        super(context);
        initView(); //初始化
    }
    public SurfaceViewTemplate(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }
    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initView();
    }

    /*
     *  初始化
     * */
    private void initView(){
        mHolder = getHolder();
        mHolder.addCallback(this); //注册SurfaceHolder的回调方法
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    //SurfaceView创建
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true; //开启子线程
        new Thread(this).start(); //在子线程中通过while不断绘制
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false; //停止绘制(退出子线程循环)
    }

    /*-----------------------------------------------
     *  子线程中不断绘制(while)：
     *  因为画面不需要过多刷新，浪费性能。所以适当进行sleep以此节省性能。
     *----------------------------------------------------*/
    @Override
    public void run() {
        long startTime;
        long endTime;
        while(mIsDrawing){
            startTime = System.currentTimeMillis();
            draw();//绘制
            endTime = System.currentTimeMillis();
            //测量一次draw的时间，并以此sleep一定时长
            //取值范围一般是50~100ms
            if(endTime - startTime < 50){
                try {
                    Thread.sleep((50-(endTime-startTime))); //睡满直至100ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*------------------------------------
     *  绘制：
     *  1.lockCanvas()获得当前canvas对象, 每次获得的都是上次的Canvas对象, 绘制操作会保留。
     *    需要擦除，再回之前通过drawColor清屏
     *  2.unlockCanvasAndPost(mCanvas); 对画布内容进行提交
     *  3.将unlockCanvasAndPost放入finally确保每次都会提交
     *-----------------------------------*/
    public void draw(){
        try {
            mCanvas = mHolder.lockCanvas(); //获得当前canvas对象

            //draw something
        }catch (Exception e){

        }finally {
            if(mCanvas != null){
                mHolder.unlockCanvasAndPost(mCanvas); //对画布内容进行提交
            }
        }
    }
}
