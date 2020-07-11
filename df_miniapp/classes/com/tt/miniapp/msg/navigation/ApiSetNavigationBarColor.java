package com.tt.miniapp.msg.navigation;

import android.graphics.Color;
import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiSetNavigationBarColor extends b {
  public ApiSetNavigationBarColor(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      final String frontColor = jSONObject.optString("frontColor");
      final String backgroundColor = jSONObject.optString("backgroundColor");
      AppBrandLogger.i("ApiSetNavigationBarColor", new Object[] { "fontColor ", str1, "backgroundColor", str2 });
      if (!TextUtils.equals(str1, "#ffffff") && !TextUtils.equals(str1, "#000000")) {
        callbackExtraInfoMsg(false, "frontColor should pass #ffffff or #000000 string");
        return;
      } 
      if (TextUtils.isEmpty(str2)) {
        callbackExtraInfoMsg(false, "backgroundColor should pass valid color String");
        return;
      } 
      Color.parseColor(str2);
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              WebViewManager.IRender iRender = AppbrandApplicationImpl.getInst().getWebViewManager().getCurrentIRender();
              if (iRender != null) {
                iRender.setNavigationBarColor(frontColor, backgroundColor);
                ApiSetNavigationBarColor.this.callbackDefaultMsg(true);
                return;
              } 
              ApiSetNavigationBarColor.this.callbackDefaultMsg(false);
            }
          });
      return;
    } catch (IllegalArgumentException illegalArgumentException) {
      AppBrandLogger.stacktrace(6, "ApiSetNavigationBarColor", illegalArgumentException.getStackTrace());
      callbackExtraInfoMsg(false, "backgroundColor should pass valid color String");
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "ApiSetNavigationBarColor", exception.getStackTrace());
      callbackDefaultMsg(false);
      return;
    } 
  }
  
  public String getActionName() {
    return "setNavigationBarColor";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\navigation\ApiSetNavigationBarColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */