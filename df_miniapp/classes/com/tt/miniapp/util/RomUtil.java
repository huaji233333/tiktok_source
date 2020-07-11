package com.tt.miniapp.util;

import android.os.Build;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class RomUtil {
  private static final CharSequence AMIGO;
  
  private static final CharSequence FUNTOUCHOS;
  
  private static final CharSequence SONY = "sony";
  
  static {
    AMIGO = "amigo";
    FUNTOUCHOS = "funtouch";
  }
  
  public static String get360OSVersion() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getSystemProperty("ro.build.uiversion"));
    stringBuilder.append("_");
    stringBuilder.append(Build.DISPLAY);
    return stringBuilder.toString();
  }
  
  public static String getAmigoVersion() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Build.DISPLAY);
    stringBuilder.append("_");
    stringBuilder.append(getSystemProperty("ro.gn.sv.version"));
    return stringBuilder.toString();
  }
  
  public static String getColorOsVersion() {
    if (isColorOS()) {
      StringBuilder stringBuilder = new StringBuilder("coloros_");
      stringBuilder.append(getSystemProperty("ro.build.version.opporom"));
      stringBuilder.append("_");
      stringBuilder.append(Build.DISPLAY);
      return stringBuilder.toString();
    } 
    return "";
  }
  
  public static String getEUIVersion() {
    if (isEUI()) {
      StringBuilder stringBuilder = new StringBuilder("eui_");
      stringBuilder.append(getSystemProperty("ro.letv.release.version"));
      stringBuilder.append("_");
      stringBuilder.append(Build.DISPLAY);
      return stringBuilder.toString();
    } 
    return "";
  }
  
  public static String getFlymeVersion() {
    String str = Build.DISPLAY;
    return (str != null && str.toLowerCase(Locale.getDefault()).contains("flyme")) ? str : "";
  }
  
  public static String getFuntouchOSVersion() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getSystemProperty("ro.vivo.os.build.display.id"));
    stringBuilder.append("_");
    stringBuilder.append(getSystemProperty("ro.vivo.product.version"));
    return stringBuilder.toString();
  }
  
  private static String getSystemProperty(String paramString) {
    BufferedReader bufferedReader;
    String str = "";
    try {
      Runtime runtime = Runtime.getRuntime();
      StringBuilder stringBuilder = new StringBuilder("getprop ");
      stringBuilder.append(paramString);
      Process process = runtime.exec(stringBuilder.toString());
      bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
    } finally {
      paramString = null;
      bufferedReader = null;
    } 
    if (bufferedReader != null)
      try {
        bufferedReader.close();
        return paramString;
      } catch (IOException iOException) {
        return paramString;
      }  
    return paramString;
  }
  
  public static boolean is360OS() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Build.MANUFACTURER);
    stringBuilder.append(Build.BRAND);
    String str = stringBuilder.toString();
    if (TextUtils.isEmpty(str))
      return false; 
    str = str.toLowerCase(Locale.getDefault());
    return (str.contains("360") || str.contains("qiku"));
  }
  
  public static boolean isAmigo() {
    return (!TextUtils.isEmpty(Build.DISPLAY) && Build.DISPLAY.toLowerCase(Locale.getDefault()).contains(AMIGO));
  }
  
  public static boolean isColorOS() {
    String str = Build.MANUFACTURER;
    return !TextUtils.isEmpty(str) ? str.toLowerCase(Locale.getDefault()).contains("oppo") : false;
  }
  
  public static boolean isColorOSMarshmallow() {
    String str = Build.MANUFACTURER;
    return (!TextUtils.isEmpty(str) && str.toLowerCase(Locale.getDefault()).contains("oppo") && Build.VERSION.SDK_INT == 23);
  }
  
  public static boolean isEUI() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Build.BRAND);
    stringBuilder.append(Build.MANUFACTURER);
    String str = stringBuilder.toString();
    return (!TextUtils.isEmpty(getSystemProperty("ro.letv.release.version")) || (!TextUtils.isEmpty(str) && str.toLowerCase(Locale.getDefault()).contains("letv")));
  }
  
  public static boolean isFunTouchOS() {
    String str = getSystemProperty("ro.vivo.os.build.display.id");
    return (!TextUtils.isEmpty(str) && str.toLowerCase(Locale.getDefault()).contains(FUNTOUCHOS));
  }
  
  public static boolean isSony() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Build.BRAND);
    stringBuilder.append(Build.MANUFACTURER);
    String str = stringBuilder.toString();
    return (!TextUtils.isEmpty(str) || str.toLowerCase(Locale.getDefault()).contains(SONY));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\RomUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */