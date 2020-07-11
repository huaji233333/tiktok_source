package com.tt.miniapphost;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.he.loader.Log;
import com.storage.async.Action;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.launchcache.meta.AppInfoHelper;
import com.tt.miniapp.settings.data.SettingsManager;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.entity.SDKBuildConfigEntity;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.process.handler.IAsyncHostDataHandler;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.DynamicAppAssetsCompat;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.e.f;
import com.tt.option.e.i;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class AppbrandContext {
  private static boolean mHasInit;
  
  public static Handler mainHandler = new Handler(Looper.getMainLooper());
  
  private SDKBuildConfigEntity configEntity;
  
  private InitParamsEntity initParams;
  
  private f.a mApiHandlerCreator;
  
  private Application mApplicationContext;
  
  private Map<String, IAsyncHostDataHandler> mAsyncHandlerMap = new HashMap<String, IAsyncHostDataHandler>();
  
  boolean mIsGame;
  
  private String mLaunchId = "";
  
  private MiniappHostBase mMiniAppActivity;
  
  private i.a mNativeViewCreator;
  
  private Map<String, ISyncHostDataHandler> mSyncHandlerMap = new HashMap<String, ISyncHostDataHandler>();
  
  private List<Object> mTitleMenuItem;
  
  private final String sUniqueId;
  
  static {
    mHasInit = false;
  }
  
  private AppbrandContext() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(UUID.randomUUID().toString().substring(0, 6));
    stringBuilder.append(System.currentTimeMillis());
    this.sUniqueId = stringBuilder.toString();
    boolean bool = GameModuleController.inst().isGameModuleReady();
    String str2 = "3.7.1-douyin";
    String str1 = str2;
    if (bool)
      try {
        Object object;
        Field field = Class.forName("com.he.BuildConfig").getDeclaredField("VERSION_NAME");
        stringBuilder = null;
        if (field != null)
          object = field.get(null); 
      } finally {
        stringBuilder = null;
        AppBrandLogger.e("AppbrandContext", new Object[] { "get dora version failed", stringBuilder });
      }  
    this.configEntity = new SDKBuildConfigEntity(1, "3.7.4-tiktok", str1);
  }
  
  private void LocaleInstance() {}
  
  public static AppbrandContext getInst() {
    return Holder.sInstance;
  }
  
  public static void init(Application paramApplication, IAppbrandInitializer paramIAppbrandInitializer) {
    if (paramApplication != null) {
      boolean bool;
      getInst().setApplicationContext(paramApplication);
      DebugUtil.updateDebugState((Context)paramApplication, paramIAppbrandInitializer);
      String str = ProcessUtil.getCurProcessName((Context)paramApplication);
      boolean bool1 = ProcessUtil.isMainProcess((Context)paramApplication);
      if (str != null && ProcessUtil.isBdpProcess()) {
        bool = true;
      } else {
        bool = false;
      } 
      if (!bool1 && !bool) {
        AppBrandLogger.d("AppbrandContext", new Object[] { "!isMainProcess && !isMiniAppProcess" });
        return;
      } 
      if (Build.VERSION.SDK_INT < 21)
        return; 
      try {
        return;
      } finally {
        paramIAppbrandInitializer = null;
        AppBrandLogger.e("AppbrandContext", new Object[] { "", paramIAppbrandInitializer });
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("errMsg", Log.getStackTraceString((Throwable)paramIAppbrandInitializer));
          jSONObject.put("mhasinit", AppbrandSupport.inst().isInit());
          jSONObject.put("isMainProcess", bool1);
          AppBrandMonitor.statusRate("mp_init_error", 1005, jSONObject);
        } catch (JSONException jSONException) {
          AppBrandLogger.e("AppbrandContext", new Object[] { jSONException });
        } 
      } 
    } 
  }
  
  private void initHostProcessDataHandler(List<ISyncHostDataHandler> paramList, List<IAsyncHostDataHandler> paramList1) {
    if (paramList != null)
      for (ISyncHostDataHandler iSyncHostDataHandler : paramList) {
        if (iSyncHostDataHandler != null)
          this.mSyncHandlerMap.put(iSyncHostDataHandler.getType(), iSyncHostDataHandler); 
      }  
    if (paramList1 != null)
      for (IAsyncHostDataHandler iAsyncHostDataHandler : paramList1) {
        if (iAsyncHostDataHandler != null)
          this.mAsyncHandlerMap.put(iAsyncHostDataHandler.getType(), iAsyncHostDataHandler); 
      }  
  }
  
  private static void initSync(Application paramApplication, String paramString, boolean paramBoolean, IAppbrandInitializer paramIAppbrandInitializer) {
    // Byte code:
    //   0: ldc com/tt/miniapphost/AppbrandContext
    //   2: monitorenter
    //   3: ldc 'AppbrandContext'
    //   5: iconst_4
    //   6: anewarray java/lang/Object
    //   9: dup
    //   10: iconst_0
    //   11: ldc_w 'processName: '
    //   14: aastore
    //   15: dup
    //   16: iconst_1
    //   17: aload_1
    //   18: aastore
    //   19: dup
    //   20: iconst_2
    //   21: ldc_w 'hasInit: '
    //   24: aastore
    //   25: dup
    //   26: iconst_3
    //   27: invokestatic inst : ()Lcom/tt/miniapphost/AppbrandSupport;
    //   30: invokevirtual isInit : ()Z
    //   33: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   36: aastore
    //   37: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   40: invokestatic inst : ()Lcom/tt/miniapphost/AppbrandSupport;
    //   43: invokevirtual isInit : ()Z
    //   46: istore #4
    //   48: iload #4
    //   50: ifeq -> 57
    //   53: ldc com/tt/miniapphost/AppbrandContext
    //   55: monitorexit
    //   56: return
    //   57: aload_0
    //   58: ldc_w 'app'
    //   61: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   64: getstatic com/bytedance/d/a/a.c : Lcom/bytedance/d/a/a;
    //   67: astore #5
    //   69: aload_0
    //   70: ldc_w 'app'
    //   73: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   76: aload_0
    //   77: putstatic com/bytedance/d/a/a.b : Landroid/app/Application;
    //   80: getstatic com/tt/a/c.b : Lcom/tt/a/c;
    //   83: checkcast com/bytedance/d/a/c/c/a/a
    //   86: astore #6
    //   88: aload #6
    //   90: ldc_w 'plugin'
    //   93: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   96: aload #5
    //   98: aload #6
    //   100: checkcast com/bytedance/d/a/b/a
    //   103: invokevirtual a : (Lcom/bytedance/d/a/b/a;)Lcom/bytedance/d/a/a;
    //   106: astore #5
    //   108: getstatic com/tt/a/a.a : Lcom/tt/a/a;
    //   111: checkcast com/bytedance/d/a/c/a/a
    //   114: astore #6
    //   116: aload #6
    //   118: ldc_w 'plugin'
    //   121: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   124: aload #5
    //   126: aload #6
    //   128: checkcast com/bytedance/d/a/b/a
    //   131: invokevirtual a : (Lcom/bytedance/d/a/b/a;)Lcom/bytedance/d/a/a;
    //   134: astore #5
    //   136: getstatic com/tt/a/b.a : Lcom/tt/a/b;
    //   139: checkcast com/bytedance/d/a/c/b/a
    //   142: astore #6
    //   144: aload #6
    //   146: ldc_w 'plugin'
    //   149: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   152: aload #5
    //   154: aload #6
    //   156: checkcast com/bytedance/d/a/b/a
    //   159: invokevirtual a : (Lcom/bytedance/d/a/b/a;)Lcom/bytedance/d/a/a;
    //   162: astore #5
    //   164: getstatic com/tt/a/d.a : Lcom/tt/a/d;
    //   167: checkcast com/bytedance/d/a/c/d/a
    //   170: astore #6
    //   172: aload #6
    //   174: ldc_w 'plugin'
    //   177: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   180: aload #5
    //   182: aload #6
    //   184: checkcast com/bytedance/d/a/b/a
    //   187: invokevirtual a : (Lcom/bytedance/d/a/b/a;)Lcom/bytedance/d/a/a;
    //   190: pop
    //   191: invokestatic inst : ()Lcom/tt/miniapphost/game/GameModuleController;
    //   194: invokevirtual tryInit : ()V
    //   197: aload_0
    //   198: invokestatic init : (Landroid/content/Context;)V
    //   201: aload_0
    //   202: invokestatic initProcessList : (Landroid/content/Context;)V
    //   205: invokestatic registerLogger : ()V
    //   208: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   211: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   214: invokevirtual createInitParams : ()Lcom/tt/miniapphost/entity/InitParamsEntity;
    //   217: invokevirtual setInitParams : (Lcom/tt/miniapphost/entity/InitParamsEntity;)V
    //   220: aload_0
    //   221: invokestatic ensureDynamicFeatureAssets : (Landroid/content/Context;)V
    //   224: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   227: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   230: invokevirtual createSyncHostDataHandlerList : ()Ljava/util/List;
    //   233: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   236: invokevirtual createAsyncHostDataHandlerList : ()Ljava/util/List;
    //   239: invokespecial initHostProcessDataHandler : (Ljava/util/List;Ljava/util/List;)V
    //   242: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   245: invokevirtual initNativeUIParams : ()V
    //   248: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   251: aload_0
    //   252: invokevirtual initFeignHostConfig : (Landroid/content/Context;)V
    //   255: aload_0
    //   256: invokestatic init : (Landroid/content/Context;)V
    //   259: iload_2
    //   260: ifeq -> 287
    //   263: invokestatic checkAndReportSavedEvents : ()V
    //   266: aload_0
    //   267: invokestatic registerHostLifecycleListener : (Landroid/app/Application;)V
    //   270: getstatic com/tt/miniapp/launchcache/LaunchCacheCleanDataManager.INSTANCE : Lcom/tt/miniapp/launchcache/LaunchCacheCleanDataManager;
    //   273: aload_0
    //   274: invokevirtual manageCacheForSdkLaunch : (Landroid/content/Context;)V
    //   277: getstatic com/tt/miniapp/launchcache/SilenceUpdateManager.INSTANCE : Lcom/tt/miniapp/launchcache/SilenceUpdateManager;
    //   280: aload_0
    //   281: invokevirtual updateForSdkLaunch : (Landroid/content/Context;)V
    //   284: goto -> 330
    //   287: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   290: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   293: invokevirtual createTitleMenuItems : ()Ljava/util/List;
    //   296: invokevirtual setTitleMenuItems : (Ljava/util/List;)V
    //   299: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   302: invokevirtual createNativeView : ()Lcom/tt/option/e/i$a;
    //   305: astore #5
    //   307: aload #5
    //   309: ifnull -> 320
    //   312: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   315: aload #5
    //   317: invokevirtual setNativeViewCreator : (Lcom/tt/option/e/i$a;)V
    //   320: aload_0
    //   321: invokestatic init : (Landroid/app/Application;)V
    //   324: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   327: invokevirtual onCreate : ()V
    //   330: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   333: invokevirtual getInitLocale : ()Ljava/util/Locale;
    //   336: astore #5
    //   338: invokestatic getInst : ()Lcom/tt/miniapphost/language/LocaleManager;
    //   341: aload #5
    //   343: invokevirtual notifyLangChange : (Ljava/util/Locale;)V
    //   346: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   349: invokevirtual createExtHandler : ()Lcom/tt/option/e/f$a;
    //   352: astore #5
    //   354: aload #5
    //   356: ifnull -> 367
    //   359: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   362: aload #5
    //   364: invokevirtual setExtensionApiCreator : (Lcom/tt/option/e/f$a;)V
    //   367: aload_3
    //   368: aload_0
    //   369: aload_1
    //   370: iload_2
    //   371: invokeinterface init : (Landroid/app/Application;Ljava/lang/String;Z)V
    //   376: new com/tt/miniapphost/AppbrandContext$1
    //   379: dup
    //   380: iload_2
    //   381: invokespecial <init> : (Z)V
    //   384: invokestatic longIO : ()Lcom/storage/async/Scheduler;
    //   387: invokestatic runOnWorkThread : (Lcom/storage/async/Action;Lcom/storage/async/Scheduler;)V
    //   390: iload_2
    //   391: ifeq -> 453
    //   394: new android/os/Handler
    //   397: dup
    //   398: invokestatic getBackgroundHandlerThread : ()Landroid/os/HandlerThread;
    //   401: invokevirtual getLooper : ()Landroid/os/Looper;
    //   404: invokespecial <init> : (Landroid/os/Looper;)V
    //   407: new com/tt/miniapphost/AppbrandContext$2
    //   410: dup
    //   411: invokespecial <init> : ()V
    //   414: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   417: invokevirtual getSettingsRequestDelayTime : ()J
    //   420: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   423: pop
    //   424: invokestatic getInst : ()Lcom/tt/miniapp/manager/basebundle/BaseBundleManager;
    //   427: invokevirtual handleBaseBundleWhenRestart : ()V
    //   430: aload_0
    //   431: invokestatic clearSnapshotDir : (Landroid/content/Context;)V
    //   434: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   437: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   440: invokestatic registerVidUploader : (Landroid/content/Context;)V
    //   443: getstatic com/tt/miniapp/offlinezip/OfflineZipManager.INSTANCE : Lcom/tt/miniapp/offlinezip/OfflineZipManager;
    //   446: aload_0
    //   447: invokevirtual init : (Landroid/content/Context;)V
    //   450: goto -> 479
    //   453: invokestatic initInMiniAppProcess : ()V
    //   456: goto -> 479
    //   459: astore_0
    //   460: ldc 'AppbrandContext'
    //   462: iconst_2
    //   463: anewarray java/lang/Object
    //   466: dup
    //   467: iconst_0
    //   468: ldc_w 'initInMiniAppProcess'
    //   471: aastore
    //   472: dup
    //   473: iconst_1
    //   474: aload_0
    //   475: aastore
    //   476: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   479: invokestatic inst : ()Lcom/tt/miniapphost/AppbrandSupport;
    //   482: invokevirtual setIsInit : ()V
    //   485: iconst_1
    //   486: putstatic com/tt/miniapphost/AppbrandContext.mHasInit : Z
    //   489: ldc com/tt/miniapphost/AppbrandContext
    //   491: monitorexit
    //   492: return
    //   493: astore_0
    //   494: ldc com/tt/miniapphost/AppbrandContext
    //   496: monitorexit
    //   497: aload_0
    //   498: athrow
    // Exception table:
    //   from	to	target	type
    //   3	48	493	finally
    //   57	259	493	finally
    //   263	284	493	finally
    //   287	307	493	finally
    //   312	320	493	finally
    //   320	330	493	finally
    //   330	354	493	finally
    //   359	367	493	finally
    //   367	390	493	finally
    //   394	450	493	finally
    //   453	456	459	java/lang/Exception
    //   453	456	493	finally
    //   460	479	493	finally
    //   479	489	493	finally
  }
  
  public static void preloadInMainProcess(Context paramContext) {
    AppInfoHelper.preload(paramContext);
  }
  
  private static void registerLogger() {
    final AppBrandLogger.ILogger logger = HostDependManager.getInst().createLogger();
    AppBrandLogger.registerLogger(iLogger);
    if (iLogger != null)
      Log.registerLogger(new Log.ILogger() {
            public final void flush() {
              logger.flush();
            }
            
            public final void logD(String param1String1, String param1String2) {
              logger.logD(param1String1, param1String2);
            }
            
            public final void logE(String param1String1, String param1String2) {
              logger.logE(param1String1, param1String2);
            }
            
            public final void logE(String param1String1, String param1String2, Throwable param1Throwable) {
              logger.logE(param1String1, param1String2, param1Throwable);
            }
            
            public final void logI(String param1String1, String param1String2) {
              logger.logI(param1String1, param1String2);
            }
            
            public final void logW(String param1String1, String param1String2) {
              logger.logW(param1String1, param1String2);
            }
          }); 
  }
  
  public static void tryInitAgain(Context paramContext) {
    boolean bool = false;
    try {
    
    } finally {
      Exception exception = null;
      AppBrandLogger.e("AppbrandContext", new Object[] { exception });
    } 
    if (!bool)
      ProcessUtil.killCurrentMiniAppProcess(paramContext); 
  }
  
  public static void tryKillIfNotInit(Context paramContext) {
    if (!AppbrandSupport.inst().isInit() || !mHasInit) {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("context", paramContext.toString());
        jSONObject.put("support init", AppbrandSupport.inst().isInit());
        jSONObject.put("context init", mHasInit);
        jSONObject.put("ProcessName", ProcessUtil.getCurProcessName(paramContext));
        AppBrandMonitor.statusRate("mp_init_error", 1007, jSONObject);
      } catch (JSONException jSONException) {
        AppBrandLogger.e("AppbrandContext", new Object[] { jSONException });
      } 
      try {
        Thread.sleep(200L);
      } catch (InterruptedException interruptedException) {
        AppBrandLogger.e("AppbrandContext", new Object[] { interruptedException });
      } 
      StringBuilder stringBuilder = new StringBuilder("Killing Process: ");
      stringBuilder.append(ProcessUtil.getCurProcessName(paramContext));
      stringBuilder.append("\n");
      stringBuilder.append(Log.getStackTraceString(new Throwable()));
      InnerEventHelper.mpTechnologyMsg(stringBuilder.toString());
      Process.killProcess(Process.myPid());
      System.exit(0);
    } 
  }
  
  public boolean checkProcessCommunicationPermission() {
    Application application = this.mApplicationContext;
    if (application == null)
      return false; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mApplicationContext.getPackageName());
    stringBuilder.append(".miniapp.PROCESS_COMMUNICATION");
    return (application.checkCallingOrSelfPermission(stringBuilder.toString()) == 0);
  }
  
  public Application getApplicationContext() {
    if (this.mApplicationContext == null)
      this.mApplicationContext = AppbrandInit.getInstance().getApplicationContext(); 
    if (this.mApplicationContext == null)
      AppBrandLogger.e("AppbrandContext", new Object[] { "mApplicationContext == null", this }); 
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this.mApplicationContext);
    return this.mApplicationContext;
  }
  
  public IAsyncHostDataHandler getAsyncHandler(String paramString) {
    return TextUtils.isEmpty(paramString) ? null : this.mAsyncHandlerMap.get(paramString);
  }
  
  public SDKBuildConfigEntity getBuildConfig() {
    return this.configEntity;
  }
  
  public MiniappHostBase getCurrentActivity() {
    return this.mMiniAppActivity;
  }
  
  public f.a getExtensionApiCreator() {
    return this.mApiHandlerCreator;
  }
  
  public InitParamsEntity getInitParams() {
    if (this.initParams == null) {
      AppBrandLogger.e("AppbrandContext", new Object[] { "no init params" });
      tryInitAgain((Context)this.mApplicationContext);
    } 
    return this.initParams;
  }
  
  public String getLaunchId() {
    return this.mLaunchId;
  }
  
  public i.a getNativeViewCreator() {
    return this.mNativeViewCreator;
  }
  
  public String getProcessCommunicationPermission() {
    if (this.mApplicationContext == null)
      return null; 
    if (!checkProcessCommunicationPermission())
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mApplicationContext.getPackageName());
    stringBuilder.append(".miniapp.PROCESS_COMMUNICATION");
    return stringBuilder.toString();
  }
  
  public ISyncHostDataHandler getSyncHandler(String paramString) {
    return TextUtils.isEmpty(paramString) ? null : this.mSyncHandlerMap.get(paramString);
  }
  
  public List<Object> getTitleMenuItems() {
    return this.mTitleMenuItem;
  }
  
  public String getUniqueId() {
    return this.sUniqueId;
  }
  
  public boolean isDataHandlerExist(String paramString) {
    return (this.mSyncHandlerMap.containsKey(paramString) || this.mAsyncHandlerMap.containsKey(paramString));
  }
  
  public boolean isGame() {
    return this.mIsGame;
  }
  
  public boolean isInitParamsReady() {
    return (this.initParams != null);
  }
  
  public void onCreate() {
    if (this.mApplicationContext != null || !DebugUtil.debug()) {
      IAppbrandApplication iAppbrandApplication = AppbrandApplication.getInst();
      if (iAppbrandApplication != null)
        iAppbrandApplication.onCreate(); 
      return;
    } 
    throw new IllegalStateException("should call setApplicationContext first");
  }
  
  public void setApplicationContext(Application paramApplication) {
    if (paramApplication != null)
      this.mApplicationContext = paramApplication; 
  }
  
  public void setCurrentActivity(MiniappHostBase paramMiniappHostBase) {
    this.mMiniAppActivity = paramMiniappHostBase;
  }
  
  public void setExtensionApiCreator(f.a parama) {
    this.mApiHandlerCreator = parama;
  }
  
  public void setGame(boolean paramBoolean) {
    this.mIsGame = paramBoolean;
  }
  
  public void setInitParams(InitParamsEntity paramInitParamsEntity) {
    this.initParams = paramInitParamsEntity;
  }
  
  public void setLaunchId(String paramString) {
    this.mLaunchId = paramString;
  }
  
  public void setNativeViewCreator(i.a parama) {
    this.mNativeViewCreator = parama;
  }
  
  public void setTitleMenuItems(List<Object> paramList) {
    this.mTitleMenuItem = paramList;
  }
  
  public void updateHostInjectResources() {
    getInst().setTitleMenuItems(HostDependManager.getInst().createTitleMenuItems());
    i.a a1 = HostDependManager.getInst().createNativeView();
    if (a1 != null)
      getInst().setNativeViewCreator(a1); 
  }
  
  static class Holder {
    static AppbrandContext sInstance = new AppbrandContext();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\AppbrandContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */