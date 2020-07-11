package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.titlebar.TitleBarControl;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiSetMenuButtonVisibilityCtrl extends b {
  public ApiSetMenuButtonVisibilityCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      final JSONObject jsonObject = new JSONObject(this.mArgs);
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              TitleBarControl.getInst().forceSetCapsuleButtonState(jsonObject.optBoolean("visible", true));
              ApiSetMenuButtonVisibilityCtrl.this.callbackOk();
            }
          });
      return;
    } catch (JSONException jSONException) {
      DebugUtil.outputError("ApiSetMenuButtonVisibilityCtrl", new Object[] { jSONException });
      callbackFail(a.a(this.mArgs));
      return;
    } 
  }
  
  public String getActionName() {
    return "setMenuButtonVisibility";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiSetMenuButtonVisibilityCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */