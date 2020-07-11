package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public class GetRealFilePathWebViewHandler extends WebEventHandler {
  public GetRealFilePathWebViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      null = (new JSONObject(this.mArgs)).optString("protocolPath");
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("absPath", null);
      return makeOkMsg(jSONObject);
    } catch (Exception exception) {
      AppBrandLogger.e("WebEventHandler", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "protocolPathToAbsPath";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\GetRealFilePathWebViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */