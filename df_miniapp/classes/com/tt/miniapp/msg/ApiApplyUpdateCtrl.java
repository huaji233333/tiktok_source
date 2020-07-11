package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.option.e.e;

public class ApiApplyUpdateCtrl extends b {
  public ApiApplyUpdateCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (AppbrandApplication.getInst().getUpdateAppInfo() != null)
      InnerEventHelper.mpAsyncApply((AppbrandApplication.getInst().getUpdateAppInfo()).version, (AppbrandApplication.getInst().getAppInfo()).version); 
    InnerHostProcessBridge.restartApp((AppbrandApplication.getInst().getAppInfo()).appId, AppbrandApplication.getInst().getSchema());
  }
  
  public String getActionName() {
    return "applyUpdate";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiApplyUpdateCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */