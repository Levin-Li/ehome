package com.eques.doorbell.a9048a3c38de2d7a.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class DisplayUtils
{
	private static DisplayMetrics getDisplayMetrics(Context context)
	{
		return context.getResources().getDisplayMetrics();
	}
	
	public static int getScreenWidth(Context context)
	{
		return getDisplayMetrics(context).widthPixels;
	}
	
	public static int getScreenHeight(Context context)
	{
		return getDisplayMetrics(context).heightPixels;
	}
	
	public static int px2dip(Context context, float pxValue)
	{
		final float scale = getDisplayMetrics(context).density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int dip2px(Context context, float dpValue)
	{
		final float scale = getDisplayMetrics(context).density;
		return (int) (dpValue * scale + 0.5f);
	}
	
    public static int px2sp(Context context, float pxValue) 
    { 
        final float fontScale =  getDisplayMetrics(context).scaledDensity; 
        return (int) (pxValue / fontScale + 0.5f); 
    } 

    public static int sp2px(Context context, float spValue) 
    { 
        final float fontScale = getDisplayMetrics(context).scaledDensity; 
        return (int) (spValue * fontScale + 0.5f); 
    } 
	
	public static GradientDrawable curveGradient()
	{
		int roundRadius = 8; // 8dp 圆角半径
		GradientDrawable gd = new GradientDrawable();
		// 创建drawable
		gd.setColor(Color.WHITE);
		gd.setCornerRadius(roundRadius);
		return gd;
	}
	
	public static GradientDrawable curveGradientTop()
	{
		float[] topRadius = new float[]{8, 8, 8, 8, 0, 0, 0, 0};
		GradientDrawable gd = new GradientDrawable();
		// 创建drawable
		gd.setColor(Color.WHITE);
		gd.setCornerRadii(topRadius);
		return gd;
	}
	
	public static GradientDrawable curveGradientBottom()
	{
		float[] topRadius = new float[]{0, 0, 0, 0, 8, 8, 8, 8};
		GradientDrawable gd = new GradientDrawable();
		// 创建drawable
		gd.setColor(Color.WHITE);
		gd.setCornerRadii(topRadius);
		return gd;
	}
	
	public static boolean isLandscape(Context context)
	{
		return context.
				getResources().
					getConfiguration().orientation == 
						Configuration.ORIENTATION_LANDSCAPE;
	}
	
	public static boolean inRangeOfView(View view, MotionEvent ev)
    {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y
                || ev.getY() > (y + view.getHeight()))
        {
            return false;
        }
        return true;
    }
}
