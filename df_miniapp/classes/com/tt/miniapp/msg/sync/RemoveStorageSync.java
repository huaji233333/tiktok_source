package com.tt.miniapp.msg.sync;

import com.a;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoveStorageSync extends SyncMsgCtrl {
  public RemoveStorageSync(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      null = (new JSONObject(this.mParams)).optString("key");
      boolean bool = Storage.removeStorage(null);
      if ((DebugManager.getInst()).mRemoteDebugEnable)
        DebugManager.getInst().getRemoteDebugManager().removeDOMStorageItem(0, bool, null); 
      return bool ? makeOkMsg() : makeFailMsg(a.a("remove storage fail,key == %s", new Object[] { null }));
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "SyncMsgCtrl", jSONException.getStackTrace());
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String getName() {
    return "removeStorageSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\RemoveStorageSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */