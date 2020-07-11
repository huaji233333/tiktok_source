package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;

public class ApiStopPullDownRefreshCtrl extends b {
  public ApiStopPullDownRefreshCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
            if (webViewManager == null) {
              ApiStopPullDownRefreshCtrl.this.callbackFail("WebViewManager is null");
              return;
            } 
            WebViewManager.IRender iRender = webViewManager.getCurrentIRender();
            if (iRender == null) {
              ApiStopPullDownRefreshCtrl.this.callbackFail("current render is null");
              return;
            } 
            iRender.onStopPullDownRefresh();
            ApiStopPullDownRefreshCtrl.this.callbackOk();
          }
        });
  }
  
  public String getActionName() {
    return "stopPullDownRefresh";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiStopPullDownRefreshCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */