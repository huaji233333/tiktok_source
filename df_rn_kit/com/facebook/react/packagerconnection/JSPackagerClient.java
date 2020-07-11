package com.facebook.react.packagerconnection;

import android.net.Uri;
import com.facebook.common.e.a;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import g.i;
import java.util.Map;
import org.json.JSONObject;

public final class JSPackagerClient implements ReconnectingWebSocket.MessageCallback {
  public static final String TAG = JSPackagerClient.class.getSimpleName();
  
  private Map<String, RequestHandler> mRequestHandlers;
  
  public ReconnectingWebSocket mWebSocket;
  
  public JSPackagerClient(String paramString, PackagerConnectionSettings paramPackagerConnectionSettings, Map<String, RequestHandler> paramMap) {
    this(paramString, paramPackagerConnectionSettings, paramMap, null);
  }
  
  public JSPackagerClient(String paramString, PackagerConnectionSettings paramPackagerConnectionSettings, Map<String, RequestHandler> paramMap, ReconnectingWebSocket.ConnectionCallback paramConnectionCallback) {
    Uri.Builder builder = new Uri.Builder();
    builder.scheme("ws").encodedAuthority(paramPackagerConnectionSettings.getDebugServerHost()).appendPath("message").appendQueryParameter("device", AndroidInfoHelpers.getFriendlyDeviceName()).appendQueryParameter("app", paramPackagerConnectionSettings.getPackageName()).appendQueryParameter("clientid", paramString);
    this.mWebSocket = new ReconnectingWebSocket(builder.build().toString(), this, paramConnectionCallback);
    this.mRequestHandlers = paramMap;
  }
  
  private void abortOnMessage(Object paramObject, String paramString) {
    if (paramObject != null)
      (new ResponderImpl(paramObject)).error(paramString); 
    paramObject = TAG;
    StringBuilder stringBuilder = new StringBuilder("Handling the message failed with reason: ");
    stringBuilder.append(paramString);
    a.c((String)paramObject, stringBuilder.toString());
  }
  
  public final void close() {
    this.mWebSocket.closeQuietly();
  }
  
  public final void init() {
    this.mWebSocket.connect();
  }
  
  public final void onMessage(i parami) {
    a.b(TAG, "Websocket received message with payload of unexpected type binary");
  }
  
  public final void onMessage(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      int i = jSONObject.optInt("version");
      paramString = jSONObject.optString("method");
      Object object1 = jSONObject.opt("id");
      Object object2 = jSONObject.opt("params");
      if (i != 2) {
        paramString = TAG;
        object1 = new StringBuilder("Message with incompatible or missing version of protocol received: ");
        object1.append(i);
        a.c(paramString, object1.toString());
        return;
      } 
      if (paramString == null) {
        abortOnMessage(object1, "No method provided");
        return;
      } 
      RequestHandler requestHandler = this.mRequestHandlers.get(paramString);
      if (requestHandler == null) {
        object2 = new StringBuilder("No request handler for method: ");
        object2.append(paramString);
        abortOnMessage(object1, object2.toString());
        return;
      } 
      if (object1 == null) {
        requestHandler.onNotification(object2);
        return;
      } 
      requestHandler.onRequest(object2, new ResponderImpl(object1));
      return;
    } catch (Exception exception) {
      a.c(TAG, "Handling the message failed", exception);
      return;
    } 
  }
  
  class ResponderImpl implements Responder {
    private Object mId;
    
    public ResponderImpl(Object param1Object) {
      this.mId = param1Object;
    }
    
    public void error(Object param1Object) {
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("version", 2);
        jSONObject.put("id", this.mId);
        jSONObject.put("error", param1Object);
        JSPackagerClient.this.mWebSocket.sendMessage(jSONObject.toString());
        return;
      } catch (Exception exception) {
        a.c(JSPackagerClient.TAG, "Responding with error failed", exception);
        return;
      } 
    }
    
    public void respond(Object param1Object) {
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("version", 2);
        jSONObject.put("id", this.mId);
        jSONObject.put("result", param1Object);
        JSPackagerClient.this.mWebSocket.sendMessage(jSONObject.toString());
        return;
      } catch (Exception exception) {
        a.c(JSPackagerClient.TAG, "Responding failed", exception);
        return;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\packagerconnection\JSPackagerClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */