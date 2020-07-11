package com.tt.miniapp.msg;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiShowToastCtrl extends b {
  protected long mDuration;
  
  protected String mIcon;
  
  protected String mTitle;
  
  public ApiShowToastCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      this.mDuration = jSONObject.optLong("duration", 1500L);
      this.mTitle = jSONObject.optString("title");
      this.mIcon = jSONObject.optString("icon");
      if (this.mDuration <= 0L)
        this.mDuration = 1500L; 
      if (TextUtils.isEmpty(this.mTitle)) {
        callbackFail("title不能为空");
        return;
      } 
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              Application application;
              MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
              if (miniappHostBase == null)
                application = AppbrandContext.getInst().getApplicationContext(); 
              if (application != null)
                ApiShowToastCtrl.this.showToast((Context)application); 
              ApiShowToastCtrl.this.callbackOk();
            }
          });
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "tma_ApiShowToastCtrl", jSONException.getStackTrace());
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "showToast";
  }
  
  protected void showToast(Context paramContext) {
    HostDependManager.getInst().showToast(paramContext, this.mArgs, this.mTitle, this.mDuration, this.mIcon);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiShowToastCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */