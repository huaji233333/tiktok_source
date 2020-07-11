package com.tt.miniapp.process.handler;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.launchcache.meta.AppInfoRequestResult;
import com.tt.miniapp.launchcache.meta.MetaService;
import com.tt.miniapp.launchschedule.LaunchScheduler;
import com.tt.miniapp.manager.PreTTRequestManager;
import com.tt.miniapp.manager.SnapshotManager;
import com.tt.miniapp.service.ServiceProvider;
import com.tt.miniapp.service.hostevent.HostEventMiniAppService;
import com.tt.miniapp.service.hostevent.HostEventMiniAppServiceInterface;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.language.LocaleUtils;
import com.tt.miniapphost.process.callback.IpcCallbackManagerProxy;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.helper.AsyncIpcHandler;
import com.tt.miniapphost.util.DebugUtil;
import java.io.File;
import java.util.Locale;
import org.json.JSONObject;

class InnerMiniAppProcessCallHandler {
  private static void appInfoPrefetched(CrossProcessDataEntity paramCrossProcessDataEntity) {
    AppInfoRequestResult appInfoRequestResult = (AppInfoRequestResult)paramCrossProcessDataEntity.getParcelable("prefetched_appinfo");
    if (appInfoRequestResult != null)
      ((MetaService)AppbrandApplicationImpl.getInst().getService(MetaService.class)).appInfoRequestResultAvailable(appInfoRequestResult); 
  }
  
  private static void callback(final CrossProcessDataEntity callData, CrossProcessDataEntity paramCrossProcessDataEntity2) {
    if (paramCrossProcessDataEntity2 == null) {
      DebugUtil.outputError("InnerMiniAppProcessCallHandler", new Object[] { "callback callExtraData == null" });
      return;
    } 
    ThreadUtil.runOnUIThread(new Runnable() {
          public final void run() {
            IpcCallbackManagerProxy.getInstance().handleIpcCallBack(callbackId, callData);
            if (finishCallBack)
              IpcCallbackManagerProxy.getInstance().unregisterIpcCallback(callbackId); 
          }
        });
  }
  
  public static void changeLanguageTo(CrossProcessDataEntity paramCrossProcessDataEntity) {
    AppBrandLogger.d("InnerMiniAppProcessCallHandler", new Object[] { "changeLanguageTo" });
    String str = paramCrossProcessDataEntity.getString("localeLang");
    StringBuilder stringBuilder = new StringBuilder("changeLanguageTo ");
    stringBuilder.append(str);
    AppBrandLogger.d("InnerMiniAppProcessCallHandler", new Object[] { stringBuilder.toString() });
    ThreadUtil.runOnUIThread(new Runnable(LocaleUtils.string2Locale(str)) {
          public final void run() {
            LocaleManager.getInst().notifyLangChange(locale);
          }
        });
  }
  
  private static void getSnapShot(final AsyncIpcHandler asyncIpcHandler) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            long l = System.currentTimeMillis();
            MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
            File file = SnapshotManager.saveSnapshotFile((Activity)miniappHostBase, SnapshotManager.compressSnapshot(SnapshotManager.getSnapshot((Activity)miniappHostBase)));
            if (file != null) {
              CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().put("snapshot", file.getAbsolutePath()).build();
            } else {
              file = null;
            } 
            asyncIpcHandler.callback((CrossProcessDataEntity)file, true);
            AppBrandLogger.d("InnerMiniAppProcessCallHandler", new Object[] { "getSnapShot duration:", Long.valueOf(System.currentTimeMillis() - l) });
          }
        }(Scheduler)LaunchThreadPool.getInst(), true);
  }
  
  static boolean handleAsyncCall(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity1, CrossProcessDataEntity paramCrossProcessDataEntity2, AsyncIpcHandler paramAsyncIpcHandler) {
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 1849852240:
        if (paramString.equals("savePermissionGrant")) {
          b = 2;
          break;
        } 
      case 1042549078:
        if (paramString.equals("notifyUpdateSnapshot")) {
          b = 4;
          break;
        } 
      case 991034266:
        if (paramString.equals("prepareLaunch")) {
          b = 7;
          break;
        } 
      case 882058405:
        if (paramString.equals("appInfoPrefetched")) {
          b = 5;
          break;
        } 
      case 360924619:
        if (paramString.equals("syncTTRequestPrefetch")) {
          b = 6;
          break;
        } 
      case 42687103:
        if (paramString.equals("miniAppProcess_callback")) {
          b = 0;
          break;
        } 
      case -821813039:
        if (paramString.equals("notifyLanguageChange")) {
          b = 3;
          break;
        } 
      case -1844080713:
        if (paramString.equals("dispatchHostEventToMiniApp")) {
          b = 8;
          break;
        } 
      case -1932192966:
        if (paramString.equals("getSnapshot")) {
          b = 1;
          break;
        } 
    } 
    switch (b) {
      default:
        return false;
      case 8:
        receiveHostEventInMiniApp(paramCrossProcessDataEntity1);
        return true;
      case 7:
        prepareLaunch(paramCrossProcessDataEntity1);
        return true;
      case 6:
        syncTTRequestPrefetchInfo(paramCrossProcessDataEntity1);
        return true;
      case 5:
        appInfoPrefetched(paramCrossProcessDataEntity1);
        return true;
      case 4:
        notifyUpdateSnapshot();
        return true;
      case 3:
        changeLanguageTo(paramCrossProcessDataEntity1);
        return true;
      case 2:
        savePermissionGrant(paramCrossProcessDataEntity1);
        return true;
      case 1:
        getSnapShot(paramAsyncIpcHandler);
        return true;
      case 0:
        break;
    } 
    callback(paramCrossProcessDataEntity1, paramCrossProcessDataEntity2);
    return true;
  }
  
  private static void notifyUpdateSnapshot() {
    AppBrandLogger.d("InnerMiniAppProcessCallHandler", new Object[] { "notifyUpdateSnapshot" });
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null)
      miniappHostBase.notifyUpdateSnapShot(); 
  }
  
  private static void prepareLaunch(CrossProcessDataEntity paramCrossProcessDataEntity) {
    AppInfoEntity appInfoEntity = (AppInfoEntity)paramCrossProcessDataEntity.getParcelable("appinfo");
    String str1 = paramCrossProcessDataEntity.getString("schema");
    String str2 = paramCrossProcessDataEntity.getString("pre_handle_vdom");
    Bundle bundle = (Bundle)paramCrossProcessDataEntity.getParcelable("launch_extra_bundle");
    if (appInfoEntity != null && !TextUtils.isEmpty(str1))
      ((LaunchScheduler)AppbrandApplicationImpl.getInst().getService(LaunchScheduler.class)).startLaunch(appInfoEntity, str1, str2, bundle); 
  }
  
  private static void receiveHostEventInMiniApp(CrossProcessDataEntity paramCrossProcessDataEntity) {
    String str1 = paramCrossProcessDataEntity.getString("host_event_evt_name");
    String str2 = paramCrossProcessDataEntity.getString("host_event_mp_id");
    JSONObject jSONObject = paramCrossProcessDataEntity.getJSONObject("host_event_evt_params");
    HostEventMiniAppServiceInterface hostEventMiniAppServiceInterface = (HostEventMiniAppServiceInterface)ServiceProvider.getInstance().getService(HostEventMiniAppService.class);
    if (hostEventMiniAppServiceInterface != null)
      hostEventMiniAppServiceInterface.onReceiveMiniAppHostEvent(str2, str1, jSONObject); 
  }
  
  public static void savePermissionGrant(CrossProcessDataEntity paramCrossProcessDataEntity) {
    int i = paramCrossProcessDataEntity.getInt("appbrandPermissionType");
    boolean bool = paramCrossProcessDataEntity.getBoolean("isGranted");
    HostDependManager.getInst().savePermissionGrant(i, bool);
  }
  
  private static void syncTTRequestPrefetchInfo(CrossProcessDataEntity paramCrossProcessDataEntity) {
    String str2 = paramCrossProcessDataEntity.getString("ttrequest_prefetch_appid");
    String str1 = paramCrossProcessDataEntity.getString("ttrequest_prefetch_info");
    if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str1))
      PreTTRequestManager.savePrefetchInfoByPreloadProcess(str2, str1); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\handler\InnerMiniAppProcessCallHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */