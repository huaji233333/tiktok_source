package com.tt.miniapp.launchschedule;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsScopedContext;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.c.a;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.JsRuntime;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.jsbridge.JsRuntimeManager;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.meta.AppInfoRequestListener;
import com.tt.miniapp.launchcache.meta.MetaService;
import com.tt.miniapp.launchcache.pkg.PkgService;
import com.tt.miniapp.launchcache.pkg.StreamDownloadInstallListener;
import com.tt.miniapp.manager.PreTTRequestManager;
import com.tt.miniapp.manager.SynHistoryManager;
import com.tt.miniapp.mvp.TTAppbrandPresenter;
import com.tt.miniapp.mvp.TTAppbrandView;
import com.tt.miniapp.net.httpdns.NetDnsResolver;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapp.route.RouteEventCtrl;
import com.tt.miniapp.storage.filestorge.FileManager;
import com.tt.miniapp.streamloader.LoadTask;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.d;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class LaunchScheduler extends AppbrandServiceManager.ServiceBase implements TTAppbrandView {
  private d.b blockLoadingCallback = HostDependManager.getInst().createBlockLoadingCallback();
  
  private boolean isAdSiteBrowser = false;
  
  private final Object mBindViewLock = new Object();
  
  private volatile boolean mIsSnapShotDomReady = false;
  
  private boolean mJsCoreReady = false;
  
  public LaunchProgress mLaunchProgress = new LaunchProgress();
  
  private final Object mLoadLock = new Object();
  
  private ArrayList<Runnable> mPendingRunnableList;
  
  public TTAppbrandPresenter mPresenter = new TTAppbrandPresenter(this.mApp, this);
  
  private int mProgress = -1;
  
  public TTAppbrandView mRealView;
  
  private volatile int mState = 0;
  
  private AbsSubLaunchScheduler mSubScheduler;
  
  private boolean mWebViewReady = false;
  
  public LaunchScheduler(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private void asyncUpdateApp(final Context context, AppInfoEntity paramAppInfoEntity) {
    final String currentAppLaunchFrom = paramAppInfoEntity.launchFrom;
    final String currentAppScene = paramAppInfoEntity.scene;
    final String currentAppSubScene = paramAppInfoEntity.subScene;
    final String currentAppVersion = paramAppInfoEntity.version;
    final long currentAppVersionCode = paramAppInfoEntity.versionCode;
    ((MetaService)this.mApp.getService(MetaService.class)).requestAsyncMeta(context, new AppInfoRequestListener() {
          public void onAppInfoInvalid(AppInfoEntity param1AppInfoEntity, int param1Int) {
            if (param1Int == 1)
              LaunchScheduler.this.offline(); 
          }
          
          public void requestAppInfoFail(String param1String1, String param1String2) {
            LaunchScheduler.this.onUpdateFailed();
          }
          
          public void requestAppInfoSuccess(AppInfoEntity param1AppInfoEntity) {
            LaunchScheduler.this.prepareAsyncDownloadPkg(context, param1AppInfoEntity, currentAppVersion, currentAppVersionCode, currentAppLaunchFrom, currentAppScene, currentAppSubScene);
          }
        });
  }
  
  private boolean initAppConfig() {
    AppConfig appConfig = this.mApp.initAppConfig();
    if (appConfig == null) {
      AppBrandLogger.e("LaunchScheduler", new Object[] { "parse appConfig error" });
      InnerEventHelper.mpLoadResult(0L, "parse_app_config_fail", "parse appConfig fail", 0L, 0L, LoadStateManager.getIns().getLoadState());
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "BaseActivityProxy_parseAppConfigFail" });
      AppBrandMonitor.reportPreloadCase("initAppConfig_appConfig_null", 6010);
      LoadHelper.handleMiniProcessFail(ErrorCode.MAIN.PARSE_APPCONFIG_ERROR.getCode());
      return false;
    } 
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity != null)
      appInfoEntity.isLandScape = "landscape".equals(appConfig.screenOrientation); 
    initStartPage();
    return true;
  }
  
  private void initInstallAppDir(AppInfoEntity paramAppInfoEntity) {
    Application application = AppbrandContext.getInst().getApplicationContext();
    LaunchCacheDAO.CacheVersionDir cacheVersionDir = LaunchCacheDAO.INSTANCE.getCacheAppIdDir((Context)application, paramAppInfoEntity.appId).getCacheVersionDir(paramAppInfoEntity.versionCode, RequestType.normal);
    FileManager.inst().setCodeRootFilePath(cacheVersionDir.getInstallDir().getAbsolutePath());
  }
  
  private void initStartPage() {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity != null) {
      String str = appInfoEntity.startPage;
      if (!TextUtils.isEmpty(str)) {
        AppBrandLogger.d("LaunchScheduler", new Object[] { "initStartPage ", str });
        getNormalStartPage(str);
      } 
    } 
  }
  
  private void updateAppInfoOnAsyncUpdate(AppInfoEntity paramAppInfoEntity) {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    appInfoEntity.encryptextra = paramAppInfoEntity.encryptextra;
    appInfoEntity.ttBlackCode = paramAppInfoEntity.ttBlackCode;
    appInfoEntity.ttSafeCode = paramAppInfoEntity.ttSafeCode;
    appInfoEntity.shareLevel = paramAppInfoEntity.shareLevel;
    appInfoEntity.state = paramAppInfoEntity.state;
    appInfoEntity.versionState = paramAppInfoEntity.versionState;
    appInfoEntity.domains = paramAppInfoEntity.domains;
    appInfoEntity.adlist = paramAppInfoEntity.adlist;
    appInfoEntity.libra_path = paramAppInfoEntity.libra_path;
    this.mApp.setAppInfo(appInfoEntity);
  }
  
  public void bindView(TTAppbrandView paramTTAppbrandView) {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_bindView" });
    synchronized (this.mBindViewLock) {
      this.mRealView = paramTTAppbrandView;
      if (this.mPendingRunnableList != null) {
        Iterator<Runnable> iterator = this.mPendingRunnableList.iterator();
        while (iterator.hasNext())
          ((Runnable)iterator.next()).run(); 
        this.mPendingRunnableList = null;
      } 
      if (this.mProgress >= 0)
        this.mRealView.miniAppDownloadInstallProgress(this.mProgress); 
      this.mBindViewLock.notifyAll();
      return;
    } 
  }
  
  public ArrayMap<String, Long> getDurationForOpen() {
    return this.mLaunchProgress.getDurationForOpen();
  }
  
  public int getLaunchProgress() {
    return this.mLaunchProgress.getProgress();
  }
  
  public String getNormalStartPage(String paramString) {
    if (!TextUtils.isEmpty(paramString)) {
      AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
      String str = PageUtil.getCleanPath(paramString);
      ArrayList arrayList = null;
      AppConfig appConfig = this.mApp.getAppConfig();
      if (appConfig != null)
        arrayList = appConfig.getPageList(); 
      if (arrayList != null && arrayList.size() > 0) {
        if (arrayList.contains(str)) {
          appInfoEntity.startPage = paramString;
        } else {
          appInfoEntity.startPage = appConfig.mEntryPath;
        } 
        AppBrandLogger.d("LaunchScheduler", new Object[] { "getNormalStartPage ", appInfoEntity.startPage });
        return appInfoEntity.startPage;
      } 
    } 
    return "";
  }
  
  public boolean isAtLeastLaunching() {
    return (this.mState > 0);
  }
  
  public void loadWhenInstallSuccess() {
    synchronized (this.mLoadLock) {
      if (this.mState != 3)
        return; 
      this.mState = 4;
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_loadWhenInstallSuccess" });
      ((AutoTestManager)this.mApp.getService(AutoTestManager.class)).addEvent("stopDownloadInstallTime");
      this.mApp.getLifeCycleManager().notifyMiniAppInstallSuccess();
      null = this.mApp.getAppInfo();
      if (!this.isAdSiteBrowser) {
        if (!initAppConfig())
          return; 
        this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_PKG_INSTALL_SUCCESS);
        RouteEventCtrl routeEventCtrl = this.mApp.getRouteEventCtrl();
        if (routeEventCtrl != null)
          routeEventCtrl.setLaunchOption((AppInfoEntity)null, this.mApp.getAppConfig()); 
        Application application = AppbrandContext.getInst().getApplicationContext();
        AppConfig appConfig = this.mApp.getAppConfig();
        String str = this.mApp.getSchema();
        PreTTRequestManager.saveAndStartPrefetch((Context)application, appConfig, ((AppInfoEntity)null).appId, str);
      } 
      NetUtil.registerListener();
      this.mSubScheduler.onMiniAppInstallSuccess();
      postAtViewReady(new Runnable() {
            public void run() {
              LaunchScheduler.this.mRealView.miniAppInstallSuccess();
            }
          });
      if (!null.isLocalTest()) {
        if (((AppInfoEntity)null).getFromType == 1) {
          asyncUpdateApp((Context)AppbrandContext.getInst().getApplicationContext(), (AppInfoEntity)null);
          return;
        } 
        InnerEventHelper.mpVersionInfo(true, ((AppInfoEntity)null).version, ((AppInfoEntity)null).versionCode);
      } 
      return;
    } 
  }
  
  public void metaExpired() {
    waitForViewBound();
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.metaExpired();
          }
        });
  }
  
  public void miniAppDownloadInstallFail(String paramString1, String paramString2) {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logError(new String[] { "LaunchScheduler_miniAppDownloadInstallFail" });
    waitForViewBound();
    this.mRealView.miniAppDownloadInstallFail(paramString1, paramString2);
  }
  
  public void miniAppDownloadInstallProgress(int paramInt) {
    this.mProgress = paramInt;
    TTAppbrandView tTAppbrandView = this.mRealView;
    if (tTAppbrandView != null)
      tTAppbrandView.miniAppDownloadInstallProgress(paramInt); 
  }
  
  public void miniAppInstallSuccess() {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "miniAppInstallSuccess" });
    this.mState = 3;
    if (!((RenderSnapShotManager)this.mApp.getService(RenderSnapShotManager.class)).isSnapShotRender()) {
      loadWhenInstallSuccess();
      return;
    } 
    if (this.mIsSnapShotDomReady)
      loadWhenInstallSuccess(); 
  }
  
  public void mismatchHost() {
    waitForViewBound();
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.mismatchHost();
          }
        });
  }
  
  public void noPermission() {
    waitForViewBound();
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.noPermission();
          }
        });
  }
  
  public void offline() {
    waitForViewBound();
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.offline();
          }
        });
  }
  
  public void onCheckForUpdate(AppInfoEntity paramAppInfoEntity) {
    AppBrandLogger.i("LaunchScheduler", new Object[] { "onCheckForUpdate" });
    try {
      if (!this.isAdSiteBrowser) {
        boolean bool;
        AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
        JSONObject jSONObject = new JSONObject();
        if (appInfoEntity.versionCode < paramAppInfoEntity.versionCode) {
          bool = true;
        } else {
          bool = false;
        } 
        jSONObject.put("hasUpdate", bool);
        this.mApp.getJsBridge().sendMsgToJsCore("onCheckForUpdate", jSONObject.toString());
      } 
      updateAppInfoOnAsyncUpdate(paramAppInfoEntity);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("onCheckForUpdate", new Object[] { exception });
      return;
    } 
  }
  
  public void onDOMReady() {
    this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_LOAD_ENTRY_PAGE_SUCCESS);
    ((PreloadManager)this.mApp.getService(PreloadManager.class)).preloadOnIdle();
    this.mApp.notifyPreloadEmptyProcess();
    postAtViewReady(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.onDOMReady();
            ((TimeLogger)LaunchScheduler.this.mApp.getService(TimeLogger.class)).stopScheduleFlush();
          }
        });
  }
  
  public void onDestroy() {
    AppBrandLogger.i("LaunchScheduler", new Object[] { "onDestroy" });
    LoadTask loadTask = StreamLoader.getLoadTask();
    if (loadTask != null)
      loadTask.release(); 
  }
  
  public void onEnvironmentReady() {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_onEnvironmentReady" });
    ((RenderSnapShotManager)this.mApp.getService(RenderSnapShotManager.class)).onLoadResultSuccess();
    a a = HostDependManager.getInst().getMiniAppLifeCycleInstance();
    if (a != null)
      a.a = true; 
    postAtViewReady(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.onEnvironmentReady();
          }
        });
  }
  
  public void onFirstContentfulPaint(final long adjustTimeDiff) {
    this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_FIRST_PAGE_RENDER_SUCCESS, adjustTimeDiff);
    postAtViewReady(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.onFirstContentfulPaint(adjustTimeDiff);
          }
        });
  }
  
  public void onFirstPaint() {
    this.mApp.notifyPreloadEmptyProcess();
  }
  
  public void onJsCoreLoaded(int paramInt) {
    if (paramInt == 1) {
      this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_LOAD_TMA_CORE_SUCCESS);
      return;
    } 
    if (paramInt == 2)
      this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_LOAD_TMG_CORE_SUCCESS); 
  }
  
  void onJsCoreReady() {
    if (this.mJsCoreReady)
      return; 
    synchronized (this.mLoadLock) {
      if (this.mJsCoreReady)
        return; 
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_onJsCoreReady" });
      this.mJsCoreReady = true;
      if (this.mWebViewReady)
        onEnvironmentReady(); 
      this.mApp.getRouteEventCtrl().onJsCoreReady();
      this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_LOAD_MAIN_JS_SUCCESS);
      return;
    } 
  }
  
  public void onRemoteDebugOpen() {
    waitForViewBound();
    this.mRealView.onRemoteDebugOpen();
  }
  
  public void onRenderProcessGone(Boolean paramBoolean) {
    if (isAtLeastLaunching()) {
      postAtViewReady(new Runnable() {
            public void run() {
              LoadHelper.handleMiniProcessFail(ErrorCode.WEBVIEW.ON_RENDER_PROCESS_GONE.getCode());
            }
          });
      return;
    } 
    if (paramBoolean == null) {
      LoadHelper.monitorErrorEvent(ErrorCode.WEBVIEW.ON_RENDER_PROCESS_GONE.getCode(), ErrorCode.WEBVIEW.ON_RENDER_PROCESS_GONE.getDesc());
    } else {
      String str = ErrorCode.WEBVIEW.ON_RENDER_PROCESS_GONE.getCode();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(ErrorCode.WEBVIEW.ON_RENDER_PROCESS_GONE.getDesc());
      stringBuilder.append(", didCrash: ");
      stringBuilder.append(paramBoolean);
      LoadHelper.monitorErrorEvent(str, stringBuilder.toString());
    } 
    ProcessUtil.killCurrentMiniAppProcess((Context)AppbrandContext.getInst().getApplicationContext());
  }
  
  public void onSnapShotDOMReady() {
    ((PreloadManager)this.mApp.getService(PreloadManager.class)).preloadOnIdle();
    this.mIsSnapShotDomReady = true;
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            LaunchScheduler.this.loadWhenInstallSuccess();
          }
        },  (Scheduler)LaunchThreadPool.getInst());
    postAtViewReady(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.onSnapShotDOMReady();
          }
        });
  }
  
  public void onTemplateLoaded() {
    this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_LOAD_TMPLATE_HTML_SUCCESS);
  }
  
  public void onUpdateFailed() {
    AppBrandLogger.i("LaunchScheduler", new Object[] { "onUpdateFailed" });
    if (!this.isAdSiteBrowser)
      try {
        this.mApp.getJsBridge().sendMsgToJsCore("onUpdateFailed", (new JSONObject()).toString());
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("onCheckForUpdate", new Object[] { exception });
      }  
  }
  
  public void onUpdateReady() {
    AppBrandLogger.i("LaunchScheduler", new Object[] { "onUpdateReady" });
    if (!this.isAdSiteBrowser)
      try {
        this.mApp.getJsBridge().sendMsgToJsCore("onUpdateReady", (new JSONObject()).toString());
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("onCheckForUpdate", new Object[] { exception });
      }  
  }
  
  public void onWebViewReady() {
    if (this.mWebViewReady)
      return; 
    synchronized (this.mLoadLock) {
      if (this.mWebViewReady)
        return; 
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_onWebViewReady" });
      this.mWebViewReady = true;
      if (this.mJsCoreReady)
        onEnvironmentReady(); 
      this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_LOAD_PAGE_FRAME_JS_SUCCESS);
      return;
    } 
  }
  
  public void postAtViewReady(Runnable paramRunnable) {
    synchronized (this.mBindViewLock) {
      if (this.mRealView != null) {
        paramRunnable.run();
      } else {
        if (this.mPendingRunnableList == null)
          this.mPendingRunnableList = new ArrayList<Runnable>(); 
        this.mPendingRunnableList.add(paramRunnable);
      } 
      return;
    } 
  }
  
  public void prepareAsyncDownloadPkg(Context paramContext, final AppInfoEntity appInfo, final String currentAppVersion, long paramLong, String paramString2, String paramString3, String paramString4) {
    if (TextUtils.isEmpty(appInfo.version))
      appInfo.version = currentAppVersion; 
    InnerEventHelper.mpVersionInfo(TextUtils.equals(currentAppVersion, appInfo.version), currentAppVersion, paramLong);
    onCheckForUpdate(appInfo);
    this.mApp.setUpdateAppInfo(appInfo);
    if (appInfo.versionCode > paramLong) {
      appInfo.launchFrom = paramString2;
      appInfo.scene = paramString3;
      appInfo.subScene = paramString4;
      ((PkgService)this.mApp.getService(PkgService.class)).downloadAsync(paramContext, appInfo, new StreamDownloadInstallListener() {
            public void onDownloadSuccess(File param1File, boolean param1Boolean) {
              AppBrandLogger.d("LaunchScheduler", new Object[] { "onInstallSuccess", Boolean.valueOf(param1Boolean) });
              LaunchScheduler.this.onUpdateReady();
              InnerEventHelper.mpAsyncNotify(appInfo.version, currentAppVersion);
            }
            
            public void onDownloadingProgress(int param1Int, long param1Long) {}
            
            public void onFail(String param1String1, String param1String2) {
              LaunchScheduler.this.onUpdateFailed();
            }
            
            public void onInstallSuccess() {}
            
            public void onStop() {}
          });
    } 
  }
  
  public void requestAppInfoFail(String paramString1, String paramString2) {
    waitForViewBound();
    this.mRealView.requestAppInfoFail(paramString1, paramString2);
  }
  
  public void requestAppInfoSuccess(final AppInfoEntity appInfo) {
    ((AutoTestManager)this.mApp.getService(AutoTestManager.class)).addEvent("stopRequestMeta");
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_requestAppInfoSuccess" });
    ((MetaService)this.mApp.getService(MetaService.class)).updateAppInfoAfterRequest(appInfo);
    ((AutoTestManager)this.mApp.getService(AutoTestManager.class)).addEventWithValue("appId", appInfo.appId);
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_AppInfoUpdatedAfterRequest" });
    if (!appInfo.isWebApp())
      initInstallAppDir(appInfo); 
    if (!this.isAdSiteBrowser) {
      DebugManager.getInst().openDebug(new DebugManager.DebugCallback() {
            public void complete() {
              if ((DebugManager.getInst()).mIsRemoteDebug)
                LaunchScheduler.this.onRemoteDebugOpen(); 
              JsRuntime jsRuntime = ((JsRuntimeManager)LaunchScheduler.this.mApp.getService(JsRuntimeManager.class)).getCurrentRuntime();
              if (LaunchScheduler.this.mApp.getAppInfo().isGame() && (jsRuntime == null || jsRuntime instanceof com.tt.miniapp.jsbridge.JsTMARuntime))
                LaunchScheduler.this.waitForViewBound(); 
              if (LaunchScheduler.this.mApp.getAppInfo().isGame())
                ((JsRuntimeManager)LaunchScheduler.this.mApp.getService(JsRuntimeManager.class)).getCurrentRuntime().executeInJsThread(new JsContext.ScopeCallback() {
                      public void run(JsScopedContext param2JsScopedContext) {
                        try {
                          ((TimeLogger)LaunchScheduler.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_beforeMetaReady" });
                          param2JsScopedContext.eval("metaReady();", "metaReady");
                          ((TimeLogger)LaunchScheduler.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "LaunchScheduler_afterMetaReady" });
                          return;
                        } catch (Exception exception) {
                          AppBrandLogger.eWithThrowable("LaunchScheduler", "Call metaReady error.", exception);
                          return;
                        } 
                      }
                    }); 
              LaunchScheduler.this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_META_SUCCESS);
              LaunchScheduler.this.postAtViewReady(new Runnable() {
                    public void run() {
                      LaunchScheduler.this.mRealView.requestAppInfoSuccess(appInfo);
                    }
                  });
              if (!appInfo.isWebApp()) {
                ((AutoTestManager)LaunchScheduler.this.mApp.getService(AutoTestManager.class)).addEvent("startDownloadInstallTime");
                LaunchScheduler.this.mPresenter.downloadInstallMiniApp((Context)AppbrandContext.getInst().getApplicationContext(), appInfo);
                LoadStateManager.getIns().setLoadState("pkg_downloading");
              } 
            }
          });
    } else {
      this.mLaunchProgress.updateStatus(ILaunchStatus.STATUS_META_SUCCESS);
      postAtViewReady(new Runnable() {
            public void run() {
              LaunchScheduler.this.mRealView.requestAppInfoSuccess(appInfo);
            }
          });
      if (!appInfo.isWebApp()) {
        this.mPresenter.downloadInstallMiniApp((Context)AppbrandContext.getInst().getApplicationContext(), appInfo);
        LoadStateManager.getIns().setLoadState("pkg_downloading");
      } 
    } 
    SynHistoryManager.getInstance().addToRecentApps(appInfo);
    NetDnsResolver.getInst().preResolveInetAddressFromHttpDns(appInfo);
  }
  
  public void setAllCompleteProgress() {
    for (int i = ILaunchStatus.STATUS_INIT; i < ILaunchStatus.STATUS_FIRST_PAGE_RENDER_SUCCESS; i++)
      this.mLaunchProgress.updateStatus(i); 
  }
  
  public void showNotSupportView() {
    waitForViewBound();
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            LaunchScheduler.this.mRealView.showNotSupportView();
          }
        });
  }
  
  public void startLaunch(AppInfoEntity paramAppInfoEntity, String paramString1, String paramString2, Bundle paramBundle) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mState : I
    //   6: istore #5
    //   8: iload #5
    //   10: ifle -> 16
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: aload_0
    //   17: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   20: ldc com/tt/miniapp/util/TimeLogger
    //   22: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   25: checkcast com/tt/miniapp/util/TimeLogger
    //   28: iconst_1
    //   29: anewarray java/lang/String
    //   32: dup
    //   33: iconst_0
    //   34: ldc_w 'LaunchScheduler_startLaunch'
    //   37: aastore
    //   38: invokevirtual logTimeDuration : ([Ljava/lang/String;)V
    //   41: aload_0
    //   42: iconst_1
    //   43: putfield mState : I
    //   46: aload_0
    //   47: invokestatic getInstance : ()Lcom/tt/miniapp/adsite/AdSiteManager;
    //   50: aload_0
    //   51: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   54: invokevirtual getMiniAppContext : ()Lcom/tt/miniapp/base/MiniAppContextWrapper;
    //   57: invokevirtual getApplicationContext : ()Landroid/content/Context;
    //   60: aload_1
    //   61: invokevirtual initIsAdSiteBrowser : (Landroid/content/Context;Lcom/tt/miniapphost/entity/AppInfoEntity;)Z
    //   64: putfield isAdSiteBrowser : Z
    //   67: aload_0
    //   68: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   71: aload_1
    //   72: invokevirtual setAppInfo : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   75: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   78: aload_1
    //   79: invokevirtual isGame : ()Z
    //   82: invokevirtual setGame : (Z)V
    //   85: aload_0
    //   86: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   89: aload_2
    //   90: invokevirtual setSchema : (Ljava/lang/String;)V
    //   93: aload_0
    //   94: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   97: ldc com/tt/miniapp/util/TimeLogger
    //   99: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   102: checkcast com/tt/miniapp/util/TimeLogger
    //   105: invokevirtual scheduleFlush : ()V
    //   108: aload_3
    //   109: ifnull -> 130
    //   112: aload_0
    //   113: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   116: ldc_w com/tt/miniapp/util/RenderSnapShotManager
    //   119: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   122: checkcast com/tt/miniapp/util/RenderSnapShotManager
    //   125: aload_3
    //   126: aload_1
    //   127: invokevirtual preHandleVDomData : (Ljava/lang/String;Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   130: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   133: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   136: aload_2
    //   137: invokestatic startPrefetch : (Landroid/content/Context;Ljava/lang/String;)V
    //   140: aload #4
    //   142: ifnull -> 241
    //   145: aload #4
    //   147: ldc_w 'lang'
    //   150: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   153: astore_3
    //   154: aload_3
    //   155: ifnull -> 170
    //   158: aload_3
    //   159: invokestatic string2Locale : (Ljava/lang/String;)Ljava/util/Locale;
    //   162: astore_3
    //   163: invokestatic getInst : ()Lcom/tt/miniapphost/language/LocaleManager;
    //   166: aload_3
    //   167: invokevirtual notifyLangChange : (Ljava/util/Locale;)V
    //   170: aload #4
    //   172: ldc_w 'mp_open_app_schema_timestamp'
    //   175: ldc2_w -1
    //   178: invokevirtual getLong : (Ljava/lang/String;J)J
    //   181: lstore #6
    //   183: lload #6
    //   185: lconst_0
    //   186: lcmp
    //   187: ifle -> 212
    //   190: aload_0
    //   191: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   194: ldc_w com/tt/miniapp/debug/PerformanceService
    //   197: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   200: checkcast com/tt/miniapp/debug/PerformanceService
    //   203: ldc_w 'clickApp'
    //   206: lload #6
    //   208: invokevirtual createPerformanceTimingObj : (Ljava/lang/String;J)Lcom/tt/miniapp/debug/PerformanceService$PerformanceTimingObj;
    //   211: pop
    //   212: aload #4
    //   214: ldc_w 'mp_open_app_schema_cputime'
    //   217: ldc2_w -1
    //   220: invokevirtual getLong : (Ljava/lang/String;J)J
    //   223: lstore #6
    //   225: lload #6
    //   227: lconst_0
    //   228: lcmp
    //   229: ifle -> 241
    //   232: aload_0
    //   233: getfield mLaunchProgress : Lcom/tt/miniapp/launchschedule/LaunchProgress;
    //   236: lload #6
    //   238: invokevirtual setOpenSchemaTime : (J)V
    //   241: aload_0
    //   242: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   245: ldc_w com/tt/miniapp/LifeCycleManager
    //   248: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   251: checkcast com/tt/miniapp/LifeCycleManager
    //   254: invokevirtual notifyAppStartLaunching : ()V
    //   257: aload_0
    //   258: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   261: invokevirtual getRouteEventCtrl : ()Lcom/tt/miniapp/route/RouteEventCtrl;
    //   264: astore_3
    //   265: aload_3
    //   266: ifnull -> 273
    //   269: aload_3
    //   270: invokevirtual onAppLaunch : ()V
    //   273: invokestatic getInst : ()Lcom/tt/miniapp/debug/DebugManager;
    //   276: aload_1
    //   277: invokevirtual initRemoteDebugInfo : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   280: aload_0
    //   281: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   284: invokevirtual getLifeCycleManager : ()Lcom/tt/miniapp/LifeCycleManager;
    //   287: aload_1
    //   288: invokevirtual notifyAppInfoInited : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   291: aload_1
    //   292: invokevirtual isGame : ()Z
    //   295: ifne -> 343
    //   298: aload_0
    //   299: getfield isAdSiteBrowser : Z
    //   302: ifeq -> 324
    //   305: aload_0
    //   306: new com/tt/miniapp/launchschedule/AdSiteLaunchScheduler
    //   309: dup
    //   310: aload_0
    //   311: aload_0
    //   312: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   315: invokespecial <init> : (Lcom/tt/miniapp/launchschedule/LaunchScheduler;Lcom/tt/miniapp/AppbrandApplicationImpl;)V
    //   318: putfield mSubScheduler : Lcom/tt/miniapp/launchschedule/AbsSubLaunchScheduler;
    //   321: goto -> 359
    //   324: aload_0
    //   325: new com/tt/miniapp/launchschedule/TMALaunchScheduler
    //   328: dup
    //   329: aload_0
    //   330: aload_0
    //   331: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   334: invokespecial <init> : (Lcom/tt/miniapp/launchschedule/LaunchScheduler;Lcom/tt/miniapp/AppbrandApplicationImpl;)V
    //   337: putfield mSubScheduler : Lcom/tt/miniapp/launchschedule/AbsSubLaunchScheduler;
    //   340: goto -> 359
    //   343: aload_0
    //   344: new com/tt/miniapp/launchschedule/TMGLaunchScheduler
    //   347: dup
    //   348: aload_0
    //   349: aload_0
    //   350: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   353: invokespecial <init> : (Lcom/tt/miniapp/launchschedule/LaunchScheduler;Lcom/tt/miniapp/AppbrandApplicationImpl;)V
    //   356: putfield mSubScheduler : Lcom/tt/miniapp/launchschedule/AbsSubLaunchScheduler;
    //   359: aload_0
    //   360: getfield mSubScheduler : Lcom/tt/miniapp/launchschedule/AbsSubLaunchScheduler;
    //   363: invokevirtual onStartLaunch : ()V
    //   366: new com/tt/miniapp/launchschedule/LaunchScheduler$1
    //   369: dup
    //   370: aload_0
    //   371: aload_2
    //   372: invokespecial <init> : (Lcom/tt/miniapp/launchschedule/LaunchScheduler;Ljava/lang/String;)V
    //   375: invokestatic getInst : ()Lcom/tt/miniapphost/LaunchThreadPool;
    //   378: invokestatic runOnWorkThread : (Lcom/storage/async/Action;Lcom/storage/async/Scheduler;)V
    //   381: aload_0
    //   382: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   385: ldc com/tt/miniapp/util/TimeLogger
    //   387: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   390: checkcast com/tt/miniapp/util/TimeLogger
    //   393: iconst_1
    //   394: anewarray java/lang/String
    //   397: dup
    //   398: iconst_0
    //   399: ldc_w 'LaunchScheduler_startRequestAppInfo'
    //   402: aastore
    //   403: invokevirtual logTimeDuration : ([Ljava/lang/String;)V
    //   406: invokestatic getIns : ()Lcom/tt/miniapp/event/LoadStateManager;
    //   409: ldc_w 'meta_requesting'
    //   412: invokevirtual setLoadState : (Ljava/lang/String;)V
    //   415: new com/tt/miniapp/launchschedule/LaunchScheduler$2
    //   418: dup
    //   419: aload_0
    //   420: aload_1
    //   421: invokespecial <init> : (Lcom/tt/miniapp/launchschedule/LaunchScheduler;Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   424: pop
    //   425: aload_0
    //   426: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   429: ldc_w com/tt/miniapp/autotest/AutoTestManager
    //   432: invokevirtual getService : (Ljava/lang/Class;)Lcom/tt/miniapp/AppbrandServiceManager$ServiceBase;
    //   435: checkcast com/tt/miniapp/autotest/AutoTestManager
    //   438: ldc_w 'startRequestMeta'
    //   441: invokevirtual addEvent : (Ljava/lang/String;)V
    //   444: aload_0
    //   445: getfield mPresenter : Lcom/tt/miniapp/mvp/TTAppbrandPresenter;
    //   448: aload_1
    //   449: invokevirtual requestAppInfo : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   452: aload_0
    //   453: iconst_2
    //   454: putfield mState : I
    //   457: aload_0
    //   458: monitorexit
    //   459: return
    //   460: astore_1
    //   461: aload_0
    //   462: monitorexit
    //   463: aload_1
    //   464: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	460	finally
    //   16	108	460	finally
    //   112	130	460	finally
    //   130	140	460	finally
    //   145	154	460	finally
    //   158	170	460	finally
    //   170	183	460	finally
    //   190	212	460	finally
    //   212	225	460	finally
    //   232	241	460	finally
    //   241	265	460	finally
    //   269	273	460	finally
    //   273	321	460	finally
    //   324	340	460	finally
    //   343	359	460	finally
    //   359	457	460	finally
  }
  
  public void startListenLaunchStatus(ILaunchProgressListener paramILaunchProgressListener) {
    this.mLaunchProgress.start(paramILaunchProgressListener);
  }
  
  public void stopListenLaunchStatus() {
    this.mLaunchProgress.stop();
  }
  
  public void waitForViewBound() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mRealView : Lcom/tt/miniapp/mvp/TTAppbrandView;
    //   4: ifnonnull -> 41
    //   7: aload_0
    //   8: getfield mBindViewLock : Ljava/lang/Object;
    //   11: astore_1
    //   12: aload_1
    //   13: monitorenter
    //   14: aload_0
    //   15: getfield mRealView : Lcom/tt/miniapp/mvp/TTAppbrandView;
    //   18: astore_2
    //   19: aload_2
    //   20: ifnonnull -> 33
    //   23: aload_0
    //   24: getfield mBindViewLock : Ljava/lang/Object;
    //   27: invokevirtual wait : ()V
    //   30: goto -> 14
    //   33: aload_1
    //   34: monitorexit
    //   35: return
    //   36: astore_2
    //   37: aload_1
    //   38: monitorexit
    //   39: aload_2
    //   40: athrow
    //   41: return
    //   42: astore_2
    //   43: goto -> 14
    // Exception table:
    //   from	to	target	type
    //   14	19	36	finally
    //   23	30	42	java/lang/InterruptedException
    //   23	30	36	finally
    //   33	35	36	finally
    //   37	39	36	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchschedule\LaunchScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */