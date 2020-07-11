package com.tt.miniapphost.process;

import android.app.Activity;
import com.bytedance.sandboxapp.protocol.service.c.c;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class HostProcessBridge {
  public static void addMiniAppToFavoriteMiniAppList(String paramString) {
    ProcessCallControlBridge.callHostProcessSync("type_favorite_list_handle", (new CrossProcessDataEntity.Builder()).put("favorites_handle_mode", Integer.valueOf(0)).put("miniAppId", paramString).build());
  }
  
  public static void callAwemeHandler(int paramInt, String paramString1, String paramString2, final c callback) {
    ProcessCallControlBridge.callHostProcessAsync("awemeHandler", (new CrossProcessDataEntity.Builder()).put("aweme_action", Integer.valueOf(paramInt)).put("aweme_uid", paramString1).put("aweme_sec_uid", paramString2).build(), new IpcCallback() {
          public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            if (param1CrossProcessDataEntity == null) {
              callback.onFailure(-1, "callbackData is null");
              return;
            } 
            if (param1CrossProcessDataEntity.getInt("aweme_result") == 0) {
              callback.onFollowAwemeResult(Boolean.valueOf(param1CrossProcessDataEntity.getBoolean("aweme_has_followed")));
              return;
            } 
            callback.onFailure(param1CrossProcessDataEntity.getInt("aweme_error_code"), param1CrossProcessDataEntity.getString("aweme_error_msg"));
          }
          
          public final void onIpcConnectError() {
            callback.onFailure(-1, "ipc fail");
          }
        });
  }
  
  public static void callHostLifecycleAction(final Activity activity, final String lifecycle) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            if (activity.getComponentName() != null) {
              crossProcessDataEntity = (CrossProcessDataEntity)activity.getComponentName().getClassName();
            } else {
              crossProcessDataEntity = null;
            } 
            CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().put("activityLifecycle", lifecycle).put("activityName", crossProcessDataEntity).put("hashcode", Integer.valueOf(activity.hashCode())).build();
            AppBrandLogger.d("HostProcessBridge", new Object[] { "callHostLifecycleAction ", crossProcessDataEntity });
            ProcessCallControlBridge.callHostProcessSync("tmaLifecycle", crossProcessDataEntity);
          }
        }Schedulers.shortIO());
  }
  
  public static String getCurrentLangSetting() {
    String str = null;
    try {
      CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("getCurrentLang", null);
      if (crossProcessDataEntity != null)
        str = crossProcessDataEntity.getString("localeLang"); 
      return str;
    } catch (Exception exception) {
      AppBrandLogger.e("HostProcessBridge", new Object[] { exception });
      return null;
    } 
  }
  
  public static CrossProcessDataEntity getHostSettings() {
    return ProcessCallControlBridge.callHostProcessSync("getHostSettings", null);
  }
  
  public static void getLocalPhoneNumber(IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("getLocalPhoneNumber", null, paramIpcCallback);
  }
  
  public static void getLocalPhoneNumberToken(IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("getLocalPhoneNumberToken", null, paramIpcCallback);
  }
  
  public static String getLoginCookie() {
    String str = null;
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("getLoginCookie", null);
    if (crossProcessDataEntity != null)
      str = crossProcessDataEntity.getString("loginCookie"); 
    return str;
  }
  
  public static JSONObject getNetCommonParams() {
    JSONObject jSONObject = null;
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("getNetCommonParams", null);
    if (crossProcessDataEntity != null)
      jSONObject = crossProcessDataEntity.getJSONObject("netCommonParams"); 
    return jSONObject;
  }
  
  public static List<String> getPermissionDialogABTestMPID() {
    List<String> list = null;
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("typeGetPermissionDialogABTestMPID", null);
    if (crossProcessDataEntity != null)
      list = crossProcessDataEntity.getStringList("getPermissionDialogABTestMpid"); 
    return list;
  }
  
  public static void getSnapshot(boolean paramBoolean, IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("getSnapshot", CrossProcessDataEntity.Builder.create().put("miniAppId", (AppbrandApplicationImpl.getInst().getAppInfo()).appId).put("forceGetHostActivitySnapshot", Boolean.valueOf(paramBoolean)).build(), paramIpcCallback);
  }
  
  public static CrossProcessDataEntity getUserInfo() {
    return ProcessCallControlBridge.callHostProcessSync("getUserInfo", null);
  }
  
  public static CrossProcessDataEntity getVideoPreEditSettings() {
    return ProcessCallControlBridge.callHostProcessSync("getVideoPreEditSettings", null);
  }
  
  public static void handleUserRelation(String paramString1, String paramString2, IpcCallback paramIpcCallback) {
    CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().put("apiData", paramString1).put("ttId", paramString2).build();
    AppBrandLogger.d("HostProcessBridge", new Object[] { "handleUserRelation", crossProcessDataEntity });
    ProcessCallControlBridge.callHostProcessAsync("handleUserRelation", crossProcessDataEntity, paramIpcCallback);
  }
  
  public static void hostActionAsync(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity, IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("hostActionAsync", CrossProcessDataEntity.Builder.create().put("hostActionType", paramString).putCrossProcessDataEntity("hostActionData", paramCrossProcessDataEntity).build(), paramIpcCallback);
  }
  
  public static CrossProcessDataEntity hostActionSync(String paramString, CrossProcessDataEntity paramCrossProcessDataEntity) {
    return ProcessCallControlBridge.callHostProcessSync("hostActionSync", CrossProcessDataEntity.Builder.create().put("hostActionType", paramString).putCrossProcessDataEntity("hostActionData", paramCrossProcessDataEntity).build());
  }
  
  public static boolean isDataHandlerExist(String paramString) {
    return AppbrandContext.getInst().isDataHandlerExist(paramString);
  }
  
  public static boolean isOnWhiteList() {
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("typeIsOnWhiteList", null);
    return (crossProcessDataEntity != null) ? crossProcessDataEntity.getBoolean("isOnWhiteList") : true;
  }
  
  public static boolean isReportPerformance() {
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("reportPerformanceEnable", null);
    return (crossProcessDataEntity != null) ? crossProcessDataEntity.getBoolean("reportPerformance") : false;
  }
  
  public static void logEvent(final String eventName, final JSONObject eventData) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            ProcessCallControlBridge.callHostProcessSync("actionLog", CrossProcessDataEntity.Builder.create().put("logEventVersion", "V3").put("logEventName", eventName).put("logEventData", eventData).build());
          }
        }Schedulers.shortIO());
  }
  
  public static void logMisc(final String eventName, final JSONObject eventData) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            ProcessCallControlBridge.callHostProcessSync("actionMiscAppLog", CrossProcessDataEntity.Builder.create().put("logEventName", eventName).put("logEventData", eventData).build());
          }
        }Schedulers.shortIO());
  }
  
  private static void onMiniAppLifeCycleChange(final String miniAppLifecycle, final AppInfoEntity appInfo, final boolean isGame, final Integer orientation, final String stopReason) {
    if (appInfo == null) {
      AppBrandLogger.e("HostProcessBridge", new Object[] { "onMiniAppLifeCycleChange appInfo == null" });
      return;
    } 
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            JSONObject jSONObject = new JSONObject();
            try {
              jSONObject.put("isGame", isGame);
              jSONObject.put("miniAppId", appInfo.appId);
              jSONObject.put("miniAppIcon", appInfo.icon);
              jSONObject.put("miniAppName", appInfo.appName);
              jSONObject.put("miniAppType", appInfo.type);
              jSONObject.put("miniAppLaunchFrom", appInfo.launchFrom);
              jSONObject.put("miniAppScene", appInfo.scene);
              jSONObject.put("miniAppSubScene", appInfo.subScene);
              jSONObject.put("shareTicket", appInfo.shareTicket);
              jSONObject.put("ttId", appInfo.ttId);
              jSONObject.put("miniAppOrientation", orientation);
              jSONObject.put("miniAppStopReason", stopReason);
            } catch (JSONException jSONException) {
              AppBrandLogger.e("HostProcessBridge", new Object[] { jSONException });
            } 
            CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().put("miniAppLifecycle", miniAppLifecycle).put("jsonData", jSONObject).build();
            AppBrandLogger.d("HostProcessBridge", new Object[] { "onMiniAppLifeCycleChange ", crossProcessDataEntity });
            ProcessCallControlBridge.callHostProcessSync("miniAppLifecycle", crossProcessDataEntity);
          }
        }Schedulers.shortIO());
  }
  
  public static void onMiniAppStart(AppInfoEntity paramAppInfoEntity, boolean paramBoolean, Integer paramInteger) {
    onMiniAppLifeCycleChange("open", paramAppInfoEntity, paramBoolean, paramInteger, null);
  }
  
  public static void onMiniAppStop(AppInfoEntity paramAppInfoEntity, boolean paramBoolean, Integer paramInteger, String paramString) {
    onMiniAppLifeCycleChange("close", paramAppInfoEntity, paramBoolean, paramInteger, paramString);
  }
  
  public static void preloadMiniApp(String paramString, IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("preloadMiniApp", CrossProcessDataEntity.Builder.create().put("preload_app_args", paramString).build(), paramIpcCallback);
  }
  
  public static void removeMiniAppFromFavoriteMiniAppList(String paramString) {
    ProcessCallControlBridge.callHostProcessSync("type_favorite_list_handle", (new CrossProcessDataEntity.Builder()).put("favorites_handle_mode", Integer.valueOf(1)).put("miniAppId", paramString).build());
  }
  
  public static void sendLogV1(final String category, final String tag, final String label, final long value, final long extraValue, final JSONObject extraJson) {
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            ProcessCallControlBridge.callHostProcessSync("actionLog", CrossProcessDataEntity.Builder.create().put("logEventVersion", "V1").put("category", category).put("tag", tag).put("label", label).put("value", Long.valueOf(value)).put("ext_value", Long.valueOf(extraValue)).put("ext_json", extraJson).build());
          }
        }Schedulers.shortIO());
  }
  
  public static void uploadFeedback(String paramString, JSONObject paramJSONObject, IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("uploadFeedback", CrossProcessDataEntity.Builder.create().put("feedbackLogType", paramString).put("feedbackPath", paramJSONObject).build(), paramIpcCallback);
  }
  
  public static void uploadFeedbackAlog(String paramString, IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("uploadAlog", CrossProcessDataEntity.Builder.create().put("alogScene", paramString).build(), paramIpcCallback);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\process\HostProcessBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */