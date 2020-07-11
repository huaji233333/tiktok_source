package com.tt.miniapp.manager.basebundle.handler;

import android.content.Context;
import com.tt.miniapp.manager.basebundle.BaseBundleFileManager;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;

public class CheckPushBaseBundleHandler extends BaseBundleHandler {
  public BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    if (!DebugUtil.debug())
      return paramBundleHandlerParam; 
    if (paramContext == null)
      return paramBundleHandlerParam; 
    File file = new File(paramContext.getExternalCacheDir(), "/basebundle/bundle.zip");
    if (file.exists()) {
      IOUtils.clearDir(BaseBundleFileManager.getBundleFolderFile(paramContext, "push_bundle"));
      paramBundleHandlerParam.bundleVersion = BaseBundleFileManager.unZipFileToBundle(paramContext, file, "push_bundle", true, paramBundleHandlerParam.baseBundleEvent);
    } 
    return paramBundleHandlerParam;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\CheckPushBaseBundleHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */