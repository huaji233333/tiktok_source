package com.tt.miniapphost;

import com.tt.frontendapiinterface.e;
import com.tt.frontendapiinterface.j;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.preload.IPreload;

public interface IAppbrandApplication {
  e getActivityLife();
  
  AppInfoEntity getAppInfo();
  
  String getCurrentPagePath();
  
  String getCurrentPageType();
  
  j getJsBridge();
  
  IPreload getPreloadManager();
  
  String getRequestRefer();
  
  String getSchema();
  
  AppInfoEntity getUpdateAppInfo();
  
  void invokeHandler(int paramInt1, int paramInt2, String paramString);
  
  void onCreate();
  
  void publish(int paramInt, String paramString1, String paramString2);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\IAppbrandApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */