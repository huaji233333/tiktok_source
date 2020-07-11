package com.tt.miniapp;

import android.net.Uri;
import android.os.SystemClock;
import android.webkit.WebView;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.api.a.a;
import com.bytedance.sandboxapp.protocol.service.api.b.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.b;
import com.tt.frontendapiinterface.i;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.component.nativeview.NativeWebView;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.render.export.TTWebSdkWrapper;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;

public class WebViewManager extends AppbrandServiceManager.ServiceBase {
  private ConcurrentHashMap<Integer, IRender> idToRender = new ConcurrentHashMap<Integer, IRender>();
  
  private ConcurrentHashMap<Integer, Boolean> isNotFirstPublishForWebView = new ConcurrentHashMap<Integer, Boolean>();
  
  IRender mCurrentRender;
  
  private IFeedback mIFeedback;
  
  private ConcurrentHashMap<Integer, WeakReference<NativeWebView>> webComponentMap = new ConcurrentHashMap<Integer, WeakReference<NativeWebView>>();
  
  private WebViewManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  public void addRender(IRender paramIRender) {
    if (paramIRender != null) {
      if (paramIRender.getWebView() == null)
        return; 
      this.idToRender.put(Integer.valueOf(paramIRender.getWebViewId()), paramIRender);
    } 
  }
  
  public void addWebComponent(NativeWebView paramNativeWebView) {
    if (paramNativeWebView != null) {
      if (paramNativeWebView.getWebView() == null)
        return; 
      ConcurrentHashMap<Integer, WeakReference<NativeWebView>> concurrentHashMap = this.webComponentMap;
      if (concurrentHashMap != null)
        concurrentHashMap.put(Integer.valueOf(paramNativeWebView.getWebViewId()), new WeakReference<NativeWebView>(paramNativeWebView)); 
    } 
  }
  
  public void clear() {
    this.idToRender.clear();
    setCurrentRender(null);
  }
  
  public WebView findTargetWebView(int paramInt) {
    ConcurrentHashMap<Integer, IRender> concurrentHashMap1 = this.idToRender;
    if (concurrentHashMap1 != null) {
      IRender iRender = concurrentHashMap1.get(Integer.valueOf(paramInt));
      if (iRender != null)
        return iRender.getWebView(); 
    } 
    ConcurrentHashMap<Integer, WeakReference<NativeWebView>> concurrentHashMap = this.webComponentMap;
    if (concurrentHashMap != null) {
      WeakReference<NativeWebView> weakReference = concurrentHashMap.get(Integer.valueOf(paramInt));
      if (weakReference != null && weakReference.get() != null)
        return ((NativeWebView)weakReference.get()).getWebView(); 
    } 
    return null;
  }
  
  public IRender getCurrentIRender() {
    return this.mCurrentRender;
  }
  
  public IRender getRender(int paramInt) {
    return this.idToRender.get(Integer.valueOf(paramInt));
  }
  
  public void invokeHandler(final int webviewId, final int callbackId, final String msg) {
    AppBrandLogger.d("tma_WebViewManager", new Object[] { "invokeHandler webviewId ", Integer.valueOf(webviewId), " callbackId ", Integer.valueOf(callbackId), " msg ", msg });
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            WebView webView = WebViewManager.this.findTargetWebView(webviewId);
            if (webView != null) {
              StringBuilder stringBuilder1 = new StringBuilder("ttJSBridge.invokeHandler('");
              stringBuilder1.append(callbackId);
              stringBuilder1.append("',");
              stringBuilder1.append(msg);
              stringBuilder1.append(")");
              String str = stringBuilder1.toString();
              StringBuilder stringBuilder2 = new StringBuilder("javascript:");
              stringBuilder2.append(Uri.encode(str));
              _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder2.toString());
            } 
          }
          
          class null {}
        });
  }
  
  public void libraInvokeHandler(final int webviewId, final int callbackId, final String msg, final int divId) {
    AppBrandLogger.d("tma_WebViewManager", new Object[] { "libraInvokeHandler webviewId ", Integer.valueOf(webviewId), " callbackId ", Integer.valueOf(callbackId), " msg ", msg, "divId ", Integer.valueOf(divId) });
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            WebView webView = WebViewManager.this.findTargetWebView(webviewId);
            if (webView != null) {
              StringBuilder stringBuilder1 = new StringBuilder("window.libraInvokeHandler('");
              stringBuilder1.append(callbackId);
              stringBuilder1.append("',");
              stringBuilder1.append(msg);
              stringBuilder1.append(",");
              stringBuilder1.append(divId);
              stringBuilder1.append(")");
              String str = stringBuilder1.toString();
              AppBrandLogger.d("tma_WebViewManager", new Object[] { str });
              StringBuilder stringBuilder2 = new StringBuilder("javascript:");
              stringBuilder2.append(Uri.encode(str));
              _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder2.toString());
            } 
          }
          
          class null {}
        });
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_INSTALL_SUCCESS})
  public void onAppInstallSuccess() {
    for (IRender iRender : this.idToRender.values()) {
      if (iRender instanceof AppbrandSinglePage)
        ((AppbrandSinglePage)iRender).onAppInstallSuccess(); 
    } 
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_START_LAUNCHING})
  public void onAppStartLaunch() {
    for (IRender iRender : this.idToRender.values()) {
      if (iRender instanceof AppbrandSinglePage)
        ((AppbrandSinglePage)iRender).onAppStartLaunching(); 
    } 
  }
  
  public void publish(int paramInt, final String event, final String msg) {
    AppBrandLogger.d("tma_WebViewManager", new Object[] { "publish webviewId ", Integer.valueOf(paramInt), " event ", event, " msg ", msg });
    IFeedback iFeedback = this.mIFeedback;
    if (iFeedback != null)
      iFeedback.onPublish(paramInt, event, msg); 
    if ((DebugManager.getInst()).mRemoteDebugEnable)
      DebugManager.getInst().addAppData(msg, paramInt); 
    Boolean bool = this.isNotFirstPublishForWebView.putIfAbsent(Integer.valueOf(paramInt), Boolean.TRUE);
    final WebView targetWebView = findTargetWebView(paramInt);
    if (webView != null) {
      Runnable runnable = new Runnable() {
          final long beforePost = SystemClock.elapsedRealtime();
          
          public void run() {
            StringBuilder stringBuilder = new StringBuilder("ttJSBridge.subscribeHandler('");
            stringBuilder.append(event);
            stringBuilder.append("',");
            stringBuilder.append(msg);
            stringBuilder.append(")");
            String str = stringBuilder.toString();
            AppBrandLogger.d("tma_WebViewManager", new Object[] { "publish ", str, " , ", this.val$targetWebView });
            long l = SystemClock.elapsedRealtime();
            ((AutoTestManager)WebViewManager.this.mApp.<AutoTestManager>getService(AutoTestManager.class)).addEventWithValue("publishUseTime", Long.valueOf(l - this.beforePost));
            AppBrandLogger.i("tma_WebViewManager", new Object[] { "publish use time:", Long.valueOf(l - this.beforePost) });
            targetWebView.evaluateJavascript(str, null);
          }
        };
      if (webView instanceof com.tt.miniapp.view.webcore.TTWebViewSupportWebView && TTWebSdkWrapper.INSTANCE.isTTWebView()) {
        runnable.run();
        return;
      } 
      if (bool == Boolean.TRUE) {
        AppbrandContext.mainHandler.post(runnable);
        return;
      } 
      AppbrandContext.mainHandler.postAtFrontOfQueue(runnable);
      return;
    } 
    AppBrandLogger.e("tma_WebViewManager", new Object[] { "publish webview not found:", Integer.valueOf(paramInt) });
  }
  
  public void publishDirectly(int paramInt, String paramString1, String paramString2) {
    WebView webView = findTargetWebView(paramInt);
    if (webView != null) {
      StringBuilder stringBuilder2 = new StringBuilder("ttJSBridge.subscribeHandler('");
      stringBuilder2.append(paramString1);
      stringBuilder2.append("',");
      stringBuilder2.append(paramString2);
      stringBuilder2.append(")");
      paramString1 = stringBuilder2.toString();
      StringBuilder stringBuilder1 = new StringBuilder("javascript:");
      stringBuilder1.append(Uri.encode(paramString1));
      _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder1.toString());
    } 
  }
  
  public void registerFeedback(IFeedback paramIFeedback) {
    this.mIFeedback = paramIFeedback;
  }
  
  public void removeRender(int paramInt) {
    this.idToRender.remove(Integer.valueOf(paramInt));
  }
  
  public void setCurrentRender(IRender paramIRender) {
    this.mCurrentRender = paramIRender;
  }
  
  public void unRegisterFeedback() {
    this.mIFeedback = null;
  }
  
  public void webViewinvokeHandler(final int webviewId, final int callbackId, final String msg, final int divId) {
    AppBrandLogger.d("tma_WebViewManager", new Object[] { "invokeHandler webviewId ", Integer.valueOf(webviewId), " callbackId ", Integer.valueOf(callbackId), " msg ", msg, "divId ", Integer.valueOf(divId) });
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            WebView webView = WebViewManager.this.findTargetWebView(webviewId);
            if (webView != null) {
              StringBuilder stringBuilder1 = new StringBuilder("ttJScoreLibra.invokeHandler('");
              stringBuilder1.append(callbackId);
              stringBuilder1.append("',");
              stringBuilder1.append(msg);
              stringBuilder1.append(",");
              stringBuilder1.append(divId);
              stringBuilder1.append(")");
              String str = stringBuilder1.toString();
              AppBrandLogger.d("tma_WebViewManager", new Object[] { str });
              StringBuilder stringBuilder2 = new StringBuilder("javascript:");
              stringBuilder2.append(Uri.encode(str));
              _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder2.toString());
            } 
          }
          
          class null {}
        });
  }
  
  public void webViewsubscribeHandler(final int webviewId, final int callbackId, final String msg, final int divId) {
    StringBuilder stringBuilder = new StringBuilder(" divId ");
    stringBuilder.append(divId);
    AppBrandLogger.d("tma_WebViewManager", new Object[] { "invokeHandler webviewId ", Integer.valueOf(webviewId), " callbackId ", Integer.valueOf(callbackId), " msg ", msg, stringBuilder.toString() });
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            WebView webView = WebViewManager.this.findTargetWebView(webviewId);
            if (webView != null) {
              StringBuilder stringBuilder1 = new StringBuilder("ttJScoreLibra.subscribeHandler('");
              stringBuilder1.append(callbackId);
              stringBuilder1.append("',");
              stringBuilder1.append(msg);
              stringBuilder1.append(",");
              stringBuilder1.append(divId);
              stringBuilder1.append(")");
              String str = stringBuilder1.toString();
              AppBrandLogger.d("tma_WebViewManager", new Object[] { str });
              StringBuilder stringBuilder2 = new StringBuilder("javascript:");
              stringBuilder2.append(Uri.encode(str));
              _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder2.toString());
            } 
          }
          
          class null {}
        });
  }
  
  public void workerInvokeHandler(final int webviewId, final int callbackId, final String msg, final int divId) {
    AppBrandLogger.d("tma_WebViewManager", new Object[] { "workerInvokeHandler webviewId ", Integer.valueOf(webviewId), " callbackId ", Integer.valueOf(callbackId), " msg ", msg, " divId ", Integer.valueOf(divId) });
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            AppBrandLogger.d("tma_WebViewManager", new Object[] { "workerInvokeHandler", "in post doing" });
            WebView webView = WebViewManager.this.findTargetWebView(webviewId);
            if (webView != null) {
              StringBuilder stringBuilder1 = new StringBuilder("window.workerInvokeHandler('");
              stringBuilder1.append(callbackId);
              stringBuilder1.append("',");
              stringBuilder1.append(msg);
              stringBuilder1.append(",");
              stringBuilder1.append(divId);
              stringBuilder1.append(")");
              String str = stringBuilder1.toString();
              AppBrandLogger.d("tma_WebViewManager", new Object[] { str });
              StringBuilder stringBuilder2 = new StringBuilder("javascript:");
              stringBuilder2.append(Uri.encode(str));
              _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder2.toString());
              return;
            } 
            AppBrandLogger.d("tma_WebViewManager", new Object[] { "findTargetWebView null!!!!" });
          }
          
          class null {}
        });
  }
  
  public void workerSubcribeHandler(final int webviewId, final int callbackId, final String msg, int paramInt3) {
    AppBrandLogger.d("tma_WebViewManager", new Object[] { "workerSubcribeHandler webviewId ", Integer.valueOf(webviewId), " callbackId ", Integer.valueOf(callbackId), " msg ", msg, " divId ", Integer.valueOf(paramInt3) });
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            WebView webView = WebViewManager.this.findTargetWebView(webviewId);
            if (webView != null) {
              StringBuilder stringBuilder1 = new StringBuilder("window.workerSubcribeHandler('");
              stringBuilder1.append(callbackId);
              stringBuilder1.append("',");
              stringBuilder1.append(msg);
              stringBuilder1.append(")");
              String str = stringBuilder1.toString();
              AppBrandLogger.d("tma_WebViewManager", new Object[] { str });
              StringBuilder stringBuilder2 = new StringBuilder("javascript:");
              stringBuilder2.append(Uri.encode(str));
              _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, stringBuilder2.toString());
            } 
          }
          
          class null {}
        });
  }
  
  public static interface IFeedback {
    void onPublish(int param1Int, String param1String1, String param1String2);
  }
  
  public static interface IRender extends i {}
  
  public static class WebViewApiRuntime implements a {
    private boolean mDestroyed;
    
    private int mWebViewId;
    
    public WebViewApiRuntime(int param1Int) {
      this.mWebViewId = param1Int;
    }
    
    public void destroy() {
      this.mDestroyed = true;
    }
    
    public a getContext() {
      return (a)AppbrandApplicationImpl.getInst().getMiniAppContext();
    }
    
    public b handleApiInvoke(a param1a) {
      AppbrandApplication.getInst().publish(this.mWebViewId, param1a.b, param1a.a().toString());
      return b.d;
    }
    
    public boolean isDestroyed() {
      return this.mDestroyed;
    }
  }
  
  public static class WebViewAsyncApiCallbackExecutor implements b {
    private int mCallbackId;
    
    private int mWebViewId;
    
    public WebViewAsyncApiCallbackExecutor(int param1Int1, int param1Int2) {
      this.mWebViewId = param1Int1;
      this.mCallbackId = param1Int2;
    }
    
    public void executeCallback(ApiCallbackData param1ApiCallbackData) {
      WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
      if (webViewManager != null)
        webViewManager.invokeHandler(this.mWebViewId, this.mCallbackId, param1ApiCallbackData.toString()); 
    }
  }
  
  class WebViewManager {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\WebViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */