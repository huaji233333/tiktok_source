package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONObject;

public class ShowTextAreaKeyboardHandler extends WebEventHandler {
  public ShowTextAreaKeyboardHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      final int viewId = (new JSONObject(this.mArgs)).optInt("inputId");
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              try {
                ShowTextAreaKeyboardHandler.this.mRender.getNativeViewManager().updateView(viewId, ShowTextAreaKeyboardHandler.this.mArgs, null);
                return;
              } catch (Exception exception) {
                AppBrandLogger.stacktrace(6, "tma_ShowTextAreaKeyboardHandler", exception.getStackTrace());
                return;
              } 
            }
          });
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", buildErrorMsg("updateTextArea", "ok"));
      return jSONObject.toString();
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ShowTextAreaKeyboardHandler", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "showTextAreaKeyboard";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ShowTextAreaKeyboardHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */