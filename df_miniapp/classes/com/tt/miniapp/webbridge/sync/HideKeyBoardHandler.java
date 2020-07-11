package com.tt.miniapp.webbridge.sync;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.component.nativeview.Input;
import com.tt.miniapp.component.nativeview.NativeViewManager;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.JsonBuilder;
import org.json.JSONObject;

public class HideKeyBoardHandler extends WebEventHandler {
  public HideKeyBoardHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      final int inputId = (new JSONObject(this.mArgs)).optInt("inputId");
      if (this.mRender == null)
        return makeFailMsg("current render is null"); 
      NativeViewManager nativeViewManager = this.mRender.getNativeViewManager();
      if (nativeViewManager == null)
        return makeFailMsg("native view manager is null"); 
      if (i <= 0)
        return makeFailMsg("input id error"); 
      final View view = nativeViewManager.getView(i);
      if (view instanceof EditText) {
        InputMethodUtil.hideSoftKeyboard((EditText)view, (Context)AppbrandContext.getInst().getApplicationContext());
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                View view = view;
                if (view instanceof Input) {
                  Input input = (Input)view;
                  JSONObject jSONObject = (new JsonBuilder()).put("inputId", Integer.valueOf(inputId)).put("cursor", Integer.valueOf(input.getCursor())).put("value", input.getValue()).build();
                  WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
                  if (webViewManager != null) {
                    webViewManager.publishDirectly(HideKeyBoardHandler.this.mRender.getWebViewId(), "onKeyboardConfirm", jSONObject.toString());
                    webViewManager.publishDirectly(HideKeyBoardHandler.this.mRender.getWebViewId(), "onKeyboardComplete", jSONObject.toString());
                  } 
                  HideKeyBoardHandler.this.mRender.getNativeViewManager().removeView(inputId, null);
                } 
              }
            });
        return CharacterUtils.empty();
      } 
      return makeFailMsg("input id error");
    } catch (Exception exception) {
      AppBrandLogger.e("tma_HideKeyBoardHandler", new Object[] { "", exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "hideKeyboard";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\HideKeyBoardHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */