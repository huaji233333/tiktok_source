package com.tt.miniapp.msg;

import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.senser.CompassManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.e.e;

public class ApiCompassCtrl extends b {
  private String functionName;
  
  public ApiCompassCtrl(String paramString1, String paramString2, int paramInt, e parame) {
    super(paramString2, paramInt, parame);
    this.functionName = paramString1;
  }
  
  public void act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail(a.c("activity"));
      return;
    } 
    String str = this.functionName;
    boolean bool = false;
    AppBrandLogger.i("tma_ApiCompassCtrl", new Object[] { str });
    if (TextUtils.equals(this.functionName, "startCompass")) {
      bool = CompassManager.getInst((Context)miniappHostBase).open();
    } else if (TextUtils.equals(this.functionName, "stopCompass")) {
      bool = CompassManager.getInst((Context)miniappHostBase).close();
    } 
    if (bool) {
      callbackOk();
      return;
    } 
    callbackFail("sensor unsupport or disable");
  }
  
  public String getActionName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiCompassCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */