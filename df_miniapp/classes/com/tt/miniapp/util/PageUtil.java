package com.tt.miniapp.util;

import android.net.Uri;
import android.text.TextUtils;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class PageUtil {
  public static boolean equalQuery(String paramString1, String paramString2) {
    try {
      StringBuilder stringBuilder = new StringBuilder("http://");
      stringBuilder.append(paramString1);
      Uri uri1 = Uri.parse(stringBuilder.toString());
      stringBuilder = new StringBuilder("http://");
      stringBuilder.append(paramString2);
      Uri uri2 = Uri.parse(stringBuilder.toString());
      JSONObject jSONObject1 = getQueryObject(uri1);
      JSONObject jSONObject2 = getQueryObject(uri2);
      return jSONObject1.toString().equals(jSONObject2.toString());
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "PageUtil", exception.getStackTrace());
      return false;
    } 
  }
  
  public static String getCleanPath(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return ""; 
    String str = paramString;
    if (paramString.contains("."))
      str = paramString.substring(0, paramString.indexOf(".")); 
    paramString = str;
    if (str.contains("?"))
      paramString = str.substring(0, str.indexOf("?")); 
    str = paramString;
    if (paramString.contains("&"))
      str = paramString.substring(0, paramString.indexOf("&")); 
    return str;
  }
  
  public static String[] getPathAndQuery(String paramString) {
    String[] arrayOfString2 = new String[2];
    int i = 0;
    arrayOfString2[0] = "";
    arrayOfString2[1] = "";
    if (TextUtils.isEmpty(paramString))
      return arrayOfString2; 
    String[] arrayOfString1 = paramString.split("\\?");
    if (arrayOfString1.length > 1) {
      arrayOfString2[0] = AppConfig.cutHtmlSuffix(arrayOfString1[0]);
      arrayOfString2[1] = arrayOfString1[1];
    } else {
      arrayOfString2[0] = AppConfig.cutHtmlSuffix(arrayOfString1[0]);
      arrayOfString2[1] = "";
    } 
    while (i < 2) {
      if (arrayOfString2[i] == null)
        arrayOfString2[i] = ""; 
      i++;
    } 
    return arrayOfString2;
  }
  
  public static JSONObject getQueryObject(Uri paramUri) {
    JSONObject jSONObject = new JSONObject();
    if (paramUri != null) {
      Set set = paramUri.getQueryParameterNames();
      if (set != null)
        for (String str1 : set) {
          String str2 = paramUri.getQueryParameter(str1);
          try {
            jSONObject.put(str1, str2);
          } catch (Exception exception) {
            AppBrandLogger.stacktrace(6, "PageUtil", exception.getStackTrace());
          } 
        }  
    } 
    return jSONObject;
  }
  
  public static boolean isPageValidate(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      if (appConfig.getPageList() == null)
        return false; 
      int i = paramString.indexOf("?");
      String str = paramString;
      if (i > 0)
        str = paramString.substring(0, i); 
      if (appConfig.getPageList().indexOf(str) >= 0)
        return true; 
    } 
    return false;
  }
  
  public static boolean isTabPage(String paramString, AppConfig paramAppConfig) {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramAppConfig != null) {
      if ((paramAppConfig.getTabBar()).tabs == null)
        return false; 
      if (TextUtils.isEmpty(paramString))
        return false; 
      int i = paramString.indexOf("?");
      String str = paramString;
      if (i > 0)
        str = paramString.substring(0, i); 
      Iterator iterator = (paramAppConfig.getTabBar()).tabs.iterator();
      while (true) {
        bool1 = bool2;
        if (iterator.hasNext()) {
          if (TextUtils.equals(((AppConfig.TabBar.TabContent)iterator.next()).pagePath, str)) {
            bool1 = true;
            break;
          } 
          continue;
        } 
        break;
      } 
    } 
    return bool1;
  }
  
  public static PageRouterParams parseRouteParams(String paramString) {
    PageRouterParams pageRouterParams = new PageRouterParams();
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      pageRouterParams.url = jSONObject.optString("url", CharacterUtils.empty());
      if (!TextUtils.isEmpty(pageRouterParams.url)) {
        int i = pageRouterParams.url.indexOf("?");
        if (i > 0) {
          pageRouterParams.path = pageRouterParams.url.substring(0, i);
        } else {
          pageRouterParams.path = pageRouterParams.url;
        } 
      } 
      pageRouterParams.delta = jSONObject.optInt("delta", 1);
      return pageRouterParams;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "PageUtil", jSONException.getStackTrace());
      return pageRouterParams;
    } 
  }
  
  public static class PageRouterParams {
    public int delta;
    
    public String path;
    
    public String url;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\PageUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */