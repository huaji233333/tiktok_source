package com.facebook.react.devsupport;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.common.JavascriptException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.y;

public class JSDebuggerWebSocketClient extends aj {
  private final ConcurrentHashMap<Integer, JSDebuggerCallback> mCallbacks = new ConcurrentHashMap<Integer, JSDebuggerCallback>();
  
  private JSDebuggerCallback mConnectCallback;
  
  private y mHttpClient;
  
  private final AtomicInteger mRequestID = new AtomicInteger();
  
  private ai mWebSocket;
  
  private void abort(String paramString, Throwable paramThrowable) {
    StringBuilder stringBuilder = new StringBuilder("Error occurred, shutting down websocket connection: ");
    stringBuilder.append(paramString);
    a.c("JSDebuggerWebSocketClient", stringBuilder.toString(), paramThrowable);
    closeQuietly();
    JSDebuggerCallback jSDebuggerCallback = this.mConnectCallback;
    if (jSDebuggerCallback != null) {
      jSDebuggerCallback.onFailure(paramThrowable);
      this.mConnectCallback = null;
    } 
    Iterator<JSDebuggerCallback> iterator = this.mCallbacks.values().iterator();
    while (iterator.hasNext())
      ((JSDebuggerCallback)iterator.next()).onFailure(paramThrowable); 
    this.mCallbacks.clear();
  }
  
  private void sendMessage(int paramInt, String paramString) {
    ai ai1 = this.mWebSocket;
    if (ai1 == null) {
      triggerRequestFailure(paramInt, new IllegalStateException("WebSocket connection no longer valid"));
      return;
    } 
    try {
      ai1.b(paramString);
      return;
    } catch (Exception exception) {
      triggerRequestFailure(paramInt, exception);
      return;
    } 
  }
  
  private void triggerRequestFailure(int paramInt, Throwable paramThrowable) {
    JSDebuggerCallback jSDebuggerCallback = this.mCallbacks.get(Integer.valueOf(paramInt));
    if (jSDebuggerCallback != null) {
      this.mCallbacks.remove(Integer.valueOf(paramInt));
      jSDebuggerCallback.onFailure(paramThrowable);
    } 
  }
  
  private void triggerRequestSuccess(int paramInt, String paramString) {
    JSDebuggerCallback jSDebuggerCallback = this.mCallbacks.get(Integer.valueOf(paramInt));
    if (jSDebuggerCallback != null) {
      this.mCallbacks.remove(Integer.valueOf(paramInt));
      jSDebuggerCallback.onSuccess(paramString);
    } 
  }
  
  public void closeQuietly() {
    ai ai1 = this.mWebSocket;
    if (ai1 != null) {
      try {
        ai1.b(1000, "End of session");
      } catch (Exception exception) {}
      this.mWebSocket = null;
    } 
  }
  
  public void connect(String paramString, JSDebuggerCallback paramJSDebuggerCallback) {
    if (this.mHttpClient == null) {
      this.mConnectCallback = paramJSDebuggerCallback;
      this.mHttpClient = (new y.a()).a(10L, TimeUnit.SECONDS).c(10L, TimeUnit.SECONDS).b(0L, TimeUnit.MINUTES).a();
      ac ac = (new ac.a()).a(paramString).c();
      this.mHttpClient.a(ac, this);
      return;
    } 
    throw new IllegalStateException("JSDebuggerWebSocketClient is already initialized.");
  }
  
  public void executeJSCall(String paramString1, String paramString2, JSDebuggerCallback paramJSDebuggerCallback) {
    int i = this.mRequestID.getAndIncrement();
    this.mCallbacks.put(Integer.valueOf(i), paramJSDebuggerCallback);
    try {
      StringWriter stringWriter = new StringWriter();
      JsonWriter jsonWriter = new JsonWriter(stringWriter);
      jsonWriter.beginObject().name("id").value(i).name("method").value(paramString1);
      stringWriter.append(",\"arguments\":").append(paramString2);
      jsonWriter.endObject().close();
      sendMessage(i, stringWriter.toString());
      return;
    } catch (IOException iOException) {
      triggerRequestFailure(i, iOException);
      return;
    } 
  }
  
  public void loadApplicationScript(String paramString, HashMap<String, String> paramHashMap, JSDebuggerCallback paramJSDebuggerCallback) {
    int i = this.mRequestID.getAndIncrement();
    this.mCallbacks.put(Integer.valueOf(i), paramJSDebuggerCallback);
    try {
      StringWriter stringWriter = new StringWriter();
      JsonWriter jsonWriter = (new JsonWriter(stringWriter)).beginObject().name("id").value(i).name("method").value("executeApplicationScript").name("url").value(paramString).name("inject").beginObject();
      for (String str : paramHashMap.keySet())
        jsonWriter.name(str).value(paramHashMap.get(str)); 
      jsonWriter.endObject().endObject().close();
      sendMessage(i, stringWriter.toString());
      return;
    } catch (IOException iOException) {
      triggerRequestFailure(i, iOException);
      return;
    } 
  }
  
  public void onClosed(ai paramai, int paramInt, String paramString) {
    this.mWebSocket = null;
  }
  
  public void onFailure(ai paramai, Throwable paramThrowable, ae paramae) {
    abort("Websocket exception", paramThrowable);
  }
  
  public void onMessage(ai paramai, String paramString) {
    Integer integer;
    ai ai2 = null;
    paramai = null;
    ai ai1 = ai2;
    try {
      JsonReader jsonReader = new JsonReader(new StringReader(paramString));
      ai1 = ai2;
      jsonReader.beginObject();
      paramString = null;
      while (true) {
        Integer integer1;
        ai1 = paramai;
        if (jsonReader.hasNext()) {
          ai1 = paramai;
          String str = jsonReader.nextName();
          ai1 = paramai;
          if (JsonToken.NULL == jsonReader.peek()) {
            ai1 = paramai;
            jsonReader.skipValue();
            continue;
          } 
          ai1 = paramai;
          if ("replyID".equals(str)) {
            ai1 = paramai;
            integer1 = Integer.valueOf(jsonReader.nextInt());
            continue;
          } 
          integer = integer1;
          if ("result".equals(str)) {
            integer = integer1;
            paramString = jsonReader.nextString();
            continue;
          } 
          integer = integer1;
          if ("error".equals(str)) {
            integer = integer1;
            str = jsonReader.nextString();
            integer = integer1;
            abort(str, (Throwable)new JavascriptException(str));
          } 
          continue;
        } 
        if (integer1 != null) {
          integer = integer1;
          triggerRequestSuccess(integer1.intValue(), paramString);
        } 
        return;
      } 
    } catch (IOException iOException) {
      if (integer != null) {
        triggerRequestFailure(integer.intValue(), iOException);
        return;
      } 
      abort("Parsing response message from websocket failed", iOException);
      return;
    } 
  }
  
  public void onOpen(ai paramai, ae paramae) {
    this.mWebSocket = paramai;
    ((JSDebuggerCallback)a.b(this.mConnectCallback)).onSuccess(null);
    this.mConnectCallback = null;
  }
  
  public void prepareJSRuntime(JSDebuggerCallback paramJSDebuggerCallback) {
    int i = this.mRequestID.getAndIncrement();
    this.mCallbacks.put(Integer.valueOf(i), paramJSDebuggerCallback);
    try {
      StringWriter stringWriter = new StringWriter();
      (new JsonWriter(stringWriter)).beginObject().name("id").value(i).name("method").value("prepareJSRuntime").endObject().close();
      sendMessage(i, stringWriter.toString());
      return;
    } catch (IOException iOException) {
      triggerRequestFailure(i, iOException);
      return;
    } 
  }
  
  public static interface JSDebuggerCallback {
    void onFailure(Throwable param1Throwable);
    
    void onSuccess(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\JSDebuggerWebSocketClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */