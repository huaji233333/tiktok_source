package com.tt.miniapp.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

public class WeakHandler extends Handler {
  WeakReference<IHandler> mRef;
  
  public WeakHandler(Looper paramLooper, IHandler paramIHandler) {
    super(paramLooper);
    this.mRef = new WeakReference<IHandler>(paramIHandler);
  }
  
  public WeakHandler(IHandler paramIHandler) {
    this.mRef = new WeakReference<IHandler>(paramIHandler);
  }
  
  public void handleMessage(Message paramMessage) {
    IHandler iHandler = this.mRef.get();
    if (iHandler != null && paramMessage != null)
      iHandler.handleMsg(paramMessage); 
  }
  
  public static interface IHandler {
    void handleMsg(Message param1Message);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\WeakHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */