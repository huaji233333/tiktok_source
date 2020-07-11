package com.tt.miniapphost.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.WindowManager;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.util.ConcaveScreenUtils;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.language.LocaleManager;
import java.util.regex.Pattern;

public class UIUtils {
  private static int mDeviceHeight = -1;
  
  private static int mDeviceWidth = -1;
  
  public static int argb(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    return (int)(paramFloat1 * 255.0F + 0.5F) << 24 | (int)(paramFloat2 * 255.0F + 0.5F) << 16 | (int)(paramFloat3 * 255.0F + 0.5F) << 8 | (int)(paramFloat4 * 255.0F + 0.5F);
  }
  
  public static void attachToDecorView(Activity paramActivity, View paramView) {
    if (paramActivity != null) {
      if (paramView == null)
        return; 
      detachFromParent(paramView);
      View view = paramActivity.getWindow().getDecorView();
      if (view instanceof ViewGroup) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        ((ViewGroup)view).addView(paramView, layoutParams);
      } 
    } 
  }
  
  public static void configTitleBarWithHeight(final Context context, final View titleBar) {
    if (context != null) {
      if (titleBar == null)
        return; 
      titleBar.post(new Runnable() {
            public final void run() {
              ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)titleBar.getLayoutParams();
              int i = UIUtils.getTitleBarHeight(context);
              if (i != marginLayoutParams.height || marginLayoutParams.topMargin != 0) {
                marginLayoutParams.height = i;
                marginLayoutParams.topMargin = 0;
                titleBar.requestLayout();
              } 
            }
          });
    } 
  }
  
  public static void configTitleBarWithOriHeight(final Context context, final View titleBar) {
    if (context != null) {
      if (titleBar == null)
        return; 
      titleBar.post(new Runnable() {
            public final void run() {
              ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)titleBar.getLayoutParams();
              int i = UIUtils.getOriginTitleBarHeight(context);
              if (i != marginLayoutParams.height || marginLayoutParams.topMargin != 0) {
                marginLayoutParams.height = i;
                marginLayoutParams.topMargin = 0;
                titleBar.requestLayout();
              } 
            }
          });
    } 
  }
  
  public static void detachFromParent(View paramView) {
    if (paramView != null) {
      if (paramView.getParent() == null)
        return; 
      ViewParent viewParent = paramView.getParent();
      if (!(viewParent instanceof ViewGroup))
        return; 
      try {
        ((ViewGroup)viewParent).removeView(paramView);
        return;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "UIUtils", exception.getStackTrace());
      } 
    } 
  }
  
  public static float dip2Px(Context paramContext, float paramFloat) {
    return paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F;
  }
  
  public static int getBottom(View paramView1, View paramView2) {
    return getPositionInternal(4, paramView1, paramView2);
  }
  
  public static int getBottomStatusHeight(Context paramContext) {
    return getScreenHeightWithNavBar(paramContext) - getScreenHeight(paramContext);
  }
  
  public static int getDeviceHeight(Context paramContext) {
    if (mDeviceHeight < 0) {
      WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
      if (windowManager != null) {
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        mDeviceWidth = point.x;
        mDeviceHeight = point.y;
      } 
    } 
    return mDeviceHeight;
  }
  
  public static int getDeviceWidth(Context paramContext) {
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    if (windowManager != null) {
      Point point = new Point();
      windowManager.getDefaultDisplay().getSize(point);
      mDeviceWidth = point.x;
      mDeviceHeight = point.y;
    } 
    return mDeviceWidth;
  }
  
  public static int getLeft(View paramView1, View paramView2) {
    return getPositionInternal(1, paramView1, paramView2);
  }
  
  public static Drawable getMaterialLoadingDrawable(Context paramContext, View paramView) {
    if (paramContext == null)
      return null; 
    TypedArray typedArray = paramContext.obtainStyledAttributes(16974522, new int[] { 16843067 });
    if (typedArray != null) {
      Drawable drawable = typedArray.getDrawable(0);
      typedArray.recycle();
    } else {
      paramContext = null;
    } 
    return (paramContext != null) ? paramContext.mutate() : null;
  }
  
  public static int getOriginTitleBarHeight(Context paramContext) {
    return (int)dip2Px(paramContext, 44.0F);
  }
  
  private static int getPositionInternal(int paramInt, View paramView1, View paramView2) {
    // Byte code:
    //   0: iconst_0
    //   1: istore #5
    //   3: iload #5
    //   5: istore #4
    //   7: aload_1
    //   8: ifnull -> 145
    //   11: aload_2
    //   12: ifnonnull -> 17
    //   15: iconst_0
    //   16: ireturn
    //   17: aconst_null
    //   18: astore #6
    //   20: iload_0
    //   21: iconst_3
    //   22: if_icmpeq -> 43
    //   25: iload_0
    //   26: iconst_4
    //   27: if_icmpeq -> 35
    //   30: iconst_0
    //   31: istore_3
    //   32: goto -> 52
    //   35: aload_2
    //   36: invokevirtual getHeight : ()I
    //   39: istore_3
    //   40: goto -> 48
    //   43: aload_2
    //   44: invokevirtual getWidth : ()I
    //   47: istore_3
    //   48: iload_3
    //   49: iconst_0
    //   50: iadd
    //   51: istore_3
    //   52: aload #6
    //   54: aload_1
    //   55: if_acmpeq -> 142
    //   58: iload_0
    //   59: iconst_1
    //   60: if_icmpeq -> 97
    //   63: iload_0
    //   64: iconst_2
    //   65: if_icmpeq -> 81
    //   68: iload_0
    //   69: iconst_3
    //   70: if_icmpeq -> 97
    //   73: iload_0
    //   74: iconst_4
    //   75: if_icmpeq -> 81
    //   78: goto -> 115
    //   81: iload_3
    //   82: aload_2
    //   83: invokevirtual getScrollY : ()I
    //   86: isub
    //   87: istore #4
    //   89: aload_2
    //   90: invokevirtual getTop : ()I
    //   93: istore_3
    //   94: goto -> 110
    //   97: iload_3
    //   98: aload_2
    //   99: invokevirtual getScrollX : ()I
    //   102: isub
    //   103: istore #4
    //   105: aload_2
    //   106: invokevirtual getLeft : ()I
    //   109: istore_3
    //   110: iload #4
    //   112: iload_3
    //   113: iadd
    //   114: istore_3
    //   115: aload_2
    //   116: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   119: astore #6
    //   121: iload #5
    //   123: istore #4
    //   125: aload #6
    //   127: instanceof android/view/View
    //   130: ifeq -> 145
    //   133: aload #6
    //   135: checkcast android/view/View
    //   138: astore_2
    //   139: goto -> 52
    //   142: iload_3
    //   143: istore #4
    //   145: iload #4
    //   147: ireturn
  }
  
  public static int getRight(View paramView1, View paramView2) {
    return getPositionInternal(3, paramView1, paramView2);
  }
  
  public static final int getScreenHeight(Context paramContext) {
    if (paramContext == null)
      return 0; 
    DisplayMetrics displayMetrics = paramContext.getResources().getDisplayMetrics();
    return (displayMetrics == null) ? 0 : displayMetrics.heightPixels;
  }
  
  public static int getScreenHeightWithNavBar(Context paramContext) {
    Display display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    try {
      Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[] { DisplayMetrics.class }).invoke(display, new Object[] { displayMetrics });
      return displayMetrics.heightPixels;
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public static final int getScreenWidth(Context paramContext) {
    if (paramContext == null)
      return 0; 
    DisplayMetrics displayMetrics = paramContext.getResources().getDisplayMetrics();
    return (displayMetrics == null) ? 0 : displayMetrics.widthPixels;
  }
  
  public static int getScreenWidthWithNavBar(Context paramContext) {
    Display display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    try {
      Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[] { DisplayMetrics.class }).invoke(display, new Object[] { displayMetrics });
      return displayMetrics.widthPixels;
    } catch (Exception exception) {
      return 0;
    } 
  }
  
  public static int getSlideInAnimation() {
    return isRTL() ? 2131034234 : 2131034236;
  }
  
  public static int getSlideOutAnimation() {
    return isRTL() ? 2131034240 : 2131034241;
  }
  
  private static int getStatusBarHeight(Context paramContext) {
    boolean bool;
    if ((paramContext.getResources().getConfiguration()).orientation == 2) {
      bool = true;
    } else {
      bool = false;
    } 
    return getStatusBarHeight(paramContext, bool);
  }
  
  private static int getStatusBarHeight(Context paramContext, boolean paramBoolean) {
    return paramBoolean ? (int)(dip2Px(paramContext, 14.0F) + 0.0F) : (int)(DevicesUtil.getStatusBarHeight(paramContext) + dip2Px(paramContext, 0.0F));
  }
  
  public static String getString(int paramInt) {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (application == null) ? "" : application.getResources().getString(paramInt);
  }
  
  public static int getTitleBarHeight(Context paramContext) {
    return getOriginTitleBarHeight(paramContext) + getStatusBarHeight(paramContext);
  }
  
  public static int getTitleBarHeight(Context paramContext, boolean paramBoolean) {
    return getOriginTitleBarHeight(paramContext) + getStatusBarHeight(paramContext, paramBoolean);
  }
  
  public static int getTitleBarMarginTop(Context paramContext, boolean paramBoolean) {
    return paramBoolean ? (int)(dip2Px(paramContext, 14.0F) + 0.0F) : (int)(DevicesUtil.getStatusBarHeight(paramContext) + dip2Px(paramContext, 0.0F));
  }
  
  public static int getTop(View paramView1, View paramView2) {
    return getPositionInternal(2, paramView1, paramView2);
  }
  
  public static boolean isColor(String paramString) {
    return Pattern.matches("^#([0-9a-fA-F]{8}|[0-9a-fA-F]{6}|[0-9a-fA-F]{3})$", paramString);
  }
  
  public static boolean isKeyboardActive(int paramInt) {
    return (ConcaveScreenUtils.isVivoConcaveScreen() && paramInt == 120) ? false : ((paramInt > 0));
  }
  
  public static boolean isRTL() {
    return LocaleManager.getInst().isRTL();
  }
  
  public static boolean isRTL(Context paramContext) {
    return (paramContext != null && Build.VERSION.SDK_INT >= 17 && paramContext.getResources().getConfiguration().getLayoutDirection() == 1);
  }
  
  public static boolean isScreenOriatationPortrait(Context paramContext) {
    return ((paramContext.getResources().getConfiguration()).orientation == 1);
  }
  
  public static boolean isTouchPointInView(View paramView, MotionEvent paramMotionEvent) {
    if (paramView == null)
      return false; 
    int[] arrayOfInt = new int[2];
    paramView.getLocationOnScreen(arrayOfInt);
    int i = arrayOfInt[0];
    int j = arrayOfInt[1];
    int k = paramView.getMeasuredWidth();
    int m = paramView.getMeasuredHeight();
    int n = (int)paramMotionEvent.getRawX();
    int i1 = (int)paramMotionEvent.getRawY();
    return (i1 >= j && i1 <= m + j && n >= i && n <= k + i);
  }
  
  public static final boolean isViewVisible(View paramView) {
    return (paramView == null) ? false : ((paramView.getVisibility() == 0));
  }
  
  public static int parseColor(String paramString) throws IllegalColorException {
    try {
      return Color.parseColor(paramString);
    } catch (IllegalArgumentException illegalArgumentException) {
      StringBuilder stringBuilder = new StringBuilder("illegal color ");
      stringBuilder.append(paramString);
      throw new IllegalColorException(stringBuilder.toString());
    } 
  }
  
  public static int parseColor(String paramString1, String paramString2) {
    if (!TextUtils.isEmpty(paramString1))
      try {
        return Color.parseColor(paramString1);
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder("illegal color ");
        stringBuilder.append(paramString1);
        AppBrandLogger.eWithThrowable("UIUtils", stringBuilder.toString(), exception);
      }  
    return Color.parseColor(paramString2);
  }
  
  public static float px2Sp(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).scaledDensity + 0.5F);
  }
  
  public static int px2dip(Context paramContext, float paramFloat) {
    return (int)(paramFloat / (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
  }
  
  public static void removeParentView(View paramView) {
    if (paramView == null)
      return; 
    try {
      ViewParent viewParent = paramView.getParent();
      if (viewParent instanceof ViewGroup)
        ((ViewGroup)viewParent).removeView(paramView); 
      return;
    } catch (Exception exception) {
      AppBrandLogger.d("UIUtils", new Object[] { exception });
      return;
    } 
  }
  
  public static String rgbaToFullARGBStr(String paramString1, String paramString2) {
    if (paramString1 == null)
      return paramString2; 
    int i = paramString1.length();
    StringBuilder stringBuilder = new StringBuilder("#");
    if (i == 7 && paramString1.charAt(0) == '#')
      return paramString1; 
    if (i == 9 && paramString1.charAt(0) == '#') {
      stringBuilder.append(paramString1.substring(7, 9));
      stringBuilder.append(paramString1.substring(1, 7));
      return stringBuilder.toString();
    } 
    if ((i == 4 || i == 5) && paramString1.charAt(0) == '#') {
      if (i == 5) {
        stringBuilder.append(paramString1.charAt(4));
        stringBuilder.append(paramString1.charAt(4));
      } 
      stringBuilder.append(paramString1.charAt(1));
      stringBuilder.append(paramString1.charAt(1));
      stringBuilder.append(paramString1.charAt(2));
      stringBuilder.append(paramString1.charAt(2));
      stringBuilder.append(paramString1.charAt(3));
      stringBuilder.append(paramString1.charAt(3));
      return stringBuilder.toString();
    } 
    return paramString2;
  }
  
  public static void setActivityOrientation(Activity paramActivity, int paramInt) {
    if (ProcessUtil.isMiniappProcess() && AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())
      return; 
    paramActivity.setRequestedOrientation(paramInt);
  }
  
  public static void setOnTouchBackground(View paramView) {
    if (paramView == null)
      return; 
    paramView.setOnTouchListener(new View.OnTouchListener() {
          public final boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            int i = param1MotionEvent.getAction();
            if (i != 0) {
              if (i == 1 || i == 3)
                param1View.setAlpha(1.0F); 
            } else {
              param1View.setAlpha(0.6F);
            } 
            return false;
          }
        });
  }
  
  public static void setProperLayoutDirection(View paramView) {
    if (isRTL()) {
      paramView.setLayoutDirection(1);
      return;
    } 
    paramView.setLayoutDirection(0);
  }
  
  public static final boolean setViewVisibility(View paramView, int paramInt) {
    if (paramView == null || paramView.getVisibility() == paramInt || !visibilityValid(paramInt))
      return false; 
    paramView.setVisibility(paramInt);
    return true;
  }
  
  public static void shadow(View paramView, float paramFloat1, final float radius, final float alpha, final int dx, final int dy) {
    if (paramView == null)
      return; 
    paramView.setOutlineProvider(new ViewOutlineProvider() {
          public final void getOutline(View param1View, Outline param1Outline) {
            Rect rect = new Rect(0, 0, param1View.getWidth(), param1View.getHeight());
            rect.offset(dx, dy);
            param1Outline.setRoundRect(rect, radius);
            param1Outline.setAlpha(alpha);
          }
        });
    paramView.setElevation(paramFloat1);
  }
  
  public static float sp2Px(Context paramContext, float paramFloat) {
    return paramFloat * (paramContext.getResources().getDisplayMetrics()).scaledDensity + 0.5F;
  }
  
  public static int sp2px(Context paramContext, float paramFloat) {
    return (int)TypedValue.applyDimension(2, paramFloat, paramContext.getResources().getDisplayMetrics());
  }
  
  public static ColorStateList toColorStateList(int paramInt1, int paramInt2) {
    return toColorStateList(paramInt1, paramInt2, paramInt2, paramInt1);
  }
  
  public static ColorStateList toColorStateList(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int[] arrayOfInt1 = { 16842910, 16842908 };
    int[] arrayOfInt2 = new int[0];
    return new ColorStateList(new int[][] { { 16842919, 16842910 }, , arrayOfInt1, { 16842910 }, , { 16842908 }, , { 16842909 }, , arrayOfInt2 }, new int[] { paramInt2, paramInt3, paramInt1, paramInt3, paramInt4, paramInt1 });
  }
  
  public static String toColorString(int paramInt) {
    return toColorString(paramInt, false);
  }
  
  public static String toColorString(int paramInt, boolean paramBoolean) {
    String str2 = Integer.toHexString(Color.alpha(paramInt));
    String str3 = Integer.toHexString(Color.red(paramInt));
    String str4 = Integer.toHexString(Color.green(paramInt));
    String str5 = Integer.toHexString(Color.blue(paramInt));
    String str1 = str2;
    if (str2.length() == 1) {
      StringBuilder stringBuilder1 = new StringBuilder("0");
      stringBuilder1.append(str2);
      str1 = stringBuilder1.toString();
    } 
    str2 = str3;
    if (str3.length() == 1) {
      StringBuilder stringBuilder1 = new StringBuilder("0");
      stringBuilder1.append(str3);
      str2 = stringBuilder1.toString();
    } 
    str3 = str4;
    if (str4.length() == 1) {
      StringBuilder stringBuilder1 = new StringBuilder("0");
      stringBuilder1.append(str4);
      str3 = stringBuilder1.toString();
    } 
    str4 = str5;
    if (str5.length() == 1) {
      StringBuilder stringBuilder1 = new StringBuilder("0");
      stringBuilder1.append(str5);
      str4 = stringBuilder1.toString();
    } 
    if (paramBoolean) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str1);
      stringBuilder1.append(str2);
      stringBuilder1.append(str3);
      stringBuilder1.append(str4);
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str2);
    stringBuilder.append(str3);
    stringBuilder.append(str4);
    return stringBuilder.toString();
  }
  
  public static int toDarkenColor(int paramInt) {
    return toDarkenColor(paramInt, 0.8F);
  }
  
  public static int toDarkenColor(int paramInt, float paramFloat) {
    float[] arrayOfFloat = new float[3];
    Color.colorToHSV(paramInt, arrayOfFloat);
    arrayOfFloat[2] = arrayOfFloat[2] * paramFloat;
    return Color.HSVToColor(arrayOfFloat);
  }
  
  public static CharSequence txt(int paramInt) {
    Application application = AppbrandContext.getInst().getApplicationContext();
    return (application == null) ? "" : application.getText(paramInt);
  }
  
  public static void updateLayoutMargin(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramView == null)
      return; 
    ViewGroup.LayoutParams layoutParams = paramView.getLayoutParams();
    if (layoutParams == null)
      return; 
    if (layoutParams instanceof ViewGroup.MarginLayoutParams)
      updateMargin(paramView, (ViewGroup.MarginLayoutParams)layoutParams, paramInt1, paramInt2, paramInt3, paramInt4); 
  }
  
  private static void updateMargin(View paramView, ViewGroup.MarginLayoutParams paramMarginLayoutParams, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (paramView != null && paramMarginLayoutParams != null) {
      if (paramMarginLayoutParams.leftMargin == paramInt1 && paramMarginLayoutParams.topMargin == paramInt2 && paramMarginLayoutParams.rightMargin == paramInt3 && paramMarginLayoutParams.bottomMargin == paramInt4)
        return; 
      if (paramInt1 != -3)
        paramMarginLayoutParams.leftMargin = paramInt1; 
      if (paramInt2 != -3)
        paramMarginLayoutParams.topMargin = paramInt2; 
      if (paramInt3 != -3)
        paramMarginLayoutParams.rightMargin = paramInt3; 
      if (paramInt4 != -3)
        paramMarginLayoutParams.bottomMargin = paramInt4; 
      paramView.setLayoutParams((ViewGroup.LayoutParams)paramMarginLayoutParams);
    } 
  }
  
  private static boolean visibilityValid(int paramInt) {
    return (paramInt == 0 || paramInt == 8 || paramInt == 4);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\UIUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */