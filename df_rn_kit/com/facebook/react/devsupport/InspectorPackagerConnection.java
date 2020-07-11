package com.facebook.react.devsupport;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import com.facebook.common.e.a;
import com.facebook.react.bridge.Inspector;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.y;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InspectorPackagerConnection {
  private BundleStatusProvider mBundleStatusProvider;
  
  private final Connection mConnection;
  
  public final Map<String, Inspector.LocalConnection> mInspectorConnections;
  
  private final String mPackageName;
  
  public InspectorPackagerConnection(String paramString1, String paramString2, BundleStatusProvider paramBundleStatusProvider) {
    this.mConnection = new Connection(paramString1);
    this.mInspectorConnections = new HashMap<String, Inspector.LocalConnection>();
    this.mPackageName = paramString2;
    this.mBundleStatusProvider = paramBundleStatusProvider;
  }
  
  private JSONArray getPages() throws JSONException {
    List list = Inspector.getPages();
    JSONArray jSONArray = new JSONArray();
    BundleStatus bundleStatus = this.mBundleStatusProvider.getBundleStatus();
    for (Inspector.Page page : list) {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("id", String.valueOf(page.getId()));
      jSONObject.put("title", page.getTitle());
      jSONObject.put("app", this.mPackageName);
      jSONObject.put("vm", page.getVM());
      jSONObject.put("isLastBundleDownloadSuccess", bundleStatus.isLastDownloadSucess);
      jSONObject.put("bundleUpdateTimestamp", bundleStatus.updateTimestamp);
      jSONArray.put(jSONObject);
    } 
    return jSONArray;
  }
  
  private void handleConnect(JSONObject paramJSONObject) throws JSONException {
    final String pageId = paramJSONObject.getString("pageId");
    if ((Inspector.LocalConnection)this.mInspectorConnections.remove(str) == null)
      try {
        Inspector.LocalConnection localConnection = Inspector.connect(Integer.parseInt(str), new Inspector.RemoteConnection() {
              public void onDisconnect() {
                try {
                  InspectorPackagerConnection.this.mInspectorConnections.remove(pageId);
                  InspectorPackagerConnection.this.sendEvent("disconnect", InspectorPackagerConnection.this.makePageIdPayload(pageId));
                  return;
                } catch (JSONException jSONException) {
                  a.b("InspectorPackagerConnection", "Couldn't send event to packager", (Throwable)jSONException);
                  return;
                } 
              }
              
              public void onMessage(String param1String) {
                try {
                  InspectorPackagerConnection.this.sendWrappedEvent(pageId, param1String);
                  return;
                } catch (JSONException jSONException) {
                  a.b("InspectorPackagerConnection", "Couldn't send event to packager", (Throwable)jSONException);
                  return;
                } 
              }
            });
        this.mInspectorConnections.put(str, localConnection);
        return;
      } catch (Exception exception) {
        StringBuilder stringBuilder1 = new StringBuilder("Failed to open page: ");
        stringBuilder1.append(str);
        a.b("InspectorPackagerConnection", stringBuilder1.toString(), exception);
        sendEvent("disconnect", makePageIdPayload(str));
        return;
      }  
    StringBuilder stringBuilder = new StringBuilder("Already connected: ");
    stringBuilder.append(str);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  private void handleDisconnect(JSONObject paramJSONObject) throws JSONException {
    String str = paramJSONObject.getString("pageId");
    Inspector.LocalConnection localConnection = this.mInspectorConnections.remove(str);
    if (localConnection == null)
      return; 
    localConnection.disconnect();
  }
  
  private void handleWrappedEvent(JSONObject paramJSONObject) throws JSONException {
    String str2 = paramJSONObject.getString("pageId");
    String str1 = paramJSONObject.getString("wrappedEvent");
    Inspector.LocalConnection localConnection = this.mInspectorConnections.get(str2);
    if (localConnection != null) {
      localConnection.sendMessage(str1);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Not connected: ");
    stringBuilder.append(str2);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  void closeAllConnections() {
    Iterator<Map.Entry> iterator = this.mInspectorConnections.entrySet().iterator();
    while (iterator.hasNext())
      ((Inspector.LocalConnection)((Map.Entry)iterator.next()).getValue()).disconnect(); 
    this.mInspectorConnections.clear();
  }
  
  public void closeQuietly() {
    this.mConnection.close();
  }
  
  public void connect() {
    this.mConnection.connect();
  }
  
  void handleProxyMessage(JSONObject paramJSONObject) throws JSONException, IOException {
    byte b;
    String str = paramJSONObject.getString("event");
    switch (str.hashCode()) {
      default:
        b = -1;
        break;
      case 1962251790:
        if (str.equals("getPages")) {
          b = 0;
          break;
        } 
      case 1328613653:
        if (str.equals("wrappedEvent")) {
          b = 1;
          break;
        } 
      case 951351530:
        if (str.equals("connect")) {
          b = 2;
          break;
        } 
      case 530405532:
        if (str.equals("disconnect")) {
          b = 3;
          break;
        } 
    } 
    if (b != 0) {
      StringBuilder stringBuilder;
      if (b != 1) {
        if (b != 2) {
          if (b == 3) {
            handleDisconnect(paramJSONObject.getJSONObject("payload"));
            return;
          } 
          stringBuilder = new StringBuilder("Unknown event: ");
          stringBuilder.append(str);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
        handleConnect(stringBuilder.getJSONObject("payload"));
        return;
      } 
      handleWrappedEvent(stringBuilder.getJSONObject("payload"));
      return;
    } 
    sendEvent("getPages", getPages());
  }
  
  public JSONObject makePageIdPayload(String paramString) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("pageId", paramString);
    return jSONObject;
  }
  
  public void sendEvent(String paramString, Object paramObject) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("event", paramString);
    jSONObject.put("payload", paramObject);
    this.mConnection.send(jSONObject);
  }
  
  public void sendEventToAllConnections(String paramString) {
    Iterator<Map.Entry> iterator = this.mInspectorConnections.entrySet().iterator();
    while (iterator.hasNext())
      ((Inspector.LocalConnection)((Map.Entry)iterator.next()).getValue()).sendMessage(paramString); 
  }
  
  public void sendWrappedEvent(String paramString1, String paramString2) throws JSONException {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("pageId", paramString1);
    jSONObject.put("wrappedEvent", paramString2);
    sendEvent("wrappedEvent", jSONObject);
  }
  
  public static class BundleStatus {
    public Boolean isLastDownloadSucess;
    
    public long updateTimestamp = -1L;
    
    public BundleStatus() {
      this(Boolean.valueOf(false), -1L);
    }
    
    public BundleStatus(Boolean param1Boolean, long param1Long) {
      this.isLastDownloadSucess = param1Boolean;
      this.updateTimestamp = param1Long;
    }
  }
  
  public static interface BundleStatusProvider {
    InspectorPackagerConnection.BundleStatus getBundleStatus();
  }
  
  class Connection extends aj {
    public boolean mClosed;
    
    private final Handler mHandler;
    
    private y mHttpClient;
    
    private boolean mSuppressConnectionErrors;
    
    private final String mUrl;
    
    private ai mWebSocket;
    
    public Connection(String param1String) {
      this.mUrl = param1String;
      this.mHandler = new Handler(Looper.getMainLooper());
    }
    
    private void abort(String param1String, Throwable param1Throwable) {
      StringBuilder stringBuilder = new StringBuilder("Error occurred, shutting down websocket connection: ");
      stringBuilder.append(param1String);
      a.c("InspectorPackagerConnection", stringBuilder.toString(), param1Throwable);
      InspectorPackagerConnection.this.closeAllConnections();
      closeWebSocketQuietly();
    }
    
    private void closeWebSocketQuietly() {
      ai ai1 = this.mWebSocket;
      if (ai1 != null) {
        try {
          ai1.b(1000, "End of session");
        } catch (Exception exception) {}
        this.mWebSocket = null;
      } 
    }
    
    private void reconnect() {
      if (!this.mClosed) {
        if (!this.mSuppressConnectionErrors) {
          a.b("InspectorPackagerConnection", "Couldn't connect to packager, will silently retry");
          this.mSuppressConnectionErrors = true;
        } 
        this.mHandler.postDelayed(new Runnable() {
              public void run() {
                if (!InspectorPackagerConnection.Connection.this.mClosed)
                  InspectorPackagerConnection.Connection.this.connect(); 
              }
            },  2000L);
        return;
      } 
      throw new IllegalStateException("Can't reconnect closed client");
    }
    
    public void close() {
      this.mClosed = true;
      ai ai1 = this.mWebSocket;
      if (ai1 != null) {
        try {
          ai1.b(1000, "End of session");
        } catch (Exception exception) {}
        this.mWebSocket = null;
      } 
    }
    
    public void connect() {
      if (!this.mClosed) {
        if (this.mHttpClient == null)
          this.mHttpClient = (new y.a()).a(10L, TimeUnit.SECONDS).c(10L, TimeUnit.SECONDS).b(0L, TimeUnit.MINUTES).a(); 
        ac ac = (new ac.a()).a(this.mUrl).c();
        this.mHttpClient.a(ac, this);
        return;
      } 
      throw new IllegalStateException("Can't connect closed client");
    }
    
    public void onClosed(ai param1ai, int param1Int, String param1String) {
      this.mWebSocket = null;
      InspectorPackagerConnection.this.closeAllConnections();
      if (!this.mClosed)
        reconnect(); 
    }
    
    public void onFailure(ai param1ai, Throwable param1Throwable, ae param1ae) {
      if (this.mWebSocket != null)
        abort("Websocket exception", param1Throwable); 
      if (!this.mClosed)
        reconnect(); 
    }
    
    public void onMessage(ai param1ai, String param1String) {
      try {
        InspectorPackagerConnection.this.handleProxyMessage(new JSONObject(param1String));
        return;
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      } 
    }
    
    public void onOpen(ai param1ai, ae param1ae) {
      this.mWebSocket = param1ai;
    }
    
    public void send(final JSONObject object) {
      (new AsyncTask<ai, Void, Void>() {
          protected Void doInBackground(ai... param2VarArgs) {
            if (param2VarArgs != null) {
              if (param2VarArgs.length == 0)
                return null; 
              try {
                param2VarArgs[0].b(object.toString());
                return null;
              } catch (Exception exception) {
                a.b("InspectorPackagerConnection", "Couldn't send event to packager", exception);
              } 
            } 
            return null;
          }
        }).execute((Object[])new ai[] { this.mWebSocket });
    }
  }
  
  class null implements Runnable {
    public void run() {
      if (!this.this$1.mClosed)
        this.this$1.connect(); 
    }
  }
  
  class null extends AsyncTask<ai, Void, Void> {
    protected Void doInBackground(ai... param1VarArgs) {
      if (param1VarArgs != null) {
        if (param1VarArgs.length == 0)
          return null; 
        try {
          param1VarArgs[0].b(object.toString());
          return null;
        } catch (Exception exception) {
          a.b("InspectorPackagerConnection", "Couldn't send event to packager", exception);
        } 
      } 
      return null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\InspectorPackagerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */