package com.facebook.react.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RNLogUtils {
  private static List<LogWatcher> logWatcherList;
  
  private static LogLevel uploadLevel = LogLevel.ANDROID_LOG_ERROR;
  
  static {
    logWatcherList = new ArrayList<LogWatcher>();
  }
  
  public static void LogPrint(int paramInt, String paramString) {
    if (paramInt < uploadLevel.ordinal())
      return; 
    List<LogWatcher> list = logWatcherList;
    if (list != null && list.size() > 0) {
      Iterator<LogWatcher> iterator = logWatcherList.iterator();
      while (iterator.hasNext())
        ((LogWatcher)iterator.next()).onLog("RNLogUtils", paramString); 
    } 
  }
  
  public static void LogPrintWithTag(int paramInt, String paramString1, String paramString2) {
    if (paramInt < uploadLevel.ordinal())
      return; 
    List<LogWatcher> list = logWatcherList;
    if (list != null && list.size() > 0) {
      Iterator<LogWatcher> iterator = logWatcherList.iterator();
      while (iterator.hasNext())
        ((LogWatcher)iterator.next()).onLog(paramString1, paramString2); 
    } 
  }
  
  public static void d(String paramString) {
    d("RNLogUtils", paramString);
  }
  
  public static void d(String paramString1, String paramString2) {
    LogPrintWithTag(LogLevel.ANDROID_LOG_DEBUG.ordinal(), paramString1, paramString2);
  }
  
  public static void d(String paramString, byte[] paramArrayOfbyte) {
    d(paramString, new String(paramArrayOfbyte));
  }
  
  public static void e(String paramString) {
    e("RNLogUtils", paramString);
  }
  
  public static void e(String paramString1, String paramString2) {
    LogPrintWithTag(LogLevel.ANDROID_LOG_ERROR.ordinal(), paramString1, paramString2);
  }
  
  public static void i(String paramString) {
    i("RNLogUtils", paramString);
  }
  
  public static void i(String paramString1, String paramString2) {
    LogPrintWithTag(LogLevel.ANDROID_LOG_INFO.ordinal(), paramString1, paramString2);
  }
  
  public static void setLogWatcher(LogWatcher paramLogWatcher) {
    List<LogWatcher> list = logWatcherList;
    if (list != null && !list.contains(paramLogWatcher))
      logWatcherList.add(paramLogWatcher); 
  }
  
  public static void v(String paramString) {
    v("RNLogUtils", paramString);
  }
  
  public static void v(String paramString1, String paramString2) {
    LogPrintWithTag(LogLevel.ANDROID_LOG_VERBOSE.ordinal(), paramString1, paramString2);
  }
  
  public static void w(String paramString) {
    w("RNLogUtils", paramString);
  }
  
  public static void w(String paramString1, String paramString2) {
    LogPrintWithTag(LogLevel.ANDROID_LOG_WARN.ordinal(), paramString1, paramString2);
  }
  
  enum LogLevel {
    ANDROID_LOG_DEBUG, ANDROID_LOG_DEFAULT, ANDROID_LOG_ERROR, ANDROID_LOG_FATAL, ANDROID_LOG_INFO, ANDROID_LOG_SILENT, ANDROID_LOG_UNKNOWN, ANDROID_LOG_VERBOSE, ANDROID_LOG_WARN;
    
    static {
      ANDROID_LOG_DEBUG = new LogLevel("ANDROID_LOG_DEBUG", 3);
      ANDROID_LOG_INFO = new LogLevel("ANDROID_LOG_INFO", 4);
      ANDROID_LOG_WARN = new LogLevel("ANDROID_LOG_WARN", 5);
      ANDROID_LOG_ERROR = new LogLevel("ANDROID_LOG_ERROR", 6);
      ANDROID_LOG_FATAL = new LogLevel("ANDROID_LOG_FATAL", 7);
      ANDROID_LOG_SILENT = new LogLevel("ANDROID_LOG_SILENT", 8);
      $VALUES = new LogLevel[] { ANDROID_LOG_UNKNOWN, ANDROID_LOG_DEFAULT, ANDROID_LOG_VERBOSE, ANDROID_LOG_DEBUG, ANDROID_LOG_INFO, ANDROID_LOG_WARN, ANDROID_LOG_ERROR, ANDROID_LOG_FATAL, ANDROID_LOG_SILENT };
    }
  }
  
  public static interface LogWatcher {
    void onLog(String param1String1, String param1String2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\util\RNLogUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */