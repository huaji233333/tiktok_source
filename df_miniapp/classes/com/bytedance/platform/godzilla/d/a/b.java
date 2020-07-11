package com.bytedance.platform.godzilla.d.a;

import android.os.Handler;
import android.os.HandlerThread;
import com.bytedance.platform.godzilla.d.d;

public final class b {
  private static HandlerThread a;
  
  private static Handler b = new Handler(a.getLooper());
  
  private static boolean c;
  
  private static int d = 10000;
  
  private static int e = 10000;
  
  public static String a(StackTraceElement[] paramArrayOfStackTraceElement) {
    String str = "";
    if (paramArrayOfStackTraceElement == null)
      return ""; 
    int j = paramArrayOfStackTraceElement.length;
    for (int i = 0; i < j; i++) {
      StackTraceElement stackTraceElement = paramArrayOfStackTraceElement[i];
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(stackTraceElement.toString());
      stringBuilder.append("\n");
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  public static void a(a parama) {
    b.postDelayed(parama, d);
  }
  
  public static void a(c paramc) {
    b.postDelayed(paramc, e);
  }
  
  public static boolean a() {
    return c;
  }
  
  public static void b(a parama) {
    b.removeCallbacks(parama);
  }
  
  public static void b(c paramc) {
    b.removeCallbacks(paramc);
  }
  
  static {
    d.a a = new d.a("pool-monitor", 0, 0);
    a.start();
    a = (HandlerThread)a;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */