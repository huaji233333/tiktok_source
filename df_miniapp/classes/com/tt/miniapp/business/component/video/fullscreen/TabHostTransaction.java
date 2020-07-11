package com.tt.miniapp.business.component.video.fullscreen;

import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.page.AppbrandHomePageViewWindow;
import com.tt.miniapp.page.AppbrandViewWindowBase;

public class TabHostTransaction extends FullScreenTransaction {
  private boolean mTabHostHidden;
  
  public TabHostTransaction(MiniAppContext paramMiniAppContext) {
    super(paramMiniAppContext);
  }
  
  public void enterFullScreen() {
    AppbrandViewWindowBase appbrandViewWindowBase = getCurrentViewWindowBase();
    if (!(appbrandViewWindowBase instanceof AppbrandHomePageViewWindow))
      return; 
    AppbrandHomePageViewWindow appbrandHomePageViewWindow = (AppbrandHomePageViewWindow)appbrandViewWindowBase;
    if (appbrandHomePageViewWindow.isTabMode()) {
      if (!appbrandHomePageViewWindow.isTabBarVisible())
        return; 
      appbrandHomePageViewWindow.setTabBarVisibility(false, false);
      this.mTabHostHidden = true;
    } 
  }
  
  public void exitFullScreen() {
    if (!this.mTabHostHidden)
      return; 
    this.mTabHostHidden = false;
    AppbrandViewWindowBase appbrandViewWindowBase = getCurrentViewWindowBase();
    if (!(appbrandViewWindowBase instanceof AppbrandHomePageViewWindow))
      return; 
    AppbrandHomePageViewWindow appbrandHomePageViewWindow = (AppbrandHomePageViewWindow)appbrandViewWindowBase;
    if (appbrandHomePageViewWindow.isTabMode() && !appbrandHomePageViewWindow.isTabBarVisible())
      appbrandHomePageViewWindow.setTabBarVisibility(true, false); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\fullscreen\TabHostTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */