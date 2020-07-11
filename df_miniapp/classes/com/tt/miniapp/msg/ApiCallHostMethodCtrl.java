package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Intent;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.hostmethod.HostMethodManager;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCallHostMethodCtrl extends b {
  private boolean mCanUseHostMethod;
  
  private JSONObject mExtraParams;
  
  private boolean mInited;
  
  private String mMethodName;
  
  public ApiCallHostMethodCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void initVarsIfNeed() throws JSONException {
    if (this.mInited)
      return; 
    this.mInited = true;
    JSONObject jSONObject = new JSONObject(this.mArgs);
    this.mMethodName = jSONObject.optString("method");
    this.mExtraParams = jSONObject.optJSONObject("extra");
    this.mCanUseHostMethod = ApiPermissionManager.canUseHostMethod(this.mMethodName);
    if (!HostDependManager.getInst().shouldCheckPermissionBeforeCallhostmethod())
      this.mCanUseHostMethod = true; 
  }
  
  public void act() {
    try {
      initVarsIfNeed();
      if (!this.mCanUseHostMethod) {
        callbackFail("platform auth deny");
        return;
      } 
      MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (miniappHostBase != null) {
        HostMethodManager.getInstance().invokeJavaMethod((Activity)miniappHostBase, this.mMethodName, this.mExtraParams, new HostMethodManager.ResponseCallBack() {
              public void callResponse(String param1String) {
                ApiCallHostMethodCtrl.this.mApiHandlerCallback.callback(ApiCallHostMethodCtrl.this.mCallBackId, param1String);
              }
            });
        return;
      } 
      callbackFail("activity is null");
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ApiCallHostMethodCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "callHostMethod";
  }
  
  public boolean handleActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    try {
      initVarsIfNeed();
      return !this.mCanUseHostMethod ? false : HostMethodManager.getInstance().invokeOnActivityResult(this.mMethodName, paramInt1, paramInt2, paramIntent);
    } catch (Exception exception) {
      AppBrandLogger.e("ApiCallHostMethodCtrl", new Object[] { exception });
      return false;
    } 
  }
  
  public boolean shouldHandleActivityResult() {
    try {
      initVarsIfNeed();
      return !this.mCanUseHostMethod ? false : HostMethodManager.getInstance().shouldHandleActivityResult(this.mMethodName, this.mExtraParams);
    } catch (Exception exception) {
      AppBrandLogger.e("ApiCallHostMethodCtrl", new Object[] { exception });
      return false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiCallHostMethodCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */