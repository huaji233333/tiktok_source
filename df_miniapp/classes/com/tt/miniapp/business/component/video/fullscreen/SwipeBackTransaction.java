package com.tt.miniapp.business.component.video.fullscreen;

import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandViewWindowBase;

public class SwipeBackTransaction extends FullScreenTransaction {
  private boolean mDisableGesture;
  
  public SwipeBackTransaction(MiniAppContext paramMiniAppContext) {
    super(paramMiniAppContext);
  }
  
  public void enterFullScreen() {
    AppbrandSinglePage appbrandSinglePage = getCurrentPage();
    if (appbrandSinglePage == null)
      return; 
    AppbrandViewWindowBase appbrandViewWindowBase = appbrandSinglePage.getHost();
    if (appbrandViewWindowBase == null)
      return; 
    if (appbrandViewWindowBase.isDragEnabled()) {
      appbrandViewWindowBase.setDragEnable(false);
      this.mDisableGesture = true;
    } 
  }
  
  public void exitFullScreen() {
    if (!this.mDisableGesture)
      return; 
    AppbrandSinglePage appbrandSinglePage = getCurrentPage();
    if (appbrandSinglePage == null)
      return; 
    AppbrandViewWindowBase appbrandViewWindowBase = appbrandSinglePage.getHost();
    if (appbrandViewWindowBase == null)
      return; 
    appbrandViewWindowBase.setDragEnable(true);
    this.mDisableGesture = false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\fullscreen\SwipeBackTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */