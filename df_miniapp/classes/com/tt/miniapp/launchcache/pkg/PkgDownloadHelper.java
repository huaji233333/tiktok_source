package com.tt.miniapp.launchcache.pkg;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.StatusFlagType;
import com.tt.miniapp.launchcache.meta.AppInfoHelper;
import com.tt.miniapp.service.PureServiceInterface;
import com.tt.miniapp.service.ServiceProvider;
import com.tt.miniapp.service.codecache.CodeCacheService;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.IOUtils;
import d.f.b.l;
import d.m.p;
import d.u;
import java.io.File;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class PkgDownloadHelper {
  public static final PkgDownloadHelper INSTANCE = new PkgDownloadHelper();
  
  public final void codeCache(AppInfoEntity paramAppInfoEntity, File paramFile) {
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramFile, "file");
    PureServiceInterface pureServiceInterface = ServiceProvider.getInstance().getService(CodeCacheService.class);
    l.a(pureServiceInterface, "ServiceProvider.getInsta…CacheService::class.java)");
    ((CodeCacheService)pureServiceInterface).codeCacheMiniAppPkg(paramAppInfoEntity, paramFile);
  }
  
  public final String getDownloadMpServiceName(RequestType paramRequestType) {
    return (paramRequestType == null || PkgDownloadHelper$WhenMappings.$EnumSwitchMapping$0[paramRequestType.ordinal()] != 1) ? "mp_start_download_case" : "es_preload_download_case";
  }
  
  public final int getPkgCompressType(String paramString) {
    return TextUtils.isEmpty(paramString) ? 1 : 2;
  }
  
  public final boolean hasValidPkg(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "appId");
    LaunchCacheDAO.LockObject lockObject = LaunchCacheDAO.INSTANCE.getCacheAppIdDir(paramContext, paramString).lock();
    if (lockObject == null)
      return false; 
    try {
      for (LaunchCacheDAO.CacheVersionDir cacheVersionDir : LaunchCacheDAO.INSTANCE.getCacheAppIdDir(paramContext, paramString).listCacheVersionDirs()) {
        if (cacheVersionDir.getRequestType() == RequestType.normal && cacheVersionDir.checkStatusFlag(StatusFlagType.Verified)) {
          boolean bool = cacheVersionDir.getPkgFile().exists();
          if (bool)
            return true; 
        } 
      } 
      return false;
    } finally {
      lockObject.unlock();
    } 
  }
  
  public final boolean isPkgFileValid(AppInfoEntity paramAppInfoEntity, File paramFile, Map<String, String> paramMap) {
    StringBuilder stringBuilder;
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramFile, "pkgFile");
    l.b(paramMap, "mpExtraInfoMap");
    if (!paramFile.exists()) {
      stringBuilder = new StringBuilder("pkgFile not found pkgFilePath:");
      stringBuilder.append(paramFile.getAbsolutePath());
      paramMap.put("error_msg", stringBuilder.toString());
      return false;
    } 
    if (TextUtils.isEmpty(((AppInfoEntity)stringBuilder).md5)) {
      paramMap.put("error_msg", "MD5 string empty");
      return false;
    } 
    String str1 = IOUtils.calculateMD5(paramFile, 8192);
    if (str1 == null) {
      paramMap.put("error_msg", "calculatedDigest null");
      return false;
    } 
    Locale locale1 = Locale.getDefault();
    l.a(locale1, "Locale.getDefault()");
    String str2 = str1.toLowerCase(locale1);
    l.a(str2, "(this as java.lang.String).toLowerCase(locale)");
    String str3 = ((AppInfoEntity)stringBuilder).md5;
    l.a(str3, "appInfo.md5");
    Locale locale2 = Locale.getDefault();
    l.a(locale2, "Locale.getDefault()");
    if (str3 != null) {
      str3 = str3.toLowerCase(locale2);
      l.a(str3, "(this as java.lang.String).toLowerCase(locale)");
      if (!p.b(str2, str3, false, 2, null)) {
        paramMap.put("error_msg", "calculatedDigest is not match");
        paramMap.put("calculated_digest", str1);
        String str = ((AppInfoEntity)stringBuilder).md5;
        l.a(str, "appInfo.md5");
        paramMap.put("provided_digest", str);
        return false;
      } 
      return true;
    } 
    throw new u("null cannot be cast to non-null type java.lang.String");
  }
  
  public final void updatePkgCompressType(AppInfoEntity paramAppInfoEntity, RequestType paramRequestType) {
    l.b(paramAppInfoEntity, "appInfoEntity");
    l.b(paramRequestType, "downloadType");
    if (!TextUtils.isEmpty(paramAppInfoEntity.pkgCompressType)) {
      boolean bool;
      AppbrandContext appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      if (SettingsDAO.getInt((Context)appbrandContext.getApplicationContext(), 1, new Enum[] { (Enum)Settings.BDP_TTPKG_CONFIG, (Enum)Settings.BdpTtPkgConfig.PKG_COMPRESS_DOWNGRADE }) == 1) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool) {
        paramAppInfoEntity.pkgCompressType = "";
        AppBrandLogger.i("PkgDownloadHelper", new Object[] { "StreamDownloadMgr-pkgCompressType: pkgCompressType downgrade to default" });
        return;
      } 
      appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      int i = SettingsDAO.getInt((Context)appbrandContext.getApplicationContext(), 0, new Enum[] { (Enum)Settings.BDP_TTPKG_CONFIG, (Enum)Settings.BdpTtPkgConfig.BR_DOWNLOAD_TYPES_KEY });
      if ((paramRequestType == RequestType.normal && (i & 0x1) != 1) || (paramRequestType == RequestType.async && (i & 0x2) != 2) || (paramRequestType == RequestType.preload && (i & 0x4) != 4) || (paramRequestType == RequestType.silence && (i & 0x8) != 8)) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool) {
        paramAppInfoEntity.pkgCompressType = "";
        AppBrandLogger.i("PkgDownloadHelper", new Object[] { "StreamDownloadMgr-pkgCompressType: downloadType=", paramRequestType, "brDownloadTypes=", Integer.valueOf(i) });
        return;
      } 
    } 
    AppBrandLogger.i("PkgDownloadHelper", new Object[] { "StreamDownloadMgr-pkgCompressType: ", paramAppInfoEntity.pkgCompressType });
  }
  
  public final void uploadDownloadFailStat(AppInfoEntity paramAppInfoEntity, RequestType paramRequestType, String paramString1, long paramLong1, String paramString2, int paramInt, long paramLong2) {
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestType, "downloadType");
    l.b(paramString2, "mpErrMsg");
    Event.Builder builder = Event.builder("mp_download_result", paramAppInfoEntity).kv("request_type", paramRequestType).kv("pkg_compress_type", Integer.valueOf(getPkgCompressType(paramAppInfoEntity.pkgCompressType))).kv("url", paramString1).kv("duration", Long.valueOf(paramLong1)).kv("result_type", "fail").kv("error_msg", paramString2).kv("http_status", Integer.valueOf(paramInt));
    paramLong1 = paramLong2;
    if (paramLong2 >= 0L)
      paramLong1 = paramLong2 / 1024L; 
    builder.kv("content_length", Long.valueOf(paramLong1)).flush();
  }
  
  public final void uploadDownloadInstallFailMpMonitor(AppInfoEntity paramAppInfoEntity, RequestType paramRequestType, String paramString, Map<String, String> paramMap, int paramInt) {
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestType, "downloadType");
    l.b(paramString, "mpErrMsg");
    try {
      String str;
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", paramString);
      jSONObject.put("appInfo", paramAppInfoEntity.toString());
      if (paramAppInfoEntity.isGame()) {
        str = "micro_game";
      } else {
        str = "micro_app";
      } 
      jSONObject.put("_param_for_special", str);
      if (paramMap != null)
        for (Map.Entry<String, String> entry : paramMap.entrySet())
          jSONObject.put((String)entry.getKey(), entry.getValue());  
      AppBrandMonitor.statusRate(AppInfoHelper.getErrorMpServiceName(paramRequestType), paramInt, jSONObject);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("PkgDownloadHelper", "uploadDownloadInstallFailMpMonitor", (Throwable)jSONException);
      return;
    } 
  }
  
  public final void uploadDownloadSuccessMpMonitor(AppInfoEntity paramAppInfoEntity, RequestType paramRequestType, String paramString) {
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestType, "downloadType");
    l.b(paramString, "mpMsg");
    try {
      String str;
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("errMsg", paramString);
      if (paramAppInfoEntity.isGame()) {
        str = "micro_game";
      } else {
        str = "micro_app";
      } 
      jSONObject.put("_param_for_special", str);
      AppBrandMonitor.statusRate(getDownloadMpServiceName(paramRequestType), 0, jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.eWithThrowable("PkgDownloadHelper", "uploadDownloadSuccessMpMonitor", exception);
      return;
    } 
  }
  
  public final void uploadDownloadSuccessStat(AppInfoEntity paramAppInfoEntity, RequestType paramRequestType, String paramString, long paramLong1, int paramInt, long paramLong2) {
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestType, "downloadType");
    Event.Builder builder = Event.builder("mp_download_result", paramAppInfoEntity).kv("request_type", paramRequestType).kv("pkg_compress_type", Integer.valueOf(getPkgCompressType(paramAppInfoEntity.pkgCompressType))).kv("url", paramString).kv("duration", Long.valueOf(paramLong1)).kv("result_type", "success").kv("http_status", Integer.valueOf(paramInt));
    paramLong1 = paramLong2;
    if (paramLong2 >= 0L)
      paramLong1 = paramLong2 / 1024L; 
    builder.kv("content_length", Long.valueOf(paramLong1)).flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\PkgDownloadHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */