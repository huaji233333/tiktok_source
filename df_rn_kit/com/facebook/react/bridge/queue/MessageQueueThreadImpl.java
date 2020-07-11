package com.facebook.react.bridge.queue;

import android.os.Looper;
import android.os.Process;
import com.facebook.common.e.a;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.futures.SimpleSettableFuture;
import com.lynx.base.JavaHandlerThread;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class MessageQueueThreadImpl implements MessageQueueThread {
  private final String mAssertionErrorMessage;
  
  private final MessageQueueThreadHandler mHandler;
  
  private volatile boolean mIsFinished;
  
  private final Looper mLooper;
  
  private final String mName;
  
  private MessageQueueThreadImpl(String paramString, Looper paramLooper, QueueThreadExceptionHandler paramQueueThreadExceptionHandler) {
    this.mName = paramString;
    this.mLooper = paramLooper;
    this.mHandler = new MessageQueueThreadHandler(paramLooper, paramQueueThreadExceptionHandler);
    StringBuilder stringBuilder = new StringBuilder("Expected to be called from the '");
    stringBuilder.append(getName());
    stringBuilder.append("' thread!");
    this.mAssertionErrorMessage = stringBuilder.toString();
  }
  
  public static MessageQueueThreadImpl create(MessageQueueThreadSpec paramMessageQueueThreadSpec, QueueThreadExceptionHandler paramQueueThreadExceptionHandler) {
    StringBuilder stringBuilder;
    int i = null.$SwitchMap$com$facebook$react$bridge$queue$MessageQueueThreadSpec$ThreadType[paramMessageQueueThreadSpec.getThreadType().ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i == 3)
          return startLayoutThread(paramMessageQueueThreadSpec.getName(), paramMessageQueueThreadSpec.getStackSize(), paramQueueThreadExceptionHandler); 
        stringBuilder = new StringBuilder("Unknown thread type: ");
        stringBuilder.append(paramMessageQueueThreadSpec.getThreadType());
        throw new RuntimeException(stringBuilder.toString());
      } 
      return startNewBackgroundThread(paramMessageQueueThreadSpec.getName(), paramMessageQueueThreadSpec.getStackSize(), (QueueThreadExceptionHandler)stringBuilder);
    } 
    return createForMainThread(paramMessageQueueThreadSpec.getName(), (QueueThreadExceptionHandler)stringBuilder);
  }
  
  private static MessageQueueThreadImpl createForMainThread(String paramString, QueueThreadExceptionHandler paramQueueThreadExceptionHandler) {
    MessageQueueThreadImpl messageQueueThreadImpl = new MessageQueueThreadImpl(paramString, Looper.getMainLooper(), paramQueueThreadExceptionHandler);
    if (UiThreadUtil.isOnUiThread()) {
      Process.setThreadPriority(-4);
      return messageQueueThreadImpl;
    } 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public final void run() {
            Process.setThreadPriority(-4);
          }
        });
    return messageQueueThreadImpl;
  }
  
  private static MessageQueueThreadImpl startLayoutThread(String paramString, long paramLong, QueueThreadExceptionHandler paramQueueThreadExceptionHandler) {
    StringBuilder stringBuilder = new StringBuilder("mqt_");
    stringBuilder.append(paramString);
    JavaHandlerThread javaHandlerThread = JavaHandlerThread.create(stringBuilder.toString());
    javaHandlerThread.maybeStart();
    return new MessageQueueThreadImpl(paramString, javaHandlerThread.getLooper(), paramQueueThreadExceptionHandler);
  }
  
  private static MessageQueueThreadImpl startNewBackgroundThread(String paramString, long paramLong, QueueThreadExceptionHandler paramQueueThreadExceptionHandler) {
    final SimpleSettableFuture looperFuture = new SimpleSettableFuture();
    Runnable runnable = new Runnable() {
        public final void run() {
          Process.setThreadPriority(-4);
          Looper.prepare();
          looperFuture.set(Looper.myLooper());
          Looper.loop();
        }
      };
    StringBuilder stringBuilder = new StringBuilder("mqt_");
    stringBuilder.append(paramString);
    (new Thread(null, runnable, stringBuilder.toString(), paramLong)).start();
    return new MessageQueueThreadImpl(paramString, (Looper)simpleSettableFuture.getOrThrow(), paramQueueThreadExceptionHandler);
  }
  
  public void assertIsOnThread() {
    SoftAssertions.assertCondition(isOnThread(), this.mAssertionErrorMessage);
  }
  
  public void assertIsOnThread(String paramString) {
    boolean bool = isOnThread();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mAssertionErrorMessage);
    stringBuilder.append(" ");
    stringBuilder.append(paramString);
    SoftAssertions.assertCondition(bool, stringBuilder.toString());
  }
  
  public <T> Future<T> callOnQueue(final Callable<T> callable) {
    final SimpleSettableFuture future = new SimpleSettableFuture();
    runOnQueue(new Runnable() {
          public void run() {
            try {
              future.set(callable.call());
              return;
            } catch (Exception exception) {
              future.setException(exception);
              return;
            } 
          }
        });
    return (Future<T>)simpleSettableFuture;
  }
  
  public Looper getLooper() {
    return this.mLooper;
  }
  
  public String getName() {
    return this.mName;
  }
  
  public boolean isOnThread() {
    return (this.mLooper.getThread() == Thread.currentThread());
  }
  
  public void quitSynchronous() {
    this.mIsFinished = true;
    this.mLooper.quit();
    if (this.mLooper.getThread() != Thread.currentThread())
      try {
        this.mLooper.getThread().join();
        return;
      } catch (InterruptedException interruptedException) {
        StringBuilder stringBuilder = new StringBuilder("Got interrupted waiting to join thread ");
        stringBuilder.append(this.mName);
        throw new RuntimeException(stringBuilder.toString());
      }  
  }
  
  public void runOnQueue(Runnable paramRunnable) {
    if (this.mIsFinished) {
      StringBuilder stringBuilder = new StringBuilder("Tried to enqueue runnable on already finished thread: '");
      stringBuilder.append(getName());
      stringBuilder.append("... dropping Runnable.");
      a.b("ReactNative", stringBuilder.toString());
    } 
    this.mHandler.post(paramRunnable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\queue\MessageQueueThreadImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */