package com.tt.miniapp.webbridge.sync;

import android.text.TextUtils;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class ShowKeyboardHandler extends WebEventHandler {
  public ShowKeyboardHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      if (this.mRender == null)
        return makeFailMsg("render is null"); 
      JSONObject jSONObject = new JSONObject(this.mArgs);
      final String type = jSONObject.optString("type");
      boolean bool = TextUtils.equals(str, "text");
      if (bool || TextUtils.equals(str, "digit") || TextUtils.equals(str, "number") || TextUtils.equals(str, "idcard")) {
        final int inputId = ComponentIDCreator.create();
        final JSONObject resObject = new JSONObject();
        jSONObject1.put("errMsg", buildErrorMsg("showKeyboard", "ok"));
        jSONObject1.put("inputId", i);
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                if (ShowKeyboardHandler.this.mRender != null) {
                  ShowKeyboardHandler.this.mRender.getNativeViewManager().addView(viewId, type, ShowKeyboardHandler.this.mArgs, null);
                  ShowKeyboardHandler.this.invokeHandler(resObject.toString());
                } 
              }
            });
      } 
      if (TextUtils.isEmpty(str)) {
        final int inputId = jSONObject.optInt("inputId");
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                if (ShowKeyboardHandler.this.mRender != null)
                  ShowKeyboardHandler.this.mRender.showKeyboard(inputId); 
              }
            });
      } 
      return CharacterUtils.empty();
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ShowKeyboardHandler", new Object[] { "", exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "showKeyboard";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ShowKeyboardHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */