package com.facebook.react.modules.systeminfo;

import android.os.Build;
import com.a;
import java.util.Locale;

public class AndroidInfoHelpers {
  public static String getFriendlyDeviceName() {
    if (isRunningOnGenymotion())
      return Build.MODEL; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Build.MODEL);
    stringBuilder.append(" - ");
    stringBuilder.append(Build.VERSION.RELEASE);
    stringBuilder.append(" - API ");
    stringBuilder.append(Build.VERSION.SDK_INT);
    return stringBuilder.toString();
  }
  
  public static String getInspectorProxyHost() {
    return getServerIpAddress(8082);
  }
  
  public static String getServerHost() {
    return getServerIpAddress(8081);
  }
  
  private static String getServerIpAddress(int paramInt) {
    String str;
    if (isRunningOnGenymotion()) {
      str = "10.0.3.2";
    } else if (isRunningOnStockEmulator()) {
      str = "10.0.2.2";
    } else {
      str = "localhost";
    } 
    return a.a(Locale.US, "%s:%d", new Object[] { str, Integer.valueOf(paramInt) });
  }
  
  private static boolean isRunningOnGenymotion() {
    return Build.FINGERPRINT.contains("vbox");
  }
  
  private static boolean isRunningOnStockEmulator() {
    return Build.FINGERPRINT.contains("generic");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\systeminfo\AndroidInfoHelpers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */