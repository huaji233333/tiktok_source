package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONObject;

public class WebInsertTextAreaHandler extends WebEventHandler {
  public WebInsertTextAreaHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    AppBrandLogger.d("tma_WebInsertTextAreaHandler", new Object[] { "params ", this.mArgs, " mCallbackId ", Integer.valueOf(this.mCallBackId) });
    final int viewId = ComponentIDCreator.create();
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            WebInsertTextAreaHandler.this.mRender.getNativeViewManager().addView(viewId, "textarea", WebInsertTextAreaHandler.this.mArgs, null);
          }
        });
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", buildErrorMsg("insertTextArea", "ok"));
      jSONObject.put("inputId", i);
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_WebInsertTextAreaHandler", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "insertTextArea";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\WebInsertTextAreaHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */