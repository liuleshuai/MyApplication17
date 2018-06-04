package com.example.a67017.myapplication.adapter;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by LiuKuo at 2018/5/31
 */

public class MySnapHelper extends SnapHelper {
    private OrientationHelper helper;
    private RecyclerView rv;

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        rv = recyclerView;
        super.attachToRecyclerView(recyclerView);
    }

    // 计算滚动距离
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToTarget(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        return out;
    }

    // 获取SnapView
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return findView(layoutManager, getHorizontalHelper(layoutManager));
    }

    private View findView(RecyclerView.LayoutManager layoutManager, OrientationHelper mHelper) {
        if (layoutManager instanceof LinearLayoutManager) {
            int firstVisibleChildPos = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            if (firstVisibleChildPos == RecyclerView.NO_POSITION) {
                return null;
            }
            // 如果到最后一页，返回null
            if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1) {
                return null;
            }
            View firstVisibleView = layoutManager.findViewByPosition(firstVisibleChildPos);
            if (mHelper.getDecoratedEnd(firstVisibleView) >= mHelper.getDecoratedMeasurement(firstVisibleView) / 2) {
                return firstVisibleView;
            } else {
                return layoutManager.findViewByPosition(firstVisibleChildPos + 1);
            }
        }
        return null;
    }

    // 获得Fling后的移动位置
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }
        int totalCount = layoutManager.getItemCount();
        if (totalCount == 0) {
            return RecyclerView.NO_POSITION;
        }
        View currentView = findSnapView(layoutManager);
        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }
        int currentViewPos = layoutManager.getPosition(currentView);
        if (currentViewPos == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }
        // 用来判断正序布局还是倒序布局
        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
        PointF mVector = vectorProvider.computeScrollVectorForPosition(totalCount);
        if (mVector == null) {
            return RecyclerView.NO_POSITION;
        }
        // 一屏显示个数
        int screenChild = layoutManager.getChildCount();

        int hJump, vJump;
        if (layoutManager.canScrollHorizontally()) {
            hJump = calculateFlingDistance(helper, velocityX, 0, currentView);
            if (hJump > screenChild) {
                hJump = screenChild;
            }
            if (hJump < -screenChild) {
                hJump = -screenChild;
            }

            //mVector.x < 0代表layoutManager是倒序布局的，就把偏移量取反
            if (mVector.x < 0) {
                hJump = -hJump;
            }
        } else {
            hJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            vJump = calculateFlingDistance(helper, 0, velocityY, currentView);
            if (mVector.y < 0) {
                vJump = -vJump;
            }
        } else {
            vJump = 0;
        }

        int deltaJump = layoutManager.canScrollVertically() ? vJump : hJump;
        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION;
        }
        int targetPos = currentViewPos + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= totalCount) {
            targetPos = totalCount - 1;
        }
        return targetPos;
    }

    private int calculateFlingDistance(OrientationHelper helper, int velocityX, int velocityY, View currentView) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float perDistance = helper.getDecoratedMeasurement(currentView);
        if (perDistance < 0) {
            return 0;
        }
        int distance = distances[0];
        if (distance > 0) {
            return (int) Math.floor(distance / perDistance);
        } else {
            return (int) Math.ceil(distance / perDistance);
        }
    }


    private int distanceToTarget(View target, OrientationHelper helper) {
        return helper.getDecoratedStart(target) - helper.getStartAfterPadding();
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (helper == null) {
            if (layoutManager.canScrollHorizontally()) {
                helper = OrientationHelper.createHorizontalHelper(layoutManager);
            } else {
                helper = OrientationHelper.createVerticalHelper(layoutManager);
            }
        }
        return helper;
    }

    //默认值为100，这里改为40
    private static final float MILLISECONDS_PER_INCH = 40f;

    // 创建出来的平滑滚动器,控制速度。
    @Nullable
    @Override
    protected RecyclerView.SmoothScroller createScroller(final RecyclerView.LayoutManager layoutManager) {
        return new LinearSmoothScroller(rv.getContext()) {
            //设置减速
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                int[] remoteDistance = calculateDistanceToFinalSnap(layoutManager, targetView);
                int dx = remoteDistance[0];
                int dy = remoteDistance[1];
                int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    //调用Action的update()方法，更新SmoothScroller的滚动速率，使其减速滚动到停止
                    //这里的这样做的效果是，此SmoothScroller用time这么长的时间以mDecelerateInterpolator这个差值器
                    // 的滚动变化率滚动dx或者dy这么长的距离
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
                super.onTargetFound(targetView, state, action);
            }

            //设置速度
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
    }
}
