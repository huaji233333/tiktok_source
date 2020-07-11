package com.tt.miniapp.view.webcore;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.example.a.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.monitor.RouterMonitorTask;
import com.tt.miniapp.monitor.WebviewStuckMonitor;
import com.tt.miniapp.route.IRouteEvent;
import com.tt.miniapp.route.RouteEventCtrl;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebBridge;
import com.tt.miniapp.webbridge.WebGlobalConfig;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.TimeMeter;
import java.io.File;

public abstract class NativeNestWebViewLoadBase extends FrameLayout {
  private static int LOAD_STATE_BEGIN;
  
  private static final int LOAD_STATE_LOADING_PAGE_FRAME;
  
  private static final int LOAD_STATE_LOADING_TEMPLATE;
  
  private static final int LOAD_STATE_NOT_START;
  
  private static final int LOAD_STATE_PAGE_FRAME_LOADED;
  
  public static final int LOAD_STATE_TEMPLATE_LOADED;
  
  protected AppbrandApplicationImpl mApp;
  
  public volatile boolean mArgumentReady;
  
  private boolean mForceLoadPageFrameJs;
  
  public final Object mLoadLock = new Object();
  
  public volatile int mLoadState = LOAD_STATE_NOT_START;
  
  private String mOpenType;
  
  private String mPageContent;
  
  private TimeMeter mPageLoadStartTime;
  
  private String mPageQuery;
  
  private String mPageUrl;
  
  private volatile boolean mPathFrameLoaded;
  
  private String mPathFramePlaceHolder;
  
  protected WebViewManager.IRender mRender;
  
  public String mSnapShotData;
  
  protected WebBridge mWebBridge;
  
  protected WebGlobalConfig mWebGolbalConfig;
  
  protected NestWebView mWebView;
  
  private final int mWebViewId;
  
  protected WebviewStuckMonitor mWebviewStuckMonitor;
  
  static {
    int i = LOAD_STATE_BEGIN;
    LOAD_STATE_NOT_START = i;
    LOAD_STATE_BEGIN = ++i;
    LOAD_STATE_LOADING_TEMPLATE = i;
    i = LOAD_STATE_BEGIN + 1;
    LOAD_STATE_BEGIN = i;
    LOAD_STATE_TEMPLATE_LOADED = i;
    i = LOAD_STATE_BEGIN + 1;
    LOAD_STATE_BEGIN = i;
    LOAD_STATE_LOADING_PAGE_FRAME = i;
    i = LOAD_STATE_BEGIN + 1;
    LOAD_STATE_BEGIN = i;
    LOAD_STATE_PAGE_FRAME_LOADED = i;
  }
  
  public NativeNestWebViewLoadBase(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl, WebViewManager.IRender paramIRender) {
    super(paramContext);
    if (!ThreadUtil.isUIThread())
      DebugUtil.logOrThrow("NativeNestWebViewLoadBase", new Object[] { "Init must be called on UI Thread." }); 
    this.mApp = paramAppbrandApplicationImpl;
    this.mRender = paramIRender;
    this.mWebViewId = ComponentIDCreator.create();
    this.mPathFramePlaceHolder = ((LoadPathInterceptor)this.mApp.getService(LoadPathInterceptor.class)).genPlaceHolder("__path_frame__", "");
    initNestWebView(getContext());
  }
  
  private void initNestWebView(Context paramContext) {
    if (BaseBundleManager.getInst() != null)
      BaseBundleManager.getInst().preload(paramContext); 
    MpTimeLineReporter mpTimeLineReporter = (MpTimeLineReporter)this.mApp.getService(MpTimeLineReporter.class);
    mpTimeLineReporter.addPoint("create_webview_begin");
    this.mWebView = HostDependManager.getInst().getNestWebView(paramContext);
    mpTimeLineReporter.addPoint("create_webview_end");
    this.mWebBridge = new WebBridge(this.mApp, this.mRender);
    this.mWebView.setLayerType();
    this.mWebView.addJavascriptInterface(this.mWebBridge, "ttJSCore");
    this.mWebGolbalConfig = new WebGlobalConfig();
    this.mWebGolbalConfig.setRenderInBrowser(TTWebViewSupportWebView.isRenderInBrowserEnabled());
    this.mWebView.addJavascriptInterface(this.mWebGolbalConfig, "ttGlobalConfig");
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
    addView((View)this.mWebView, (ViewGroup.LayoutParams)layoutParams);
    AppbrandWebviewClient appbrandWebviewClient = new AppbrandWebviewClient() {
        public void onPageFinished(WebView param1WebView, String param1String) {
          super.onPageFinished(param1WebView, param1String);
          int i = NativeNestWebViewLoadBase.this.mLoadState;
          int j = NativeNestWebViewLoadBase.LOAD_STATE_TEMPLATE_LOADED;
          boolean bool = false;
          if (i >= j) {
            ((TimeLogger)NativeNestWebViewLoadBase.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_onPageFinished_MoreThanOnce" });
            return;
          } 
          ((TimeLogger)NativeNestWebViewLoadBase.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_onPageFinished" });
          ((MpTimeLineReporter)NativeNestWebViewLoadBase.this.mApp.getService(MpTimeLineReporter.class)).addPoint("load_pageFrameHtml_end");
          ((LaunchScheduler)NativeNestWebViewLoadBase.this.mApp.getService(LaunchScheduler.class)).onTemplateLoaded();
          synchronized (NativeNestWebViewLoadBase.this.mLoadLock) {
            NativeNestWebViewLoadBase.this.mLoadState = NativeNestWebViewLoadBase.LOAD_STATE_TEMPLATE_LOADED;
            if (NativeNestWebViewLoadBase.this.mSnapShotData != null)
              bool = true; 
            boolean bool1 = NativeNestWebViewLoadBase.this.mArgumentReady;
            if (bool) {
              null = NativeNestWebViewLoadBase.this;
              null.sendSnapShotData(((NativeNestWebViewLoadBase)null).mSnapShotData);
              NativeNestWebViewLoadBase.this.mSnapShotData = null;
            } 
            if (bool1)
              NativeNestWebViewLoadBase.this.sendOnAppRoute(); 
            NativeNestWebViewLoadBase.this.continuePreloadIfNeed();
            return;
          } 
        }
      };
    this.mWebView.setWebViewClient(c.a(appbrandWebviewClient));
    this.mWebView.setWebChromeClient(new WebChromeClient() {
          private int errorUploadCount;
          
          public boolean onConsoleMessage(ConsoleMessage param1ConsoleMessage) {
            if (param1ConsoleMessage != null && param1ConsoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
              int i;
              this.errorUploadCount++;
              if (this.errorUploadCount < 10) {
                i = 1;
              } else {
                i = 0;
              } 
              if (i)
                try {
                  String str1 = param1ConsoleMessage.message();
                  i = param1ConsoleMessage.lineNumber();
                  String str2 = param1ConsoleMessage.sourceId();
                  StringBuffer stringBuffer = new StringBuffer();
                  stringBuffer.append(str1);
                  stringBuffer.append(" at line:");
                  stringBuffer.append(i);
                  stringBuffer.append(" source:");
                  stringBuffer.append(str2);
                  str1 = stringBuffer.toString();
                  LaunchScheduler launchScheduler = (LaunchScheduler)NativeNestWebViewLoadBase.this.mApp.getService(LaunchScheduler.class);
                  i = -1;
                  if (launchScheduler != null)
                    i = launchScheduler.getLaunchProgress(); 
                  AppBrandLogger.e("NativeNestWebViewLoadBase", new Object[] { str1 });
                  ((TimeLogger)NativeNestWebViewLoadBase.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_web_view_error", String.valueOf(i), str1 });
                } catch (Exception exception) {} 
            } 
            return super.onConsoleMessage(param1ConsoleMessage);
          }
        });
    this.mWebviewStuckMonitor = new WebviewStuckMonitor(this.mWebView);
  }
  
  private void loadPathFrameIfNeed() {
    synchronized (this.mLoadLock) {
      if (this.mPathFrameLoaded || this.mLoadState < LOAD_STATE_LOADING_PAGE_FRAME)
        return; 
      if (!this.mArgumentReady && !this.mForceLoadPageFrameJs)
        return; 
      this.mPathFrameLoaded = true;
      this.mPageLoadStartTime = TimeMeter.newAndStart();
      InnerEventHelper.mpPageLoadStart(this.mPageContent);
      null = new StringBuilder("ttJSBridge.subscribeHandler('onLoadPageFrame',{path: '");
      null.append(this.mPathFramePlaceHolder);
      null.append("'})");
      null = null.toString();
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_startStreamLoadPathFrame" });
      ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEvent("sendLoadPathFrame");
      NestWebView nestWebView = this.mWebView;
      ValueCallback<String> valueCallback = new ValueCallback<String>() {
          public void onReceiveValue(String param1String) {
            ((TimeLogger)NativeNestWebViewLoadBase.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_stopStreamLoadPathFrame" });
          }
        };
      StringBuilder stringBuilder = new StringBuilder("PATH_FRAME=>");
      stringBuilder.append(this.mPathFramePlaceHolder);
      nestWebView.evaluateJavascript((String)null, valueCallback, stringBuilder.toString());
      return;
    } 
  }
  
  private void showLoadError(String paramString) {
    if (!AppbrandContext.getInst().isGame())
      LoadHelper.handleMiniProcessFail(paramString); 
  }
  
  public void continuePreloadIfNeed() {
    if (this.mApp.getAppInfo() != null && this.mApp.getAppInfo().isGame())
      return; 
    if (this.mLoadState < LOAD_STATE_LOADING_TEMPLATE) {
      File file = new File(AppbrandConstant.getJsBundleDir((Context)AppbrandContext.getInst().getApplicationContext()), "page-frame.html");
      if (file.exists()) {
        boolean bool = DevicesUtil.isScreenPortrait(getContext());
        if (bool || AppbrandContext.getInst().getCurrentActivity() != null) {
          if (!bool) {
            AppBrandLogger.e("NativeNestWebViewLoadBase", new Object[] { "横屏Activity，至少保证小程序能用", new Throwable() });
            AppBrandMonitor.reportPreloadCase("landscape_force_load", 6001);
          } 
          synchronized (this.mLoadLock) {
            if (this.mLoadState < LOAD_STATE_LOADING_TEMPLATE) {
              this.mLoadState = LOAD_STATE_LOADING_TEMPLATE;
              null = new StringBuilder();
              null.append(AppbrandConstant.OpenApi.getInst().getPageFrameFakeURLHost());
              null.append("/page-frame.html");
              null = null.toString();
              ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_loadPreloadTemplate" });
              ((MpTimeLineReporter)this.mApp.getService(MpTimeLineReporter.class)).addPoint("load_pageFrameHtml_begin");
              this.mWebView.loadUrl((String)null);
            } else {
              return;
            } 
          } 
        } else {
          AppBrandLogger.e("NativeNestWebViewLoadBase", new Object[] { "当前屏幕不是竖屏的，不预加载", new Throwable() });
          AppBrandMonitor.reportPreloadCase("landscape_caused_preload_block", 6000);
        } 
      } else {
        showLoadError(ErrorCode.WEBVIEW.TEMPLATE_NOT_FOUND.getCode());
        StringBuilder stringBuilder = new StringBuilder("TemplateFile not found: ");
        stringBuilder.append(file.getAbsolutePath());
        AppBrandLogger.e("NativeNestWebViewLoadBase", new Object[] { stringBuilder.toString() });
        AppBrandMonitor.reportPreloadCase("templatefile_not_found", 6002);
      } 
    } else if (this.mLoadState >= LOAD_STATE_TEMPLATE_LOADED && this.mLoadState < LOAD_STATE_LOADING_PAGE_FRAME) {
      synchronized (this.mLoadLock) {
        if (this.mLoadState < LOAD_STATE_LOADING_PAGE_FRAME && ((LaunchScheduler)this.mApp.getService(LaunchScheduler.class)).isAtLeastLaunching()) {
          this.mLoadState = LOAD_STATE_LOADING_PAGE_FRAME;
          ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_startStreamLoadPageFrame" });
          ((AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class)).addEvent("sendLoadPageFrame");
          this.mWebView.evaluateJavascript("ttJSBridge.subscribeHandler('onPreloadPageFrame');true", new ValueCallback<String>() {
                public void onReceiveValue(String param1String) {
                  TimeLogger timeLogger = (TimeLogger)NativeNestWebViewLoadBase.this.mApp.getService(TimeLogger.class);
                  StringBuilder stringBuilder = new StringBuilder("TTWVStatusCode:");
                  stringBuilder.append(NativeNestWebViewLoadBase.this.getLoadingStatusCode());
                  timeLogger.logTimeDuration(new String[] { "NativeNestWebViewLoadBase_stopStreamLoadPageFrame", param1String, stringBuilder.toString() }, );
                }
              },  "PAGE_FRAME");
          null = this.mApp.getService(TimeLogger.class);
          StringBuilder stringBuilder = new StringBuilder("TTWVStatusCode:");
          stringBuilder.append(getLoadingStatusCode());
          null.logTimeDuration(new String[] { "NativeNestWebViewLoadBase_eval", stringBuilder.toString() });
          this.mWebviewStuckMonitor.start();
        } else {
          TimeLogger timeLogger = (TimeLogger)this.mApp.getService(TimeLogger.class);
          StringBuilder stringBuilder = new StringBuilder("state: ");
          stringBuilder.append(this.mLoadState);
          timeLogger.logTimeDuration(new String[] { "NativeNestWebViewLoadBase_notLoadPageFrame", stringBuilder.toString() });
          return;
        } 
      } 
    } 
    loadPathFrameIfNeed();
  }
  
  public long getLoadingStatusCode() {
    return this.mWebView.getLoadingStatusCode();
  }
  
  public int getWebViewId() {
    return this.mWebViewId;
  }
  
  public void markForceLoadPathFrameJs() {
    synchronized (this.mLoadLock) {
      this.mForceLoadPageFrameJs = true;
      continuePreloadIfNeed();
      return;
    } 
  }
  
  public void onDOMReady() {
    InnerEventHelper.mpPageLoadResult(this.mPageContent, "success", TimeMeter.stop(this.mPageLoadStartTime), CharacterUtils.empty());
    RouterMonitorTask.completedPageRouter(this.mPageUrl);
  }
  
  public void onFirstContentfulPaint() {
    WebviewStuckMonitor webviewStuckMonitor = this.mWebviewStuckMonitor;
    if (webviewStuckMonitor != null)
      webviewStuckMonitor.stop(); 
  }
  
  public void onLoadApp(String paramString) {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_onLoadApp" });
    StringBuilder stringBuilder = new StringBuilder("ttJSBridge.subscribeHandler('onLoadApp',");
    stringBuilder.append(paramString);
    stringBuilder.append(")");
    paramString = stringBuilder.toString();
    this.mWebView.evaluateJavascript(paramString, (ValueCallback<String>)null);
  }
  
  public void onWebviewReady() {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_onWebviewReady" });
    synchronized (this.mLoadLock) {
      this.mLoadState = LOAD_STATE_PAGE_FRAME_LOADED;
      ((LaunchScheduler)this.mApp.getService(LaunchScheduler.class)).onWebViewReady();
      return;
    } 
  }
  
  public void publishSnapShotData(String paramString) {
    if (this.mLoadState < LOAD_STATE_TEMPLATE_LOADED)
      synchronized (this.mLoadLock) {
        if (this.mLoadState < LOAD_STATE_TEMPLATE_LOADED) {
          this.mSnapShotData = paramString;
          return;
        } 
      }  
    sendSnapShotData(paramString);
  }
  
  public void sendOnAppRoute() {
    RouteEventCtrl routeEventCtrl = this.mApp.getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.onAppRoute(new IRouteEvent.RouteEventBean(this.mWebViewId, this.mPageContent, this.mPageQuery, this.mOpenType)); 
  }
  
  public void sendSnapShotData(String paramString) {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NativeNestWebViewLoadBase_sendSnapShotData" });
    StringBuilder stringBuilder = new StringBuilder("ttJSBridge.subscribeHandler('onRenderSnapshot',");
    stringBuilder.append(paramString);
    stringBuilder.append(")");
    paramString = stringBuilder.toString();
    this.mWebView.evaluateJavascript(paramString, (ValueCallback<String>)null);
  }
  
  public void setOpenType(String paramString) {
    this.mOpenType = paramString;
  }
  
  public void updateArgument(String paramString1, String paramString2, String paramString3, String paramString4) {
    this.mOpenType = paramString1;
    this.mPageUrl = paramString2;
    this.mPageContent = paramString3;
    this.mPageQuery = paramString4;
    synchronized (this.mLoadLock) {
      boolean bool = this.mArgumentReady;
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (!bool) {
        this.mArgumentReady = true;
        bool1 = bool2;
        if (this.mLoadState >= LOAD_STATE_TEMPLATE_LOADED)
          bool1 = true; 
      } 
      if (bool1)
        sendOnAppRoute(); 
      null = this.mApp.getService(LoadPathInterceptor.class);
      paramString2 = this.mPathFramePlaceHolder;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString3);
      stringBuilder.append("-frame.js");
      null.updateRealPath(paramString2, stringBuilder.toString());
      continuePreloadIfNeed();
      this.mWebviewStuckMonitor.setPageUrl(this.mPageUrl);
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\NativeNestWebViewLoadBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */