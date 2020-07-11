package com.tt.miniapp.webbridge.sync;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.a;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.NativeModule;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.UIUtils;
import com.tt.option.v.c;
import org.json.JSONException;
import org.json.JSONObject;

public class LaunchAppHandler extends WebEventHandler {
  public LaunchAppHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public static boolean launchApp(String paramString1, String paramString2) {
    try {
      Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(paramString1));
      intent.setFlags(268435456);
      intent.setPackage(paramString2);
      MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (miniappHostBase != null)
        miniappHostBase.startActivity(intent); 
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("WebEventHandler", new Object[] { exception });
      return false;
    } 
  }
  
  public String act() {
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return makeFailMsg("activity is null"); 
    final AppInfoEntity entity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity == null)
      return makeFailMsg("app info is null"); 
    final AppConfig config = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig == null)
      return makeFailMsg("appconfig is null"); 
    final JSONObject resObject = new JSONObject();
    try {
      if (!appInfoEntity.isCanLaunchApp()) {
        buildErrorMsg(jSONObject, ErrorMsg.HAVE_NO_PERMISSION.getDesc());
      } else if (TextUtils.isEmpty((appConfig.getLaunchAppConfig()).appName) || TextUtils.isEmpty((appConfig.getLaunchAppConfig()).packageName)) {
        buildErrorMsg(jSONObject, ErrorMsg.APP_JSON_CONFIG_ERROR.getDesc());
      } else if (!c.a().b()) {
        buildErrorMsg(jSONObject, ErrorMsg.INVALID_SCENE.getDesc());
      } 
      if (AppbrandApplicationImpl.getInst().getWebViewManager() != null && !TextUtils.isEmpty(jSONObject.optString("errMsg"))) {
        AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(this.mRender.getWebViewId(), this.mCallBackId, jSONObject.toString());
        Event.builder("micro_app_call_app_button_click", appInfoEntity).kv("error_msg", jSONObject.optString("errMsg")).flush();
        return CharacterUtils.empty();
      } 
      Event.builder("micro_app_call_app_button_click", appInfoEntity).flush();
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              HostDependManager.getInst().showModal(activity, null, "", a.a(UIUtils.getString(2097741934), new Object[] { (this.val$config.getLaunchAppConfig()).appName }), true, UIUtils.getString(2097741944), "", UIUtils.getString(2097741866), "", new NativeModule.NativeModuleCallback<Integer>() {
                    public void onNativeModuleCall(Integer param2Integer) {
                      try {
                        int i = param2Integer.intValue();
                        if (i == 0) {
                          Event.builder("micro_app_call_app_pop_up", entity).kv("select_option", "no").flush();
                          final JSONObject resObject = new JSONObject();
                          LaunchAppHandler.this.buildErrorMsg(jSONObject, LaunchAppHandler.ErrorMsg.LAUNCH_ERROR.getDesc());
                          if (AppbrandApplicationImpl.getInst().getWebViewManager() != null) {
                            AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(LaunchAppHandler.this.mRender.getWebViewId(), LaunchAppHandler.this.mCallBackId, jSONObject.toString());
                            return;
                          } 
                        } else {
                          boolean bool;
                          Event.builder("micro_app_call_app_pop_up", entity).kv("select_option", "yes").flush();
                          final JSONObject resObject = new JSONObject();
                          String str = (new JSONObject(LaunchAppHandler.this.mArgs)).optString("appParameter");
                          str = LaunchAppHandler.this.getLaunchSchema(str, entity.appId);
                          if (c.a().b() && entity.isCanLaunchApp()) {
                            bool = LaunchAppHandler.launchApp(str, (config.getLaunchAppConfig()).packageName);
                          } else {
                            bool = false;
                          } 
                          boolean bool1 = LaunchAppHandler.this.isAppInstalled((Context)activity, (config.getLaunchAppConfig()).packageName);
                          if (bool1) {
                            LaunchAppHandler.this.buildErrorMsg(jSONObject, LaunchAppHandler.ErrorMsg.NEED_UPDATE.getDesc());
                          } else {
                            LaunchAppHandler.this.buildErrorMsg(jSONObject, LaunchAppHandler.ErrorMsg.LAUNCH_ERROR.getDesc());
                          } 
                          if (!bool || !bool1) {
                            LaunchAppHandler.this.callbackFailMsg(entity, jSONObject);
                            return;
                          } 
                          AppbrandContext.mainHandler.postDelayed(new Runnable() {
                                public void run() {
                                  if (AppbrandContext.getInst().getCurrentActivity().isOnActivityStackTop())
                                    LaunchAppHandler.this.callbackFailMsg(entity, resObject); 
                                }
                              }1000L);
                          return;
                        } 
                      } catch (Exception exception) {
                        AppBrandLogger.d("WebEventHandler", new Object[] { exception });
                        try {
                          LaunchAppHandler.this.buildErrorMsg(resObject, LaunchAppHandler.ErrorMsg.LAUNCH_ERROR.getDesc());
                          LaunchAppHandler.this.callbackFailMsg(entity, resObject);
                          return;
                        } catch (JSONException jSONException) {
                          AppBrandLogger.e("WebEventHandler", new Object[] { jSONException });
                          return;
                        } 
                      } 
                    }
                  });
            }
          });
      return CharacterUtils.empty();
    } catch (Exception exception) {
      AppBrandLogger.d("WebEventHandler", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public void buildErrorMsg(JSONObject paramJSONObject, String paramString) throws JSONException {
    StringBuilder stringBuilder = new StringBuilder("fail ");
    stringBuilder.append(paramString);
    paramJSONObject.put("errMsg", buildErrorMsg("launchApp", stringBuilder.toString()));
  }
  
  public void callbackFailMsg(AppInfoEntity paramAppInfoEntity, JSONObject paramJSONObject) {
    Event.builder("micro_app_call_app_failed", paramAppInfoEntity).kv("error_msg", paramJSONObject.optString("errMsg")).flush();
    if (AppbrandApplicationImpl.getInst().getWebViewManager() != null)
      AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(this.mRender.getWebViewId(), this.mCallBackId, paramJSONObject.toString()); 
  }
  
  public String getApiName() {
    return "launchApp";
  }
  
  public String getLaunchSchema(String paramString1, String paramString2) {
    paramString1 = Base64.encodeToString(paramString1.getBytes(), 8);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString2);
    stringBuilder.append("://bytedance.com/bdp/launchApp?app-parameter-base64=");
    stringBuilder.append(paramString1);
    return stringBuilder.toString();
  }
  
  public boolean isAppInstalled(Context paramContext, String paramString) {
    try {
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramString, 0);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      AppBrandLogger.e("WebEventHandler", new Object[] { nameNotFoundException });
      nameNotFoundException = null;
    } 
    return (nameNotFoundException != null);
  }
  
  enum ErrorMsg {
    APP_JSON_CONFIG_ERROR,
    HAVE_NO_PERMISSION,
    INVALID_SCENE("invalid scene"),
    LAUNCH_ERROR("invalid scene"),
    NEED_UPDATE("invalid scene");
    
    private String desc;
    
    static {
      APP_JSON_CONFIG_ERROR = new ErrorMsg("APP_JSON_CONFIG_ERROR", 2, "app json config error");
      NEED_UPDATE = new ErrorMsg("NEED_UPDATE", 3, "need update");
      LAUNCH_ERROR = new ErrorMsg("LAUNCH_ERROR", 4, "launch error");
      $VALUES = new ErrorMsg[] { INVALID_SCENE, HAVE_NO_PERMISSION, APP_JSON_CONFIG_ERROR, NEED_UPDATE, LAUNCH_ERROR };
    }
    
    ErrorMsg(String param1String1) {
      this.desc = param1String1;
    }
    
    public final String getDesc() {
      return this.desc;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\LaunchAppHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */