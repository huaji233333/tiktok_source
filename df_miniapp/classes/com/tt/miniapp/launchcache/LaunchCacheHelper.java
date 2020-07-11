package com.tt.miniapp.launchcache;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.launchcache.meta.AppInfoHelper;
import com.tt.miniapp.launchcache.meta.RequestResultInfo;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.ProcessUtil;
import d.f.b.l;
import java.io.File;
import java.util.Iterator;
import org.json.JSONObject;

public final class LaunchCacheHelper {
  public static final LaunchCacheHelper INSTANCE = new LaunchCacheHelper();
  
  private static Integer sMetaExpireTime;
  
  private final int getMetaExpireTime(Context paramContext) {
    if (sMetaExpireTime == null) {
      HostDependManager hostDependManager = HostDependManager.getInst();
      l.a(hostDependManager, "HostDependManager.getInst()");
      JSONObject jSONObject = hostDependManager.getTmaFeatureConfig();
      byte b = -1;
      int i = b;
      if (jSONObject != null) {
        jSONObject = jSONObject.optJSONObject("bdp_silence_update_strategy");
        i = b;
        if (jSONObject != null)
          i = jSONObject.optInt("meta_expire_time", -1); 
      } 
      sMetaExpireTime = Integer.valueOf(i * 60 * 60 * 1000);
    } 
    Integer integer = sMetaExpireTime;
    if (integer == null)
      l.a(); 
    return integer.intValue();
  }
  
  private final String mergeMeta(LaunchCacheDAO.CacheVersionDir paramCacheVersionDir, AppInfoEntity paramAppInfoEntity, String paramString1, String paramString2, String paramString3) {
    if (!paramCacheVersionDir.getMetaFile().exists() || !paramCacheVersionDir.getPkgFile().exists() || !paramCacheVersionDir.checkStatusFlag(StatusFlagType.Verified)) {
      AppBrandLogger.i("LaunchCacheHelper", new Object[] { "不满足merge条件" });
      return paramString3;
    } 
    RequestResultInfo requestResultInfo = new RequestResultInfo();
    if (!getRawLocalMetaLocked(paramCacheVersionDir, requestResultInfo)) {
      AppBrandLogger.i("LaunchCacheHelper", new Object[] { "获取本地meta失败" });
      return paramString3;
    } 
    try {
      String str = AppInfoHelper.mergeMetaSimple(requestResultInfo.originData, requestResultInfo.encryKey, requestResultInfo.encryIV, paramAppInfoEntity, paramString3, paramString1, paramString2);
      l.a(str, "AppInfoHelper.mergeMetaS… data, encryKey, encryIV)");
      return str;
    } catch (Exception exception) {
      AppBrandLogger.e("LaunchCacheHelper", new Object[] { exception });
      return paramString3;
    } 
  }
  
  public final LaunchCacheDAO.CacheVersionDir getBestAvailableCacheVersionDirLocked(LaunchCacheDAO.CacheAppIdDir paramCacheAppIdDir) {
    LaunchCacheDAO.CacheVersionDir cacheVersionDir;
    l.b(paramCacheAppIdDir, "cacheAppIdDir");
    paramCacheAppIdDir.checkLocked();
    Iterator<LaunchCacheDAO.CacheVersionDir> iterator = paramCacheAppIdDir.listCacheVersionDirs().iterator();
    paramCacheAppIdDir = null;
    while (iterator.hasNext()) {
      LaunchCacheDAO.CacheVersionDir cacheVersionDir1 = iterator.next();
      if (cacheVersionDir1.checkStatusFlag(StatusFlagType.Verified) && cacheVersionDir1.getMetaFile().exists() && cacheVersionDir1.getPkgFile().exists() && (paramCacheAppIdDir == null || paramCacheAppIdDir.getVersionCode() < cacheVersionDir1.getVersionCode() || (paramCacheAppIdDir.getVersionCode() == cacheVersionDir1.getVersionCode() && cacheVersionDir1.getRequestType() == RequestType.normal)))
        cacheVersionDir = cacheVersionDir1; 
    } 
    return cacheVersionDir;
  }
  
  public final boolean getRawLocalMetaLocked(LaunchCacheDAO.CacheVersionDir paramCacheVersionDir, RequestResultInfo paramRequestResultInfo) {
    l.b(paramCacheVersionDir, "cacheVersionDir");
    l.b(paramRequestResultInfo, "requestResultInfo");
    paramCacheVersionDir.checkLocked();
    File file = paramCacheVersionDir.getMetaFile();
    String str = IOUtils.readString(file.getAbsolutePath(), "utf-8");
    if (TextUtils.isEmpty(str)) {
      paramRequestResultInfo.errorCode = ErrorCode.META.NULL.getCode();
      paramRequestResultInfo.errorMsg = "cachedData is empty";
      return false;
    } 
    try {
      byte b;
      JSONObject jSONObject = new JSONObject(str);
      str = jSONObject.getString("value");
      String str1 = jSONObject.getString("encryKey");
      String str2 = jSONObject.getString("encryIV");
      if (ProcessUtil.isMainProcess(paramCacheVersionDir.getContext())) {
        b = 0;
      } else {
        if (ProcessUtil.isMiniappProcess()) {
          boolean bool = true;
          paramRequestResultInfo.fromProcess = bool;
          paramRequestResultInfo.encryKey = str1;
          paramRequestResultInfo.encryIV = str2;
          paramRequestResultInfo.originData = str;
          return true;
        } 
        b = -1;
      } 
      paramRequestResultInfo.fromProcess = b;
      paramRequestResultInfo.encryKey = str1;
      paramRequestResultInfo.encryIV = str2;
      paramRequestResultInfo.originData = str;
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("LaunchCacheHelper", new Object[] { exception });
      paramRequestResultInfo.errorCode = ErrorCode.META.PARSE_ERROR.getCode();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getMessage());
      stringBuilder.append("\n");
      stringBuilder.append(Log.getStackTraceString(exception));
      paramRequestResultInfo.errorMsg = stringBuilder.toString();
      IOUtils.delete(file);
      return false;
    } 
  }
  
  public final boolean isMetaExpired(LaunchCacheDAO.CacheVersionDir paramCacheVersionDir) {
    l.b(paramCacheVersionDir, "normalVersionDir");
    int i = getMetaExpireTime(paramCacheVersionDir.getContext());
    if (i < 0)
      return false; 
    long l = paramCacheVersionDir.getUpdateTime();
    l = System.currentTimeMillis() - l;
    return (l < 0L || l > i);
  }
  
  public final void saveMetaDataLocked(LaunchCacheDAO.CacheVersionDir paramCacheVersionDir, AppInfoEntity paramAppInfoEntity, String paramString1, String paramString2, String paramString3) {
    l.b(paramCacheVersionDir, "cacheVersionDir");
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramString1, "encryKey");
    l.b(paramString2, "encryIV");
    l.b(paramString3, "data");
    paramCacheVersionDir.checkLocked();
    paramString3 = mergeMeta(paramCacheVersionDir, paramAppInfoEntity, paramString1, paramString2, paramString3);
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("encryKey", paramString1).put("encryIV", paramString2).put("value", paramString3);
      File file = paramCacheVersionDir.getMetaFile();
      IOUtils.delete(file);
      IOUtils.writeStringToFile(file.getAbsolutePath(), jSONObject.toString(), "utf-8");
      if (!file.exists())
        return; 
      paramCacheVersionDir.setUpdateTimeLocked(System.currentTimeMillis());
      if (paramCacheVersionDir.getCurrentStatusFlag() == null) {
        paramCacheVersionDir.setStatusFlagLocked(StatusFlagType.Downloading);
        return;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("LaunchCacheHelper", new Object[] { exception });
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\LaunchCacheHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */