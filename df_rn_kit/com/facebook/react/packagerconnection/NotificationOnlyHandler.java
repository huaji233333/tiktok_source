package com.facebook.react.packagerconnection;

import com.facebook.common.e.a;

public abstract class NotificationOnlyHandler implements RequestHandler {
  private static final String TAG = JSPackagerClient.class.getSimpleName();
  
  public abstract void onNotification(Object paramObject);
  
  public final void onRequest(Object paramObject, Responder paramResponder) {
    paramResponder.error("Request is not supported");
    a.c(TAG, "Request is not supported");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\packagerconnection\NotificationOnlyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */