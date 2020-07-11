package com.facebook.react.modules.websocket;

import com.a;
import com.facebook.common.e.a;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import g.i;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.y;

@ReactModule(hasConstants = false, name = "WebSocketModule")
public final class WebSocketModule extends ReactContextBaseJavaModule {
  public final Map<Integer, ContentHandler> mContentHandlers = new ConcurrentHashMap<Integer, ContentHandler>();
  
  private ForwardingCookieHandler mCookieHandler;
  
  private ReactContext mReactContext;
  
  public final Map<Integer, ai> mWebSocketConnections = new ConcurrentHashMap<Integer, ai>();
  
  public WebSocketModule(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
    this.mReactContext = (ReactContext)paramReactApplicationContext;
    this.mCookieHandler = new ForwardingCookieHandler((ReactContext)paramReactApplicationContext);
  }
  
  private String getCookie(String paramString) {
    try {
      URI uRI = new URI(getDefaultOrigin(paramString));
      List<String> list = (List)this.mCookieHandler.get(uRI, new HashMap<Object, Object>()).get("Cookie");
      return (list == null || list.isEmpty()) ? null : list.get(0);
    } catch (URISyntaxException|java.io.IOException uRISyntaxException) {
      StringBuilder stringBuilder = new StringBuilder("Unable to get cookie from ");
      stringBuilder.append(paramString);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
  }
  
  private static String getDefaultOrigin(String paramString) {
    String str = "";
    try {
      URI uRI = new URI(paramString);
      boolean bool = uRI.getScheme().equals("wss");
      if (bool) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append("https");
        null = stringBuilder.toString();
      } else {
        bool = uRI.getScheme().equals("ws");
        if (bool) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("");
          stringBuilder.append("http");
          null = stringBuilder.toString();
        } else if (uRI.getScheme().equals("http") || uRI.getScheme().equals("https")) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("");
          stringBuilder.append(uRI.getScheme());
          null = stringBuilder.toString();
        } 
      } 
      return (uRI.getPort() != -1) ? a.a("%s://%s:%s", new Object[] { null, uRI.getHost(), Integer.valueOf(uRI.getPort()) }) : a.a("%s://%s/", new Object[] { null, uRI.getHost() });
    } catch (URISyntaxException uRISyntaxException) {
      StringBuilder stringBuilder = new StringBuilder("Unable to set ");
      stringBuilder.append(paramString);
      stringBuilder.append(" as default origin header");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
  }
  
  @ReactMethod
  public final void close(int paramInt1, String paramString, int paramInt2) {
    ai ai = this.mWebSocketConnections.get(Integer.valueOf(paramInt2));
    if (ai == null)
      return; 
    try {
      ai.b(paramInt1, paramString);
      this.mWebSocketConnections.remove(Integer.valueOf(paramInt2));
      this.mContentHandlers.remove(Integer.valueOf(paramInt2));
      return;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder("Could not close WebSocket connection for id ");
      stringBuilder.append(paramInt2);
      a.c("ReactNative", stringBuilder.toString(), exception);
      return;
    } 
  }
  
  @ReactMethod
  public final void connect(String paramString, ReadableArray paramReadableArray, ReadableMap paramReadableMap, final int id) {
    try {
      y y = (new y.a()).a(10L, TimeUnit.SECONDS).c(10L, TimeUnit.SECONDS).b(0L, TimeUnit.MINUTES).a();
      ac.a a = (new ac.a()).a(Integer.valueOf(id)).a(paramString);
      String str = getCookie(paramString);
      if (str != null)
        a.b("Cookie", str); 
      if (paramReadableMap != null && paramReadableMap.hasKey("headers") && paramReadableMap.getType("headers").equals(ReadableType.Map)) {
        paramReadableMap = paramReadableMap.getMap("headers");
        ReadableMapKeySetIterator readableMapKeySetIterator = paramReadableMap.keySetIterator();
        if (!paramReadableMap.hasKey("origin"))
          a.b("origin", getDefaultOrigin(paramString)); 
        while (readableMapKeySetIterator.hasNextKey()) {
          paramString = readableMapKeySetIterator.nextKey();
          if (ReadableType.String.equals(paramReadableMap.getType(paramString))) {
            a.b(paramString, paramReadableMap.getString(paramString));
            continue;
          } 
          StringBuilder stringBuilder = new StringBuilder("Ignoring: requested ");
          stringBuilder.append(paramString);
          stringBuilder.append(", value not a string");
          a.b("ReactNative", stringBuilder.toString());
        } 
      } else {
        a.b("origin", getDefaultOrigin(paramString));
      } 
      if (paramReadableArray != null && paramReadableArray.size() > 0) {
        StringBuilder stringBuilder = new StringBuilder("");
        int i;
        for (i = 0;; i++) {
          if (i < paramReadableArray.size()) {
            String str1 = paramReadableArray.getString(i).trim();
            if (!str1.isEmpty() && !str1.contains(",")) {
              stringBuilder.append(str1);
              stringBuilder.append(",");
            } 
          } else {
            if (stringBuilder.length() > 0) {
              stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
              a.b("Sec-WebSocket-Protocol", stringBuilder.toString());
            } 
            y.a(a.c(), new aj() {
                  public void onClosed(ai param1ai, int param1Int, String param1String) {
                    WritableMap writableMap = Arguments.createMap();
                    writableMap.putInt("id", id);
                    writableMap.putInt("code", param1Int);
                    writableMap.putString("reason", param1String);
                    WebSocketModule.this.sendEvent("websocketClosed", writableMap);
                  }
                  
                  public void onFailure(ai param1ai, Throwable param1Throwable, ae param1ae) {
                    WebSocketModule.this.notifyWebSocketFailed(id, param1Throwable.getMessage());
                  }
                  
                  public void onMessage(ai param1ai, i param1i) {
                    WritableMap writableMap = Arguments.createMap();
                    writableMap.putInt("id", id);
                    writableMap.putString("type", "binary");
                    WebSocketModule.ContentHandler contentHandler = WebSocketModule.this.mContentHandlers.get(Integer.valueOf(id));
                    if (contentHandler != null) {
                      contentHandler.onMessage(param1i, writableMap);
                    } else {
                      writableMap.putString("data", param1i.base64());
                    } 
                    WebSocketModule.this.sendEvent("websocketMessage", writableMap);
                  }
                  
                  public void onMessage(ai param1ai, String param1String) {
                    WritableMap writableMap = Arguments.createMap();
                    writableMap.putInt("id", id);
                    writableMap.putString("type", "text");
                    WebSocketModule.ContentHandler contentHandler = WebSocketModule.this.mContentHandlers.get(Integer.valueOf(id));
                    if (contentHandler != null) {
                      contentHandler.onMessage(param1String, writableMap);
                    } else {
                      writableMap.putString("data", param1String);
                    } 
                    WebSocketModule.this.sendEvent("websocketMessage", writableMap);
                  }
                  
                  public void onOpen(ai param1ai, ae param1ae) {
                    WebSocketModule.this.mWebSocketConnections.put(Integer.valueOf(id), param1ai);
                    WritableMap writableMap = Arguments.createMap();
                    writableMap.putInt("id", id);
                    WebSocketModule.this.sendEvent("websocketOpen", writableMap);
                  }
                });
            y.c.a().shutdown();
          } 
        } 
      } 
      y.a(a.c(), new aj() {
            public void onClosed(ai param1ai, int param1Int, String param1String) {
              WritableMap writableMap = Arguments.createMap();
              writableMap.putInt("id", id);
              writableMap.putInt("code", param1Int);
              writableMap.putString("reason", param1String);
              WebSocketModule.this.sendEvent("websocketClosed", writableMap);
            }
            
            public void onFailure(ai param1ai, Throwable param1Throwable, ae param1ae) {
              WebSocketModule.this.notifyWebSocketFailed(id, param1Throwable.getMessage());
            }
            
            public void onMessage(ai param1ai, i param1i) {
              WritableMap writableMap = Arguments.createMap();
              writableMap.putInt("id", id);
              writableMap.putString("type", "binary");
              WebSocketModule.ContentHandler contentHandler = WebSocketModule.this.mContentHandlers.get(Integer.valueOf(id));
              if (contentHandler != null) {
                contentHandler.onMessage(param1i, writableMap);
              } else {
                writableMap.putString("data", param1i.base64());
              } 
              WebSocketModule.this.sendEvent("websocketMessage", writableMap);
            }
            
            public void onMessage(ai param1ai, String param1String) {
              WritableMap writableMap = Arguments.createMap();
              writableMap.putInt("id", id);
              writableMap.putString("type", "text");
              WebSocketModule.ContentHandler contentHandler = WebSocketModule.this.mContentHandlers.get(Integer.valueOf(id));
              if (contentHandler != null) {
                contentHandler.onMessage(param1String, writableMap);
              } else {
                writableMap.putString("data", param1String);
              } 
              WebSocketModule.this.sendEvent("websocketMessage", writableMap);
            }
            
            public void onOpen(ai param1ai, ae param1ae) {
              WebSocketModule.this.mWebSocketConnections.put(Integer.valueOf(id), param1ai);
              WritableMap writableMap = Arguments.createMap();
              writableMap.putInt("id", id);
              WebSocketModule.this.sendEvent("websocketOpen", writableMap);
            }
          });
      y.c.a().shutdown();
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  public final String getName() {
    return "WebSocketModule";
  }
  
  public final void notifyWebSocketFailed(int paramInt, String paramString) {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("id", paramInt);
    writableMap.putString("message", paramString);
    sendEvent("websocketFailed", writableMap);
  }
  
  @ReactMethod
  public final void ping(int paramInt) {
    WritableMap writableMap;
    ai ai = this.mWebSocketConnections.get(Integer.valueOf(paramInt));
    if (ai == null) {
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putString("message", "client is null");
      sendEvent("websocketFailed", writableMap);
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putInt("code", 0);
      writableMap.putString("reason", "client is null");
      sendEvent("websocketClosed", writableMap);
      this.mWebSocketConnections.remove(Integer.valueOf(paramInt));
      this.mContentHandlers.remove(Integer.valueOf(paramInt));
      return;
    } 
    try {
      writableMap.d(i.EMPTY);
      return;
    } catch (Exception exception) {
      notifyWebSocketFailed(paramInt, exception.getMessage());
      return;
    } 
  }
  
  @ReactMethod
  public final void send(String paramString, int paramInt) {
    WritableMap writableMap;
    ai ai = this.mWebSocketConnections.get(Integer.valueOf(paramInt));
    if (ai == null) {
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putString("message", "client is null");
      sendEvent("websocketFailed", writableMap);
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putInt("code", 0);
      writableMap.putString("reason", "client is null");
      sendEvent("websocketClosed", writableMap);
      this.mWebSocketConnections.remove(Integer.valueOf(paramInt));
      this.mContentHandlers.remove(Integer.valueOf(paramInt));
      return;
    } 
    try {
      ai.b((String)writableMap);
      return;
    } catch (Exception exception) {
      notifyWebSocketFailed(paramInt, exception.getMessage());
      return;
    } 
  }
  
  public final void sendBinary(i parami, int paramInt) {
    WritableMap writableMap;
    ai ai = this.mWebSocketConnections.get(Integer.valueOf(paramInt));
    if (ai == null) {
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putString("message", "client is null");
      sendEvent("websocketFailed", writableMap);
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putInt("code", 0);
      writableMap.putString("reason", "client is null");
      sendEvent("websocketClosed", writableMap);
      this.mWebSocketConnections.remove(Integer.valueOf(paramInt));
      this.mContentHandlers.remove(Integer.valueOf(paramInt));
      return;
    } 
    try {
      ai.d((i)writableMap);
      return;
    } catch (Exception exception) {
      notifyWebSocketFailed(paramInt, exception.getMessage());
      return;
    } 
  }
  
  @ReactMethod
  public final void sendBinary(String paramString, int paramInt) {
    WritableMap writableMap;
    ai ai = this.mWebSocketConnections.get(Integer.valueOf(paramInt));
    if (ai == null) {
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putString("message", "client is null");
      sendEvent("websocketFailed", writableMap);
      writableMap = Arguments.createMap();
      writableMap.putInt("id", paramInt);
      writableMap.putInt("code", 0);
      writableMap.putString("reason", "client is null");
      sendEvent("websocketClosed", writableMap);
      this.mWebSocketConnections.remove(Integer.valueOf(paramInt));
      this.mContentHandlers.remove(Integer.valueOf(paramInt));
      return;
    } 
    try {
      ai.d(i.decodeBase64((String)writableMap));
      return;
    } catch (Exception exception) {
      notifyWebSocketFailed(paramInt, exception.getMessage());
      return;
    } 
  }
  
  public final void sendEvent(String paramString, WritableMap paramWritableMap) {
    ((DeviceEventManagerModule.RCTDeviceEventEmitter)this.mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(paramString, paramWritableMap);
  }
  
  public final void setContentHandler(int paramInt, ContentHandler paramContentHandler) {
    if (paramContentHandler != null) {
      this.mContentHandlers.put(Integer.valueOf(paramInt), paramContentHandler);
      return;
    } 
    this.mContentHandlers.remove(Integer.valueOf(paramInt));
  }
  
  public static interface ContentHandler {
    void onMessage(i param1i, WritableMap param1WritableMap);
    
    void onMessage(String param1String, WritableMap param1WritableMap);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\websocket\WebSocketModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */