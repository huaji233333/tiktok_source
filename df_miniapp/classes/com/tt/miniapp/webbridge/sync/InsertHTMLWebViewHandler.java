package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import org.json.JSONObject;

public class InsertHTMLWebViewHandler extends WebEventHandler {
  public InsertHTMLWebViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    try {
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              JSONObject jSONObject = new JSONObject();
              try {
                AppBrandLogger.d("tma_InsertHTMLWebViewHandle", new Object[] { "insertHtml webviewId ", Integer.valueOf(AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender().getWebViewId()) });
                JSONObject jSONObject1 = new JSONObject(InsertHTMLWebViewHandler.this.mArgs);
                int i = ComponentIDCreator.create();
                jSONObject1.put("htmlId", i);
                InsertHTMLWebViewHandler.this.mRender.getNativeViewManager().addView(i, "webHtml", jSONObject1.toString(), null);
                jSONObject.put("errMsg", InsertHTMLWebViewHandler.this.buildErrorMsg("insertHTMLWebView", "ok"));
                jSONObject.put("htmlId", i);
                AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(InsertHTMLWebViewHandler.this.mRender.getWebViewId(), InsertHTMLWebViewHandler.this.mCallBackId, jSONObject.toString());
                return;
              } catch (Exception exception) {
                AppBrandLogger.stacktrace(6, "tma_InsertHTMLWebViewHandle", exception.getStackTrace());
                try {
                  jSONObject.put("errMsg", InsertHTMLWebViewHandler.this.buildErrorMsg("insertHTMLWebView", "fail"));
                  AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(InsertHTMLWebViewHandler.this.mRender.getWebViewId(), InsertHTMLWebViewHandler.this.mCallBackId, jSONObject.toString());
                  return;
                } catch (Exception exception1) {
                  AppBrandLogger.e("tma_InsertHTMLWebViewHandle", new Object[] { "insertHTMLWebView", exception1 });
                  return;
                } 
              } 
            }
          });
      return CharacterUtils.empty();
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_InsertHTMLWebViewHandle", exception.getStackTrace());
      return makeFailMsg(exception);
    } 
  }
  
  public String getApiName() {
    return "insertHTMLWebView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\InsertHTMLWebViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */