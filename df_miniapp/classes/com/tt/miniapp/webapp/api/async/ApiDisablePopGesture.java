package com.tt.miniapp.webapp.api.async;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.webapp.TTWebAppViewWindow;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiDisablePopGesture extends b {
  public ApiDisablePopGesture(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      final boolean isEnableSwipeBack;
      if ((new JSONObject(this.mArgs)).optInt("disable") == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      final TTWebAppViewWindow ttWebAppViewWindow = TTWebAppViewWindow.getWeakRef().get();
      if (tTWebAppViewWindow != null)
        ThreadUtil.runOnUIThread(new Runnable() {
              public void run() {
                ttWebAppViewWindow.setIsEnableSwipeBack(isEnableSwipeBack);
              }
            }); 
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_disablePopGesture", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "disablePopGesture";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\api\async\ApiDisablePopGesture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */