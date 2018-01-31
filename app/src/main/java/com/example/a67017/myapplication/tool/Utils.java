package com.example.a67017.myapplication.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author 67017
 * @date 2017/11/16
 */

public class Utils {
    private static float screenWidth;
    private static float screenHeight;


    public static int dip2px(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static DisplayMetrics getScreenProperty(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    public static float getScreenWidth(Context context) {
        DisplayMetrics outMetrics = getScreenProperty(context);
        screenWidth = outMetrics.widthPixels;
        return screenWidth;
    }

    public static float getScreenHeight(Context context) {
        DisplayMetrics outMetrics = getScreenProperty(context);
        screenHeight = outMetrics.heightPixels;
        return screenHeight;
    }
}
