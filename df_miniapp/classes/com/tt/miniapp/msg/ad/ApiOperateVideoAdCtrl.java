package com.tt.miniapp.msg.ad;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.option.ad.h;
import com.tt.option.e.e;

public class ApiOperateVideoAdCtrl extends VideoAdCtrl {
  public ApiOperateVideoAdCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    final h adModel = new h(this.mArgs);
    StringBuilder stringBuilder = new StringBuilder("operateVideoAd:");
    stringBuilder.append(h);
    AppBrandLogger.d("tma_ApiOperateVideoAdCtrl", new Object[] { stringBuilder.toString() });
    if (!isSupportExcitingVideoAd()) {
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
                ApiOperateVideoAdCtrl.this.callbackFail("activity proxy is null");
                return;
              } 
              if (iActivityProxy.onOperateVideoAd(adModel)) {
                ApiOperateVideoAdCtrl.this.callbackOk();
                return;
              } 
              ApiOperateVideoAdCtrl.this.callbackFail("can not operate video ad");
            }
          });
      return;
    } 
    callbackFail("activity is not null");
  }
  
  public String getActionName() {
    return "operateVideoAd";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ad\ApiOperateVideoAdCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */