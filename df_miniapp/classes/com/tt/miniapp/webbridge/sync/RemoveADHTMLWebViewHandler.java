package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.e;
import org.json.JSONObject;

public class RemoveADHTMLWebViewHandler extends WebEventHandler {
  public RemoveADHTMLWebViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    if (ApiPermissionManager.interceptAdApi(getApiName(), this.mCallBackId, new e() {
          public void callback(int param1Int, String param1String) {
            RemoveADHTMLWebViewHandler.this.invokeHandler(param1String);
          }
        }))
      return CharacterUtils.empty(); 
    try {
      final int htmlId = (new JSONObject(this.mArgs)).optInt("htmlId");
      if (this.mRender != null) {
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                try {
                  if (RemoveADHTMLWebViewHandler.this.mRender != null) {
                    RemoveADHTMLWebViewHandler.this.mRender.getNativeViewManager().removeView(htmlId, null);
                    RemoveADHTMLWebViewHandler.this.invokeHandler(RemoveADHTMLWebViewHandler.this.makeOkMsg());
                  } 
                  return;
                } catch (Exception exception) {
                  AppBrandLogger.stacktrace(6, "RemoveADHTMLWebViewHandler", exception.getStackTrace());
                  try {
                    RemoveADHTMLWebViewHandler.this.invokeHandler(RemoveADHTMLWebViewHandler.this.makeFailMsg(exception));
                    return;
                  } catch (Exception exception1) {
                    AppBrandLogger.e("RemoveADHTMLWebViewHandler", new Object[] { "removeAdHTMLWebView", exception1 });
                    return;
                  } 
                } 
              }
            });
      } else {
        throw new RuntimeException("render is null");
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "RemoveADHTMLWebViewHandler", exception.getStackTrace());
      invokeHandler(makeFailMsg(exception));
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "removeAdHTMLWebView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\RemoveADHTMLWebViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */