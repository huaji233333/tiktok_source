package com.tt.miniapp.util.timeline;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.bytedance.sandboxapp.protocol.service.h.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.ParamManager;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import d.f.b.g;
import d.f.b.l;
import d.u;
import okhttp3.ac;
import okhttp3.ae;
import okhttp3.ai;
import okhttp3.aj;
import okhttp3.y;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class IDETimeLineSender extends AbsTimeLineSender {
  public static final Companion Companion = new Companion(null);
  
  private static final int MSG_CREATE_CONNECT;
  
  private static final int MSG_DISCONNECTED;
  
  public static final int MSG_IDE_CONNECTED;
  
  private static final int MSG_IDE_CONNECTING_TIMEOUT;
  
  public static final int MSG_IDE_DISCONNECT;
  
  public static final int MSG_JS_END_COLLECT_POINT;
  
  public static final int MSG_SOCKET_OPEN;
  
  private static final int MSG_WAIT_END_COLLECTED_TIMEOUT;
  
  private boolean isEnableTrace = true;
  
  private volatile int mSocketStatus;
  
  private String mUrl;
  
  public ai mWebSocket;
  
  static {
    AbsTimeLineSender.Companion companion = AbsTimeLineSender.Companion;
    int i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_CREATE_CONNECT = i;
    companion = AbsTimeLineSender.Companion;
    i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_SOCKET_OPEN = i;
    companion = AbsTimeLineSender.Companion;
    i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_IDE_CONNECTED = i;
    companion = AbsTimeLineSender.Companion;
    i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_IDE_CONNECTING_TIMEOUT = i;
    companion = AbsTimeLineSender.Companion;
    i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_IDE_DISCONNECT = i;
    companion = AbsTimeLineSender.Companion;
    i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_WAIT_END_COLLECTED_TIMEOUT = i;
    companion = AbsTimeLineSender.Companion;
    i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_DISCONNECTED = i;
    companion = AbsTimeLineSender.Companion;
    i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_JS_END_COLLECT_POINT = i;
  }
  
  public IDETimeLineSender(Looper paramLooper) {
    super(paramLooper);
  }
  
  private final void connect() {
    if (this.mSocketStatus != 2) {
      if (this.mSocketStatus == 1)
        return; 
      AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
      l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
      String str = (appbrandApplicationImpl.getAppInfo()).timelineServerUrl;
      if (TextUtils.isEmpty(str))
        return; 
      StringBuilder stringBuilder = new StringBuilder("connect socket ");
      stringBuilder.append(str);
      AppBrandLogger.d("IDETimeLineReporter", new Object[] { stringBuilder.toString() });
      (new y.a()).a().a((new ac.a()).a(str).c(), new IDETimeLineSender$connect$1());
    } 
  }
  
  private final JSONObject packData(JSONArray paramJSONArray) {
    AppbrandContext appbrandContext1 = AppbrandContext.getInst();
    l.a(appbrandContext1, "AppbrandContext.getInst()");
    Application application = appbrandContext1.getApplicationContext();
    AppbrandApplicationImpl appbrandApplicationImpl2 = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl2, "AppbrandApplicationImpl.getInst()");
    AppInfoEntity appInfoEntity = appbrandApplicationImpl2.getAppInfo();
    AppbrandContext appbrandContext2 = AppbrandContext.getInst();
    l.a(appbrandContext2, "AppbrandContext.getInst()");
    InitParamsEntity initParamsEntity = appbrandContext2.getInitParams();
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("unique_id", getMUniqueId());
      jSONObject.put("points", paramJSONArray);
      jSONObject.put("mp_id", appInfoEntity.appId);
      jSONObject.put("mp_name", appInfoEntity.appName);
      jSONObject.put("index", getMGroupIndex().getAndIncrement());
      l.a(appInfoEntity, "appInfo");
      if (appInfoEntity.isGame()) {
        String str1 = "micro_game";
        jSONObject.put("param_for_special", str1);
        jSONObject.put("lib_version", ParamManager.getJsSdkVersion((Context)application));
        jSONObject.put("miniapp_sdk_version", ParamManager.getFullAppSdkVersion());
        jSONObject.put("dora_version", ParamManager.getDoraVersion());
        jSONObject.put("launch_from", appInfoEntity.launchFrom);
        l.a(initParamsEntity, "initParamsEntity");
        jSONObject.put("app_id", initParamsEntity.getAppId());
        jSONObject.put("app_version", initParamsEntity.getVersionCode());
        jSONObject.put("version_code", initParamsEntity.getUpdateVersionCode());
        jSONObject.put("channel", initParamsEntity.getChannel());
        AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
        l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
        jSONObject.put("user_id", (((c)appbrandApplicationImpl.getMiniAppContext().getService(c.class)).getHostAppUserInfo()).b);
        jSONObject.put("device_id", initParamsEntity.getDeviceId());
        jSONObject.put("device_model", Build.MODEL);
        jSONObject.put("os_type", "Android");
        jSONObject.put("os_version", DevicesUtil.getSystem());
        jSONObject.put("access", NetUtil.getNewNetType((Context)application));
        return jSONObject;
      } 
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable(getClass().getName(), "", (Throwable)jSONException);
      return jSONObject;
    } 
    String str = "micro_app";
    jSONObject.put("param_for_special", str);
    jSONObject.put("lib_version", ParamManager.getJsSdkVersion((Context)application));
    jSONObject.put("miniapp_sdk_version", ParamManager.getFullAppSdkVersion());
    jSONObject.put("dora_version", ParamManager.getDoraVersion());
    jSONObject.put("launch_from", appInfoEntity.launchFrom);
    l.a(initParamsEntity, "initParamsEntity");
    jSONObject.put("app_id", initParamsEntity.getAppId());
    jSONObject.put("app_version", initParamsEntity.getVersionCode());
    jSONObject.put("version_code", initParamsEntity.getUpdateVersionCode());
    jSONObject.put("channel", initParamsEntity.getChannel());
    AppbrandApplicationImpl appbrandApplicationImpl1 = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl1, "AppbrandApplicationImpl.getInst()");
    jSONObject.put("user_id", (((c)appbrandApplicationImpl1.getMiniAppContext().getService(c.class)).getHostAppUserInfo()).b);
    jSONObject.put("device_id", initParamsEntity.getDeviceId());
    jSONObject.put("device_model", Build.MODEL);
    jSONObject.put("os_type", "Android");
    jSONObject.put("os_version", DevicesUtil.getSystem());
    jSONObject.put("access", NetUtil.getNewNetType((Context)application));
    return jSONObject;
  }
  
  private final void sendToIDE(String paramString, JSONObject paramJSONObject) {
    String str;
    JSONObject jSONObject2 = new JSONObject();
    JSONObject jSONObject3 = new JSONObject();
    JSONObject jSONObject1 = paramJSONObject;
    if (paramJSONObject == null)
      str = ""; 
    jSONObject3.put("data", str);
    jSONObject2.put("method", paramString);
    jSONObject2.put("params", jSONObject3);
    AppBrandLogger.d("IDETimeLineReporter", new Object[] { jSONObject2.toString() });
    ai ai1 = this.mWebSocket;
    if (ai1 != null)
      ai1.b(jSONObject2.toString()); 
  }
  
  public final boolean handleMessage(Message paramMessage) {
    l.b(paramMessage, "msg");
    int i = paramMessage.what;
    if (i == MSG_CREATE_CONNECT) {
      connect();
    } else if (i == MSG_SOCKET_OPEN) {
      if (this.mSocketStatus == 4 || this.mSocketStatus == 0) {
        this.mSocketStatus = 1;
        Object object = paramMessage.obj;
        if (object != null) {
          this.mWebSocket = (ai)object;
          sendToIDE$default(this, "Timeline.connect", (JSONObject)null, 2, (Object)null);
          getMHandler().removeMessages(MSG_IDE_CONNECTING_TIMEOUT);
          getMHandler().sendEmptyMessageDelayed(MSG_IDE_CONNECTING_TIMEOUT, 10000L);
        } else {
          throw new u("null cannot be cast to non-null type okhttp3.WebSocket");
        } 
      } 
    } else if (i == MSG_IDE_CONNECTED) {
      if (this.mSocketStatus != 2) {
        this.mSocketStatus = 2;
        getMHandler().removeMessages(MSG_IDE_CONNECTING_TIMEOUT);
        flush();
      } 
    } else if (i == MSG_IDE_CONNECTING_TIMEOUT) {
      if (this.mSocketStatus == 1) {
        AppBrandLogger.d("IDETimeLineReporter", new Object[] { "MSG_CONNECTING_TIMEOUT" });
        sendToIDE$default(this, "Timeline.connect", (JSONObject)null, 2, (Object)null);
        getMHandler().removeMessages(MSG_IDE_CONNECTING_TIMEOUT);
        getMHandler().sendEmptyMessageDelayed(MSG_IDE_CONNECTING_TIMEOUT, 10000L);
      } 
    } else if (i == MSG_IDE_DISCONNECT) {
      this.mSocketStatus = 3;
      AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
      l.a(appbrandApplicationImpl, "AppbrandApplicationImpl.getInst()");
      WebViewManager webViewManager = appbrandApplicationImpl.getWebViewManager();
      l.a(webViewManager, "webViewManager");
      WebViewManager.IRender iRender = webViewManager.getCurrentIRender();
      l.a(iRender, "webViewManager.currentIRender");
      webViewManager.publish(iRender.getWebViewId(), "collect_timeline_points", null);
      flush();
      getMHandler().sendEmptyMessageDelayed(MSG_WAIT_END_COLLECTED_TIMEOUT, 1000L);
    } else if (i == MSG_WAIT_END_COLLECTED_TIMEOUT) {
      if (this.mSocketStatus == 3) {
        AppBrandLogger.d("IDETimeLineReporter", new Object[] { "MSG_WAIT_END_COLLECTED_TIMEOUT" });
        flush();
        getMHandler().sendEmptyMessage(MSG_DISCONNECTED);
      } 
    } else if (i == MSG_JS_END_COLLECT_POINT) {
      getMHandler().removeMessages(MSG_WAIT_END_COLLECTED_TIMEOUT);
      flush();
      if (this.mSocketStatus == 3)
        getMHandler().sendEmptyMessage(MSG_DISCONNECTED); 
    } else if (i == MSG_DISCONNECTED) {
      this.mSocketStatus = 4;
      sendToIDE$default(this, "Timeline.disconnected", (JSONObject)null, 2, (Object)null);
      ai ai1 = this.mWebSocket;
      if (ai1 != null)
        ai1.b(3999, "client disconnect"); 
      this.mWebSocket = null;
    } 
    return super.handleMessage(paramMessage);
  }
  
  public final boolean isEnableTrace() {
    return (this.mSocketStatus == 3 || this.mSocketStatus == 4) ? false : this.isEnableTrace;
  }
  
  public final boolean isReadySend() {
    return (this.mSocketStatus == 2 || this.mSocketStatus == 3);
  }
  
  public final boolean isThreshold() {
    return (getMStashPointList().size() >= 25);
  }
  
  public final void onAppInfoInited(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
    this.mUrl = paramAppInfoEntity.timelineServerUrl;
    this.isEnableTrace = TextUtils.isEmpty(this.mUrl) ^ true;
    if (this.isEnableTrace) {
      Message message = getMHandler().obtainMessage(MSG_CREATE_CONNECT);
      if (message != null) {
        message.sendToTarget();
        return;
      } 
      return;
    } 
    release();
  }
  
  public final void realSendData(String paramString) {
    l.b(paramString, "content");
    try {
      realSendData(new JSONArray(paramString));
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  public final void realSendData(JSONArray paramJSONArray) {
    l.b(paramJSONArray, "ja");
    sendToIDE("Timeline.send", packData(paramJSONArray));
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final int getMSG_JS_END_COLLECT_POINT() {
      return IDETimeLineSender.MSG_JS_END_COLLECT_POINT;
    }
  }
  
  public static final class IDETimeLineSender$connect$1 extends aj {
    public final void onClosed(ai param1ai, int param1Int, String param1String) {
      l.b(param1ai, "webSocket");
      l.b(param1String, "reason");
      StringBuilder stringBuilder = new StringBuilder("onClosed ");
      stringBuilder.append(param1String);
      AppBrandLogger.d("IDETimeLineReporter", new Object[] { stringBuilder.toString() });
      IDETimeLineSender.this.mWebSocket = null;
    }
    
    public final void onFailure(ai param1ai, Throwable param1Throwable, ae param1ae) {
      l.b(param1ai, "webSocket");
      l.b(param1Throwable, "t");
      AppBrandLogger.e("IDETimeLineReporter", new Object[] { "failure:", param1Throwable, param1ae });
    }
    
    public final void onMessage(ai param1ai, String param1String) {
      Message message;
      l.b(param1ai, "webSocket");
      l.b(param1String, "text");
      String str = (new JSONObject(param1String)).optString("method");
      l.a(str, "jo.optString(\"method\")");
      StringBuilder stringBuilder = new StringBuilder("message ");
      stringBuilder.append(str);
      AppBrandLogger.d("IDETimeLineReporter", new Object[] { stringBuilder.toString() });
      int i = str.hashCode();
      if (i != 12663228) {
        if (i != 1698621513)
          return; 
        if (str.equals("Timeline.disconnect")) {
          message = IDETimeLineSender.this.getMHandler().obtainMessage(IDETimeLineSender.MSG_IDE_DISCONNECT);
          if (message != null) {
            message.sendToTarget();
            return;
          } 
        } 
      } else if (message.equals("Timeline.connected")) {
        message = IDETimeLineSender.this.getMHandler().obtainMessage(IDETimeLineSender.MSG_IDE_CONNECTED);
        if (message != null)
          message.sendToTarget(); 
      } 
    }
    
    public final void onOpen(ai param1ai, ae param1ae) {
      l.b(param1ai, "webSocket");
      l.b(param1ae, "response");
      AppBrandLogger.d("IDETimeLineReporter", new Object[] { "open:", param1ae });
      Message message = IDETimeLineSender.this.getMHandler().obtainMessage(IDETimeLineSender.MSG_SOCKET_OPEN, param1ai);
      if (message != null)
        message.sendToTarget(); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\timeline\IDETimeLineSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */