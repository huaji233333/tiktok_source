package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONObject;

public abstract class BasePickerEventHandler extends WebEventHandler {
  public BasePickerEventHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  protected void makeCancelMsg(String paramString) {
    AppBrandLogger.d("tma_BasePickerEventHandler", new Object[] { "timePicker onCancel" });
    try {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", buildErrorMsg(paramString, "cancel"));
      AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(this.mRender.getWebViewId(), this.mCallBackId, jSONObject.toString());
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_BasePickerEventHandler", exception.getStackTrace());
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\BasePickerEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */