package com.tt.miniapp.manager.basebundle.handler;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapp.manager.basebundle.BaseBundleDAO;
import com.tt.miniapp.manager.basebundle.BaseBundleFileManager;
import com.tt.miniapphost.util.IOUtils;

public class CheckBuildInBaseBundleHandler extends BaseBundleHandler {
  private boolean isNeedUpdate(Context paramContext) {
    long l1 = BaseBundleDAO.getLastBundleVersion(paramContext);
    long l2 = Long.valueOf("1700001").longValue();
    String str = BaseBundleDAO.getLastVersionName(paramContext);
    if (l2 > l1 || !TextUtils.equals("3.7.4-tiktok", str)) {
      BaseBundleDAO.setLastBundleVersion(paramContext, l2);
      BaseBundleDAO.setLastVersionName(paramContext, "3.7.4-tiktok");
      return true;
    } 
    return false;
  }
  
  public BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    BaseBundleEventHelper.BaseBundleEvent baseBundleEvent1;
    StringBuilder stringBuilder;
    BaseBundleEventHelper.BaseBundleEvent baseBundleEvent2 = paramBundleHandlerParam.baseBundleEvent;
    long l = paramBundleHandlerParam.bundleVersion;
    if (!IOUtils.isAssetsFileExist(paramContext, "__dev__.zip")) {
      baseBundleEvent1 = paramBundleHandlerParam.baseBundleEvent;
      stringBuilder = new StringBuilder("ignore download Task?");
      stringBuilder.append(paramBundleHandlerParam.isIgnoreTask);
      baseBundleEvent1.appendLog(stringBuilder.toString());
      return paramBundleHandlerParam;
    } 
    if (isNeedUpdate((Context)baseBundleEvent1) || BaseBundleFileManager.getLatestBaseBundleVersion() <= 0L)
      l = BaseBundleFileManager.unZipAssetsBundle((Context)baseBundleEvent1, "__dev__.zip", "buildin_bundle", (BaseBundleEventHelper.BaseBundleEvent)stringBuilder, false); 
    paramBundleHandlerParam.bundleVersion = l;
    return paramBundleHandlerParam;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\CheckBuildInBaseBundleHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */