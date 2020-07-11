package com.tt.frontendapiinterface;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.DebugUtil;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class a {
  public static String a(String paramString) {
    return com.a.a("illegal args:%s", new Object[] { paramString });
  }
  
  public static String a(Throwable paramThrowable) {
    if (paramThrowable == null)
      return "null throwable"; 
    if (DebugUtil.debug())
      AppBrandLogger.e("ApiCallResultHelper", new Object[] { "generateThrowableExtraInfo", paramThrowable }); 
    return com.a.a("native exception %s stack:%s", new Object[] { paramThrowable, a(paramThrowable, 0) });
  }
  
  private static String a(Throwable paramThrowable, int paramInt) {
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    StringBuilder stringBuilder = new StringBuilder();
    int j = paramInt + 5;
    int i = j;
    if (j > arrayOfStackTraceElement.length)
      i = arrayOfStackTraceElement.length; 
    if (paramInt < i) {
      stringBuilder.append(arrayOfStackTraceElement[paramInt]);
      while (true) {
        if (++paramInt < i) {
          stringBuilder.append(" ");
          stringBuilder.append(arrayOfStackTraceElement[paramInt]);
          continue;
        } 
        break;
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String a(boolean paramBoolean, String... paramVarArgs) {
    String str = com.a.a("permission denied, %s %s", new Object[] { paramVarArgs[0], paramVarArgs[1] });
    AppBrandLogger.e("ApiCallResultHelper", new Object[] { "PermissionDenyStr unsupported args length" });
    return TextUtils.isEmpty(str) ? str : str.trim();
  }
  
  public static String a(String... paramVarArgs) {
    if (paramVarArgs.length == 2)
      return com.a.a("permission denied, %s %s", new Object[] { paramVarArgs[0], paramVarArgs[1] }); 
    if (paramVarArgs.length == 3)
      return com.a.a("permission denied, %s %s -> %s", new Object[] { paramVarArgs[0], paramVarArgs[1], paramVarArgs[2] }); 
    AppBrandLogger.e("ApiCallResultHelper", new Object[] { "PermissionDenyStr unsupported args length" });
    return "";
  }
  
  public static JSONObject a(HashMap<String, Object> paramHashMap) {
    if (paramHashMap != null) {
      JSONObject jSONObject = new JSONObject();
      for (String str : paramHashMap.keySet()) {
        try {
          jSONObject.put(str, paramHashMap.get(str));
        } catch (JSONException jSONException) {
          AppBrandLogger.e("ApiCallResultHelper", new Object[] { "generateJsonObjectResponseData", jSONException });
        } 
      } 
      return jSONObject;
    } 
    return null;
  }
  
  public static String b(String paramString) {
    return com.a.a("param:%s illegal", new Object[] { paramString });
  }
  
  public static String b(String... paramVarArgs) {
    if (paramVarArgs.length == 1)
      return com.a.a("no such file or directory %s", new Object[] { paramVarArgs[0] }); 
    if (paramVarArgs.length == 2)
      return com.a.a("no such file or directory, %s %s", new Object[] { paramVarArgs[0], paramVarArgs[1] }); 
    if (paramVarArgs.length == 3)
      return com.a.a("no such file or directory, %s %s -> %s", new Object[] { paramVarArgs[0], paramVarArgs[1], paramVarArgs[2] }); 
    AppBrandLogger.e("ApiCallResultHelper", new Object[] { "NoSuchFileStr unsupported args length" });
    return "";
  }
  
  public static String c(String paramString) {
    return com.a.a("%s is null", new Object[] { paramString });
  }
  
  public static String d(String paramString) {
    return com.a.a("unknown error on method %s stack:%s", new Object[] { paramString, a(new Throwable(), 1) });
  }
  
  public static String e(String paramString) {
    return com.a.a("file not exist path:%s", new Object[] { paramString });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\frontendapiinterface\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */