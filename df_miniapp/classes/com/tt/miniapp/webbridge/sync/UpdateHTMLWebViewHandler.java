package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class UpdateHTMLWebViewHandler extends WebEventHandler {
  public String TAG = "UpdateHTMLWebViewHandler";
  
  public UpdateHTMLWebViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      final int htmlId = (new JSONObject(this.mArgs)).optInt("htmlId");
      if (this.mRender != null) {
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                new JSONObject();
                try {
                  if (UpdateHTMLWebViewHandler.this.mRender != null) {
                    UpdateHTMLWebViewHandler.this.mRender.getNativeViewManager().updateView(htmlId, UpdateHTMLWebViewHandler.this.mArgs, null);
                    UpdateHTMLWebViewHandler.this.invokeHandler(UpdateHTMLWebViewHandler.this.makeOkMsg());
                  } 
                  return;
                } catch (Exception exception) {
                  AppBrandLogger.stacktrace(6, UpdateHTMLWebViewHandler.this.TAG, exception.getStackTrace());
                  try {
                    UpdateHTMLWebViewHandler.this.invokeHandler(UpdateHTMLWebViewHandler.this.makeFailMsg(exception));
                    return;
                  } catch (Exception exception1) {
                    AppBrandLogger.e(UpdateHTMLWebViewHandler.this.TAG, new Object[] { "updateHTMLWebView", exception1 });
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
    return "updateHTMLWebView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\UpdateHTMLWebViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */