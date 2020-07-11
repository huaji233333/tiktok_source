package com.tt.miniapphost.render.export;

import com.bytedance.lynx.webview.TTWebSdk;
import com.bytedance.lynx.webview.glue.IWebViewExtension;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.render.internal.InterfaceConverter;

public final class TTWebSdkWrapper {
  public static final TTWebSdkWrapper INSTANCE = new TTWebSdkWrapper();
  
  private static final boolean isTTWebView;
  
  static {
    boolean bool = false;
    try {
      boolean bool1 = TTWebSdk.isTTWebView();
      bool = bool1;
    } catch (Error error) {
      AppBrandLogger.e("TTWebSdkWrapper", new Object[] { error });
    } 
    isTTWebView = bool;
    if (bool) {
      InterfaceConverter.INSTANCE.checkAndCacheInterface(PerformanceTimingListener.class, IWebViewExtension.PerformanceTimingListener.class);
      InterfaceConverter.INSTANCE.checkAndCacheInterface(PlatformViewLayersScrollListener.class, IWebViewExtension.PlatformViewLayersScrollListener.class);
    } 
  }
  
  public final boolean isTTWebView() {
    return isTTWebView;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\render\export\TTWebSdkWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */