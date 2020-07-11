package com.tt.miniapp;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Choreographer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.c;
import com.tt.frontendapiinterface.e;
import com.tt.frontendapiinterface.k;
import com.tt.miniapp.ad.app.video.VideoAdMgr;
import com.tt.miniapp.badcase.BlockPageManager;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.base.ui.viewwindow.ViewWindow;
import com.tt.miniapp.debug.PerformanceService;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.event.external.search.SearchEventHelper;
import com.tt.miniapp.exit.AppBrandExitManager;
import com.tt.miniapp.guide.ReenterGuideHelper;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.jsbridge.JsRuntimeManager;
import com.tt.miniapp.keyboarddetect.KeyboardHeightProvider;
import com.tt.miniapp.launch.MiniAppLaunchConfig;
import com.tt.miniapp.launchschedule.ILaunchProgressListener;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.monitor.BaseMonitorTask;
import com.tt.miniapp.monitor.FpsMonitorTask;
import com.tt.miniapp.monitor.MiniAppPerformanceDialog;
import com.tt.miniapp.monitor.MonitorHandler;
import com.tt.miniapp.msg.favorite.ApiGetFavoritesList;
import com.tt.miniapp.page.AppbrandHomePageViewWindow;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandViewWindowBase;
import com.tt.miniapp.page.AppbrandViewWindowRoot;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.route.RouteEventCtrl;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlebar.BaseTitleBar;
import com.tt.miniapp.titlebar.ITitleBar;
import com.tt.miniapp.titlebar.TitleBarControl;
import com.tt.miniapp.user.TmaUserManager;
import com.tt.miniapp.util.JsCoreUtils;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.SystemInfoUtil;
import com.tt.miniapp.util.TimeLineReporter;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.LaunchLoadingView;
import com.tt.miniapp.view.MiniAppContainerView;
import com.tt.miniapp.view.SizeDetectFrameLayout;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapp.webapp.TTWebAppViewWindow;
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
import com.tt.option.ad.h;
import com.tt.option.n.c;
import d.f.a.a;
import d.x;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class TTAppbrandTabUI extends BaseActivityProxy implements e, k, ILaunchProgressListener, SystemInfoUtil.IActivityRootView, SizeDetectFrameLayout.IWindowSizeChangeListener, LanguageChangeListener {
  private List<k> IKeyboardObserverList = new ArrayList<k>();
  
  private boolean isTabRemove;
  
  public KeyboardHeightProvider keyboardHeightProvider;
  
  private IActivityResultHandler mActivityResultHandler;
  
  private View mCloseBtn;
  
  private ImmersedStatusBarHelper mImmersedStatusBarHelper;
  
  private boolean mNeedReportResultOnResume;
  
  private boolean mReportedFCP;
  
  private boolean mReportedFirstDOMReady;
  
  public MiniAppContainerView mRootLayout;
  
  private int mSavedScreenOrientation = -1;
  
  private VideoAdMgr mVideoAdMgr;
  
  public FrameLayout mWindowRootContainer;
  
  public TTAppbrandTabUI(FragmentActivity paramFragmentActivity) {
    super(paramFragmentActivity);
    ((TimeLineReporter)this.mApp.<TimeLineReporter>getService(TimeLineReporter.class)).recordLaunchStartTime();
  }
  
  private void doPause() {
    this.keyboardHeightProvider.setKeyboardHeightObserver(null);
    this.mSavedScreenOrientation = this.mActivity.getRequestedOrientation();
  }
  
  private void doResume() {
    if (this.mSavedScreenOrientation != this.mActivity.getRequestedOrientation())
      UIUtils.setActivityOrientation((Activity)this.mActivity, this.mSavedScreenOrientation); 
    this.keyboardHeightProvider.setKeyboardHeightObserver(this);
    reportResultIfNeed();
  }
  
  private void exitInternal(final int exitType) {
    Runnable runnable = new Runnable() {
        public void run() {
          ToolUtils.onActivityExit((Activity)TTAppbrandTabUI.this.mActivity, exitType);
          AppBrandLogger.d("TTAppbrandTabUI", new Object[] { " moveTaskToBack " });
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
  
  private String handleStartPage(String paramString) {
    return !TextUtils.isEmpty(paramString) ? ((LaunchScheduler)this.mApp.<LaunchScheduler>getService(LaunchScheduler.class)).getNormalStartPage(paramString) : "";
  }
  
  private boolean hasNativeWebView() {
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getTopView();
    if (appbrandViewWindowBase != null) {
      AppbrandSinglePage appbrandSinglePage = appbrandViewWindowBase.getCurrentPage();
      if (appbrandSinglePage != null && appbrandSinglePage.getNativeViewManager().getCurrentNativeWebView() != null)
        return true; 
    } 
    return false;
  }
  
  public static void initFavoriteList() {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            boolean bool;
            if (InnerHostProcessBridge.getFavoriteSet() == null) {
              bool = true;
            } else {
              bool = false;
            } 
            if (bool && (UserInfoManager.getHostClientUserInfo()).isLogin)
              ApiGetFavoritesList.getCurrentUserMiniAppFavoriteListFromNet(); 
          }
        },  Schedulers.longIO());
  }
  
  private void initLoadingView() {
    this.mLaunchLoadingView = (LaunchLoadingView)((PreloadManager)this.mApp.<PreloadManager>getService(PreloadManager.class)).getPreloadedView(1);
    this.mLaunchLoadingView.setLoadStartTime(this.mLoadStartTime);
    this.mLaunchLoadingView.initWithActivity((Activity)this.mActivity);
    this.mLaunchLoadingView.hideBottomTip();
    final RenderSnapShotManager renderSnapShotManager = this.mApp.<RenderSnapShotManager>getService(RenderSnapShotManager.class);
    if (renderSnapShotManager.isSnapShotRender()) {
      if (!TextUtils.isEmpty(renderSnapShotManager.getSnapShotErrorArgs())) {
        this.mRootLayout.addView((View)this.mLaunchLoadingView);
        postError(renderSnapShotManager.getSnapShotErrorArgs());
      } else {
        this.isShowingLoadingView = false;
        AppbrandContext.mainHandler.postDelayed(new Runnable() {
              public void run() {
                if (!renderSnapShotManager.isSnapShotReady()) {
                  ((TimeLogger)TTAppbrandTabUI.this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "TTAppbrandTabUI_firstrender_showloading" }, );
                  TTAppbrandTabUI tTAppbrandTabUI = TTAppbrandTabUI.this;
                  tTAppbrandTabUI.isShowingLoadingView = true;
                  tTAppbrandTabUI.mRootLayout.addView((View)TTAppbrandTabUI.this.mLaunchLoadingView);
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
            AppBrandLogger.e("TTAppbrandTabUI", new Object[] { "mCloseBtn" });
            if (UIUtils.isViewVisible((View)TTAppbrandTabUI.this.mLaunchLoadingView) && TTAppbrandTabUI.this.mApp.getAppInfo() != null) {
              AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onBackPressed cancel" });
              InnerEventHelper.mpLoadResultInner(TimeMeter.stop(TTAppbrandTabUI.this.mLoadStartTime), "cancel", "exit_close", TTAppbrandTabUI.this.mLaunchProfileTime.getTime(), TimeMeter.stop(TTAppbrandTabUI.this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
            } 
            TTAppbrandTabUI.this.mApp.setStopReason("click_close_btn");
            EventParamsValue.PARAMS_EXIT_TYPE = "btn";
            EventParamsValue.IS_OTHER_FLAG = false;
            ToolUtils.onActivityExit((Activity)TTAppbrandTabUI.this.mActivity, 2);
          }
        });
  }
  
  private void initPerformanceDialog() {
    if (this.mApp.getAppInfo() != null) {
      if (!this.mApp.getAppInfo().isLocalTest())
        return; 
      if (((SwitchManager)this.mApp.<SwitchManager>getService(SwitchManager.class)).isPerformanceSwitchOn())
        MiniAppPerformanceDialog.showPerformanceDialog((Context)this.mActivity, new MiniAppPerformanceDialog.IDismissCallback() {
              public void onDismiss() {}
            }); 
    } 
  }
  
  public static void preload(Context paramContext) {}
  
  private void reportFirstDOMReady() {
    if (this.mReportedFirstDOMReady)
      return; 
    this.mReportedFirstDOMReady = true;
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
  
  private void switchToLibraMiniApp(final AppInfoEntity appInfo) {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "requestAppInfoSuccess isWebApp" });
            TTWebAppViewWindow tTWebAppViewWindow = TTWebAppViewWindow.createInstance((Context)TTAppbrandTabUI.this.getActivity(), TTAppbrandTabUI.this, appInfo);
            AppbrandViewWindowRoot appbrandViewWindowRoot = ((PageRouter)TTAppbrandTabUI.this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot();
            appbrandViewWindowRoot.getAppbrandHomePage().setDragEnable(true);
            appbrandViewWindowRoot.showViewWindow((ViewWindow)tTWebAppViewWindow, null);
            if (appbrandViewWindowRoot.getContainer().getParent() != TTAppbrandTabUI.this.mWindowRootContainer) {
              UIUtils.removeParentView((View)appbrandViewWindowRoot.getContainer());
              TTAppbrandTabUI.this.mWindowRootContainer.addView((View)appbrandViewWindowRoot.getContainer());
            } 
          }
        });
  }
  
  public void doBack(String paramString1, String paramString2, boolean paramBoolean, int paramInt) {
    this.mApp.setStopReason(paramString1);
    EventParamsValue.PARAMS_EXIT_TYPE = paramString2;
    EventParamsValue.IS_OTHER_FLAG = paramBoolean;
    exitInternal(paramInt);
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
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getTopView();
    return (ITitleBar)((appbrandViewWindowBase == null || appbrandViewWindowBase.getCurrentPage() == null) ? BaseTitleBar.EMPTY : appbrandViewWindowBase.getCurrentPage().getTitleBar());
  }
  
  public void goback() {
    onBackPressed();
  }
  
  public void hideLoadShowUI() {
    if (!this.isShowingLoadingView)
      return; 
    this.isShowingLoadingView = false;
    this.mLaunchLoadingView.removeLoadingLayout();
    initAnchor();
  }
  
  public void miniAppDownloadInstallFail(String paramString1, String paramString2) {
    super.miniAppDownloadInstallFail(paramString1, paramString2);
    SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 0, SearchEventHelper.failReasonClient("download_fail"));
  }
  
  public void miniAppDownloadInstallProgress(int paramInt) {}
  
  public void miniAppInstallSuccess() {
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "TTAppbrandTabUI_miniAppInstallSuccess" });
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
        AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "change permission ", entry.getKey(), " ", entry.getValue() });
      } 
      if (map1.size() > 0)
        HostDependManager.getInst().syncPermissionToService(); 
      return true;
    } 
    if (paramInt1 == 11) {
      AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getTopView();
      if (appbrandViewWindowBase != null) {
        AppbrandSinglePage appbrandSinglePage = appbrandViewWindowBase.getCurrentPage();
        if (appbrandSinglePage != null) {
          c c = appbrandSinglePage.getFileChooseHandler();
          if (c != null)
            c.onActivityResult(paramInt1, paramInt2, (Intent)map1); 
        } 
      } 
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
  
  public void onAddVideoFragment() {
    AppbrandHomePageViewWindow appbrandHomePageViewWindow = ((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage();
    this.mVideoAdMgr.setSwipeEnableBefore(appbrandHomePageViewWindow.isDragEnabled());
    appbrandHomePageViewWindow.setDragEnable(false);
    doPause();
  }
  
  public void onBackPressed() {
    if (this.isShowingLoadingView) {
      this.mApp.setStopReason("backpress");
      AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onBackPressed cancel" });
      InnerEventHelper.mpLoadResultInner(TimeMeter.stop(this.mLoadStartTime), "cancel", "exit_back", this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
      ToolUtils.onActivityExit((Activity)this.mActivity, 9);
      return;
    } 
    if (this.mVideoAdMgr.onBack())
      return; 
    if (consumeBackPress())
      return; 
    if (!((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).onBackPressed()) {
      Runnable runnable = new Runnable() {
          public void run() {
            TTAppbrandTabUI.this.doBack("backpress", "back", false, 9);
          }
        };
      if (ApiPermissionManager.shouldCallbackBeforeClose()) {
        AppBrandExitManager.getInst().onBeforeExit(true, runnable);
      } else {
        runnable.run();
      } 
    } 
    dismissFavoriteGuide();
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "TTAppbrandTabUI_onCreate" });
    View view = ((PreloadManager)this.mApp.<PreloadManager>getService(PreloadManager.class)).getPreloadedLoadingView((Activity)this.mActivity, 1);
    if (view == null) {
      view = LayoutInflater.from((Context)AppbrandContext.getInst().getApplicationContext()).inflate(2097676296, null);
      this.mActivity.setContentView(view);
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
    AppbrandViewWindowRoot appbrandViewWindowRoot = ((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot();
    appbrandViewWindowRoot.bindActivity((Activity)this.mActivity);
    appbrandViewWindowRoot.getAppbrandHomePage().setOnDragDispearEndListener(new a<x>() {
          public x invoke() {
            TTAppbrandTabUI.this.onDragFinish();
            return null;
          }
        });
    this.keyboardHeightProvider = new KeyboardHeightProvider((Activity)this.mActivity);
    this.mRootLayout = (MiniAppContainerView)this.mActivity.findViewById(2097545471);
    this.mWindowRootContainer = (FrameLayout)this.mActivity.findViewById(2097545457);
    this.mRootLayout.setWindowSizeListener(this);
    this.mRootLayout.post(new Runnable() {
          public void run() {
            TTAppbrandTabUI.this.keyboardHeightProvider.start();
          }
        });
    if (((RenderSnapShotManager)this.mApp.<RenderSnapShotManager>getService(RenderSnapShotManager.class)).isSnapShotRender() && appbrandViewWindowRoot.getContainer().getParent() != this.mWindowRootContainer) {
      UIUtils.removeParentView((View)appbrandViewWindowRoot.getContainer());
      this.mWindowRootContainer.addView((View)appbrandViewWindowRoot.getContainer());
    } 
    initLoadingView();
    getLaunchScheduler().startListenLaunchStatus(this);
    this.mApp.setActivityLife(this);
    MonitorHandler monitorHandler = ((PerformanceService)this.mApp.<PerformanceService>getService(PerformanceService.class)).getMonitorHandler();
    if (monitorHandler != null)
      monitorHandler.addTask((BaseMonitorTask)new FpsMonitorTask(Choreographer.getInstance())); 
    UIUtils.setActivityOrientation((Activity)this.mActivity, 1);
    this.mSavedScreenOrientation = this.mActivity.getRequestedOrientation();
    LocaleManager.getInst().registerLangChangeListener(this);
    ((BlockPageManager)this.mApp.<BlockPageManager>getService(BlockPageManager.class)).handleColdLaunch();
    SystemInfoUtil.setActivityRootViewCallBack(this);
    this.mVideoAdMgr = new VideoAdMgr(this);
    this.mRootLayout.configStyle((Activity)getActivity(), TitleBarControl.getInst(), AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig());
  }
  
  public boolean onCreateVideoAd(h paramh) {
    return this.mVideoAdMgr.onCreateVideoAd(paramh);
  }
  
  public void onDOMReady() {
    reportFirstDOMReady();
    initFavoriteList();
    initPerformanceDialog();
  }
  
  public void onDestroy() {
    super.onDestroy();
    KeyboardHeightProvider keyboardHeightProvider = this.keyboardHeightProvider;
    if (keyboardHeightProvider != null)
      keyboardHeightProvider.close(); 
    getLaunchScheduler().stopListenLaunchStatus();
    AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onDestroy" });
    InnerEventHelper.mpTechnologyMsg("micro app onDestroy called");
  }
  
  public void onDragFinish() {
    doBack("backpress", "back", false, 10);
  }
  
  public void onEnvironmentReady() {
    AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onEnvironmentReady ", Long.valueOf(this.mLoadStartTime.getMillisAfterStart()) });
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "TTAppbrandTabUI_onEnvironmentReady" });
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            TTAppbrandTabUI.this.showFirstPage();
          }
        });
  }
  
  public void onFirstContentfulPaint(long paramLong) {
    if (this.mReportedFCP)
      return; 
    this.mReportedFCP = true;
    this.mLaunchProfileTime.pause();
    InnerEventHelper.mpFirstContentfulPaint("success", this.mLaunchProfileTime.getTime(), getLaunchScheduler().getDurationForOpen(), "", this.mHasActivityStoped, LoadStateManager.getIns().getLoadState(), getLaunchScheduler().getLaunchProgress());
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return false;
  }
  
  public void onKeyboardHeightChanged(int paramInt1, int paramInt2) {
    AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onKeyboardHeightChanged height ", Integer.valueOf(paramInt1), " orientation ", Integer.valueOf(paramInt2) });
    Iterator<k> iterator = this.IKeyboardObserverList.iterator();
    while (iterator.hasNext())
      ((k)iterator.next()).onKeyboardHeightChanged(paramInt1, paramInt2); 
  }
  
  public void onLanguageChange() {
    if (this.mApp.getAppInfo() != null)
      this.mLaunchLoadingView.updateViews(this.mApp.getAppInfo()); 
  }
  
  public void onNewIntent(Intent paramIntent) {
    super.onNewIntent(paramIntent);
    if (paramIntent == null || !paramIntent.hasExtra("microapp_url")) {
      AppBrandLogger.e("TTAppbrandTabUI", new Object[] { "onNewIntent fail, intent == ", paramIntent });
      return;
    } 
    Bundle bundle = paramIntent.getBundleExtra("mp_launch_extra");
    if (bundle != null) {
      MiniAppLaunchConfig miniAppLaunchConfig = (MiniAppLaunchConfig)bundle.getParcelable("launchConfig");
      if (miniAppLaunchConfig != null) {
        AppbrandApplicationImpl.getInst().setMiniAppLaunchConfig(miniAppLaunchConfig);
      } else {
        AppbrandApplicationImpl.getInst().setMiniAppLaunchConfig(MiniAppLaunchConfig.DEFAULT);
      } 
    } 
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle()) {
      this.mRootLayout.directFloat();
    } else {
      this.mRootLayout.directFillUpContainer();
    } 
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity == null) {
      AppBrandLogger.e("TTAppbrandTabUI", new Object[] { "onNewIntent fail, mAppInfo is null " });
      return;
    } 
    SearchEventHelper.init(appInfoEntity.launchFrom, this.mApp.getSchema());
    SearchEventHelper.onRelaunched();
    ((BlockPageManager)this.mApp.<BlockPageManager>getService(BlockPageManager.class)).handleHotLaunch();
    if (!TextUtils.isEmpty(appInfoEntity.startPage)) {
      String str1 = appInfoEntity.startPage;
      String str2 = this.mApp.getCurrentPageUrl();
      RouteEventCtrl routeEventCtrl = this.mApp.getRouteEventCtrl();
      if (routeEventCtrl != null && routeEventCtrl.shouldReLaunch(str1, str2)) {
        try {
          if (getAppConfig() != null) {
            this.mApp.getRouteEventCtrl().onAppLaunch();
            (getAppConfig()).isBackToHome = false;
            ((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).reLaunchByUrl(handleStartPage(str1));
            AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "handledPage ", handleStartPage(str1), " currentPage ", str2 });
            PermissionsManager permissionsManager = PermissionsManager.getInstance();
            if (permissionsManager != null) {
              permissionsManager.forceFlushPendingRequest((Activity)this.mActivity, false);
              return;
            } 
          } 
        } catch (Exception exception) {
          AppBrandLogger.e("TTAppbrandTabUI", new Object[] { "onNewIntent", exception });
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
  
  public boolean onOperateVideoAd(h paramh) {
    return this.mVideoAdMgr.onOperateVideoAd(paramh);
  }
  
  public void onPause() {
    super.onPause();
    AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onPause" });
    if (this.mVideoAdMgr.isVideoAdShown())
      return; 
    doPause();
  }
  
  public void onProgressChanged(int paramInt) {
    updateProgressTv(0, paramInt);
  }
  
  public void onRemoteDebugOpen() {
    ((JsRuntimeManager)this.mApp.<JsRuntimeManager>getService(JsRuntimeManager.class)).initTMARuntime((ContextWrapper)this.mActivity);
  }
  
  public void onRemoveVideoFragment() {
    ((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage().setDragEnable(this.mVideoAdMgr.getSwipeEnableBefore());
    doResume();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    if ((paramInt >> 16 & 0xFFFF) == 0)
      PermissionsManager.getInstance().notifyPermissionsChange((Activity)this.mActivity, paramArrayOfString, paramArrayOfint); 
  }
  
  public void onResume() {
    super.onResume();
    this.mRootLayout.resetState();
    AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onResume" });
    if (this.mVideoAdMgr.isVideoAdShown())
      return; 
    doResume();
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())
      ((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage().setDragEnable(false); 
  }
  
  public void onSnapShotDOMReady() {
    reportFirstDOMReady();
    initFavoriteList();
    initPerformanceDialog();
  }
  
  public void onStop() {
    if (!this.mReportedFCP && !hasNativeWebView())
      InnerEventHelper.mpFirstContentfulPaint("stop", this.mLaunchProfileTime.getTime(), getLaunchScheduler().getDurationForOpen(), this.mApp.getStopReason(), this.mHasActivityStoped, LoadStateManager.getIns().getLoadState(), getLaunchScheduler().getLaunchProgress()); 
    super.onStop();
  }
  
  public void onUserInteraction() {}
  
  public void onWindowSizeChange(int paramInt1, int paramInt2) {
    JsCoreUtils.sendOnWindowReSize((Context)this.mActivity);
  }
  
  public void postError(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      StringBuilder stringBuilder = new StringBuilder("WebView postError：");
      stringBuilder.append(paramString);
      jSONObject.put("errMsg", stringBuilder.toString());
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("TTAppbrandTabUI", "postError", exception);
    } 
    AppBrandMonitor.statusRate("mp_start_error", 5000, jSONObject);
    if (!this.mReportedFirstDOMReady) {
      ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "TTAppbrandTabUI_web_view_post_error", paramString });
      LoadHelper.handleMiniProcessFail(ErrorCode.WEBVIEW.RECEIVE_WEBVIEW_ERROR.getCode());
    } 
  }
  
  public void registerKeyboardListener(k paramk) {
    if (paramk != null && !this.IKeyboardObserverList.contains(paramk))
      this.IKeyboardObserverList.add(paramk); 
  }
  
  public void requestAppInfoSuccess(AppInfoEntity paramAppInfoEntity) {
    super.requestAppInfoSuccess(paramAppInfoEntity);
    StringBuilder stringBuilder = new StringBuilder("appinfo ");
    stringBuilder.append(paramAppInfoEntity.toString());
    AppBrandLogger.d("TTAppbrandTabUI", new Object[] { stringBuilder.toString() });
    if (paramAppInfoEntity.isWebApp())
      switchToLibraMiniApp(paramAppInfoEntity); 
  }
  
  public void setActivityResultHandler(IActivityResultHandler paramIActivityResultHandler) {
    this.mActivityResultHandler = paramIActivityResultHandler;
  }
  
  public void setKeepScreenOn(boolean paramBoolean) {
    this.mRootLayout.setKeepScreenOn(paramBoolean);
  }
  
  public void setSavedScreenOrientation(int paramInt) {
    this.mSavedScreenOrientation = paramInt;
  }
  
  public void showFirstPage() {
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "TTAppbrandTabUI_showFirstPage" });
    AppBrandLogger.d("TTAppbrandTabUI", new Object[] { "onSuccess ", Long.valueOf(this.mLoadStartTime.getMillisAfterStart()) });
    reportResult();
    AppbrandViewWindowRoot appbrandViewWindowRoot = ((PageRouter)this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot();
    if (appbrandViewWindowRoot.getContainer().getParent() != this.mWindowRootContainer) {
      UIUtils.removeParentView((View)appbrandViewWindowRoot.getContainer());
      this.mWindowRootContainer.addView((View)appbrandViewWindowRoot.getContainer(), 0);
    } 
    hideLoadShowUI();
    startModule();
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    HostProcessBridge.onMiniAppStart(appInfoEntity, appInfoEntity.isGame(), getScreenOrientationFromAppConfig());
    this.mMicroAppStartShowTimes = System.currentTimeMillis();
    InnerHostProcessBridge.setTmaLaunchFlag();
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle() && !this.mRootLayout.ismFilledUpContainer()) {
      AppbrandSinglePage appbrandSinglePage = ((AppbrandViewWindowBase)appbrandViewWindowRoot.getTopView()).getCurrentPage();
      if (appbrandSinglePage != null && appbrandSinglePage.getTitleBar() != null)
        TitleBarControl.showTitlebarRadius((BaseTitleBar)appbrandSinglePage.getTitleBar()); 
    } 
  }
  
  public void unRegisterKeyboardListener(k paramk) {
    if (paramk != null)
      this.IKeyboardObserverList.remove(paramk); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\TTAppbrandTabUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */