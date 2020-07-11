package com.tt.miniapp.msg.sync;

import android.os.BatteryManager;
import com.tt.frontendapiinterface.a;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import java.util.HashMap;

public class GetBatteryInfoSync extends SyncMsgCtrl {
  public GetBatteryInfoSync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null)
      return makeFailMsg(a.c("activity")); 
    BatteryManager batteryManager = (BatteryManager)miniappHostBase.getSystemService("batterymanager");
    if (batteryManager != null) {
      int i = batteryManager.getIntProperty(4);
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("level", String.valueOf(i));
      return makeOkMsg(a.a(hashMap));
    } 
    return makeFailMsg(a.c("batteryManager"));
  }
  
  public String getName() {
    return "getBatteryInfoSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\GetBatteryInfoSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */