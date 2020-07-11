package com.tt.miniapp.business.ad.site;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.a.a.a;
import com.bytedance.sandboxapp.protocol.service.a.a.a.a;
import com.bytedance.sandboxapp.protocol.service.a.a.a.b;
import com.bytedance.sandboxapp.protocol.service.a.a.a.c;
import com.bytedance.sandboxapp.protocol.service.a.a.a.d;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.ad.a.a;

public class AdSiteService implements a {
  private a mDxppManager;
  
  private final MiniAppContext mMiniAppContext;
  
  public AdSiteService(MiniAppContext paramMiniAppContext) {
    this.mMiniAppContext = paramMiniAppContext;
  }
  
  public void cancelDxppAd(b paramb) {
    isSupportDxppManager();
  }
  
  public void dxppAd(b paramb) {
    isSupportDxppManager();
  }
  
  public a getContext() {
    return (a)this.mMiniAppContext;
  }
  
  public boolean isSupportDxppManager() {
    if (this.mDxppManager == null)
      this.mDxppManager = HostDependManager.getInst().createAdSiteDxppManager(); 
    return (this.mDxppManager != null);
  }
  
  public void onDestroy() {
    if (this.mDxppManager != null)
      this.mDxppManager = null; 
  }
  
  public void openAdLandPageLinks(d paramd, c paramc) {
    if (isSupportDxppManager()) {
      AppbrandApplicationImpl.getInst().setOpenedSchema(true);
      ActivityUtil.previousGetSnapshot((MiniappHostBase)getContext().getCurrentActivity());
      AppbrandApplicationImpl.getInst().getForeBackgroundManager().pauseBackgroundOverLimitTimeStrategy();
      return;
    } 
    if (paramc != null)
      paramc.a(); 
  }
  
  public void subscribeAppAd(b paramb, a parama) {
    isSupportDxppManager();
  }
  
  public void unsubscribeAppAd(b paramb) {
    isSupportDxppManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\ad\site\AdSiteService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */