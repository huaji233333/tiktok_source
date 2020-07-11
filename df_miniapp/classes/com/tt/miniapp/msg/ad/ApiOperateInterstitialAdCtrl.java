package com.tt.miniapp.msg.ad;

import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.ad.c;
import com.tt.option.ad.h;
import org.json.JSONObject;

public class ApiOperateInterstitialAdCtrl extends SyncMsgCtrl {
  public ApiOperateInterstitialAdCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    h h = new h(this.mParams);
    StringBuilder stringBuilder = new StringBuilder("API: ");
    stringBuilder.append(this.mParams);
    AppBrandLogger.d("InterstitialAdCtrl", new Object[] { stringBuilder.toString() });
    if (!HostDependManager.getInst().isSupportAd(c.GAME_INTERSTITIAL)) {
      BaseAdCtrl.notifyStateChanged("onInterstitialAdStateChange", String.valueOf(BaseAdCtrl.buildErrorState(h.a, 1003, "feature is not supported in app")));
      AppBrandLogger.e("InterstitialAdCtrl", new Object[] { "API Not Support" });
      return makeFailMsg("feature is not supported in app");
    } 
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      AppBrandLogger.e("InterstitialAdCtrl", new Object[] { "null activity" });
      return makeFailMsg("activity is not null");
    } 
    IActivityProxy iActivityProxy = miniappHostBase.getActivityProxy();
    if (iActivityProxy == null) {
      AppBrandLogger.e("InterstitialAdCtrl", new Object[] { "null proxy" });
      return makeFailMsg("activity proxy is null");
    } 
    String str = iActivityProxy.onOperateInterstitialAd(h);
    if ("".equals(str))
      return makeOkMsg(); 
    AppBrandLogger.w("InterstitialAdCtrl", new Object[] { "operate failed" });
    JSONObject jSONObject = (new JsonBuilder(str)).build();
    return makeFailMsg(jSONObject.optString("errMsg"), jSONObject);
  }
  
  public String getName() {
    return "operateInterstitialAd";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ad\ApiOperateInterstitialAdCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */