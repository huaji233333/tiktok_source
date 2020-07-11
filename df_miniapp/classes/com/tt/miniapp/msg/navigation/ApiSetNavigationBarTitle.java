package com.tt.miniapp.msg.navigation;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.util.NavigationBarUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiSetNavigationBarTitle extends b {
  public ApiSetNavigationBarTitle(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      final String title = (new JSONObject(this.mArgs)).optString("title");
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              boolean bool = ApiSetNavigationBarTitle.this.customNavigatonBar();
              AppBrandLogger.d("tma_ApiSetNavigationBarTitle", new Object[] { "isCustomNavigatonBar ", Boolean.valueOf(bool) });
              if (!bool) {
                WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
                if (webViewManager != null) {
                  WebViewManager.IRender iRender = webViewManager.getCurrentIRender();
                  if (iRender != null) {
                    iRender.setNavigationBarTitle(title);
                    return;
                  } 
                  ApiSetNavigationBarTitle.this.callbackFail("current render is null");
                  return;
                } 
                ApiSetNavigationBarTitle.this.callbackFail("WebViewManager is null");
              } 
            }
          });
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiSetNavigationBarTitle", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public boolean customNavigatonBar() {
    return TextUtils.equals(NavigationBarUtil.getNavigationStyle(), "custom");
  }
  
  public String getActionName() {
    return "setNavigationBarTitle";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\navigation\ApiSetNavigationBarTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */