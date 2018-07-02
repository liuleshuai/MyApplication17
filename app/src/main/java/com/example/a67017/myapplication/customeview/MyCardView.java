package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LiuKuo at 2018/1/10
 *
 * @author liukuo
 */

public class MyCardView extends AppCompatImageView {

    private int width;
    private int height;
    private Drawable d;
    private Paint mPaint;
    private Paint mBorderPaint;
    private PorterDuffXfermode mXfermode;
    private Bitmap pathBitmap;
    private Bitmap dBitmap;

    public MyCardView(Context context) {
        this(context, null);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
/*        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyCardViewStyle);
        int b = a.getInt(R.styleable.MyCardViewStyle_backColor, R.color.black);
        String c = a.getString(R.styleable.MyCardViewStyle_name);
        a.recycle();*/

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速加速
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setDither(true);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBorderPaint.setAntiAlias(true);//抗锯齿
        mBorderPaint.setDither(true);//防抖动
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        d = drawable;
        dBitmap = drawable2Bitmap(d);
        invalidate();
    }

    private Bitmap drawable2Bitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        return bitmap;
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (d != null) {
            width = (widthMode == MeasureSpec.AT_MOST) ? d.getIntrinsicWidth() : MeasureSpec.getSize(widthMeasureSpec);
            height = (heightMode == MeasureSpec.AT_MOST) ? d.getIntrinsicWidth() : MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        }
        if (width != 0 && height != 0) {
            pathBitmap = makePathBitmap();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(pathBitmap, 0, 0, mBorderPaint);
        mPaint.setXfermode(mXfermode);
        Bitmap bitmap = cutBitmap(dBitmap, width, height);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        mPaint = null;
        canvas.restore();
    }

    private Bitmap makePathBitmap() {
        Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mBitmap);

        Point lt = new Point(0, 30);
        Point rt = new Point(width, 0);
        Point rb = new Point(width, height);
        Point lb = new Point(0, height - 30);

        Path path = new Path();
        path.moveTo(lt.x, lt.y);
        path.lineTo(rt.x, rt.y);
        path.lineTo(rb.x, rb.y);
        path.lineTo(lb.x, lb.y);
        path.close();

        c.drawPath(path, mBorderPaint);
        return mBitmap;
    }

    private Bitmap cutBitmap(Bitmap bitmap, int width, int height) {
        float srcRatio = ((float) bitmap.getWidth()) / bitmap.getHeight();
        float viewRatio = ((float) width) / height;
        if (srcRatio < viewRatio) {
            float newHeight = ((float) width) / bitmap.getWidth() * bitmap.getHeight();
            return Bitmap.createScaledBitmap(bitmap, width, (int) newHeight, false);
        } else {
            float newWidth = ((float) height) / bitmap.getHeight() * bitmap.getWidth();
            return Bitmap.createScaledBitmap(bitmap, (int) newWidth, height, false);
        }
    }
}
