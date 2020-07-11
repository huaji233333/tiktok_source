package com.facebook.react.bridge.queue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MessageQueueThreadHandler extends Handler {
  private final QueueThreadExceptionHandler mExceptionHandler;
  
  public MessageQueueThreadHandler(Looper paramLooper, QueueThreadExceptionHandler paramQueueThreadExceptionHandler) {
    super(paramLooper);
    this.mExceptionHandler = paramQueueThreadExceptionHandler;
  }
  
  public void dispatchMessage(Message paramMessage) {
    try {
      super.dispatchMessage(paramMessage);
      return;
    } catch (Exception exception) {
      this.mExceptionHandler.handleException(exception);
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\queue\MessageQueueThreadHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */