package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.e;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApisetKeepScreenOnCtrl extends b {
  public ApisetKeepScreenOnCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      final boolean keepScreen = (new JSONObject(this.mArgs)).optBoolean("keepScreenOn");
      final e iActivityLife = AppbrandApplication.getInst().getActivityLife();
      if (e != null) {
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                iActivityLife.setKeepScreenOn(keepScreen);
              }
            });
        callbackOk();
        return;
      } 
      callbackFail(a.c("iActivityLife"));
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "setKeepScreenOn";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApisetKeepScreenOnCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */