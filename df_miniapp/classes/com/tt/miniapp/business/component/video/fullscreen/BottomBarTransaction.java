package com.tt.miniapp.business.component.video.fullscreen;

import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.page.AppbrandSinglePage;

public class BottomBarTransaction extends FullScreenTransaction {
  private boolean mBottomHidden;
  
  public BottomBarTransaction(MiniAppContext paramMiniAppContext) {
    super(paramMiniAppContext);
  }
  
  public void enterFullScreen() {
    AppbrandSinglePage appbrandSinglePage = getCurrentPage();
    if (appbrandSinglePage == null)
      return; 
    if (appbrandSinglePage.isBottomBarHidden())
      return; 
    appbrandSinglePage.hideBottomBar();
    this.mBottomHidden = true;
  }
  
  public void exitFullScreen() {
    if (!this.mBottomHidden)
      return; 
    this.mBottomHidden = false;
    AppbrandSinglePage appbrandSinglePage = getCurrentPage();
    if (appbrandSinglePage == null)
      return; 
    if (appbrandSinglePage.isBottomBarShown())
      return; 
    appbrandSinglePage.showBottomBar();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\fullscreen\BottomBarTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */