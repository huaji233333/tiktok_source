package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.component.nativeview.game.GameButtonManager;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiOperateFollowButton extends b {
  public ApiOperateFollowButton(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    boolean bool2 = ApiPermissionManager.intercept(getActionName(), this.mCallBackId);
    boolean bool1 = false;
    if (bool2) {
      AppBrandLogger.d("tma_operateFollowButton", new Object[] { "in blacklist" });
      callbackFail("permission deny");
      return;
    } 
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.getString("buttonId");
      String str2 = jSONObject.getString("type");
      StringBuilder stringBuilder = new StringBuilder("id:");
      stringBuilder.append(str1);
      stringBuilder.append(" type:");
      stringBuilder.append(str2);
      AppBrandLogger.d("tma_operateFollowButton", new Object[] { stringBuilder.toString() });
      GameButtonManager gameButtonManager = GameButtonManager.get();
      if (gameButtonManager == null) {
        callbackFail("render activity not found");
        return;
      } 
      try {
        int i = Integer.parseInt(str1);
        if (TextUtils.equals(str2, "show")) {
          bool1 = gameButtonManager.setVisible(i, true);
        } else if (TextUtils.equals(str2, "hide")) {
          bool1 = gameButtonManager.setVisible(i, false);
        } else if (TextUtils.equals(str2, "destroy")) {
          bool1 = gameButtonManager.removeButton(i);
        } 
        if (bool1) {
          callbackOk();
          return;
        } 
        callbackFail("native view not found");
        return;
      } catch (NumberFormatException numberFormatException) {
        callbackFail("illegal button id");
      } 
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      AppBrandLogger.eWithThrowable("tma_operateFollowButton", "json args parse error", (Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "operateFollowButton";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiOperateFollowButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */