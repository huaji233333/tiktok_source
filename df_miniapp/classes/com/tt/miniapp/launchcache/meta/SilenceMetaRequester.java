package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.LaunchCacheHelper;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;

public final class SilenceMetaRequester extends BaseMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  public SilenceMetaRequester(Context paramContext) {
    super(paramContext, RequestType.silence);
  }
  
  protected final boolean onFetchLocalMetaSync(Context paramContext, AppInfoEntity paramAppInfoEntity, RequestResultInfo paramRequestResultInfo) {
    AppInfoEntity appInfoEntity;
    l.b(paramContext, "context");
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestResultInfo, "requestResultInfo");
    LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
    String str = paramAppInfoEntity.appId;
    l.a(str, "appInfo.appId");
    LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = launchCacheDAO.getCacheAppIdDir(paramContext, str);
    if (TextUtils.isEmpty(paramAppInfoEntity.appId)) {
      paramRequestResultInfo.errorCode = ErrorCode.META.INVALID_APP_ID.getCode();
      paramRequestResultInfo.errorMsg = "Silence onFetchLocalMetaSync, appId is null";
      return true;
    } 
    if (paramAppInfoEntity.versionCode == 0L) {
      paramRequestResultInfo.errorCode = ErrorCode.META.INVALID_VERSION.getCode();
      paramRequestResultInfo.errorMsg = "Silence onFetchLocalMetaSync, versionCode is assigned";
      return true;
    } 
    LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
    if (lockObject == null) {
      paramRequestResultInfo.errorCode = ErrorCode.MAIN.GET_LAUNCHCACHE_FILE_LOCK_FAIL.getCode();
      paramRequestResultInfo.errorMsg = "Silence onFetchLocalMetaSync, get lock fail";
      return true;
    } 
    try {
      LaunchCacheDAO.CacheVersionDir cacheVersionDir = cacheAppIdDir.getCacheVersionDir(paramAppInfoEntity.versionCode, getMRequestType());
      boolean bool = LaunchCacheHelper.INSTANCE.getRawLocalMetaLocked(cacheVersionDir, paramRequestResultInfo);
      lockObject.unlock();
      return true;
    } finally {
      appInfoEntity.unlock();
    } 
  }
  
  protected final AppInfoRequestResult onRequestSync(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
    return null;
  }
  
  protected final void onSaveMetaData(RequestResultInfo paramRequestResultInfo) {
    l.b(paramRequestResultInfo, "requestResultInfo");
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\SilenceMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */