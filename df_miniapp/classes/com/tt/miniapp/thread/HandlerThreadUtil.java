package com.tt.miniapp.thread;

import android.os.HandlerThread;

public class HandlerThreadUtil {
  public static HandlerThread getBackgroundHandlerThread() {
    return (PlatformHandlerThread.getBackgroundHandler().getLooper() == null) ? PlatformHandlerThread.getNewHandlerThread("background-handler") : PlatformHandlerThread.getBackgroundHandlerThread();
  }
  
  public static HandlerThread getDefaultHandlerThread() {
    return (PlatformHandlerThread.getDefaultHandlerThread().getLooper() == null) ? PlatformHandlerThread.getNewHandlerThread("platform-handler") : PlatformHandlerThread.getDefaultHandlerThread();
  }
  
  public static HandlerThread getNewHandlerThread(String paramString) {
    return PlatformHandlerThread.getNewHandlerThread(paramString);
  }
  
  public static HandlerThread getNewHandlerThread(String paramString, int paramInt) {
    return PlatformHandlerThread.getNewHandlerThread(paramString, paramInt, null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\HandlerThreadUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */