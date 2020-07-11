package com.tt.miniapp.msg;

import android.content.Intent;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.TimeMeter;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiLoginCtrl extends b {
  private UserInfoManager.HostClientLoginListener mHostClientLoginListener = new UserInfoManager.HostClientLoginListener() {
      public void onLoginFail() {
        ApiLoginCtrl.this.callbackFail("host login failed");
      }
      
      public void onLoginSuccess() {
        UserInfoManager.requestLoginMiniAppPlatform(true, ApiLoginCtrl.this.mStartTime, ApiLoginCtrl.this.mMiniAppPlatformLoginListener, null);
      }
      
      public void onLoginUnSupport() {
        ApiLoginCtrl.this.callbackAppUnSupportFeature();
      }
      
      public void onLoginWhenBackground() {
        ApiLoginCtrl.this.callbackFail("login fail background");
      }
      
      public void onTriggerHostClientLogin(String param1String) {
        ApiLoginCtrl.this.mTriggeredHostClientLogin = true;
      }
    };
  
  public UserInfoManager.MiniAppPlatformLoginListener mMiniAppPlatformLoginListener = new UserInfoManager.MiniAppPlatformLoginListener() {
      public void onLoginFail(String param1String) {
        ApiLoginCtrl.this.callbackExtraInfoMsg(false, param1String);
      }
      
      public void onLoginSuccess(String param1String, JSONObject param1JSONObject) {
        JSONObject jSONObject = param1JSONObject;
        if (param1JSONObject == null) {
          jSONObject = new JSONObject();
          AppBrandLogger.e("ApiLoginCtrl", new Object[] { "onLoginSuccess dataObject == null" });
        } 
        try {
          jSONObject.put("errMsg", b.buildErrorMsg("login", "ok"));
        } catch (JSONException jSONException) {
          AppBrandLogger.eWithThrowable("ApiLoginCtrl", "onLoginSuccess", (Throwable)jSONException);
        } 
        ApiLoginCtrl.this.doCallbackByApiHandler(jSONObject.toString());
      }
    };
  
  public long mStartTime;
  
  public boolean mTriggeredHostClientLogin;
  
  ApiLoginCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    this.mStartTime = TimeMeter.currentMillis();
    Event.builder("mp_login").flush();
    boolean bool2 = true;
    boolean bool1 = bool2;
    try {
      if (!TextUtils.isEmpty(this.mArgs))
        bool1 = (new JSONObject(this.mArgs)).optBoolean("force", true); 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiLoginCtrl", exception.getStackTrace());
      bool1 = bool2;
    } 
    UserInfoManager.requestLoginMiniAppPlatform(bool1, this.mStartTime, this.mMiniAppPlatformLoginListener, this.mHostClientLoginListener);
  }
  
  public String getActionName() {
    return "login";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return this.mTriggeredHostClientLogin ? UserInfoManager.handleHostClientLoginResult(paramInt1, paramInt2, paramIntent, this.mHostClientLoginListener) : false;
  }
  
  public boolean shouldHandleActivityResult() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiLoginCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */