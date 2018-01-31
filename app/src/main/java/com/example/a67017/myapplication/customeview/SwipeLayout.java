package com.example.a67017.myapplication.customeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 侧滑删除
 *
 * @author DanielXiao
 */
@SuppressLint("ClickableViewAccessibility")
public class SwipeLayout extends FrameLayout {

    private ViewDragHelper dragHelper;
    private View backView;//侧滑菜单
    private View frontView;//内容区域
    private int range;//侧滑菜单可滑动范围

    //重写三个构造方法
    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, callback);
    }

    //获取两个View
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount < 2) {
            throw new IllegalStateException("you need 2 children view");
        }
        if (!(getChildAt(0) instanceof ViewGroup) || !(getChildAt(1) instanceof ViewGroup)) {
            throw new IllegalArgumentException("your children must be instance of ViewGroup");
    }

        backView = getChildAt(0);//侧滑菜单
        frontView = getChildAt(1);//内容区域
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        //所有子View都可拖拽
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        //水平拖拽后处理:移动的边界进行控制
        //决定拖拽的View在水平方向上面移动到的位置

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == frontView) {
                int leftBound = -range;
                int rightBound = 0;
                int newLeft = Math.min(Math.max(leftBound, left), rightBound);
                return newLeft;
            } else {
                return 0;
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == frontView) {
                backView.offsetLeftAndRight(dx);
            } else if (changedView == backView) {
                frontView.offsetLeftAndRight(dx);
            }
            invalidate();
        }

        //松手后根据侧滑位移确定菜单打开与否
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (frontView.getLeft() < -range * 0.5f) {
                open();
            } else {
                close();
            }
            invalidate();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 0;
        }
    };

    @Override
    public void computeScroll() {
        // 如果返回true，动画还需要继续
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void open() {
        int finalLeft = -range;
        dragHelper.smoothSlideViewTo(frontView, finalLeft, 0);
    }

    public void close() {
        int finalLeft = 0;
        dragHelper.smoothSlideViewTo(frontView, finalLeft, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int cWidth = childView.getMeasuredWidth();
            int cHeight = childView.getMeasuredHeight();
            MarginLayoutParams cParams = (MarginLayoutParams) childView.getLayoutParams();
            int cl = 0, ct = 0, cr = 0, cb = 0;
            switch (i) {
                case 0:
                    cl = getWidth();
                    ct = 0;
                    cb = cHeight;
                    cr = cl + cWidth;
                    break;
                case 1:
                    cl = cParams.leftMargin;
                    ct = 0;
                    cb = cHeight;
                    cr = cl + cWidth;
                    break;
                default:
                    break;
            }
            childView.layout(cl, ct, cr, cb);
        }
    }

    //初始化布局的高height宽width以及可滑动的范围range
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        range = backView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

}
