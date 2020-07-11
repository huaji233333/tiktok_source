package com.tt.miniapp.util;

public class StackUtil {
  public static String createLog(Object[] paramArrayOfObject) {
    String str1;
    String str2;
    if (paramArrayOfObject == null)
      return "emptyLog"; 
    StringBuilder stringBuilder = new StringBuilder();
    StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
    if (arrayOfStackTraceElement.length > 2) {
      str1 = arrayOfStackTraceElement[2].getFileName();
      str2 = arrayOfStackTraceElement[2].getMethodName();
      i = arrayOfStackTraceElement[2].getLineNumber();
    } else {
      str2 = "unknown";
      str1 = "unknown file";
      i = -1;
    } 
    stringBuilder.append(str2);
    stringBuilder.append("(");
    stringBuilder.append(str1);
    stringBuilder.append(":");
    stringBuilder.append(i);
    stringBuilder.append(") ");
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
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\StackUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */