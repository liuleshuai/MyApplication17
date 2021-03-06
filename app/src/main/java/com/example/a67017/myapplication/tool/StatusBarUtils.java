package com.example.a67017.myapplication.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * @author liukuo
 */
public class StatusBarUtils {


    /**
     * @param activity       上下文
     * @param color          颜色
     * @param statusBarAlpha 0 - 255
     */
    public static void setColor(Activity activity, @ColorInt int color, int statusBarAlpha) {
        //先设置的全屏模式,隐藏状态栏
        setFullScreen(activity);
        //在透明状态栏的垂直下方放置一个和状态栏同样高宽的view
        addStatusBar(activity, color, statusBarAlpha);
    }

    /**
     * 重新添加了一个状态栏(实际上是个view)
     */
    public static void addStatusBar(Activity activity, @ColorInt int color, int statusBarAlpha) {
        //获取windowphone下的decorView
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int count = decorView.getChildCount();
        if (decorView.getChildAt(0) instanceof LinearLayout) {
            Log.d("LK", "LinearLayout");
        }
        if (decorView.getChildAt(0) instanceof StatusBarView) {
            Log.d("LK", "StatusBarView");
        }
        //判断是否已经添加了statusBarView
        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
            decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
        } else {
            //新建一个和状态栏高宽的view
            StatusBarView statusView = createStatusBarView(activity, color, statusBarAlpha);
            decorView.addView(statusView);
        }
        setRootView(activity);
    }

    /**
     * 沉浸式状态栏，并设置相关控件marginTop一个状态栏的高度
     *
     * @param activity
     * @param alpha
     * @param needOffsetView
     */
    public static void setTranslucentStatusBar(Activity activity, int alpha, View needOffsetView) {
        setFullScreen(activity);
        changeStatusBarView(activity, alpha);
        marginOffsetView(activity, needOffsetView);
    }

    /**
     * 沉浸式状态栏，并设置相关控件paddingTop一个状态栏的高度
     *
     * @param activity
     * @param alpha
     * @param needOffsetView
     */
    public static void setTranslucentStatusBarPadding(Activity activity, int alpha, View needOffsetView) {
        setFullScreen(activity);
        changeStatusBarView(activity, alpha);
        paddingOffsetView(activity, needOffsetView);
    }

    private static void paddingOffsetView(Activity activity, View needOffsetView) {
        if (needOffsetView != null) {
            ViewGroup.LayoutParams lp = needOffsetView.getLayoutParams();
            if (lp != null && lp.height > 0) {
                lp.height += getStatusBarHeight(activity);//增高
            }
            needOffsetView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
    }

    /**
     * 设置相关控件marginTop一个状态栏的高度
     *
     * @param activity
     * @param needOffsetView
     */
    private static void marginOffsetView(Activity activity, View needOffsetView) {
        if (needOffsetView != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(0, getStatusBarHeight(activity), 0, 0);
        }
    }

    private static void changeStatusBarView(Activity activity, int alpha) {
        //获取windowphone下的decorView
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int count = decorView.getChildCount();
        //判断是否已经添加了statusBarView
        if (decorView instanceof FrameLayout) {
            Log.d("LK", "111");  // run
        }
        if (decorView.getChildAt(0) instanceof LinearLayout) {
            Log.d("LK", "222");  // run
        }
        if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
            decorView.getChildAt(count - 1).setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        } else {
            //新建一个和状态栏高宽的view
            StatusBarView statusView = createTranslucentStatusBarView(activity, alpha);
            decorView.addView(statusView);
        }
    }


    private static StatusBarView createTranslucentStatusBarView(Activity activity, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        return statusBarView;
    }

    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        //rootview不会自动为状态栏留出状态栏空间
        ViewCompat.setFitsSystemWindows(rootView, true);
        // 默认就为true
        rootView.setClipToPadding(true);
    }

    private static StatusBarView createStatusBarView(Activity activity, int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        return statusBarView;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    public static void setFullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置透明状态栏,这样才能让 ContentView 向上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static class StatusBarView extends View {

        public StatusBarView(Context context) {
            super(context);
        }

        public StatusBarView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
    }
}
