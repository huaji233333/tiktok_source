package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class InsertMapHandler extends WebEventHandler {
  public InsertMapHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              try {
                AppBrandLogger.d("tma_InsertMapHandler", new Object[] { "insertMap webviewId ", Integer.valueOf(AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender().getWebViewId()) });
                int i = (new JSONObject(InsertMapHandler.this.mArgs)).optInt("htmlId");
                InsertMapHandler.this.mRender.getNativeViewManager().addView(i, "webHtml", InsertMapHandler.this.mArgs, null);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("errMsg", InsertMapHandler.this.buildErrorMsg("insertMap", "ok"));
                AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(InsertMapHandler.this.mRender.getWebViewId(), InsertMapHandler.this.mCallBackId, jSONObject.toString());
                return;
              } catch (Exception exception) {
                AppBrandLogger.stacktrace(6, "tma_InsertMapHandler", exception.getStackTrace());
                return;
              } 
            }
          });
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_InsertMapHandler", exception.getStackTrace());
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "insertHTMLWebView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\InsertMapHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */