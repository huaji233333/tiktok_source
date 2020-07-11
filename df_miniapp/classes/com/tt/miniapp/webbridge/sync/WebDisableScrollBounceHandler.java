package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class WebDisableScrollBounceHandler extends WebEventHandler {
  public WebDisableScrollBounceHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    boolean bool;
    try {
      bool = (new JSONObject(this.mArgs)).optBoolean("disable");
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "WebEventHandler", jSONException.getStackTrace());
      bool = false;
    } 
    if (this.mRender instanceof AppbrandSinglePage) {
      ((AppbrandSinglePage)this.mRender).setDisableRefresh(bool);
      return makeOkMsg();
    } 
    return makeFailMsg("render type error");
  }
  
  public String getApiName() {
    return "disableScrollBounce";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\WebDisableScrollBounceHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */