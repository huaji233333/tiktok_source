package com.tt.miniapp.debug;

import android.os.Process;
import android.util.SparseArray;
import com.tt.miniapp.websocket.UnixSocketFactory;
import com.tt.miniapphost.AppBrandLogger;
import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import javax.net.SocketFactory;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.e;
import okhttp3.f;
import okhttp3.o;
import okhttp3.t;
import okhttp3.y;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebviewDebugManager {
  public String curWsId;
  
  public Boolean isFirstConnect = Boolean.valueOf(true);
  
  private int pid = Process.myPid();
  
  public SparseArray<String> webviewIds = new SparseArray();
  
  public HashMap<String, ai> webviewWsHash = new HashMap<String, ai>();
  
  private y buildHttpClient(UnixSocketFactory paramUnixSocketFactory) {
    y.a a = new y.a();
    if (paramUnixSocketFactory != null) {
      a.l = (SocketFactory)paramUnixSocketFactory;
      y.a a1 = a.a((o)paramUnixSocketFactory);
      a1.h = new ProxySelector() {
          public void connectFailed(URI param1URI, SocketAddress param1SocketAddress, IOException param1IOException) {
            AppBrandLogger.e("WebviewDebugManager", new Object[] { param1IOException.getMessage() });
          }
          
          public List<Proxy> select(URI param1URI) {
            return null;
          }
        };
      return a1.a();
    } 
    throw new NullPointerException("socketFactory == null");
  }
  
  public void connectToNewWebviewWs(final int webviewId) {
    try {
      UnixSocketFactory unixSocketFactory = new UnixSocketFactory();
      StringBuilder stringBuilder = new StringBuilder("webview_devtools_remote_");
      stringBuilder.append(this.pid);
      t t = unixSocketFactory.urlForPath(stringBuilder.toString(), "devtools", "page", this.curWsId);
      ac ac = (new ac.a()).a(t).c();
      final String wsId = this.curWsId;
      buildHttpClient(unixSocketFactory).a(ac, new aj() {
            public void onClosed(ai param1ai, int param1Int, String param1String) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(wsId);
              stringBuilder.append(" webviewWsClient code: ");
              stringBuilder.append(param1Int);
              stringBuilder.append(" reason: ");
              stringBuilder.append(param1String);
              AppBrandLogger.d("WebviewDebugManager", new Object[] { stringBuilder.toString() });
              WebviewDebugManager.this.webviewWsHash.remove(wsId);
            }
            
            public void onFailure(ai param1ai, Throwable param1Throwable, ae param1ae) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(wsId);
              stringBuilder.append(" webviewWsClient onFailure ");
              stringBuilder.append(param1Throwable.toString());
              AppBrandLogger.d("WebviewDebugManager", new Object[] { stringBuilder.toString() });
              WebviewDebugManager.this.webviewWsHash.remove(wsId);
            }
            
            public void onMessage(ai param1ai, String param1String) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(wsId);
              stringBuilder.append(" webviewWsClient ");
              stringBuilder.append(param1String);
              AppBrandLogger.d("WebviewDebugManager", new Object[] { stringBuilder.toString() });
              if (WebviewDebugManager.this.webviewWsHash.size() > 0)
                try {
                  if ((new JSONObject(param1String)).get("method").equals("Inspector.detached")) {
                    WebviewDebugManager.this.webviewWsHash.remove(wsId);
                    return;
                  } 
                } catch (Exception exception) {} 
              DebugManager.getInst().sendMessageByRemoteWs(param1String);
            }
            
            public void onOpen(ai param1ai, ae param1ae) {
              AppBrandLogger.d("WebviewDebugManager", new Object[] { this.val$wsId, " webviewWsClient open" });
              WebviewDebugManager.this.webviewIds.put(webviewId, wsId);
              WebviewDebugManager.this.webviewWsHash.put(wsId, param1ai);
              if (!WebviewDebugManager.this.isFirstConnect.booleanValue()) {
                DebugManager.getInst().sendMessageToIDE("reloadDevtool");
                WebviewDebugManager.this.sendMessageToCurrentWebview("{\"id\":991,\"method\":\"DOM.enable\"}");
                WebviewDebugManager.this.sendMessageToCurrentWebview("{\"id\":992,\"method\":\"CSS.enable\"}");
                WebviewDebugManager.this.sendMessageToCurrentWebview("{\"id\":993,\"method\":\"Overlay.enable\"}");
                WebviewDebugManager.this.sendMessageToCurrentWebview("{\"id\":994,\"method\":\"Overlay.setShowViewportSizeOnResize\",\"params\":{\"show\":true}");
              } else {
                WebviewDebugManager.this.isFirstConnect = Boolean.valueOf(false);
                DebugManager.getInst().sendMessageToIDE("webviewReady");
              } 
              DebugManager.getInst().sendAppData(webviewId);
            }
          });
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "WebviewDebugManager", exception.getStackTrace());
      return;
    } 
  }
  
  public void getCurWebviewTarget(final String pageContent, final int webviewId) {
    AppBrandLogger.d("WebviewDebugManager", new Object[] { "getCurWebviewTarget ", pageContent, " webviewId ", Integer.valueOf(webviewId) });
    String str = (String)this.webviewIds.get(webviewId);
    if (str != null) {
      this.curWsId = str;
      DebugManager.getInst().sendMessageToIDE("reloadDevtool");
      DebugManager.getInst().sendAppData(webviewId);
      return;
    } 
    UnixSocketFactory unixSocketFactory = new UnixSocketFactory();
    StringBuilder stringBuilder = new StringBuilder("webview_devtools_remote_");
    stringBuilder.append(this.pid);
    t t = unixSocketFactory.urlForPath(stringBuilder.toString(), "json");
    ac ac = (new ac.a()).a(t).b("Host", "").c();
    buildHttpClient(unixSocketFactory).a(ac).a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {
            StringBuilder stringBuilder = new StringBuilder("onFailure: ");
            stringBuilder.append(param1IOException.getMessage());
            AppBrandLogger.e("WebviewDebugManager", new Object[] { stringBuilder.toString() });
            WebviewDebugManager.this.getCurWebviewTarget(pageContent, webviewId);
          }
          
          public void onResponse(e param1e, ae param1ae) throws IOException {
            if (param1ae.g != null) {
              String str = param1ae.g.string();
            } else {
              param1e = null;
            } 
            StringBuilder stringBuilder2 = new StringBuilder("onResponse: ");
            stringBuilder2.append((String)param1e);
            AppBrandLogger.d("WebviewDebugManager", new Object[] { stringBuilder2.toString() });
            try {
              JSONArray jSONArray = new JSONArray((String)param1e);
              for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String str1 = jSONObject.get("url").toString();
                String str2 = jSONObject.get("id").toString();
                if (str1.contains(pageContent) && WebviewDebugManager.this.webviewWsHash.get(str2) == null) {
                  WebviewDebugManager.this.curWsId = str2;
                  break;
                } 
              } 
            } catch (JSONException jSONException) {}
            if (WebviewDebugManager.this.curWsId == null) {
              WebviewDebugManager.this.getCurWebviewTarget(pageContent, webviewId);
              return;
            } 
            StringBuilder stringBuilder1 = new StringBuilder("curWsId");
            stringBuilder1.append(WebviewDebugManager.this.curWsId);
            stringBuilder1.append(" webviewid: ");
            stringBuilder1.append(webviewId);
            AppBrandLogger.i("WebviewDebugManager", new Object[] { stringBuilder1.toString() });
            WebviewDebugManager.this.connectToNewWebviewWs(webviewId);
          }
        });
  }
  
  public void removeWebviewId(int paramInt) {
    this.webviewIds.remove(paramInt);
  }
  
  public void sendMessageToCurrentWebview(String paramString) {
    if (this.webviewWsHash.get(this.curWsId) != null) {
      ((ai)this.webviewWsHash.get(this.curWsId)).b(paramString);
      AppBrandLogger.d("WebviewDebugManager", new Object[] { this.curWsId, paramString });
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\WebviewDebugManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */