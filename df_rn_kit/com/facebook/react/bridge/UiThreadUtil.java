package com.facebook.react.bridge;

import android.os.Handler;
import android.os.Looper;

public class UiThreadUtil {
  private static Handler sMainHandler;
  
  public static void assertNotOnUiThread() {
    SoftAssertions.assertCondition(isOnUiThread() ^ true, "Expected not to run on UI thread!");
  }
  
  public static void assertOnUiThread() {
    SoftAssertions.assertCondition(isOnUiThread(), "Expected to run on UI thread!");
  }
  
  public static boolean isOnUiThread() {
    return (Looper.getMainLooper().getThread() == Thread.currentThread());
  }
  
  public static void runOnUiThread(Runnable paramRunnable) {
    // Byte code:
    //   0: ldc com/facebook/react/bridge/UiThreadUtil
    //   2: monitorenter
    //   3: getstatic com/facebook/react/bridge/UiThreadUtil.sMainHandler : Landroid/os/Handler;
    //   6: ifnonnull -> 22
    //   9: new android/os/Handler
    //   12: dup
    //   13: invokestatic getMainLooper : ()Landroid/os/Looper;
    //   16: invokespecial <init> : (Landroid/os/Looper;)V
    //   19: putstatic com/facebook/react/bridge/UiThreadUtil.sMainHandler : Landroid/os/Handler;
    //   22: ldc com/facebook/react/bridge/UiThreadUtil
    //   24: monitorexit
    //   25: getstatic com/facebook/react/bridge/UiThreadUtil.sMainHandler : Landroid/os/Handler;
    //   28: aload_0
    //   29: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   32: pop
    //   33: return
    //   34: astore_0
    //   35: ldc com/facebook/react/bridge/UiThreadUtil
    //   37: monitorexit
    //   38: aload_0
    //   39: athrow
    // Exception table:
    //   from	to	target	type
    //   3	22	34	finally
    //   22	25	34	finally
    //   35	38	34	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\UiThreadUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */