package com.tt.option.q;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.HostProcessBridge;
import org.json.JSONObject;

public final class d {
  public static String a = "0";
  
  private static String b = "0";
  
  public static String a() {
    if (a(b))
      return b; 
    if (AppbrandContext.getInst().getInitParams() != null)
      b = AppbrandContext.getInst().getInitParams().getDeviceId(); 
    if (a(b))
      return b; 
    JSONObject jSONObject = HostProcessBridge.getNetCommonParams();
    if (jSONObject != null)
      b = jSONObject.optString("device_id", "0"); 
    return b;
  }
  
  public static boolean a(Context paramContext) {
    NetworkInfo[] arrayOfNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getAllNetworkInfo();
    if (arrayOfNetworkInfo != null) {
      int j = arrayOfNetworkInfo.length;
      for (int i = 0; i < j; i++) {
        NetworkInfo networkInfo = arrayOfNetworkInfo[i];
        if (networkInfo.isAvailable() && networkInfo.getState() == NetworkInfo.State.CONNECTED)
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean a(String paramString) {
    return (!TextUtils.isEmpty(paramString) && !TextUtils.equals(paramString, "null") && !TextUtils.equals(paramString, "0"));
  }
  
  public static final String b(Context paramContext) {
    String str1;
    String str3 = null;
    Context context = null;
    String str2 = str3;
    try {
      NetworkInfo networkInfo = e.a((ConnectivityManager)paramContext.getSystemService("connectivity"));
      paramContext = context;
      if (networkInfo != null) {
        paramContext = context;
        str2 = str3;
        if (networkInfo.isAvailable()) {
          str2 = str3;
          String str = networkInfo.getTypeName();
          str2 = str3;
          if (!"MOBILE".equalsIgnoreCase(str)) {
            str1 = str;
          } else {
            str2 = str3;
            str3 = networkInfo.getExtraInfo();
            str1 = str3;
            if (str3 == null) {
              str2 = str3;
              StringBuilder stringBuilder = new StringBuilder();
              str2 = str3;
              stringBuilder.append(str);
              str2 = str3;
              stringBuilder.append("#[]");
              str2 = str3;
              String str4 = stringBuilder.toString();
            } 
          } 
        } 
      } 
    } catch (Exception exception) {
      str1 = str2;
      AppBrandLogger.stacktrace(6, "NetRequestUtil", exception.getStackTrace());
    } 
    return (str1 == null) ? "" : str1;
  }
  
  public static boolean b() {
    return (SettingsDAO.getInt((Context)AppbrandContext.getInst().getApplicationContext(), 0, new Enum[] { (Enum)Settings.TT_TMA_HEADER_UNITE, (Enum)Settings.TmaHeaderUnite.IS_NEW_HEADER }) == 1);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\q\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */