package com.tt.miniapp.component.nativeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.storage.async.Action;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.bridge.AdWebBridge;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.msg.ApiGetAdSiteBaseInfoCtrl;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.MimeTypeUtil;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.TimeMeter;
import java.io.File;
import java.io.InputStream;

public class NativeAdWebView extends NativeWebView {
  public NativeAdWebView(Context paramContext) {
    this(paramContext, ComponentIDCreator.create());
  }
  
  public NativeAdWebView(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mWebViewClient.setWebResourceResponseInterceptor(new NativeWebView.WebResourceResponseInterceptor() {
          public WebResourceResponse interceptRequest(WebView param1WebView, WebResourceRequest param1WebResourceRequest) {
            Uri uri = param1WebResourceRequest.getUrl();
            if (uri == null)
              return null; 
            String str1 = uri.toString();
            String str2 = uri.getLastPathSegment();
            AppBrandLogger.d("NativeAdWebView", new Object[] { "urlString:", str1, "lastPath:", str2 });
            if (TextUtils.equals(str2, "toutiao.js"))
              return new WebResourceResponse(MimeTypeUtil.getMimeType(str1), "UTF-8", null); 
            NativeAdWebView.AdLoadResource adLoadResource = new NativeAdWebView.AdLoadResource(uri);
            if (adLoadResource.mIsPkgFile) {
              String str = adLoadResource.mPath;
              AppBrandLogger.i("NativeAdWebView", new Object[] { "path", str });
              long l = SystemClock.elapsedRealtime();
              InputStream inputStream = StreamLoader.getStream(str);
              AppBrandLogger.d("AdTimer", new Object[] { Long.valueOf(SystemClock.elapsedRealtime() - l), str1 });
              if (inputStream != null)
                try {
                  return new WebResourceResponse(MimeTypeUtil.getMimeType(str1), "UTF-8", inputStream);
                } catch (Exception exception) {
                  AppBrandLogger.e("NativeAdWebView", new Object[] { "shouldInterceptRequest", exception });
                }  
            } 
            return null;
          }
        });
    ApiGetAdSiteBaseInfoCtrl.sPageType = 5;
  }
  
  protected void addBridge() {
    this.mWebView.addJavascriptInterface(new AdWebBridge((NativeWebView)this), "ttJSCore");
    this.mWebView.getSettings().setDomStorageEnabled(false);
  }
  
  public void bind(WebViewManager.IRender paramIRender) {
    super.bind(paramIRender);
    this.mWebViewClient.setWebViewCallback(new NativeWebView.WebViewCallback() {
          private TimeMeter mPageLoadStartTime;
          
          public void onPageError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
            TimeMeter timeMeter = this.mPageLoadStartTime;
            if (timeMeter != null) {
              long l = TimeMeter.stop(timeMeter);
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(param1Int);
              stringBuilder.append("#");
              stringBuilder.append(param1String1);
              InnerEventHelper.mpPageLoadResult(param1String2, "fail", l, stringBuilder.toString());
              this.mPageLoadStartTime = null;
            } 
          }
          
          public void onPageFinished(WebView param1WebView, String param1String) {
            TimeMeter timeMeter = this.mPageLoadStartTime;
            if (timeMeter != null) {
              InnerEventHelper.mpPageLoadResult(param1String, "success", TimeMeter.stop(timeMeter), CharacterUtils.empty());
              this.mPageLoadStartTime = null;
            } 
          }
          
          public void onPageStart(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
            this.mPageLoadStartTime = TimeMeter.newAndStart();
            InnerEventHelper.mpPageLoadStart(param1String);
          }
        });
  }
  
  protected boolean interceptLoadSpecialUrl(final String url) {
    final AdLoadResource adLoadResource = new AdLoadResource(url);
    if (adLoadResource.mIsPkgFile) {
      AppBrandLogger.d("NativeAdWebView", new Object[] { "interceptLoadSpecialUrl url:", url });
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              String str1;
              String str2 = adLoadResource.mPath;
              if (!TextUtils.isEmpty(str2) && str2.startsWith(File.separator)) {
                str1 = str2.substring(1);
              } else {
                str1 = str2;
              } 
              File file = new File(FileManager.inst().getRealFilePath(str1));
              final String localFileUrl = Uri.parse(url).buildUpon().scheme("file").authority("").path(file.getPath()).build().toString();
              StreamLoader.extractToFile(str2, file.getParent(), file.getName());
              AppBrandLogger.d("NativeAdWebView", new Object[] { "interceptLoadSpecialUrl localFileUrl:", str3 });
              ThreadUtil.runOnUIThread(new Runnable() {
                    public void run() {
                      _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(NativeAdWebView.this.mWebView, localFileUrl);
                    }
                    
                    class null {}
                  });
            }
          }ThreadPools.defaults());
      return true;
    } 
    return super.interceptLoadSpecialUrl(url);
  }
  
  protected boolean isAllowInterceptUrl() {
    return false;
  }
  
  class AdLoadResource {
    public boolean mIsPkgFile;
    
    public String mPath;
    
    AdLoadResource(Uri param1Uri) {
      if (param1Uri == null)
        return; 
      this.mIsPkgFile = TextUtils.equals(param1Uri.getScheme(), "ttadcache");
      this.mPath = param1Uri.getPath();
    }
    
    AdLoadResource(String param1String) {
      if (TextUtils.isEmpty(param1String))
        return; 
      Uri uri = Uri.parse(param1String);
      if (uri == null)
        return; 
      this.mIsPkgFile = TextUtils.equals(uri.getScheme(), "ttadcache");
      this.mPath = uri.getPath();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\NativeAdWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */