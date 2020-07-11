package com.tt.miniapp.adsite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Choreographer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.c;
import com.tt.frontendapiinterface.e;
import com.tt.frontendapiinterface.k;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.BaseActivityProxy;
import com.tt.miniapp.ImmersedStatusBarHelper;
import com.tt.miniapp.TTAppbrandTabUI;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.badcase.BlockPageManager;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.component.nativeview.NativeAdWebView;
import com.tt.miniapp.debug.PerformanceService;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.event.external.search.SearchEventHelper;
import com.tt.miniapp.extraWeb.ComponentWebViewRender;
import com.tt.miniapp.guide.ReenterGuideHelper;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.launchschedule.ILaunchProgressListener;
import com.tt.miniapp.monitor.BaseMonitorTask;
import com.tt.miniapp.monitor.FpsMonitorTask;
import com.tt.miniapp.monitor.MiniAppPerformanceDialog;
import com.tt.miniapp.monitor.MonitorHandler;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlebar.BaseTitleBar;
import com.tt.miniapp.titlebar.ITitleBar;
import com.tt.miniapp.user.TmaUserManager;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.SystemInfoUtil;
import com.tt.miniapp.util.TimeLineReporter;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.LaunchLoadingView;
import com.tt.miniapp.view.MiniAppContainerView;
import com.tt.miniapp.view.SizeDetectFrameLayout;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.ModeManager;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageChangeListener;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.n.c;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class AdSiteBrowser extends BaseActivityProxy implements e, k, ILaunchProgressListener, SystemInfoUtil.IActivityRootView, SizeDetectFrameLayout.IWindowSizeChangeListener, LanguageChangeListener {
  private List<k> IKeyboardObserverList = new ArrayList<k>();
  
  public KeyboardHeightProvider keyboardHeightProvider;
  
  private IActivityResultHandler mActivityResultHandler;
  
  private NativeAdWebView mAdWebView;
  
  private View mCloseBtn;
  
  private ImmersedStatusBarHelper mImmersedStatusBarHelper;
  
  private boolean mNeedReportResultOnResume;
  
  private boolean mReportedFirstDOMReady;
  
  public MiniAppContainerView mRootLayout;
  
  public AdSiteTitleBar mTitleBar;
  
  public AdSiteBrowser(FragmentActivity paramFragmentActivity) {
    super(paramFragmentActivity);
    ((TimeLineReporter)this.mApp.getService(TimeLineReporter.class)).recordLaunchStartTime();
  }
  
  private void doBack(String paramString1, String paramString2, boolean paramBoolean, int paramInt) {
    this.mApp.setStopReason(paramString1);
    EventParamsValue.PARAMS_EXIT_TYPE = paramString2;
    EventParamsValue.IS_OTHER_FLAG = paramBoolean;
    exitInternal(paramInt);
  }
  
  private void doPause() {
    this.mAdWebView.onPause();
    this.keyboardHeightProvider.setKeyboardHeightObserver(null);
  }
  
  private void doResume() {
    this.mTitleBar.updateLeftViewVisibility();
    this.mAdWebView.onResume();
    this.keyboardHeightProvider.setKeyboardHeightObserver(this);
    reportResultIfNeed();
  }
  
  private void exitInternal(final int exitType) {
    Runnable runnable = new Runnable() {
        public void run() {
          ToolUtils.onActivityExit((Activity)AdSiteBrowser.this.mActivity, exitType);
          AppBrandLogger.d("AdSiteBrowser", new Object[] { " moveTaskToBack " });
        }
      };
    if (exitType != 10) {
      ReenterGuideHelper.checkReenterGuideTip((Activity)this.mActivity, runnable);
      return;
    } 
    runnable.run();
  }
  
  private ImmersedStatusBarHelper.ImmersedStatusBarConfig getImmersedStatusBarConfig() {
    return new ImmersedStatusBarHelper.ImmersedStatusBarConfig();
  }
  
  private void hideLoadShowUI() {
    if (!this.isShowingLoadingView)
      return; 
    this.isShowingLoadingView = false;
    this.mLaunchLoadingView.removeLoadingLayout();
  }
  
  private void initLoadingView() {
    this.keyboardHeightProvider = new KeyboardHeightProvider((Activity)this.mActivity);
    this.mRootLayout = (MiniAppContainerView)this.mActivity.findViewById(2097545471);
    this.mRootLayout.setWindowSizeListener(this);
    this.mRootLayout.post(new Runnable() {
          public void run() {
            AdSiteBrowser.this.keyboardHeightProvider.start();
          }
        });
    this.mLaunchLoadingView = (LaunchLoadingView)((PreloadManager)this.mApp.getService(PreloadManager.class)).getPreloadedView(1);
    this.mLaunchLoadingView.setLoadStartTime(this.mLoadStartTime);
    this.mLaunchLoadingView.initWithActivity((Activity)this.mActivity);
    this.mLaunchLoadingView.hideBottomTip();
    final RenderSnapShotManager renderSnapShotManager = (RenderSnapShotManager)this.mApp.getService(RenderSnapShotManager.class);
    if (renderSnapShotManager.isSnapShotRender()) {
      if (!TextUtils.isEmpty(renderSnapShotManager.getSnapShotErrorArgs())) {
        this.mRootLayout.addView((View)this.mLaunchLoadingView);
        postError(renderSnapShotManager.getSnapShotErrorArgs());
      } else {
        AppbrandContext.mainHandler.postDelayed(new Runnable() {
              public void run() {
                if (!renderSnapShotManager.isSnapShotReady()) {
                  TimeLogger.getInstance().logTimeDuration(new String[] { "AdSiteBrowser_firstrender_showloading" }, );
                  AdSiteBrowser.this.mRootLayout.addView((View)AdSiteBrowser.this.mLaunchLoadingView);
                } 
              }
            },  1000L);
      } 
    } else {
      this.mRootLayout.addView((View)this.mLaunchLoadingView);
    } 
    this.mCloseBtn = this.mLaunchLoadingView.findViewById(2097545405);
    this.mLaunchLoadingView.updateViews(this.mApp.getAppInfo());
    this.mCloseBtn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Event.builder("mp_close_btn_click").flush();
            AppBrandLogger.e("AdSiteBrowser", new Object[] { "mCloseBtn" });
            if (UIUtils.isViewVisible((View)AdSiteBrowser.this.mLaunchLoadingView) && AdSiteBrowser.this.mApp.getAppInfo() != null) {
              AppBrandLogger.d("AdSiteBrowser", new Object[] { "onBackPressed cancel" });
              InnerEventHelper.mpLoadResultInner(TimeMeter.stop(AdSiteBrowser.this.mLoadStartTime), "cancel", "exit_close", AdSiteBrowser.this.mLaunchProfileTime.getTime(), TimeMeter.stop(AdSiteBrowser.this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
            } 
            AdSiteBrowser.this.mApp.setStopReason("click_close_btn");
            EventParamsValue.PARAMS_EXIT_TYPE = "btn";
            EventParamsValue.IS_OTHER_FLAG = false;
            ToolUtils.onActivityExit((Activity)AdSiteBrowser.this.mActivity, 2);
          }
        });
  }
  
  public static void preload(Context paramContext) {
    AdSiteManager.getInstance().preload(paramContext);
  }
  
  private void reportFirstDOMReady() {
    if (this.mReportedFirstDOMReady)
      return; 
    this.mReportedFirstDOMReady = true;
    this.mLaunchProfileTime.pause();
    InnerEventHelper.mpLoadDomReady(TimeMeter.nowAfterStart(this.mLoadStartTime), this.mLaunchProfileTime.getTime(), TimeMeter.nowAfterStart(this.mEntranceClickTimeMeter), this.mHasActivityStoped);
  }
  
  private void reportResult() {
    LoadStateManager.getIns().stopRenderTime();
    TimeMeter.stop(this.mLoadStartTime);
    TimeMeter.stop(this.mEntranceClickTimeMeter);
    SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 1, CharacterUtils.empty());
    if (!this.mApp.getForeBackgroundManager().isBackground()) {
      LoadStateManager.getIns().reportPreloadResult("success");
      InnerEventHelper.mpLoadResultSuccess(TimeMeter.stop(this.mLoadStartTime), this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState(), this.mHasActivityStoped);
      this.mLaunchDuration = this.mLoadingViewShowTimes.getTime();
      MiniAppPerformanceDialog.saveLaunchTime(this.mLaunchProfileTime.getTime());
      InnerEventHelper.mpLaunch(this.mLaunchDuration);
      return;
    } 
    this.mNeedReportResultOnResume = true;
  }
  
  private void reportResultIfNeed() {
    if (!this.mNeedReportResultOnResume)
      return; 
    this.mNeedReportResultOnResume = false;
    LoadStateManager.getIns().reportPreloadResult("success");
    InnerEventHelper.mpLoadResultSuccess(TimeMeter.stop(this.mLoadStartTime), this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState(), this.mHasActivityStoped);
    this.mLaunchDuration = this.mLoadingViewShowTimes.getTime();
    InnerEventHelper.mpLaunch(this.mLaunchDuration);
  }
  
  private void showFirstPage() {
    TimeLogger.getInstance().logTimeDuration(new String[] { "AdSiteBrowser_showFirstPage" });
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "onSuccess ", Long.valueOf(this.mLoadStartTime.getMillisAfterStart()) });
    reportResult();
    onDOMReady();
    hideLoadShowUI();
    startModule();
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    HostProcessBridge.onMiniAppStart(appInfoEntity, appInfoEntity.isGame(), getScreenOrientationFromAppConfig());
    this.mMicroAppStartShowTimes = System.currentTimeMillis();
    InnerHostProcessBridge.setTmaLaunchFlag();
  }
  
  public View findViewById(int paramInt) {
    return (this.mHomeLayout != null) ? this.mHomeLayout.findViewById(paramInt) : null;
  }
  
  public View getActivityRootView() {
    return (this.mActivity == null) ? null : this.mActivity.findViewById(16908290);
  }
  
  public FrameLayout getRootView() {
    return (FrameLayout)this.mRootLayout;
  }
  
  public ITitleBar getTitleBar() {
    AdSiteTitleBar adSiteTitleBar = this.mTitleBar;
    return (ITitleBar)((adSiteTitleBar != null) ? adSiteTitleBar : BaseTitleBar.EMPTY);
  }
  
  public void goback() {
    onBackPressed();
  }
  
  public void initPerformanceDialog() {
    if (this.mApp.getAppInfo() != null) {
      if (!this.mApp.getAppInfo().isLocalTest())
        return; 
      if (((SwitchManager)this.mApp.getService(SwitchManager.class)).isPerformanceSwitchOn())
        MiniAppPerformanceDialog.showPerformanceDialog((Context)this.mActivity, new MiniAppPerformanceDialog.IDismissCallback() {
              public void onDismiss() {}
            }); 
    } 
  }
  
  public void initView() {
    TimeLogger.getInstance().logTimeDuration(new String[] { "AdSiteBrowser_initView" });
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "initView " });
    showFirstPage();
    this.mTitleBar.initView(null);
    this.mTitleBar.makeStatusBar();
    String str = AdSiteManager.getInstance().buildUrlFromSchema(this.mApp.getSchema()).build().toString();
    this.mAdWebView.loadUrl(str, true);
  }
  
  public void miniAppDownloadInstallFail(String paramString1, String paramString2) {
    super.miniAppDownloadInstallFail(paramString1, paramString2);
    SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 0, SearchEventHelper.failReasonClient("download_fail"));
  }
  
  public void miniAppDownloadInstallProgress(int paramInt) {}
  
  public void miniAppInstallSuccess() {
    TimeLogger.getInstance().logTimeDuration(new String[] { "AdSiteBrowser_miniAppInstallSuccess" });
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "miniAppInstallSuccess " });
  }
  
  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    Map map1;
    if (TmaUserManager.getInstance().handleActivityLoginResult(paramInt1, paramInt2, paramIntent))
      return true; 
    Map map2 = ModeManager.getInst().getModules();
    if (map2 != null) {
      Iterator<NativeModule> iterator1 = map2.values().iterator();
      while (iterator1.hasNext()) {
        if (((NativeModule)iterator1.next()).onActivityResult(paramInt1, paramInt2, paramIntent))
          return true; 
      } 
    } 
    boolean bool2 = false;
    if (paramInt1 == 5 && paramInt2 == 51 && paramIntent != null) {
      map1 = (Map)paramIntent.getSerializableExtra("extra_change_permission_map");
      for (Map.Entry entry : map1.entrySet()) {
        BrandPermissionUtils.setPermission(((Integer)entry.getKey()).intValue(), ((Boolean)entry.getValue()).booleanValue());
        AppBrandLogger.d("AdSiteBrowser", new Object[] { "change permission ", entry.getKey(), " ", entry.getValue() });
      } 
      if (map1.size() > 0)
        HostDependManager.getInst().syncPermissionToService(); 
      return true;
    } 
    if (paramInt1 == 11) {
      c c = this.mAdWebView.getFileChooseHandler();
      if (c != null)
        c.onActivityResult(paramInt1, paramInt2, (Intent)map1); 
    } 
    Iterator<?> iterator = (new ArrayList((c.a()).a)).iterator();
    while (iterator.hasNext()) {
      if (((b)iterator.next()).onActivityResult(paramInt1, paramInt2, (Intent)map1))
        bool2 = true; 
    } 
    boolean bool1 = bool2;
    if (!bool2) {
      b b = (c.a()).b;
      bool1 = bool2;
      if (b != null)
        bool1 = b.onActivityResult(paramInt1, paramInt2, (Intent)map1); 
    } 
    bool2 = bool1;
    if (!bool1) {
      IActivityResultHandler iActivityResultHandler = this.mActivityResultHandler;
      bool2 = bool1;
      if (iActivityResultHandler != null)
        bool2 = iActivityResultHandler.handleActivityResult(paramInt1, paramInt2, (Intent)map1); 
    } 
    return bool2;
  }
  
  public void onAddVideoFragment() {}
  
  public void onBackPressed() {
    if (this.isShowingLoadingView) {
      AppBrandLogger.d("AdSiteBrowser", new Object[] { "onBackPressed cancel" });
      InnerEventHelper.mpLoadResultInner(TimeMeter.stop(this.mLoadStartTime), "cancel", "exit_back", this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
      ToolUtils.onActivityExit((Activity)this.mActivity, 9);
      return;
    } 
    if (consumeBackPress())
      return; 
    if (!this.mAdWebView.onBackPressed())
      doBack("backpress", "back", false, 9); 
    dismissFavoriteGuide();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    TimeLogger.getInstance().logTimeDuration(new String[] { "AdSiteBrowser_onCreate" });
    View view = AdSiteManager.getInstance().getLoadingView();
    if (view == null) {
      try {
        view = LayoutInflater.from(this.mActivity.getApplicationContext()).inflate(2097676290, null);
        this.mActivity.setContentView(view);
      } finally {
        view = null;
        AppBrandLogger.e("AdSiteBrowser", new Object[] { view });
        try {
          JSONObject jSONObject = new JSONObject();
          jSONObject.put("errMsg", "microapp setContentView fail ");
          jSONObject.put("throwable", view.toString());
          AppBrandMonitor.statusRate("mp_start_error", 5000, jSONObject);
          Thread.sleep(200L);
        } catch (Exception exception) {
          AppBrandLogger.e("AdSiteBrowser", new Object[] { view });
        } 
        this.mActivity.finish();
      } 
    } else {
      UIUtils.removeParentView(view);
      this.mActivity.setContentView(view);
    } 
    this.mImmersedStatusBarHelper = new ImmersedStatusBarHelper((Activity)this.mActivity, getImmersedStatusBarConfig());
    this.mImmersedStatusBarHelper.setup(true);
    this.mImmersedStatusBarHelper.setUseLightStatusBarInternal(true);
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    SearchEventHelper.init(appInfoEntity.launchFrom, this.mApp.getSchema());
    Event.builder("mp_load_start").kv("launch_type", appInfoEntity.launchType).flush();
    SearchEventHelper.onLoadStart();
    initLoadingView();
    getLaunchScheduler().startListenLaunchStatus(this);
    this.mApp.setActivityLife(this);
    MonitorHandler monitorHandler = ((PerformanceService)this.mApp.getService(PerformanceService.class)).getMonitorHandler();
    if (monitorHandler != null)
      monitorHandler.addTask((BaseMonitorTask)new FpsMonitorTask(Choreographer.getInstance())); 
    UIUtils.setActivityOrientation((Activity)this.mActivity, 1);
    LocaleManager.getInst().registerLangChangeListener(this);
    ((BlockPageManager)this.mApp.getService(BlockPageManager.class)).handleColdLaunch();
    SystemInfoUtil.setActivityRootViewCallBack(this);
    this.mTitleBar = new AdSiteTitleBar((Context)AppbrandContext.getInst().getApplicationContext(), (ViewGroup)this.mRootLayout);
    this.mAdWebView = AdSiteManager.getInstance().getWebView((Context)this.mActivity);
    this.mAdWebView.bind((WebViewManager.IRender)new ComponentWebViewRender(this.mApp, this.mAdWebView.getWebView(), this.mAdWebView.getWebViewId()) {
          public Activity getCurrentActivity() {
            return (Activity)AdSiteBrowser.this.mActivity;
          }
          
          public c getFileChooseHandler() {
            return HostDependManager.getInst().createChooseFileHandler((Activity)AdSiteBrowser.this.mActivity);
          }
          
          public void onNativeWebViewPageFinished(boolean param1Boolean) {
            AdSiteBrowser.this.mTitleBar.setWebViewPageBackView(param1Boolean);
          }
          
          public void updateWebTitle(String param1String, boolean param1Boolean) {
            AppBrandLogger.d("AdSiteBrowser", new Object[] { "updateWebTitle", param1String });
            AdSiteBrowser.this.mTitleBar.updateWebTitle(param1String, param1Boolean);
          }
        });
    ((FrameLayout)this.mRootLayout.findViewById(2097545353)).addView((View)this.mAdWebView, -1, -1);
  }
  
  public void onDOMReady() {
    reportFirstDOMReady();
  }
  
  public void onDestroy() {
    super.onDestroy();
    KeyboardHeightProvider keyboardHeightProvider = this.keyboardHeightProvider;
    if (keyboardHeightProvider != null)
      keyboardHeightProvider.close(); 
    getLaunchScheduler().stopListenLaunchStatus();
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "onDestroy" });
    InnerEventHelper.mpTechnologyMsg("micro app onDestroy called");
  }
  
  public void onEnvironmentReady() {
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "onEnvironmentReady ", Long.valueOf(this.mLoadStartTime.getMillisAfterStart()) });
    TimeLogger.getInstance().logTimeDuration(new String[] { "AdSiteBrowser_onEnvironmentReady" });
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            AdSiteBrowser.this.initView();
            TTAppbrandTabUI.initFavoriteList();
            AdSiteBrowser.this.initPerformanceDialog();
          }
        });
  }
  
  public void onFirstContentfulPaint(long paramLong) {}
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return false;
  }
  
  public void onKeyboardHeightChanged(int paramInt1, int paramInt2) {
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "onKeyboardHeightChanged height ", Integer.valueOf(paramInt1), " orientation ", Integer.valueOf(paramInt2) });
    Iterator<k> iterator = this.IKeyboardObserverList.iterator();
    while (iterator.hasNext())
      ((k)iterator.next()).onKeyboardHeightChanged(paramInt1, paramInt2); 
  }
  
  public void onLanguageChange() {
    if (this.mApp.getAppInfo() != null)
      this.mLaunchLoadingView.updateViews(this.mApp.getAppInfo()); 
  }
  
  public void onNewIntent(Intent paramIntent) {
    String str = this.mApp.getSchema();
    super.onNewIntent(paramIntent);
    if (paramIntent == null || !paramIntent.hasExtra("microapp_url")) {
      AppBrandLogger.e("AdSiteBrowser", new Object[] { "onNewIntent fail, intent == ", paramIntent });
      return;
    } 
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity == null) {
      AppBrandLogger.e("AdSiteBrowser", new Object[] { "onNewIntent fail, mAppInfo is null " });
      return;
    } 
    SearchEventHelper.init(appInfoEntity.launchFrom, this.mApp.getSchema());
    SearchEventHelper.onRelaunched();
    ((BlockPageManager)this.mApp.getService(BlockPageManager.class)).handleHotLaunch();
    if (!TextUtils.isEmpty(appInfoEntity.startPage)) {
      String str1 = AdSiteManager.getInstance().buildUrlFromSchema(str).toString();
      str = AdSiteManager.getInstance().buildUrlFromSchema(this.mApp.getSchema()).toString();
      if (!TextUtils.equals(str, str1)) {
        this.mAdWebView.loadUrl(str, true);
        PermissionsManager permissionsManager = PermissionsManager.getInstance();
        if (permissionsManager != null) {
          permissionsManager.forceFlushPendingRequest((Activity)this.mActivity, false);
          return;
        } 
      } else {
        PermissionsManager permissionsManager = PermissionsManager.getInstance();
        if (permissionsManager != null)
          permissionsManager.forceFlushPendingRequest((Activity)this.mActivity, true); 
        return;
      } 
    } else {
      PermissionsManager permissionsManager = PermissionsManager.getInstance();
      if (permissionsManager != null)
        permissionsManager.forceFlushPendingRequest((Activity)this.mActivity, true); 
    } 
  }
  
  public void onPause() {
    super.onPause();
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "onPause" });
    doPause();
  }
  
  public void onProgressChanged(int paramInt) {
    updateProgressTv(0, paramInt);
  }
  
  public void onRemoteDebugOpen() {}
  
  public void onRemoveVideoFragment() {}
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    if ((paramInt >> 16 & 0xFFFF) == 0)
      PermissionsManager.getInstance().notifyPermissionsChange((Activity)this.mActivity, paramArrayOfString, paramArrayOfint); 
  }
  
  public void onResume() {
    super.onResume();
    AppBrandLogger.d("AdSiteBrowser", new Object[] { "onResume" });
    doResume();
  }
  
  public void onSnapShotDOMReady() {}
  
  public void onUserInteraction() {}
  
  public void onWindowSizeChange(int paramInt1, int paramInt2) {}
  
  public void postError(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      StringBuilder stringBuilder = new StringBuilder("WebView postError：");
      stringBuilder.append(paramString);
      jSONObject.put("errMsg", stringBuilder.toString());
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("AdSiteBrowser", "postError", exception);
    } 
    AppBrandMonitor.statusRate("mp_start_error", 5000, jSONObject);
    if (!this.mReportedFirstDOMReady) {
      TimeLogger.getInstance().logTimeDuration(new String[] { "AdSiteBrowser_web_view_post_error", paramString });
      LoadHelper.handleMiniProcessFail(ErrorCode.WEBVIEW.RECEIVE_WEBVIEW_ERROR.getCode());
    } 
  }
  
  public void registerKeyboardListener(k paramk) {
    if (paramk != null && !this.IKeyboardObserverList.contains(paramk))
      this.IKeyboardObserverList.add(paramk); 
  }
  
  public void setActivityResultHandler(IActivityResultHandler paramIActivityResultHandler) {
    this.mActivityResultHandler = paramIActivityResultHandler;
  }
  
  public void setKeepScreenOn(boolean paramBoolean) {
    this.mRootLayout.setKeepScreenOn(paramBoolean);
  }
  
  public void unRegisterKeyboardListener(k paramk) {
    if (paramk != null)
      this.IKeyboardObserverList.remove(paramk); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\adsite\AdSiteBrowser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */