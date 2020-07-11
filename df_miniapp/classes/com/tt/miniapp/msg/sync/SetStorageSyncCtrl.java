package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import java.io.IOException;
import org.json.JSONObject;

public class SetStorageSyncCtrl extends SyncMsgCtrl {
  public SetStorageSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mParams);
      null = jSONObject.optString("key");
      if (TextUtils.isEmpty(null))
        return makeFailMsg(a.b("key")); 
      String str1 = jSONObject.optString("data");
      String str2 = jSONObject.optString("dataType");
      if ((DebugManager.getInst()).mRemoteDebugEnable) {
        String str = Storage.getValue(null);
        boolean bool = Storage.setValue(null, str1, str2);
        DebugManager.getInst().getRemoteDebugManager().setDOMStorageItem(0, bool, null, str, str1);
      } else {
        Storage.setValue(null, str1, str2);
      } 
      return makeOkMsg();
    } catch (IOException iOException) {
      return makeFailMsg(iOException.getMessage());
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_SetStorageSyncCtrl", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "setStorageSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\SetStorageSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */