package com.tt.miniapp.extraWeb.bridge;

import com.bytedance.sandboxapp.protocol.service.api.a;
import com.bytedance.sandboxapp.protocol.service.api.a.a;
import com.bytedance.sandboxapp.protocol.service.api.b.a;
import com.bytedance.sandboxapp.protocol.service.api.b.b;
import com.bytedance.sandboxapp.protocol.service.api.b.c;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.extraWeb.ComponentWebViewRender;
import com.tt.miniapp.jsbridge.JsBridge;
import com.tt.miniapp.msg.ApiCallHostMethodCtrl;
import com.tt.miniapp.msg.ApiInvokeCtrl;
import com.tt.miniapp.msg.MiniAppApiInvokeParam;
import com.tt.miniapp.webbridge.WebBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.e;

public class ModalWebBridge extends WebBridge {
  private static c mWebViewApiHandleExecutor = new c() {
      public final void scheduleHandle(Runnable param1Runnable) {
        param1Runnable.run();
      }
    };
  
  private final a mSandboxAppApiRuntime;
  
  private final WebViewManager.WebViewApiRuntime mWebViewApiRuntime;
  
  private int mWebViewId;
  
  public ModalWebBridge(AppbrandApplicationImpl paramAppbrandApplicationImpl, ComponentWebViewRender paramComponentWebViewRender, int paramInt) {
    super(paramAppbrandApplicationImpl, (WebViewManager.IRender)paramComponentWebViewRender);
    this.mWebViewId = paramInt;
    this.mSandboxAppApiRuntime = ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).a();
    this.mWebViewApiRuntime = new WebViewManager.WebViewApiRuntime(paramInt);
  }
  
  private String handleInvoke(String paramString1, String paramString2, int paramInt) {
    ApiCallbackData apiCallbackData;
    AppBrandLogger.d("ModalWebBridge", new Object[] { "handleInterceptedInvoke event ", paramString1, " param ", paramString2, " callbackId ", Integer.valueOf(paramInt) });
    b b1 = this.mSandboxAppApiRuntime.handleApiInvoke(a.b.a((a)this.mWebViewApiRuntime, paramString1, (a)new MiniAppApiInvokeParam(paramString2)).a(mWebViewApiHandleExecutor, (b)new WebViewManager.WebViewAsyncApiCallbackExecutor(this.mWebViewId, paramInt)).a());
    if (b1.a) {
      apiCallbackData = b1.b;
      return (apiCallbackData == null) ? CharacterUtils.empty() : apiCallbackData.toString();
    } 
    byte b = -1;
    int i = apiCallbackData.hashCode();
    if (i != -2056950218) {
      if (i != -238006873) {
        if (i == 1652140151 && apiCallbackData.equals("requestPayment"))
          b = 0; 
      } else if (apiCallbackData.equals("callHostMethod")) {
        b = 2;
      } 
    } else if (apiCallbackData.equals("requestWXPayment")) {
      b = 1;
    } 
    if (b != 0 && b != 1) {
      if (b != 2)
        return null; 
      (new ApiCallHostMethodCtrl(paramString2, paramInt, new e() {
            public void callback(int param1Int, String param1String) {
              ModalWebBridge.this.callbackWebView(param1Int, param1String);
            }
          })).doAct();
      return CharacterUtils.empty();
    } 
    try {
      (new ApiInvokeCtrl(new JsBridge.NativeApiEvent((String)apiCallbackData, paramString2, paramInt, new e() {
              public void callback(int param1Int, String param1String) {
                ModalWebBridge.this.callbackWebView(param1Int, param1String);
              }
            }))).doAct();
    } catch (Exception exception) {
      AppBrandLogger.e("ModalWebBridge", new Object[] { "handleInterceptedInvoke ", exception });
    } 
    return CharacterUtils.empty();
  }
  
  public void callbackWebView(int paramInt, String paramString) {
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager != null)
      webViewManager.invokeHandler(this.mWebViewId, paramInt, paramString); 
  }
  
  public void destroy() {
    this.mWebViewApiRuntime.destroy();
  }
  
  public WebBridge.InterceptedInvokeResult handleInterceptedInvoke(String paramString1, String paramString2, int paramInt) {
    if (paramString1 == null)
      return null; 
    paramString1 = handleInvoke(paramString1, paramString2, paramInt);
    return (paramString1 == null) ? null : new WebBridge.InterceptedInvokeResult(paramString1);
  }
  
  public boolean interceptInvoke(String paramString) {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\extraWeb\bridge\ModalWebBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */