package com.tt.miniapp.msg.wifi;

import com.tt.frontendapiinterface.b;
import com.tt.option.e.e;

public class ApiOnGetWifiListCtrl extends b {
  public ApiOnGetWifiListCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    callbackAppUnSupportFeature();
  }
  
  public String getActionName() {
    return "onGetWifiList";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\wifi\ApiOnGetWifiListCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */