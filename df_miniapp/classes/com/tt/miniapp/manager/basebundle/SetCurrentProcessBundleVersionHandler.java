package com.tt.miniapp.manager.basebundle;

import android.content.Context;
import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapp.manager.basebundle.handler.BaseBundleHandler;
import com.tt.miniapp.manager.basebundle.handler.BundleHandlerParam;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.ProcessUtil;

public class SetCurrentProcessBundleVersionHandler extends BaseBundleHandler {
  private void killMiniProcessIfNotUsed() {
    AppProcessManager.killProcess("");
    AppbrandConstants.getProcessManager().preloadEmptyProcess(true);
  }
  
  public BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    if (ProcessUtil.isMainProcess(paramContext) && paramBundleHandlerParam.bundleVersion > 0L) {
      String str = AppbrandUtil.convertVersionCodeToStr(paramBundleHandlerParam.bundleVersion);
      BaseBundleFileManager.updateLatestBaseBundleFile(paramContext, paramBundleHandlerParam.bundleVersion);
      BaseBundleEventHelper.BaseBundleEvent baseBundleEvent = paramBundleHandlerParam.baseBundleEvent;
      StringBuilder stringBuilder2 = new StringBuilder("update latest version, the version is");
      stringBuilder2.append(paramBundleHandlerParam.bundleVersion);
      baseBundleEvent.appendLog(stringBuilder2.toString());
      BaseBundleManager.getInst().setBaseBundleEntityVersion(str, AppbrandUtil.convertFourToThreeVersion(str));
      baseBundleEvent = paramBundleHandlerParam.baseBundleEvent;
      StringBuilder stringBuilder1 = new StringBuilder("setCurrentBaseVersion:");
      stringBuilder1.append(paramBundleHandlerParam.bundleVersion);
      baseBundleEvent.appendLog(stringBuilder1.toString());
      BaseBundleEventHelper.monitor(this.mParam.baseBundleEvent);
      killMiniProcessIfNotUsed();
    } 
    return paramBundleHandlerParam;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\SetCurrentProcessBundleVersionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */