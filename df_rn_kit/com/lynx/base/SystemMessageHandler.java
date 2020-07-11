package com.lynx.base;

import android.os.Handler;
import android.os.Message;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SystemMessageHandler extends Handler {
  private boolean mIsRunning;
  
  private Method mMessageMethodSetAsynchronous;
  
  private long mMessagePumpDelegateNative;
  
  private SystemMessageHandler(long paramLong) {
    this.mMessagePumpDelegateNative = paramLong;
    try {
      this.mMessageMethodSetAsynchronous = Class.forName("android.os.Message").getMethod("setAsynchronous", new Class[] { boolean.class });
    } catch (ClassNotFoundException|NoSuchMethodException|RuntimeException classNotFoundException) {}
    this.mIsRunning = true;
  }
  
  public static SystemMessageHandler create(long paramLong) {
    return new SystemMessageHandler(paramLong);
  }
  
  private native void nativeRunWork(long paramLong);
  
  private Message obtainAsyncMessage(int paramInt) {
    Message message = Message.obtain();
    message.what = paramInt;
    Method method = this.mMessageMethodSetAsynchronous;
    if (method != null)
      try {
        method.invoke(message, new Object[] { Boolean.valueOf(true) });
        return message;
      } catch (IllegalAccessException illegalAccessException) {
        this.mMessageMethodSetAsynchronous = null;
        return message;
      } catch (IllegalArgumentException illegalArgumentException) {
        this.mMessageMethodSetAsynchronous = null;
        return message;
      } catch (InvocationTargetException invocationTargetException) {
        this.mMessageMethodSetAsynchronous = null;
        return message;
      } catch (RuntimeException runtimeException) {
        this.mMessageMethodSetAsynchronous = null;
        return message;
      }  
    return message;
  }
  
  private void scheduleWork() {
    sendMessage(obtainAsyncMessage(1));
  }
  
  private void stop() {
    this.mIsRunning = false;
  }
  
  public void handleMessage(Message paramMessage) {
    if (this.mIsRunning) {
      if (paramMessage.what != 1) {
        super.handleMessage(paramMessage);
        return;
      } 
      nativeRunWork(this.mMessagePumpDelegateNative);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\lynx\base\SystemMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */