package com.tt.miniapp.msg.sync;

import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;

public class ClearStorageSync extends SyncMsgCtrl {
  public ClearStorageSync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    boolean bool = Storage.clearStorage();
    try {
      if ((DebugManager.getInst()).mRemoteDebugEnable)
        DebugManager.getInst().getRemoteDebugManager().clearStorage(0, bool); 
      return bool ? makeOkMsg() : makeFailMsg("clear storage fail");
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrl", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "clearStorageSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ClearStorageSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */