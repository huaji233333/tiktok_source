package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.storage.Storage;
import com.tt.option.e.e;
import org.json.JSONException;

public class ApiClearStorageCtrl extends b {
  public ApiClearStorageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    boolean bool = Storage.clearStorage();
    if (bool) {
      callbackOk();
    } else {
      callbackFail("clear storage fail");
    } 
    if ((DebugManager.getInst()).mRemoteDebugEnable)
      try {
        DebugManager.getInst().getRemoteDebugManager().clearStorage(0, bool);
        return;
      } catch (JSONException jSONException) {
        return;
      }  
  }
  
  public String getActionName() {
    return "clearStorage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiClearStorageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */