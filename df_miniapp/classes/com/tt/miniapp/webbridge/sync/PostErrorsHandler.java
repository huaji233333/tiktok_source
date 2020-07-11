package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.TTAppbrandTabUI;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;

public class PostErrorsHandler extends WebEventHandler {
  public PostErrorsHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      IActivityProxy iActivityProxy = miniappHostBase.getActivityProxy();
      if (iActivityProxy instanceof TTAppbrandTabUI) {
        ((TTAppbrandTabUI)iActivityProxy).postError(this.mArgs);
        return makeOkMsg();
      } 
      return makeFailMsg("is not mini app");
    } 
    if (((RenderSnapShotManager)AppbrandApplicationImpl.getInst().getService(RenderSnapShotManager.class)).isSnapShotRender()) {
      ((RenderSnapShotManager)AppbrandApplicationImpl.getInst().getService(RenderSnapShotManager.class)).postErrorToLoadingView(this.mArgs);
      return makeOkMsg();
    } 
    return makeFailMsg("activity is null");
  }
  
  public boolean canOverride() {
    return false;
  }
  
  public String getApiName() {
    return "postErrors";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\PostErrorsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */