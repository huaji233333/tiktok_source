package com.tt.miniapp.mvp;

import com.tt.miniapphost.entity.AppInfoEntity;

public interface TTAppbrandView {
  void metaExpired();
  
  void miniAppDownloadInstallFail(String paramString1, String paramString2);
  
  void miniAppDownloadInstallProgress(int paramInt);
  
  void miniAppInstallSuccess();
  
  void mismatchHost();
  
  void noPermission();
  
  void offline();
  
  void onDOMReady();
  
  void onEnvironmentReady();
  
  void onFirstContentfulPaint(long paramLong);
  
  void onRemoteDebugOpen();
  
  void onSnapShotDOMReady();
  
  void requestAppInfoFail(String paramString1, String paramString2);
  
  void requestAppInfoSuccess(AppInfoEntity paramAppInfoEntity);
  
  void showNotSupportView();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mvp\TTAppbrandView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */