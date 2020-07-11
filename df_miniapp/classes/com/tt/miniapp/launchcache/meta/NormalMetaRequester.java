package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.LaunchCacheHelper;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.manager.PreConnectCDNManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;

public final class NormalMetaRequester extends BaseMetaRequester {
  public static final Companion Companion = new Companion(null);
  
  private final AppbrandApplicationImpl mApp;
  
  public NormalMetaRequester(AppbrandApplicationImpl paramAppbrandApplicationImpl, Context paramContext) {
    super(paramContext, RequestType.normal);
    this.mApp = paramAppbrandApplicationImpl;
  }
  
  protected final boolean onFetchLocalMetaSync(Context paramContext, AppInfoEntity paramAppInfoEntity, RequestResultInfo paramRequestResultInfo) {
    l.b(paramContext, "context");
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestResultInfo, "requestResultInfo");
    if (paramAppInfoEntity.isLocalTest())
      return false; 
    MetaService metaService = (MetaService)this.mApp.getService(MetaService.class);
    String str = paramAppInfoEntity.appId;
    l.a(str, "appInfo.appId");
    RequestResultInfo requestResultInfo = metaService.tryFetchLocalMeta(paramContext, str, getMRequestType());
    if (requestResultInfo != null) {
      AppInfoEntity appInfoEntity = requestResultInfo.appInfo;
    } else {
      paramContext = null;
    } 
    if (paramContext != null && TextUtils.isEmpty(requestResultInfo.errorCode)) {
      paramRequestResultInfo.appInfo = requestResultInfo.appInfo;
      paramRequestResultInfo.url = requestResultInfo.url;
      paramRequestResultInfo.fromProcess = requestResultInfo.fromProcess;
      paramRequestResultInfo.errorCode = requestResultInfo.errorCode;
      paramRequestResultInfo.errorMsg = requestResultInfo.errorMsg;
      paramRequestResultInfo.originData = requestResultInfo.originData;
      paramRequestResultInfo.encryIV = requestResultInfo.encryIV;
      paramRequestResultInfo.encryKey = requestResultInfo.encryKey;
      AutoTestManager.addEventWithValue$default((AutoTestManager)this.mApp.getService(AutoTestManager.class), "isMetaExist", Boolean.valueOf(true), 0L, 4, null);
      return true;
    } 
    AutoTestManager.addEventWithValue$default((AutoTestManager)this.mApp.getService(AutoTestManager.class), "isMetaExist", Boolean.valueOf(false), 0L, 4, null);
    return false;
  }
  
  protected final AppInfoRequestResult onRequestSync(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
    AppBrandLogger.i("NormalMetaRequester", new Object[] { "onRequestSync" });
    PreConnectCDNManager.inst().preConnectCDN();
    return ((MetaService)this.mApp.getService(MetaService.class)).competeRequest(getMContext(), paramAppInfoEntity, getMRequestType(), 0);
  }
  
  protected final void onSaveMetaData(RequestResultInfo paramRequestResultInfo) {
    l.b(paramRequestResultInfo, "requestResultInfo");
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
          LaunchCacheDAO.CacheVersionDir cacheVersionDir = cacheAppIdDir.getCacheVersionDir(appInfoEntity.versionCode, getMRequestType());
          LaunchCacheHelper.INSTANCE.saveMetaDataLocked(cacheVersionDir, appInfoEntity, str3, str2, str1);
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


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\NormalMetaRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */