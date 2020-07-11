package com.tt.miniapp.component.nativeview.bridge;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.api.a;
import com.bytedance.sandboxapp.protocol.service.api.a.a;
import com.bytedance.sandboxapp.protocol.service.api.b.a;
import com.bytedance.sandboxapp.protocol.service.api.b.b;
import com.bytedance.sandboxapp.protocol.service.api.b.c;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.b;
import com.storage.async.Action;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeWebView;
import com.tt.miniapp.msg.MiniAppApiInvokeParam;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.webbridge.WebComponentBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.option.e.e;
import org.json.JSONObject;

public class AdWebBridge extends WebComponentBridge {
  private a mApiRuntime = ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).a();
  
  private c mAsyncApiHandleExecutor = new c() {
      public void scheduleHandle(final Runnable asyncApiHandleRunnable) {
        ThreadUtil.runOnWorkThread(new Action() {
              public void act() {
                asyncApiHandleRunnable.run();
              }
            },  ThreadPools.defaults());
      }
    };
  
  private a mJSCoreApiRuntime = new a() {
      public a getContext() {
        return (a)AppbrandApplicationImpl.getInst().getMiniAppContext();
      }
      
      public b handleApiInvoke(a param1a) {
        AdWebBridge.this.publishWebView(param1a.b, param1a.a().toString());
        return b.d;
      }
      
      public boolean isDestroyed() {
        return false;
      }
    };
  
  public AdWebBridge(NativeWebView paramNativeWebView) {
    super(paramNativeWebView);
  }
  
  private void monitorInvokeApiFailed(String paramString1, String paramString2, String paramString3) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("eventName", paramString1);
      jSONObject.put("invokeMethodName", paramString2);
      jSONObject.put("errorMessage", paramString3);
      AppBrandMonitor.statusRate("mp_invoke_api_failed", 7000, jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("AdWebBridge", new Object[] { exception });
      return;
    } 
  }
  
  public void callbackWebView(String paramString1, int paramInt, String paramString2) {
    if (!TextUtils.isEmpty(paramString2) && paramString2.contains(":fail")) {
      AppBrandLogger.e("AdWebBridge", new Object[] { "******************callbackWebView callbackID ", Integer.valueOf(paramInt), "msg:", paramString2, new Throwable() });
      monitorInvokeApiFailed(paramString1, "callbackWebView", paramString2);
    } else {
      AppBrandLogger.d("AdWebBridge", new Object[] { "callbackWebView callbackID ", Integer.valueOf(paramInt), "msg:", paramString2 });
    } 
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager != null)
      webViewManager.invokeHandler(this.mWebViewId, paramInt, paramString2); 
  }
  
  @JavascriptInterface
  public String invoke(final String event, String paramString2, final int callbackId) {
    a a1;
    b b;
    final String param;
    if (event == null)
      return CharacterUtils.empty(); 
    byte b1 = 6;
    AppBrandLogger.d("AdWebBridge", new Object[] { "invoke event ", event, " param ", paramString2, " callbackId ", Integer.valueOf(callbackId) });
    switch (event.hashCode()) {
      default:
        b1 = -1;
        break;
      case 2050035697:
        if (event.equals("cancelDxppAd")) {
          b1 = 14;
          break;
        } 
      case 1472294650:
        if (event.equals("subscribeAppAd")) {
          b1 = 12;
          break;
        } 
      case 1321118366:
        if (event.equals("makePhoneCall")) {
          b1 = 4;
          break;
        } 
      case 1140159543:
        if (event.equals("_serviceGetPhoneNumber")) {
          b1 = 1;
          break;
        } 
      case 213265471:
        if (event.equals("getAdSiteBaseInfo")) {
          b1 = 3;
          break;
        } 
      case 196312531:
        if (event.equals("unsubscribeAppAd")) {
          b1 = 15;
          break;
        } 
      case 103149417:
        if (event.equals("login")) {
          b1 = 2;
          break;
        } 
      case 94388255:
        if (event.equals("openLocation")) {
          b1 = 8;
          break;
        } 
      case -316023509:
        if (event.equals("getLocation")) {
          b1 = 7;
          break;
        } 
      case -617633438:
        if (event.equals("getLocalPhoneNumber")) {
          b1 = 5;
          break;
        } 
      case -650072649:
        if (event.equals("getLocalPhoneNumberToken"))
          break; 
      case -732466452:
        if (event.equals("chooseLocation")) {
          b1 = 11;
          break;
        } 
      case -891002358:
        if (event.equals("scanCode")) {
          b1 = 9;
          break;
        } 
      case -1023873614:
        if (event.equals("openAdLandPageLinks")) {
          b1 = 16;
          break;
        } 
      case -1317783337:
        if (event.equals("dxppAd")) {
          b1 = 13;
          break;
        } 
      case -1337695621:
        if (event.equals("_webviewGetPhoneNumber")) {
          b1 = 0;
          break;
        } 
      case -1925380643:
        if (event.equals("chooseAddress")) {
          b1 = 10;
          break;
        } 
    } 
    switch (b1) {
      default:
        AppBrandLogger.d("AdWebBridge", new Object[] { "unsupported API" });
        paramString2 = ApiCallResult.a.a(event, "api is not exist", 0).toString();
        callbackWebView(event, callbackId, paramString2);
        AppBrandLogger.d("AdWebBridge", new Object[] { paramString2 });
        return CharacterUtils.empty();
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
        a1 = a.b.a(this.mJSCoreApiRuntime, event, (a)new MiniAppApiInvokeParam(paramString2)).a(this.mAsyncApiHandleExecutor, new JSCoreAsyncApiCallbackExecutor(event, callbackId)).a();
        b = this.mApiRuntime.handleApiInvoke(a1);
        if (b.a) {
          ApiCallbackData apiCallbackData = b.b;
          if (apiCallbackData == null) {
            AppBrandLogger.d("AdWebBridge", new Object[] { "ApiService handle asyncEvent:", event });
            return CharacterUtils.empty();
          } 
          str = apiCallbackData.toString();
          AppBrandLogger.d("AdWebBridge", new Object[] { "ApiService handle syncEvent:", event, "result:", str });
          return str;
        } 
        return CharacterUtils.empty();
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
        break;
    } 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            try {
              return;
            } finally {
              Exception exception = null;
              AdWebBridge adWebBridge = AdWebBridge.this;
              String str = event;
              adWebBridge.callbackWebView(str, callbackId, ApiCallResult.a.b(str).d(a.a(exception)).toString());
              AppBrandLogger.e("AdWebBridge", new Object[] { "handleInvoke ", exception });
            } 
          }
        }ThreadPools.defaults());
    return CharacterUtils.empty();
  }
  
  public void publishWebView(String paramString1, String paramString2) {
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager != null)
      webViewManager.publish(this.mWebViewId, paramString1, paramString2); 
  }
  
  class JSCoreAsyncApiCallbackExecutor implements b {
    private int mCallbackId;
    
    private String mEvent;
    
    public JSCoreAsyncApiCallbackExecutor(String param1String, int param1Int) {
      this.mEvent = param1String;
      this.mCallbackId = param1Int;
    }
    
    public void executeCallback(ApiCallbackData param1ApiCallbackData) {
      if (DebugUtil.debug())
        AppBrandLogger.d("AdWebBridge", new Object[] { "ApiService async callback:", param1ApiCallbackData.toString() }); 
      AdWebBridge.this.callbackWebView(this.mEvent, this.mCallbackId, param1ApiCallbackData.toString());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\bridge\AdWebBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */