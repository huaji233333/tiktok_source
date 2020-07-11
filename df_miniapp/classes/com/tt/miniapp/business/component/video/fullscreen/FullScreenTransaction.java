package com.tt.miniapp.business.component.video.fullscreen;

import android.app.Activity;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.TTAppbrandTabUI;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandViewWindowBase;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;

public abstract class FullScreenTransaction {
  protected MiniAppContext mContext;
  
  public FullScreenTransaction(MiniAppContext paramMiniAppContext) {
    this.mContext = paramMiniAppContext;
  }
  
  public abstract void enterFullScreen();
  
  public abstract void exitFullScreen();
  
  protected Activity getCurrentActivity() {
    return this.mContext.getCurrentActivity();
  }
  
  protected AppbrandSinglePage getCurrentPage() {
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getTopView();
    return (appbrandViewWindowBase != null) ? appbrandViewWindowBase.getCurrentPage() : null;
  }
  
  protected AppbrandViewWindowBase getCurrentViewWindowBase() {
    return (AppbrandViewWindowBase)((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getTopView();
  }
  
  protected TTAppbrandTabUI getTabUI() {
    Activity activity = this.mContext.getCurrentActivity();
    if (!(activity instanceof MiniappHostBase))
      return null; 
    IActivityProxy iActivityProxy = ((MiniappHostBase)activity).getActivityProxy();
    return (iActivityProxy == null) ? null : (!(iActivityProxy instanceof TTAppbrandTabUI) ? null : (TTAppbrandTabUI)iActivityProxy);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\component\video\fullscreen\FullScreenTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */