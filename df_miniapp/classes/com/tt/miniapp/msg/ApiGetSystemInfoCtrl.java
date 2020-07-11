package com.tt.miniapp.msg;

import android.app.Application;
import android.content.Context;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.util.SystemInfoUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;

public class ApiGetSystemInfoCtrl extends b {
  public ApiGetSystemInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    boolean bool = AppbrandContext.getInst().isGame();
    try {
      callbackOk(SystemInfoUtil.getSystemInfo((Context)application, bool));
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "getSystemInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetSystemInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */