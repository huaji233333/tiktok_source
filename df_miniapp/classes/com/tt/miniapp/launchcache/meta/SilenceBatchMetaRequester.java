package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.LaunchCacheHelper;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;
import java.util.List;

public final class SilenceBatchMetaRequester extends BaseBatchMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  public SilenceBatchMetaRequester(Context paramContext) {
    super(paramContext, RequestType.silence);
  }
  
  public final void onSaveMetaList(List<? extends RequestResultInfo> paramList) {
    l.b(paramList, "requestInfoList");
    AppBrandLogger.i("SilenceBatchMetaRequester", new Object[] { getMRequestType(), "onSaveMetaList" });
    for (RequestResultInfo requestResultInfo : paramList) {
      AppInfoEntity appInfoEntity = requestResultInfo.appInfo;
      String str1 = requestResultInfo.encryKey;
      String str2 = requestResultInfo.encryIV;
      String str3 = requestResultInfo.originData;
      if (appInfoEntity == null || !appInfoEntity.isAppValid() || str3 == null || str1 == null || str2 == null || !TextUtils.isEmpty(requestResultInfo.errorCode))
        continue; 
      LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
      Context context = getMContext();
      String str4 = appInfoEntity.appId;
      l.a(str4, "appInfo.appId");
      LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = launchCacheDAO.getCacheAppIdDir(context, str4);
      LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
      if (lockObject == null)
        continue; 
      try {
        LaunchCacheDAO.CacheVersionDir cacheVersionDir = cacheAppIdDir.getCacheVersionDir(appInfoEntity.versionCode, RequestType.normal);
        if (cacheVersionDir.getMetaFile().exists() && cacheVersionDir.getPkgFile().exists()) {
          LaunchCacheHelper.INSTANCE.saveMetaDataLocked(cacheVersionDir, appInfoEntity, str1, str2, str3);
        } else {
          LaunchCacheDAO.CacheVersionDir cacheVersionDir1 = cacheAppIdDir.getCacheVersionDir(appInfoEntity.versionCode, getMRequestType());
          LaunchCacheHelper.INSTANCE.saveMetaDataLocked(cacheVersionDir1, appInfoEntity, str1, str2, str3);
        } 
      } finally {
        lockObject.unlock();
      } 
    } 
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\SilenceBatchMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */