package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.RecentAppsManager;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiRemoveRecentAppsCtrl extends b {
  public ApiRemoveRecentAppsCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("app_id");
      if (TextUtils.isEmpty(str)) {
        callbackFail(a.b("app_id"));
        return;
      } 
      RecentAppsManager.inst().deleteRecentApp(str, new RecentAppsManager.OnAppDeleteListener() {
            public void onFail(String param1String) {
              ApiRemoveRecentAppsCtrl.this.callbackFail(param1String);
            }
            
            public void onSuccess() {
              ApiRemoveRecentAppsCtrl.this.callbackOk();
            }
          });
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ApiHandler", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "removeFromRecentAppList";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiRemoveRecentAppsCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */