package com.tt.miniapp.base.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class DensityUtil {
  private static float density = -1.0F;
  
  private static int heightPixels = 0;
  
  private static int sStatusBarHeight = -1;
  
  private static int widthPixels;
  
  public static int dip2px(Context paramContext, float paramFloat) {
    return (int)(paramFloat * getScreenDensity(paramContext) + 0.5F);
  }
  
  public static float getScreenDensity(Context paramContext) {
    float f = density;
    if (f >= 0.0F)
      return f; 
    try {
      DisplayMetrics displayMetrics = new DisplayMetrics();
      ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
      f = displayMetrics.density;
      density = f;
      return f;
    } catch (Exception exception) {
      return 2.0F;
    } 
  }
  
  public static int getScreenHeight(Context paramContext) {
    if (widthPixels == 0 || heightPixels == 0)
      try {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
      } catch (Exception exception) {} 
    if ((paramContext.getResources().getConfiguration()).orientation == 1) {
      int k = widthPixels;
      int m = heightPixels;
      return (k > m) ? k : m;
    } 
    int i = widthPixels;
    int j = heightPixels;
    return (i < j) ? i : j;
  }
  
  public static int getScreenWidth(Context paramContext) {
    if (widthPixels == 0 || heightPixels == 0)
      try {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        widthPixels = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
      } catch (Exception exception) {} 
    if ((paramContext.getResources().getConfiguration()).orientation == 1) {
      int k = widthPixels;
      int m = heightPixels;
      return (k < m) ? k : m;
    } 
    int i = widthPixels;
    int j = heightPixels;
    return (i > j) ? i : j;
  }
  
  public static int getStatusBarHeight(Context paramContext) {
    if (sStatusBarHeight < 0 && paramContext != null) {
      int i = paramContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (i > 0)
        sStatusBarHeight = paramContext.getResources().getDimensionPixelSize(i); 
    } 
    return sStatusBarHeight;
  }
  
  public static final boolean isScreenLocked(Context paramContext) {
    return !((KeyguardManager)paramContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
  }
  
  public static final boolean isScreenOn(Context paramContext) {
    return ((PowerManager)paramContext.getSystemService("power")).isScreenOn();
  }
  
  public static int measureViewHeight(View paramView) {
    if (paramView == null)
      return 0; 
    ViewGroup.LayoutParams layoutParams2 = paramView.getLayoutParams();
    ViewGroup.LayoutParams layoutParams1 = layoutParams2;
    if (layoutParams2 == null)
      layoutParams1 = new ViewGroup.LayoutParams(-1, -2); 
    int j = ViewGroup.getChildMeasureSpec(0, 0, layoutParams1.width);
    int i = layoutParams1.height;
    if (i > 0) {
      i = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    } else {
      i = View.MeasureSpec.makeMeasureSpec(0, 0);
    } 
    paramView.measure(j, i);
    return paramView.getMeasuredHeight();
  }
  
  public static int px2dip(Context paramContext, float paramFloat) {
    return (int)(paramFloat / getScreenDensity(paramContext) + 0.5F);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\utils\DensityUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */