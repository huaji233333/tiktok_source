package com.facebook.react.packagerconnection;

import com.facebook.common.e.a;

public abstract class RequestOnlyHandler implements RequestHandler {
  private static final String TAG = JSPackagerClient.class.getSimpleName();
  
  public final void onNotification(Object paramObject) {
    a.c(TAG, "Notification is not supported");
  }
  
  public abstract void onRequest(Object paramObject, Responder paramResponder);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\packagerconnection\RequestOnlyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */