package com.tt.miniapp.msg.ad;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.ad.h;
import com.tt.option.e.e;

public class ApiOperateBannerAdCtrl extends BannerAdCtrl {
  public ApiOperateBannerAdCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    final h adModel = new h(this.mArgs);
    StringBuilder stringBuilder = new StringBuilder("operateBannerAd:");
    stringBuilder.append(h);
    AppBrandLogger.d("tma_ApiOperateBannerAdCtrl", new Object[] { stringBuilder.toString() });
    if (!isSupportGameBannerAd()) {
      notifyErrorState(h.a, 1003, "feature is not supported in app");
      callbackFail("feature is not supported in app");
      return;
    } 
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              IActivityProxy iActivityProxy = activity.getActivityProxy();
              if (iActivityProxy == null) {
                ApiOperateBannerAdCtrl.this.callbackFail("activity proxy is null");
                return;
              } 
              if (iActivityProxy.onOperateBannerView(adModel)) {
                ApiOperateBannerAdCtrl.this.callbackOk();
                return;
              } 
              ApiOperateBannerAdCtrl.this.callbackFail("can not operate banner ad");
            }
          });
      return;
    } 
    callbackFail("activity is null");
  }
  
  public String getActionName() {
    return "operateBannerAd";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ad\ApiOperateBannerAdCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */