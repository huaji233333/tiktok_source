package com.tt.miniapp.webbridge;

import android.webkit.JavascriptInterface;
import com.bytedance.sandboxapp.protocol.service.m.a;
import com.tt.miniapp.AppbrandApplicationImpl;

public class WebGlobalConfig {
  private boolean mIsRenderInBrowser;
  
  @JavascriptInterface
  public boolean isRenderInBrowser() {
    return this.mIsRenderInBrowser;
  }
  
  public void setRenderInBrowser(boolean paramBoolean) {
    this.mIsRenderInBrowser = paramBoolean;
  }
  
  @JavascriptInterface
  public boolean useWebLivePlayer() {
    return ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).useWebLivePlayer();
  }
  
  @JavascriptInterface
  public boolean useWebVideo() {
    return ((a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class)).useWebVideo();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\WebGlobalConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */