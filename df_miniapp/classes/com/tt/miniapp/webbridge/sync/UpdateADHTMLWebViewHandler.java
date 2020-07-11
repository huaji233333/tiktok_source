package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.e;
import org.json.JSONObject;

public class UpdateADHTMLWebViewHandler extends WebEventHandler {
  public String TAG = "UpdateADHTMLWebViewHandler";
  
  public UpdateADHTMLWebViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    if (ApiPermissionManager.interceptAdApi(getApiName(), this.mCallBackId, new e() {
          public void callback(int param1Int, String param1String) {
            UpdateADHTMLWebViewHandler.this.invokeHandler(param1String);
          }
        }))
      return CharacterUtils.empty(); 
    try {
      final int htmlId = (new JSONObject(this.mArgs)).optInt("htmlId");
      if (this.mRender != null) {
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                new JSONObject();
                try {
                  if (UpdateADHTMLWebViewHandler.this.mRender != null) {
                    UpdateADHTMLWebViewHandler.this.mRender.getNativeViewManager().updateView(htmlId, UpdateADHTMLWebViewHandler.this.mArgs, null);
                    UpdateADHTMLWebViewHandler.this.invokeHandler(UpdateADHTMLWebViewHandler.this.makeOkMsg());
                  } 
                  return;
                } catch (Exception exception) {
                  AppBrandLogger.stacktrace(6, UpdateADHTMLWebViewHandler.this.TAG, exception.getStackTrace());
                  try {
                    UpdateADHTMLWebViewHandler.this.invokeHandler(UpdateADHTMLWebViewHandler.this.makeFailMsg(exception));
                    return;
                  } catch (Exception exception1) {
                    AppBrandLogger.e(UpdateADHTMLWebViewHandler.this.TAG, new Object[] { "updateAdHTMLWebView", exception1 });
                    return;
                  } 
                } 
              }
            });
      } else {
        throw new RuntimeException("render is null");
      } 
    } catch (Exception exception) {
      invokeHandler(makeFailMsg(exception));
      AppBrandLogger.stacktrace(6, this.TAG, exception.getStackTrace());
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "updateAdHTMLWebView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\UpdateADHTMLWebViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */