package com.tt.miniapp.manager.basebundle.handler;

import android.content.Context;
import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.manager.basebundle.BaseBundleFileManager;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.TimeMeter;

public class ResolveDownloadHandler extends BaseBundleHandler {
  public BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    if (paramBundleHandlerParam.isIgnoreTask)
      return paramBundleHandlerParam; 
    BaseBundleEventHelper.BaseBundleEvent baseBundleEvent = paramBundleHandlerParam.baseBundleEvent;
    if (!paramBundleHandlerParam.isLastTaskSuccess || paramBundleHandlerParam.targetZipFile == null || !paramBundleHandlerParam.targetZipFile.exists()) {
      baseBundleEvent.appendLog("basebundle download resolve fail");
      return paramBundleHandlerParam;
    } 
    paramBundleHandlerParam.timeMeter = TimeMeter.newAndStart();
    paramBundleHandlerParam.bundleVersion = BaseBundleFileManager.unZipFileToBundle(paramContext, paramBundleHandlerParam.targetZipFile, "download_bundle", false, baseBundleEvent);
    String str = AppbrandUtil.convertVersionCodeToStr(BaseBundleFileManager.getLatestBaseBundleVersion());
    long l = paramBundleHandlerParam.timeMeter.getMillisAfterStart();
    if (paramBundleHandlerParam.bundleVersion > 0L) {
      StringBuilder stringBuilder = new StringBuilder("basebundle download resolve success, version: ");
      stringBuilder.append(paramBundleHandlerParam.bundleVersion);
      baseBundleEvent.appendLog(stringBuilder.toString());
      InnerEventHelper.mpLibResult("mp_lib_install_result", str, AppbrandUtil.convertVersionCodeToStr(paramBundleHandlerParam.bundleVersion), "success", "", l);
      return paramBundleHandlerParam;
    } 
    baseBundleEvent.appendLog("basebundle download resolve fail");
    InnerEventHelper.mpLibResult("mp_lib_install_result", str, AppbrandUtil.convertVersionCodeToStr(paramBundleHandlerParam.bundleVersion), "fail", "jssdkcheck_fail", l);
    return paramBundleHandlerParam;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\ResolveDownloadHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */