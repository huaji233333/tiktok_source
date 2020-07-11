package com.tt.miniapp.debug;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.WebView;
import com.he.JsRunLoop;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsEngine;
import com.he.jsbinding.JsObject;
import com.he.v8_inspect.Inspect;
import com.tt.miniapp.debug.appData.AppData;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.DebugUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class DebugManager {
  private static DebugManager sInstance;
  
  public String jsDebugURL;
  
  private Handler mDebugHandler;
  
  public boolean mHasComplete;
  
  public boolean mIsDebugOpen;
  
  public boolean mIsRemoteDebug;
  
  public boolean mRemoteDebugEnable;
  
  public RemoteDebugManager mRemoteDebugManager;
  
  public boolean mTmaDebugOpen;
  
  public WebviewDebugManager mWebviewDebugManager;
  
  public DebugManager() {
    boolean bool;
    if (DebugUtil.debug() || ChannelUtil.isLocalTest()) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mIsDebugOpen = bool;
    this.mIsRemoteDebug = false;
    this.mRemoteDebugEnable = false;
    this.mTmaDebugOpen = false;
    this.mDebugHandler = null;
    this.mRemoteDebugManager = new RemoteDebugManager();
    this.mWebviewDebugManager = new WebviewDebugManager();
  }
  
  public static DebugManager getInst() {
    // Byte code:
    //   0: ldc com/tt/miniapp/debug/DebugManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/debug/DebugManager.sInstance : Lcom/tt/miniapp/debug/DebugManager;
    //   6: ifnonnull -> 40
    //   9: ldc com/tt/miniapp/debug/DebugManager
    //   11: monitorenter
    //   12: getstatic com/tt/miniapp/debug/DebugManager.sInstance : Lcom/tt/miniapp/debug/DebugManager;
    //   15: ifnonnull -> 28
    //   18: new com/tt/miniapp/debug/DebugManager
    //   21: dup
    //   22: invokespecial <init> : ()V
    //   25: putstatic com/tt/miniapp/debug/DebugManager.sInstance : Lcom/tt/miniapp/debug/DebugManager;
    //   28: ldc com/tt/miniapp/debug/DebugManager
    //   30: monitorexit
    //   31: goto -> 40
    //   34: astore_0
    //   35: ldc com/tt/miniapp/debug/DebugManager
    //   37: monitorexit
    //   38: aload_0
    //   39: athrow
    //   40: getstatic com/tt/miniapp/debug/DebugManager.sInstance : Lcom/tt/miniapp/debug/DebugManager;
    //   43: astore_0
    //   44: ldc com/tt/miniapp/debug/DebugManager
    //   46: monitorexit
    //   47: aload_0
    //   48: areturn
    //   49: astore_0
    //   50: ldc com/tt/miniapp/debug/DebugManager
    //   52: monitorexit
    //   53: aload_0
    //   54: athrow
    // Exception table:
    //   from	to	target	type
    //   3	12	49	finally
    //   12	28	34	finally
    //   28	31	34	finally
    //   35	38	34	finally
    //   38	40	49	finally
    //   40	44	49	finally
  }
  
  private void initHandler(final DebugCallback callback) {
    this.mDebugHandler = new Handler(HandlerThreadUtil.getBackgroundHandlerThread().getLooper()) {
        public void handleMessage(Message param1Message) {
          super.handleMessage(param1Message);
          int i = param1Message.what;
          if (i != -1000) {
            if (i != -1) {
              if (i != 1000) {
                if (i != 2000)
                  return; 
                try {
                  JSONObject jSONObject = (JSONObject)param1Message.obj;
                  StringBuilder stringBuilder1 = new StringBuilder("handleMessage: pageSwitchInfo: ");
                  stringBuilder1.append(jSONObject);
                  AppBrandLogger.d("DebugManager", new Object[] { stringBuilder1.toString() });
                  DebugManager.this.mWebviewDebugManager.getCurWebviewTarget(jSONObject.getString("mPageContent"), jSONObject.getInt("webviewId"));
                  return;
                } catch (JSONException jSONException) {
                  AppBrandLogger.e("DebugManager", new Object[] { jSONException });
                  return;
                } 
              } 
              DebugManager debugManager = DebugManager.this;
              debugManager.mRemoteDebugEnable = true;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(DebugManager.this.mRemoteDebugManager.getBaseDebugURL());
              stringBuilder.append("&cursor=v8&role=phone");
              debugManager.jsDebugURL = stringBuilder.toString();
              DebugManager.this.callComplete(true, callback);
              return;
            } 
            DebugManager.this.mRemoteDebugManager.closeRemoteWs("time over 10 second");
            DebugManager.this.callComplete(false, callback);
            return;
          } 
          DebugManager.this.callComplete(false, callback);
        }
      };
  }
  
  private void openJsDebug(String paramString, JsContext paramJsContext) {
    StringBuilder stringBuilder = new StringBuilder("jsDebugURL: ");
    stringBuilder.append(paramString);
    AppBrandLogger.d("DebugManager", new Object[] { stringBuilder.toString() });
    Inspect.setRemoteDebugURL(paramString);
    Inspect.start();
    Inspect.onNewIsolate("LittleApp", "0");
    (getInst()).mTmaDebugOpen = true;
    JsObject jsObject2 = paramJsContext.global();
    final JsObject Math = jsObject2.getObject("Math");
    jsObject2.release();
    final Handler handler = ((JsRunLoop)Thread.currentThread()).getHandler();
    handler.post(new Runnable() {
          public void run() {
            handler.postDelayed(this, 17L);
            Math.callMethod("random", 0);
            JsEngine.popResult();
          }
        });
  }
  
  public void addAppData(String paramString, int paramInt) {
    AppData appData = this.mRemoteDebugManager.getAppDataReporter().parseAppData(paramString, paramInt);
    if (appData != null)
      this.mRemoteDebugManager.getAppDataReporter().addAppData(appData); 
  }
  
  public void callComplete(boolean paramBoolean, final DebugCallback callback) {
    if (paramBoolean || DebugUtil.debug() || ChannelUtil.isLocalTest()) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    this.mIsDebugOpen = paramBoolean;
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            if (DebugManager.this.mIsDebugOpen)
              WebView.setWebContentsDebuggingEnabled(true); 
            if (!DebugManager.this.mHasComplete) {
              DebugManager.this.mHasComplete = true;
              callback.complete();
            } 
          }
        });
  }
  
  public Handler getDebugHandler() {
    return this.mDebugHandler;
  }
  
  public RemoteDebugManager getRemoteDebugManager() {
    return this.mRemoteDebugManager;
  }
  
  public void initRemoteDebugInfo(AppInfoEntity paramAppInfoEntity) {
    this.mIsRemoteDebug = this.mRemoteDebugManager.initRemoteDebugInfo(paramAppInfoEntity);
    AppBrandLogger.i("DebugManager", new Object[] { "mIsRemoteDebug ", Boolean.valueOf(this.mIsRemoteDebug) });
  }
  
  public void openDebug(DebugCallback paramDebugCallback) {
    AppBrandLogger.d("DebugManager", new Object[] { "open debug" });
    if (this.mIsRemoteDebug) {
      initHandler(paramDebugCallback);
      this.mRemoteDebugManager.openRemoteWsClient(this.mDebugHandler);
      return;
    } 
    this.mHasComplete = true;
    paramDebugCallback.complete();
  }
  
  public void openDebugIfNeed(JsContext paramJsContext) {
    if (!this.mIsDebugOpen)
      return; 
    getInst().openJsDebug(this.jsDebugURL, paramJsContext);
  }
  
  public void removeWebviewId(int paramInt) {
    this.mWebviewDebugManager.removeWebviewId(paramInt);
  }
  
  public void sendAppData(int paramInt) {
    AppData appData = this.mRemoteDebugManager.getAppDataReporter().getAppData(paramInt);
    sendMessageToIDE(this.mRemoteDebugManager.getAppDataReporter().geneResult(appData));
  }
  
  public void sendMessageByRemoteWs(String paramString) {
    this.mRemoteDebugManager.sendMessageByRemoteWs(paramString);
  }
  
  public void sendMessageToCurrentWebview(String paramString) {
    this.mWebviewDebugManager.sendMessageToCurrentWebview(paramString);
  }
  
  public void sendMessageToIDE(String paramString) {
    this.mRemoteDebugManager.sendMessageToIDE(paramString);
  }
  
  public static interface DebugCallback {
    void complete();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\DebugManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */