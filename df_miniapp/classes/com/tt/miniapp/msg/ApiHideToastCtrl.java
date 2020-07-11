package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;

public class ApiHideToastCtrl extends b {
  public ApiHideToastCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            ApiHideToastCtrl.this.hideToast();
            ApiHideToastCtrl.this.callbackOk();
          }
        });
  }
  
  public String getActionName() {
    return "hideToast";
  }
  
  protected void hideToast() {
    HostDependManager.getInst().hideToast();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiHideToastCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */