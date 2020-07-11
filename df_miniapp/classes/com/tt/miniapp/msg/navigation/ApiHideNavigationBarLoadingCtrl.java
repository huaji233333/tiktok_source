package com.tt.miniapp.msg.navigation;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.util.NavigationBarUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;

public class ApiHideNavigationBarLoadingCtrl extends b {
  public ApiHideNavigationBarLoadingCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (TextUtils.equals("custom", NavigationBarUtil.getNavigationStyle())) {
      callbackOk("custom navigation style");
      return;
    } 
    WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
    if (webViewManager == null) {
      callbackFail("webview manager is null");
      return;
    } 
    final WebViewManager.IRender currentIRender = webViewManager.getCurrentIRender();
    if (iRender == null) {
      callbackFail("current render is null");
      return;
    } 
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            try {
              return;
            } finally {
              Exception exception = null;
              AppBrandLogger.e("ApiHideNavigationBarLoadingCtrl", new Object[] { exception });
              ApiHideNavigationBarLoadingCtrl.this.callbackFail(exception);
            } 
          }
        });
  }
  
  public String getActionName() {
    return "hideNavigationBarLoading";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\navigation\ApiHideNavigationBarLoadingCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */