package com.tt.miniapp.business.component.video.fullscreen;

import android.view.View;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.page.AppbrandSinglePage;

public class NavigationBarTransaction extends FullScreenTransaction {
  private boolean mNavigationBarHidden;
  
  public NavigationBarTransaction(MiniAppContext paramMiniAppContext) {
    super(paramMiniAppContext);
  }
  
  private View getNavigationBar() {
    AppbrandSinglePage appbrandSinglePage = getCurrentPage();
    return (appbrandSinglePage == null) ? null : appbrandSinglePage.getTitleBar().getTitleView();
  }
  
  public void enterFullScreen() {
    View view = getNavigationBar();
    if (view == null)
      return; 
    if (view.getVisibility() == 0) {
      this.mNavigationBarHidden = true;
      view.setVisibility(8);
    } 
  }
  
  public void exitFullScreen() {
    if (!this.mNavigationBarHidden)
      return; 
    View view = getNavigationBar();
    if (view == null)
      return; 
    view.setVisibility(0);
    this.mNavigationBarHidden = false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\fullscreen\NavigationBarTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */