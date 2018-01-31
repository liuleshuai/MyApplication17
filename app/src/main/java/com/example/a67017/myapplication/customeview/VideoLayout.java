package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by LiuKuo at 2018/1/12
 */

public class VideoLayout extends LinearLayout {
    private ViewDragHelper mDragHelper;
    private View dragView;
    private OnMyDragListener listener;
    private static boolean scrollEnable = true;
    private float downX, downY, moveX, moveY;
    private boolean lock = false;

    public VideoLayout(Context context) {
        this(context, null);
    }

    public VideoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new RuntimeException("内部必须只能有一个View!");
        }
        if (mDragHelper == null) {
            dragView = getChildAt(0);
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
        Log.d("LK", action + "  " + scrollEnable);
        return mDragHelper.shouldInterceptTouchEvent(ev) || scrollEnable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                dragView.dispatchTouchEvent(event);
                mDragHelper.processTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = event.getY();
                if (!lock) {
                    lock = true;
                    Log.d("LK", dragView.getTop() + "");
                    if ((dragView.getTop() == 0) && (moveY - downY > 0)) {
                        Log.d("LK", "onTouchEvent, 顶部下拉拖拽，分发事件给VerticalDragHelper");
                        mDragHelper.processTouchEvent(event);
                        ((MyDragScrollView)dragView).setStopScroll(true);
                        dragView.dispatchTouchEvent(cloneMotionEventWithAction(event, MotionEvent.ACTION_CANCEL));
                    } else {
                        Log.d("LK", "onTouchEvent, 滑动事件，分发事件给子控件");
                        dragView.dispatchTouchEvent(event);
                        mDragHelper.cancel();
                    }
                }
            default:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    lock = false;
                    ((MyDragScrollView)dragView).setStopScroll(false);
                }
                dragView.dispatchTouchEvent(event);
                mDragHelper.processTouchEvent(event);
                break;
        }

        return true;
    }

    private MotionEvent cloneMotionEventWithAction(MotionEvent event, int action) {
        return MotionEvent.obtain(event.getDownTime(), event.getEventTime(), action, event.getX(),
                event.getY(), event.getMetaState());
    }

    public static void setScrollEnable(boolean enable) {
        scrollEnable = enable;
    }

    /****************************************************************************/
    private class DragHelperCallBack extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == dragView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return Math.max(0, top);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (listener != null) {
                listener.changeSize(top);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            lock = false;
            if (releasedChild == dragView) {
                if (((float) releasedChild.getTop()) / getHeight() > 0.5) {
                    mDragHelper.smoothSlideViewTo(dragView, 0, getHeight());
                    if (listener != null) {
                        listener.close();
                    }
                } else {
                    mDragHelper.smoothSlideViewTo(dragView, 0, 0);
                }
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

    public interface OnMyDragListener {
        void close();

        void changeSize(int top);
    }

    public void setMyDragListener(OnMyDragListener listener) {
        this.listener = listener;
    }
}
