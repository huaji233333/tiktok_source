package com.tt.miniapp.debug;

import android.os.Handler;
import android.text.TextUtils;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsScopedContext;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.debug.appData.AppData;
import com.tt.miniapp.debug.appData.AppDataReporter;
import com.tt.miniapp.debug.storage.StorageReporter;
import com.tt.miniapp.jsbridge.JsRuntimeManager;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.DebugUtil;
import java.io.IOException;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.y;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteDebugManager {
  public AppDataReporter appDataReporter = new AppDataReporter();
  
  private String baseDebugURL;
  
  public ai remoteWs;
  
  public StorageReporter storageReporter = new StorageReporter();
  
  public void clearStorage(int paramInt, boolean paramBoolean) throws JSONException {
    sendMessageByRemoteWs(this.storageReporter.clearStorage(paramInt, paramBoolean));
  }
  
  public void closeRemoteWs(String paramString) {
    AppBrandLogger.e("RemoteDebugManager", new Object[] { "close_remote_ws", paramString });
    ai ai1 = this.remoteWs;
    if (ai1 != null)
      ai1.b(3999, paramString); 
  }
  
  public AppDataReporter getAppDataReporter() {
    return this.appDataReporter;
  }
  
  public String getBaseDebugURL() {
    return this.baseDebugURL;
  }
  
  public void getDOMStorageItems(int paramInt, JSONArray paramJSONArray) throws JSONException {
    sendMessageByRemoteWs(this.storageReporter.getDOMStorageItems(paramInt, paramJSONArray));
  }
  
  public boolean initRemoteDebugInfo(AppInfoEntity paramAppInfoEntity) {
    if (paramAppInfoEntity != null && !TextUtils.isEmpty(paramAppInfoEntity.session) && !TextUtils.isEmpty(paramAppInfoEntity.gtoken) && !TextUtils.isEmpty(paramAppInfoEntity.roomid)) {
      StringBuilder stringBuilder = new StringBuilder("ws://gate.snssdk.com/debug_room?session=");
      stringBuilder.append(paramAppInfoEntity.session);
      stringBuilder.append("&gToken=");
      stringBuilder.append(paramAppInfoEntity.gtoken);
      stringBuilder.append("&room_id=");
      stringBuilder.append(paramAppInfoEntity.roomid);
      stringBuilder.append("&need_cache=1");
      this.baseDebugURL = stringBuilder.toString();
      return true;
    } 
    return false;
  }
  
  public void openRemoteWsClient(final Handler debugHandler) {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(this.baseDebugURL);
    stringBuilder1.append("&cursor=webview&role=phone");
    String str = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder("openRemoteWsClient: ");
    stringBuilder2.append(str);
    AppBrandLogger.d("RemoteDebugManager", new Object[] { stringBuilder2.toString() });
    y y = (new y.a()).a();
    ac ac = (new ac.a()).a(str).c();
    debugHandler.sendEmptyMessageDelayed(-1, 10000L);
    y.a(ac, new aj() {
          public void onClosed(ai param1ai, int param1Int, String param1String) {
            StringBuilder stringBuilder = new StringBuilder("remoteWsClient code: ");
            stringBuilder.append(param1Int);
            stringBuilder.append(" reason: ");
            stringBuilder.append(param1String);
            AppBrandLogger.d("RemoteDebugManager", new Object[] { stringBuilder.toString() });
            RemoteDebugManager.this.remoteWs = null;
            debugHandler.sendEmptyMessage(-1000);
          }
          
          public void onFailure(ai param1ai, Throwable param1Throwable, ae param1ae) {
            StringBuilder stringBuilder = new StringBuilder("remoteWsClient onFailure");
            stringBuilder.append(param1Throwable.toString());
            AppBrandLogger.d("RemoteDebugManager", new Object[] { stringBuilder.toString() });
            debugHandler.sendEmptyMessage(-1000);
          }
          
          public void onMessage(ai param1ai, String param1String) {
            StringBuilder stringBuilder = new StringBuilder("onMessage remoteWsClient ");
            stringBuilder.append(param1String);
            AppBrandLogger.d("RemoteDebugManager", new Object[] { stringBuilder.toString() });
            if (TextUtils.equals(param1String, "entrustDebug")) {
              if (debugHandler.hasMessages(-1))
                debugHandler.removeMessages(-1); 
              debugHandler.sendEmptyMessage(1000);
              return;
            } 
            if (TextUtils.equals(param1String, "cancelDebug")) {
              if (debugHandler.hasMessages(-1))
                debugHandler.removeMessages(-1); 
              RemoteDebugManager.this.closeRemoteWs(param1String);
              debugHandler.sendEmptyMessage(-1000);
              return;
            } 
            try {
              final AppData appData;
              JSONArray jSONArray;
              String str1;
              JSONObject jSONObject2 = new JSONObject(param1String);
              String str2 = jSONObject2.optString("method");
              JSONObject jSONObject1 = jSONObject2.optJSONObject("params");
              int i = jSONObject2.optInt("id");
              if (TextUtils.equals(str2, "AppData")) {
                appData = AppData.parseJson(jSONObject1);
                ((JsRuntimeManager)AppbrandApplicationImpl.getInst().getService(JsRuntimeManager.class)).getCurrentRuntime().executeInJsThread(new JsContext.ScopeCallback() {
                      public void run(JsScopedContext param2JsScopedContext) {
                        try {
                          StringBuilder stringBuilder = new StringBuilder("var pageStacks = getCurrentPages();\nvar currentPages = pageStacks[pageStacks.length-1];\ncurrentPages.setData(");
                          stringBuilder.append(appData.data);
                          stringBuilder.append("); ");
                          param2JsScopedContext.eval(stringBuilder.toString(), null);
                          RemoteDebugManager.this.appDataReporter.addAppData(appData);
                          return;
                        } catch (Exception exception) {
                          DebugUtil.outputError("RemoteDebugManager", new Object[] { "AppData setData fail", exception });
                          return;
                        } 
                      }
                    });
                return;
              } 
              if (TextUtils.equals(str2, "DOMStorage.getDOMStorageItems")) {
                RemoteDebugManager.this.storageReporter.setStorageId(appData.optJSONObject("storageId"));
                jSONArray = Storage.getKeys();
                RemoteDebugManager.this.getDOMStorageItems(i, jSONArray);
                return;
              } 
              boolean bool = TextUtils.equals(str2, "DOMStorage.removeDOMStorageItem");
              if (bool) {
                str1 = jSONArray.optString("key");
                bool = Storage.removeStorage(str1);
                RemoteDebugManager.this.removeDOMStorageItem(i, bool, str1);
                return;
              } 
              if (TextUtils.equals(str2, "DOMStorage.setDOMStorageItem")) {
                String str = str1.optString("key");
                str1 = str1.optString("value");
                try {
                  str2 = Storage.getValue(str);
                  bool = Storage.setValue(str, str1, "String");
                  RemoteDebugManager.this.setDOMStorageItem(i, bool, str, str2, str1);
                  return;
                } catch (IOException iOException) {
                  return;
                } 
              } 
              if (TextUtils.equals(str2, "DOMStorage.clear")) {
                bool = Storage.clearStorage();
                RemoteDebugManager.this.clearStorage(i, bool);
                return;
              } 
            } catch (JSONException jSONException) {}
            DebugManager.getInst().sendMessageToCurrentWebview(param1String);
          }
          
          public void onOpen(ai param1ai, ae param1ae) {
            AppBrandLogger.d("RemoteDebugManager", new Object[] { "onOpen remoteWsClient" });
            RemoteDebugManager.this.remoteWs = param1ai;
          }
        });
  }
  
  public void removeDOMStorageItem(int paramInt, boolean paramBoolean, String paramString) throws JSONException {
    sendMessageByRemoteWs(this.storageReporter.removeDOMStorageItem(paramInt, paramBoolean, paramString));
  }
  
  public void sendMessageByRemoteWs(String paramString) {
    ai ai1 = this.remoteWs;
    if (ai1 != null) {
      ai1.b(paramString);
      AppBrandLogger.d("RemoteDebugManager", new Object[] { paramString });
    } 
  }
  
  public void sendMessageToIDE(String paramString) {
    ai ai1 = this.remoteWs;
    if (ai1 != null) {
      StringBuilder stringBuilder = new StringBuilder("__IDE__");
      stringBuilder.append(paramString);
      ai1.b(stringBuilder.toString());
      AppBrandLogger.d("RemoteDebugManager", new Object[] { paramString });
    } 
  }
  
  public void setDOMStorageItem(int paramInt, boolean paramBoolean, String paramString1, String paramString2, String paramString3) throws JSONException {
    sendMessageByRemoteWs(this.storageReporter.setDOMStorageItem(paramInt, paramBoolean, paramString1, paramString2, paramString3));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\RemoteDebugManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */