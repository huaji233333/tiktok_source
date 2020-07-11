package com.tt.miniapp.webapp.api.async;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiReportCustomEvent extends b {
  public ApiReportCustomEvent(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("event");
      if (str.hashCode() == 1201856762)
        str.equals("mp_load_result"); 
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_reportCustomEvent", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "reportCustomEvent";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\api\async\ApiReportCustomEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */