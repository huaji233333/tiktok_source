package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.extraWeb.control.ModalWebViewControl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiOperateModalWebViewStateCtrl extends b {
  public ApiOperateModalWebViewStateCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    boolean bool = false;
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      int i = jSONObject.optInt("id", -1);
      int j = jSONObject.optInt("show");
      if (j == 1)
        bool = true; 
      ModalWebViewControl.getInst().operateModalWebViewShowState(i, bool, new ModalWebViewControl.OperateModalWebViewShowStateListener() {
            public void onOperateShowStateFinish(boolean param1Boolean) {
              if (param1Boolean) {
                ApiOperateModalWebViewStateCtrl.this.callbackOk();
                return;
              } 
              ApiOperateModalWebViewStateCtrl.this.callbackFailWithErrorCode(1002, "invalid webview id");
            }
          });
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ApiHandler", new Object[] { exception });
      callbackFailWithErrorCode(1003, a.a(exception));
      return;
    } 
  }
  
  public void callbackFailWithErrorCode(int paramInt, String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errCode", paramInt);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiHandler", new Object[] { jSONException });
    } 
    callbackFail(paramString, jSONObject);
  }
  
  public String getActionName() {
    return "operateModalWebviewState";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiOperateModalWebViewStateCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */