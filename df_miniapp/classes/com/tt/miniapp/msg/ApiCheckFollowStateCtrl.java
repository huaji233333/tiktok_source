package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.follow.CheckFollowMethodImpl;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCheckFollowStateCtrl extends b {
  public ApiCheckFollowStateCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    CheckFollowMethodImpl.requestFollowState(new CheckFollowMethodImpl.FollowResultCallback() {
          public void failed(Throwable param1Throwable) {
            ApiCheckFollowStateCtrl.this.callbackFail(param1Throwable);
          }
          
          public void success(boolean param1Boolean) {
            JSONObject jSONObject = new JSONObject();
            try {
              jSONObject.put("result", param1Boolean);
              ApiCheckFollowStateCtrl.this.callbackOk(jSONObject);
              return;
            } catch (JSONException jSONException) {
              ApiCheckFollowStateCtrl.this.callbackFail((Throwable)jSONException);
              return;
            } 
          }
        });
  }
  
  public String getActionName() {
    return "checkFollowState";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiCheckFollowStateCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */