package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONObject;

public class UpdateTextAreaHandler extends WebEventHandler {
  public UpdateTextAreaHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              try {
                int i = (new JSONObject(UpdateTextAreaHandler.this.mArgs)).optInt("inputId");
                UpdateTextAreaHandler.this.mRender.getNativeViewManager().updateView(i, UpdateTextAreaHandler.this.mArgs, null);
                return;
              } catch (Exception exception) {
                AppBrandLogger.stacktrace(6, "WebEventHandler", exception.getStackTrace());
                return;
              } 
            }
          });
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", buildErrorMsg("updateTextArea", "ok"));
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "WebEventHandler", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "updateTextArea";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\UpdateTextAreaHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */