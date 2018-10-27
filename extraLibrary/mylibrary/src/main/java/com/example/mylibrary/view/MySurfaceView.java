package com.example.mylibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 67017
 *
 * 这个例子说明：surfaceView双缓存机制，两个缓存页交替显示，各自显示各自的部分（两部分都改变的话，每一个都在之前的基础上改变）。
 * 如果想视觉上只出现一个页面的话，需要清空页面，将之前的数据和新数据重新显示。见  DrawBoardView
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Timer timer = null;
    private MyTimerTask task = null;

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        timer = new Timer();
        task = new MyTimerTask(holder);
        timer.schedule(task, 500, 1000);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        task = null;
    }

    public class MyTimerTask extends TimerTask {
        private SurfaceHolder holder = null;
        private Paint paint = null;
        private int count = 0;

        public MyTimerTask(SurfaceHolder _holder) {
            holder = _holder;
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(32);
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (count > 40)//测试程序，大于20不再画了
                return;

            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas(null);//锁整个画布
//                canvas.drawColor(Color.BLACK);
                if (count % 2 == 0) {
                    canvas.drawText(count + "", 50, 50 + count * 20, paint);
                } else {
                    canvas.drawText(count + "", 150, 50 + count * 20, paint);
                }
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            count++;
        }

        public void clearDraw() {
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas(null);
                canvas.drawColor(Color.BLACK);
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}
