package com.tt.miniapp.msg.sync;

import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.util.HashMap;

public class GetHostLaunchQuerySync extends SyncMsgCtrl {
  public GetHostLaunchQuerySync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (appInfoEntity != null) {
      hashMap.put("launchQuery", appInfoEntity.bdpLaunchQuery);
    } else {
      hashMap.put("launchQuery", "");
    } 
    return makeMsgByResult(true, (HashMap)hashMap, null, null);
  }
  
  public String getName() {
    return "getHostLaunchQuerySync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\GetHostLaunchQuerySync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */