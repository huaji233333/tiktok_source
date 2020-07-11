package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class RemoveHTMLWebViewHandler extends WebEventHandler {
  public RemoveHTMLWebViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      final int htmlId = (new JSONObject(this.mArgs)).optInt("htmlId");
      if (this.mRender != null) {
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                try {
                  if (RemoveHTMLWebViewHandler.this.mRender != null) {
                    RemoveHTMLWebViewHandler.this.mRender.getNativeViewManager().removeView(htmlId, null);
                    RemoveHTMLWebViewHandler.this.invokeHandler(RemoveHTMLWebViewHandler.this.makeOkMsg());
                  } 
                  return;
                } catch (Exception exception) {
                  AppBrandLogger.stacktrace(6, "RemoveHTMLWebViewHandler", exception.getStackTrace());
                  try {
                    RemoveHTMLWebViewHandler.this.invokeHandler(RemoveHTMLWebViewHandler.this.makeFailMsg(exception));
                    return;
                  } catch (Exception exception1) {
                    AppBrandLogger.e("RemoveHTMLWebViewHandler", new Object[] { "removeHTMLWebView", exception1 });
                    return;
                  } 
                } 
              }
            });
      } else {
        AppBrandLogger.e("RemoveHTMLWebViewHandler", new Object[] { "render is null" });
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "RemoveHTMLWebViewHandler", exception.getStackTrace());
      invokeHandler(makeFailMsg(exception));
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "removeHTMLWebView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\RemoveHTMLWebViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */