package com.tt.miniapp.webapp.api.async;

import android.app.Activity;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.util.InputMethodUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;

public class ApiEndEditing extends b {
  public ApiEndEditing(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      InputMethodUtil.hideSoftKeyboard((Activity)AppbrandContext.getInst().getCurrentActivity());
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_endEditing", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "endEditing";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webapp\api\async\ApiEndEditing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */