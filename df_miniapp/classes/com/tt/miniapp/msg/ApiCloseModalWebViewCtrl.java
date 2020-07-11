package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.extraWeb.control.ModalWebViewControl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCloseModalWebViewCtrl extends b {
  public ApiCloseModalWebViewCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void callbackFailWithErrorCode(int paramInt, String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errCode", paramInt);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiCloseModalWebViewCtrl", new Object[] { jSONException });
    } 
    callbackFail(paramString, jSONObject);
  }
  
  public void act() {
    try {
      int i = (new JSONObject(this.mArgs)).optInt("id", -1);
      if (ModalWebViewControl.getInst().closeModalWebView(i)) {
        callbackOk();
        return;
      } 
      callbackFailWithErrorCode(1002, "invalid webview id");
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ApiCloseModalWebViewCtrl", new Object[] { exception });
      callbackFailWithErrorCode(1003, a.a(exception));
      return;
    } 
  }
  
  public String getActionName() {
    return "closeModalWebview";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiCloseModalWebViewCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */