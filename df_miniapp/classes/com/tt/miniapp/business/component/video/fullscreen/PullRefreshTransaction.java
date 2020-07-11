package com.tt.miniapp.business.component.video.fullscreen;

import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.page.AppbrandSinglePage;

public final class PullRefreshTransaction extends FullScreenTransaction {
  private boolean mDisablePullToRefresh;
  
  public PullRefreshTransaction(MiniAppContext paramMiniAppContext) {
    super(paramMiniAppContext);
  }
  
  public final void enterFullScreen() {
    AppbrandSinglePage appbrandSinglePage = getCurrentPage();
    if (appbrandSinglePage != null && appbrandSinglePage.pullDownRefreshEnabled()) {
      appbrandSinglePage.setDisableRefresh(true);
      this.mDisablePullToRefresh = true;
    } 
  }
  
  public final void exitFullScreen() {
    if (!this.mDisablePullToRefresh)
      return; 
    AppbrandSinglePage appbrandSinglePage = getCurrentPage();
    if (appbrandSinglePage != null) {
      appbrandSinglePage.setDisableRefresh(false);
      this.mDisablePullToRefresh = false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\fullscreen\PullRefreshTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */