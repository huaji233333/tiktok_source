package com.tt.miniapp.msg;

import android.os.BatteryManager;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.e.e;
import java.util.HashMap;

public class ApiGetBatteryInfoCtrl extends b {
  public ApiGetBatteryInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail(a.c("activity"));
      return;
    } 
    BatteryManager batteryManager = (BatteryManager)miniappHostBase.getSystemService("batterymanager");
    if (batteryManager != null) {
      int i = batteryManager.getIntProperty(4);
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("level", String.valueOf(i));
      callbackOk(a.a(hashMap));
      return;
    } 
    callbackFail(a.c("batteryManager"));
  }
  
  public String getActionName() {
    return "getBatteryInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetBatteryInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */