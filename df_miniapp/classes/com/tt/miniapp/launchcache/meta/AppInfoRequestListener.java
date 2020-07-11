package com.tt.miniapp.launchcache.meta;

import com.tt.miniapphost.entity.AppInfoEntity;

public interface AppInfoRequestListener {
  void onAppInfoInvalid(AppInfoEntity paramAppInfoEntity, int paramInt);
  
  void requestAppInfoFail(String paramString1, String paramString2);
  
  void requestAppInfoSuccess(AppInfoEntity paramAppInfoEntity);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\AppInfoRequestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */