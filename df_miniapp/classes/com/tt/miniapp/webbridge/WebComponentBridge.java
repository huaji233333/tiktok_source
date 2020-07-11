package com.tt.miniapp.webbridge;

import android.webkit.JavascriptInterface;
import com.tt.miniapp.component.nativeview.NativeWebView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import java.lang.ref.SoftReference;

public class WebComponentBridge {
  private SoftReference<NativeWebView> mSoftWebView;
  
  protected int mWebViewId;
  
  public WebComponentBridge(NativeWebView paramNativeWebView) {
    if (paramNativeWebView != null) {
      this.mWebViewId = paramNativeWebView.getWebViewId();
      this.mSoftWebView = new SoftReference<NativeWebView>(paramNativeWebView);
    } 
  }
  
  @JavascriptInterface
  public String invoke(String paramString) {
    AppBrandLogger.d("WebComponentBridge", new Object[] { "invoke native webView event---", paramString });
    SoftReference<NativeWebView> softReference = this.mSoftWebView;
    if (softReference != null && softReference.get() != null) {
      NativeWebView nativeWebView = this.mSoftWebView.get();
      if ("reload".equals(paramString))
        nativeWebView.reloadErrorUrl(); 
    } 
    return "";
  }
  
  @JavascriptInterface
  public String publish(String paramString1, String paramString2, int[] paramArrayOfint) {
    AppBrandLogger.d("WebComponentBridge", new Object[] { " publish event ", paramString1, " param ", paramString2, " webviewIds ", paramArrayOfint });
    AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore(paramString1, paramString2, this.mWebViewId);
    return null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\WebComponentBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */