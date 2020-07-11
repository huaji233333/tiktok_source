package com.tt.miniapphost;

import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.storage.StorageManagerImpl;
import com.tt.miniapphost.dynamic.IBundleManager;
import com.tt.miniapphost.dynamic.IProcessManager;
import com.tt.miniapphost.dynamic.IStorageManager;
import com.tt.miniapphost.host.HostDependManager;

public class AppbrandConstants {
  public static IBundleManager iBundleManager;
  
  public static IProcessManager iProcessManager;
  
  public static IStorageManager iStorageManager;
  
  public static IBundleManager getBundleManager() {
    if (iBundleManager == null)
      iBundleManager = new BundleManager(); 
    return iBundleManager;
  }
  
  public static IProcessManager getProcessManager() {
    if (iProcessManager == null)
      iProcessManager = (IProcessManager)new AppProcessManager(); 
    return iProcessManager;
  }
  
  public static IStorageManager getStorageManager() {
    if (iStorageManager == null)
      iStorageManager = (IStorageManager)new StorageManagerImpl(); 
    return iStorageManager;
  }
  
  public static class ActivityLifeCycle {}
  
  public static class ApiResult {}
  
  public static class DownloadStatus {}
  
  public static class Event_Param {}
  
  public static class GamePayType {}
  
  public static class MiniAppLifecycle {}
  
  public static class OpenLoginActivityParamsKey {}
  
  public static class RequestCode {}
  
  public static class ResultCode {}
  
  public static class SharePreferences {
    public static String getCookieSp() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(HostDependManager.getInst().getSpPrefixPath());
      stringBuilder.append("MiniAppCookiePersistence");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\AppbrandConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */