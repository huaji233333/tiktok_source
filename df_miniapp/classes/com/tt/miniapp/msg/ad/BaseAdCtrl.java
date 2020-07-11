package com.tt.miniapp.msg.ad;

import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.j;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.e.e;
import org.json.JSONObject;

public abstract class BaseAdCtrl extends b {
  public BaseAdCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public static JSONObject buildErrorState(String paramString1, int paramInt, String paramString2) {
    AppBrandLogger.d("ApiHandler", new Object[] { "adUnitId", paramString1, "errMsg", paramString2, "errCode", Integer.valueOf(paramInt) });
    return (new JsonBuilder()).put("adUnitId", paramString1).put("state", "error").put("data", (new JsonBuilder()).put("errCode", Integer.valueOf(paramInt)).put("errMsg", paramString2).build()).build();
  }
  
  public static void notifyStateChanged(String paramString1, String paramString2) {
    j j = AppbrandApplication.getInst().getJsBridge();
    if (j == null)
      return; 
    j.sendMsgToJsCore(paramString1, paramString2);
  }
  
  protected abstract String getEventType();
  
  protected void notifyErrorState(String paramString1, int paramInt, String paramString2) {
    JSONObject jSONObject = buildErrorState(paramString1, paramInt, paramString2);
    notifyStateChanged(getEventType(), String.valueOf(jSONObject));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ad\BaseAdCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */