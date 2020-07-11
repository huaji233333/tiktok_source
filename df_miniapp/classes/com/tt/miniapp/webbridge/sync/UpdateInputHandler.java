package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONObject;

public class UpdateInputHandler extends WebEventHandler {
  public UpdateInputHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      if (this.mRender == null)
        return makeFailMsg("render is null"); 
      final int inputId = (new JSONObject(this.mArgs)).optInt("inputId");
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              if (UpdateInputHandler.this.mRender != null)
                UpdateInputHandler.this.mRender.getNativeViewManager().updateView(inputId, UpdateInputHandler.this.mArgs, null); 
            }
          });
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", buildErrorMsg("updateInput", "ok"));
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_UpdateInputHandler", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "updateInput";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\UpdateInputHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */