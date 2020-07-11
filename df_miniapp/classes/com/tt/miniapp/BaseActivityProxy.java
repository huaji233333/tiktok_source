package com.tt.miniapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Looper;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.storage.async.Action;
import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Scheduler;
import com.storage.async.Schedulers;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.d;
import com.tt.frontendapiinterface.h;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.database.usagerecord.UsageRecordInfo;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.event.external.search.SearchEventHelper;
import com.tt.miniapp.event.origin.OriginHelper;
import com.tt.miniapp.favorite.FavoriteGuideModel;
import com.tt.miniapp.favorite.FavoriteGuideWidget;
import com.tt.miniapp.launch.MiniAppLaunchConfig;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.manager.AppInfoManager;
import com.tt.miniapp.manager.DbManager;
import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapp.manager.SnapshotManager;
import com.tt.miniapp.manager.SynHistoryManager;
import com.tt.miniapp.msg.ad.BaseAdCtrl;
import com.tt.miniapp.mvp.TTAppbrandView;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandViewWindowBase;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.route.RouteEventCtrl;
import com.tt.miniapp.service.suffixmeta.SuffixMetaServiceInterface;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.time.CustomizeTimer;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapp.view.LaunchLoadingView;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.EventHelper;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.ModeManager;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.ParamManager;
import com.tt.miniapphost.entity.AnchorConfig;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.FeignHostConfig;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageUtils;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.ad.g;
import com.tt.option.ad.h;
import com.tt.option.w.d;
import com.tt.option.w.g;
import com.tt.option.w.h;
import com.tt.option.y.b;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseActivityProxy implements TTAppbrandView, IActivityProxy, g.a {
  protected boolean isShowingLoadingView = true;
  
  public FragmentActivity mActivity;
  
  public AppbrandApplicationImpl mApp;
  
  private List<WeakReference<h>> mBackPressedListenerWrList = new ArrayList<WeakReference<h>>();
  
  public TimeMeter mEntranceClickTimeMeter;
  
  protected boolean mHasActivityStoped = false;
  
  protected FrameLayout mHomeLayout;
  
  protected boolean mIsActivityRecreate = false;
  
  private boolean mIsCreated = false;
  
  protected long mLaunchDuration;
  
  public LaunchLoadingView mLaunchLoadingView;
  
  public CustomizeTimer mLaunchProfileTime = new CustomizeTimer();
  
  public TimeMeter mLoadStartTime;
  
  protected CustomizeTimer mLoadingViewShowTimes = new CustomizeTimer();
  
  public long mMicroAppStartShowTimes = 0L;
  
  private TimeMeter mOnActivityStartTime;
  
  public BaseActivityProxy(FragmentActivity paramFragmentActivity) {
    this.mActivity = paramFragmentActivity;
    this.mApp = AppbrandApplicationImpl.getInst();
  }
  
  private WeakReference<h> getMatchBackPressedListenerWr(h paramh) {
    for (WeakReference<h> weakReference : this.mBackPressedListenerWrList) {
      if ((h)weakReference.get() == paramh)
        return weakReference; 
    } 
    return null;
  }
  
  private void initMpValOnCreate(String paramString, Bundle paramBundle) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private void notifyLoadProgress(int paramInt) {
    HostDependManager.getInst().getMiniAppLifeCycleInstance();
    this.mApp.getAppInfo();
  }
  
  private void overrideActivityInAnimation(boolean paramBoolean1, boolean paramBoolean2) {
    if (!paramBoolean1) {
      if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle()) {
        if (paramBoolean2) {
          HostDependManager.getInst().overridePendingTransition((Activity)this.mActivity, 2131034237, 2131034235, 3);
          return;
        } 
        ActivityUtil.changeToSilentHideActivityAnimation((Activity)getActivity());
        return;
      } 
      HostDependManager.getInst().overridePendingTransition((Activity)this.mActivity, 2131034237, 2131034235, 1);
    } 
  }
  
  public static void recordMiniAppUsage(final AppInfoEntity entity, final long startTime) {
    AppBrandLogger.d("BaseActivityProxy", new Object[] { "appid = ", entity.appId, "starttime = ", Long.valueOf(startTime) });
    Observable.create(new Action() {
          public final void act() {
            if (AppbrandContext.getInst().getApplicationContext() == null) {
              AppBrandLogger.e("BaseActivityProxy", new Object[] { "recordMiniappUsage context == null" });
              return;
            } 
            if (TextUtils.isEmpty(entity.appId))
              return; 
            UsageRecordInfo usageRecordInfo = new UsageRecordInfo();
            usageRecordInfo.appID = entity.appId;
            usageRecordInfo.scene = entity.scene;
            usageRecordInfo.subScene = entity.subScene;
            long l2 = startTime;
            long l1 = 0L;
            if (l2 != 0L)
              l1 = System.currentTimeMillis() - startTime; 
            usageRecordInfo.duration = Long.valueOf(l1);
            usageRecordInfo.startTime = Long.valueOf(startTime);
            DbManager.getInstance().getUsageRecordDao().insert(usageRecordInfo);
          }
        }).schudleOn(Schedulers.shortIO()).subscribeSimple();
  }
  
  private void runOnUIThreadQuickly(Runnable paramRunnable) {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      paramRunnable.run();
      return;
    } 
    AppbrandContext.mainHandler.postAtFrontOfQueue(paramRunnable);
  }
  
  private void updateAnchorBtn(AnchorConfig paramAnchorConfig, Button paramButton) {
    ViewGroup.LayoutParams layoutParams = paramButton.getLayoutParams();
    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      int i;
      ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
      if ((this.mApp.getAppInfo()).isLandScape) {
        i = 2097414147;
      } else {
        i = 2097414148;
      } 
      marginLayoutParams.bottomMargin = this.mActivity.getResources().getDimensionPixelSize(i);
      paramButton.setLayoutParams(layoutParams);
    } 
    GradientDrawable gradientDrawable = new GradientDrawable();
    gradientDrawable.setColor(paramAnchorConfig.getBackgroundColor());
    gradientDrawable.setCornerRadius(UIUtils.dip2Px((Context)this.mActivity, 27.0F));
    paramButton.setBackground((Drawable)gradientDrawable);
    paramButton.setText(paramAnchorConfig.getTitle());
    if ("ADD".equals(paramAnchorConfig.getAction()) || "REMOVE".equals(paramAnchorConfig.getAction()) || "REPLACE".equals(paramAnchorConfig.getAction())) {
      int i;
      paramButton.setVisibility(0);
      if ("ADD".equals(paramAnchorConfig.getAction()) || "REPLACE".equals(paramAnchorConfig.getAction())) {
        i = 2097479688;
      } else {
        i = 2097479689;
      } 
      paramButton.setCompoundDrawablesWithIntrinsicBounds(i, 0, 0, 0);
      return;
    } 
    paramButton.setVisibility(4);
  }
  
  private void updateLaunchType(AppInfoEntity paramAppInfoEntity) {
    if (this.mIsActivityRecreate)
      paramAppInfoEntity.launchType = "resume"; 
  }
  
  public final void afterOnCreate(Bundle paramBundle) {
    AppBrandLogger.i("BaseActivityProxy", new Object[] { "afterOnCreate" });
    ((LaunchScheduler)this.mApp.<LaunchScheduler>getService(LaunchScheduler.class)).bindView(this);
    this.mActivity.getWindow().setBackgroundDrawable((Drawable)new ColorDrawable(0));
    this.mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
    this.mHomeLayout = (FrameLayout)((PreloadManager)this.mApp.<PreloadManager>getService(PreloadManager.class)).getPreloadedView(5);
    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
    this.mHomeLayout.setLayoutParams(layoutParams);
    ((MpTimeLineReporter)this.mApp.<MpTimeLineReporter>getService(MpTimeLineReporter.class)).addPoint("activity_on_create_end");
    ((AutoTestManager)this.mApp.<AutoTestManager>getService(AutoTestManager.class)).addEvent("afterOnCreate");
  }
  
  public final boolean beforeOnCreate(Bundle paramBundle) {
    ((AutoTestManager)this.mApp.<AutoTestManager>getService(AutoTestManager.class)).addEvent("beforeOnCreate");
    paramBundle = this.mActivity.getIntent().getBundleExtra("mp_launch_extra");
    if (paramBundle != null) {
      MiniAppLaunchConfig miniAppLaunchConfig = (MiniAppLaunchConfig)paramBundle.getParcelable("launchConfig");
      if (miniAppLaunchConfig != null)
        AppbrandApplicationImpl.getInst().setMiniAppLaunchConfig(miniAppLaunchConfig); 
    } 
    overrideActivityInAnimation(false, false);
    AppBrandLogger.i("BaseActivityProxy", new Object[] { "beforeOnCreate" });
    this.mOnActivityStartTime = new TimeMeter();
    Intent intent = this.mActivity.getIntent();
    if (intent == null || !intent.hasExtra("microapp_url")) {
      AppBrandLogger.e("BaseActivityProxy", new Object[] { "onCreate fail, intent == ", intent });
      EventParamsValue.PARAMS_EXIT_TYPE = "others";
      EventParamsValue.IS_OTHER_FLAG = true;
      EventHelper.mpInitResult(null, null, null, null, false, 0L, "fail", "error intent");
      return false;
    } 
    return true;
  }
  
  protected boolean consumeBackPress() {
    for (WeakReference<h> weakReference : this.mBackPressedListenerWrList) {
      if (weakReference != null) {
        h h = weakReference.get();
        if (h != null && h.onBackPressed())
          return true; 
      } 
    } 
    return false;
  }
  
  public void dismissFavoriteGuide() {
    dismissFavoriteGuide(0);
  }
  
  public void dismissFavoriteGuide(int paramInt) {
    FavoriteGuideWidget favoriteGuideWidget = this.mApp.<FavoriteGuideWidget>getService(FavoriteGuideWidget.class);
    if (paramInt != 0) {
      if (paramInt != 1) {
        if (paramInt != 2)
          return; 
        favoriteGuideWidget.dismissBar();
        return;
      } 
      favoriteGuideWidget.dismissTip();
      return;
    } 
    favoriteGuideWidget.dismissAll();
  }
  
  public FragmentActivity getActivity() {
    return this.mActivity;
  }
  
  public void getAnchorShareInfo(final AnchorConfig anchorConfig) {
    HostDependManager.getInst().getShareBaseInfo(anchorConfig.getChannel(), new d() {
          public void onFail() {
            BaseActivityProxy.this.shareResultEvent("fail", anchorConfig.getChannel());
            HostDependManager.getInst().showToast((Context)BaseActivityProxy.this.mActivity, null, UIUtils.getString(2097741862), 0L, null);
          }
          
          public void onSuccess(final h shareInfoModel, g param1g) {
            ThreadUtil.runOnUIThread(new Runnable() {
                  public void run() {
                    if (BaseActivityProxy.this.mApp != null && !BaseActivityProxy.this.mApp.getAppInfo().isGame()) {
                      AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)((PageRouter)BaseActivityProxy.this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getTopView();
                      if (appbrandViewWindowBase != null) {
                        AppbrandSinglePage appbrandSinglePage = appbrandViewWindowBase.getCurrentPage();
                        appbrandSinglePage.getTitleBar().setTitleBarCapsuleVisible(false);
                        appbrandSinglePage.getTitleBar().setPageCloseButtonVisible(false);
                      } 
                    } 
                    final Button anchorBtn = (Button)BaseActivityProxy.this.mActivity.findViewById(2097545246);
                    if (button != null)
                      button.setVisibility(4); 
                    ThreadUtil.runOnWorkThread(new Action() {
                          public void act() {
                            Bitmap bitmap = SnapshotManager.getSnapshot((Activity)BaseActivityProxy.this.mActivity);
                            File file = SnapshotManager.saveSnapshotFile((Activity)BaseActivityProxy.this.mActivity, SnapshotManager.compressSnapshot(bitmap));
                            anchorConfig.setSnapshotUrl(file.getAbsolutePath());
                            ThreadUtil.runOnUIThread(new Runnable() {
                                  public void run() {
                                    if (BaseActivityProxy.this.mApp != null && !BaseActivityProxy.this.mApp.getAppInfo().isGame()) {
                                      AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)((PageRouter)BaseActivityProxy.this.mApp.<PageRouter>getService(PageRouter.class)).getViewWindowRoot().getTopView();
                                      if (appbrandViewWindowBase != null) {
                                        AppbrandSinglePage appbrandSinglePage = appbrandViewWindowBase.getCurrentPage();
                                        appbrandSinglePage.getTitleBar().setTitleBarCapsuleVisible(true);
                                        appbrandSinglePage.getTitleBar().setPageCloseButtonVisible(true);
                                      } 
                                    } 
                                    if (anchorBtn != null && !success)
                                      anchorBtn.setVisibility(0); 
                                  }
                                });
                          }
                        }(Scheduler)LaunchThreadPool.getInst());
                  }
                });
          }
        });
  }
  
  protected AppConfig getAppConfig() {
    return this.mApp.getAppConfig();
  }
  
  public long getLaunchDuration() {
    return this.mLaunchDuration;
  }
  
  public LaunchScheduler getLaunchScheduler() {
    return this.mApp.<LaunchScheduler>getService(LaunchScheduler.class);
  }
  
  public abstract FrameLayout getRootView();
  
  public int getScreenOrientation() {
    return (this.mApp.getAppInfo()).isLandScape ? 0 : 1;
  }
  
  public Integer getScreenOrientationFromAppConfig() {
    boolean bool = AdSiteManager.getInstance().isAdSiteBrowser();
    Integer integer = Integer.valueOf(0);
    return bool ? integer : ((getAppConfig() != null) ? ("portrait".equals((getAppConfig()).screenOrientation) ? integer : Integer.valueOf(1)) : null);
  }
  
  public void hideAnchorButton() {
    FragmentActivity fragmentActivity = this.mActivity;
    if (fragmentActivity == null)
      return; 
    Button button = (Button)fragmentActivity.findViewById(2097545246);
    if (button == null)
      return; 
    button.setVisibility(4);
  }
  
  public void initAnchor() {
    FragmentActivity fragmentActivity = this.mActivity;
    if (fragmentActivity == null)
      return; 
    final Button anchorBtn = (Button)fragmentActivity.findViewById(2097545246);
    if (button == null)
      return; 
    if (TextUtils.isEmpty(this.mApp.getSchema()) || this.mApp.getAppInfo() == null) {
      button.setVisibility(4);
      return;
    } 
    final AnchorConfig anchorConfig = HostDependManager.getInst().getAnchorConfig(this.mApp.getSchema());
    if (anchorConfig == null) {
      button.setVisibility(4);
      return;
    } 
    button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if ("REPLACE".equals(anchorConfig.getAction())) {
              HostDependManager.getInst().showModal((Activity)BaseActivityProxy.this.mActivity, "", "", anchorConfig.getReplaceTitle(), true, "取消", "", "添加", "", new NativeModule.NativeModuleCallback<Integer>() {
                    public void onNativeModuleCall(Integer param2Integer) {
                      int i = param2Integer.intValue();
                      if (i != 0) {
                        if (i != 1)
                          return; 
                        BaseActivityProxy.this.getAnchorShareInfo(anchorConfig);
                      } 
                    }
                  });
              return;
            } 
            if ("ADD".equals(anchorConfig.getAction())) {
              BaseActivityProxy.this.getAnchorShareInfo(anchorConfig);
              return;
            } 
            if ("REMOVE".endsWith(anchorConfig.getAction()) && BaseActivityProxy.this.sendShareInfoToMainProcess(null, anchorConfig))
              anchorBtn.setVisibility(4); 
          }
        });
    updateAnchorBtn(anchorConfig, button);
  }
  
  public void metaExpired() {
    AppBrandLogger.d("BaseActivityProxy", new Object[] { "metaExpired" });
    InnerEventHelper.mpLoadResult(TimeMeter.stop(this.mLoadStartTime), "qr_code_expired", "qr_code_expired", this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
    if (!this.mApp.getAppInfo().isGame())
      SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 0, SearchEventHelper.failReasonClient("qr_code_expired")); 
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "BaseActivityProxy_MetaExpired" });
    getLaunchScheduler().stopListenLaunchStatus();
    LoadHelper.monitorErrorEvent(ErrorCode.META.QRCODE_EXPIRED.getCode(), ErrorCode.META.QRCODE_EXPIRED.getDesc());
    runOnUIThreadQuickly(new Runnable() {
          public void run() {
            LoadHelper.handleMiniProcessFail(ErrorCode.META.QRCODE_EXPIRED.getCode());
          }
        });
  }
  
  public void miniAppDownloadInstallFail(String paramString1, String paramString2) {
    getLaunchScheduler().stopListenLaunchStatus();
    updateProgressTv(8, 0);
    long l = TimeMeter.stop(this.mLoadStartTime);
    StringBuilder stringBuilder = new StringBuilder("miniAppDownloadInstallFail ");
    stringBuilder.append(paramString2);
    InnerEventHelper.mpLoadResult(l, "download_fail", stringBuilder.toString(), this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
    if (TextUtils.isEmpty(this.mApp.getSchema()) || !HostDependManager.getInst().handleAppbrandDisablePage((Context)this.mActivity, this.mApp.getSchema()))
      LoadHelper.handleMiniProcessFail(paramString1); 
  }
  
  public void mismatchHost() {
    AppBrandLogger.d("BaseActivityProxy", new Object[] { "mismatchHost" });
    InnerEventHelper.mpLoadResult(TimeMeter.stop(this.mLoadStartTime), "mismatch_host", "mismatch_host", this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logError(new String[] { "BaseActivityProxy_NotSupport" });
    getLaunchScheduler().stopListenLaunchStatus();
    LoadHelper.monitorErrorEvent(ErrorCode.META.HOST_MISMATCH.getCode(), ErrorCode.META.HOST_MISMATCH.getDesc());
    HostDependManager.getInst().showUnSupportView((Activity)this.mActivity, this.mApp.getSchema(), new b.g() {
          public void proceed() {
            if (BaseActivityProxy.this.mActivity != null) {
              EventParamsValue.PARAMS_EXIT_TYPE = "others";
              EventParamsValue.IS_OTHER_FLAG = true;
              ToolUtils.onActivityExit((Activity)BaseActivityProxy.this.mActivity, 6);
            } 
          }
        });
  }
  
  public void noPermission() {
    AppBrandLogger.d("BaseActivityProxy", new Object[] { "noPermission " });
    InnerEventHelper.mpLoadResult(TimeMeter.stop(this.mLoadStartTime), "no_permission", "no_permission", this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
    if (!this.mApp.getAppInfo().isGame())
      SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 0, SearchEventHelper.failReasonClient("no_permission")); 
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logError(new String[] { "BaseActivityProxy_NoPermission" });
    getLaunchScheduler().stopListenLaunchStatus();
    LoadHelper.monitorErrorEvent(ErrorCode.META.PERMISSION_DENY.getCode(), ErrorCode.META.PERMISSION_DENY.getDesc());
    runOnUIThreadQuickly(new Runnable() {
          public void run() {
            LoadHelper.handleMiniProcessFail(ErrorCode.META.PERMISSION_DENY.getCode());
          }
        });
  }
  
  public void offline() {
    AppBrandLogger.d("BaseActivityProxy", new Object[] { "offline" });
    InnerEventHelper.mpLoadResult(TimeMeter.stop(this.mLoadStartTime), "mp_offline", "mp_offline", this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
    if (!this.mApp.getAppInfo().isGame())
      SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 0, SearchEventHelper.failReasonClient("mp_offline")); 
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logError(new String[] { "BaseActivityProxy_offline" });
    getLaunchScheduler().stopListenLaunchStatus();
    LoadHelper.monitorErrorEvent(ErrorCode.META.OFFLINE.getCode(), ErrorCode.META.OFFLINE.getDesc());
    if (this.mActivity != null) {
      EventParamsValue.PARAMS_EXIT_TYPE = "others";
      EventParamsValue.IS_OTHER_FLAG = true;
      String str = this.mApp.getSchema();
      if (this.mActivity == null || TextUtils.isEmpty(str) || !HostDependManager.getInst().handleAppbrandDisablePage((Context)this.mActivity, str)) {
        HostDependManager hostDependManager = HostDependManager.getInst();
        FragmentActivity fragmentActivity = this.mActivity;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppbrandConstant.OpenApi.getInst().getOFFLINE_URL());
        stringBuilder.append("?");
        stringBuilder.append(LanguageUtils.appendLanguageQueryParam());
        hostDependManager.jumpToWebView((Context)fragmentActivity, stringBuilder.toString(), this.mActivity.getResources().getString(2097741961), true);
      } 
      ToolUtils.onActivityExit((Activity)this.mActivity, 5);
    } 
  }
  
  public void onAdStateChanged(String paramString1, String paramString2) {
    BaseAdCtrl.notifyStateChanged(paramString1, paramString2);
  }
  
  public abstract void onAddVideoFragment();
  
  public void onCreate(Bundle paramBundle) {
    boolean bool = true;
    AppBrandLogger.i("BaseActivityProxy", new Object[] { "onCreate" });
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "baseActivityProxy onCreate" });
    this.mLoadStartTime = TimeMeter.newAndStart();
    LoadStateManager.getIns().setLoadStartTime(this.mLoadStartTime);
    Intent intent = this.mActivity.getIntent();
    if (paramBundle == null)
      bool = false; 
    this.mIsActivityRecreate = bool;
    String str = intent.getStringExtra("microapp_url");
    Bundle bundle = intent.getBundleExtra("mp_launch_extra");
    initMpValOnCreate(str, bundle);
    MpTimeLineReporter mpTimeLineReporter = this.mApp.<MpTimeLineReporter>getService(MpTimeLineReporter.class);
    mpTimeLineReporter.addPoint("parse_schema_begin");
    AppInfoEntity appInfoEntity2 = (AppInfoEntity)intent.getParcelableExtra("microapp_appinfo");
    AppInfoEntity appInfoEntity1 = appInfoEntity2;
    if (appInfoEntity2 == null)
      appInfoEntity1 = AppInfoManager.generateInitAppInfo(str); 
    if (appInfoEntity1 != null) {
      mpTimeLineReporter.addPoint("parse_schema_end");
      updateLaunchType(appInfoEntity1);
      ((RenderSnapShotManager)this.mApp.<RenderSnapShotManager>getService(RenderSnapShotManager.class)).flushOnUIThread();
      ((LaunchScheduler)this.mApp.<LaunchScheduler>getService(LaunchScheduler.class)).startLaunch(appInfoEntity1, str, null, bundle);
      HostDependManager.getInst().getMiniAppLifeCycleInstance();
      EventHelper.mpInitResult(appInfoEntity1.appId, appInfoEntity1.launchFrom, appInfoEntity1.scene, appInfoEntity1.subScene, appInfoEntity1.isGame(), LoadStateManager.getIns().getTotalDuration(), "success", "success");
      ((SuffixMetaServiceInterface)this.mApp.getMiniAppContext().getService(SuffixMetaServiceInterface.class)).requestSuffixMeta();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("AppInfo is null, intent = ");
    stringBuilder.append(intent);
    throw new NullPointerException(stringBuilder.toString());
  }
  
  public boolean onCreateBannerView(h paramh) {
    return false;
  }
  
  public boolean onCreateVideoAd(h paramh) {
    return false;
  }
  
  public void onDestroy() {
    ((LaunchScheduler)this.mApp.<LaunchScheduler>getService(LaunchScheduler.class)).onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return false;
  }
  
  public void onMemoryWarning(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder("onMemoryWarning:");
    stringBuilder.append(paramInt);
    AppBrandLogger.d("BaseActivityProxy", new Object[] { stringBuilder.toString() });
    try {
      boolean bool;
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("level", String.valueOf(paramInt));
      this.mApp.getJsBridge().sendMsgToJsCore("onMemoryWarning", jSONObject.toString());
      Debug.MemoryInfo[] arrayOfMemoryInfo = ((ActivityManager)this.mActivity.getSystemService("activity")).getProcessMemoryInfo(new int[] { Process.myPid() });
      if (arrayOfMemoryInfo != null && arrayOfMemoryInfo.length > 0) {
        bool = arrayOfMemoryInfo[0].getTotalPss();
      } else {
        bool = false;
      } 
      Event.builder("mp_memorywarning_report").kv("usedMemory", Integer.valueOf(bool)).kv("type", Integer.valueOf(1)).kv("level", Integer.valueOf(paramInt)).flush();
      return;
    } catch (Exception exception) {
      StringBuilder stringBuilder1 = new StringBuilder("onMemoryWarning error:");
      stringBuilder1.append(paramInt);
      AppBrandLogger.e("BaseActivityProxy", new Object[] { stringBuilder1.toString() });
      return;
    } 
  }
  
  public void onNewIntent(Intent paramIntent) {
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "BaseActivityProxy_onNewIntent" });
    this.mApp.getForeBackgroundManager().pauseBackgroundOverLimitTimeStrategy();
    overrideActivityInAnimation(ActivityUtil.isMoveActivityToFrontSilentIntent(paramIntent), true);
    if (paramIntent == null || !paramIntent.hasExtra("microapp_url")) {
      AppBrandLogger.e("BaseActivityProxy", new Object[] { "onNewIntent fail, intent == ", paramIntent });
      return;
    } 
    updateAppInfoOnNewIntent(paramIntent);
    SynHistoryManager.getInstance().addToRecentApps(this.mApp.getAppInfo());
    if (!AdSiteManager.getInstance().isAdSiteBrowser())
      initAnchor(); 
    RouteEventCtrl routeEventCtrl = this.mApp.getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.updateLaunchOption(this.mApp.getAppInfo()); 
  }
  
  public boolean onOperateBannerView(h paramh) {
    return false;
  }
  
  public String onOperateInterstitialAd(h paramh) {
    return null;
  }
  
  public boolean onOperateVideoAd(h paramh) {
    return false;
  }
  
  public void onPause() {
    AppBrandLogger.i("BaseActivityProxy", new Object[] { "onPause" });
    dismissFavoriteGuide();
  }
  
  public void onPostCreate(Bundle paramBundle) {}
  
  public abstract void onRemoveVideoFragment();
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    AppBrandLogger.d("BaseActivityProxy", new Object[] { "requestCode ", Integer.valueOf(paramInt) });
  }
  
  public void onResume() {
    AppBrandLogger.i("BaseActivityProxy", new Object[] { "onResume" });
    ForeBackgroundManager foreBackgroundManager = this.mApp.getForeBackgroundManager();
    if (!foreBackgroundManager.isBackground()) {
      foreBackgroundManager.resumeBackgroundOverLimitTimeStrategy();
      AppBrandLogger.i("BaseActivityProxy", new Object[] { "resumeWhenForeground" });
      j j = AppbrandApplication.getInst().getJsBridge();
      if (j != null)
        j.sendMsgToJsCore("onBackFromFloatPage", (new JSONObject()).toString()); 
      return;
    } 
    this.mApp.onShow();
    final AppInfoEntity appInfo = this.mApp.getAppInfo();
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            InnerHostProcessBridge.updateJumpList(appInfo.appId, appInfo.isGame(), appInfo.isSpecial());
          }
        }Schedulers.shortIO());
    this.mOnActivityStartTime.start();
    this.mLoadingViewShowTimes.begin();
    ParamManager.newSession();
    Event.builder("mp_enter").kv("launch_type", appInfoEntity.launchType).flush();
    if (!this.isShowingLoadingView) {
      startModule();
      HostProcessBridge.onMiniAppStart(appInfoEntity, appInfoEntity.isGame(), getScreenOrientationFromAppConfig());
      this.mMicroAppStartShowTimes = System.currentTimeMillis();
    } 
    if (this.mIsCreated) {
      AppbrandConstants.getBundleManager().checkUpdateBaseBundle((Context)AppbrandContext.getInst().getApplicationContext());
    } else {
      this.mIsCreated = true;
    } 
    startModule();
  }
  
  public void onStartActivityForResult(Intent paramIntent, int paramInt) {
    AppBrandLogger.i("BaseActivityProxy", new Object[] { "onStartActivityForResult" });
    this.mApp.getForeBackgroundManager().pauseBackgroundOverLimitTimeStrategy();
  }
  
  public void onStop() {
    AppBrandLogger.i("BaseActivityProxy", new Object[] { "onStop" });
    if (this.mApp.getForeBackgroundManager().isBackground()) {
      AppBrandLogger.i("BaseActivityProxy", new Object[] { "stopWhenBackground" });
      return;
    } 
    this.mApp.onHide();
    Map map = ModeManager.getInst().getModules();
    if (map != null) {
      Iterator<NativeModule> iterator = map.values().iterator();
      while (iterator.hasNext())
        ((NativeModule)iterator.next()).onStop(); 
    } 
    if (EventParamsValue.IS_OTHER_FLAG && !TextUtils.equals(EventParamsValue.PARAMS_EXIT_TYPE, "others"))
      EventParamsValue.PARAMS_EXIT_TYPE = "others"; 
    Event.builder("mp_exit").kv("page_path", this.mApp.getCurrentPageUrl()).kv("exit_type", EventParamsValue.PARAMS_EXIT_TYPE).kv("launch_type", (this.mApp.getAppInfo()).launchType).kv("duration", Long.valueOf(TimeMeter.stop(this.mOnActivityStartTime))).kv("load_state", LoadStateManager.getIns().getLoadState()).kv("progress", Integer.valueOf(getLaunchScheduler().getLaunchProgress())).addKVJsonObject(OriginHelper.getOriginJson()).flush();
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (!appInfoEntity.isGame())
      SearchEventHelper.stayPageEvent(false); 
    this.mHasActivityStoped = true;
    EventParamsValue.IS_OTHER_FLAG = true;
    HostProcessBridge.onMiniAppStop(appInfoEntity, appInfoEntity.isGame(), getScreenOrientationFromAppConfig(), this.mApp.getStopReason());
    recordMiniAppUsage(appInfoEntity, this.mMicroAppStartShowTimes);
    this.mApp.setStopReason("");
    this.mLoadingViewShowTimes.pause();
    AppbrandConstants.getBundleManager().checkUpdateBaseBundle((Context)AppbrandContext.getInst().getApplicationContext());
    AppBrandMonitor.flush();
    AppBrandLogger.flush();
  }
  
  public boolean onUpdateBannerView(h paramh) {
    return false;
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {}
  
  public void overrideActivityExitAnimation() {
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle()) {
      HostDependManager.getInst().overridePendingTransition((Activity)this.mActivity, 2131034235, 2131034232, 4);
      return;
    } 
    HostDependManager.getInst().overridePendingTransition((Activity)this.mActivity, 2131034235, 2131034232, 2);
  }
  
  public void registerBackPressedListener(h paramh) {
    if (paramh == null)
      return; 
    if (getMatchBackPressedListenerWr(paramh) == null)
      this.mBackPressedListenerWrList.add(new WeakReference<h>(paramh)); 
  }
  
  public void requestAppInfoFail(final String code, String paramString2) {
    AppBrandLogger.e("BaseActivityProxy", new Object[] { "requestAppInfoFail ", paramString2, new Throwable() });
    long l = TimeMeter.stop(this.mLoadStartTime);
    StringBuilder stringBuilder = new StringBuilder("requestAppInfoFail ");
    stringBuilder.append(paramString2);
    InnerEventHelper.mpLoadResult(l, "meta_request_fail", stringBuilder.toString(), this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
    if (!this.mApp.getAppInfo().isGame())
      SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 0, SearchEventHelper.failReasonClient("meta_request_fail")); 
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logError(new String[] { "BaseActivityProxy_requestAppInfoFail", paramString2 });
    getLaunchScheduler().stopListenLaunchStatus();
    paramString2 = this.mApp.getSchema();
    if (this.mActivity == null || TextUtils.isEmpty(paramString2) || !HostDependManager.getInst().handleAppbrandDisablePage((Context)this.mActivity, paramString2)) {
      runOnUIThreadQuickly(new Runnable() {
            public void run() {
              LoadHelper.handleMiniProcessFail(code);
            }
          });
      return;
    } 
    FragmentActivity fragmentActivity = this.mActivity;
    if (fragmentActivity != null)
      ToolUtils.onActivityExit((Activity)fragmentActivity, 3); 
  }
  
  public void requestAppInfoSuccess(AppInfoEntity paramAppInfoEntity) {
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logTimeDuration(new String[] { "BaseActivityProxy_requestAppInfoSuccess" });
    this.mLaunchLoadingView.updateViews(paramAppInfoEntity);
    setActivityTaskDescription();
  }
  
  public boolean sendShareInfoToMainProcess(h paramh, AnchorConfig paramAnchorConfig) {
    JSONObject jSONObject;
    if (paramAnchorConfig == null)
      return false; 
    if (paramh != null) {
      paramh.anchorExtra = paramAnchorConfig.getAnchorExtra();
      paramh.snapshotUrl = paramAnchorConfig.getSnapshotUrl();
      jSONObject = paramh.toJson();
    } else {
      jSONObject = new JSONObject();
      try {
        jSONObject.put("anchorExtra", paramAnchorConfig.getAnchorExtra());
      } catch (JSONException jSONException) {}
    } 
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("anchorAction", CrossProcessDataEntity.Builder.create().put("anchorAction", paramAnchorConfig.getAction()).put("anchorShareInfo", jSONObject.toString()).build());
    if (paramAnchorConfig.getAction().equals("ADD") || paramAnchorConfig.getAction().equals("REPLACE") || paramAnchorConfig.getAction().equals("REMOVE")) {
      if (crossProcessDataEntity != null && crossProcessDataEntity.getBoolean("anchorActionResult")) {
        shareResultEvent("success", paramAnchorConfig.getChannel());
        EventParamsValue.PARAMS_EXIT_TYPE = "anchor_add";
        EventParamsValue.IS_OTHER_FLAG = false;
        ToolUtils.onActivityExit((Activity)this.mActivity, 9);
        return true;
      } 
      shareResultEvent("fail", paramAnchorConfig.getChannel());
      HostDependManager.getInst().showToast((Context)this.mActivity, null, UIUtils.getString(2097741862), 0L, null);
      return false;
    } 
    return false;
  }
  
  protected void setActivityTaskDescription() {
    final AppInfoEntity appInfo = this.mApp.getAppInfo();
    if (appInfoEntity == null)
      return; 
    FragmentActivity fragmentActivity = this.mActivity;
    if (fragmentActivity instanceof MiniappHostBase && ((MiniappHostBase)fragmentActivity).isInHostStack())
      return; 
    Observable.create(new Function<Bitmap>() {
          public Bitmap fun() {
            if (!TextUtils.isEmpty(appInfo.icon))
              try {
                byte[] arrayOfByte = NetUtil.readImage(appInfo.icon);
                if (arrayOfByte != null && arrayOfByte.length > 0)
                  return BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length); 
              } catch (Exception exception) {
                AppBrandLogger.stacktrace(6, "BaseActivityProxy", exception.getStackTrace());
              }  
            return null;
          }
        }).schudleOn(Schedulers.longIO()).observeOn(Schedulers.ui()).subscribe((Subscriber)new Subscriber.ResultableSubscriber<Bitmap>() {
          public void onError(Throwable param1Throwable) {
            ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(appInfo.appName);
            BaseActivityProxy.this.mActivity.setTaskDescription(taskDescription);
          }
          
          public void onSuccess(Bitmap param1Bitmap) {
            if (param1Bitmap != null) {
              ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(appInfo.appName, param1Bitmap);
              BaseActivityProxy.this.mActivity.setTaskDescription(taskDescription);
              return;
            } 
            onError(new NullPointerException("bitmap == null"));
          }
        });
  }
  
  public void shareResultEvent(String paramString1, String paramString2) {
    InnerEventHelper.mpShareResult(paramString2, "inside", paramString1, null, false);
  }
  
  public d showFavoriteGuide(FavoriteGuideModel paramFavoriteGuideModel) {
    FavoriteGuideWidget favoriteGuideWidget = this.mApp.<FavoriteGuideWidget>getService(FavoriteGuideWidget.class);
    favoriteGuideWidget.registerCallback(new FavoriteGuideWidget.Callback() {
          public Activity getActivity() {
            return (Activity)BaseActivityProxy.this.mActivity;
          }
          
          public boolean isGame() {
            return BaseActivityProxy.this.mApp.getAppInfo().isGame();
          }
        });
    return favoriteGuideWidget.show(paramFavoriteGuideModel);
  }
  
  public boolean showLoadFailMessage(String paramString, boolean paramBoolean) {
    FeignHostConfig.LaunchLoadingListener launchLoadingListener;
    RenderSnapShotManager renderSnapShotManager = this.mApp.<RenderSnapShotManager>getService(RenderSnapShotManager.class);
    if (this.mLaunchLoadingView != null && this.isShowingLoadingView) {
      if (renderSnapShotManager.isSnapShotRender() && !TextUtils.isEmpty(renderSnapShotManager.getSnapShotErrorArgs())) {
        this.mLaunchLoadingView.showFailMessage(paramString, paramBoolean, true);
      } else {
        this.mLaunchLoadingView.showFailMessage(paramString, paramBoolean, false);
      } 
      if (this.mApp.getAppInfo().isGame()) {
        launchLoadingListener = FeignHostConfig.inst().getGameLaunchLoadingListener();
      } else {
        launchLoadingListener = FeignHostConfig.inst().getAppLaunchLoadingListener();
      } 
      if (launchLoadingListener != null)
        launchLoadingListener.onLoadingFail(paramString); 
      return true;
    } 
    if (launchLoadingListener.isSnapShotRender()) {
      launchLoadingListener.onLoadResultFail(paramString);
      return true;
    } 
    return false;
  }
  
  public void showNotSupportView() {
    AppBrandLogger.d("BaseActivityProxy", new Object[] { "showNotSupportView" });
    InnerEventHelper.mpLoadResult(TimeMeter.stop(this.mLoadStartTime), "old_js_sdk", "old_js_sdk", this.mLaunchProfileTime.getTime(), TimeMeter.stop(this.mEntranceClickTimeMeter), LoadStateManager.getIns().getLoadState());
    if (!this.mApp.getAppInfo().isGame())
      SearchEventHelper.loadDetailEvent(false, false, TimeMeter.stop(this.mLoadStartTime), 0, SearchEventHelper.failReasonClient("old_js_sdk")); 
    ((TimeLogger)this.mApp.<TimeLogger>getService(TimeLogger.class)).logError(new String[] { "BaseActivityProxy_NotSupport" });
    getLaunchScheduler().stopListenLaunchStatus();
    LoadHelper.monitorErrorEvent(ErrorCode.META.JSON_ERROR.getCode(), ErrorCode.META.JSON_ERROR.getDesc());
    HostDependManager.getInst().showUnSupportView((Activity)this.mActivity, this.mApp.getSchema(), new b.g() {
          public void proceed() {
            if (BaseActivityProxy.this.mActivity != null) {
              EventParamsValue.PARAMS_EXIT_TYPE = "others";
              EventParamsValue.IS_OTHER_FLAG = true;
              ToolUtils.onActivityExit((Activity)BaseActivityProxy.this.mActivity, 6);
            } 
          }
        });
  }
  
  protected void startModule() {
    Map map = ModeManager.getInst().getModules();
    if (map != null) {
      Iterator<NativeModule> iterator = map.values().iterator();
      while (iterator.hasNext())
        ((NativeModule)iterator.next()).onStart(); 
    } 
  }
  
  public void unRegisterBackPressedLinstener(h paramh) {
    if (paramh == null)
      return; 
    WeakReference<h> weakReference = getMatchBackPressedListenerWr(paramh);
    if (weakReference != null)
      this.mBackPressedListenerWrList.remove(weakReference); 
  }
  
  protected final void updateAppInfoOnNewIntent(Intent paramIntent) {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity != null) {
      if (paramIntent == null)
        return; 
      String str = paramIntent.getStringExtra("microapp_url");
      if (this.mApp != null && !TextUtils.isEmpty(str))
        this.mApp.setSchema(str); 
      AppInfoManager.readAppInfoFromSchemaCommon(str, appInfoEntity);
      appInfoEntity.isNotRecordRecentUseApps = ((AppInfoEntity)paramIntent.getParcelableExtra("microapp_appinfo")).isNotRecordRecentUseApps;
      this.mApp.setAppInfo(appInfoEntity);
      InnerEventHelper.mpSchemaAssess(str);
    } 
  }
  
  protected void updateProgressTv(int paramInt1, int paramInt2) {
    if (this.isShowingLoadingView) {
      if (paramInt1 == 0)
        notifyLoadProgress(paramInt2); 
      this.mLaunchLoadingView.updateProgressTv(paramInt1, paramInt2);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\BaseActivityProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */