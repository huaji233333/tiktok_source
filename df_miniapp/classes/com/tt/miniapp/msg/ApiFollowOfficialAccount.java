package com.tt.miniapp.msg;

import android.app.Activity;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.follow.FollowMethodImpl;
import com.tt.miniapp.follow.FollowResultCallback;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiFollowOfficialAccount extends b {
  public ApiFollowOfficialAccount(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void startFollowProcedure(Activity paramActivity) {
    (new FollowMethodImpl(new FollowResultCallback() {
          public void callBackResult(int param1Int, String param1String) {
            JSONObject jSONObject = new JSONObject();
            boolean bool = false;
            try {
              jSONObject.put("errCode", param1Int);
            } finally {
              Exception exception = null;
            } 
            if (bool) {
              ApiFollowOfficialAccount.this.callbackOk(param1String, jSONObject);
              return;
            } 
            ApiFollowOfficialAccount.this.callbackFail(param1String, jSONObject);
          }
        })).startFollow(paramActivity);
  }
  
  public void act() {
    AppBrandLogger.d("ApiHandler", new Object[] { "ApiFollowOfficialAccount" });
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      callbackFail("activity is null");
      return;
    } 
    startFollowProcedure((Activity)miniappHostBase);
  }
  
  public String getActionName() {
    return "followOfficialAccount";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiFollowOfficialAccount.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */