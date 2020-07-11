package com.tt.miniapp.msg;

import android.app.Activity;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.navigate2.Nav2Util;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiNavigateBackMiniappCtrl extends b {
  public ApiNavigateBackMiniappCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (!TextUtils.equals("in_mp", appInfoEntity.launchFrom)) {
      callbackFail("not open by NavigateToMiniProgram");
      return;
    } 
    if (appInfoEntity.isGame() && !appInfoEntity.isWhite() && !Nav2Util.isOriginWhiteGame(appInfoEntity)) {
      callbackFail("unsupported operation");
      return;
    } 
    try {
      JSONObject jSONObject1 = new JSONObject(this.mArgs);
      String str = jSONObject1.optString("extraData");
      JSONObject jSONObject2 = new JSONObject();
      try {
        JSONObject jSONObject;
        jSONObject2.put("appId", (AppbrandApplicationImpl.getInst().getAppInfo()).appId);
        if (TextUtils.isEmpty(str)) {
          str = "";
        } else {
          jSONObject = new JSONObject(str);
        } 
        jSONObject2.put("extraData", jSONObject);
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ApiHandler", new Object[] { "act", jSONException });
      } 
      if (InnerHostProcessBridge.backApp(jSONObject2.toString(), true)) {
        ToolUtils.onActivityExit((Activity)AppbrandContext.getInst().getCurrentActivity(), 9);
        callbackOk();
        return;
      } 
      callbackFail("client trigger navigateBack Fail");
      return;
    } catch (JSONException jSONException) {
      callbackFail(a.a(this.mArgs));
      return;
    } 
  }
  
  public String getActionName() {
    return "navigateBackMiniProgram";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiNavigateBackMiniappCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */