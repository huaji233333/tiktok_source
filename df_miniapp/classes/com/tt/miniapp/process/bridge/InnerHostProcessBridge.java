package com.tt.miniapp.process.bridge;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;
import com.bytedance.sandboxapp.protocol.service.request.entity.HttpRequest;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.origin.OriginHelper;
import com.tt.miniapp.offlinezip.OnOfflineZipCheckUpdateResultListener;
import com.tt.miniapp.util.UriUtil;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;
import java.util.LinkedHashSet;
import java.util.List;

public class InnerHostProcessBridge {
  public static void addHostListener(String paramString1, String paramString2) {
    ProcessCallControlBridge.callHostProcessSync("addHostEventListener", CrossProcessDataEntity.Builder.create().put("host_event_mp_id", paramString1).put("host_event_event_name", paramString2).build());
  }
  
  public static void addToFavoriteSet(String paramString) {
    ProcessCallControlBridge.callHostProcessSync("type_add_to_favorite_set", CrossProcessDataEntity.Builder.create().put("mini_app_id", paramString).build());
  }
  
  public static boolean backApp(String paramString, boolean paramBoolean) {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity != null) {
      String str = appInfoEntity.appId;
    } else {
      appInfoEntity = null;
    } 
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("back_app", CrossProcessDataEntity.Builder.create().put("refererInfo", paramString).put("isApiCall", Boolean.valueOf(paramBoolean)).put("is_launch_with_float_style", Boolean.valueOf(AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())).put("miniAppId", appInfoEntity).build());
    return (crossProcessDataEntity != null) ? crossProcessDataEntity.getBoolean("back_app_result", false) : false;
  }
  
  public static void checkUpdateOfflineZip(List<String> paramList, final OnOfflineZipCheckUpdateResultListener listener) {
    CrossProcessDataEntity crossProcessDataEntity = CrossProcessDataEntity.Builder.create().putStringList("offline_zip_module_names", paramList).build();
    if (listener != null) {
      IpcCallback ipcCallback = new IpcCallback() {
          public final void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            boolean bool;
            if (param1CrossProcessDataEntity != null) {
              bool = param1CrossProcessDataEntity.getBoolean("offline_zip_update_result");
            } else {
              bool = false;
            } 
            listener.onComplete(bool);
          }
          
          public final void onIpcConnectError() {
            listener.onComplete(false);
          }
        };
    } else {
      paramList = null;
    } 
    ProcessCallControlBridge.callHostProcessAsync("checkUpdateOfflineZip", crossProcessDataEntity, (IpcCallback)paramList);
  }
  
  public static LinkedHashSet<String> getFavoriteSet() {
    LinkedHashSet<String> linkedHashSet2 = null;
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("type_get_favorite_set", null);
    LinkedHashSet<String> linkedHashSet1 = linkedHashSet2;
    if (crossProcessDataEntity != null) {
      List<?> list = crossProcessDataEntity.getStringList("favorite_set", null);
      linkedHashSet1 = linkedHashSet2;
      if (list != null)
        linkedHashSet1 = new LinkedHashSet(list); 
    } 
    return linkedHashSet1;
  }
  
  public static String getFavoriteSettings() {
    String str = null;
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("type_get_favorite_settings", null);
    if (crossProcessDataEntity != null)
      str = crossProcessDataEntity.getString("favorite_settings", null); 
    return str;
  }
  
  public static String getPlatformSession(String paramString) {
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("getPlatformSession", CrossProcessDataEntity.Builder.create().put("miniAppId", paramString).build());
    return (crossProcessDataEntity != null) ? crossProcessDataEntity.getString("platformSession") : null;
  }
  
  public static String getSpData(String paramString1, String paramString2, String paramString3) {
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("getSpData", CrossProcessDataEntity.Builder.create().put("sp_data_key", paramString2).put("sp_data_file", paramString1).put("sp_data_default", paramString3).build());
    return (crossProcessDataEntity != null) ? crossProcessDataEntity.getString("sp_data_value") : paramString3;
  }
  
  public static HttpRequest.RequestResult httpRequestWithCommonParam(HttpRequest.RequestTask paramRequestTask) {
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("httpRequestWithCommonParam", (new CrossProcessDataEntity.Builder()).putParcelable("httpRequestTask", (Parcelable)paramRequestTask).build());
    return (crossProcessDataEntity == null) ? null : (HttpRequest.RequestResult)crossProcessDataEntity.getParcelable("httpRequestResult");
  }
  
  public static boolean isInJumpList(String paramString) {
    CrossProcessDataEntity crossProcessDataEntity = ProcessCallControlBridge.callHostProcessSync("is_in_jump_list", CrossProcessDataEntity.Builder.create().put("miniAppId", paramString).build());
    return (crossProcessDataEntity != null) ? crossProcessDataEntity.getBoolean("is_in_jumplist", false) : false;
  }
  
  public static void jumpToApp(AppInfoEntity paramAppInfoEntity, String paramString) {
    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public static void jumpToAppFromSchema(String paramString1, boolean paramBoolean, String paramString2) {
    paramString1 = OriginHelper.addOriginIfNeeded(paramString1);
    ProcessCallControlBridge.callHostProcessSync("jump_to_app_from_schema", CrossProcessDataEntity.Builder.create().put("schema", paramString1).put("miniAppFromId", paramString2).put("isGame", Boolean.valueOf(paramBoolean)).build());
    AppbrandApplicationImpl.getInst().setJumpToApp(true);
  }
  
  public static void notifyMiniAppProcessCrash(String paramString1, String paramString2) {
    ProcessCallControlBridge.callHostProcessSync("notify_mini_app_process_crash", CrossProcessDataEntity.Builder.create().put("processName", paramString1).put("exceptionMessage", paramString2).build());
  }
  
  public static void notifyMiniAppProcessUsed(String paramString) {
    ProcessCallControlBridge.callHostProcessSync("mini_process_used", CrossProcessDataEntity.Builder.create().put("processName", paramString).build());
  }
  
  public static void notifyPreloadEmptyProcess() {
    ProcessCallControlBridge.callHostProcessAsync("notifyPreloadEmptyProcess", null, null);
  }
  
  public static void removeFromFavoriteSet(String paramString) {
    ProcessCallControlBridge.callHostProcessSync("type_remove_from_favorite_set", CrossProcessDataEntity.Builder.create().put("mini_app_id", paramString).build());
  }
  
  public static void removeHostListener(String paramString1, String paramString2) {
    ProcessCallControlBridge.callHostProcessSync("removeHostEventListener", CrossProcessDataEntity.Builder.create().put("host_event_mp_id", paramString1).put("host_event_event_name", paramString2).build());
  }
  
  public static void removeSpData(String paramString1, String paramString2) {
    ProcessCallControlBridge.callHostProcessSync("removeSpData", CrossProcessDataEntity.Builder.create().put("sp_data_key", paramString2).put("sp_data_file", paramString1).build());
  }
  
  public static void restartApp(String paramString1, String paramString2) {
    Uri uri = Uri.parse(paramString2);
    if (!TextUtils.isEmpty(uri.getQueryParameter("bdp_launch_type"))) {
      uri = UriUtil.replaceUriParameter(uri, "bdp_launch_type", "restart");
    } else {
      uri = uri.buildUpon().appendQueryParameter("bdp_launch_type", "restart").build();
    } 
    ProcessCallControlBridge.callHostProcessSync("restart_app", CrossProcessDataEntity.Builder.create().put("miniAppId", paramString1).put("miniAppSchema", uri.toString()).build());
  }
  
  public static void savePlatformSession(String paramString1, String paramString2) {
    ProcessCallControlBridge.callHostProcessAsync("savePlatformSession", CrossProcessDataEntity.Builder.create().put("miniAppId", paramString2).put("platformSession", paramString1).build(), null);
  }
  
  public static void saveSpData(String paramString1, String paramString2, String paramString3) {
    ProcessCallControlBridge.callHostProcessSync("saveSpData", CrossProcessDataEntity.Builder.create().put("sp_data_key", paramString2).put("sp_data_file", paramString1).put("sp_data_value", paramString3).build());
  }
  
  public static void scheduleApiHandlerAct(String paramString1, String paramString2, IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("scheduleApiHandler", CrossProcessDataEntity.Builder.create().put("apiName", paramString1).put("apiData", paramString2).build(), paramIpcCallback);
  }
  
  public static void setTmaLaunchFlag() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity == null)
      return; 
    Application application = AppbrandContext.getInst().getApplicationContext();
    ProcessCallControlBridge.callHostProcessAsync("setTmaLaunchFlag", CrossProcessDataEntity.Builder.create().put("miniAppId", appInfoEntity.appId).put("miniAppVersionType", appInfoEntity.versionType).put("processName", ProcessUtil.getCurProcessName((Context)application)).build(), null);
  }
  
  public static void startLocate(IpcCallback paramIpcCallback) {
    ProcessCallControlBridge.callHostProcessAsync("getLocation", CrossProcessDataEntity.Builder.create().build(), paramIpcCallback);
  }
  
  public static void updateBaseBundle() {
    ProcessCallControlBridge.callHostProcessAsync("updateBaseBundle", CrossProcessDataEntity.Builder.create().build(), null);
  }
  
  public static void updateFavoriteSet(List<String> paramList) {
    ProcessCallControlBridge.callHostProcessSync("type_update_favorite_set", CrossProcessDataEntity.Builder.create().putStringList("favorite_set", paramList).build());
  }
  
  public static void updateJumpList(String paramString, boolean paramBoolean1, boolean paramBoolean2) {
    ProcessCallControlBridge.callHostProcessSync("update_jump_list", CrossProcessDataEntity.Builder.create().put("miniAppId", paramString).put("isGame", Boolean.valueOf(paramBoolean1)).put("isSpecial", Boolean.valueOf(paramBoolean2)).build());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\process\bridge\InnerHostProcessBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */