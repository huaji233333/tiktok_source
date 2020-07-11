package com.tt.miniapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.f.a;
import android.webkit.WebView;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.frontendapiinterface.e;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapp.audio.background.BgAudioManagerClient;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.badcase.BlockPageManager;
import com.tt.miniapp.base.IActivityFetcher;
import com.tt.miniapp.base.MiniAppContextWrapper;
import com.tt.miniapp.call.PhoneCallImpl;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.component.nativeview.NativeWebView;
import com.tt.miniapp.debug.PerformanceService;
import com.tt.miniapp.debug.SwitchManager;
import com.tt.miniapp.dialog.ActionSheetImpl;
import com.tt.miniapp.dialog.DialogImpl;
import com.tt.miniapp.favorite.FavoriteGuideWidget;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.jsbridge.JsRuntimeManager;
import com.tt.miniapp.launch.MiniAppLaunchConfig;
import com.tt.miniapp.launchcache.meta.MetaService;
import com.tt.miniapp.launchcache.pkg.PkgService;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.manager.AppConfigManager;
import com.tt.miniapp.manager.AppbrandBroadcastService;
import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapp.manager.HostSnapShotManager;
import com.tt.miniapp.manager.MainMessageLoggerManager;
import com.tt.miniapp.manager.StorageManager;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.route.RouteEventCtrl;
import com.tt.miniapp.shortcut.ShortcutService;
import com.tt.miniapp.streamloader.FileAccessLogger;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlemenu.item.FavoriteMiniAppMenuItem;
import com.tt.miniapp.toast.HideToastImpl;
import com.tt.miniapp.toast.ToastImpl;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.TimeLineReporter;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapp.view.webcore.LoadPathInterceptor;
import com.tt.miniapp.webapp.WebAppPreloadManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IAppbrandApplication;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.ModeManager;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.preload.IPreload;
import com.tt.miniapphost.process.HostProcessBridge;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AppbrandApplicationImpl implements IAppbrandApplication {
  private static AppbrandApplicationImpl sInstance;
  
  private boolean isOpenedSchema = false;
  
  private boolean jumpToApp = false;
  
  private e mActivityLife;
  
  private AppInfoEntity mAppInfo;
  
  private a<String, Boolean> mCurrentPageHideShareMenuArrayMap = new a();
  
  private String mCurrentPagePath;
  
  private String mCurrentPageType;
  
  private String mCurrentPageUrl;
  
  private int mCurrentWebViewId;
  
  private final ForeBackgroundManager mForeBackgroundManager;
  
  private MiniAppLaunchConfig mMiniAppLaunchConfig = MiniAppLaunchConfig.DEFAULT;
  
  private boolean mNeedNotifyPreloadEmptyProcess = false;
  
  protected List<ILifecycleObserver> mObservers = new CopyOnWriteArrayList<ILifecycleObserver>();
  
  private RouteEventCtrl mRouteEventCtrl;
  
  private String mSchema;
  
  private AppbrandServiceManager mServiceManager = new AppbrandServiceManager(this);
  
  private String mStopReason = "";
  
  private Handler mainHandler = new Handler(Looper.getMainLooper());
  
  private MiniAppContextWrapper miniAppContextWrapper;
  
  private AppInfoEntity updateAppInfo;
  
  private AppbrandApplicationImpl() {
    GameModuleController.inst().registerService(this.mServiceManager);
    this.mServiceManager.register(WebViewManager.class);
    this.mServiceManager.register(TimeLineReporter.class);
    this.mServiceManager.register(JsRuntimeManager.class);
    this.mServiceManager.register(PerformanceService.class);
    this.mServiceManager.register(PreloadManager.class);
    this.mServiceManager.register(SwitchManager.class);
    this.mServiceManager.register(MpTimeLineReporter.class);
    this.mServiceManager.register(FileAccessLogger.class);
    this.mServiceManager.register(AppConfigManager.class);
    this.mServiceManager.register(ShortcutService.class);
    this.mServiceManager.register(LaunchScheduler.class);
    this.mServiceManager.register(LoadPathInterceptor.class);
    this.mServiceManager.register(TimeLogger.class);
    this.mServiceManager.register(AppbrandBroadcastService.class);
    this.mServiceManager.register(PageRouter.class);
    this.mServiceManager.register(HostSnapShotManager.class);
    this.mServiceManager.register(RenderSnapShotManager.class);
    this.mServiceManager.register(BlockPageManager.class);
    this.mServiceManager.register(FavoriteGuideWidget.class);
    this.mServiceManager.register(WebAppPreloadManager.class);
    this.mServiceManager.register(AutoTestManager.class);
    this.mServiceManager.register(MetaService.class);
    this.mServiceManager.register(PkgService.class);
    this.mServiceManager.register(MainMessageLoggerManager.class);
    this.mForeBackgroundManager = new ForeBackgroundManager();
  }
  
  public static AppbrandApplicationImpl getInst() {
    // Byte code:
    //   0: ldc com/tt/miniapp/AppbrandApplicationImpl
    //   2: monitorenter
    //   3: invokestatic isBdpProcess : ()Z
    //   6: ifne -> 23
    //   9: ldc 'tma_AppbrandApplicationImpl'
    //   11: iconst_1
    //   12: anewarray java/lang/Object
    //   15: dup
    //   16: iconst_0
    //   17: ldc '这个类只应该在小程序、小游戏、UC进程里被使用'
    //   19: aastore
    //   20: invokestatic logOrThrow : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   23: getstatic com/tt/miniapp/AppbrandApplicationImpl.sInstance : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   26: ifnonnull -> 60
    //   29: ldc com/tt/miniapp/AppbrandApplicationImpl
    //   31: monitorenter
    //   32: getstatic com/tt/miniapp/AppbrandApplicationImpl.sInstance : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   35: ifnonnull -> 48
    //   38: new com/tt/miniapp/AppbrandApplicationImpl
    //   41: dup
    //   42: invokespecial <init> : ()V
    //   45: putstatic com/tt/miniapp/AppbrandApplicationImpl.sInstance : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   48: ldc com/tt/miniapp/AppbrandApplicationImpl
    //   50: monitorexit
    //   51: goto -> 60
    //   54: astore_0
    //   55: ldc com/tt/miniapp/AppbrandApplicationImpl
    //   57: monitorexit
    //   58: aload_0
    //   59: athrow
    //   60: getstatic com/tt/miniapp/AppbrandApplicationImpl.sInstance : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   63: astore_0
    //   64: ldc com/tt/miniapp/AppbrandApplicationImpl
    //   66: monitorexit
    //   67: aload_0
    //   68: areturn
    //   69: astore_0
    //   70: ldc com/tt/miniapp/AppbrandApplicationImpl
    //   72: monitorexit
    //   73: aload_0
    //   74: athrow
    // Exception table:
    //   from	to	target	type
    //   3	23	69	finally
    //   23	32	69	finally
    //   32	48	54	finally
    //   48	51	54	finally
    //   55	58	54	finally
    //   58	60	69	finally
    //   60	64	69	finally
  }
  
  public void finish() {
    this.mForeBackgroundManager.clear();
  }
  
  public e getActivityLife() {
    return this.mActivityLife;
  }
  
  public AppConfig getAppConfig() {
    return ((AppConfigManager)getService(AppConfigManager.class)).getAppConfig();
  }
  
  public AppInfoEntity getAppInfo() {
    return this.mAppInfo;
  }
  
  public a<String, Boolean> getCurrentPageHideShareMenuArrayMap() {
    return this.mCurrentPageHideShareMenuArrayMap;
  }
  
  public String getCurrentPagePath() {
    return this.mCurrentPagePath;
  }
  
  public String getCurrentPageType() {
    return this.mCurrentPageType;
  }
  
  public String getCurrentPageUrl() {
    return this.mCurrentPageUrl;
  }
  
  public int getCurrentWebViewId() {
    return this.mCurrentWebViewId;
  }
  
  public String getCurrentWebViewUrl() {
    WebViewManager webViewManager = getWebViewManager();
    if (webViewManager != null) {
      WebViewManager.IRender iRender = webViewManager.getCurrentIRender();
      if (iRender != null) {
        NativeViewManager nativeViewManager = iRender.getNativeViewManager();
        if (nativeViewManager != null) {
          NativeWebView nativeWebView = nativeViewManager.getCurrentNativeWebView();
          if (nativeWebView != null) {
            WebView webView = nativeWebView.getWebView();
            if (webView != null)
              return webView.getUrl(); 
          } 
        } 
      } 
    } 
    return null;
  }
  
  public ForeBackgroundManager getForeBackgroundManager() {
    return this.mForeBackgroundManager;
  }
  
  public j getJsBridge() {
    return ((JsRuntimeManager)getService(JsRuntimeManager.class)).getJsBridge();
  }
  
  public boolean getJumToApp() {
    return this.jumpToApp;
  }
  
  public LifeCycleManager getLifeCycleManager() {
    return this.mServiceManager.<LifeCycleManager>get(LifeCycleManager.class);
  }
  
  public Handler getMainHandler() {
    return this.mainHandler;
  }
  
  public MiniAppContextWrapper getMiniAppContext() {
    // Byte code:
    //   0: aload_0
    //   1: getfield miniAppContextWrapper : Lcom/tt/miniapp/base/MiniAppContextWrapper;
    //   4: ifnonnull -> 50
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield miniAppContextWrapper : Lcom/tt/miniapp/base/MiniAppContextWrapper;
    //   13: ifnonnull -> 40
    //   16: aload_0
    //   17: new com/tt/miniapp/AppbrandApplicationImpl$1
    //   20: dup
    //   21: aload_0
    //   22: invokespecial <init> : (Lcom/tt/miniapp/AppbrandApplicationImpl;)V
    //   25: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   28: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   31: invokestatic create : (Lcom/tt/miniapp/base/IActivityFetcher;Landroid/content/Context;)Lcom/tt/miniapp/base/MiniAppContextWrapper$Builder;
    //   34: invokevirtual build : ()Lcom/tt/miniapp/base/MiniAppContextWrapper;
    //   37: putfield miniAppContextWrapper : Lcom/tt/miniapp/base/MiniAppContextWrapper;
    //   40: aload_0
    //   41: monitorexit
    //   42: goto -> 50
    //   45: astore_1
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_1
    //   49: athrow
    //   50: aload_0
    //   51: getfield miniAppContextWrapper : Lcom/tt/miniapp/base/MiniAppContextWrapper;
    //   54: areturn
    // Exception table:
    //   from	to	target	type
    //   9	40	45	finally
    //   40	42	45	finally
    //   46	48	45	finally
  }
  
  public MiniAppLaunchConfig getMiniAppLaunchConfig() {
    return this.mMiniAppLaunchConfig;
  }
  
  public IPreload getPreloadManager() {
    return (IPreload)getService(PreloadManager.class);
  }
  
  public String getRequestRefer() {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("https://tmaservice.developer.toutiao.com");
    AppInfoEntity appInfoEntity = this.mAppInfo;
    if (appInfoEntity != null && appInfoEntity.appId != null && this.mAppInfo.version != null) {
      stringBuffer.append("?appid=");
      stringBuffer.append(this.mAppInfo.appId);
      stringBuffer.append("&version=");
      stringBuffer.append(this.mAppInfo.version);
    } 
    return stringBuffer.toString();
  }
  
  public RouteEventCtrl getRouteEventCtrl() {
    return this.mRouteEventCtrl;
  }
  
  public String getSchema() {
    return this.mSchema;
  }
  
  public <T extends AppbrandServiceManager.ServiceBase> T getService(Class<T> paramClass) {
    return this.mServiceManager.get(paramClass);
  }
  
  public String getStopReason() {
    return this.mStopReason;
  }
  
  public AppInfoEntity getUpdateAppInfo() {
    return this.updateAppInfo;
  }
  
  public WebViewManager getWebViewManager() {
    return getService(WebViewManager.class);
  }
  
  public AppConfig initAppConfig() {
    return ((AppConfigManager)getService(AppConfigManager.class)).initAppConfig();
  }
  
  public void invokeHandler(int paramInt1, int paramInt2, String paramString) {
    WebViewManager webViewManager = getWebViewManager();
    if (webViewManager != null)
      webViewManager.invokeHandler(paramInt1, paramInt2, paramString); 
  }
  
  public void markNeedPreload() {
    this.mNeedNotifyPreloadEmptyProcess = true;
  }
  
  public void notifyPreloadEmptyProcess() {
    if (this.mNeedNotifyPreloadEmptyProcess) {
      ThreadUtil.runOnWorkThread(new Action() {
            public void act() {
              InnerHostProcessBridge.notifyPreloadEmptyProcess();
            }
          },  (Scheduler)LaunchThreadPool.getInst());
      this.mNeedNotifyPreloadEmptyProcess = false;
    } 
  }
  
  public void onCreate() {
    getLifeCycleManager().notifyAppCreate();
    AppBrandLogger.d("tma_AppbrandApplicationImpl", new Object[] { "--------onCreate---- " });
    this.mRouteEventCtrl = new RouteEventCtrl();
    AppbrandContext.getInst().getApplicationContext().registerActivityLifecycleCallbacks(new AppbrandActivityLifeCycleCallback(this));
    ArrayList<ActionSheetImpl> arrayList = new ArrayList();
    arrayList.add(new ActionSheetImpl(AppbrandContext.getInst()));
    arrayList.add(new DialogImpl(AppbrandContext.getInst()));
    arrayList.add(new ToastImpl(AppbrandContext.getInst()));
    arrayList.add(new HideToastImpl(AppbrandContext.getInst()));
    arrayList.add(new PhoneCallImpl(AppbrandContext.getInst()));
    arrayList.add(new FavoriteMiniAppMenuItem.FavoriteModule(AppbrandContext.getInst()));
    for (NativeModule nativeModule : arrayList)
      ModeManager.getInst().register(nativeModule.getName(), nativeModule); 
    List list = HostDependManager.getInst().createNativeModules(AppbrandContext.getInst());
    if (list != null)
      for (NativeModule nativeModule : list)
        ModeManager.getInst().register(nativeModule.getName(), nativeModule);  
  }
  
  public void onError(String paramString) {}
  
  public void onHide() {
    AppBrandLogger.d("tma_AppbrandApplicationImpl", new Object[] { "onHide" });
    HostDependManager.getInst().getMiniAppLifeCycleInstance();
    this.mForeBackgroundManager.onBackground();
    ToastManager.clearToast();
    AudioManager.getInst().onEnterBackground();
    GameModuleController.inst().onHide();
    getLifeCycleManager().notifyAppHide();
    j j = getJsBridge();
    if (j != null) {
      j.onHide();
    } else {
      RouteEventCtrl routeEventCtrl = getRouteEventCtrl();
      if (routeEventCtrl != null)
        routeEventCtrl.onAppHide(); 
    } 
    if (!this.isOpenedSchema && !InnerHostProcessBridge.isInJumpList((getAppInfo()).appId) && !BgAudioManagerClient.getInst().needKeepAlive()) {
      AppBrandLogger.i("tma_AppbrandApplicationImpl", new Object[] { "小程序进入后台等待 5 分钟后被 SDK 逻辑杀死" });
      AppProcessManager.getProcessHandler().sendEmptyMessageDelayed(1, 300000L);
    } else {
      AppBrandLogger.i("tma_AppbrandApplicationImpl", new Object[] { "小程序进入后台时保活，不会被 SDK 逻辑自动杀死" });
    } 
    Iterator<ILifecycleObserver> iterator = this.mObservers.iterator();
    while (iterator.hasNext())
      ((ILifecycleObserver)iterator.next()).onHide(); 
    StorageManager.reportDiskOccupy();
  }
  
  public void onShow() {
    HostDependManager.getInst().getMiniAppLifeCycleInstance();
    this.mForeBackgroundManager.onForeground();
    AudioManager.getInst().onEnterForeground();
    GameModuleController.inst().onShow();
    getLifeCycleManager().notifyAppShow();
    AppBrandLogger.d("tma_AppbrandApplicationImpl", new Object[] { "onShow" });
    this.isOpenedSchema = false;
    j j = getJsBridge();
    if (j != null) {
      j.onShow();
    } else {
      RouteEventCtrl routeEventCtrl = getRouteEventCtrl();
      if (routeEventCtrl != null)
        routeEventCtrl.onAppShow(); 
    } 
    InnerHostProcessBridge.setTmaLaunchFlag();
    AppProcessManager.getProcessHandler().removeMessages(1);
    Iterator<ILifecycleObserver> iterator = this.mObservers.iterator();
    while (iterator.hasNext())
      ((ILifecycleObserver)iterator.next()).onShow(); 
  }
  
  public void publish(int paramInt, String paramString1, String paramString2) {
    WebViewManager webViewManager = getWebViewManager();
    if (webViewManager != null)
      webViewManager.publish(paramInt, paramString1, paramString2); 
  }
  
  public void registerLifecycleObserver(ILifecycleObserver paramILifecycleObserver) {
    this.mObservers.add(paramILifecycleObserver);
  }
  
  public void setActivityLife(e parame) {
    this.mActivityLife = parame;
  }
  
  public void setAppInfo(final AppInfoEntity appInfo) {
    this.mAppInfo = appInfo;
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            ApiPermissionManager.initApiWhiteList(appInfo.ttSafeCode);
            ApiPermissionManager.initApiBlackList(appInfo.ttBlackCode);
            ApiPermissionManager.initHostMethodWhiteList(appInfo.encryptextra);
            appInfo.parseDomain();
          }
        },  (Scheduler)LaunchThreadPool.getInst());
  }
  
  public void setCurrentPageHideShareMenuArrayMap(a<String, Boolean> parama) {
    this.mCurrentPageHideShareMenuArrayMap = parama;
  }
  
  public void setCurrentPagePath(String paramString) {
    this.mCurrentPagePath = paramString;
  }
  
  public void setCurrentPageType(String paramString) {
    this.mCurrentPageType = paramString;
  }
  
  public void setCurrentPageUrl(String paramString) {
    this.mCurrentPageUrl = paramString;
  }
  
  public void setCurrentWebViewId(int paramInt) {
    this.mCurrentWebViewId = paramInt;
  }
  
  public void setJumpToApp(boolean paramBoolean) {
    this.jumpToApp = paramBoolean;
  }
  
  public void setMiniAppLaunchConfig(MiniAppLaunchConfig paramMiniAppLaunchConfig) {
    this.mMiniAppLaunchConfig = paramMiniAppLaunchConfig;
  }
  
  public void setOpenedSchema(boolean paramBoolean) {
    this.isOpenedSchema = paramBoolean;
  }
  
  public void setSchema(String paramString) {
    this.mSchema = paramString;
  }
  
  public void setStopReason(String paramString) {
    this.mStopReason = paramString;
  }
  
  public void setUpdateAppInfo(AppInfoEntity paramAppInfoEntity) {
    this.updateAppInfo = paramAppInfoEntity;
  }
  
  public void ungisterLifecycleObserver(ILifecycleObserver paramILifecycleObserver) {
    this.mObservers.remove(paramILifecycleObserver);
  }
  
  static class AppbrandActivityLifeCycleCallback implements Application.ActivityLifecycleCallbacks {
    AppbrandActivityLifeCycleCallback(AppbrandApplicationImpl param1AppbrandApplicationImpl) {}
    
    public void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {
      HostProcessBridge.callHostLifecycleAction(param1Activity, "onCreate");
    }
    
    public void onActivityDestroyed(Activity param1Activity) {}
    
    public void onActivityPaused(Activity param1Activity) {
      AppBrandLogger.d("tma_AppbrandApplicationImpl", new Object[] { "onActivityPaused" });
      HostProcessBridge.callHostLifecycleAction(param1Activity, "onPause");
    }
    
    public void onActivityResumed(Activity param1Activity) {
      HostProcessBridge.callHostLifecycleAction(param1Activity, "onResume");
      AppBrandLogger.d("tma_AppbrandApplicationImpl", new Object[] { "onActivityResumed" });
    }
    
    public void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {
      AppBrandLogger.d("tma_AppbrandApplicationImpl", new Object[] { "onActivitySaveInstanceState" });
    }
    
    public void onActivityStarted(Activity param1Activity) {}
    
    public void onActivityStopped(Activity param1Activity) {
      AppBrandLogger.d("tma_AppbrandApplicationImpl", new Object[] { "onActivityStopped" });
    }
  }
  
  public static interface ILifecycleObserver {
    void onHide();
    
    void onShow();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\AppbrandApplicationImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */