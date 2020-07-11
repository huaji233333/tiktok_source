package com.tt.miniapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.BatteryManager;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;

public class DevicesUtil {
  private static boolean sIsMiui;
  
  private static boolean sIsMiuiInited;
  
  private static String sMiuiVersion;
  
  private static int sStatusBarHeight;
  
  public static String version;
  
  public static String getBrand() {
    return Build.BRAND;
  }
  
  public static int getCurrentBattery(Context paramContext) {
    return ((BatteryManager)paramContext.getSystemService("batterymanager")).getIntProperty(4);
  }
  
  private static Display getDisplay(Context paramContext) {
    WindowManager windowManager;
    if (paramContext instanceof Activity) {
      windowManager = ((Activity)paramContext).getWindowManager();
    } else {
      windowManager = (WindowManager)windowManager.getSystemService("window");
    } 
    return (windowManager != null) ? windowManager.getDefaultDisplay() : null;
  }
  
  public static float getFontSize(Context paramContext) {
    Configuration configuration = paramContext.getResources().getConfiguration();
    float f = UIUtils.sp2px(paramContext, 12.0F);
    return configuration.fontScale * f;
  }
  
  public static String getLanguage() {
    Locale locale;
    if (Build.VERSION.SDK_INT >= 24) {
      locale = LocaleList.getDefault().get(0);
    } else {
      locale = Locale.getDefault();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(locale.getLanguage());
    stringBuilder.append("-");
    stringBuilder.append(locale.getCountry());
    return stringBuilder.toString();
  }
  
  public static String getModel() {
    return Build.MODEL;
  }
  
  public static int[] getNotchSize(Context paramContext) {
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    try {
      return arrayOfInt;
    } catch (Exception exception) {
      return arrayOfInt;
    } finally {
      paramContext = null;
    } 
  }
  
  public static float getPixelRadio(Context paramContext) {
    return (paramContext.getResources().getDisplayMetrics()).density;
  }
  
  public static String getPlatform() {
    return "Android";
  }
  
  public static int getScreenHight(Context paramContext) {
    Display display = getDisplay(paramContext);
    if (display == null)
      return 0; 
    try {
      Point point = new Point();
      display.getSize(point);
      return point.y;
    } catch (Exception exception) {
      AppBrandLogger.e("DevicesUtil", new Object[0]);
      return 0;
    } 
  }
  
  public static int getScreenRotation(Context paramContext) {
    return ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getRotation();
  }
  
  public static int getScreenWidth(Context paramContext) {
    Display display = getDisplay(paramContext);
    if (display == null)
      return 0; 
    try {
      Point point = new Point();
      display.getSize(point);
      return point.x;
    } catch (Exception exception) {
      AppBrandLogger.e("DevicesUtil", new Object[0]);
      return 0;
    } 
  }
  
  public static int getStatusBarHeight(Context paramContext) {
    int i = sStatusBarHeight;
    if (i > 0)
      return i; 
    if (ConcaveScreenUtils.isOVConcaveScreen(paramContext)) {
      i = (int)UIUtils.dip2Px(paramContext, 27.0F);
      sStatusBarHeight = i;
      return i;
    } 
    if (ConcaveScreenUtils.isHWConcaveScreen(paramContext)) {
      i = ConcaveScreenUtils.getHWConcaveScreenHeight(paramContext);
      sStatusBarHeight = i;
      return i;
    } 
    i = 0;
    int j = paramContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (j > 0)
      i = paramContext.getResources().getDimensionPixelOffset(j); 
    j = i;
    if (i == 0)
      j = (int)UIUtils.dip2Px(paramContext, 25.0F); 
    sStatusBarHeight = j;
    return j;
  }
  
  public static String getSystem() {
    return Build.VERSION.RELEASE;
  }
  
  public static String getVersion(Context paramContext) {
    if (TextUtils.isEmpty(version)) {
      PackageManager packageManager = paramContext.getPackageManager();
      try {
        version = (packageManager.getPackageInfo(paramContext.getPackageName(), 0)).versionName;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "DevicesUtil", exception.getStackTrace());
      } 
    } 
    return version;
  }
  
  public static int getWifiSignal(Context paramContext) {
    return 0;
  }
  
  public static boolean hasNotchInScreen(Context paramContext) {
    boolean bool;
    try {
      if (isHuawei()) {
        Class<?> clazz = paramContext.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
        bool = ((Boolean)clazz.getMethod("hasNotchInScreen", new Class[0]).invoke(clazz, new Object[0])).booleanValue();
        try {
          AppBrandLogger.d("DevicesUtil", new Object[] { "hasNotchInScreen:", Boolean.valueOf(bool) });
          return bool;
        } catch (Exception null) {}
      } else {
        return false;
      } 
    } catch (Exception exception) {
      bool = false;
    } 
    AppBrandLogger.e("DevicesUtil", new Object[] { "hasNotchInScreen error:", exception });
    return bool;
  }
  
  private static void initMiuiVersion() {
    if (sMiuiVersion == null) {
      try {
        sMiuiVersion = BuildProperties.getInstance().getProperty("ro.miui.ui.version.name");
      } catch (IOException iOException) {
        AppBrandLogger.stacktrace(6, "DevicesUtil", iOException.getStackTrace());
      } 
      String str2 = sMiuiVersion;
      String str1 = str2;
      if (str2 == null)
        str1 = ""; 
      sMiuiVersion = str1;
    } 
  }
  
  public static boolean isFlyme() {
    return Build.DISPLAY.startsWith("Flyme");
  }
  
  public static boolean isHuawei() {
    return (Build.MANUFACTURER != null && Build.MANUFACTURER.contains("HUAWEI"));
  }
  
  public static boolean isMiui() {
    if (!sIsMiuiInited) {
      try {
        Class.forName("miui.os.Build");
        sIsMiui = true;
      } catch (Exception exception) {
        AppBrandLogger.w("DevicesUtil", new Object[] { exception });
      } 
      sIsMiuiInited = true;
    } 
    return sIsMiui;
  }
  
  public static boolean isMiuiV7() {
    initMiuiVersion();
    return "V7".equals(sMiuiVersion);
  }
  
  public static boolean isMiuiV8() {
    initMiuiVersion();
    return "V8".equals(sMiuiVersion);
  }
  
  public static boolean isMiuiV9() {
    initMiuiVersion();
    return "V9".equals(sMiuiVersion);
  }
  
  public static boolean isScreenPortrait(Context paramContext) {
    int i = getScreenRotation(paramContext);
    return (i == 0 || i == 2);
  }
  
  public static void setFullScreenWindowLayoutInDisplayCutout(Window paramWindow) {
    if (paramWindow == null)
      return; 
    WindowManager.LayoutParams layoutParams = paramWindow.getAttributes();
    try {
      if (isHuawei()) {
        Class<?> clazz = Class.forName("com.huawei.android.view.LayoutParamsEx");
        layoutParams = clazz.getConstructor(new Class[] { WindowManager.LayoutParams.class }).newInstance(new Object[] { layoutParams });
        clazz.getMethod("addHwFlags", new Class[] { int.class }).invoke(layoutParams, new Object[] { Integer.valueOf(65536) });
      } 
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("DevicesUtil", new Object[] { "setFullScreenWindowLayoutInDisplayCutout error:", exception });
      return;
    } 
  }
  
  public static void setMiuiStatusBarDarkMode(boolean paramBoolean, Window paramWindow) {
    try {
      boolean bool;
      Class<?> clazz1 = paramWindow.getClass();
      Class<?> clazz2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
      int i = clazz2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(clazz2);
      Method method = clazz1.getMethod("setExtraFlags", new Class[] { int.class, int.class });
      if (paramBoolean) {
        bool = i;
      } else {
        bool = false;
      } 
      return;
    } finally {
      paramWindow = null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\DevicesUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */