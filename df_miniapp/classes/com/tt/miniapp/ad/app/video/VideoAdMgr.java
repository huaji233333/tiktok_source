package com.tt.miniapp.ad.app.video;

import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.ad.g;
import com.tt.option.ad.h;

public class VideoAdMgr {
  private g mGameAdManager;
  
  private boolean mSwipeEnableBefore;
  
  public VideoAdMgr(g.a parama) {
    this.mGameAdManager = HostDependManager.getInst().createGameAdManager(parama);
  }
  
  private boolean invalid() {
    return (this.mGameAdManager == null);
  }
  
  public boolean getSwipeEnableBefore() {
    return this.mSwipeEnableBefore;
  }
  
  public boolean isVideoAdShown() {
    return invalid() ? false : this.mGameAdManager.b();
  }
  
  public boolean onBack() {
    return invalid() ? false : this.mGameAdManager.a();
  }
  
  public boolean onCreateVideoAd(h paramh) {
    return invalid() ? false : this.mGameAdManager.d(paramh);
  }
  
  public boolean onOperateVideoAd(h paramh) {
    return invalid() ? false : this.mGameAdManager.e(paramh);
  }
  
  public void setSwipeEnableBefore(boolean paramBoolean) {
    this.mSwipeEnableBefore = paramBoolean;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ad\app\video\VideoAdMgr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */