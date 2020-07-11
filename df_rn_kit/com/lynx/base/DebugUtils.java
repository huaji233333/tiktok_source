package com.lynx.base;

public class DebugUtils {
  public static void checkMemoryLeak() {
    (new Thread(new Runnable() {
          public final void run() {
            try {
              Thread.sleep(5000L);
            } catch (InterruptedException interruptedException) {
              interruptedException.printStackTrace();
            } 
            DebugUtils.nativeCheckMemoryLeak();
          }
        })).start();
  }
  
  public static native void nativeCheckMemoryLeak();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\lynx\base\DebugUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */