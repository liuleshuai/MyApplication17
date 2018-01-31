package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a67017.myapplication.R;

/**
 * Created by LiuKuo at 2018/1/12
 */

public class DragLayout extends LinearLayout {
    private ViewDragHelper mDragHelper;
    private View dragView;
    private MyCloseListener listener;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new RuntimeException("内部必须只能有一个View!");
        }
        if (mDragHelper == null) {
            dragView = findViewById(R.id.view);
            mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallBack());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private class DragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == dragView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
          /*  if (child == dragView) {
                int topBound = getPaddingTop();
                int bottomBound = getHeight() - dragView.getHeight();
                int newTop = Math.min(Math.max(topBound, top), bottomBound);
                return newTop;
            } else {
                return 0;
            }*/
            return Math.max(0, top);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == dragView) {
                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - dragView.getWidth();
                int newLeft = Math.min(Math.max(leftBound, left), rightBound);
                return newLeft;
            } else {
                return 0;
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            listener.change(top);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == dragView) {
                mDragHelper.smoothSlideViewTo(dragView, 0, 0);
                //            mDragHelper.settleCapturedViewAt(0, 0);
            }
            invalidate();
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public interface MyCloseListener {
        void close();
        void change(int top);
    }

    public void setCloseListener(MyCloseListener listener) {
        this.listener = listener;
    }
}
