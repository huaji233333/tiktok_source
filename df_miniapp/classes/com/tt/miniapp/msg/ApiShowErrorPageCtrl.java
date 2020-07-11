package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.badcase.BlockPageManager;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.swipeback.EventParamsValue;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LanguageUtils;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiShowErrorPageCtrl extends b {
  public ApiShowErrorPageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private String appendURL(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(AppbrandConstant.OpenApi.getInst().getCurrentDomain());
    stringBuilder.append("/");
    stringBuilder.append(paramString);
    stringBuilder.append("?");
    stringBuilder.append(LanguageUtils.appendLanguageQueryParam());
    return stringBuilder.toString();
  }
  
  public void act() {
    try {
      MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (miniappHostBase == null) {
        callbackFail("activity is null");
        return;
      } 
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.optString("type");
      if (TextUtils.isEmpty(str1)) {
        callbackFail(a.b("type"));
        return;
      } 
      if ("appblock".equals(str1))
        ((BlockPageManager)AppbrandApplicationImpl.getInst().getService(BlockPageManager.class)).handleErrorPage(); 
      EventParamsValue.PARAMS_EXIT_TYPE = "others";
      EventParamsValue.IS_OTHER_FLAG = true;
      String str2 = AppbrandApplicationImpl.getInst().getSchema();
      if (TextUtils.isEmpty(str2) || !HostDependManager.getInst().handleAppbrandDisablePage((Context)miniappHostBase, str2))
        HostDependManager.getInst().jumpToWebView((Context)miniappHostBase, appendURL(str1), null, true); 
      callbackOk();
      if (jSONObject.optBoolean("closeApp")) {
        ToolUtils.onActivityExit((Activity)miniappHostBase, 14);
        return;
      } 
      ToolUtils.onActivityExit((Activity)miniappHostBase, 9);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiShowErrorPageCtrl", new Object[] { jSONException });
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "showErrorPage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiShowErrorPageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */