package com.tt.miniapp.launchcache.pkg;

import java.io.File;

public interface StreamDownloadInstallListener {
  void onDownloadSuccess(File paramFile, boolean paramBoolean);
  
  void onDownloadingProgress(int paramInt, long paramLong);
  
  void onFail(String paramString1, String paramString2);
  
  void onInstallSuccess();
  
  void onStop();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\StreamDownloadInstallListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */