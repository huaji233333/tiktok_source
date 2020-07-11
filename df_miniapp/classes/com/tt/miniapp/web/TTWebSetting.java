package com.tt.miniapp.web;

import android.os.Build;
import android.webkit.WebSettings;
import com.tt.miniapp.util.ToolUtils;

public class TTWebSetting {
  private WebSettings mWebSettings;
  
  public TTWebSetting(WebSettings paramWebSettings) {
    this.mWebSettings = paramWebSettings;
  }
  
  public void appendUserAgent(String paramString) {
    String str = this.mWebSettings.getUserAgentString();
    WebSettings webSettings = this.mWebSettings;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append("; ");
    stringBuilder.append(paramString);
    webSettings.setUserAgentString(stringBuilder.toString());
  }
  
  public void enableAppCache() {
    this.mWebSettings.setAppCacheEnabled(true);
    this.mWebSettings.setCacheMode(1);
  }
  
  public void enableZoomSupport() {
    this.mWebSettings.setSupportZoom(true);
    this.mWebSettings.setLoadWithOverviewMode(true);
    this.mWebSettings.setBuiltInZoomControls(true);
    this.mWebSettings.setUseWideViewPort(true);
  }
  
  public void setDefaultSetting() {
    this.mWebSettings.setJavaScriptEnabled(true);
    if (Build.VERSION.SDK_INT >= 11) {
      this.mWebSettings.setDisplayZoomControls(false);
      this.mWebSettings.setAllowContentAccess(true);
    } 
    this.mWebSettings.setSupportZoom(false);
    this.mWebSettings.setBuiltInZoomControls(false);
    this.mWebSettings.setUserAgentString(ToolUtils.getCustomUA());
    this.mWebSettings.setSavePassword(false);
    this.mWebSettings.setPluginState(WebSettings.PluginState.ON);
    this.mWebSettings.setAppCacheEnabled(false);
    this.mWebSettings.setCacheMode(-1);
    this.mWebSettings.setGeolocationEnabled(true);
    this.mWebSettings.setAllowFileAccess(true);
    this.mWebSettings.setDatabaseEnabled(true);
    this.mWebSettings.setAllowFileAccessFromFileURLs(true);
    this.mWebSettings.setAllowUniversalAccessFromFileURLs(true);
    this.mWebSettings.setDefaultTextEncodingName("utf-8");
    this.mWebSettings.setTextZoom(100);
    if (Build.VERSION.SDK_INT >= 21)
      this.mWebSettings.setMixedContentMode(0); 
  }
  
  public void setDomStorageEnabled() {
    this.mWebSettings.setDomStorageEnabled(true);
  }
  
  public void setHighRenderPriority() {
    this.mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\web\TTWebSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */