package com.tt.miniapp.msg.sync;

import android.content.Context;
import com.tt.miniapp.util.SystemInfoUtil;
import com.tt.miniapphost.AppbrandContext;

public class GetSystemInfoSyncCtrl extends SyncMsgCtrl {
  public GetSystemInfoSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      return makeOkMsg(SystemInfoUtil.getSystemInfo((Context)AppbrandContext.getInst().getApplicationContext(), AppbrandContext.getInst().isGame()));
    } catch (Exception exception) {
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "getSystemInfoSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\GetSystemInfoSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */