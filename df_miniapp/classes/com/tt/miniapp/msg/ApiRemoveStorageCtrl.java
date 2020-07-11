package com.tt.miniapp.msg;

import com.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiRemoveStorageCtrl extends b {
  public ApiRemoveStorageCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("key");
      boolean bool = Storage.removeStorage(str);
      if (bool) {
        callbackOk();
      } else {
        callbackFail(a.a("remove storage fail,key == %s", new Object[] { str }));
      } 
      if ((DebugManager.getInst()).mRemoteDebugEnable)
        DebugManager.getInst().getRemoteDebugManager().removeDOMStorageItem(0, bool, str); 
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "ApiHandler", jSONException.getStackTrace());
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "removeStorage";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiRemoveStorageCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */