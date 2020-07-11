package com.bytedance.sandboxapp.b.a.b;

import android.util.Log;
import d.f.b.l;

public final class b implements a {
  public static a a;
  
  public static final b b = new b();
  
  public static String a(Object[] paramArrayOfObject) {
    l.b(paramArrayOfObject, "messages");
    StringBuilder stringBuilder = new StringBuilder();
    if (com.bytedance.sandboxapp.b.a.a.b.b.isDebugMode()) {
      byte b1;
      String str1;
      String str2;
      StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
      if (arrayOfStackTraceElement.length > 2) {
        StackTraceElement stackTraceElement1 = arrayOfStackTraceElement[2];
        l.a(stackTraceElement1, "sElements[2]");
        str1 = stackTraceElement1.getFileName();
        l.a(str1, "sElements[2].fileName");
        StackTraceElement stackTraceElement2 = arrayOfStackTraceElement[2];
        l.a(stackTraceElement2, "sElements[2]");
        str2 = stackTraceElement2.getMethodName();
        l.a(str2, "sElements[2].methodName");
        StackTraceElement stackTraceElement3 = arrayOfStackTraceElement[2];
        l.a(stackTraceElement3, "sElements[2]");
        b1 = stackTraceElement3.getLineNumber();
      } else {
        b1 = -1;
        str1 = "unknown file";
        str2 = "unknown";
      } 
      stringBuilder.append(str2);
      stringBuilder.append("(");
      stringBuilder.append(str1);
      stringBuilder.append(":");
      stringBuilder.append(b1);
      stringBuilder.append(") ");
    } 
    int i = paramArrayOfObject.length;
    boolean bool = false;
    if (i == 0) {
      i = 1;
    } else {
      i = 0;
    } 
    if ((i ^ 0x1) != 0) {
      int j = paramArrayOfObject.length;
      for (i = bool; i < j; i++) {
        Object object1 = paramArrayOfObject[i];
        stringBuilder.append(" ");
        if (object1 != null) {
          stringBuilder.append(object1);
        } else {
          stringBuilder.append("null");
        } 
      } 
      Object object = paramArrayOfObject[paramArrayOfObject.length - 1];
      if (object instanceof Throwable) {
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString((Throwable)object));
      } 
    } 
    String str = stringBuilder.toString();
    l.a(str, "buffer.toString()");
    return str;
  }
  
  public final void d(String paramString, Object... paramVarArgs) {
    l.b(paramVarArgs, "messages");
    a a1 = a;
    if (a1 != null)
      a1.d(paramString, new Object[] { a(paramVarArgs) }); 
  }
  
  public final void e(String paramString, Object... paramVarArgs) {
    l.b(paramVarArgs, "messages");
    a a1 = a;
    if (a1 != null)
      a1.e(paramString, new Object[] { a(paramVarArgs) }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\b\a\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */