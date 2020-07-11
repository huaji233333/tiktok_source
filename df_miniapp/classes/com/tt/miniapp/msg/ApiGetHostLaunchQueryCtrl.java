package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.option.e.e;
import java.util.HashMap;

public class ApiGetHostLaunchQueryCtrl extends b {
  public ApiGetHostLaunchQueryCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (appInfoEntity != null) {
      hashMap.put("launchQuery", appInfoEntity.bdpLaunchQuery);
    } else {
      hashMap.put("launchQuery", "");
    } 
    callbackMsg(true, hashMap, null);
  }
  
  public String getActionName() {
    return "getHostLaunchQuery";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetHostLaunchQueryCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */