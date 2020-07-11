package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import d.f.b.l;
import org.json.JSONException;
import org.json.JSONObject;

public final class SaveLogEventHandler extends WebEventHandler {
  private final String params;
  
  public SaveLogEventHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
    this.params = paramString;
  }
  
  private final void handleSaveLog(String paramString) {
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      String str = jSONObject.keys().next();
      AutoTestManager autoTestManager = (AutoTestManager)AppbrandApplicationImpl.getInst().getService(AutoTestManager.class);
      l.a(str, "key");
      AutoTestManager.addEventWithValue$default(autoTestManager, str, Long.valueOf(jSONObject.getLong(str)), 0L, 4, null);
      autoTestManager.addCalculator(str, AutoTestManager.Companion.firstValueCalculator(str));
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  public final String act() {
    String str = this.params;
    if (str != null)
      handleSaveLog(str); 
    return "";
  }
  
  public final String getApiName() {
    return "saveLog";
  }
  
  public final String getParams() {
    return this.params;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\SaveLogEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */