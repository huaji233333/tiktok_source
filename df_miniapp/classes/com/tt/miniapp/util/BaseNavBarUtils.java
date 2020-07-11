package com.tt.miniapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.view.ViewConfiguration;
import java.lang.reflect.Method;

public class BaseNavBarUtils {
  private static String getNavBarOverride() {
    if (Build.VERSION.SDK_INT >= 19)
      try {
        Method method = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[] { String.class });
        method.setAccessible(true);
        return (String)method.invoke((Object)null, new Object[] { "qemu.hw.mainkeys" });
      } finally {
        Exception exception;
      }  
    return null;
  }
  
  public static boolean hasNavBar(Context paramContext) {
    String str;
    Resources resources = paramContext.getResources();
    int i = resources.getIdentifier("config_showNavigationBar", "bool", "android");
    if (i != 0) {
      boolean bool = resources.getBoolean(i);
      str = getNavBarOverride();
      if ("1".equals(str))
        return false; 
      if ("0".equals(str))
        bool = true; 
      return bool;
    } 
    return !ViewConfiguration.get((Context)str).hasPermanentMenuKey();
  }
  
  public static void hideNavigation(Activity paramActivity) {
    if (!hasNavBar((Context)paramActivity))
      return; 
    if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
      paramActivity.getWindow().getDecorView().setSystemUiVisibility(8);
      return;
    } 
    if (Build.VERSION.SDK_INT >= 19) {
      paramActivity.getWindow().getDecorView().setSystemUiVisibility(7942);
      paramActivity.getWindow().addFlags(134217728);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\BaseNavBarUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */