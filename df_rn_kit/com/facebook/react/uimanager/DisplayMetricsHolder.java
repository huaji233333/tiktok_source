package com.facebook.react.uimanager;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.facebook.i.a.a;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DisplayMetricsHolder {
  private static DisplayMetrics sScreenDisplayMetrics;
  
  private static DisplayMetrics sWindowDisplayMetrics;
  
  public static WritableNativeMap getDisplayMetricsMap(double paramDouble) {
    if (sWindowDisplayMetrics != null || sScreenDisplayMetrics != null) {
      boolean bool1 = true;
      a.a(Boolean.valueOf(bool1), "DisplayMetricsHolder must be initialized with initDisplayMetricsIfNotInitialized or initDisplayMetrics");
      WritableNativeMap writableNativeMap1 = new WritableNativeMap();
      writableNativeMap1.putMap("windowPhysicalPixels", (WritableMap)getPhysicalPixelsMap(sWindowDisplayMetrics, paramDouble));
      writableNativeMap1.putMap("screenPhysicalPixels", (WritableMap)getPhysicalPixelsMap(sScreenDisplayMetrics, paramDouble));
      return writableNativeMap1;
    } 
    boolean bool = false;
    a.a(Boolean.valueOf(bool), "DisplayMetricsHolder must be initialized with initDisplayMetricsIfNotInitialized or initDisplayMetrics");
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    writableNativeMap.putMap("windowPhysicalPixels", (WritableMap)getPhysicalPixelsMap(sWindowDisplayMetrics, paramDouble));
    writableNativeMap.putMap("screenPhysicalPixels", (WritableMap)getPhysicalPixelsMap(sScreenDisplayMetrics, paramDouble));
    return writableNativeMap;
  }
  
  private static WritableNativeMap getPhysicalPixelsMap(DisplayMetrics paramDisplayMetrics, double paramDouble) {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    writableNativeMap.putInt("width", paramDisplayMetrics.widthPixels);
    writableNativeMap.putInt("height", paramDisplayMetrics.heightPixels);
    writableNativeMap.putDouble("scale", paramDisplayMetrics.density);
    writableNativeMap.putDouble("fontScale", paramDouble);
    writableNativeMap.putDouble("densityDpi", paramDisplayMetrics.densityDpi);
    return writableNativeMap;
  }
  
  public static DisplayMetrics getScreenDisplayMetrics() {
    return sScreenDisplayMetrics;
  }
  
  @Deprecated
  public static DisplayMetrics getWindowDisplayMetrics() {
    return sWindowDisplayMetrics;
  }
  
  public static void initDisplayMetrics(Context paramContext) {
    DisplayMetrics displayMetrics2 = paramContext.getResources().getDisplayMetrics();
    setWindowDisplayMetrics(displayMetrics2);
    DisplayMetrics displayMetrics1 = new DisplayMetrics();
    displayMetrics1.setTo(displayMetrics2);
    WindowManager windowManager = (WindowManager)paramContext.getSystemService("window");
    a.a(windowManager, "WindowManager is null!");
    Display display = windowManager.getDefaultDisplay();
    if (Build.VERSION.SDK_INT >= 17) {
      display.getRealMetrics(displayMetrics1);
    } else {
      try {
        Method method = Display.class.getMethod("getRawHeight", new Class[0]);
        displayMetrics1.widthPixels = ((Integer)Display.class.getMethod("getRawWidth", new Class[0]).invoke(display, new Object[0])).intValue();
        displayMetrics1.heightPixels = ((Integer)method.invoke(display, new Object[0])).intValue();
        setScreenDisplayMetrics(displayMetrics1);
        return;
      } catch (InvocationTargetException invocationTargetException) {
      
      } catch (IllegalAccessException illegalAccessException) {
      
      } catch (NoSuchMethodException noSuchMethodException) {}
      throw new RuntimeException("Error getting real dimensions for API level < 17", noSuchMethodException);
    } 
    setScreenDisplayMetrics(displayMetrics1);
  }
  
  public static void initDisplayMetricsIfNotInitialized(Context paramContext) {
    if (getScreenDisplayMetrics() != null)
      return; 
    initDisplayMetrics(paramContext);
  }
  
  public static void setScreenDisplayMetrics(DisplayMetrics paramDisplayMetrics) {
    sScreenDisplayMetrics = paramDisplayMetrics;
  }
  
  public static void setWindowDisplayMetrics(DisplayMetrics paramDisplayMetrics) {
    sWindowDisplayMetrics = paramDisplayMetrics;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\DisplayMetricsHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */