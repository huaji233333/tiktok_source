package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.util.JsRuntimeErrorReporter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiReportJsRuntimeErrorCtrl extends b {
  public ApiReportJsRuntimeErrorCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      JsRuntimeErrorReporter.getInstance().report(jSONObject);
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ApiReportJsRuntimeErrorCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "reportJsRuntimeError";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiReportJsRuntimeErrorCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */