package com.tt.miniapp.extraWeb;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapp.webbridge.WebBridge;
import com.tt.option.n.c;

public class ComponentWebViewRender implements WebViewManager.IRender {
  private AppbrandApplicationImpl mApp;
  
  private WebView mComponentWebView;
  
  private int mComponentWebViewId;
  
  private WebBridge mWebBridge;
  
  public ComponentWebViewRender(AppbrandApplicationImpl paramAppbrandApplicationImpl, WebView paramWebView, int paramInt) {
    this.mApp = paramAppbrandApplicationImpl;
    this.mComponentWebView = paramWebView;
    this.mComponentWebViewId = paramInt;
  }
  
  public void destroyWebBridge() {
    WebBridge webBridge = this.mWebBridge;
    if (webBridge != null)
      webBridge.destroy(); 
  }
  
  public Activity getCurrentActivity() {
    return null;
  }
  
  public c getFileChooseHandler() {
    return null;
  }
  
  public NativeNestWebView getNativeNestWebView() {
    return null;
  }
  
  public NativeViewManager getNativeViewManager() {
    return null;
  }
  
  public String getPage() {
    return null;
  }
  
  public int getRenderHeight() {
    return 0;
  }
  
  public int getRenderWidth() {
    return 0;
  }
  
  public View getRootView() {
    return null;
  }
  
  public int getTitleBarHeight() {
    return 0;
  }
  
  public WebView getWebView() {
    return this.mComponentWebView;
  }
  
  public int getWebViewId() {
    return this.mComponentWebViewId;
  }
  
  public void hideNavigationBarHomeButton() {}
  
  public void onNativeWebViewPageFinished(boolean paramBoolean) {}
  
  public void onStartPullDownRefresh() {}
  
  public void onStopPullDownRefresh() {}
  
  public boolean pullDownRefreshEnabled() {
    return false;
  }
  
  public void setBridge(WebBridge paramWebBridge) {
    this.mWebBridge = paramWebBridge;
  }
  
  public void setLoadAsWebView() {}
  
  public void setNavigationBarColor(String paramString1, String paramString2) {}
  
  public void setNavigationBarLoading(boolean paramBoolean) {}
  
  public void setNavigationBarTitle(String paramString) {}
  
  public void showKeyboard(int paramInt) {}
  
  public void updateWebTitle(String paramString, boolean paramBoolean) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\extraWeb\ComponentWebViewRender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */