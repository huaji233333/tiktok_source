package com.tt.miniapp.msg.ad;

import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.ad.c;
import com.tt.option.e.e;

public abstract class VideoAdCtrl extends BaseAdCtrl {
  public VideoAdCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private boolean isMicroGame() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    return (appInfoEntity != null && appInfoEntity.isGame());
  }
  
  protected String getEventType() {
    return "onVideoAdStateChange";
  }
  
  protected boolean isSupportExcitingVideoAd() {
    return isMicroGame() ? HostDependManager.getInst().isSupportAd(c.GAME_EXCITING_VIDEO) : HostDependManager.getInst().isSupportAd(c.APP_EXCITING_VIDEO);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ad\VideoAdCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */