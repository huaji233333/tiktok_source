package com.tt.miniapp.webapp.api.async;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.webapp.TTWebAppViewWindow;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import java.lang.ref.WeakReference;
import org.json.JSONObject;

public class ApiSetStatusBarStyle extends b {
  public ApiSetStatusBarStyle(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      String str = (new JSONObject(this.mArgs)).optString("style");
      WeakReference<TTWebAppViewWindow> weakReference = TTWebAppViewWindow.getWeakRef();
      if (weakReference != null && weakReference.get() != null)
        ((TTWebAppViewWindow)weakReference.get()).setTitleMenuBarColor(str); 
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_setStatusBarStyle", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "setStatusBarStyle";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\api\async\ApiSetStatusBarStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */