package com.tt.miniapp.autotest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Printer;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.manager.MainMessageLoggerManager;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import d.a.m;
import d.f.b.g;
import d.f.b.l;
import d.m.p;
import java.util.LinkedList;
import java.util.List;

public final class AutoTestLooperMonitor implements Printer {
  public static final Companion Companion = new Companion(null);
  
  private String distpachText;
  
  private final LinkedList<AutoTestLoopEvent> mInfoList = new LinkedList<AutoTestLoopEvent>();
  
  private Handler mIoHandler;
  
  private final Runnable mLogRunnable = new AutoTestLooperMonitor$mLogRunnable$1();
  
  private HandlerThread mLogThread;
  
  public String stackTraceContent;
  
  private long startTime;
  
  private final void endLoopTask() {
    long l = SystemClock.elapsedRealtime();
    Handler handler = this.mIoHandler;
    if (handler != null)
      handler.removeCallbacks(this.mLogRunnable); 
    this.mInfoList.add(new AutoTestLoopEvent(this.distpachText, this.startTime, l, this.stackTraceContent));
  }
  
  private final void startLoopTask(String paramString) {
    this.startTime = SystemClock.elapsedRealtime();
    this.distpachText = paramString;
    this.stackTraceContent = "";
    Handler handler = this.mIoHandler;
    if (handler != null)
      handler.postDelayed(this.mLogRunnable, 100L); 
  }
  
  public final void clear() {
    this.mInfoList.clear();
  }
  
  public final List<AutoTestLoopEvent> dump() {
    return m.f(this.mInfoList);
  }
  
  public final void println(String paramString) {
    if (paramString == null)
      return; 
    if (p.b(paramString, ">>>>> Dispatching", false, 2, null)) {
      startLoopTask(paramString);
      return;
    } 
    if (p.b(paramString, "<<<<< Finished", false, 2, null))
      endLoopTask(); 
  }
  
  public final void start() {
    this.mLogThread = HandlerThreadUtil.getNewHandlerThread("AutoTestLooperDetector");
    HandlerThread handlerThread = this.mLogThread;
    if (handlerThread != null)
      handlerThread.start(); 
    handlerThread = this.mLogThread;
    if (handlerThread != null) {
      Looper looper = handlerThread.getLooper();
    } else {
      handlerThread = null;
    } 
    this.mIoHandler = new Handler((Looper)handlerThread);
    this.mInfoList.clear();
    ((MainMessageLoggerManager)AppbrandApplicationImpl.getInst().getService(MainMessageLoggerManager.class)).register(this);
    AppBrandLogger.d("AutoTestLooperDetector", new Object[] { "start" });
  }
  
  public final void stop() {
    if (this.mLogThread != null) {
      ((MainMessageLoggerManager)AppbrandApplicationImpl.getInst().getService(MainMessageLoggerManager.class)).unregister(this);
      Handler handler = this.mIoHandler;
      if (handler != null)
        handler.removeCallbacks(this.mLogRunnable); 
      HandlerThread handlerThread = this.mLogThread;
      if (handlerThread != null)
        handlerThread.quitSafely(); 
      this.mLogThread = null;
      AppBrandLogger.d("AutoTestLooperDetector", new Object[] { "end" });
    } 
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class AutoTestLooperMonitor$mLogRunnable$1 implements Runnable {
    public final void run() {
      StringBuilder stringBuilder = new StringBuilder();
      Looper looper = Looper.getMainLooper();
      l.a(looper, "Looper.getMainLooper()");
      Thread thread = looper.getThread();
      l.a(thread, "Looper.getMainLooper().thread");
      for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(stackTraceElement.toString());
        stringBuilder1.append("\n");
        stringBuilder.append(stringBuilder1.toString());
      } 
      AutoTestLooperMonitor.this.stackTraceContent = stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\autotest\AutoTestLooperMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */