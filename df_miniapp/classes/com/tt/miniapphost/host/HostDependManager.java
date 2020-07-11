package com.tt.miniapphost.host;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import com.bytedance.sandboxapp.protocol.service.c.c;
import com.ss.android.ugc.aweme.miniapp.e;
import com.ss.android.ugc.aweme.miniapp.g.b;
import com.ss.android.ugc.aweme.miniapp.g.j;
import com.ss.android.ugc.aweme.miniapp.g.r;
import com.tt.b.b;
import com.tt.b.c;
import com.tt.c.a;
import com.tt.frontendapiinterface.i;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.base.activity.ActivityServiceInterface;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.component.nativeview.NativeComponent;
import com.tt.miniapp.liveplayer.ITTLivePlayer;
import com.tt.miniapp.liveplayer.dns.ILivePlayerDns;
import com.tt.miniapp.maplocate.ILocator;
import com.tt.miniapp.msg.ApiChooseAddressCtrl;
import com.tt.miniapp.msg.ApiRequestPayCtrl;
import com.tt.miniapp.msg.ApiWXRequestPayCtrl;
import com.tt.miniapp.msg.download.DxppCallback;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.settings.data.ABTestDAO;
import com.tt.miniapp.settings.net.RequestService;
import com.tt.miniapp.titlemenu.item.IMenuItem;
import com.tt.miniapp.view.webcore.AbsoluteLayout;
import com.tt.miniapp.view.webcore.NativeNestWebView;
import com.tt.miniapp.view.webcore.NestWebView;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.EventHelper;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.apm.IApmService;
import com.tt.miniapphost.entity.AnchorConfig;
import com.tt.miniapphost.entity.AppLaunchInfo;
import com.tt.miniapphost.entity.ChooseLocationResultEntity;
import com.tt.miniapphost.entity.DisableStateEntity;
import com.tt.miniapphost.entity.GamePayResultEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.entity.ScanResultEntity;
import com.tt.miniapphost.monitor.AppBrandEnsure;
import com.tt.miniapphost.permission.IPermissionsRequestCallback;
import com.tt.miniapphost.permission.IPermissionsResultAction;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.aa.c;
import com.tt.option.ad.a.a;
import com.tt.option.ad.c;
import com.tt.option.ad.e;
import com.tt.option.ad.g;
import com.tt.option.ad.i;
import com.tt.option.ad.j;
import com.tt.option.b;
import com.tt.option.b.a;
import com.tt.option.b.b;
import com.tt.option.c;
import com.tt.option.c.a;
import com.tt.option.c.b;
import com.tt.option.d;
import com.tt.option.d.a;
import com.tt.option.e.f;
import com.tt.option.e.i;
import com.tt.option.f.b;
import com.tt.option.g.b;
import com.tt.option.h.b;
import com.tt.option.m.a;
import com.tt.option.n.b;
import com.tt.option.n.c;
import com.tt.option.p.e;
import com.tt.option.q.c;
import com.tt.option.q.f;
import com.tt.option.q.g;
import com.tt.option.q.i;
import com.tt.option.q.j;
import com.tt.option.q.k;
import com.tt.option.t.c;
import com.tt.option.w.b;
import com.tt.option.w.d;
import com.tt.option.w.e;
import com.tt.option.w.f;
import com.tt.option.w.g;
import com.tt.option.w.h;
import com.tt.option.y.b;
import com.tt.option.z.b;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HostDependManager extends d implements b, b {
  private c hostOptionDependRegister = new c();
  
  public static HostDependManager getInst() {
    return Holder.sInstance;
  }
  
  public boolean bindPhoneNumber(d.a parama) {
    return (c.i() != null) ? c.i().bindPhoneNumber(parama) : false;
  }
  
  public DisableStateEntity checkExtraAppbrandDisableState(int paramInt) {
    return (c.i() != null) ? c.i().checkExtraAppbrandDisableState(paramInt) : null;
  }
  
  public void checkFollowAwemeState(String paramString1, String paramString2, c paramc) {
    a a = this.hostOptionDependRegister.s;
    if (a != null) {
      a.checkFollowAwemeState(paramString1, paramString2, paramc);
      return;
    } 
    paramc.onFailure(-2, "feature is not supported in app");
  }
  
  public void checkSession(String paramString, b.a parama) {
    this.hostOptionDependRegister.h.checkSession(paramString, parama);
  }
  
  public void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, b.b paramb) {
    c.c().chooseImage(paramActivity, paramInt, paramBoolean1, paramBoolean2, paramb, new b.a() {
          public void setActivityResultHandler(IActivityResultHandler param1IActivityResultHandler) {
            ((ActivityServiceInterface)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(ActivityServiceInterface.class)).registerActivityResultHandler(param1IActivityResultHandler);
          }
        });
  }
  
  public void chooseImage(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, b.b paramb, b.a parama) {
    c.c().chooseImage(paramActivity, paramInt, paramBoolean1, paramBoolean2, paramb, parama);
  }
  
  public boolean chooseLocationActivity(Activity paramActivity, int paramInt) {
    return c.h().chooseLocationActivity(paramActivity, paramInt);
  }
  
  public void chooseVideo(Activity paramActivity, int paramInt, boolean paramBoolean1, boolean paramBoolean2, b.c paramc) {
    c.c().chooseVideo(paramActivity, paramInt, paramBoolean1, paramBoolean2, paramc);
  }
  
  public i convertMetaRequest(i parami) {
    return c.e().convertMetaRequest(parami);
  }
  
  public a createAdSiteDxppManager() {
    i i = this.hostOptionDependRegister.q;
    return (i != null) ? i.createAdSiteDxppManager() : null;
  }
  
  public e createAdViewManager(e.a parama) {
    i i = this.hostOptionDependRegister.q;
    return (i != null) ? i.createAdViewManager(parama) : null;
  }
  
  public IApmService createApmService() {
    return this.hostOptionDependRegister.l.createApmService();
  }
  
  public List<IAsyncHostDataHandler> createAsyncHostDataHandlerList() {
    return c.a().createAsyncHostDataHandlerList();
  }
  
  public d.b createBlockLoadingCallback() {
    return (c.i() != null) ? c.i().createBlockLoadingCallback() : super.createBlockLoadingCallback();
  }
  
  public b createBottomBar(String paramString1, String paramString2, b.a parama) {
    a a1 = this.hostOptionDependRegister.o;
    return (a1 != null) ? a1.createBottomBar(paramString1, paramString2, parama) : null;
  }
  
  public c createChooseFileHandler(Activity paramActivity) {
    return c.c().createChooseFileHandler(paramActivity);
  }
  
  public void createDxppTask(Context paramContext, String paramString1, boolean paramBoolean, String paramString2, String paramString3, String paramString4, JSONArray paramJSONArray, String paramString5, JSONObject paramJSONObject, long paramLong, DxppCallback paramDxppCallback) {
    a a = this.hostOptionDependRegister.r;
    if (a != null)
      a.createDxppTask(paramContext, paramString1, paramBoolean, paramString2, paramString3, paramString4, paramJSONArray, paramString5, paramJSONObject, paramLong, paramDxppCallback); 
  }
  
  public f.a createExtHandler() {
    return this.hostOptionDependRegister.a.createExtHandler();
  }
  
  public g createGameAdManager(g.a parama) {
    i i = this.hostOptionDependRegister.q;
    return (i != null) ? i.createGameAdManager(parama) : null;
  }
  
  public InitParamsEntity createInitParams() {
    return getHostEssentialDepend().createInitParams();
  }
  
  public ITTLivePlayer createLivePlayer(Context paramContext) {
    return (c.j() != null) ? c.j().createLivePlayer(paramContext) : null;
  }
  
  public ILocator createLocateInstance(Context paramContext) {
    return (this.hostOptionDependRegister.p != null) ? this.hostOptionDependRegister.p.createLocateInstance(paramContext) : null;
  }
  
  public AppBrandLogger.ILogger createLogger() {
    return c.b().createLogger();
  }
  
  public a createMapInstance() {
    return (this.hostOptionDependRegister.p != null) ? this.hostOptionDependRegister.p.createMapInstance() : null;
  }
  
  public List<NativeModule> createNativeModules(AppbrandContext paramAppbrandContext) {
    return r.a().createNativeModules(paramAppbrandContext);
  }
  
  public i.a createNativeView() {
    return this.hostOptionDependRegister.b.createNativeView();
  }
  
  public e createSDKMonitorInstance(Context paramContext, String paramString, JSONObject paramJSONObject) {
    return (c.k() != null) ? c.k().createSDKMonitorInstance(paramContext, paramString, paramJSONObject) : null;
  }
  
  public RequestService createSettingsResponseService() {
    return c.e().createSettingsResponseService();
  }
  
  public List<ISyncHostDataHandler> createSyncHostDataHandlerList() {
    return c.a().createSyncHostDataHandlerList();
  }
  
  public List<Object> createTitleMenuItems() {
    return c.d().createTitleMenuItems();
  }
  
  public j createVideoPatchAdManager(j.a parama) {
    i i = this.hostOptionDependRegister.q;
    return (i != null) ? i.createVideoPatchAdManager(parama) : null;
  }
  
  public k createWsClient(k.a parama) {
    return c.e().createWsClient(parama);
  }
  
  public void dismissLiveWindowView(Activity paramActivity, String paramString, boolean paramBoolean) {
    c.g().dismissLiveWindowView(paramActivity, paramString, paramBoolean);
  }
  
  public boolean doAppBundleSplitInstallAction(Context paramContext) {
    return c.l().doAppBundleSplitInstallAction(paramContext);
  }
  
  public j doGet(i parami) {
    j j1;
    long l = TimeMeter.currentMillis();
    try {
      j1 = c.e().doGet(parami);
    } catch (Exception exception) {
      j1 = new j();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getClass());
      stringBuilder.append(":");
      stringBuilder.append(exception.getMessage());
      j1.c = stringBuilder.toString();
      j1.f = exception;
      if (DebugUtil.debug()) {
        Application application = AppbrandContext.getInst().getApplicationContext();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(UIUtils.getString(2097741960));
        stringBuilder1.append(exception.getMessage());
        showToast((Context)application, null, stringBuilder1.toString(), 0L, null);
      } 
    } 
    j j2 = j1;
    if (j1 == null) {
      j2 = new j();
      j2.c = "unknown net error";
    } 
    EventHelper.mpSdkRequestResult(parami, j2, TimeMeter.currentMillis() - l);
    return j2;
  }
  
  public j doPostBody(i parami) {
    j j1;
    long l = TimeMeter.currentMillis();
    try {
      j1 = c.e().doPostBody(parami);
    } catch (Exception exception) {
      j1 = new j();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getClass());
      stringBuilder.append(":");
      stringBuilder.append(exception.getMessage());
      j1.c = stringBuilder.toString();
      j1.f = exception;
      if (DebugUtil.debug()) {
        Application application = AppbrandContext.getInst().getApplicationContext();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(UIUtils.getString(2097741960));
        stringBuilder1.append(exception.getMessage());
        showToast((Context)application, null, stringBuilder1.toString(), 0L, null);
      } 
    } 
    j j2 = j1;
    if (j1 == null) {
      j2 = new j();
      j2.c = "unknown net error";
    } 
    EventHelper.mpSdkRequestResult(parami, j2, TimeMeter.currentMillis() - l);
    return j2;
  }
  
  public j doPostUrlEncoded(i parami) throws Exception {
    j j1;
    long l = TimeMeter.currentMillis();
    try {
      j1 = c.e().doPostUrlEncoded(parami);
    } catch (Exception exception) {
      j1 = new j();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getClass());
      stringBuilder.append(":");
      stringBuilder.append(exception.getMessage());
      j1.c = stringBuilder.toString();
      j1.f = exception;
      if (DebugUtil.debug()) {
        Application application = AppbrandContext.getInst().getApplicationContext();
        StringBuilder stringBuilder1 = new StringBuilder("网络请求失败 ");
        stringBuilder1.append(exception.getMessage());
        showToast((Context)application, null, stringBuilder1.toString(), 0L, null);
      } 
    } 
    j j2 = j1;
    if (j1 == null) {
      j2 = new j();
      j2.c = "unknown net error";
    } 
    EventHelper.mpSdkRequestResult(parami, j2, TimeMeter.currentMillis() - l);
    return j2;
  }
  
  public j doRequest(i parami) {
    j j1;
    try {
      j1 = c.e().doRequest(parami);
    } finally {
      Exception exception = null;
      j1 = new j();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getClass());
      stringBuilder.append(":");
      stringBuilder.append(exception.getMessage());
      j1.c = stringBuilder.toString();
      j1.f = exception;
    } 
    j j2 = j1;
    if (j1 == null) {
      j2 = new j();
      j2.c = "unknown net error";
    } 
    return j2;
  }
  
  public g downloadFile(f paramf, c.a parama) {
    g g1;
    long l = TimeMeter.currentMillis();
    try {
      g1 = c.e().downloadFile(paramf, parama);
    } catch (Exception exception) {
      g1 = new g();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getClass());
      stringBuilder.append(":");
      stringBuilder.append(exception.getMessage());
      ((j)g1).c = stringBuilder.toString();
      ((j)g1).f = exception;
      if (DebugUtil.debug()) {
        Application application = AppbrandContext.getInst().getApplicationContext();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(AppbrandContext.getInst().getApplicationContext().getResources().getString(2097741960));
        stringBuilder1.append(exception.getMessage());
        showToast((Context)application, null, stringBuilder1.toString(), 0L, null);
      } 
    } 
    g g2 = g1;
    if (g1 == null) {
      g2 = new g();
      ((j)g2).c = "unknown net error";
    } 
    EventHelper.mpSdkRequestResult((i)paramf, (j)g2, TimeMeter.currentMillis() - l);
    return g2;
  }
  
  public boolean enableTTRender(View paramView) {
    return (c.j() != null) ? c.j().enableTTRender(paramView) : true;
  }
  
  public boolean enableTTRender(String paramString) {
    return (c.j() != null) ? c.j().enableTTRender(paramString) : true;
  }
  
  public boolean feedbackIntercept(Activity paramActivity, b paramb) {
    return (this.hostOptionDependRegister.j != null) ? this.hostOptionDependRegister.j.feedbackIntercept(paramActivity, paramb) : false;
  }
  
  public Set<BrandPermissionUtils.BrandPermission> filterNeedRequestPermission(String paramString, Set<BrandPermissionUtils.BrandPermission> paramSet) {
    return this.hostOptionDependRegister.f.filterNeedRequestPermission(paramString, paramSet);
  }
  
  public void firstFavoriteAction() {
    if (this.hostOptionDependRegister.i != null)
      this.hostOptionDependRegister.i.firstFavoriteAction(); 
  }
  
  public boolean gamePay(Activity paramActivity, JSONObject paramJSONObject, String paramString) {
    return (c.i() != null) ? c.i().gamePay(paramActivity, paramJSONObject, paramString) : false;
  }
  
  public Bundle getAdConfig() {
    i i = this.hostOptionDependRegister.q;
    return (i != null) ? i.getAdConfig() : new Bundle();
  }
  
  public AnchorConfig getAnchorConfig(String paramString) {
    return c.g().getAnchorConfig(paramString);
  }
  
  public List<AppLaunchInfo> getAppLaunchInfo() {
    return (c.i() != null) ? c.i().getAppLaunchInfo() : null;
  }
  
  public JSONObject getDxppTaskStatus(String paramString1, String paramString2, String paramString3, boolean paramBoolean) {
    a a = this.hostOptionDependRegister.r;
    return (a != null) ? a.getDxppTaskStatus(paramString1, paramString2, paramString3, paramBoolean) : null;
  }
  
  public AppBrandEnsure.IEnsure getEnsure() {
    return this.hostOptionDependRegister.c.getEnsure();
  }
  
  public b getHostCustomFavoriteEntity(Context paramContext) {
    return (this.hostOptionDependRegister.i != null) ? this.hostOptionDependRegister.i.getHostCustomFavoriteEntity(paramContext) : (new b.a(paramContext)).a();
  }
  
  public <T> T getHostData(int paramInt, T paramT) {
    return (T)((c.i() != null) ? c.i().getHostData(paramInt, paramT) : super.getHostData(paramInt, paramT));
  }
  
  public b getHostEssentialDepend() {
    return (b)e.a();
  }
  
  public Locale getInitLocale() {
    return c.l().getInitLocale();
  }
  
  public ILivePlayerDns getLivePlayerDnsOptimizer(Context paramContext) {
    return (c.j() != null) ? c.j().getLivePlayerDnsOptimizer(paramContext) : null;
  }
  
  public Dialog getLoadingDialog(Activity paramActivity, String paramString) {
    return c.g().getLoadingDialog(paramActivity, paramString);
  }
  
  public void getLocalScope(JSONObject paramJSONObject) throws JSONException {
    this.hostOptionDependRegister.f.getLocalScope(paramJSONObject);
  }
  
  public a getMiniAppLifeCycleInstance() {
    return (c.i() != null) ? c.i().getMiniAppLifeCycleInstance() : super.getMiniAppLifeCycleInstance();
  }
  
  public NativeComponent getNativeComponentView(String paramString, int paramInt1, int paramInt2, AbsoluteLayout paramAbsoluteLayout, NativeNestWebView paramNativeNestWebView, i parami) {
    return (c.j() != null) ? c.j().getNativeComponentView(paramString, paramInt1, paramInt2, paramAbsoluteLayout, paramNativeNestWebView, parami) : null;
  }
  
  public NestWebView getNestWebView(Context paramContext) {
    return (c.i() != null) ? c.i().getNestWebView(paramContext) : super.getNestWebView(paramContext);
  }
  
  public boolean getPayEnable() {
    return (c.i() != null) ? c.i().getPayEnable() : super.getPayEnable();
  }
  
  public ApiRequestPayCtrl.IPayExecutor getPayExecutor(Activity paramActivity) {
    return (c.i() != null) ? c.i().getPayExecutor(paramActivity) : null;
  }
  
  public c getPermissionCustomDialogMsgEntity() {
    return this.hostOptionDependRegister.f.getPermissionCustomDialogMsgEntity();
  }
  
  public String getPrefixPath() {
    return this.hostOptionDependRegister.g.getPrefixPath();
  }
  
  public List<AppLaunchInfo> getRecommendList() {
    return (c.i() != null) ? c.i().getRecommendList() : null;
  }
  
  public String getScene(String paramString) {
    return b.a().getScene(paramString);
  }
  
  public long getSettingsRequestDelayTime() {
    return (c.i() != null) ? c.i().getSettingsRequestDelayTime() : 3000L;
  }
  
  public void getShareBaseInfo(String paramString, d paramd) {
    this.hostOptionDependRegister.e.getShareBaseInfo(paramString, paramd);
  }
  
  public void getShareToken(h paramh, e parame) {
    this.hostOptionDependRegister.e.getShareToken(paramh, parame);
  }
  
  public SharedPreferences getSharedPreferences(Context paramContext, String paramString) {
    return j.a().getSharedPreferences(paramContext, paramString);
  }
  
  public String getSpPrefixPath() {
    return this.hostOptionDependRegister.g.getSpPrefixPath();
  }
  
  public JSONObject getTmaFeatureConfig() {
    return (c.i() != null) ? c.i().getTmaFeatureConfig() : null;
  }
  
  public long getUseDuration() {
    return c.h().getUseDuration();
  }
  
  public List<BrandPermissionUtils.BrandPermission> getUserDefinableHostPermissionList() {
    return this.hostOptionDependRegister.f.getUserDefinableHostPermissionList();
  }
  
  public c getVideoEditor(Context paramContext, String paramString) {
    return this.hostOptionDependRegister.d.getVideoEditor(paramContext, paramString);
  }
  
  public ApiWXRequestPayCtrl.IWxPayExecutor getWxPayExecutor(Activity paramActivity) {
    return (c.i() != null) ? c.i().getWxPayExecutor(paramActivity) : null;
  }
  
  public boolean handleActivityChooseAddressResult(int paramInt1, int paramInt2, Intent paramIntent, ApiChooseAddressCtrl.ChooseAddressListener paramChooseAddressListener) {
    return c.f().handleActivityChooseAddressResult(paramInt1, paramInt2, paramIntent, paramChooseAddressListener);
  }
  
  public GamePayResultEntity handleActivityGamePayResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return (c.i() != null) ? c.i().handleActivityGamePayResult(paramInt1, paramInt2, paramIntent) : new GamePayResultEntity();
  }
  
  public boolean handleActivityLoginResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return getHostEssentialDepend().handleActivityLoginResult(paramInt1, paramInt2, paramIntent);
  }
  
  public ScanResultEntity handleActivityScanResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return c.c().handleActivityScanResult(paramInt1, paramInt2, paramIntent);
  }
  
  public boolean handleActivityShareResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return getHostEssentialDepend().handleActivityShareResult(paramInt1, paramInt2, paramIntent);
  }
  
  public boolean handleAppbrandDisablePage(Context paramContext, String paramString) {
    return c.f().handleAppbrandDisablePage(paramContext, paramString);
  }
  
  public ChooseLocationResultEntity handleChooseLocationResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return c.h().handleChooseLocationResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void handleCustomizePermissionResult(JSONObject paramJSONObject, int paramInt, boolean paramBoolean) throws JSONException {
    this.hostOptionDependRegister.f.handleCustomizePermissionResult(paramJSONObject, paramInt, paramBoolean);
  }
  
  public boolean hasAwemeDepend() {
    return (this.hostOptionDependRegister.s != null);
  }
  
  public void hideToast() {
    c.g().hideToast();
  }
  
  public void initAdDepend() {
    i i = this.hostOptionDependRegister.q;
    if (i != null)
      i.initAdDepend(); 
  }
  
  public void initFeignHostConfig(Context paramContext) {
    c.g().initFeignHostConfig(paramContext);
  }
  
  public void initNativeUIParams() {
    c.g().initNativeUIParams();
  }
  
  public boolean interceptOpenWebUrl(Context paramContext, String paramString) {
    return c.f().interceptOpenWebUrl(paramContext, paramString);
  }
  
  public boolean isBlockChanelDefault(String paramString, boolean paramBoolean) {
    return this.hostOptionDependRegister.e.isBlockChanelDefault(paramString, paramBoolean);
  }
  
  public boolean isCheckSafeDomain(boolean paramBoolean, String paramString1, String paramString2) {
    return (c.i() != null) ? c.i().isCheckSafeDomain(paramBoolean, paramString1, paramString2) : super.isCheckSafeDomain(paramBoolean, paramString1, paramString2);
  }
  
  public boolean isEnableAppBundleMode() {
    return c.l().isEnableAppBundleMode();
  }
  
  public boolean isEnableDownlaodFileApiRewrite() {
    return (c.i() != null) ? c.i().isEnableDownlaodFileApiRewrite() : super.isEnableDownlaodFileApiRewrite();
  }
  
  public boolean isEnableI18NRequestRefer() {
    return c.l().isEnableI18NRequestRefer();
  }
  
  public boolean isEnableI18nNetRequest() {
    return c.l().isEnableI18nNetRequest();
  }
  
  public boolean isEnableOpenSchemaAnimation() {
    return (c.i() != null) ? c.i().isEnableOpenSchemaAnimation() : super.isEnableOpenSchemaAnimation();
  }
  
  public boolean isEnablePermissionSaveTest() {
    return (c.i() != null) ? c.i().isEnablePermissionSaveTest() : super.isEnablePermissionSaveTest();
  }
  
  public boolean isEnableWebAppPreload() {
    return (c.i() != null) ? c.i().isEnableWebAppPreload() : super.isEnableWebAppPreload();
  }
  
  public boolean isEnableWebviewPreload() {
    return (c.i() != null) ? c.i().isEnableWebviewPreload() : super.isEnableWebviewPreload();
  }
  
  public boolean isHideTitleMenuAboutItem() {
    return (c.i() != null) ? c.i().isHideTitleMenuAboutItem() : super.isHideTitleMenuAboutItem();
  }
  
  public boolean isHostOptionShareDialogDependEnable() {
    return (this.hostOptionDependRegister.n != null);
  }
  
  public boolean isMediaPlaybackRequiresUserGesture() {
    return (c.i() != null) ? c.i().isMediaPlaybackRequiresUserGesture() : super.isMediaPlaybackRequiresUserGesture();
  }
  
  public boolean isReturnDeviceIdInSystemInfo() {
    return (c.i() != null) ? c.i().isReturnDeviceIdInSystemInfo() : super.isReturnDeviceIdInSystemInfo();
  }
  
  public boolean isSlideActivity(String paramString) {
    return (c.i() != null) ? c.i().isSlideActivity(paramString) : false;
  }
  
  public boolean isSupportAd(c paramc) {
    i i = this.hostOptionDependRegister.q;
    return (i != null) ? i.isSupportAd(paramc) : false;
  }
  
  public boolean isSupportExitEntirely() {
    return (c.i() != null) ? c.i().isSupportExitEntirely() : super.isSupportExitEntirely();
  }
  
  public boolean isSupportNativeLivePlayer() {
    return (c.j() != null) ? c.j().isSupportNativeLivePlayer() : false;
  }
  
  public boolean isTitlebarMoreMenuVisible() {
    return (c.i() != null) ? c.i().isTitlebarMoreMenuVisible() : super.isTitlebarMoreMenuVisible();
  }
  
  public void jumpToWebView(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {
    c.f().jumpToWebView(paramContext, paramString1, paramString2, paramBoolean);
  }
  
  public void loadImage(Context paramContext, ImageView paramImageView, Uri paramUri) {
    c c1 = (new c(paramUri)).a((View)paramImageView);
    getHostEssentialDepend().loadImage(paramContext, c1);
  }
  
  public void loadImage(Context paramContext, c paramc) {
    getHostEssentialDepend().loadImage(paramContext, paramc);
  }
  
  public void loadSoInHost(String paramString, d.c paramc) {
    if (c.i() != null)
      c.i().loadSoInHost(paramString, paramc); 
  }
  
  public boolean loadSoInHost(String paramString) {
    return (c.i() != null) ? c.i().loadSoInHost(paramString) : false;
  }
  
  public void metaExtraNotify(String paramString1, String paramString2) {
    this.hostOptionDependRegister.f.metaExtraNotify(paramString1, paramString2);
  }
  
  public void muteLiveWindowView(Activity paramActivity, String paramString) {
    c.g().muteLiveWindowView(paramActivity, paramString);
  }
  
  public boolean navigateToVideoView(Activity paramActivity, String paramString) {
    return c.f().navigateToVideoView(paramActivity, paramString);
  }
  
  public boolean needSettingTitleMenuItem() {
    return (c.i() != null) ? c.i().needSettingTitleMenuItem() : super.needSettingTitleMenuItem();
  }
  
  public h obtainShareInfo() {
    return this.hostOptionDependRegister.e.obtainShareInfo();
  }
  
  public b.a obtainShareInfoCallback() {
    return this.hostOptionDependRegister.e.obtainShareInfoCallback();
  }
  
  public void onDeniedWhenHasRequested(Activity paramActivity, String paramString) {
    this.hostOptionDependRegister.f.onDeniedWhenHasRequested(paramActivity, paramString);
  }
  
  public void onWebViewComponentDestroyed(WebView paramWebView) {
    if (c.i() != null)
      c.i().onWebViewComponentDestroyed(paramWebView); 
  }
  
  public void openAwemeUserProfile(Activity paramActivity, String paramString1, String paramString2, boolean paramBoolean, b paramb) {
    a a = this.hostOptionDependRegister.s;
    if (a != null) {
      a.openAwemeUserProfile(paramActivity, paramString1, paramString2, paramBoolean, paramb);
      return;
    } 
    paramb.onFailure(-2, "feature is not supported in app");
  }
  
  public boolean openChooseAddressActivity(Activity paramActivity, int paramInt, String paramString) {
    return c.f().openChooseAddressActivity(paramActivity, paramInt, paramString);
  }
  
  public boolean openCustomerService(Context paramContext, String paramString) {
    return c.f().openCustomerService(paramContext, paramString);
  }
  
  public boolean openDocument(Activity paramActivity, String paramString1, String paramString2) {
    return c.f().openDocument(paramActivity, paramString1, paramString2);
  }
  
  public boolean openLocation(Activity paramActivity, String paramString1, String paramString2, double paramDouble1, double paramDouble2, int paramInt, String paramString3) {
    return c.h().openLocation(paramActivity, paramString1, paramString2, paramDouble1, paramDouble2, paramInt, paramString3);
  }
  
  public boolean openLoginActivity(Activity paramActivity, HashMap<String, Object> paramHashMap) {
    return getHostEssentialDepend().openLoginActivity(paramActivity, paramHashMap);
  }
  
  public boolean openProfile(Activity paramActivity, String paramString) {
    return c.f().openProfile(paramActivity, paramString);
  }
  
  public boolean openSchema(Context paramContext, String paramString) {
    return c.f().openSchema(paramContext, paramString);
  }
  
  public boolean openSchema(Context paramContext, String paramString1, String paramString2) {
    return c.f().openSchema(paramContext, paramString1, paramString2);
  }
  
  public void openShareDialog(Activity paramActivity, h paramh, f paramf) {
    if (this.hostOptionDependRegister.n == null)
      return; 
    this.hostOptionDependRegister.n.openShareDialog(paramActivity, paramh, paramf);
  }
  
  public boolean openWebBrowser(Context paramContext, String paramString, boolean paramBoolean) {
    return c.f().openWebBrowser(paramContext, paramString, paramBoolean);
  }
  
  public void operateDxppTask(String paramString1, String paramString2, long paramLong, String paramString3, String paramString4, String paramString5, JSONArray paramJSONArray, String paramString6, JSONObject paramJSONObject, boolean paramBoolean, DxppCallback paramDxppCallback) {
    a a = this.hostOptionDependRegister.r;
    if (a != null)
      a.operateDxppTask(paramString1, paramString2, paramLong, paramString3, paramString4, paramString5, paramJSONArray, paramString6, paramJSONObject, paramBoolean, paramDxppCallback); 
  }
  
  public void overridePendingTransition(Activity paramActivity, int paramInt1, int paramInt2, int paramInt3) {
    c.f().overridePendingTransition(paramActivity, paramInt1, paramInt2, paramInt3);
  }
  
  public BrandPermissionUtils.BrandPermission permissionTypeToPermission(int paramInt) {
    return this.hostOptionDependRegister.f.permissionTypeToPermission(paramInt);
  }
  
  public j postMultiPart(i parami) throws Exception {
    j j1;
    long l = TimeMeter.currentMillis();
    try {
      j1 = c.e().postMultiPart(parami);
    } catch (Exception exception) {
      j1 = new j();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getClass());
      stringBuilder.append(":");
      stringBuilder.append(exception.getMessage());
      j1.c = stringBuilder.toString();
      j1.f = exception;
      if (DebugUtil.debug()) {
        Application application = AppbrandContext.getInst().getApplicationContext();
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(AppbrandContext.getInst().getApplicationContext().getResources().getString(2097741960));
        stringBuilder1.append(exception.getMessage());
        showToast((Context)application, null, stringBuilder1.toString(), 0L, null);
      } 
    } 
    j j2 = j1;
    if (j1 == null) {
      j2 = new j();
      j2.c = "unknown net error";
    } 
    EventHelper.mpSdkRequestResult(parami, j2, TimeMeter.currentMillis() - l);
    return j2;
  }
  
  public List<Object> replaceMenuItems(List<Object> paramList) {
    return (c.i() != null) ? c.i().replaceMenuItems(paramList) : super.replaceMenuItems(paramList);
  }
  
  public String replaceMicroAppCallName() {
    return c.l().replaceMicroAppCallName();
  }
  
  public String replaceOpenApiDomain() {
    return c.l().replaceOpenApiDomain();
  }
  
  public String replaceProcessName(String paramString) {
    return (c.i() != null) ? c.i().replaceProcessName(paramString) : super.replaceProcessName(paramString);
  }
  
  public String replaceSnssdkApiDomain() {
    return c.l().replaceSnssdkApiDomain();
  }
  
  public List<IMenuItem> replacesMenuItems(List<IMenuItem> paramList) {
    return c.d().replacesMenuItems(paramList);
  }
  
  public void savePermissionGrant(int paramInt, boolean paramBoolean) {
    this.hostOptionDependRegister.f.savePermissionGrant(paramInt, paramBoolean);
  }
  
  public boolean scanCode(Activity paramActivity, b.d paramd) {
    return c.c().scanCode(paramActivity, paramd);
  }
  
  public BrandPermissionUtils.BrandPermission scopeToBrandPermission(String paramString) {
    return this.hostOptionDependRegister.f.scopeToBrandPermission(paramString);
  }
  
  public void setPermissionTime(int paramInt) {
    this.hostOptionDependRegister.f.setPermissionTime(paramInt);
  }
  
  public boolean share(Activity paramActivity, h paramh, g paramg) {
    return getHostEssentialDepend().share(paramActivity, paramh, paramg);
  }
  
  public boolean shouldCheckPermissionBeforeCallhostmethod() {
    return (c.i() != null) ? c.i().shouldCheckPermissionBeforeCallhostmethod() : super.shouldCheckPermissionBeforeCallhostmethod();
  }
  
  public void showActionSheet(Context paramContext, String paramString, String[] paramArrayOfString, NativeModule.NativeModuleCallback<Integer> paramNativeModuleCallback) {
    c.g().showActionSheet(paramContext, paramString, paramArrayOfString, paramNativeModuleCallback);
  }
  
  public void showDatePickerView(Activity paramActivity, String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, b.a<String> parama) {
    c.g().showDatePickerView(paramActivity, paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, parama);
  }
  
  public void showLiveWindowView(Activity paramActivity, String paramString) {
    c.g().showLiveWindowView(paramActivity, paramString);
  }
  
  public void showModal(Activity paramActivity, String paramString1, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5, String paramString6, String paramString7, NativeModule.NativeModuleCallback<Integer> paramNativeModuleCallback) {
    c.g().showModal(paramActivity, paramString1, paramString2, paramString3, paramBoolean, paramString4, paramString5, paramString6, paramString7, paramNativeModuleCallback);
  }
  
  public void showMultiPickerView(Activity paramActivity, String paramString, List<List<String>> paramList, int[] paramArrayOfint, b.b paramb) {
    c.g().showMultiPickerView(paramActivity, paramString, paramList, paramArrayOfint, paramb);
  }
  
  public Dialog showPermissionDialog(Activity paramActivity, int paramInt, String paramString, IPermissionsResultAction paramIPermissionsResultAction) {
    return c.g().showPermissionDialog(paramActivity, paramInt, paramString, paramIPermissionsResultAction);
  }
  
  public Dialog showPermissionsDialog(Activity paramActivity, Set<Integer> paramSet, LinkedHashMap<Integer, String> paramLinkedHashMap, IPermissionsRequestCallback paramIPermissionsRequestCallback, HashMap<String, String> paramHashMap) {
    return c.g().showPermissionsDialog(paramActivity, paramSet, paramLinkedHashMap, paramIPermissionsRequestCallback, paramHashMap);
  }
  
  public void showPickerView(Activity paramActivity, String paramString, int paramInt, List<String> paramList, b.c<String> paramc) {
    c.g().showPickerView(paramActivity, paramString, paramInt, paramList, paramc);
  }
  
  public void showRegionPickerView(Activity paramActivity, String paramString, String[] paramArrayOfString, b.e parame) {
    c.g().showRegionPickerView(paramActivity, paramString, paramArrayOfString, parame);
  }
  
  public void showShareDialog(Activity paramActivity, f paramf) {
    getHostEssentialDepend().showShareDialog(paramActivity, paramf);
  }
  
  public void showTimePickerView(Activity paramActivity, String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, b.f<String> paramf) {
    c.g().showTimePickerView(paramActivity, paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramf);
  }
  
  public void showToast(Context paramContext, String paramString1, String paramString2, long paramLong, String paramString3) {
    c.g().showToast(paramContext, paramString1, paramString2, paramLong, paramString3);
  }
  
  public void showUnSupportView(Activity paramActivity, String paramString, b.g paramg) {
    c.g().showUnSupportView(paramActivity, paramString, paramg);
  }
  
  public boolean startAboutActivity(Activity paramActivity) {
    return (c.i() != null) ? c.i().startAboutActivity(paramActivity) : super.startAboutActivity(paramActivity);
  }
  
  public void startFaceLive(Activity paramActivity, HashMap<String, String> paramHashMap, b paramb) {
    this.hostOptionDependRegister.k.startFaceLive(paramActivity, paramHashMap, paramb);
  }
  
  public boolean startImagePreviewActivity(Activity paramActivity, String paramString, List<String> paramList, int paramInt) {
    return getHostEssentialDepend().startImagePreviewActivity(paramActivity, paramString, paramList, paramInt);
  }
  
  public boolean startMiniAppActivity(Context paramContext, Intent paramIntent) {
    return c.f().startMiniAppActivity(paramContext, paramIntent);
  }
  
  public boolean startMiniAppService(Context paramContext, Intent paramIntent) {
    return c.f().startMiniAppService(paramContext, paramIntent);
  }
  
  public boolean supportChooseAddress() {
    return c.f().supportChooseAddress();
  }
  
  public boolean supportCustomerService() {
    return c.f().supportCustomerService();
  }
  
  public boolean supportDxpp() {
    a a = this.hostOptionDependRegister.r;
    return (a != null && a.supportDxpp());
  }
  
  public boolean supportRequestCommonParamsInChildProcess() {
    return (c.i() != null) ? c.i().supportRequestCommonParamsInChildProcess() : super.supportRequestCommonParamsInChildProcess();
  }
  
  public void syncPermissionToService() {
    this.hostOptionDependRegister.f.syncPermissionToService();
  }
  
  public void syncWebViewLoginCookie(String paramString) {
    if (c.i() != null)
      c.i().syncWebViewLoginCookie(paramString); 
  }
  
  public void updateNpthParams(Map<String, Object> paramMap) {
    this.hostOptionDependRegister.m.updateNpthParams(paramMap);
  }
  
  public ABTestDAO.IUploadVids uploadVid() {
    return c.b().uploadVid();
  }
  
  static class Holder {
    static HostDependManager sInstance = new HostDependManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\host\HostDependManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */