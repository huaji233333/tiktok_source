package com.tt.miniapp.webbridge.sync;

import android.app.Activity;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class RemoveTextAreaHandler extends WebEventHandler {
  public RemoveTextAreaHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      final int inputId = (new JSONObject(this.mArgs)).optInt("inputId");
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              InputMethodUtil.hideSoftKeyboard((Activity)AppbrandContext.getInst().getCurrentActivity());
              RemoveTextAreaHandler.this.mRender.getNativeViewManager().removeView(inputId, null);
            }
          });
      return CharacterUtils.empty();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "RemoveTextAreaHandler", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "removeTextArea";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\RemoveTextAreaHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */