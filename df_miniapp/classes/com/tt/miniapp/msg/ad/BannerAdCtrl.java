package com.tt.miniapp.msg.ad;

import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.ad.c;
import com.tt.option.e.e;

public abstract class BannerAdCtrl extends BaseAdCtrl {
  public BannerAdCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  protected String getEventType() {
    return "onBannerAdStateChange";
  }
  
  protected boolean isSupportGameBannerAd() {
    return HostDependManager.getInst().isSupportAd(c.GAME_BANNER);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ad\BannerAdCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */