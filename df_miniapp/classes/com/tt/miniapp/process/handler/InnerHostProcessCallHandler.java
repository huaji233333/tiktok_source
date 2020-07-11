package com.tt.miniapp.process.handler;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import com.a;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Scheduler;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.audio.background.BgAudioCallExtra;
import com.tt.miniapp.audio.background.BgAudioCommand;
import com.tt.miniapp.audio.background.BgAudioManagerServiceNative;
import com.tt.miniapp.audio.background.BgAudioModel;
import com.tt.miniapp.audio.background.BgAudioPlayStateListener;
import com.tt.miniapp.audio.background.BgAudioState;
import com.tt.miniapp.base.netrequest.RequestManagerV2;
import com.tt.miniapp.entity.AppJumpListManager;
import com.tt.miniapp.event.remedy.InnerMiniProcessLogHelper;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.locate.LocateCrossProcessHandler;
import com.tt.miniapp.manager.HostActivityManager;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.offlinezip.OfflineZipManager;
import com.tt.miniapp.offlinezip.OnOfflineZipCheckUpdateResultListener;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.extra.CrossProcessOperatorScheduler;
import com.tt.miniapp.service.ServiceProvider;
import com.tt.miniapp.service.hostevent.HostEventService;
import com.tt.miniapp.service.hostevent.HostEventServiceInterface;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlemenu.item.FavoriteMiniAppMenuItem;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.AppbrandSupport;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.language.LocaleUtils;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.callback.IpcCallbackManagerProxy;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.StorageUtil;
import com.tt.option.e.e;
import com.tt.option.e.f;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

class InnerHostProcessCallHandler {
  public static void addHostEventListener(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity != null) {
      String str2 = paramCrossProcessDataEntity.getString("host_event_mp_id");
      String str1 = paramCrossProcessDataEntity.getString("host_event_event_name");
      if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str1)) {
        HostEventServiceInterface hostEventServiceInterface = (HostEventServiceInterface)ServiceProvider.getInstance().getService(HostEventService.class);
        if (hostEventServiceInterface != null)
          hostEventServiceInterface.hostProcessAddHostEventListener(str2, str1); 
      } 
    } 
  }
  
  private static void addToFavoriteSet(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null)
      return; 
    String str = paramCrossProcessDataEntity.getString("mini_app_id");
    if (TextUtils.isEmpty(str))
      return; 
    if (FavoriteMiniAppMenuItem.sFavoriteSet == null)
      FavoriteMiniAppMenuItem.sFavoriteSet = new LinkedHashSet(); 
    FavoriteMiniAppMenuItem.sFavoriteSet.add(str);
  }
  
  private static void apiHandler(CrossProcessDataEntity paramCrossProcessDataEntity, final AsyncIpcHandler asyncIpcHandler) {
    if (paramCrossProcessDataEntity == null) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "apiHandler callData == null" });
      return;
    } 
    String str2 = paramCrossProcessDataEntity.getString("apiName");
    String str1 = paramCrossProcessDataEntity.getString("apiData");
    AppBrandLogger.d("InnerHostProcessCallHandler", new Object[] { "handleAsyncCall apiHandler apiName: ", str2, " apiData: ", str1 });
    f.a a = AppbrandContext.getInst().getExtensionApiCreator();
    if (!TextUtils.isEmpty(str2) && a != null) {
      final b handler = a.a(str2, str1, 0, null);
      if (b != null) {
        b.mApiHandlerCallback = new e() {
            public final void callback(int param1Int, String param1String) {
              handler.operateFinishOnGoalProcess(param1String, asyncIpcHandler);
            }
          };
        CrossProcessOperatorScheduler.apiHandlerAct(b);
        return;
      } 
      asyncIpcHandler.callback(null, true);
      AppBrandLogger.e("InnerHostProcessCallHandler", new Object[] { "handleAsyncCall apiHandler cannot create target ApiHandler. apiName:", str2 });
      return;
    } 
    asyncIpcHandler.callback(null, true);
    AppBrandLogger.e("InnerHostProcessCallHandler", new Object[] { "handleAsyncCall TextUtils.isEmpty(apiName) || extApiHandlerCreator== null. apiName:", str2 });
  }
  
  private static CrossProcessDataEntity backApp(CrossProcessDataEntity paramCrossProcessDataEntity) {
    String str1 = paramCrossProcessDataEntity.getString("miniAppId");
    String str2 = paramCrossProcessDataEntity.getString("refererInfo");
    boolean bool = paramCrossProcessDataEntity.getBoolean("isApiCall");
    if (paramCrossProcessDataEntity.getBoolean("is_launch_with_float_style")) {
      Activity activity = HostActivityManager.getHostTopActivity();
      if (activity != null)
        ActivityUtil.moveHostActivityTaskToFront(activity, false); 
    } 
    return CrossProcessDataEntity.Builder.create().put("back_app_result", Boolean.valueOf(AppJumpListManager.backToApp(str1, str2, bool))).build();
  }
  
  private static void callback(CrossProcessDataEntity paramCrossProcessDataEntity1, CrossProcessDataEntity paramCrossProcessDataEntity2) {
    if (paramCrossProcessDataEntity2 == null) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "callback callExtraData == null" });
      return;
    } 
    int i = paramCrossProcessDataEntity2.getInt("callbackId", 0);
    boolean bool = paramCrossProcessDataEntity2.getBoolean("finishCallBack");
    IpcCallbackManagerProxy.getInstance().handleIpcCallBack(i, paramCrossProcessDataEntity1);
    if (bool)
      IpcCallbackManagerProxy.getInstance().unregisterIpcCallback(i); 
  }
  
  private static void checkUpdateOfflineZip(CrossProcessDataEntity paramCrossProcessDataEntity, final AsyncIpcHandler asyncIpcHandler) {
    if (paramCrossProcessDataEntity != null) {
      List list = paramCrossProcessDataEntity.getStringList("offline_zip_module_names");
      if (list != null)
        OfflineZipManager.INSTANCE.checkUpdateOfflineZipInMainProcess((Context)AppbrandContext.getInst().getApplicationContext(), list, new OnOfflineZipCheckUpdateResultListener() {
              public final void onComplete(boolean param1Boolean) {
                asyncIpcHandler.callback((new CrossProcessDataEntity.Builder()).put("offline_zip_update_result", Boolean.valueOf(param1Boolean)).build(), true);
              }
            }); 
    } 
  }
  
  private static CrossProcessDataEntity getCurrentLocale(CrossProcessDataEntity paramCrossProcessDataEntity) {
    String str1;
    Locale locale = LocaleManager.getInst().getCurrentHostSetLocale();
    if (locale != null) {
      str1 = LocaleUtils.locale2String(locale);
    } else {
      str1 = "";
    } 
    String str2 = str1;
    if (TextUtils.isEmpty(str1))
      str2 = ""; 
    StringBuilder stringBuilder = new StringBuilder("getCurrentLocale:");
    stringBuilder.append(str2);
    AppBrandLogger.d("InnerHostProcessCallHandler", new Object[] { stringBuilder.toString() });
    return CrossProcessDataEntity.Builder.create().put("localeLang", str2).build();
  }
  
  private static CrossProcessDataEntity getFavoriteSet() {
    return (FavoriteMiniAppMenuItem.sFavoriteSet == null) ? null : CrossProcessDataEntity.Builder.create().putStringList("favorite_set", new ArrayList(FavoriteMiniAppMenuItem.sFavoriteSet)).build();
  }
  
  private static CrossProcessDataEntity getFavoriteSettings() {
    String str = SettingsDAO.getString((Context)AppbrandContext.getInst().getApplicationContext(), null, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.FAVORITES });
    AppBrandLogger.d("InnerHostProcessCallHandler", new Object[] { "favoritesJsonStr == ", str });
    return CrossProcessDataEntity.Builder.create().put("favorite_settings", str).build();
  }
  
  private static void getLocation(AsyncIpcHandler paramAsyncIpcHandler) {
    LocateCrossProcessHandler.inst((Context)AppbrandContext.getInst().getApplicationContext()).getLocation(paramAsyncIpcHandler);
  }
  
  private static CrossProcessDataEntity getPlatformSession(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "getPlatformSession callData == null" });
      return null;
    } 
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null) {
      AppBrandLogger.e("InnerHostProcessCallHandler", new Object[] { "getPlatformSession context == null" });
      return null;
    } 
    String str = paramCrossProcessDataEntity.getString("miniAppId");
    if (TextUtils.isEmpty(str)) {
      AppBrandLogger.e("InnerHostProcessCallHandler", new Object[] { "getPlatformSession appId is empty" });
      return null;
    } 
    return CrossProcessDataEntity.Builder.create().put("platformSession", StorageUtil.getSessionByAppid((Context)application, str)).build();
  }
  
  private static void getSnapShot(CrossProcessDataEntity paramCrossProcessDataEntity, AsyncIpcHandler paramAsyncIpcHandler) {
    // Byte code:
    //   0: invokestatic currentTimeMillis : ()J
    //   3: lstore_2
    //   4: invokestatic getHostTopActivity : ()Landroid/app/Activity;
    //   7: astore #7
    //   9: aconst_null
    //   10: astore #6
    //   12: aload_0
    //   13: ifnull -> 185
    //   16: aload_0
    //   17: ldc_w 'forceGetHostActivitySnapshot'
    //   20: invokevirtual getBoolean : (Ljava/lang/String;)Z
    //   23: istore #5
    //   25: aload_0
    //   26: ldc 'miniAppId'
    //   28: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   31: astore_0
    //   32: aload_0
    //   33: invokestatic getProcessInfoByAppId : (Ljava/lang/String;)Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   36: astore #8
    //   38: aload #8
    //   40: ifnull -> 57
    //   43: aload #8
    //   45: invokevirtual isLaunchActivityInHostStack : ()Z
    //   48: ifeq -> 57
    //   51: iconst_1
    //   52: istore #4
    //   54: goto -> 60
    //   57: iconst_0
    //   58: istore #4
    //   60: ldc 'InnerHostProcessCallHandler'
    //   62: bipush #6
    //   64: anewarray java/lang/Object
    //   67: dup
    //   68: iconst_0
    //   69: ldc_w 'request getSnapShot appId:'
    //   72: aastore
    //   73: dup
    //   74: iconst_1
    //   75: aload_0
    //   76: aastore
    //   77: dup
    //   78: iconst_2
    //   79: ldc_w 'forceGetHostActivitySnapshot:'
    //   82: aastore
    //   83: dup
    //   84: iconst_3
    //   85: iload #5
    //   87: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   90: aastore
    //   91: dup
    //   92: iconst_4
    //   93: ldc_w 'isMiniAppLaunchInHostStack:'
    //   96: aastore
    //   97: dup
    //   98: iconst_5
    //   99: iload #4
    //   101: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   104: aastore
    //   105: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   108: iload #5
    //   110: ifeq -> 125
    //   113: iload #4
    //   115: ifne -> 125
    //   118: invokestatic getLatestUsingHostStackProcessInfo : ()Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   121: astore_0
    //   122: goto -> 154
    //   125: aload_0
    //   126: invokestatic getPreAppId : (Ljava/lang/String;)Ljava/lang/String;
    //   129: astore_0
    //   130: ldc 'InnerHostProcessCallHandler'
    //   132: iconst_2
    //   133: anewarray java/lang/Object
    //   136: dup
    //   137: iconst_0
    //   138: ldc_w 'getSnapShot preAppId:'
    //   141: aastore
    //   142: dup
    //   143: iconst_1
    //   144: aload_0
    //   145: aastore
    //   146: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   149: aload_0
    //   150: invokestatic getProcessInfoByAppId : (Ljava/lang/String;)Lcom/tt/miniapp/process/AppProcessManager$ProcessInfo;
    //   153: astore_0
    //   154: ldc 'InnerHostProcessCallHandler'
    //   156: iconst_2
    //   157: anewarray java/lang/Object
    //   160: dup
    //   161: iconst_0
    //   162: ldc_w 'getSnapShot snapshotMiniAppProcessInfo:'
    //   165: aastore
    //   166: dup
    //   167: iconst_1
    //   168: aload_0
    //   169: aastore
    //   170: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   173: aload_0
    //   174: ifnull -> 185
    //   177: aload_0
    //   178: getfield mProcessIdentity : Ljava/lang/String;
    //   181: astore_0
    //   182: goto -> 187
    //   185: aconst_null
    //   186: astore_0
    //   187: aload_0
    //   188: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   191: ifeq -> 288
    //   194: ldc 'InnerHostProcessCallHandler'
    //   196: iconst_2
    //   197: anewarray java/lang/Object
    //   200: dup
    //   201: iconst_0
    //   202: ldc_w 'getHostActivitySnapShot activity:'
    //   205: aastore
    //   206: dup
    //   207: iconst_1
    //   208: aload #7
    //   210: aastore
    //   211: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   214: aload #7
    //   216: aload #7
    //   218: invokestatic getSnapshot : (Landroid/app/Activity;)Landroid/graphics/Bitmap;
    //   221: invokestatic compressSnapshot : (Landroid/graphics/Bitmap;)[B
    //   224: invokestatic saveSnapshotFile : (Landroid/app/Activity;[B)Ljava/io/File;
    //   227: astore #7
    //   229: aload #6
    //   231: astore_0
    //   232: aload #7
    //   234: ifnull -> 255
    //   237: invokestatic create : ()Lcom/tt/miniapphost/process/data/CrossProcessDataEntity$Builder;
    //   240: ldc_w 'snapshot'
    //   243: aload #7
    //   245: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   248: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lcom/tt/miniapphost/process/data/CrossProcessDataEntity$Builder;
    //   251: invokevirtual build : ()Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;
    //   254: astore_0
    //   255: aload_1
    //   256: aload_0
    //   257: iconst_1
    //   258: invokevirtual callback : (Lcom/tt/miniapphost/process/data/CrossProcessDataEntity;Z)V
    //   261: ldc 'InnerHostProcessCallHandler'
    //   263: iconst_2
    //   264: anewarray java/lang/Object
    //   267: dup
    //   268: iconst_0
    //   269: ldc_w '获取主进程页面截图 duration:'
    //   272: aastore
    //   273: dup
    //   274: iconst_1
    //   275: invokestatic currentTimeMillis : ()J
    //   278: lload_2
    //   279: lsub
    //   280: invokestatic valueOf : (J)Ljava/lang/Long;
    //   283: aastore
    //   284: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   287: return
    //   288: ldc 'InnerHostProcessCallHandler'
    //   290: iconst_2
    //   291: anewarray java/lang/Object
    //   294: dup
    //   295: iconst_0
    //   296: ldc_w 'getMiniAppSnapShot preAppProcessIdentify:'
    //   299: aastore
    //   300: dup
    //   301: iconst_1
    //   302: aload_0
    //   303: aastore
    //   304: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   307: aload_0
    //   308: new com/tt/miniapp/process/handler/InnerHostProcessCallHandler$6
    //   311: dup
    //   312: aload_1
    //   313: lload_2
    //   314: invokespecial <init> : (Lcom/tt/miniapphost/process/helper/AsyncIpcHandler;J)V
    //   317: invokestatic getSnapshot : (Ljava/lang/String;Lcom/tt/miniapphost/process/callback/IpcCallback;)V
    //   320: return
  }
  
  public static CrossProcessDataEntity getSpData(CrossProcessDataEntity paramCrossProcessDataEntity) {
    CrossProcessDataEntity crossProcessDataEntity = null;
    if (paramCrossProcessDataEntity != null) {
      String str1;
      CrossProcessDataEntity.Builder builder = new CrossProcessDataEntity.Builder();
      String str4 = paramCrossProcessDataEntity.getString("sp_data_file");
      String str5 = paramCrossProcessDataEntity.getString("sp_data_key");
      String str3 = paramCrossProcessDataEntity.getString("sp_data_default");
      paramCrossProcessDataEntity = crossProcessDataEntity;
      if (!TextUtils.isEmpty(str5)) {
        paramCrossProcessDataEntity = crossProcessDataEntity;
        if (str4 != null)
          str1 = KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), str4).getString(str5, str3); 
      } 
      String str2 = str1;
      if (TextUtils.isEmpty(str1)) {
        str1 = str3;
        if (str3 == null)
          str1 = ""; 
        str2 = str1;
      } 
      builder.put("sp_data_value", str2);
      return builder.build();
    } 
    return null;
  }
  
  static boolean handleAsyncCall(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity1, CrossProcessDataEntity paramCrossProcessDataEntity2, AsyncIpcHandler paramAsyncIpcHandler) {
    byte b;
    AppBrandLogger.i("InnerHostProcessCallHandler", new Object[] { "beforeHandleAsyncCall callType:", paramString });
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 1915939293:
        if (paramString.equals("hostProcess_callback")) {
          b = 4;
          break;
        } 
      case 1707634780:
        if (paramString.equals("updateBaseBundle")) {
          b = 3;
          break;
        } 
      case 820504303:
        if (paramString.equals("registerBgAudioPlayState")) {
          b = 6;
          break;
        } 
      case -316023509:
        if (paramString.equals("getLocation")) {
          b = 5;
          break;
        } 
      case -600628793:
        if (paramString.equals("scheduleApiHandler")) {
          b = 0;
          break;
        } 
      case -806813950:
        if (paramString.equals("notifyPreloadEmptyProcess")) {
          b = 8;
          break;
        } 
      case -953620209:
        if (paramString.equals("checkUpdateOfflineZip")) {
          b = 9;
          break;
        } 
      case -1405248603:
        if (paramString.equals("setTmaLaunchFlag")) {
          b = 1;
          break;
        } 
      case -1614149786:
        if (paramString.equals("savePlatformSession")) {
          b = 2;
          break;
        } 
      case -1932192966:
        if (paramString.equals("getSnapshot")) {
          b = 7;
          break;
        } 
    } 
    switch (b) {
      default:
        bool = false;
        AppBrandLogger.i("InnerHostProcessCallHandler", new Object[] { "afterHandleAsyncCall callType:", paramString, "handled:", Boolean.valueOf(bool) });
        return bool;
      case 9:
        checkUpdateOfflineZip(paramCrossProcessDataEntity1, paramAsyncIpcHandler);
        break;
      case 8:
        preloadEmptyProcess();
        break;
      case 7:
        getSnapShot(paramCrossProcessDataEntity1, paramAsyncIpcHandler);
        break;
      case 6:
        registerBgAudioPlayState(paramCrossProcessDataEntity1, paramAsyncIpcHandler);
        break;
      case 5:
        getLocation(paramAsyncIpcHandler);
        break;
      case 4:
        callback(paramCrossProcessDataEntity1, paramCrossProcessDataEntity2);
        break;
      case 3:
        updateBaseBundle();
        break;
      case 2:
        savePlatformSession(paramCrossProcessDataEntity1);
        break;
      case 1:
        setTmaLaunchFlag(paramCrossProcessDataEntity1);
        break;
      case 0:
        apiHandler(paramCrossProcessDataEntity1, paramAsyncIpcHandler);
        break;
    } 
    boolean bool = true;
    AppBrandLogger.i("InnerHostProcessCallHandler", new Object[] { "afterHandleAsyncCall callType:", paramString, "handled:", Boolean.valueOf(bool) });
    return bool;
  }
  
  static boolean handleSyncCall(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity1, CrossProcessDataEntity paramCrossProcessDataEntity2, CrossProcessDataEntity[] paramArrayOfCrossProcessDataEntity) {
    byte b;
    AppBrandLogger.i("InnerHostProcessCallHandler", new Object[] { "beforeHandleSyncCall callType:", paramString });
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 2121261769:
        if (paramString.equals("back_app")) {
          b = 3;
          break;
        } 
      case 2034582128:
        if (paramString.equals("type_update_favorite_set")) {
          b = 15;
          break;
        } 
      case 1301584782:
        if (paramString.equals("jump_to_app")) {
          b = 1;
          break;
        } 
      case 1084566112:
        if (paramString.equals("type_add_to_favorite_set")) {
          b = 13;
          break;
        } 
      case 1078329351:
        if (paramString.equals("notify_mini_app_process_crash")) {
          b = 8;
          break;
        } 
      case 1064884100:
        if (paramString.equals("saveSpData")) {
          b = 16;
          break;
        } 
      case 803973105:
        if (paramString.equals("restart_app")) {
          b = 0;
          break;
        } 
      case 798957213:
        if (paramString.equals("getSpData")) {
          b = 17;
          break;
        } 
      case 638061061:
        if (paramString.equals("addHostEventListener")) {
          b = 19;
          break;
        } 
      case 399449253:
        if (paramString.equals("jump_to_app_from_schema")) {
          b = 2;
          break;
        } 
      case 383221365:
        if (paramString.equals("mini_process_used")) {
          b = 7;
          break;
        } 
      case 213806617:
        if (paramString.equals("update_jump_list")) {
          b = 4;
          break;
        } 
      case 198277422:
        if (paramString.equals("actionLog")) {
          b = 9;
          break;
        } 
      case -39279151:
        if (paramString.equals("getCurrentLang")) {
          b = 21;
          break;
        } 
      case -370283307:
        if (paramString.equals("httpRequestWithCommonParam")) {
          b = 22;
          break;
        } 
      case -488436872:
        if (paramString.equals("type_get_favorite_settings")) {
          b = 11;
          break;
        } 
      case -674684898:
        if (paramString.equals("type_remove_from_favorite_set")) {
          b = 14;
          break;
        } 
      case -676828179:
        if (paramString.equals("type_get_favorite_set")) {
          b = 12;
          break;
        } 
      case -858493621:
        if (paramString.equals("removeSpData")) {
          b = 18;
          break;
        } 
      case -965406614:
        if (paramString.equals("is_in_jump_list")) {
          b = 5;
          break;
        } 
      case -991883533:
        if (paramString.equals("type_bg_audio_sync_commond")) {
          b = 10;
          break;
        } 
      case -1276138387:
        if (paramString.equals("getPlatformSession")) {
          b = 6;
          break;
        } 
      case -2056786814:
        if (paramString.equals("removeHostEventListener")) {
          b = 20;
          break;
        } 
    } 
    switch (b) {
      default:
        bool = false;
        AppBrandLogger.i("InnerHostProcessCallHandler", new Object[] { "afterHandleSyncCall callType:", paramString, "handled:", Boolean.valueOf(bool) });
        return bool;
      case 22:
        paramArrayOfCrossProcessDataEntity[0] = httpRequestWithCommonParam(paramCrossProcessDataEntity1);
        break;
      case 21:
        paramArrayOfCrossProcessDataEntity[0] = getCurrentLocale(paramCrossProcessDataEntity1);
        break;
      case 20:
        removeHostEventListener(paramCrossProcessDataEntity1);
        break;
      case 19:
        addHostEventListener(paramCrossProcessDataEntity1);
        break;
      case 18:
        removeSpData(paramCrossProcessDataEntity1);
        break;
      case 17:
        paramArrayOfCrossProcessDataEntity[0] = getSpData(paramCrossProcessDataEntity1);
        break;
      case 16:
        saveSpData(paramCrossProcessDataEntity1);
        break;
      case 15:
        updateFavoriteSet(paramCrossProcessDataEntity1);
        break;
      case 14:
        removeFromFavoriteSet(paramCrossProcessDataEntity1);
        break;
      case 13:
        addToFavoriteSet(paramCrossProcessDataEntity1);
        break;
      case 12:
        paramArrayOfCrossProcessDataEntity[0] = getFavoriteSet();
        break;
      case 11:
        paramArrayOfCrossProcessDataEntity[0] = getFavoriteSettings();
        break;
      case 10:
        paramArrayOfCrossProcessDataEntity[0] = innerDealBgAudioCommond(paramCrossProcessDataEntity1);
        break;
      case 9:
        bool = innerPreHandleEventLog(paramCrossProcessDataEntity1);
        AppBrandLogger.i("InnerHostProcessCallHandler", new Object[] { "afterHandleSyncCall callType:", paramString, "handled:", Boolean.valueOf(bool) });
        return bool;
      case 8:
        notifyMiniAppProcessCrash(paramCrossProcessDataEntity1);
        break;
      case 7:
        initMiniProcessMonitor(paramCrossProcessDataEntity1);
        break;
      case 6:
        paramArrayOfCrossProcessDataEntity[0] = getPlatformSession(paramCrossProcessDataEntity1);
        break;
      case 5:
        paramArrayOfCrossProcessDataEntity[0] = isInJumplist(paramCrossProcessDataEntity1);
        break;
      case 4:
        updateJumpList(paramCrossProcessDataEntity1);
        break;
      case 3:
        paramArrayOfCrossProcessDataEntity[0] = backApp(paramCrossProcessDataEntity1);
        break;
      case 2:
        jumpToAppFromOpenSchema(paramCrossProcessDataEntity1);
        break;
      case 1:
        jumpToApp(paramCrossProcessDataEntity1);
        break;
      case 0:
        restartApp(paramCrossProcessDataEntity1);
        break;
    } 
    boolean bool = true;
    AppBrandLogger.i("InnerHostProcessCallHandler", new Object[] { "afterHandleSyncCall callType:", paramString, "handled:", Boolean.valueOf(bool) });
    return bool;
  }
  
  private static CrossProcessDataEntity httpRequestWithCommonParam(CrossProcessDataEntity paramCrossProcessDataEntity) {
    HttpRequest.RequestResult requestResult;
    HttpRequest.RequestTask requestTask = (HttpRequest.RequestTask)paramCrossProcessDataEntity.getParcelable("httpRequestTask");
    try {
      requestResult = RequestManagerV2.getInst().requestSync("ttnet", requestTask);
    } finally {
      Exception exception = null;
      AppBrandLogger.e("InnerHostProcessCallHandler", new Object[] { "httpRequestWithCommonParam", exception });
      requestResult = new HttpRequest.RequestResult(requestTask.a);
      requestResult.b = false;
    } 
    return CrossProcessDataEntity.Builder.create().putParcelable("httpRequestResult", (Parcelable)requestResult).build();
  }
  
  private static void initMiniProcessMonitor(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null)
      return; 
    AppProcessManager.ProcessInfo processInfo = AppProcessManager.getProcessInfoByProcessName(paramCrossProcessDataEntity.getString("processName"));
    if (processInfo != null)
      AppProcessManager.startMiniProcessMonitor((Context)AppbrandContext.getInst().getApplicationContext(), processInfo, false); 
  }
  
  private static CrossProcessDataEntity innerDealBgAudioCommond(CrossProcessDataEntity paramCrossProcessDataEntity) {
    BgAudioState bgAudioState1;
    String str2;
    BgAudioModel bgAudioModel;
    String str1;
    CrossProcessDataEntity.Builder builder;
    BgAudioState bgAudioState2 = null;
    if (paramCrossProcessDataEntity == null)
      return null; 
    int i = paramCrossProcessDataEntity.getInt("bgAudioId");
    BgAudioCommand bgAudioCommand = BgAudioCommand.fromString(paramCrossProcessDataEntity.getString("bgAudioCommondType"));
    switch (bgAudioCommand) {
      default:
        return null;
      case null:
        return CrossProcessDataEntity.Builder.create().put("bgAudioCommandRetNeedKeepAlive", Boolean.valueOf(BgAudioManagerServiceNative.getInst().needKeepAlive(i))).build();
      case GET_AUDIO_STATE:
        bgAudioState1 = BgAudioManagerServiceNative.getInst().getAudioState(i);
        builder = CrossProcessDataEntity.Builder.create();
        if (bgAudioState1 == null) {
          bgAudioState1 = bgAudioState2;
        } else {
          str2 = bgAudioState1.toJSONStr();
        } 
        return builder.put("bgAudioCommondRetState", str2).build();
      case SET_AUDIO_MODEL:
        bgAudioModel = BgAudioModel.parseFromJSONStr(str2.getString("bgAudioCommondInfo"));
        if (bgAudioModel != null) {
          BgAudioManagerServiceNative.getInst().setAudioModel(i, bgAudioModel);
          return null;
        } 
        return null;
      case SEEK:
        str1 = bgAudioModel.getString("bgAudioCommondInfo");
        if (!TextUtils.isEmpty(str1)) {
          BgAudioManagerServiceNative.getInst().seek(i, Integer.valueOf(str1).intValue());
          return null;
        } 
        return null;
      case STOP:
        BgAudioManagerServiceNative.getInst().stop(i);
        return null;
      case PAUSE:
        BgAudioManagerServiceNative.getInst().pause(i);
        return null;
      case PLAY:
        BgAudioManagerServiceNative.getInst().play(i);
        return null;
      case OBTAIN_MANAGER:
        break;
    } 
    BgAudioCallExtra bgAudioCallExtra = BgAudioCallExtra.parseFromJSONStr(str1.getString("bgAudioCommondInfo"));
    i = BgAudioManagerServiceNative.getInst().obtainManager(i, bgAudioCallExtra);
    return CrossProcessDataEntity.Builder.create().put("bgAudioId", Integer.valueOf(i)).build();
  }
  
  private static boolean innerPreHandleEventLog(CrossProcessDataEntity paramCrossProcessDataEntity) {
    return (paramCrossProcessDataEntity == null) ? false : InnerMiniProcessLogHelper.innerHandleEventLog(paramCrossProcessDataEntity.getString("logEventName"), paramCrossProcessDataEntity.getJSONObject("logEventData"));
  }
  
  private static CrossProcessDataEntity isInJumplist(CrossProcessDataEntity paramCrossProcessDataEntity) {
    String str = paramCrossProcessDataEntity.getString("miniAppId");
    return CrossProcessDataEntity.Builder.create().put("is_in_jumplist", Boolean.valueOf(AppJumpListManager.isInAppJumpList(str))).build();
  }
  
  private static void jumpToApp(CrossProcessDataEntity paramCrossProcessDataEntity) {
    String str1 = paramCrossProcessDataEntity.getString("miniAppToId");
    String str2 = paramCrossProcessDataEntity.getString("miniAppFromId");
    String str3 = paramCrossProcessDataEntity.getString("startPage");
    String str4 = paramCrossProcessDataEntity.getString("query");
    String str5 = paramCrossProcessDataEntity.getString("refererInfo");
    String str6 = paramCrossProcessDataEntity.getString("version_type");
    int i = paramCrossProcessDataEntity.getInt("miniAppOrientation");
    AppJumpListManager.jumpToApp(str1, str3, str4, str5, paramCrossProcessDataEntity.getBoolean("isGame"), str6, str2, i, paramCrossProcessDataEntity.getString("miniAppOriginEntrance"));
  }
  
  private static void jumpToAppFromOpenSchema(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null)
      return; 
    AppJumpListManager.jumpToApp(paramCrossProcessDataEntity.getString("schema"), paramCrossProcessDataEntity.getString("miniAppFromId"), paramCrossProcessDataEntity.getBoolean("isGame"));
  }
  
  private static void notifyMiniAppProcessCrash(final CrossProcessDataEntity callData) {
    if (callData == null)
      return; 
    if (ChannelUtil.isLocalTest()) {
      final Activity hostProcessTopActivity = HostActivityManager.getHostTopActivity();
      if (activity == null || activity.isFinishing()) {
        HostDependManager.getInst().showToast((Context)AppbrandContext.getInst().getApplicationContext(), null, a.a("小程序进程 %s 出现 crash：%s", new Object[] { callData.getString("processName"), callData.getString("exceptionMessage") }), 0L, null);
        return;
      } 
      ThreadUtil.runOnUIThread(new Runnable() {
            public final void run() {
              HostDependManager hostDependManager = HostDependManager.getInst();
              Activity activity = hostProcessTopActivity;
              StringBuilder stringBuilder = new StringBuilder("此消息仅在 local_test 渠道下展示。\nCrash信息：\n");
              stringBuilder.append(callData.getString("exceptionMessage"));
              hostDependManager.showModal(activity, null, "小程序进程 Crash", stringBuilder.toString(), false, null, null, "确定", null, new NativeModule.NativeModuleCallback<Integer>() {
                    public void onNativeModuleCall(Integer param2Integer) {}
                  });
            }
          });
      return;
    } 
  }
  
  private static void preloadEmptyProcess() {
    AppbrandConstants.getProcessManager().preloadEmptyProcess(true);
  }
  
  private static void registerBgAudioPlayState(CrossProcessDataEntity paramCrossProcessDataEntity, final AsyncIpcHandler asyncIpcHandler) {
    if (paramCrossProcessDataEntity == null) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "registerBgAudioPlayState callData == null" });
      return;
    } 
    final int audioId = paramCrossProcessDataEntity.getInt("bgAudioId");
    if (i < 0)
      return; 
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            BgAudioManagerServiceNative.getInst().register(audioId, new BgAudioPlayStateListener() {
                  public void onBgPlayProcessDied(BgAudioModel param2BgAudioModel, boolean param2Boolean) {}
                  
                  public void onPlayStateChange(String param2String, BgAudioModel param2BgAudioModel, boolean param2Boolean) {
                    asyncIpcHandler.callback(CrossProcessDataEntity.Builder.create().put("bgAudioPlayState", param2String).build(), false);
                  }
                  
                  public void onProgressChange(int param2Int, BgAudioModel param2BgAudioModel) {}
                  
                  public void onTriggerPlay(BgAudioModel param2BgAudioModel) {}
                });
          }
        }ThreadPools.defaults());
  }
  
  private static void removeFromFavoriteSet(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null)
      return; 
    String str = paramCrossProcessDataEntity.getString("mini_app_id");
    if (TextUtils.isEmpty(str))
      return; 
    if (FavoriteMiniAppMenuItem.sFavoriteSet == null)
      return; 
    Iterator<String> iterator = FavoriteMiniAppMenuItem.sFavoriteSet.iterator();
    while (iterator.hasNext()) {
      if (((String)iterator.next()).contentEquals(str)) {
        iterator.remove();
        break;
      } 
    } 
  }
  
  public static void removeHostEventListener(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity != null) {
      String str2 = paramCrossProcessDataEntity.getString("host_event_mp_id");
      String str1 = paramCrossProcessDataEntity.getString("host_event_event_name");
      if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str1)) {
        HostEventServiceInterface hostEventServiceInterface = (HostEventServiceInterface)ServiceProvider.getInstance().getService(HostEventService.class);
        if (hostEventServiceInterface != null)
          hostEventServiceInterface.hostProcessRemoveHostEventListener(str2, str1); 
      } 
    } 
  }
  
  public static void removeSpData(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity != null) {
      String str2 = paramCrossProcessDataEntity.getString("sp_data_key");
      String str1 = paramCrossProcessDataEntity.getString("sp_data_file");
      if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str1))
        KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), str1).edit().remove(str2).apply(); 
    } 
  }
  
  private static void restartApp(final CrossProcessDataEntity callData) {
    if (callData == null) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "restartApp callData == null" });
      return;
    } 
    Observable.create(new Action() {
          public final void act() {
            AppbrandContext.getInst().getApplicationContext();
            String str2 = callData.getString("miniAppId");
            String str1 = callData.getString("miniAppSchema");
            if (!TextUtils.isEmpty(str2))
              AppProcessManager.killProcess(str2); 
            try {
              Thread.sleep(500L);
            } catch (InterruptedException interruptedException) {
              AppBrandLogger.stacktrace(6, "InnerHostProcessCallHandler", interruptedException.getStackTrace());
            } 
            if (!TextUtils.isEmpty(str1))
              AppbrandSupport.inst().openAppbrand(str1); 
          }
        }).schudleOn((Scheduler)LaunchThreadPool.getInst()).subscribeSimple();
  }
  
  private static void savePlatformSession(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "savePlatformSession callData == null" });
      return;
    } 
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null) {
      AppBrandLogger.e("InnerHostProcessCallHandler", new Object[] { "savePlatformSession context == null" });
      return;
    } 
    String str2 = paramCrossProcessDataEntity.getString("miniAppId");
    String str1 = paramCrossProcessDataEntity.getString("platformSession");
    StringBuilder stringBuilder = new StringBuilder("appId ");
    stringBuilder.append(str2);
    stringBuilder.append(" platformSession ");
    stringBuilder.append(str1);
    AppBrandLogger.d("InnerHostProcessCallHandler", new Object[] { stringBuilder.toString() });
    if (!TextUtils.isEmpty(str2)) {
      if (TextUtils.isEmpty(str1))
        return; 
      StorageUtil.saveSession((Context)application, str2, str1);
    } 
  }
  
  public static void saveSpData(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity != null) {
      String str2 = paramCrossProcessDataEntity.getString("sp_data_file");
      String str3 = paramCrossProcessDataEntity.getString("sp_data_key");
      String str1 = paramCrossProcessDataEntity.getString("sp_data_value");
      if (!TextUtils.isEmpty(str3) && str2 != null)
        KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), str2).edit().putString(str3, str1).apply(); 
    } 
  }
  
  private static void setTmaLaunchFlag(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "setTmaLaunchFlag callData == null" });
      return;
    } 
    final String processName = paramCrossProcessDataEntity.getString("processName");
    final String appId = paramCrossProcessDataEntity.getString("miniAppId");
    final String versionType = paramCrossProcessDataEntity.getString("miniAppVersionType");
    AppBrandLogger.d("InnerHostProcessCallHandler", new Object[] { "handleAsyncCall setTmaLaunchFlag processName:", str2, "appId:", str3, "versionType:", str1 });
    if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
      DebugUtil.outputError("InnerHostProcessCallHandler", new Object[] { "TextUtils.isEmpty(processName) || TextUtils.isEmpty(appId)" });
      return;
    } 
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            AppProcessManager.updateAppProcessInfo(processName, appId, versionType);
            Application application = AppbrandContext.getInst().getApplicationContext();
            LaunchCacheDAO.INSTANCE.increaseNormalLaunchCounter((Context)application, appId);
          }
        }Schedulers.shortIO());
  }
  
  private static void updateBaseBundle() {
    AppbrandConstants.getBundleManager().checkUpdateBaseBundle((Context)AppbrandContext.getInst().getApplicationContext());
  }
  
  private static void updateFavoriteSet(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null) {
      FavoriteMiniAppMenuItem.sFavoriteSet = null;
      return;
    } 
    List<?> list = paramCrossProcessDataEntity.getStringList("favorite_set");
    if (list == null) {
      FavoriteMiniAppMenuItem.sFavoriteSet = null;
      return;
    } 
    FavoriteMiniAppMenuItem.sFavoriteSet = new LinkedHashSet(list);
  }
  
  private static void updateJumpList(CrossProcessDataEntity paramCrossProcessDataEntity) {
    AppJumpListManager.updateJumpList(paramCrossProcessDataEntity.getString("miniAppId"), paramCrossProcessDataEntity.getBoolean("isGame"), paramCrossProcessDataEntity.getBoolean("isSpecial"));
    AppJumpListManager.killPreAppIfNeed();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\handler\InnerHostProcessCallHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */