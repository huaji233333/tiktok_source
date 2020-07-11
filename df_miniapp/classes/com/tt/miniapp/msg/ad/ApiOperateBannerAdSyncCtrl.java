package com.tt.miniapp.msg.ad;

import com.tt.frontendapiinterface.a;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.ad.c;
import com.tt.option.ad.h;
import java.util.concurrent.CountDownLatch;

public class ApiOperateBannerAdSyncCtrl extends SyncMsgCtrl {
  public ApiOperateBannerAdSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    final h adModel = new h(this.mParams);
    StringBuilder stringBuilder = new StringBuilder("operateBannerAd:");
    stringBuilder.append(h);
    AppBrandLogger.d("ApiOperateBannerAdSyncCtrl", new Object[] { stringBuilder.toString() });
    if (!HostDependManager.getInst().isSupportAd(c.GAME_BANNER))
      return makeFailMsg("feature is not supported in app"); 
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      final int[] result = new int[1];
      final CountDownLatch latch = new CountDownLatch(1);
      AppbrandContext.mainHandler.post(new Runnable() {
            public void run() {
              IActivityProxy iActivityProxy = activity.getActivityProxy();
              if (iActivityProxy != null) {
                if (iActivityProxy.onOperateBannerView(adModel)) {
                  result[0] = 0;
                } else {
                  result[0] = -1;
                } 
              } else {
                result[0] = -2;
              } 
              latch.countDown();
            }
          });
      try {
        countDownLatch.await();
        return (arrayOfInt[0] == 0) ? makeOkMsg() : ((arrayOfInt[0] == -1) ? makeFailMsg("can not operate banner ad") : ((arrayOfInt[0] == -2) ? makeFailMsg("activity proxy is null") : makeFailMsg(a.d(getName()))));
      } catch (Exception exception) {
        AppBrandLogger.e("ApiOperateBannerAdSyncCtrl", new Object[] { exception });
        return makeFailMsg(exception);
      } 
    } 
    return makeFailMsg("activity is null");
  }
  
  public String getName() {
    return "operateBannerAd";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ad\ApiOperateBannerAdSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */