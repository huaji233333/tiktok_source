package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.webbridge.ComponentIDCreator;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.option.e.e;
import org.json.JSONObject;

public class InsertADHTMLWebViewHandler extends WebEventHandler {
  public InsertADHTMLWebViewHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    if (ApiPermissionManager.interceptAdApi(getApiName(), this.mCallBackId, new e() {
          public void callback(int param1Int, String param1String) {
            InsertADHTMLWebViewHandler.this.invokeHandler(param1String);
          }
        }))
      return CharacterUtils.empty(); 
    try {
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              JSONObject jSONObject = new JSONObject();
              try {
                AppBrandLogger.d("tma_InsertADHTMLWebViewHandler", new Object[] { "insertHtml webviewId ", Integer.valueOf(AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender().getWebViewId()) });
                JSONObject jSONObject1 = new JSONObject(InsertADHTMLWebViewHandler.this.mArgs);
                int i = ComponentIDCreator.create();
                jSONObject1.put("htmlId", i);
                InsertADHTMLWebViewHandler.this.mRender.getNativeViewManager().addView(i, "adWebHtml", jSONObject1.toString(), null);
                jSONObject.put("errMsg", InsertADHTMLWebViewHandler.this.buildErrorMsg("insertAdHTMLWebView", "ok"));
                jSONObject.put("htmlId", i);
                AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(InsertADHTMLWebViewHandler.this.mRender.getWebViewId(), InsertADHTMLWebViewHandler.this.mCallBackId, jSONObject.toString());
                return;
              } catch (Exception exception) {
                AppBrandLogger.stacktrace(6, "tma_InsertADHTMLWebViewHandler", exception.getStackTrace());
                try {
                  jSONObject.put("errMsg", InsertADHTMLWebViewHandler.this.buildErrorMsg("insertAdHTMLWebView", "fail"));
                  AppbrandApplicationImpl.getInst().getWebViewManager().invokeHandler(InsertADHTMLWebViewHandler.this.mRender.getWebViewId(), InsertADHTMLWebViewHandler.this.mCallBackId, jSONObject.toString());
                  return;
                } catch (Exception exception1) {
                  AppBrandLogger.e("tma_InsertADHTMLWebViewHandler", new Object[] { "insertAdHTMLWebView", exception1 });
                  return;
                } 
              } 
            }
          });
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_InsertADHTMLWebViewHandler", exception.getStackTrace());
    } 
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "insertAdHTMLWebView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\InsertADHTMLWebViewHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */