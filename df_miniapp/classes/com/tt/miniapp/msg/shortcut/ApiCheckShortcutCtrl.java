package com.tt.miniapp.msg.shortcut;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.shortcut.ShortcutEntity;
import com.tt.miniapp.shortcut.adapter.CustomShortcutManagerCompat;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCheckShortcutCtrl extends b {
  public ApiCheckShortcutCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    if (initParamsEntity != null && TextUtils.isEmpty(initParamsEntity.getShortcutClassName())) {
      AppBrandLogger.e("ApiCheckShortcutCtrl", new Object[] { "shortcut launch activty not config" });
      callbackFail("feature is not supported in app");
      return;
    } 
    Application application = AppbrandContext.getInst().getApplicationContext();
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    if (appInfoEntity == null) {
      AppBrandLogger.e("ApiCheckShortcutCtrl", new Object[] { "appInfo is null" });
      callbackFail("app info is null");
      return;
    } 
    CustomShortcutManagerCompat.ShortcutStatus shortcutStatus = CustomShortcutManagerCompat.getShortcutState((Context)application, (new ShortcutEntity.Builder()).setLabel(appInfoEntity.appName).setIcon(appInfoEntity.icon).setAppId(appInfoEntity.appId).build());
    JSONObject jSONObject1 = new JSONObject();
    JSONObject jSONObject2 = new JSONObject();
    try {
      jSONObject2.put("exist", shortcutStatus.exist);
      jSONObject2.put("needUpdate", shortcutStatus.needUpdate);
      jSONObject1.put("status", jSONObject2);
      callbackOk(jSONObject1);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiCheckShortcutCtrl", new Object[] { jSONException });
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "checkShortcut";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\shortcut\ApiCheckShortcutCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */