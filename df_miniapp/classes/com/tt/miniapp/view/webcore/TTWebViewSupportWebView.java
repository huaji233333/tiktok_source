package com.tt.miniapp.view.webcore;

import android.content.Context;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.tt.miniapp.monitor.TTWebviewPerformanceTiming;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.render.export.PerformanceTimingListener;
import com.tt.miniapphost.render.export.PlatformViewLayersScrollListener;
import com.tt.miniapphost.render.export.TTWebSdkWrapper;
import com.tt.miniapphost.render.export.TTWebViewExtensionWrapper;

public class TTWebViewSupportWebView extends WebView implements PlatformViewLayersScrollListener {
  private static Boolean sMultiThreadEvalJsEnabled;
  
  private static Boolean sRenderInBrowserEnabled;
  
  private OnScrollListener mScrollerListener;
  
  private TTWebviewPerformanceTiming mTTWebviewPerf;
  
  private TTWebViewExtensionWrapper mWebViewExtension;
  
  public TTWebViewSupportWebView(Context paramContext) {
    super(paramContext);
    init(paramContext);
  }
  
  public static boolean hasSetRenderInBrowser() {
    return (sRenderInBrowserEnabled != null);
  }
  
  private void init(Context paramContext) {
    if (TTWebSdkWrapper.INSTANCE.isTTWebView()) {
      this.mWebViewExtension = new TTWebViewExtensionWrapper(this);
      if (sRenderInBrowserEnabled == null) {
        sRenderInBrowserEnabled = Boolean.valueOf(isTTRenderInBrowserEnabled(paramContext, this.mWebViewExtension));
        this.mTTWebviewPerf = new TTWebviewPerformanceTiming(this);
        this.mWebViewExtension.setPerformanceTimingListener((PerformanceTimingListener)this.mTTWebviewPerf);
      } 
      if (isRenderInBrowserEnabled()) {
        this.mWebViewExtension.setPlatformViewLayersScrollListener(this);
        return;
      } 
    } else if (sRenderInBrowserEnabled == null) {
      sRenderInBrowserEnabled = Boolean.valueOf(false);
    } 
  }
  
  public static boolean isRenderInBrowserEnabled() {
    Boolean bool = sRenderInBrowserEnabled;
    return (bool != null && bool.booleanValue());
  }
  
  private boolean isTTRenderInBrowserEnabled(Context paramContext, TTWebViewExtensionWrapper paramTTWebViewExtensionWrapper) {
    return (TTWebSdkWrapper.INSTANCE.isTTWebView() && paramTTWebViewExtensionWrapper != null && paramTTWebViewExtensionWrapper.isTTRenderEnabled() && SettingsDAO.getInt(paramContext, 0, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.TT_RENDER_IN_BROWSER }) == 1);
  }
  
  public void destroy() {
    super.destroy();
    this.mWebViewExtension = null;
  }
  
  public void evaluateJavascript(final String script, final ValueCallback<String> callback) {
    Boolean bool = sMultiThreadEvalJsEnabled;
    boolean bool2 = false;
    if (bool == null) {
      Context context = getContext();
      Settings settings = Settings.TT_TMA_SWITCH;
      Settings.TmaSwitch tmaSwitch = Settings.TmaSwitch.ENABLE_MULTI_THREAD_EJS;
      boolean bool3 = true;
      if (SettingsDAO.getInt(context, 0, new Enum[] { (Enum)settings, (Enum)tmaSwitch }) != 1)
        bool3 = false; 
      sMultiThreadEvalJsEnabled = Boolean.valueOf(bool3);
    } 
    boolean bool1 = bool2;
    if (sMultiThreadEvalJsEnabled.booleanValue()) {
      TTWebViewExtensionWrapper tTWebViewExtensionWrapper = this.mWebViewExtension;
      bool1 = bool2;
      if (tTWebViewExtensionWrapper != null)
        bool1 = tTWebViewExtensionWrapper.renderEvaluateJavaScript(script, callback); 
    } 
    if (!bool1)
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              TTWebViewSupportWebView.this.evaluateJavascript(script, callback);
            }
          }); 
  }
  
  public long getLoadingStatusCode() {
    TTWebViewExtensionWrapper tTWebViewExtensionWrapper = this.mWebViewExtension;
    return (tTWebViewExtensionWrapper != null) ? tTWebViewExtensionWrapper.getLoadingStatusCode() : -3L;
  }
  
  public void onBoundsChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3) {
    OnScrollListener onScrollListener = this.mScrollerListener;
    if (onScrollListener != null)
      onScrollListener.onBackNativeScrollChanged(paramInt1, paramInt2, paramInt3); 
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    OnScrollListener onScrollListener = this.mScrollerListener;
    if (onScrollListener != null)
      onScrollListener.onNativeScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4); 
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void registerPlatformView(View paramView) {
    if (isRenderInBrowserEnabled()) {
      if (paramView == null)
        return; 
      int i = paramView.getId();
      if (i == -1)
        return; 
      TTWebViewExtensionWrapper tTWebViewExtensionWrapper = this.mWebViewExtension;
      if (tTWebViewExtensionWrapper != null)
        tTWebViewExtensionWrapper.registerPlatformView(paramView, i); 
    } 
  }
  
  public void setLayerType() {
    if (isRenderInBrowserEnabled())
      setLayerType(2, null); 
  }
  
  public void setScrollListener(OnScrollListener paramOnScrollListener) {
    this.mScrollerListener = paramOnScrollListener;
  }
  
  public static interface OnScrollListener {
    void onBackNativeScrollChanged(int param1Int1, int param1Int2, int param1Int3);
    
    void onNativeScrollChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\TTWebViewSupportWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */