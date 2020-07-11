package com.tt.miniapp.webapp;

import android.os.Handler;
import android.os.HandlerThread;
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
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.msg.ApiGetSystemInfoCtrl;
import com.tt.miniapp.msg.ApiHideToastCtrl;
import com.tt.miniapp.msg.ApiShowToastCtrl;
import com.tt.miniapp.msg.MiniAppApiInvokeParam;
import com.tt.miniapp.msg.sync.ApiReportAnalyticsCtrl;
import com.tt.miniapp.webapp.api.async.ApiDisablePopGesture;
import com.tt.miniapp.webapp.api.async.ApiEndEditing;
import com.tt.miniapp.webapp.api.async.ApiReportCustomEvent;
import com.tt.miniapp.webapp.api.async.ApiSetStatusBarStyle;
import com.tt.miniapp.webapp.api.sync.ApiGetLibraAPIListSync;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.option.e.e;
import java.lang.ref.WeakReference;

public class WebAppBridge {
  Handler handler;
  
  HandlerThread handlerThread;
  
  private final a mSandboxAppApiRuntime;
  
  private c mWebAppApiHandleExecutor = new c() {
      public void scheduleHandle(Runnable param1Runnable) {
        WebAppBridge.this.handler.post(param1Runnable);
      }
    };
  
  private final a mWebAppApiRuntime;
  
  private int mWebViewId;
  
  public WebAppBridge(int paramInt) {
    this.mWebViewId = paramInt;
    this.handlerThread = new HandlerThread("WebAppBridge");
    this.handlerThread.start();
    this.handler = new Handler(this.handlerThread.getLooper());
    this.mSandboxAppApiRuntime = ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).a();
    this.mWebAppApiRuntime = new WebAppApiRuntime(paramInt);
  }
  
  public void callbackDispatch(int paramInt1, String paramString1, int paramInt2, String paramString2) {
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager != null) {
      if (paramString2.equals("webview")) {
        webViewManager.webViewinvokeHandler(this.mWebViewId, paramInt1, paramString1, paramInt2);
        return;
      } 
      if (paramString2.equals("worker")) {
        webViewManager.workerInvokeHandler(this.mWebViewId, paramInt1, paramString1, paramInt2);
        return;
      } 
      if (paramString2.equals("libra"))
        webViewManager.libraInvokeHandler(this.mWebViewId, paramInt1, paramString1, paramInt2); 
    } 
  }
  
  @JavascriptInterface
  public String invoke(String paramString1, final String param, final int callbackId, final int divId, final String type) {
    if (paramString1 == null)
      return CharacterUtils.empty(); 
    AppBrandLogger.d("WebAppBridge", new Object[] { "invoke event ", paramString1, " param ", param, " callbackId ", Integer.valueOf(callbackId), " divid ", Integer.valueOf(divId), " type ", type });
    byte b = -1;
    int i = paramString1.hashCode();
    if (i != -1816397254) {
      if (i != -516489038) {
        if (i == 332589195 && paramString1.equals("openSchema"))
          b = 2; 
      } else if (paramString1.equals("reportAnalytics")) {
        b = 1;
      } 
    } else if (paramString1.equals("getLibraAPIList")) {
      b = 0;
    } 
    if (b != 0) {
      final ApiCallbackData event;
      if (b != 1) {
        if (b == 2) {
          b b1 = this.mSandboxAppApiRuntime.handleApiInvoke(a.b.a(this.mWebAppApiRuntime, paramString1, (a)new MiniAppApiInvokeParam(param)).a(this.mWebAppApiHandleExecutor, new WebAppAsyncApiCallbackExecutor(callbackId, divId, type)).a());
          if (b1.a) {
            apiCallbackData = b1.b;
            return (apiCallbackData == null) ? CharacterUtils.empty() : apiCallbackData.toString();
          } 
          DebugUtil.logOrThrow("not trigger api handle", new Object[0]);
        } 
        this.handler.post(new Runnable() {
              public void run() {
                WebAppBridge.this.runJsApiAsync(event, param, callbackId, divId, type);
              }
            });
        return CharacterUtils.empty();
      } 
      return (new ApiReportAnalyticsCtrl((String)apiCallbackData, param)).act();
    } 
    return (new ApiGetLibraAPIListSync(param)).act();
  }
  
  @JavascriptInterface
  public void onDocumentReady(String paramString) {
    AppBrandLogger.d("WebAppBridge", new Object[] { "onDocumentReady" });
    WeakReference<TTWebAppViewWindow> weakReference = TTWebAppViewWindow.getWeakRef();
    if (weakReference == null)
      return; 
    TTWebAppViewWindow tTWebAppViewWindow = weakReference.get();
    if (tTWebAppViewWindow == null)
      return; 
    tTWebAppViewWindow.hideLoading();
  }
  
  @JavascriptInterface
  public String publish(String paramString1, String paramString2, int paramInt, String paramString3) {
    AppBrandLogger.d("WebAppBridge", new Object[] { " publish event ", paramString1, " param ", paramString2, " divid ", Integer.valueOf(paramInt), "  type ", paramString3 });
    AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore(paramString1, paramString2, paramInt);
    return null;
  }
  
  public void runJsApiAsync(String paramString1, String paramString2, int paramInt1, final int divId, final String type) {
    byte b;
    switch (paramString1.hashCode()) {
      default:
        b = -1;
        break;
      case 2104007794:
        if (paramString1.equals("setStatusBarStyle")) {
          b = 4;
          break;
        } 
      case 843366917:
        if (paramString1.equals("hideToast")) {
          b = 1;
          break;
        } 
      case 344806259:
        if (paramString1.equals("getSystemInfo")) {
          b = 6;
          break;
        } 
      case -734314539:
        if (paramString1.equals("reportCustomEvent")) {
          b = 2;
          break;
        } 
      case -1224756544:
        if (paramString1.equals("disablePopGesture")) {
          b = 3;
          break;
        } 
      case -1660458947:
        if (paramString1.equals("endEditing")) {
          b = 5;
          break;
        } 
      case -1913642710:
        if (paramString1.equals("showToast")) {
          b = 0;
          break;
        } 
    } 
    switch (b) {
      default:
        return;
      case 6:
        (new ApiGetSystemInfoCtrl(paramString2, paramInt1, new e() {
              public void callback(int param1Int, String param1String) {
                WebAppBridge.this.callbackDispatch(param1Int, param1String, divId, type);
              }
            })).doAct();
        return;
      case 5:
        (new ApiEndEditing(paramString2, paramInt1, new e() {
              public void callback(int param1Int, String param1String) {
                WebAppBridge.this.callbackDispatch(param1Int, param1String, divId, type);
              }
            })).doAct();
        return;
      case 4:
        (new ApiSetStatusBarStyle(paramString2, paramInt1, new e() {
              public void callback(int param1Int, String param1String) {
                WebAppBridge.this.callbackDispatch(param1Int, param1String, divId, type);
              }
            })).doAct();
        return;
      case 3:
        (new ApiDisablePopGesture(paramString2, paramInt1, new e() {
              public void callback(int param1Int, String param1String) {
                WebAppBridge.this.callbackDispatch(param1Int, param1String, divId, type);
              }
            })).doAct();
        return;
      case 2:
        (new ApiReportCustomEvent(paramString2, paramInt1, new e() {
              public void callback(int param1Int, String param1String) {
                WebAppBridge.this.callbackDispatch(param1Int, param1String, divId, type);
              }
            })).doAct();
        return;
      case 1:
        (new ApiHideToastCtrl(paramString2, paramInt1, new e() {
              public void callback(int param1Int, String param1String) {
                WebAppBridge.this.callbackDispatch(param1Int, param1String, divId, type);
              }
            })).doAct();
        return;
      case 0:
        break;
    } 
    (new ApiShowToastCtrl(paramString2, paramInt1, new e() {
          public void callback(int param1Int, String param1String) {
            WebAppBridge.this.callbackDispatch(param1Int, param1String, divId, type);
          }
        })).doAct();
  }
  
  public static class WebAppApiRuntime implements a {
    public WebAppApiRuntime(int param1Int) {}
    
    public a getContext() {
      return (a)AppbrandApplicationImpl.getInst().getMiniAppContext();
    }
    
    public b handleApiInvoke(a param1a) {
      return b.c;
    }
    
    public boolean isDestroyed() {
      return false;
    }
  }
  
  class WebAppAsyncApiCallbackExecutor implements b {
    private final int mCallbackId;
    
    private final int mDivId;
    
    private final String mType;
    
    private WebAppAsyncApiCallbackExecutor(int param1Int1, int param1Int2, String param1String) {
      this.mCallbackId = param1Int1;
      this.mDivId = param1Int2;
      this.mType = param1String;
    }
    
    public void executeCallback(ApiCallbackData param1ApiCallbackData) {
      WebAppBridge.this.callbackDispatch(this.mCallbackId, param1ApiCallbackData.toString(), this.mDivId, this.mType);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\WebAppBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */