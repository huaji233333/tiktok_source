package com.tt.miniapphost;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import com.storage.async.Action;
import com.tt.frontendapiinterface.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.TTAppbrandTabUI;
import com.tt.miniapp.adsite.AdSiteBrowser;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.manager.AppInfoManager;
import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapp.manager.HostSnapShotManager;
import com.tt.miniapp.manager.SchemeEntityHelper;
import com.tt.miniapp.manager.SnapshotManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.shortcut.ShortcutService;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.game.GameNotReadyActivityProxy;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.miniapphost.view.BaseActivity;

public class MiniappHostBase extends BaseActivity {
  private boolean isFilledUpContainer;
  
  protected IActivityProxy mActivityProxy;
  
  private int mDefaultFragmentBackground;
  
  private boolean mIsOnActivityStackTop;
  
  private static void startCacheSpecialCrossProcessData() {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            HostProcessBridge.getLoginCookie();
            HostProcessBridge.getNetCommonParams();
            HostProcessBridge.getUserInfo();
            InnerHostProcessBridge.getPlatformSession((AppbrandApplicationImpl.getInst().getAppInfo()).appId);
          }
        },  LaunchThreadPool.getInst());
  }
  
  public void attachBaseContext(Context paramContext) {
    super.attachBaseContext(paramContext);
    AppbrandContext.tryKillIfNotInit(paramContext);
  }
  
  protected IActivityProxy createRealActivity(int paramInt) {
    GameNotReadyActivityProxy gameNotReadyActivityProxy;
    if (paramInt != 2) {
      if (AdSiteManager.getInstance().isAdSiteBrowserInited()) {
        if (AdSiteManager.getInstance().isAdSiteBrowser())
          return (IActivityProxy)new AdSiteBrowser((FragmentActivity)this); 
      } else {
        AppInfoEntity appInfoEntity2 = AppbrandApplicationImpl.getInst().getAppInfo();
        AppInfoEntity appInfoEntity1 = appInfoEntity2;
        if (appInfoEntity2 == null) {
          Intent intent = getIntent();
          appInfoEntity1 = appInfoEntity2;
          if (intent != null) {
            appInfoEntity2 = (AppInfoEntity)intent.getParcelableExtra("microapp_appinfo");
            appInfoEntity1 = appInfoEntity2;
            if (appInfoEntity2 == null) {
              String str = intent.getStringExtra("microapp_url");
              appInfoEntity1 = appInfoEntity2;
              if (!TextUtils.isEmpty(str))
                appInfoEntity1 = AppInfoManager.generateInitAppInfo(str); 
            } 
          } 
        } 
        if (AdSiteManager.getInstance().initIsAdSiteBrowser(getApplicationContext(), appInfoEntity1))
          return (IActivityProxy)new AdSiteBrowser((FragmentActivity)this); 
      } 
      return (IActivityProxy)new TTAppbrandTabUI((FragmentActivity)this);
    } 
    IActivityProxy iActivityProxy2 = GameModuleController.inst().getGameActivity((FragmentActivity)this);
    IActivityProxy iActivityProxy1 = iActivityProxy2;
    if (iActivityProxy2 == null) {
      gameNotReadyActivityProxy = new GameNotReadyActivityProxy((FragmentActivity)this);
      LoadHelper.handleMiniProcessFail(ErrorCode.MAIN.GAME_MODULE_NOT_READY.getCode());
    } 
    return (IActivityProxy)gameNotReadyActivityProxy;
  }
  
  public View findViewById(int paramInt) {
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null) {
      View view = iActivityProxy.findViewById(paramInt);
      if (view != null)
        return view; 
    } 
    return super.findViewById(paramInt);
  }
  
  public IActivityProxy getActivityProxy() {
    return this.mActivityProxy;
  }
  
  public int getDefaultFragmentBackground() {
    return this.mDefaultFragmentBackground;
  }
  
  public boolean isFilledUpContainer() {
    return this.isFilledUpContainer;
  }
  
  public boolean isInHostStack() {
    return false;
  }
  
  public boolean isOnActivityStackTop() {
    return this.mIsOnActivityStackTop;
  }
  
  public boolean isTriggeredHomeOrRecentApp() {
    return ((HostSnapShotManager)AppbrandApplicationImpl.getInst().getService(HostSnapShotManager.class)).isTriggeredHomeOrRecentApp();
  }
  
  protected boolean needGetSnapShot() {
    return true;
  }
  
  public void notifyUpdateSnapShot() {
    ((HostSnapShotManager)AppbrandApplicationImpl.getInst().getService(HostSnapShotManager.class)).notifyUpdateSnapShot();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null && iActivityProxy.onActivityResult(paramInt1, paramInt2, paramIntent)) {
      c.a().b();
      return;
    } 
    c.a().b();
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onBackPressed() {
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onBackPressed" });
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onBackPressed(); 
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(null);
    TimeLogger timeLogger = TimeLogger.getInstance();
    int i = 1;
    timeLogger.logTimeDuration(new String[] { "MiniappHostBase_onCreate" });
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    if (appbrandContext != null)
      appbrandContext.setCurrentActivity(this); 
    Intent intent = getIntent();
    if (intent != null) {
      int j = intent.getIntExtra("app_type", 1);
      i = j;
      if (ChannelUtil.isLocalTest()) {
        SchemeEntityHelper.remoteValidate((Activity)this, j, intent.getStringExtra("microapp_url"));
        i = j;
      } 
    } 
    this.mActivityProxy = createRealActivity(i);
    if (this.mActivityProxy.beforeOnCreate(paramBundle)) {
      this.mActivityProxy.onCreate(paramBundle);
      this.mActivityProxy.afterOnCreate(paramBundle);
      AppbrandApplicationImpl.getInst().getForeBackgroundManager().registerCloseSystemDialogReceiver();
      final HostSnapShotManager hostSnapShotManager = (HostSnapShotManager)AppbrandApplicationImpl.getInst().getService(HostSnapShotManager.class);
      if (needGetSnapShot())
        hostSnapShotManager.updateSnapShotView(); 
      if (!isInHostStack())
        AppbrandApplicationImpl.getInst().getForeBackgroundManager().registerForeBackgroundListener((ForeBackgroundManager.ForeBackgroundListener)new ForeBackgroundManager.DefaultForeBackgroundListener() {
              public void onTriggerHomeOrRecentApp() {
                AppbrandApplicationImpl.getInst().setJumpToApp(false);
                if (MiniappHostBase.this.needGetSnapShot()) {
                  hostSnapShotManager.setTriggeredHomeOrRecentApp(true);
                  hostSnapShotManager.clearSwipeBackground();
                } 
              }
            }); 
      startCacheSpecialCrossProcessData();
      return;
    } 
    this.mActivityProxy = null;
    LoadHelper.handleMiniProcessFail(ErrorCode.MAIN.BEFORE_ON_CREATE_CHECK_FAIL.getCode());
  }
  
  public void onDestroy() {
    super.onDestroy();
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onDestroy" });
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onDestroy(); 
    AppbrandApplicationImpl.getInst().finish();
    ProcessUtil.killCurrentMiniAppProcess((Context)this);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    IActivityProxy iActivityProxy = this.mActivityProxy;
    return (iActivityProxy != null && iActivityProxy.onKeyDown(paramInt, paramKeyEvent)) ? true : super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void onNewIntent(Intent paramIntent) {
    super.onNewIntent(paramIntent);
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onNewIntent" });
    ((MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class)).addPoint("activity_on_create_begin", (new MpTimeLineReporter.ExtraBuilder()).kv("start_type", Integer.valueOf(2)).build());
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onNewIntent(paramIntent); 
    if (needGetSnapShot()) {
      HostSnapShotManager hostSnapShotManager = (HostSnapShotManager)AppbrandApplicationImpl.getInst().getService(HostSnapShotManager.class);
      hostSnapShotManager.setTriggeredHomeOrRecentApp(false);
      hostSnapShotManager.setNeedUpdateSnapshotWhenOnStart(true);
    } 
  }
  
  public void onPause() {
    super.onPause();
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onPause" });
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onPause(); 
    this.mIsOnActivityStackTop = false;
    ((ShortcutService)AppbrandApplicationImpl.getInst().getService(ShortcutService.class)).onActivityPause();
  }
  
  public void onPostCreate(Bundle paramBundle) {
    super.onPostCreate(paramBundle);
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onPostCreate(paramBundle); 
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint); 
  }
  
  protected void onRestart() {
    super.onRestart();
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onRestart" });
  }
  
  public void onResume() {
    super.onResume();
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onResume" });
    if (AppbrandApplicationImpl.getInst().getJumToApp())
      (AppbrandApplicationImpl.getInst().getAppInfo()).scene = HostDependManager.getInst().getScene("back_mp"); 
    AppbrandApplicationImpl.getInst().setJumpToApp(false);
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onResume(); 
    this.mIsOnActivityStackTop = true;
    ((ShortcutService)AppbrandApplicationImpl.getInst().getService(ShortcutService.class)).onActivityResume();
  }
  
  public void onSaveInstanceState(Bundle paramBundle, PersistableBundle paramPersistableBundle) {
    TimeLogger.getInstance().logTimeDuration(new String[] { "MiniappHostBase_onSaveInstanceState" });
  }
  
  public void onStart() {
    super.onStart();
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onStart" });
    if (needGetSnapShot()) {
      HostSnapShotManager hostSnapShotManager = (HostSnapShotManager)AppbrandApplicationImpl.getInst().getService(HostSnapShotManager.class);
      if (this.mActivityProxy instanceof TTAppbrandTabUI)
        if (hostSnapShotManager.isTriggeredHomeOrRecentApp()) {
          hostSnapShotManager.updateSnapShotView((Context)this, true);
        } else if (hostSnapShotManager.isNeedUpdateSnapshotWhenOnStart()) {
          hostSnapShotManager.updateSnapShotView();
        }  
      getWindow().clearFlags(8192);
      hostSnapShotManager.setNeedUpdateSnapshotWhenOnStart(false);
    } 
    SnapshotManager.clearCacheSnapshot();
  }
  
  public void onStop() {
    super.onStop();
    AppBrandLogger.d("MiniappHostBase", new Object[] { "onStop" });
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onStop(); 
  }
  
  public void onTrimMemory(int paramInt) {
    if (paramInt != 5 && paramInt != 10 && paramInt != 15)
      return; 
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onMemoryWarning(paramInt); 
  }
  
  public void onUserInteraction() {
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onUserInteraction(); 
    super.onUserInteraction();
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onWindowFocusChanged(paramBoolean); 
    super.onWindowFocusChanged(paramBoolean);
  }
  
  public void setDefaultFragmentBackground(int paramInt) {
    this.mDefaultFragmentBackground = paramInt;
  }
  
  public void setFilledUpContainer(boolean paramBoolean) {
    this.isFilledUpContainer = paramBoolean;
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt) {
    super.startActivityForResult(paramIntent, paramInt);
    IActivityProxy iActivityProxy = this.mActivityProxy;
    if (iActivityProxy != null)
      iActivityProxy.onStartActivityForResult(paramIntent, paramInt); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\MiniappHostBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */