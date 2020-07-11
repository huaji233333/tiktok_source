package com.tt.miniapp.msg.onUserCaptureScreen;

import com.tt.frontendapiinterface.b;
import com.tt.option.e.e;

public class ApiOffUserCaptureScreenCtrl extends b {
  public ApiOffUserCaptureScreenCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    OnUserCaptureScreenManager.getInstance().unregisterOnUserCaptureScreen();
    callbackOk();
  }
  
  public String getActionName() {
    return "offUserCaptureScreen";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\onUserCaptureScreen\ApiOffUserCaptureScreenCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */