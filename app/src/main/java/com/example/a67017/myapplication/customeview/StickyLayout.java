package com.example.a67017.myapplication.customeview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

public class StickyLayout extends LinearLayout implements NestedScrollingParent {

    private View view;
    private View layout;
    private View indicator;
    private int mTopViewHeight;
    private OverScroller mScroller;

    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 3) {
            layout = getChildAt(0);
            indicator = getChildAt(1);
            view = getChildAt(2);
        } else {
            throw new RuntimeException(
                    "the child number is not equal with 3 !");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = getMeasuredHeight() - indicator.getMeasuredHeight();
        measureChild(view, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), layout.getMeasuredHeight() + indicator.getMeasuredHeight() + view.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = layout.getMeasuredHeight();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() > 0 && !target.canScrollVertically(-1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        if (getScrollY() >= mTopViewHeight) {
            return false;
        }
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    public void showTopView() {
        if (getScrollY() >= mTopViewHeight) {
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
            invalidate();
        }
    }

    public void hideTopView() {
        if (getScrollY() < mTopViewHeight) {
            mScroller.startScroll(0, getScrollY(), 0, mTopViewHeight - getScrollY());
            invalidate();
        }
    }

    public boolean isShow() {
        return getScrollY() == 0;
    }

    public boolean isHide() {
        return getScrollY() == mTopViewHeight;
    }
}
