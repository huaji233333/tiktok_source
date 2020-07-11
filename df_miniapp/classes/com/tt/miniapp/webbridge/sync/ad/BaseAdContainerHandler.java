package com.tt.miniapp.webbridge.sync.ad;

import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.ad.c;

public abstract class BaseAdContainerHandler extends WebEventHandler {
  public BaseAdContainerHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  protected boolean isSupportAppAd() {
    return (HostDependManager.getInst().isSupportAd(c.APP_BANNER) || HostDependManager.getInst().isSupportAd(c.APP_FEED));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\ad\BaseAdContainerHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */