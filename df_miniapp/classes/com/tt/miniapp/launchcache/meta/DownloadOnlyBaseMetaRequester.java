package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.LaunchCacheHelper;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;

public abstract class DownloadOnlyBaseMetaRequester extends BaseMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  public DownloadOnlyBaseMetaRequester(Context paramContext, RequestType paramRequestType) {
    super(paramContext, paramRequestType);
  }
  
  protected boolean onFetchLocalMetaSync(Context paramContext, AppInfoEntity paramAppInfoEntity, RequestResultInfo paramRequestResultInfo) {
    l.b(paramContext, "context");
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestResultInfo, "requestResultInfo");
    return false;
  }
  
  protected void onSaveMetaData(RequestResultInfo paramRequestResultInfo) {
    l.b(paramRequestResultInfo, "requestResultInfo");
    AppBrandLogger.i("DownloadOnlyBaseMetaRequester", new Object[] { getMRequestType(), "onSaveMetaData" });
    AppInfoEntity appInfoEntity = paramRequestResultInfo.appInfo;
    String str2 = paramRequestResultInfo.encryIV;
    String str3 = paramRequestResultInfo.encryKey;
    String str1 = paramRequestResultInfo.originData;
    if (appInfoEntity != null && str2 != null && str3 != null) {
      if (str1 == null)
        return; 
      if (appInfoEntity.isAppValid()) {
        if (appInfoEntity.isLocalTest())
          return; 
        LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
        Context context = getMContext();
        String str = appInfoEntity.appId;
        l.a(str, "appInfo.appId");
        LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = launchCacheDAO.getCacheAppIdDir(context, str);
        LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
        if (lockObject == null)
          return; 
        try {
          LaunchCacheDAO.CacheVersionDir cacheVersionDir = cacheAppIdDir.getCacheVersionDir(appInfoEntity.versionCode, RequestType.normal);
          if (cacheVersionDir.getMetaFile().exists() && cacheVersionDir.getPkgFile().exists()) {
            LaunchCacheHelper.INSTANCE.saveMetaDataLocked(cacheVersionDir, appInfoEntity, str3, str2, str1);
          } else {
            LaunchCacheDAO.CacheVersionDir cacheVersionDir1 = cacheAppIdDir.getCacheVersionDir(appInfoEntity.versionCode, getMRequestType());
            LaunchCacheHelper.INSTANCE.saveMetaDataLocked(cacheVersionDir1, appInfoEntity, str3, str2, str1);
          } 
          return;
        } finally {
          lockObject.unlock();
        } 
      } 
    } 
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\DownloadOnlyBaseMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */