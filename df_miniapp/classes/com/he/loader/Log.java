package com.he.loader;

public class Log {
  private static ILogger sLogger;
  
  private static boolean sLoggerDisable = true;
  
  private static boolean sShowMoreLogInfo;
  
  private static String createLog(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null)
      return "emptyLog"; 
    StringBuilder stringBuilder = new StringBuilder();
    if (sShowMoreLogInfo) {
      byte b;
      String str1;
      String str2;
      StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
      if (arrayOfStackTraceElement.length > 2) {
        str1 = arrayOfStackTraceElement[2].getFileName();
        str2 = arrayOfStackTraceElement[2].getMethodName();
        b = arrayOfStackTraceElement[2].getLineNumber();
      } else {
        str2 = "unknown";
        str1 = "unknown file";
        b = -1;
      } 
      stringBuilder.append(str2);
      stringBuilder.append("(");
      stringBuilder.append(str1);
      stringBuilder.append(":");
      stringBuilder.append(b);
      stringBuilder.append(") ");
    } 
    int j = paramArrayOfObject.length;
    for (int i = 0; i < j; i++) {
      Object object = paramArrayOfObject[i];
      stringBuilder.append(" ");
      if (object != null) {
        stringBuilder.append(object);
      } else {
        stringBuilder.append("null");
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static void d(String paramString, Object... paramVarArgs) {
    if (sLoggerDisable)
      return; 
    if (sShowMoreLogInfo) {
      paramString = formatTag(paramString);
      String str = createLog(paramVarArgs);
      ILogger iLogger = sLogger;
      if (iLogger != null)
        iLogger.logD(paramString, str); 
    } 
  }
  
  public static void e(String paramString, Object... paramVarArgs) {
    boolean bool;
    if (paramVarArgs != null && paramVarArgs.length > 0 && paramVarArgs[paramVarArgs.length - 1] instanceof Throwable) {
      bool = true;
    } else {
      bool = false;
    } 
    if (sLoggerDisable) {
      if (sShowMoreLogInfo)
        createLog(paramVarArgs); 
      return;
    } 
    paramString = formatTag(paramString);
    String str = createLog(paramVarArgs);
    if (bool) {
      eWithThrowable(paramString, str, (Throwable)paramVarArgs[paramVarArgs.length - 1]);
      return;
    } 
    ILogger iLogger = sLogger;
    if (iLogger != null)
      iLogger.logE(paramString, str); 
  }
  
  public static void eWithThrowable(String paramString1, String paramString2, Throwable paramThrowable) {
    if (sLoggerDisable)
      return; 
    paramString1 = formatTag(paramString1);
    ILogger iLogger = sLogger;
    if (iLogger != null)
      iLogger.logE(paramString1, paramString2, paramThrowable); 
  }
  
  public static void flush() {
    ILogger iLogger = sLogger;
    if (iLogger != null)
      iLogger.flush(); 
  }
  
  private static String formatTag(String paramString) {
    if (paramString.isEmpty())
      return "helium"; 
    String str = paramString;
    if (!paramString.startsWith("helium_")) {
      StringBuilder stringBuilder = new StringBuilder("helium_");
      stringBuilder.append(paramString);
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  public static void i(String paramString, Object... paramVarArgs) {
    if (sLoggerDisable)
      return; 
    paramString = formatTag(paramString);
    String str = createLog(paramVarArgs);
    ILogger iLogger = sLogger;
    if (iLogger != null)
      iLogger.logI(paramString, str); 
  }
  
  public static void registerLogger(ILogger paramILogger) {
    sLogger = paramILogger;
    updateLoggerEnableState();
  }
  
  private static void updateLoggerEnableState() {
    boolean bool;
    if (sLogger == null) {
      bool = true;
    } else {
      bool = false;
    } 
    sLoggerDisable = bool;
  }
  
  public static void w(String paramString, Object... paramVarArgs) {
    if (sLoggerDisable)
      return; 
    paramString = formatTag(paramString);
    String str = createLog(paramVarArgs);
    ILogger iLogger = sLogger;
    if (iLogger != null)
      iLogger.logW(paramString, str); 
  }
  
  public static interface ILogger {
    void flush();
    
    void logD(String param1String1, String param1String2);
    
    void logE(String param1String1, String param1String2);
    
    void logE(String param1String1, String param1String2, Throwable param1Throwable);
    
    void logI(String param1String1, String param1String2);
    
    void logW(String param1String1, String param1String2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\loader\Log.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */