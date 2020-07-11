package com.tt.miniapp.manager.basebundle.handler;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.event.BaseBundleEventHelper;
import com.tt.miniapp.manager.basebundle.BaseBundleFileManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.AppbrandUtil;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;

public class ClearNotUsedBaseBundleHandler extends BaseBundleHandler {
  private final String TAG = "ClearNotUsedBaseBundleHandler";
  
  public BundleHandlerParam handle(Context paramContext, BundleHandlerParam paramBundleHandlerParam) {
    try {
      BaseBundleEventHelper.BaseBundleEvent baseBundleEvent = paramBundleHandlerParam.baseBundleEvent;
      File file1 = AppbrandConstant.getOldBaseBundleDir();
      if (file1 != null && file1.exists())
        IOUtils.delete(file1); 
      Application application = AppbrandContext.getInst().getApplicationContext();
      if (AppProcessManager.isMiniAppProcessExist((Context)application))
        return paramBundleHandlerParam; 
      baseBundleEvent.appendLog("start clean old version base bundle");
      file1 = BaseBundleFileManager.getLatestBaseBundleFile();
      if (!file1.exists())
        return paramBundleHandlerParam; 
      file2 = AppbrandUtil.getAppServiceDir((Context)application);
      long l = Long.valueOf(CharacterUtils.replaceBlank(IOUtils.readString(file1.getAbsolutePath(), "UTF-8"))).longValue();
      if (l > 0L) {
        if (!file2.exists())
          return paramBundleHandlerParam; 
        for (File file2 : file2.listFiles()) {
          if (file2 != null && !TextUtils.isEmpty(file2.getName()) && file2.getName().contains(".")) {
            long l1 = AppbrandUtil.convertVersionStrToCode(file2.getName());
            if (l1 != l) {
              StringBuilder stringBuilder = new StringBuilder("clean bundle version: ");
              stringBuilder.append(l1);
              baseBundleEvent.appendLog(stringBuilder.toString());
              IOUtils.delete(file2);
            } 
          } 
        } 
      } else {
        return paramBundleHandlerParam;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("ClearNotUsedBaseBundleHandler", new Object[] { exception });
    } 
    return paramBundleHandlerParam;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\handler\ClearNotUsedBaseBundleHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */