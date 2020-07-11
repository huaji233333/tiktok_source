package com.tt.miniapp.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.example.a.c;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WxPayManager {
  public static List<WebView> sPayWebViewList = new ArrayList<WebView>();
  
  public static void payOnH5(final Activity activity, final String url, final String referer, final FrameLayout.LayoutParams webViewLayoutParams, final WxPayListener wxPayListener) {
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            final WebView webView = new WebView((Context)activity);
            final Runnable failCallRunnable = new Runnable() {
                public void run() {
                  ToolUtils.clearWebView(webView);
                  WxPayManager.sPayWebViewList.remove(webView);
                  wxPayListener.onPayFail("long time not response");
                }
              };
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("UTF-8");
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(c.a(new WebViewClient() {
                    public void onReceivedError(WebView param2WebView, int param2Int, String param2String1, String param2String2) {
                      mainHandler.removeCallbacks(failCallRunnable);
                      ThreadUtil.runOnUIThread(new Runnable() {
                            public void run() {
                              ToolUtils.clearWebView(webView);
                              WxPayManager.sPayWebViewList.remove(webView);
                            }
                          });
                      wxPayListener.onPayFail(param2String1);
                    }
                    
                    public boolean onRenderProcessGone(WebView param2WebView, RenderProcessGoneDetail param2RenderProcessGoneDetail) {
                      return c.a(param2WebView, param2RenderProcessGoneDetail);
                    }
                    
                    public boolean shouldOverrideUrlLoading(WebView param2WebView, String param2String) {
                      if (param2String.startsWith("weixin://wap/pay")) {
                        mainHandler.removeCallbacks(failCallRunnable);
                        ThreadUtil.runOnUIThread(new Runnable() {
                              public void run() {
                                ToolUtils.clearWebView(webView);
                                WxPayManager.sPayWebViewList.remove(webView);
                              }
                            });
                        try {
                          Intent intent = new Intent();
                          intent.setAction("android.intent.action.VIEW");
                          intent.setData(Uri.parse(param2String));
                        } finally {
                          Exception exception = null;
                          AppBrandLogger.e("WxPayManager", new Object[] { "shouldOverrideUrlLoading", exception });
                        } 
                      } 
                      return super.shouldOverrideUrlLoading(param2WebView, param2String);
                    }
                  }));
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("Referer", referer);
            _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(webView, url, hashMap);
            WxPayManager.sPayWebViewList.add(webView);
            if (webViewLayoutParams != null)
              ((ViewGroup)((ViewGroup)AppbrandContext.getInst().getCurrentActivity().findViewById(16908290)).getChildAt(0)).addView((View)webView, (ViewGroup.LayoutParams)webViewLayoutParams); 
            mainHandler.postDelayed(runnable, 5000L);
          }
          
          class null {}
        });
  }
  
  public static interface WxPayListener {
    void onNotInstalled();
    
    void onPayFail(String param1String);
    
    void onTriggerWxClientPay();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\WxPayManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */