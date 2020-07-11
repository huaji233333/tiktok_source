package com.facebook.react.common;

public class SystemClock {
  public static long currentTimeMillis() {
    return System.currentTimeMillis();
  }
  
  public static long nanoTime() {
    return System.nanoTime();
  }
  
  public static long uptimeMillis() {
    return android.os.SystemClock.uptimeMillis();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\SystemClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */