package com.tt.miniapp.util;

import android.text.TextUtils;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;

public class NavigationBarUtil {
  private static String getCurrentPageNavigationStyle() {
    String str2 = AppbrandApplicationImpl.getInst().getCurrentPagePath();
    if (TextUtils.isEmpty(str2))
      return null; 
    int i = str2.indexOf("?");
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig == null)
      return null; 
    String str1 = str2;
    if (i >= 0)
      if (i > str2.length()) {
        str1 = str2;
      } else {
        str1 = str2.substring(0, i);
      }  
    AppConfig.Page page = appConfig.page;
    if (page != null) {
      if (TextUtils.isEmpty(str1))
        return null; 
      AppConfig.Window window = page.getWindow(str1);
      if (window == null)
        return null; 
      if (window.hasNavigationStyle)
        return window.navigationStyle; 
    } 
    return null;
  }
  
  public static String getNavigationStyle() {
    String str = getCurrentPageNavigationStyle();
    if (!TextUtils.isEmpty(str))
      return str; 
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      AppConfig.Global global = appConfig.global;
      if (global != null) {
        AppConfig.Window window = global.window;
        if (window != null && window.hasNavigationStyle)
          return window.navigationStyle; 
      } 
    } 
    return "default";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\NavigationBarUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */