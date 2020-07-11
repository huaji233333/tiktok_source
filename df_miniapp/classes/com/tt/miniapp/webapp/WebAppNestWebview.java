package com.tt.miniapp.webapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.a.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapp.web.TTWebSetting;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapphost.AppBrandLogger;

public class WebAppNestWebview extends NestWebView {
  private boolean isJsbridgeEnable = true;
  
  private WebAppBridge webAppBridge;
  
  public WebAppNestWebview(Context paramContext) {
    super(paramContext);
    setWebViewClient(c.a(createWebviewClient()));
    setWebChromeClient(new WebChromeClient());
    WebSettings webSettings = getSettings();
    webSettings.setMediaPlaybackRequiresUserGesture(false);
    TTWebSetting tTWebSetting = new TTWebSetting(webSettings);
    tTWebSetting.appendUserAgent("webview/ByteDanceWebAppPlatform");
    tTWebSetting.setDefaultSetting();
    int i = ComponentIDCreator.create();
    this.webAppBridge = new WebAppBridge(i);
    WebApptWebViewRender webApptWebViewRender = new WebApptWebViewRender((WebView)this, i);
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager != null) {
      webViewManager.setCurrentRender(webApptWebViewRender);
      webViewManager.addRender(webApptWebViewRender);
    } 
    addJavascriptInterface(this.webAppBridge, "ttJSCoreLibra");
    this.isJsbridgeEnable = true;
  }
  
  private WebViewClient createWebviewClient() {
    return new WebViewClient() {
        public void onPageFinished(WebView param1WebView, String param1String) {
          super.onPageFinished(param1WebView, param1String);
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "onPageFinished", param1String });
        }
        
        public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
          super.onPageStarted(param1WebView, param1String, param1Bitmap);
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "onPageStarted", param1String });
        }
        
        public void onReceivedError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
          super.onReceivedError(param1WebView, param1Int, param1String1, param1String2);
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "onReceivedError", param1String2, Integer.valueOf(param1Int), param1String1 });
        }
        
        public void onReceivedError(WebView param1WebView, WebResourceRequest param1WebResourceRequest, WebResourceError param1WebResourceError) {
          super.onReceivedError(param1WebView, param1WebResourceRequest, param1WebResourceError);
          if (Build.VERSION.SDK_INT >= 23)
            AppBrandLogger.d("WebAppNestWebview", new Object[] { "onReceivedError", param1WebResourceRequest.getUrl(), Integer.valueOf(param1WebResourceError.getErrorCode()), param1WebResourceError.getDescription() }); 
        }
        
        public void onReceivedHttpAuthRequest(WebView param1WebView, HttpAuthHandler param1HttpAuthHandler, String param1String1, String param1String2) {
          super.onReceivedHttpAuthRequest(param1WebView, param1HttpAuthHandler, param1String1, param1String2);
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "onReceivedHttpAuthRequest" });
        }
        
        public void onReceivedHttpError(WebView param1WebView, WebResourceRequest param1WebResourceRequest, WebResourceResponse param1WebResourceResponse) {
          super.onReceivedHttpError(param1WebView, param1WebResourceRequest, param1WebResourceResponse);
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "onReceivedHttpError", param1WebResourceRequest.getUrl() });
        }
        
        public void onReceivedSslError(WebView param1WebView, SslErrorHandler param1SslErrorHandler, SslError param1SslError) {
          super.onReceivedSslError(param1WebView, param1SslErrorHandler, param1SslError);
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "onReceivedSslError" });
        }
        
        public boolean onRenderProcessGone(WebView param1WebView, RenderProcessGoneDetail param1RenderProcessGoneDetail) {
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "onRenderProcessGone" });
          super.onRenderProcessGone(param1WebView, param1RenderProcessGoneDetail);
          return c.a(param1WebView, param1RenderProcessGoneDetail);
        }
        
        public WebResourceResponse shouldInterceptRequest(WebView param1WebView, WebResourceRequest param1WebResourceRequest) {
          AppBrandLogger.d("WebAppNestWebview", new Object[] { "shouldInterceptRequest", param1WebResourceRequest.getUrl() });
          return _lancet.com_ss_android_ugc_aweme_lancet_WebLancet_shouldInterceptRequest((WebViewClient)this, param1WebView, param1WebResourceRequest);
        }
        
        class null {}
      };
  }
  
  public void disableJsBridge() {
    if (this.isJsbridgeEnable) {
      removeJavascriptInterface("ttJSCoreLibra");
      this.isJsbridgeEnable = false;
    } 
  }
  
  public void enableJsBridge() {
    if (!this.isJsbridgeEnable) {
      addJavascriptInterface(this.webAppBridge, "ttJSCoreLibra");
      this.isJsbridgeEnable = true;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\WebAppNestWebview.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */