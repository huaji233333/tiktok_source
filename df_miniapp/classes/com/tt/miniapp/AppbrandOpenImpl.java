package com.tt.miniapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.storage.async.Schedulers;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.launch.MiniAppLaunchConfig;
import com.tt.miniapp.launchcache.LaunchCacheCleanDataManager;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.meta.AppInfoHelper;
import com.tt.miniapp.launchcache.meta.AppInfoRequestResult;
import com.tt.miniapp.manager.HostActivityManager;
import com.tt.miniapp.manager.MiniAppPreloadManager;
import com.tt.miniapp.manager.SnapshotManager;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.net.download.DownloadManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.data.SettingsManager;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.toast.ToastManager;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.EventHelper;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.appbase.IAppbrandSupport;
import com.tt.miniapphost.appbase.listener.MiniAppPreloadListCheckListener;
import com.tt.miniapphost.dynamic.IBundleManager;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.DisableStateEntity;
import com.tt.miniapphost.entity.MiniAppPreloadConfigEntity;
import com.tt.miniapphost.entity.PreLoadAppEntity;
import com.tt.miniapphost.entity.PreloadExtSrcEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.language.LocaleUtils;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.StorageUtil;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.option.q.f;
import com.tt.option.q.i;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import okhttp3.ae;
import org.json.JSONObject;

public class AppbrandOpenImpl implements IAppbrandSupport {
  private long getEntranceClickTimestamp(Uri paramUri, long paramLong) {
    if (paramUri == null)
      return paramLong; 
    String str = paramUri.getQueryParameter("entranceClickTs");
    if (TextUtils.isEmpty(str))
      return paramLong; 
    try {
      return Long.parseLong(str);
    } catch (RuntimeException runtimeException) {
      AppBrandLogger.eWithThrowable("tma_AppbrandOpenImpl", "load plugin & clickTs err", runtimeException);
      return paramLong;
    } 
  }
  
  private boolean isNeedDebugSnapShot(Uri paramUri) {
    return (paramUri == null) ? false : (!TextUtils.isEmpty(paramUri.getQueryParameter("snapshot_compile_version")));
  }
  
  private void openMiniAppActivity(Context paramContext, AppInfoEntity paramAppInfoEntity, Uri paramUri, Bundle paramBundle) {
    boolean bool2;
    MiniAppLaunchConfig miniAppLaunchConfig;
    int i = paramAppInfoEntity.type;
    boolean bool1 = true;
    if (i == 1) {
      miniAppLaunchConfig = (MiniAppLaunchConfig)paramBundle.getParcelable("launchConfig");
    } else {
      miniAppLaunchConfig = null;
    } 
    if (miniAppLaunchConfig != null) {
      bool2 = miniAppLaunchConfig.isLaunchWithFloatStyle();
    } else {
      bool2 = false;
    } 
    AppBrandLogger.i("tma_AppbrandOpenImpl", new Object[] { "openMiniAppActivity context:", paramContext });
    int j = AppProcessManager.isAppProcessExist(paramContext, paramAppInfoEntity.appId) ^ true;
    Event.builder("mp_entrance_click", paramAppInfoEntity).kv("cold_launch", Boolean.valueOf(j)).flush();
    if (!TextUtils.isEmpty(paramAppInfoEntity.session) && !TextUtils.isEmpty(paramAppInfoEntity.gtoken) && !TextUtils.isEmpty(paramAppInfoEntity.roomid)) {
      j = 0;
    } else {
      bool1 = false;
    } 
    AppProcessManager.LaunchInfo launchInfo = AppProcessManager.getLaunchClass(paramContext, paramAppInfoEntity, new AppProcessManager.LaunchConfig(AppInfoHelper.isInHostStack(paramUri), bool2, bool1 ^ true, paramUri.getBooleanQueryParameter("forceColdBoot", false)));
    if (launchInfo == null) {
      EventHelper.mpInitResult(paramAppInfoEntity.appId, paramAppInfoEntity.launchFrom, paramAppInfoEntity.scene, paramAppInfoEntity.subScene, paramAppInfoEntity.isGame(), TimeMeter.nowDiff(paramBundle.getLong("entrance_click_timestamp")), "fail", "launchInfo is null");
      return;
    } 
    if (!bool1)
      AppProcessManager.finishServiceSticky(paramContext, launchInfo.getLaunchServiceClass()); 
    if (HostDependManager.getInst().getMiniAppLifeCycleInstance() != null)
      launchInfo.isNeedClearTask(); 
    SnapshotManager.clearCacheSnapshot();
    LaunchCacheCleanDataManager.INSTANCE.manageCacheForNormalLaunch(paramContext, paramAppInfoEntity.appId);
    if (j != 0)
      if (!isNeedDebugSnapShot(paramUri)) {
        doOnMainProcessBeforeColdLaunch(paramContext, launchInfo, paramAppInfoEntity, paramUri.toString(), paramBundle);
      } else {
        startMiniAppWithSnapshotIfHave(paramContext, paramUri, launchInfo, paramAppInfoEntity, paramBundle);
        return;
      }  
    startActivityInMainThread(paramContext, launchInfo, paramAppInfoEntity, paramUri, paramBundle);
  }
  
  private void openUnityContainerActivity(Context paramContext, String paramString, Bundle paramBundle) {
    try {
      Intent intent = new Intent(paramContext, Class.forName("com.unity3d.player.UCUnityPlayerActivity"));
      intent.putExtra("scheme", paramString);
      intent.putExtra("extraInfo", paramBundle);
      intent.addFlags(268435456);
      HostDependManager.getInst().startMiniAppActivity(paramContext, intent);
      return;
    } catch (ClassNotFoundException classNotFoundException) {
      ToastManager.showToast(paramContext, "open uc error", 1L);
      return;
    } 
  }
  
  private void startMiniAppWithSnapshotIfHave(final Context context, final Uri uri, final AppProcessManager.LaunchInfo launchInfo, final AppInfoEntity appInfoEntity, final Bundle launchExtraBundle) {
    if (uri == null)
      return; 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            // Byte code:
            //   0: ldc ''
            //   2: astore_3
            //   3: new java/lang/StringBuilder
            //   6: dup
            //   7: invokespecial <init> : ()V
            //   10: astore #4
            //   12: aload_0
            //   13: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   16: getfield token : Ljava/lang/String;
            //   19: ldc 'utf-8'
            //   21: invokestatic encode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
            //   24: astore_1
            //   25: aload_0
            //   26: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   29: getfield startPage : Ljava/lang/String;
            //   32: ldc 'utf-8'
            //   34: invokestatic encode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
            //   37: astore_2
            //   38: goto -> 47
            //   41: ldc ''
            //   43: astore_1
            //   44: ldc ''
            //   46: astore_2
            //   47: aload #4
            //   49: invokestatic getInst : ()Lcom/tt/miniapp/AppbrandConstant$OpenApi;
            //   52: invokevirtual getSNAP_SHOT_URL : ()Ljava/lang/String;
            //   55: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   58: pop
            //   59: aload #4
            //   61: ldc '?appid='
            //   63: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   66: pop
            //   67: aload #4
            //   69: aload_0
            //   70: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   73: getfield appId : Ljava/lang/String;
            //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   79: pop
            //   80: aload #4
            //   82: ldc '&path='
            //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   87: pop
            //   88: aload #4
            //   90: aload_2
            //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   94: pop
            //   95: aload #4
            //   97: ldc '&token='
            //   99: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   102: pop
            //   103: aload #4
            //   105: aload_1
            //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   109: pop
            //   110: aload #4
            //   112: ldc '&version='
            //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   117: pop
            //   118: aload #4
            //   120: aload_0
            //   121: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   124: getfield versionType : Ljava/lang/String;
            //   127: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   130: pop
            //   131: aload #4
            //   133: ldc '&device_id='
            //   135: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   138: pop
            //   139: aload #4
            //   141: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
            //   144: invokevirtual getInitParams : ()Lcom/tt/miniapphost/entity/InitParamsEntity;
            //   147: invokevirtual getDeviceId : ()Ljava/lang/String;
            //   150: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   153: pop
            //   154: aload_0
            //   155: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   158: getfield snapShotCompileVersion : Ljava/lang/String;
            //   161: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
            //   164: ifne -> 188
            //   167: aload #4
            //   169: ldc '&snapshot_compile_version='
            //   171: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   174: pop
            //   175: aload #4
            //   177: aload_0
            //   178: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   181: getfield snapShotCompileVersion : Ljava/lang/String;
            //   184: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   187: pop
            //   188: aload_0
            //   189: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   192: getfield sourceMd5 : Ljava/lang/String;
            //   195: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
            //   198: ifne -> 222
            //   201: aload #4
            //   203: ldc '&source_md5='
            //   205: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   208: pop
            //   209: aload #4
            //   211: aload_0
            //   212: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   215: getfield sourceMd5 : Ljava/lang/String;
            //   218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   221: pop
            //   222: invokestatic getInst : ()Lcom/tt/miniapp/manager/NetManager;
            //   225: new com/tt/option/q/i
            //   228: dup
            //   229: aload #4
            //   231: invokevirtual toString : ()Ljava/lang/String;
            //   234: ldc 'GET'
            //   236: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
            //   239: invokevirtual request : (Lcom/tt/option/q/i;)Lcom/tt/option/q/j;
            //   242: invokevirtual a : ()Ljava/lang/String;
            //   245: astore_1
            //   246: new org/json/JSONObject
            //   249: dup
            //   250: aload_1
            //   251: invokespecial <init> : (Ljava/lang/String;)V
            //   254: astore_1
            //   255: aload_1
            //   256: ldc 'error'
            //   258: invokevirtual optInt : (Ljava/lang/String;)I
            //   261: ifeq -> 287
            //   264: ldc 'tma_AppbrandOpenImpl'
            //   266: iconst_1
            //   267: anewarray java/lang/Object
            //   270: dup
            //   271: iconst_0
            //   272: aload_1
            //   273: ldc 'message'
            //   275: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
            //   278: aastore
            //   279: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
            //   282: aload_3
            //   283: astore_1
            //   284: goto -> 326
            //   287: aload_1
            //   288: ldc 'data'
            //   290: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
            //   293: astore_2
            //   294: aload_3
            //   295: astore_1
            //   296: aload_2
            //   297: ifnull -> 326
            //   300: aload_2
            //   301: ldc 'snapshot_url'
            //   303: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
            //   306: astore_1
            //   307: goto -> 326
            //   310: astore_1
            //   311: ldc 'tma_AppbrandOpenImpl'
            //   313: iconst_1
            //   314: anewarray java/lang/Object
            //   317: dup
            //   318: iconst_0
            //   319: aload_1
            //   320: aastore
            //   321: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
            //   324: aload_3
            //   325: astore_1
            //   326: aload_0
            //   327: getfield this$0 : Lcom/tt/miniapp/AppbrandOpenImpl;
            //   330: aload_1
            //   331: invokevirtual downloadSnapShot : (Ljava/lang/String;)Ljava/lang/String;
            //   334: astore_1
            //   335: aload_0
            //   336: getfield val$launchExtraBundle : Landroid/os/Bundle;
            //   339: ldc 'mp_extra_vdom'
            //   341: aload_1
            //   342: invokevirtual putString : (Ljava/lang/String;Ljava/lang/String;)V
            //   345: aload_0
            //   346: getfield this$0 : Lcom/tt/miniapp/AppbrandOpenImpl;
            //   349: aload_0
            //   350: getfield val$context : Landroid/content/Context;
            //   353: aload_0
            //   354: getfield val$launchInfo : Lcom/tt/miniapp/process/AppProcessManager$LaunchInfo;
            //   357: aload_0
            //   358: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   361: aload_0
            //   362: getfield val$uri : Landroid/net/Uri;
            //   365: invokevirtual toString : ()Ljava/lang/String;
            //   368: aload_0
            //   369: getfield val$launchExtraBundle : Landroid/os/Bundle;
            //   372: invokevirtual doOnMainProcessBeforeColdLaunch : (Landroid/content/Context;Lcom/tt/miniapp/process/AppProcessManager$LaunchInfo;Lcom/tt/miniapphost/entity/AppInfoEntity;Ljava/lang/String;Landroid/os/Bundle;)V
            //   375: aload_0
            //   376: getfield this$0 : Lcom/tt/miniapp/AppbrandOpenImpl;
            //   379: aload_0
            //   380: getfield val$context : Landroid/content/Context;
            //   383: aload_0
            //   384: getfield val$launchInfo : Lcom/tt/miniapp/process/AppProcessManager$LaunchInfo;
            //   387: aload_0
            //   388: getfield val$appInfoEntity : Lcom/tt/miniapphost/entity/AppInfoEntity;
            //   391: aload_0
            //   392: getfield val$uri : Landroid/net/Uri;
            //   395: aload_0
            //   396: getfield val$launchExtraBundle : Landroid/os/Bundle;
            //   399: invokevirtual startActivityInMainThread : (Landroid/content/Context;Lcom/tt/miniapp/process/AppProcessManager$LaunchInfo;Lcom/tt/miniapphost/entity/AppInfoEntity;Landroid/net/Uri;Landroid/os/Bundle;)V
            //   402: return
            //   403: astore_1
            //   404: goto -> 41
            //   407: astore_2
            //   408: goto -> 44
            // Exception table:
            //   from	to	target	type
            //   12	25	403	java/io/UnsupportedEncodingException
            //   25	38	407	java/io/UnsupportedEncodingException
            //   246	282	310	org/json/JSONException
            //   287	294	310	org/json/JSONException
            //   300	307	310	org/json/JSONException
          }
        },  Schedulers.longIO());
  }
  
  public void cancelPreloadMiniApp(String paramString) {
    MiniAppPreloadManager.cancelPreloadMiniApp(paramString);
  }
  
  public void doOnMainProcessBeforeColdLaunch(final Context context, final AppProcessManager.LaunchInfo launchInfo, final AppInfoEntity appInfoEntity, final String schema, final Bundle extra) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            AppProcessManager.ProcessInfo processInfo = launchInfo.getProcessInfo();
            AppProcessManager.startMiniProcessMonitor(context, processInfo);
            try {
              InnerMiniAppProcessBridge.prepareLaunch(processInfo.mProcessIdentity, appInfoEntity, schema, extra);
            } catch (Exception exception) {
              AppBrandLogger.eWithThrowable("tma_AppbrandOpenImpl", "sendAppInfo", exception);
            } 
            try {
              if (SettingsDAO.getInt(context, 0, new Enum[] { (Enum)Settings.BDP_META_CONFIG, (Enum)Settings.BdpMetaConfig.MAIN_PROCESS_PREFETCH_KEY }) == 1) {
                AppInfoRequestResult appInfoRequestResult = AppInfoHelper.request(context, appInfoEntity, RequestType.prefetch_host);
                if (appInfoRequestResult != null) {
                  boolean bool = appInfoRequestResult.requestRecordList.isEmpty();
                  if (!bool)
                    try {
                      InnerMiniAppProcessBridge.sendPrefetchedAppInfo(processInfo.mProcessIdentity, appInfoRequestResult);
                      return;
                    } catch (Exception exception) {
                      return;
                    }  
                } 
              } 
              return;
            } catch (Exception exception) {
              AppBrandLogger.eWithThrowable("tma_AppbrandOpenImpl", "prefetchAppInfo", exception);
              return;
            } 
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public boolean doOpenSchema(Context paramContext, String paramString, Bundle paramBundle) {
    StringBuilder stringBuilder;
    byte b;
    if (paramBundle == null)
      paramBundle = new Bundle(); 
    String str4 = paramBundle.getString("mp_open_launch_id");
    String str3 = str4;
    if (TextUtils.isEmpty(str4))
      str3 = AppbrandContext.getInst().getUniqueId(); 
    AppbrandContext.getInst().setLaunchId(str3);
    paramBundle.putString("mp_open_launch_id", str3);
    paramBundle.putLong("mp_open_app_schema_timestamp", System.currentTimeMillis());
    paramBundle.putLong("mp_open_app_schema_cputime", SystemClock.elapsedRealtime());
    long l = TimeMeter.currentMillis();
    Uri uri = Uri.parse(paramString);
    l = getEntranceClickTimestamp(uri, l);
    paramBundle.putLong("entrance_click_timestamp", l);
    Locale locale = LocaleManager.getInst().getCurrentHostSetLocale();
    if (locale != null)
      paramBundle.putString("lang", LocaleUtils.locale2String(locale)); 
    String str2 = uri.getHost();
    if (!TextUtils.equals(str2, "microapp") && !TextUtils.equals(str2, "microgame")) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "scheme is not match" });
      EventHelper.mpInitResult(null, null, null, null, false, 0L, "fail", "scheme is not match");
      LoadHelper.handleHostProcessFail(paramContext, ErrorCode.MAIN.SCHEME_NOT_MATCH.getCode());
      return false;
    } 
    IBundleManager iBundleManager = AppbrandConstants.getBundleManager();
    if (TextUtils.equals(str2, "microapp")) {
      b = 1;
    } else {
      b = 2;
    } 
    DisableStateEntity disableStateEntity = iBundleManager.checkMiniAppDisableState(b);
    if (disableStateEntity != null) {
      if (paramContext == null || TextUtils.isEmpty(paramString) || !HostDependManager.getInst().handleAppbrandDisablePage(paramContext, paramString))
        AppbrandUtil.handleAppbrandDisableState(paramContext, disableStateEntity); 
      stringBuilder = new StringBuilder("disable: ");
      stringBuilder.append(disableStateEntity.getHintMessage());
      EventHelper.mpInitResult(null, null, null, null, false, 0L, "fail", stringBuilder.toString());
      LoadHelper.monitorErrorEvent(ErrorCode.MAIN.DEVICE_BLACK_LIST.getCode(), ErrorCode.MAIN.DEVICE_BLACK_LIST.getDesc());
      return false;
    } 
    String str1 = uri.getQueryParameter("app_id");
    if (TextUtils.isEmpty(str1)) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "scheme is not ok, appId is null" });
      EventHelper.mpInitResult(null, null, null, null, false, 0L, "fail", "app_id is empty");
      LoadHelper.handleHostProcessFail((Context)stringBuilder, ErrorCode.MAIN.SCHEME_APPID_NULL.getCode());
      return false;
    } 
    try {
      return true;
    } finally {
      paramBundle = null;
      JSONObject jSONObject = new JSONObject();
      try {
        StringBuilder stringBuilder2 = new StringBuilder("openMiniAppActivity 异常：");
        stringBuilder2.append(paramBundle.toString());
        jSONObject.put("errMsg", stringBuilder2.toString());
      } catch (Exception exception) {
        AppBrandLogger.eWithThrowable("tma_AppbrandOpenImpl", "openMiniAppActivity", exception);
      } 
      AppBrandMonitor.statusRate("mp_start_error", 5000, jSONObject);
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "openMiniAppActivity", paramBundle });
      l = TimeMeter.nowDiff(l);
      StringBuilder stringBuilder1 = new StringBuilder("openMiniAppActivity fail scheme:");
      stringBuilder1.append(paramString);
      stringBuilder1.append(" error:");
      stringBuilder1.append(paramBundle.getMessage());
      EventHelper.mpInitResult(str1, null, null, null, false, l, "fail", stringBuilder1.toString());
      LoadHelper.handleHostProcessFail((Context)stringBuilder, ErrorCode.MAIN.START_MINI_APP_ERROR.getCode());
    } 
  }
  
  public String downloadSnapShot(String paramString) {
    String str;
    if (TextUtils.isEmpty(paramString)) {
      AppBrandLogger.d("tma_AppbrandOpenImpl", new Object[] { "snapshot api response snapshot url is null" });
      return "";
    } 
    try {
      paramString = URLDecoder.decode(paramString, "utf-8");
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { unsupportedEncodingException });
      str = "";
    } 
    f f = new f(str, false);
    f.a = StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()).getPath();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(System.currentTimeMillis());
    stringBuilder.append(".ooo");
    f.b = stringBuilder.toString();
    File file = DownloadManager.get().syncDownload(f.f(), ((i)f).f, f.a, f.b, new DownloadManager.OnDownloadListener() {
          public void onDownloadFailed(String param1String, Throwable param1Throwable) {}
          
          public void onDownloadSuccess(ae param1ae) {}
          
          public void onDownloading(int param1Int, long param1Long1, long param1Long2) {}
        },  null);
    if (file == null) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "snapshot download file is null" });
      return "";
    } 
    return IOUtils.readString(file.getAbsolutePath(), "utf-8");
  }
  
  public String getTmaJssdkVersion() {
    try {
      Application application = AppbrandContext.getInst().getApplicationContext();
      return BaseBundleManager.getInst().getSdkCurrentVersionStr((Context)application);
    } catch (Exception exception) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { exception });
      return "";
    } 
  }
  
  public String getTmaJssdkVersion(Context paramContext) {
    return getTmaJssdkVersion();
  }
  
  public boolean isSDKSupport() {
    try {
      return AppbrandConstants.getBundleManager().isSDKSupport(0);
    } catch (Exception exception) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { exception });
      return false;
    } 
  }
  
  public boolean isSDKSupport(Context paramContext) {
    return isSDKSupport();
  }
  
  public boolean openAppbrand(String paramString) {
    return openAppbrand(paramString, null);
  }
  
  public boolean openAppbrand(final String scheme, final Bundle extra) {
    AppBrandLogger.i("tma_AppbrandOpenImpl", new Object[] { "openAppbrand scheme == ", scheme });
    final Application context = AppbrandContext.getInst().getApplicationContext();
    if (TextUtils.isEmpty(scheme)) {
      LoadHelper.handleHostProcessFail((Context)application, ErrorCode.MAIN.SCHEME_NULL_ERROR.getCode());
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "scheme is empty" });
      EventHelper.mpInitResult(null, null, null, null, false, 0L, "fail", "scheme is empty");
      return false;
    } 
    if (!BaseBundleManager.getInst().isRealBaseBundleReadyNow()) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "basebundle not ready,try download basebundle" });
      SettingsManager.getInstance().forceUpdateSettingsAndBasebundle(new BaseBundleManager.BaseBundleUpdateListener() {
            public void onBaseBundleUpdate() {
              AppbrandOpenImpl.this.doOpenSchema(context, scheme, extra);
            }
          });
      return true;
    } 
    return doOpenSchema((Context)application, scheme, extra);
  }
  
  public boolean openShortcut(Intent paramIntent) {
    if (paramIntent == null) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "shortcut intent is null" });
      return false;
    } 
    Uri uri = paramIntent.getData();
    if (uri == null) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "shortcut intent data null" });
      return false;
    } 
    String str = uri.toString();
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("tma_AppbrandOpenImpl", new Object[] { "shortcut intent schemaStr null" });
      return false;
    } 
    openAppbrand(str);
    return true;
  }
  
  public void preloadEmptyProcess() {
    AppbrandConstants.getProcessManager().preloadEmptyProcess(true);
  }
  
  public void preloadMiniApp(List<PreLoadAppEntity> paramList, List<PreloadExtSrcEntity> paramList1) {
    MiniAppPreloadManager.startPreloadMiniApp(paramList, null, null);
  }
  
  public void preloadMiniApp(List<PreLoadAppEntity> paramList, Map<String, String> paramMap, MiniAppPreloadListCheckListener paramMiniAppPreloadListCheckListener) {
    MiniAppPreloadManager.startPreloadMiniApp(paramList, paramMap, paramMiniAppPreloadListCheckListener);
  }
  
  public void preloadMiniApp(List<PreLoadAppEntity> paramList, Map<String, String> paramMap, MiniAppPreloadListCheckListener paramMiniAppPreloadListCheckListener, Executor paramExecutor) {
    MiniAppPreloadManager.startPreloadMiniApp(paramList, paramMap, paramMiniAppPreloadListCheckListener, paramExecutor);
  }
  
  public void setMiniAppPreloadConfigEntity(MiniAppPreloadConfigEntity paramMiniAppPreloadConfigEntity) {
    MiniAppPreloadManager.setMiniAppPreloadConfigEntity(paramMiniAppPreloadConfigEntity);
  }
  
  public void startActivityInMainThread(final Context context, final AppProcessManager.LaunchInfo launchInfo, final AppInfoEntity appInfoEntity, final Uri uri, final Bundle launchExtraBundle) {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            try {
              return;
            } finally {
              Exception exception = null;
              AppbrandOpenImpl.this.uploadOpenMiniAppError(context, exception, appInfoEntity, launchExtraBundle);
            } 
          }
        });
  }
  
  public void startMiniAppActivity(Context paramContext, Uri paramUri, AppProcessManager.LaunchInfo paramLaunchInfo, AppInfoEntity paramAppInfoEntity, Bundle paramBundle) {
    boolean bool;
    Activity activity = HostActivityManager.getHostTopActivity();
    if (Boolean.parseBoolean(paramUri.getQueryParameter("pluginLoading")) || paramLaunchInfo.isFloatStyle()) {
      bool = true;
    } else {
      bool = false;
    } 
    Intent intent = new Intent();
    intent.setClass(paramContext, paramLaunchInfo.getLaunchActivityClass());
    intent.putExtra("microapp_url", paramUri.toString());
    intent.putExtra("microapp_appinfo", (Parcelable)paramAppInfoEntity);
    intent.putExtra("app_type", paramAppInfoEntity.type);
    if (paramLaunchInfo.isInHostStack()) {
      intent.addFlags(67108864);
      if (!paramLaunchInfo.isNeedClearTask())
        intent.addFlags(536870912); 
    } else if (paramLaunchInfo.isNeedClearTask()) {
      intent.addFlags(32768);
    } 
    paramBundle.putLong("mp_start_activity_timestamp", System.currentTimeMillis());
    paramBundle.putLong("mp_start_activity_cputime", SystemClock.elapsedRealtime());
    intent.putExtra("mp_launch_extra", paramBundle);
    intent.addFlags(268435456);
    AppBrandLogger.i("tma_AppbrandOpenImpl", new Object[] { "startMiniAppActivity context:", paramContext });
    if (bool)
      intent.addFlags(65536); 
    HostDependManager.getInst().startMiniAppActivity(paramContext, intent);
    if (activity != null) {
      if (bool) {
        ActivityUtil.changeToSilentHideActivityAnimation(activity);
        return;
      } 
      HostDependManager.getInst().overridePendingTransition(activity, 2131034237, 2131034235, 1);
    } 
  }
  
  public void switchLang(Locale paramLocale) {
    AppbrandUtil.setLanguage(paramLocale);
  }
  
  public void uploadOpenMiniAppError(Context paramContext, Throwable paramThrowable, AppInfoEntity paramAppInfoEntity, Bundle paramBundle) {
    JSONObject jSONObject = new JSONObject();
    try {
      StringBuilder stringBuilder = new StringBuilder("openMiniAppActivity 异常：");
      stringBuilder.append(paramThrowable.toString());
      jSONObject.put("errMsg", stringBuilder.toString());
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("tma_AppbrandOpenImpl", "openMiniAppActivity", exception);
    } 
    AppBrandMonitor.statusRate("mp_start_error", 5000, jSONObject);
    AppBrandLogger.eWithThrowable("tma_AppbrandOpenImpl", "openMiniAppActivity", paramThrowable);
    EventHelper.mpInitResult(paramAppInfoEntity.appId, paramAppInfoEntity.launchFrom, paramAppInfoEntity.scene, paramAppInfoEntity.subScene, paramAppInfoEntity.isGame(), TimeMeter.nowDiff(paramBundle.getLong("entrance_click_timestamp")), "fail", "openMiniAppActivity exp");
    LoadHelper.handleHostProcessFail(paramContext, ErrorCode.MAIN.START_MINI_APP_ERROR.getCode());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\AppbrandOpenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */