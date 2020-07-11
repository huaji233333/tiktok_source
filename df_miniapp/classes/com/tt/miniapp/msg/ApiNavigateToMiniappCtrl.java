package com.tt.miniapp.msg;

import android.app.Activity;
import android.text.TextUtils;
import com.a;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.launchcache.meta.AppInfoHelper;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiNavigateToMiniappCtrl extends b {
  private String appId;
  
  private String extraData;
  
  private String query;
  
  private String startPage;
  
  private String versionType;
  
  public ApiNavigateToMiniappCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void showDialog(final AppInfoEntity info) {
    int i;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(a.a(UIUtils.getString(2097741927), new Object[] { info.appName }));
    if (info.isGame()) {
      i = 2097741950;
    } else {
      i = 2097741949;
    } 
    stringBuilder.append(UIUtils.getString(i));
    String str = stringBuilder.toString();
    HostDependManager.getInst().showModal((Activity)AppbrandContext.getInst().getCurrentActivity(), null, "", str, true, UIUtils.getString(2097741944), "", UIUtils.getString(2097741866), "", new NativeModule.NativeModuleCallback<Integer>() {
          public void onNativeModuleCall(Integer param1Integer) {
            if (param1Integer.intValue() == 1) {
              ApiNavigateToMiniappCtrl.this.openJump(info);
              return;
            } 
            ApiNavigateToMiniappCtrl.this.callbackFail("cancel");
          }
        });
  }
  
  public void act() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity != null && appInfoEntity.isGame() && !appInfoEntity.isWhite()) {
      callbackFail("unsupported operation");
      return;
    } 
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      if ("more_game".equalsIgnoreCase(jSONObject.optString("callFrom")) && appInfoEntity != null && appInfoEntity.isGameCenter()) {
        GameModuleController.inst().callMGNavTo(this, jSONObject);
        return;
      } 
      this.appId = jSONObject.optString("appId");
      this.startPage = jSONObject.optString("startPage");
      this.query = jSONObject.optString("query");
      this.extraData = jSONObject.optString("extraData");
      this.versionType = jSONObject.optString("versionType", "current");
      if (TextUtils.equals(this.appId, (AppbrandApplication.getInst().getAppInfo()).appId)) {
        callbackFail("can not jump to self");
        return;
      } 
      if (AppbrandApplicationImpl.getInst().getAppInfo().isLocalTest() && TextUtils.equals("latest", this.versionType)) {
        this.versionType = "latest";
      } else {
        this.versionType = "current";
      } 
      if (AppbrandApplication.getInst().getAppInfo().isWhite()) {
        openJump(AppInfoHelper.requestMeta(this.appId, this.versionType));
        return;
      } 
      AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
      if (appConfig == null) {
        callbackFail(a.c("config"));
        return;
      } 
      if (!appConfig.getNaviToAppList().contains(this.appId)) {
        callbackFail(a.a("appId %s is not in navigateToMiniProgramAppIdList", new Object[] { this.appId }));
        return;
      } 
      for (AppInfoEntity appInfoEntity2 : appConfig.getNaviToAppInfoList()) {
        if (appInfoEntity2 != null && TextUtils.equals(appInfoEntity2.appId, this.appId)) {
          showDialog(appInfoEntity2);
          return;
        } 
      } 
      AppInfoEntity appInfoEntity1 = AppInfoHelper.requestMeta(this.appId, this.versionType);
      if (appInfoEntity1 == null) {
        callbackFail(a.c("requested navigateApp appInfo"));
        return;
      } 
      appConfig.getNaviToAppInfoList().add(appInfoEntity1);
      showDialog(appInfoEntity1);
      return;
    } catch (JSONException jSONException) {
      callbackFail(a.a(this.mArgs));
      return;
    } 
  }
  
  public String getActionName() {
    return "navigateToMiniProgram";
  }
  
  public void openJump(AppInfoEntity paramAppInfoEntity) {
    AppInfoEntity appInfoEntity = paramAppInfoEntity;
    if (paramAppInfoEntity == null)
      appInfoEntity = new AppInfoEntity(); 
    appInfoEntity.appId = this.appId;
    appInfoEntity.startPage = this.startPage;
    appInfoEntity.versionType = this.versionType;
    appInfoEntity.query = this.query;
    JSONObject jSONObject = new JSONObject();
    try {
      JSONObject jSONObject1;
      jSONObject.put("appId", (AppbrandApplicationImpl.getInst().getAppInfo()).appId);
      if (TextUtils.isEmpty(this.extraData)) {
        String str = "";
      } else {
        jSONObject1 = new JSONObject(this.extraData);
      } 
      jSONObject.put("extraData", jSONObject1);
      jSONObject.put("__origin_wg_or_app", true);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiHandler", new Object[] { "openJump", jSONException });
    } 
    appInfoEntity.refererInfo = jSONObject.toString();
    InnerHostProcessBridge.jumpToApp(appInfoEntity, (AppbrandApplicationImpl.getInst().getAppInfo()).appId);
    AppbrandApplicationImpl.getInst().getForeBackgroundManager().pauseBackgroundOverLimitTimeStrategy();
    callbackOk();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiNavigateToMiniappCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */