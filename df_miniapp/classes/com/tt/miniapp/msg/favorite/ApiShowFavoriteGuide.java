package com.tt.miniapp.msg.favorite;

import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.d;
import com.tt.miniapp.favorite.FavoriteGuideModel;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiShowFavoriteGuide extends b {
  public ApiShowFavoriteGuide(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      final FavoriteGuideModel model = new FavoriteGuideModel(jSONObject.optString("type", "bar"), jSONObject.optString("content", ""), jSONObject.optString("position", "bottom"));
      final MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
      if (miniappHostBase != null) {
        AppbrandContext.mainHandler.post(new Runnable() {
              public void run() {
                IActivityProxy iActivityProxy = miniappHostBase.getActivityProxy();
                if (iActivityProxy != null) {
                  d d = iActivityProxy.showFavoriteGuide(model);
                  ApiShowFavoriteGuide.this.callbackExtraInfoMsg(d.a, d.b);
                  return;
                } 
                ApiShowFavoriteGuide.this.callbackExtraInfoMsg(false, "common env error");
              }
            });
        return;
      } 
      callbackExtraInfoMsg(false, "common env error");
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("ApiShowFavoriteGuide", "showFavoriteGuide", (Throwable)jSONException);
      callbackExtraInfoMsg(false, "json params error");
      return;
    } 
  }
  
  public String getActionName() {
    return "showFavoriteGuide";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\favorite\ApiShowFavoriteGuide.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */