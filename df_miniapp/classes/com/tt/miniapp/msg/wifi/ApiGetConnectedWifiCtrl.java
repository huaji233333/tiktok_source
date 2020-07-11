package com.tt.miniapp.msg.wifi;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.net.WifiObject;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import java.util.HashMap;

public class ApiGetConnectedWifiCtrl extends b {
  public ApiGetConnectedWifiCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    callbackAppUnSupportFeature();
  }
  
  public String getActionName() {
    return "getConnectedWifi";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\wifi\ApiGetConnectedWifiCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */