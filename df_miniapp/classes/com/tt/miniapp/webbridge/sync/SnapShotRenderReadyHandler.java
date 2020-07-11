package com.tt.miniapp.webbridge.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.webbridge.WebEventHandler;
import com.tt.miniapphost.util.CharacterUtils;

public class SnapShotRenderReadyHandler extends WebEventHandler {
  public SnapShotRenderReadyHandler(WebViewManager.IRender paramIRender, String paramString, int paramInt) {
    super(paramIRender, paramString, paramInt);
  }
  
  public String act() {
    TimeLogger.getInstance().logTimeDuration(new String[] { "SnapShotRenderReadyHandler snapshot show" });
    ((RenderSnapShotManager)AppbrandApplicationImpl.getInst().getService(RenderSnapShotManager.class)).ready();
    return CharacterUtils.empty();
  }
  
  public String getApiName() {
    return "snapshotReady";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\webbridge\sync\SnapShotRenderReadyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */