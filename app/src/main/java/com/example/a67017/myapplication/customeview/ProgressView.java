package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.a67017.myapplication.R;

/**
 * TODO: document your custom view class.
 */
public class ProgressView extends View {

    private RectF rect;
    private Paint paint;
    private Paint textPaint;
    private float progress;

    public ProgressView(Context context) {
        super(context);
        init(null, 0);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(40);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(150);
        textPaint.setFakeBoldText(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        rect = new RectF(paddingLeft, paddingTop, contentWidth + paddingLeft, contentHeight + paddingTop);
        canvas.drawArc(rect, 90, progress * 3.6f, false, paint);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textTop = fontMetrics.top;
        float textBottom = fontMetrics.bottom;
        String text = (int) progress + "%";
        canvas.drawText(text, getWidth() / 2, getHeight() / 2 + (textBottom - textTop) / 2 - textBottom, textPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public float getProgress() {
        return progress;
    }
}
