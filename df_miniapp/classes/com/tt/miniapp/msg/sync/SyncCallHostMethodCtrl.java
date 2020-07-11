package com.tt.miniapp.msg.sync;

import android.app.Activity;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.hostmethod.HostMethodManager;
import org.json.JSONObject;

public class SyncCallHostMethodCtrl extends SyncMsgCtrl {
  public SyncCallHostMethodCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mParams);
      null = jSONObject.optString("method");
      jSONObject = jSONObject.optJSONObject("extra");
      if (!ApiPermissionManager.canUseHostMethod(null))
        return makeFailMsg("platform auth deny"); 
      MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (miniappHostBase != null) {
        null = HostMethodManager.getInstance().invokeJavaMethodSync((Activity)miniappHostBase, null, jSONObject);
        return (null != null) ? null : makeFailMsg("result is null");
      } 
      return makeFailMsg("activity is null");
    } catch (Exception exception) {
      AppBrandLogger.e("tma_SyncCallHostMethodCtrl", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return "callHostMethodSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\SyncCallHostMethodCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */