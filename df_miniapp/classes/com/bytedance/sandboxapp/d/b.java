package com.bytedance.sandboxapp.d;

import d.f.b.l;

public final class b {
  public static final b a = new b();
  
  public static final String a(Throwable paramThrowable, int paramInt1, int paramInt2) {
    l.b(paramThrowable, "throwable");
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    StringBuilder stringBuilder = new StringBuilder();
    paramInt2 = arrayOfStackTraceElement.length;
    paramInt1 = 6;
    if (6 > paramInt2)
      paramInt1 = arrayOfStackTraceElement.length; 
    if (1 < paramInt1) {
      stringBuilder.append(arrayOfStackTraceElement[1]);
      for (paramInt2 = 2; paramInt2 < paramInt1; paramInt2++) {
        stringBuilder.append(" ");
        stringBuilder.append(arrayOfStackTraceElement[paramInt2]);
      } 
    } 
    String str = stringBuilder.toString();
    l.a(str, "stackInfoBuilder.toString()");
    return str;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */