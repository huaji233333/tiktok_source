package com.tt.miniapp.base.log;

import android.os.Process;

public class LogX {
  public static boolean LOG_ENABLE;
  
  private static int mProcessID;
  
  private static String createLog(Object[] paramArrayOfObject) {
    String str1;
    StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
    int i = arrayOfStackTraceElement.length;
    String str2 = "";
    if (i > 2) {
      str1 = arrayOfStackTraceElement[2].getMethodName();
      i = arrayOfStackTraceElement[2].getLineNumber();
      str2 = arrayOfStackTraceElement[2].getFileName().replace(".java", "");
    } else {
      i = -1;
      str1 = "unknown";
    } 
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(str2);
    stringBuffer.append(" (");
    stringBuffer.append(getProcessID());
    stringBuffer.append(") [");
    stringBuffer.append(str1);
    stringBuffer.append(':');
    stringBuffer.append(i);
    stringBuffer.append(']');
    int j = paramArrayOfObject.length;
    for (i = 0; i < j; i++) {
      Object object = paramArrayOfObject[i];
      stringBuffer.append(" ");
      if (object != null) {
        stringBuffer.append(object);
      } else {
        stringBuffer.append("null");
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static void d(Object... paramVarArgs) {
    if (LOG_ENABLE)
      createLog(paramVarArgs); 
  }
  
  public static void e(Object... paramVarArgs) {
    if (LOG_ENABLE)
      createLog(paramVarArgs); 
  }
  
  private static int getProcessID() {
    if (mProcessID <= 0)
      mProcessID = Process.myPid(); 
    return mProcessID;
  }
  
  public static void i(Object... paramVarArgs) {
    if (LOG_ENABLE)
      createLog(paramVarArgs); 
  }
  
  public static void printStacktrace(Throwable paramThrowable) {}
  
  public static void v(Object... paramVarArgs) {
    if (LOG_ENABLE)
      createLog(paramVarArgs); 
  }
  
  public static void w(Object... paramVarArgs) {
    if (LOG_ENABLE)
      createLog(paramVarArgs); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\log\LogX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */