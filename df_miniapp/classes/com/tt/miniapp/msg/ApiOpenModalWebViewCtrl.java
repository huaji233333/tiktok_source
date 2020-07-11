package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.extraWeb.control.ModalWebViewControl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiOpenModalWebViewCtrl extends b {
  public ApiOpenModalWebViewCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    ModalWebViewControl.getInst().openModalWebView(this.mArgs, new ModalWebViewControl.OpenModalWebViewListener() {
          public void onOpenModalWebView(boolean param1Boolean, int param1Int1, String param1String, int param1Int2) {
            JSONObject jSONObject1;
            if (param1Boolean) {
              jSONObject1 = new JSONObject();
              try {
                jSONObject1.put("id", String.valueOf(param1Int2));
              } catch (JSONException jSONException) {
                AppBrandLogger.e("ApiOpenModalWebViewCtrl", new Object[] { jSONException });
              } 
              ApiOpenModalWebViewCtrl.this.callbackOk(jSONObject1);
              AppBrandMonitor.statusRate("mp_modal_webview_load", 0, null);
              return;
            } 
            ModalWebViewControl.getInst().closeModalWebView(param1Int2);
            JSONObject jSONObject2 = new JSONObject();
            try {
              jSONObject2.put("errCode", param1Int1);
            } catch (JSONException jSONException) {
              AppBrandLogger.e("ApiOpenModalWebViewCtrl", new Object[] { jSONException });
            } 
            ApiOpenModalWebViewCtrl.this.callbackFail((String)jSONObject1, jSONObject2);
            AppBrandMonitor.statusRate("mp_modal_webview_load", 9300, jSONObject2);
          }
        });
  }
  
  public String getActionName() {
    return "openModalWebview";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiOpenModalWebViewCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */