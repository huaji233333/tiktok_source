package com.tt.miniapp.base.ui.statusbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.tt.miniapp.base.utils.CompatibilitySupport;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;

public class StatusBarManager {
  private static volatile boolean isInited;
  
  public static HashSet<WeakReference<StatusBarHeightChangeListener>> listeners = new HashSet<WeakReference<StatusBarHeightChangeListener>>();
  
  public static volatile int statusBarHeight = 0;
  
  static {
    isInited = false;
  }
  
  public static void dispatchStatusBarHeightChange(int paramInt) {
    if (statusBarHeight != paramInt) {
      statusBarHeight = paramInt;
      for (WeakReference<StatusBarHeightChangeListener> weakReference : listeners) {
        if (weakReference != null) {
          StatusBarHeightChangeListener statusBarHeightChangeListener = weakReference.get();
          if (statusBarHeightChangeListener != null)
            statusBarHeightChangeListener.onStatusBarHeightChange(statusBarHeight); 
        } 
      } 
    } 
  }
  
  public static int getStatusBarHeight(Context paramContext) {
    int i = paramContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (i > 0) {
      i = paramContext.getResources().getDimensionPixelSize(i);
    } else {
      i = 0;
    } 
    int j = i;
    if (i == 0)
      try {
        Class<?> clazz = Class.forName("com.android.internal.R$dimen");
        Object object = clazz.newInstance();
        return paramContext.getResources().getDimensionPixelSize(j);
      } finally {
        paramContext = null;
      }  
    return j;
  }
  
  public static void init(Context paramContext) {
    if (isInited)
      return; 
    if (Build.VERSION.SDK_INT >= 19)
      statusBarHeight = getStatusBarHeight(paramContext); 
    isInited = true;
  }
  
  public static void registerStatusBarHeightChangeListener(StatusBarHeightChangeListener paramStatusBarHeightChangeListener) {
    for (WeakReference<StatusBarHeightChangeListener> weakReference1 : listeners) {
      if (weakReference1 != null) {
        StatusBarHeightChangeListener statusBarHeightChangeListener = weakReference1.get();
        if (statusBarHeightChangeListener != null && statusBarHeightChangeListener == paramStatusBarHeightChangeListener)
          return; 
      } 
    } 
    WeakReference<StatusBarHeightChangeListener> weakReference = new WeakReference<StatusBarHeightChangeListener>(paramStatusBarHeightChangeListener);
    listeners.add(weakReference);
    paramStatusBarHeightChangeListener.onStatusBarHeightChange(statusBarHeight);
  }
  
  public static boolean setStatusBarDarkMode(boolean paramBoolean, Activity paramActivity) {
    WindowManager.LayoutParams layoutParams;
    int i;
    boolean bool2;
    Method method;
    Window window = paramActivity.getWindow();
    if (Build.VERSION.SDK_INT >= 23) {
      View view = window.getDecorView();
      int j = view.getSystemUiVisibility();
      if (paramBoolean) {
        j |= 0x2000;
      } else {
        j &= 0xFFFFDFFF;
      } 
      view.setSystemUiVisibility(j);
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (CompatibilitySupport.isXiaomi()) {
      Class<?> clazz = paramActivity.getWindow().getClass();
      try {
        Class<?> clazz1 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
        i = clazz1.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(clazz1);
        method = clazz.getMethod("setExtraFlags", new Class[] { int.class, int.class });
        Window window1 = paramActivity.getWindow();
        if (paramBoolean) {
          int j = i;
          method.invoke(window1, new Object[] { Integer.valueOf(j), Integer.valueOf(i) });
          return true;
        } 
      } catch (Exception exception) {
        return bool2;
      } 
    } else {
      Field field;
      if (CompatibilitySupport.isMeizu()) {
        layoutParams = method.getAttributes();
        Field field1 = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
        field = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
        field1.setAccessible(true);
        field.setAccessible(true);
        j = field1.getInt((Object)null);
        i = field.getInt(layoutParams);
        if (paramBoolean) {
          j = i | j;
          field.setInt(layoutParams, j);
          method.setAttributes(layoutParams);
          return true;
        } 
      } else {
        return bool2;
      } 
      int j = (j ^ 0xFFFFFFFF) & i;
      field.setInt(layoutParams, j);
      method.setAttributes(layoutParams);
      return true;
    } 
    boolean bool1 = false;
    method.invoke(layoutParams, new Object[] { Integer.valueOf(bool1), Integer.valueOf(i) });
    return true;
  }
  
  public static void unRegisterStatusBarHeightChangeListener(StatusBarHeightChangeListener paramStatusBarHeightChangeListener) {
    Iterator<WeakReference<StatusBarHeightChangeListener>> iterator = listeners.iterator();
    while (iterator.hasNext()) {
      WeakReference<StatusBarHeightChangeListener> weakReference = iterator.next();
      if (weakReference != null) {
        StatusBarHeightChangeListener statusBarHeightChangeListener = weakReference.get();
        if (statusBarHeightChangeListener != null && statusBarHeightChangeListener == paramStatusBarHeightChangeListener) {
          iterator.remove();
          break;
        } 
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\ui\statusbar\StatusBarManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */