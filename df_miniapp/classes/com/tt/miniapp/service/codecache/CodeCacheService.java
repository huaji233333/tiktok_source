package com.tt.miniapp.service.codecache;

import com.tt.miniapp.service.PureServiceInterface;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.io.File;

public interface CodeCacheService extends PureServiceInterface {
  void codeCacheMiniAppPkg(AppInfoEntity paramAppInfoEntity, File paramFile);
  
  boolean isCodeCacheEnabled(AppInfoEntity paramAppInfoEntity);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\codecache\CodeCacheService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */