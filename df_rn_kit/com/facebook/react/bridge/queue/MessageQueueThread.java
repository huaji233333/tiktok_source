package com.facebook.react.bridge.queue;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface MessageQueueThread {
  void assertIsOnThread();
  
  void assertIsOnThread(String paramString);
  
  <T> Future<T> callOnQueue(Callable<T> paramCallable);
  
  boolean isOnThread();
  
  void quitSynchronous();
  
  void runOnQueue(Runnable paramRunnable);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\queue\MessageQueueThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */