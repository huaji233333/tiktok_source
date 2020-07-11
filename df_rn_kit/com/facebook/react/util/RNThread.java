package com.facebook.react.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Pair;
import java.util.LinkedList;
import java.util.List;

public class RNThread extends HandlerThread {
  public static ThreadLocal<Handler> handlerCache = new ThreadLocal<Handler>();
  
  private Handler.Callback mCallback;
  
  private Handler mHandler;
  
  private List<Pair<Message, Long>> waitingJobs = new LinkedList<Pair<Message, Long>>();
  
  public RNThread(String paramString) {
    super(paramString);
    start();
  }
  
  public RNThread(String paramString, int paramInt) {
    super(paramString, paramInt);
    start();
  }
  
  public RNThread(String paramString, int paramInt, Handler.Callback paramCallback) {
    super(paramString, paramInt);
    this.mCallback = secure(paramCallback);
    start();
  }
  
  public RNThread(String paramString, Handler.Callback paramCallback) {
    super(paramString);
    this.mCallback = secure(paramCallback);
    start();
  }
  
  public static Handler.Callback secure(Handler.Callback paramCallback) {
    return (paramCallback != null) ? ((paramCallback instanceof SafeCallback) ? paramCallback : new SafeCallback(paramCallback)) : paramCallback;
  }
  
  public static Runnable secure(Runnable paramRunnable) {
    return (paramRunnable != null) ? ((paramRunnable instanceof SafeRunnable) ? paramRunnable : new SafeRunnable(paramRunnable)) : paramRunnable;
  }
  
  public static Runnable secure(Runnable paramRunnable, long paramLong) {
    return (paramRunnable != null) ? ((paramRunnable instanceof SafeRunnable) ? paramRunnable : new SafeRunnable(paramRunnable, paramLong)) : paramRunnable;
  }
  
  public Handler getHandler() {
    return this.mHandler;
  }
  
  public boolean isRNThreadAlive() {
    return (this.mHandler != null && getLooper() != null && isAlive());
  }
  
  protected void onLooperPrepared() {
    super.onLooperPrepared();
    this.mHandler = new Handler(getLooper(), this.mCallback);
    handlerCache.set(this.mHandler);
    for (int i = 0; i < this.waitingJobs.size(); i++) {
      Pair pair = this.waitingJobs.get(i);
      this.mHandler.sendMessageDelayed((Message)pair.first, ((Long)pair.second).longValue());
    } 
  }
  
  public void post(Runnable paramRunnable) {
    Message message = Message.obtain(this.mHandler, secure(paramRunnable));
    Handler handler = this.mHandler;
    if (handler != null) {
      handler.sendMessage(message);
      return;
    } 
    this.waitingJobs.add(Pair.create(message, Long.valueOf(0L)));
  }
  
  public void post(Runnable paramRunnable, Object paramObject) {
    Message message = Message.obtain(this.mHandler, secure(paramRunnable));
    message.obj = paramObject;
    if (this.mHandler != null) {
      message.sendToTarget();
      return;
    } 
    this.waitingJobs.add(Pair.create(message, Long.valueOf(0L)));
  }
  
  public void postDelayed(Runnable paramRunnable, long paramLong) {
    Message message = Message.obtain(this.mHandler, secure(paramRunnable));
    Handler handler = this.mHandler;
    if (handler == null) {
      this.waitingJobs.add(Pair.create(message, Long.valueOf(paramLong)));
      return;
    } 
    handler.sendMessageDelayed(message, paramLong);
  }
  
  public boolean quit() {
    Handler handler = this.mHandler;
    if (handler != null)
      handler.removeCallbacksAndMessages(null); 
    return super.quit();
  }
  
  public void removeMessage(int paramInt, Object paramObject) {
    Handler handler = this.mHandler;
    if (handler != null)
      handler.removeMessages(paramInt, paramObject); 
  }
  
  public void run() {
    super.run();
    handlerCache.remove();
  }
  
  public void sendMessageDelayed(Message paramMessage, long paramLong) {
    if (paramMessage == null)
      return; 
    if (!isRNThreadAlive())
      return; 
    Handler handler = this.mHandler;
    if (handler == null) {
      this.waitingJobs.add(Pair.create(paramMessage, Long.valueOf(paramLong)));
      return;
    } 
    handler.sendMessageDelayed(paramMessage, paramLong);
  }
  
  static class SafeCallback implements Handler.Callback {
    final Handler.Callback mCallback;
    
    SafeCallback(Handler.Callback param1Callback) {
      this.mCallback = param1Callback;
    }
    
    public boolean handleMessage(Message param1Message) {
      boolean bool = false;
      try {
        return bool;
      } finally {
        param1Message = null;
      } 
    }
  }
  
  static class SafeRunnable implements Runnable {
    final long mDelayTime;
    
    final Runnable mTask;
    
    SafeRunnable(Runnable param1Runnable) {
      this.mTask = param1Runnable;
    }
    
    SafeRunnable(Runnable param1Runnable, long param1Long) {
      this.mTask = param1Runnable;
      this.mDelayTime = param1Long;
    }
    
    public void run() {
      try {
        return;
      } finally {
        Exception exception = null;
        Handler handler = RNThread.handlerCache.get();
        if (handler != null) {
          if (exception instanceof RuntimeException && (exception.toString().contains("java.lang.RuntimeException: Unable to load script") || exception.toString().contains("java.lang.RuntimeException: Could not open file"))) {
            Message message1 = handler.obtainMessage(10002);
            message1.obj = exception;
            handler.sendMessage(message1);
            return;
          } 
          Message message = handler.obtainMessage(10001);
          message.obj = exception;
          handler.sendMessage(message);
        } 
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\util\RNThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */