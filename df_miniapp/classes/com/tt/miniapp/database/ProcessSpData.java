package com.tt.miniapp.database;

import com.tt.miniapp.process.bridge.InnerHostProcessBridge;

public class ProcessSpData {
  public static boolean getBoolean(String paramString1, String paramString2, boolean paramBoolean) {
    paramString1 = getSpData(paramString1, paramString2, String.valueOf(paramBoolean));
    try {
      return Boolean.parseBoolean(paramString1);
    } catch (Exception exception) {
      return paramBoolean;
    } 
  }
  
  public static double getDouble(String paramString1, String paramString2, double paramDouble) {
    paramString1 = getSpData(paramString1, paramString2, String.valueOf(paramDouble));
    try {
      return Double.parseDouble(paramString1);
    } catch (Exception exception) {
      return paramDouble;
    } 
  }
  
  public static float getFloat(String paramString1, String paramString2, float paramFloat) {
    paramString1 = getSpData(paramString1, paramString2, String.valueOf(paramFloat));
    try {
      return Float.parseFloat(paramString1);
    } catch (Exception exception) {
      return paramFloat;
    } 
  }
  
  public static int getInt(String paramString1, String paramString2, int paramInt) {
    paramString1 = getSpData(paramString1, paramString2, String.valueOf(paramInt));
    try {
      return Integer.parseInt(paramString1);
    } catch (Exception exception) {
      return paramInt;
    } 
  }
  
  public static long getLong(String paramString1, String paramString2, long paramLong) {
    paramString1 = getSpData(paramString1, paramString2, String.valueOf(paramLong));
    try {
      return Long.parseLong(paramString1);
    } catch (Exception exception) {
      return paramLong;
    } 
  }
  
  public static String getSpData(String paramString1, String paramString2, String paramString3) {
    return InnerHostProcessBridge.getSpData(paramString1, paramString2, paramString3);
  }
  
  public static String getString(String paramString1, String paramString2, String paramString3) {
    return getSpData(paramString1, paramString2, paramString3);
  }
  
  public static void removeSpData(String paramString) {
    InnerHostProcessBridge.removeSpData("MiniAppSpData", paramString);
  }
  
  public static void removeSpData(String paramString1, String paramString2) {
    InnerHostProcessBridge.removeSpData(paramString1, paramString2);
  }
  
  public static void saveBoolean(String paramString1, String paramString2, boolean paramBoolean) {
    saveSpData(paramString1, paramString2, String.valueOf(paramBoolean));
  }
  
  public static void saveDouble(String paramString1, String paramString2, double paramDouble) {
    saveSpData(paramString1, paramString2, String.valueOf(paramDouble));
  }
  
  public static void saveFloat(String paramString1, String paramString2, float paramFloat) {
    saveSpData(paramString1, paramString2, String.valueOf(paramFloat));
  }
  
  public static void saveInt(String paramString1, String paramString2, int paramInt) {
    saveSpData(paramString1, paramString2, String.valueOf(paramInt));
  }
  
  public static void saveLong(String paramString1, String paramString2, long paramLong) {
    saveSpData(paramString1, paramString2, String.valueOf(paramLong));
  }
  
  public static void saveSpData(String paramString1, String paramString2, String paramString3) {
    InnerHostProcessBridge.saveSpData(paramString1, paramString2, paramString3);
  }
  
  public static void saveString(String paramString1, String paramString2, String paramString3) {
    saveSpData(paramString1, paramString2, paramString3);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\database\ProcessSpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */