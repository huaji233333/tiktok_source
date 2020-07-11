package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiNavigateToVideoViewCtrl extends b {
  public ApiNavigateToVideoViewCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private JSONObject getAppInfoLogpb(AppInfoEntity paramAppInfoEntity) {
    if (!TextUtils.isEmpty(paramAppInfoEntity.extra))
      try {
        JSONObject jSONObject = (new JSONObject(paramAppInfoEntity.extra)).getJSONObject("event_extra");
        if (jSONObject != null)
          return jSONObject.getJSONObject("log_pb"); 
      } catch (JSONException jSONException) {
        AppBrandLogger.e("tma_ExtApiNavigateToVideoViewCtrl", new Object[] { jSONException });
      }  
    return null;
  }
  
  private void reportPlayVideoEvent() {
    JSONObject jSONObject = new JSONObject();
    try {
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity != null) {
        String str;
        if (appInfoEntity.isGame()) {
          str = "small_game";
        } else {
          str = "small_app";
        } 
        jSONObject.put("entrance", str);
        jSONObject.put("mp_id", appInfoEntity.appId);
        jSONObject.put("mp_name", appInfoEntity.appName);
        if (appInfoEntity.launchFrom != null)
          jSONObject.put("launch_from", appInfoEntity.launchFrom); 
        if (appInfoEntity.isGame()) {
          str = "micro_game";
        } else {
          str = "micro_app";
        } 
        jSONObject.put("_param_for_special", str);
        jSONObject.put("mp_gid", appInfoEntity.ttId);
        JSONObject jSONObject1 = getAppInfoLogpb(appInfoEntity);
        if (jSONObject1 != null)
          jSONObject.put("log_pb", jSONObject1); 
      } 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("tma_ExtApiNavigateToVideoViewCtrl", new Object[] { jSONException });
    } 
    HostProcessBridge.logEvent("mp_short_video_play", jSONObject);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("gid");
      MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (TextUtils.isEmpty(str)) {
        callbackFail("gid is empty!", 1008);
        AppBrandLogger.e("tma_ExtApiNavigateToVideoViewCtrl", new Object[] { "gid is empty!" });
        return;
      } 
      if (miniappHostBase == null) {
        callbackFail("currentActivity == null", 1007);
        AppBrandLogger.e("tma_ExtApiNavigateToVideoViewCtrl", new Object[] { "currentActivity == null" });
        return;
      } 
      if (!NetUtil.isNetworkAvailable((Context)miniappHostBase)) {
        callbackFail("network is unavailable", 1006);
        AppBrandLogger.e("tma_ExtApiNavigateToVideoViewCtrl", new Object[] { "network is unavailable" });
        return;
      } 
      if (HostDependManager.getInst().navigateToVideoView((Activity)miniappHostBase, str)) {
        callbackOk();
        reportPlayVideoEvent();
        return;
      } 
      callbackAppUnSupportFeature();
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      AppBrandLogger.e("tma_ExtApiNavigateToVideoViewCtrl", new Object[] { exception });
      return;
    } 
  }
  
  public String getActionName() {
    return "navigateToVideoView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiNavigateToVideoViewCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */