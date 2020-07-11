package com.ss.android.ugc.aweme.miniapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebView;
import com.ss.android.ugc.aweme.miniapp.a.a;
import com.ss.android.ugc.aweme.miniapp.c.a;
import com.ss.android.ugc.aweme.miniapp.e.a;
import com.ss.android.ugc.aweme.miniapp.k.a;
import com.ss.android.ugc.aweme.miniapp.utils.f;
import com.ss.android.ugc.aweme.miniapp_api.a.b;
import com.ss.android.ugc.aweme.miniapp_api.a.c;
import com.ss.android.ugc.aweme.miniapp_api.a.d;
import com.ss.android.ugc.aweme.miniapp_api.a.e;
import com.ss.android.ugc.aweme.miniapp_api.a.f;
import com.ss.android.ugc.aweme.miniapp_api.a.g;
import com.ss.android.ugc.aweme.miniapp_api.a.h;
import com.ss.android.ugc.aweme.miniapp_api.a.i;
import com.ss.android.ugc.aweme.miniapp_api.a.j;
import com.ss.android.ugc.aweme.miniapp_api.a.k;
import com.ss.android.ugc.aweme.miniapp_api.a.l;
import com.ss.android.ugc.aweme.miniapp_api.a.m;
import com.ss.android.ugc.aweme.miniapp_api.a.n;
import com.ss.android.ugc.aweme.miniapp_api.a.o;
import com.ss.android.ugc.aweme.miniapp_api.a.p;
import com.ss.android.ugc.aweme.miniapp_api.b.g;
import com.ss.android.ugc.aweme.miniapp_api.b.i;
import com.ss.android.ugc.aweme.miniapp_api.d;
import com.ss.android.ugc.aweme.miniapp_api.model.b;
import com.ss.android.ugc.aweme.miniapp_api.model.b.b;
import com.ss.android.ugc.aweme.miniapp_api.model.e;
import com.ss.android.ugc.aweme.miniapp_api.services.IMiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.services.b;
import com.tt.miniapp.business.aweme.AwemeHandler;
import com.tt.miniapp.manager.HostActivityManager;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandSupport;
import com.tt.miniapphost.entity.MediaEntity;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.WebViewDataUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;

public class MiniAppService implements IMiniAppService {
  private com.ss.android.ugc.aweme.miniapp_api.a.a mABTestDepend;
  
  private String mAid;
  
  private String mAppName;
  
  private Application mApplication;
  
  private c mBaseLibDepend;
  
  private String mBusinessVersionName;
  
  private String mChannel;
  
  private b mIApmServiceDepend;
  
  private d mICarrierServiceDepend;
  
  private e mIConstantDepend;
  
  private f mIFacialVerifyDepend;
  
  private h mINetWorkDepend;
  
  private j mIPopToastDepend;
  
  private k mIProfileDepend;
  
  private m mISDKMonitorDepend;
  
  private o mIVideoEditorDepend;
  
  private Locale mLocale;
  
  private com.ss.android.ugc.aweme.miniapp_api.services.a mMobClickCombinerIpcService = (com.ss.android.ugc.aweme.miniapp_api.services.a)new a();
  
  private g mMonitorDepend;
  
  private i mPayDepend;
  
  private String mPluginVersionCode;
  
  private l mRouterDepend;
  
  private n mSettingsDepend;
  
  private b mTTDownloaderIpcService = (b)new a();
  
  private String mVersionCode;
  
  private p mWebDepend;
  
  private MiniAppService() {}
  
  public static MiniAppService inst() {
    return a.a;
  }
  
  public String addScene(String paramString1, String paramString2) {
    String str = paramString1;
    if (!TextUtils.isEmpty(paramString1)) {
      if (TextUtils.isEmpty(paramString2))
        return paramString1; 
      MicroSchemaEntity microSchemaEntity = MicroSchemaEntity.parseFromSchema(paramString1);
      if (microSchemaEntity == null)
        return paramString1; 
      microSchemaEntity.setScene(paramString2);
      str = microSchemaEntity.toSchema();
    } 
    return str;
  }
  
  public boolean checkMiniAppEnable(Context paramContext) {
    return (AppbrandConstants.getBundleManager().checkMiniAppDisableState(0) == null);
  }
  
  public com.ss.android.ugc.aweme.miniapp_api.a.a getABTestDepend() {
    return this.mABTestDepend;
  }
  
  public String getAid() {
    return this.mAid;
  }
  
  public b getApmServiceDepend() {
    return this.mIApmServiceDepend;
  }
  
  public String getAppName() {
    return this.mAppName;
  }
  
  public Application getApplication() {
    return this.mApplication;
  }
  
  public c getBaseLibDepend() {
    return this.mBaseLibDepend;
  }
  
  public String getBusinessVersionName() {
    return this.mBusinessVersionName;
  }
  
  public d getCarrierServiceDepend() {
    return this.mICarrierServiceDepend;
  }
  
  public String getChannel() {
    return this.mChannel;
  }
  
  public e getConstantDepend() {
    return this.mIConstantDepend;
  }
  
  public Locale getCurrentLocale() {
    Locale locale = this.mBaseLibDepend.a((Context)this.mApplication);
    return (locale != null) ? locale : this.mLocale;
  }
  
  public f getFacialVerifyDepend() {
    return this.mIFacialVerifyDepend;
  }
  
  public String getJsSdkVersion(Application paramApplication) {
    this.mApplication = paramApplication;
    paramApplication = this.mApplication;
    return AppbrandSupport.inst().getTmaJssdkVersion();
  }
  
  public com.ss.android.ugc.aweme.miniapp_api.services.a getMobClickCombinerIpcService() {
    return this.mMobClickCombinerIpcService;
  }
  
  public g getMonitorDepend() {
    return this.mMonitorDepend;
  }
  
  public h getNetWorkDepend() {
    return this.mINetWorkDepend;
  }
  
  public i getPayDepend() {
    return this.mPayDepend;
  }
  
  public String getPluginVersionCode() {
    return this.mPluginVersionCode;
  }
  
  public j getPopToastDepend() {
    return this.mIPopToastDepend;
  }
  
  public k getProfileDepend() {
    return this.mIProfileDepend;
  }
  
  public l getRouterDepend() {
    return this.mRouterDepend;
  }
  
  public m getSDKMonitorDepend() {
    return this.mISDKMonitorDepend;
  }
  
  public n getSettingsDepend() {
    return this.mSettingsDepend;
  }
  
  public void getShareInfo(String paramString, i parami) {
    if (AppbrandSupport.inst().isInit()) {
      a.a(paramString, parami);
      return;
    } 
    inst().getBaseLibDepend().a(new n(paramString, parami));
  }
  
  public b getTTDownloaderIpcService() {
    return this.mTTDownloaderIpcService;
  }
  
  public String getVersionCode() {
    return this.mVersionCode;
  }
  
  public o getVideoEditorDepend() {
    return this.mIVideoEditorDepend;
  }
  
  public p getWebDepend() {
    return this.mWebDepend;
  }
  
  public void handleActivityImageResult(int paramInt1, int paramInt2, Intent paramIntent) {
    List list = (List)paramIntent.getSerializableExtra("key_media_list");
    ArrayList<MediaEntity> arrayList = new ArrayList();
    for (b.b b1 : list)
      arrayList.add(new MediaEntity(b1.getPath(), b1.getName(), b1.getTime(), b1.getMediaType(), b1.getSize(), b1.getId(), b1.getParentDir())); 
    paramIntent.putParcelableArrayListExtra("key_media_list", arrayList);
  }
  
  public boolean hasFollowedAweme(int paramInt) {
    return !(paramInt != 1 && paramInt != 2);
  }
  
  public void initMiniApp(g paramg) {
    i.a(this.mApplication, paramg, true);
  }
  
  public void initWebViewSuffix(ContextWrapper paramContextWrapper, String paramString) {
    WebViewDataUtil.init(paramContextWrapper, paramString);
    WebView.setDataDirectorySuffix(WebViewDataUtil.getSuffix());
  }
  
  public void initialize(Application paramApplication, IMiniAppService.a parama) {
    this.mApplication = paramApplication;
    this.mAid = parama.a;
    this.mChannel = parama.b;
    this.mVersionCode = parama.c;
    this.mBusinessVersionName = parama.d;
    this.mPluginVersionCode = parama.e;
    this.mAppName = parama.f;
    this.mRouterDepend = parama.h;
    this.mPayDepend = parama.i;
    this.mMonitorDepend = parama.j;
    this.mABTestDepend = parama.k;
    this.mBaseLibDepend = parama.l;
    this.mSettingsDepend = parama.m;
    this.mINetWorkDepend = parama.n;
    this.mIConstantDepend = parama.o;
    this.mISDKMonitorDepend = parama.p;
    this.mIPopToastDepend = parama.q;
    this.mIVideoEditorDepend = parama.r;
    this.mIApmServiceDepend = parama.s;
    this.mIFacialVerifyDepend = parama.t;
    this.mICarrierServiceDepend = parama.u;
    this.mWebDepend = parama.w;
    if (this.mLocale == null)
      this.mLocale = parama.g; 
    this.mIProfileDepend = parama.v;
  }
  
  public boolean isMinAppAvailable(Context paramContext, String paramString) {
    return f.a(paramContext, paramString);
  }
  
  public void jumpToMiniApp(Context paramContext, String paramString1, String paramString2, boolean paramBoolean, String paramString3, String paramString4) {
    if (AppbrandSupport.inst().isInit()) {
      f.a(paramContext, paramString1, paramString2, paramBoolean, paramString3, paramString4);
      return;
    } 
    inst().getBaseLibDepend().a(new m(paramContext, paramString1, paramString2, paramBoolean, paramString3, paramString4));
  }
  
  public void logExcitingVideoAd(Context paramContext, String paramString1, long paramLong, String paramString2) {
    a.a(paramContext, paramString1, paramLong, paramString2);
  }
  
  public void notifyFollowAwemeState(int paramInt) {
    AwemeHandler.Companion.notifyFollowAwemeState(hasFollowedAweme(paramInt));
  }
  
  public void notifyLocaleChange(Locale paramLocale) {
    if (paramLocale == null)
      return; 
    this.mLocale = paramLocale;
    AppbrandSupport.inst().switchLang(paramLocale);
  }
  
  public void onRequestPermissionsResult(Activity paramActivity, String[] paramArrayOfString, int[] paramArrayOfint) {
    PermissionsManager.getInstance().notifyPermissionsChange(paramActivity, paramArrayOfString, paramArrayOfint);
  }
  
  public boolean openMiniApp(Context paramContext, e parame, b paramb) {
    if (d.e(parame.getSchema())) {
      c c1 = this.mBaseLibDepend;
      if (c1 != null && !c1.k())
        return false; 
    } 
    if (AppbrandSupport.inst().isInit())
      return f.a(paramContext, parame, paramb); 
    inst().getBaseLibDepend().a(new l(paramContext, parame, paramb));
    return true;
  }
  
  public boolean openMiniApp(Context paramContext, String paramString, b paramb) {
    if (d.e(paramString)) {
      c c1 = this.mBaseLibDepend;
      if (c1 != null && !c1.k())
        return false; 
    } 
    if (AppbrandSupport.inst().isInit())
      return f.a(paramContext, paramString, paramb); 
    inst().getBaseLibDepend().a(new k(paramContext, paramString, paramb));
    return true;
  }
  
  public boolean openShortcut(Context paramContext, Intent paramIntent) {
    if (!AppbrandSupport.inst().isInit())
      i.a(this.mApplication, null, false); 
    if (AppbrandSupport.inst().isInit());
    return false;
  }
  
  public void preloadMiniApp(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("preloadMiniApp:");
    stringBuilder.append(paramString);
    AppBrandLogger.d("MiniAppService", new Object[] { stringBuilder.toString() });
    if (AppbrandSupport.inst().isInit()) {
      f.a(paramString);
      f.a();
      return;
    } 
    inst().getBaseLibDepend().a(new p(paramString));
  }
  
  public void preloadMiniApp(String paramString, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder("preloadMiniApp:");
    stringBuilder.append(paramString);
    AppBrandLogger.d("MiniAppService", new Object[] { stringBuilder.toString() });
    if (AppbrandSupport.inst().isInit()) {
      f.a(paramString, paramInt, null);
      f.a();
      return;
    } 
    inst().getBaseLibDepend().a(new o(paramString, paramInt));
  }
  
  public void remoteMobV3(String paramString, JSONObject paramJSONObject) {
    HostProcessBridge.logEvent(paramString, paramJSONObject);
  }
  
  public void setBaseLibDepend(c paramc) {
    this.mBaseLibDepend = paramc;
  }
  
  public String setLaunchModeHostTask(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return paramString; 
    MicroSchemaEntity microSchemaEntity = MicroSchemaEntity.parseFromSchema(paramString);
    microSchemaEntity.setLaunchMode(MicroSchemaEntity.LaunchMode.HOST_STACK);
    return microSchemaEntity.toSchema();
  }
  
  public void tryMoveMiniAppActivityToFront(String paramString) {
    HostActivityManager.tryMoveMiniAppActivityTaskToFront(paramString);
  }
  
  static final class a {
    public static final MiniAppService a = new MiniAppService();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\MiniAppService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */