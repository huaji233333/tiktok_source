package com.tt.miniapp.component.nativeview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.bytedance.v.a.a.a.b;
import com.example.a.c;
import com.tt.frontendapiinterface.e;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.util.Trick4MoneyUtil;
import com.tt.miniapp.util.VideoFullScreenHelper;
import com.tt.miniapp.util.WebviewSchemaUtil;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapp.view.webcore.TTWebViewSupportWebView;
import com.tt.miniapp.web.TTWebSetting;
import com.tt.miniapp.webbridge.WebComponentBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageUtils;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.IllegalColorException;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.e.k;
import com.tt.option.n.c;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;

public class NativeWebView extends FrameLayout implements DownloadListener, NativeComponent {
  protected VideoFullScreenHelper mHelper;
  
  private View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
      public void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, int param1Int8) {
        if (NativeWebView.this.mRender != null) {
          if (NativeWebView.this.mParentView == null)
            return; 
          ViewGroup.LayoutParams layoutParams = NativeWebView.this.getLayoutParams();
          if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
            AbsoluteLayout.LayoutParams layoutParams1 = (AbsoluteLayout.LayoutParams)layoutParams;
            if (layoutParams1.width != NativeWebView.this.mRender.getRenderWidth() || layoutParams1.height != NativeWebView.this.mRender.getRenderHeight()) {
              AppBrandLogger.d("tma_NativeWebView", new Object[] { "onLayoutChange", "width", Integer.valueOf(layoutParams1.width), "height", Integer.valueOf(layoutParams1.height), "windowWidth", Integer.valueOf(this.this$0.mRender.getRenderWidth()), "windowHeight", Integer.valueOf(this.this$0.mRender.getRenderHeight()) });
              layoutParams1.width = NativeWebView.this.mRender.getRenderWidth();
              layoutParams1.height = NativeWebView.this.mRender.getRenderHeight();
              NativeWebView.this.setLayoutParams((ViewGroup.LayoutParams)layoutParams1);
            } 
          } 
        } 
      }
    };
  
  protected AbsoluteLayout mParentView;
  
  protected ProgressBarView mProgressBarView;
  
  protected WebViewManager.IRender mRender;
  
  protected WebView mWebView;
  
  protected final ComponentWebViewClient mWebViewClient;
  
  protected int mWebViewId;
  
  public NativeWebView(Context paramContext, int paramInt) {
    super(paramContext);
    this.mWebViewId = paramInt;
    this.mWebView = new WebView(paramContext);
    this.mWebView.setDownloadListener((DownloadListener)this);
    CookieManager.getInstance().setAcceptThirdPartyCookies(this.mWebView, true);
    this.mProgressBarView = new ProgressBarView(paramContext);
    this.mWebViewClient = new ComponentWebViewClient();
    initWebView();
  }
  
  private void cleanWebView() {
    this.mWebView.setWebChromeClient(null);
    this.mWebView.setWebViewClient(c.a(null));
    ViewParent viewParent = getParent();
    if (viewParent instanceof ViewGroup) {
      ((ViewGroup)viewParent).removeView((View)this);
      try {
        return;
      } finally {
        viewParent = null;
      } 
    } 
  }
  
  public static String getUnsafePageUrl() {
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    return (initParamsEntity != null) ? initParamsEntity.getHostStr(1004, AppbrandConstant.OpenApi.getInst().getNOT_SUPPORT_URL()) : AppbrandConstant.OpenApi.getInst().getNOT_SUPPORT_URL();
  }
  
  public static boolean isHttpUrl(String paramString) {
    return (!TextUtils.isEmpty(paramString) && (paramString.startsWith("http://") || paramString.startsWith("https://")));
  }
  
  private static void loadUnsafeTipPage(WebView paramWebView, String paramString1, String paramString2) {
    byte b;
    StringBuffer stringBuffer;
    Uri uri;
    String str = getUnsafePageUrl();
    try {
      if (!TextUtils.isEmpty(str)) {
        stringBuffer = new StringBuffer(str);
        stringBuffer.append("?");
        stringBuffer.append(LanguageUtils.appendLanguageQueryParam());
        uri = Uri.parse(paramString1);
        b = -1;
        int i = paramString2.hashCode();
        if (i != 290602151) {
          if (i == 1224424441 && paramString2.equals("webview"))
            b = 0; 
        } else if (paramString2.equals("webview_schema")) {
          b = 1;
        } 
      } else {
        return;
      } 
    } catch (Exception exception) {
      AppBrandLogger.d("tma_NativeWebView", new Object[] { exception });
      if (paramWebView != null) {
        StringBuffer stringBuffer1 = new StringBuffer(str);
        stringBuffer1.append("?");
        stringBuffer1.append(LanguageUtils.appendLanguageQueryParam());
        _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(paramWebView, stringBuffer1.toString());
      } 
      return;
    } 
    if (b != 0) {
      if (b == 1) {
        paramString1 = uri.getScheme();
        stringBuffer.append("&unconfig_schema=");
        stringBuffer.append(Uri.encode(paramString1));
      } 
    } else {
      paramString2 = uri.getHost();
      paramString1 = paramString1.substring(0, paramString1.indexOf(paramString2) + paramString2.length());
      stringBuffer.append("&unconfig_domain=");
      stringBuffer.append(Uri.encode(paramString1));
    } 
    if (paramWebView != null)
      _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(paramWebView, stringBuffer.toString()); 
  }
  
  private void monitorRenderSize() {
    WebViewManager.IRender iRender = this.mRender;
    if (iRender != null) {
      NativeNestWebView nativeNestWebView = iRender.getNativeNestWebView();
      if (nativeNestWebView != null)
        nativeNestWebView.addOnLayoutChangeListener(this.mOnLayoutChangeListener); 
    } 
  }
  
  private void startWebBrowser(Context paramContext, String paramString, boolean paramBoolean) {
    HostDependManager.getInst().openWebBrowser(paramContext, paramString, paramBoolean);
  }
  
  private void unMonitorRenderSize() {
    WebViewManager.IRender iRender = this.mRender;
    if (iRender != null) {
      NativeNestWebView nativeNestWebView = iRender.getNativeNestWebView();
      if (nativeNestWebView != null)
        nativeNestWebView.removeOnLayoutChangeListener(this.mOnLayoutChangeListener); 
    } 
  }
  
  protected void addBridge() {
    this.mWebView.addJavascriptInterface(new WebComponentBridge(this), "ttJSCore");
  }
  
  public void addView(String paramString, k paramk) {
    if (this.mParentView != null) {
      if (this.mRender == null)
        return; 
      try {
        JSONObject jSONObject = new JSONObject(paramString);
        int i = this.mRender.getRenderWidth();
        int j = this.mRender.getRenderHeight();
        AppBrandLogger.d("tma_NativeWebView", new Object[] { "addView", "left", Integer.valueOf(0), "top", Integer.valueOf(0), "width", Integer.valueOf(i), "height", Integer.valueOf(j) });
        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(i, j, 0 - this.mParentView.getCurScrollX(), 0 - this.mParentView.getCurScrollY());
        if (jSONObject.has("zIndex"))
          layoutParams.zIndex = jSONObject.optInt("zIndex"); 
        if (jSONObject.has("fixed"))
          layoutParams.isFixed = jSONObject.optBoolean("fixed"); 
        this.mParentView.addView((View)this, (ViewGroup.LayoutParams)layoutParams);
        setProgressBarColor(jSONObject.optString("progressBarColor"));
        monitorRenderSize();
        return;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "tma_NativeWebView", exception.getStackTrace());
      } 
    } 
  }
  
  public void bind(WebViewManager.IRender paramIRender) {
    this.mRender = paramIRender;
  }
  
  public void bind(AbsoluteLayout paramAbsoluteLayout, WebViewManager.IRender paramIRender) {
    this.mParentView = paramAbsoluteLayout;
    this.mRender = paramIRender;
    this.mWebViewClient.setWebViewCallback(new WebViewCallback() {
          private void callbackWebViewEvent(String param1String1, String param1String2) {
            if (NativeWebView.this.mRender == null)
              return; 
            JSONObject jSONObject = (new JsonBuilder()).put("htmlId", Integer.valueOf(NativeWebView.this.getWebViewId())).put("src", param1String1).build();
            AppbrandApplicationImpl.getInst().getWebViewManager().publish(NativeWebView.this.mRender.getWebViewId(), param1String2, jSONObject.toString());
          }
          
          public void onPageError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
            callbackWebViewEvent(param1String2, "onWebviewError");
          }
          
          public void onPageFinished(WebView param1WebView, String param1String) {
            callbackWebViewEvent(param1String, "onWebviewFinishLoad");
            CookieManager.getInstance().flush();
          }
          
          public void onPageStart(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
            callbackWebViewEvent(param1String, "onWebviewStartLoad");
          }
        });
  }
  
  public boolean canGoBack() {
    WebView webView = this.mWebView;
    return (webView != null) ? webView.canGoBack() : false;
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool = super.dispatchTouchEvent(paramMotionEvent);
    AppBrandLogger.d("tma_NativeWebView", new Object[] { "dispatchTouchEvent ", Boolean.valueOf(bool) });
    return bool;
  }
  
  public c getFileChooseHandler() {
    WebViewManager.IRender iRender = this.mRender;
    return (iRender != null) ? iRender.getFileChooseHandler() : null;
  }
  
  public WebView getWebView() {
    return this.mWebView;
  }
  
  public int getWebViewId() {
    return this.mWebViewId;
  }
  
  public void initWebView() {
    int i = (int)UIUtils.dip2Px(getContext(), 2.0F);
    addView((View)this.mWebView, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
    addView((View)this.mProgressBarView, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, i));
    this.mProgressBarView.hide();
    setProgressBarColor("");
    this.mWebView.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            param1View.getParent().requestDisallowInterceptTouchEvent(true);
            if ((param1MotionEvent.getAction() & 0xFF) != 1)
              return false; 
            param1View.getParent().requestDisallowInterceptTouchEvent(false);
            return false;
          }
        });
    TTWebSetting tTWebSetting = new TTWebSetting(this.mWebView.getSettings());
    tTWebSetting.setDefaultSetting();
    tTWebSetting.enableZoomSupport();
    tTWebSetting.setDomStorageEnabled();
    if ((DebugManager.getInst()).mIsDebugOpen)
      WebView.setWebContentsDebuggingEnabled(true); 
    this.mWebView.setWebChromeClient(new WebChromeClient() {
          private boolean isDefaultUrl(String param1String) {
            return (param1String == null) ? false : ((param1String.startsWith(NativeWebView.getUnsafePageUrl()) || param1String.startsWith("file:///android_asset/error-page.html")));
          }
          
          private boolean isUrlTitle(String param1String1, String param1String2) {
            if (param1String1 == null)
              return false; 
            if (param1String1.equals(param1String2))
              return true; 
            Uri uri = Uri.parse(param1String1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(uri.getScheme());
            stringBuilder.append("://");
            stringBuilder.append(param1String2);
            if (param1String1.equals(stringBuilder.toString())) {
              StringBuilder stringBuilder1 = new StringBuilder("title is url:");
              stringBuilder1.append(param1String1);
              AppBrandLogger.d("tma_NativeWebView", new Object[] { stringBuilder1.toString() });
              return true;
            } 
            return false;
          }
          
          public void NativeWebView$4__onGeolocationPermissionsShowPrompt$___twin___(final String origin, final GeolocationPermissions.Callback callback) {
            b.a(this, new Object[] { origin, callback }, 100003, "com.tt.miniapp.component.nativeview.NativeWebView$4.onGeolocationPermissionsShowPrompt(java.lang.String,android.webkit.GeolocationPermissions$Callback)");
            AppBrandLogger.d("tma_NativeWebView", new Object[] { "location permission ", origin });
            HashSet<String> hashSet = new HashSet();
            hashSet.add("android.permission.ACCESS_COARSE_LOCATION");
            hashSet.add("android.permission.ACCESS_FINE_LOCATION");
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity)AppbrandContext.getInst().getCurrentActivity(), hashSet, new PermissionsResultAction() {
                  public void onDenied(String param2String) {
                    AppBrandLogger.d("tma_NativeWebView", new Object[] { "denied location" });
                    callback.invoke(origin, false, false);
                  }
                  
                  public void onGranted() {
                    AppBrandLogger.d("tma_NativeWebView", new Object[] { "granted location" });
                    callback.invoke(origin, true, false);
                  }
                });
          }
          
          public void onGeolocationPermissionsShowPrompt(String param1String, GeolocationPermissions.Callback param1Callback) {
            _lancet.com_ss_android_ugc_aweme_lancet_WebLancet_onGeolocationPermissionsShowPrompt(this, param1String, param1Callback);
          }
          
          public void onHideCustomView() {
            AppBrandLogger.d("tma_NativeWebView", new Object[] { "onHideCustomView " });
            super.onHideCustomView();
            if (NativeWebView.this.mHelper == null)
              return; 
            if (NativeWebView.this.mRender != null)
              NativeWebView.this.mHelper.exitFullScreen(NativeWebView.this.mRender.getCurrentActivity()); 
          }
          
          public void onProgressChanged(WebView param1WebView, int param1Int) {
            AppBrandLogger.d("tma_NativeWebView", new Object[] { "onProgressChanged ", Integer.valueOf(param1Int) });
            if (param1Int >= 99) {
              NativeWebView.this.mProgressBarView.hide();
              if (NativeWebView.this.mRender != null)
                NativeWebView.this.mRender.onNativeWebViewPageFinished(param1WebView.canGoBack()); 
            } else {
              if (!NativeWebView.this.mProgressBarView.isShown())
                NativeWebView.this.mProgressBarView.show(); 
              NativeWebView.this.mProgressBarView.setProgress(param1Int);
            } 
            super.onProgressChanged(param1WebView, param1Int);
          }
          
          public void onReceivedTitle(WebView param1WebView, String param1String) {
            super.onReceivedTitle(param1WebView, param1String);
            if (!isUrlTitle(param1WebView.getUrl(), param1String) && NativeWebView.this.mRender != null)
              NativeWebView.this.mRender.updateWebTitle(param1String, isDefaultUrl(param1WebView.getUrl())); 
          }
          
          public void onShowCustomView(View param1View, WebChromeClient.CustomViewCallback param1CustomViewCallback) {
            AppBrandLogger.d("tma_NativeWebView", new Object[] { "onShowCustomView " });
            super.onShowCustomView(param1View, param1CustomViewCallback);
            if (NativeWebView.this.mHelper == null)
              NativeWebView.this.mHelper = new VideoFullScreenHelper(); 
            NativeWebView.this.mHelper.setDirection(VideoFullScreenHelper.ScreenDirection.LANDSCAPE);
            if (NativeWebView.this.mRender != null)
              NativeWebView.this.mHelper.enterFullScreen(NativeWebView.this.mRender.getCurrentActivity(), param1View, param1CustomViewCallback); 
          }
          
          public boolean onShowFileChooser(WebView param1WebView, ValueCallback<Uri[]> param1ValueCallback, WebChromeClient.FileChooserParams param1FileChooserParams) {
            c c = NativeWebView.this.getFileChooseHandler();
            if (c != null) {
              c.openFileChooser(param1ValueCallback, param1FileChooserParams);
              return true;
            } 
            return false;
          }
          
          class null {}
        });
    this.mWebView.setWebViewClient(c.a((WebViewClient)this.mWebViewClient));
    _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(this.mWebView, "javascript: window.__ttjsenv__='microapp';console.log(__ttjsenv__);");
    addBridge();
    AppbrandApplicationImpl.getInst().getWebViewManager().addWebComponent(this);
  }
  
  protected boolean interceptLoadSpecialUrl(String paramString) {
    return false;
  }
  
  public boolean interceptUrl(WebView paramWebView, String paramString) {
    if (!isAllowInterceptUrl())
      return false; 
    String str = WebviewSchemaUtil.getSchema(paramString);
    if (WebviewSchemaUtil.isWhiteList("webview_schema", str)) {
      if (!WebviewSchemaUtil.openSchema(str, paramString))
        loadUnsafeTipPage(paramWebView, paramString, "webview_schema"); 
      return true;
    } 
    if (!WebviewSchemaUtil.isDefaultSchema(str)) {
      if (Trick4MoneyUtil.ignoreWebViewScheme(str, AppbrandApplicationImpl.getInst().getAppInfo()))
        return true; 
      loadUnsafeTipPage(paramWebView, paramString, "webview_schema");
      return true;
    } 
    if (!NetUtil.isSafeDomain("webview", paramString)) {
      loadUnsafeTipPage(paramWebView, paramString, "webview");
      Event.builder("mp_webview_invalid_domain").kv("host", paramString).flush();
      return true;
    } 
    return false;
  }
  
  public boolean interceptUrlByHost(Context paramContext, String paramString) {
    return !isAllowInterceptUrl() ? false : HostDependManager.getInst().interceptOpenWebUrl(paramContext, paramString);
  }
  
  protected boolean isAllowInterceptUrl() {
    return true;
  }
  
  public void loadUrl(String paramString, boolean paramBoolean) {
    this.mWebViewClient.setClearHistory(paramBoolean);
    if (interceptUrlByHost(getContext(), paramString)) {
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              e e = AppbrandApplication.getInst().getActivityLife();
              if (e != null)
                e.goback(); 
            }
          });
      return;
    } 
    if (!interceptLoadSpecialUrl(paramString)) {
      if (interceptUrl(this.mWebView, paramString))
        return; 
      HostDependManager.getInst().syncWebViewLoginCookie(paramString);
      _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(this.mWebView, paramString);
    } 
  }
  
  public boolean onBackPressed() {
    VideoFullScreenHelper videoFullScreenHelper = this.mHelper;
    if (videoFullScreenHelper != null && videoFullScreenHelper.exitFullScreenManual())
      return true; 
    ComponentWebViewClient componentWebViewClient = this.mWebViewClient;
    if (componentWebViewClient != null)
      componentWebViewClient.setLoadUrlByBack(true); 
    if (this.mWebView.canGoBack()) {
      this.mWebView.goBack();
      return true;
    } 
    return false;
  }
  
  public void onDestroy() {
    cleanWebView();
    unMonitorRenderSize();
  }
  
  public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong) {
    startWebBrowser(getContext(), paramString1, true);
  }
  
  public void onPause() {
    try {
      this.mWebView.onPause();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_NativeWebView", new Object[] { "onPause", exception });
      return;
    } 
  }
  
  public void onResume() {
    try {
      this.mWebView.onResume();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_NativeWebView", new Object[] { "onResume", exception });
      return;
    } 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    AppBrandLogger.d("tma_NativeWebView", new Object[] { "onTouchEvent ", Boolean.valueOf(bool) });
    return bool;
  }
  
  public void onViewPause() {
    onPause();
  }
  
  public void onViewResume() {
    onResume();
  }
  
  public void reloadErrorUrl() {
    post(new Runnable() {
          public void run() {
            WebView webView = NativeWebView.this.mWebView;
            StringBuilder stringBuilder = new StringBuilder("document.location.replace('");
            stringBuilder.append(NativeWebView.this.mWebViewClient.getErrorUrl());
            stringBuilder.append("')");
            webView.evaluateJavascript(stringBuilder.toString(), null);
          }
        });
  }
  
  public void removeView(int paramInt, k paramk) {
    WebView webView = this.mWebView;
    if (webView != null) {
      webView.setWebChromeClient(null);
      this.mWebView.setWebViewClient(c.a(null));
      HostDependManager.getInst().onWebViewComponentDestroyed(this.mWebView);
    } 
    unMonitorRenderSize();
  }
  
  public void setProgressBarColor(String paramString) {
    try {
      paramString = UIUtils.rgbaToFullARGBStr(paramString, "#51a0d8");
      if (!TextUtils.isEmpty(paramString))
        this.mProgressBarView.setProgressDrawable((Drawable)new ClipDrawable((Drawable)new ColorDrawable(UIUtils.parseColor(paramString)), 3, 1)); 
      return;
    } catch (IllegalColorException illegalColorException) {
      AppBrandLogger.e("tma_NativeWebView", new Object[] { illegalColorException });
      return;
    } 
  }
  
  public void updateView(String paramString, k paramk) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      String str = jSONObject.optString("src");
      AppBrandLogger.d("tma_NativeWebView", new Object[] { "updateView ", paramString });
      if (!TextUtils.isEmpty(str))
        loadUrl(str, false); 
      AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams)getLayoutParams();
      if (jSONObject.has("fixed"))
        layoutParams.isFixed = jSONObject.optBoolean("fixed"); 
      if (jSONObject.has("zIndex")) {
        layoutParams.zIndex = jSONObject.optInt("zIndex");
        requestLayout();
      } 
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_NativeWebView", new Object[] { "updateView error ", exception });
      return;
    } 
  }
  
  protected class ComponentWebViewClient extends WebViewClient {
    private boolean isLoadUrlByBack;
    
    private NativeWebView.WebViewCallback mCallback;
    
    private final String mDefaultErrorPage = "file:///android_asset/error-page.html";
    
    private String mErrorUrl;
    
    private NativeWebView.WebResourceResponseInterceptor mWebResourceResponseInterceptor;
    
    boolean shouldClearHistory;
    
    private boolean shouldInterceptUrlLoading(WebView param1WebView, String param1String) {
      if (NativeWebView.this.interceptUrl(param1WebView, param1String))
        return true; 
      if (NativeWebView.this.interceptUrlByHost(param1WebView.getContext(), param1String))
        return true; 
      if (NativeWebView.isHttpUrl(param1String)) {
        HostDependManager.getInst().syncWebViewLoginCookie(param1String);
        return false;
      } 
      if (TextUtils.isEmpty(param1String) || "about".equals(WebviewSchemaUtil.getSchema(param1String)) || "about:blank".equals(param1String));
      return false;
    }
    
    public void doUpdateVisitedHistory(WebView param1WebView, String param1String, boolean param1Boolean) {
      AppBrandLogger.d("tma_NativeWebView", new Object[] { "doUpdateVisitedHistory ", param1String });
      super.doUpdateVisitedHistory(param1WebView, param1String, param1Boolean);
      if (this.shouldClearHistory) {
        param1WebView.clearHistory();
        this.shouldClearHistory = false;
      } 
    }
    
    String getErrorUrl() {
      return this.mErrorUrl;
    }
    
    public void onPageFinished(WebView param1WebView, String param1String) {
      super.onPageFinished(param1WebView, param1String);
      if (param1String != null && !param1String.startsWith("file:///android_asset/error-page.html")) {
        NativeWebView.WebViewCallback webViewCallback = this.mCallback;
        if (webViewCallback != null)
          webViewCallback.onPageFinished(param1WebView, param1String); 
      } 
      this.isLoadUrlByBack = false;
      StringBuilder stringBuilder = new StringBuilder("window.isRenderInBrowser=");
      stringBuilder.append(TTWebViewSupportWebView.isRenderInBrowserEnabled());
      stringBuilder.append(";");
      param1WebView.evaluateJavascript(stringBuilder.toString(), null);
      AppBrandLogger.d("tma_NativeWebView", new Object[] { "onPageFinished url ", param1String });
    }
    
    public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
      super.onPageStarted(param1WebView, param1String, param1Bitmap);
      AppBrandLogger.d("tma_NativeWebView", new Object[] { "onPageStarted url ", param1String });
      if (param1String != null && !param1String.startsWith("file:///android_asset/error-page.html")) {
        NativeWebView.WebViewCallback webViewCallback = this.mCallback;
        if (webViewCallback != null)
          webViewCallback.onPageStart(param1WebView, param1String, param1Bitmap); 
      } 
    }
    
    public void onReceivedError(WebView param1WebView, int param1Int, String param1String1, String param1String2) {
      if (param1String2 != null && !param1String2.startsWith("file:///android_asset/error-page.html")) {
        NativeWebView.WebViewCallback webViewCallback = this.mCallback;
        if (webViewCallback != null)
          webViewCallback.onPageError(param1WebView, param1Int, param1String1, param1String2); 
      } 
      if (!this.isLoadUrlByBack) {
        this.mErrorUrl = param1String2;
        param1WebView.stopLoading();
        StringBuilder stringBuilder = new StringBuilder("file:///android_asset/error-page.html?language=");
        stringBuilder.append(DevicesUtil.getLanguage());
        _lancet.com_ss_android_ugc_aweme_lancet_H5UrlCheckLancet_loadUrl(param1WebView, stringBuilder.toString());
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("url", param1String2);
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(6, "tma_NativeWebView", jSONException.getStackTrace());
        } 
        if (Build.VERSION.SDK_INT >= 23) {
          try {
            jSONObject.put("code", param1Int);
            jSONObject.put("errMsg", param1String1);
          } catch (JSONException jSONException) {
            AppBrandLogger.stacktrace(6, "tma_NativeWebView", jSONException.getStackTrace());
          } 
          AppBrandLogger.d("tma_NativeWebView", new Object[] { "onReceivedError WebResourceRequest  ", param1String2, " ", param1String1, " ", Integer.valueOf(param1Int) });
        } 
        AppBrandMonitor.statusRate("mp_start_error", 3000, jSONObject);
      } 
    }
    
    public void onReceivedHttpError(WebView param1WebView, WebResourceRequest param1WebResourceRequest, WebResourceResponse param1WebResourceResponse) {
      AppBrandLogger.d("tma_NativeWebView", new Object[] { "onReceivedHttpError WebResourceRequest  ", param1WebResourceRequest.getUrl().toString(), " ", Integer.valueOf(param1WebResourceResponse.getStatusCode()), " ", Boolean.valueOf(param1WebResourceRequest.isForMainFrame()) });
      super.onReceivedHttpError(param1WebView, param1WebResourceRequest, param1WebResourceResponse);
      if (param1WebResourceRequest.isForMainFrame()) {
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("url", param1WebResourceRequest.getUrl().toString());
          jSONObject.put("code", param1WebResourceResponse.getStatusCode());
          jSONObject.put("errMsg", param1WebResourceResponse.getEncoding());
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(6, "tma_NativeWebView", jSONException.getStackTrace());
        } 
        AppBrandMonitor.statusRate("mp_start_error", 3000, jSONObject);
      } 
    }
    
    public boolean onRenderProcessGone(WebView param1WebView, RenderProcessGoneDetail param1RenderProcessGoneDetail) {
      return c.a(param1WebView, param1RenderProcessGoneDetail);
    }
    
    void setClearHistory(boolean param1Boolean) {
      this.shouldClearHistory = param1Boolean;
    }
    
    void setLoadUrlByBack(boolean param1Boolean) {
      this.isLoadUrlByBack = param1Boolean;
    }
    
    public void setWebResourceResponseInterceptor(NativeWebView.WebResourceResponseInterceptor param1WebResourceResponseInterceptor) {
      this.mWebResourceResponseInterceptor = param1WebResourceResponseInterceptor;
    }
    
    public void setWebViewCallback(NativeWebView.WebViewCallback param1WebViewCallback) {
      this.mCallback = param1WebViewCallback;
    }
    
    public WebResourceResponse shouldInterceptRequest(WebView param1WebView, WebResourceRequest param1WebResourceRequest) {
      NativeWebView.WebResourceResponseInterceptor webResourceResponseInterceptor = this.mWebResourceResponseInterceptor;
      if (webResourceResponseInterceptor != null) {
        WebResourceResponse webResourceResponse = webResourceResponseInterceptor.interceptRequest(param1WebView, param1WebResourceRequest);
        if (webResourceResponse != null)
          return webResourceResponse; 
      } 
      return _lancet.com_ss_android_ugc_aweme_lancet_WebLancet_shouldInterceptRequest((WebViewClient)this, param1WebView, param1WebResourceRequest);
    }
    
    public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
      AppBrandLogger.d("tma_NativeWebView", new Object[] { "shouldOverrideUrlLoading ", param1String });
      return shouldInterceptUrlLoading(param1WebView, param1String);
    }
    
    class ComponentWebViewClient {}
  }
  
  class NativeWebView {}
  
  public static interface WebResourceResponseInterceptor {
    WebResourceResponse interceptRequest(WebView param1WebView, WebResourceRequest param1WebResourceRequest);
  }
  
  public static interface WebViewCallback {
    void onPageError(WebView param1WebView, int param1Int, String param1String1, String param1String2);
    
    void onPageFinished(WebView param1WebView, String param1String);
    
    void onPageStart(WebView param1WebView, String param1String, Bitmap param1Bitmap);
  }
  
  class NativeWebView {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\component\nativeview\NativeWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */