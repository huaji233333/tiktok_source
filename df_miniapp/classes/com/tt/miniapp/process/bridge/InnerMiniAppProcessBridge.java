package com.tt.miniapp.process.bridge;

import android.os.Bundle;
import android.os.Parcelable;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.launchcache.meta.AppInfoRequestResult;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.process.ProcessConstant;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import org.json.JSONObject;

public class InnerMiniAppProcessBridge {
  public static void broadcastLangChangeEvnet(String paramString) {
    CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().put("localeLang", paramString).build();
    String[] arrayOfString = ProcessConstant.Identify.PROCESS_IDENTIFY_LIST;
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++)
      ProcessCallControlBridge.callMiniAppProcessAsync(arrayOfString[i], "notifyLanguageChange", crossProcessDataEntity, null); 
  }
  
  public static void dispatchHostEventToMiniApp(String paramString1, String paramString2, String paramString3, JSONObject paramJSONObject) {
    ProcessCallControlBridge.callMiniAppProcessAsync(paramString1, "dispatchHostEventToMiniApp", CrossProcessDataEntity.Builder.create().put("host_event_evt_name", paramString3).put("host_event_mp_id", paramString2).put("host_event_evt_params", paramJSONObject).build(), null);
  }
  
  public static void getSnapshot(String paramString, IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callMiniAppProcessAsync(paramString, "getSnapshot", null, paramIpcCallback);
  }
  
  public static void notifyAllMiniAppUpdateSnapshotEvent() {
    String[] arrayOfString = ProcessConstant.Identify.PROCESS_IDENTIFY_LIST;
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++)
      ProcessCallControlBridge.callMiniAppProcessAsync(arrayOfString[i], "notifyUpdateSnapshot", null, null); 
  }
  
  public static void notifyUpdateSnapshotEvent(String paramString) {
    ProcessCallControlBridge.callMiniAppProcessAsync(paramString, "notifyUpdateSnapshot", null, null);
  }
  
  public static void prepareLaunch(String paramString1, AppInfoEntity paramAppInfoEntity, String paramString2, Bundle paramBundle) {
    String str = paramBundle.getString("mp_extra_vdom");
    paramBundle.remove("mp_extra_vdom");
    ProcessCallControlBridge.callMiniAppProcessAsync(paramString1, "prepareLaunch", CrossProcessDataEntity.Builder.create().putParcelable("appinfo", (Parcelable)paramAppInfoEntity).putParcelable("launch_extra_bundle", (Parcelable)paramBundle).put("pre_handle_vdom", str).put("schema", paramString2).build(), null);
  }
  
  public static void savePermissionGrant(final String miniAppProcessIdentify, final int permission, final boolean isGranted) {
    Observable.create(new Action() {
          public final void act() {
            ProcessCallControlBridge.callMiniAppProcessAsync(miniAppProcessIdentify, "savePermissionGrant", CrossProcessDataEntity.Builder.create().put("appbrandPermissionType", Integer.valueOf(permission)).put("isGranted", Boolean.valueOf(isGranted)).build(), null);
          }
        }).schudleOn(Schedulers.shortIO()).subscribeSimple();
  }
  
  public static void sendPrefetchedAppInfo(String paramString, AppInfoRequestResult paramAppInfoRequestResult) {
    ProcessCallControlBridge.callMiniAppProcessAsync(paramString, "appInfoPrefetched", CrossProcessDataEntity.Builder.create().putParcelable("prefetched_appinfo", (Parcelable)paramAppInfoRequestResult).build(), null);
  }
  
  public static void syncTTRequestPrefetchInfo(String paramString1, String paramString2, String paramString3) {
    ProcessCallControlBridge.callMiniAppProcessAsync(paramString1, "syncTTRequestPrefetch", CrossProcessDataEntity.Builder.create().put("ttrequest_prefetch_info", paramString3).put("ttrequest_prefetch_appid", paramString2).build(), null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\bridge\InnerMiniAppProcessBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */