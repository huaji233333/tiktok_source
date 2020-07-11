package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;

public class ApiStartPullDownRefreshCtrl extends b {
  public ApiStartPullDownRefreshCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager == null) {
      callbackFail("WebViewManager is null");
      return;
    } 
    final WebViewManager.IRender iRender = webViewManager.getCurrentIRender();
    if (iRender == null) {
      callbackFail("current render is null");
      return;
    } 
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            iRender.onStartPullDownRefresh();
            ApiStartPullDownRefreshCtrl.this.callbackOk();
          }
        });
  }
  
  public String getActionName() {
    return "startPullDownRefresh";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiStartPullDownRefreshCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */