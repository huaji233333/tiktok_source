package com.tt.miniapphost.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.base.MiniAppContextWrapper;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapp.service.suffixmeta.SuffixMetaEntity;
import com.tt.miniapp.service.suffixmeta.SuffixMetaServiceInterface;
import com.tt.miniapp.util.UserInfoUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.DisableStateEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.language.LocaleUtils;
import java.io.File;
import java.util.Locale;

public class AppbrandUtil {
  public static String convertFourToThreeVersion(String paramString) {
    int i = paramString.lastIndexOf(".");
    String str = paramString;
    if (i > 0)
      str = paramString.substring(0, i); 
    return str;
  }
  
  public static String convertVersionCodeToStr(long paramLong) {
    try {
      String str = sdkVersionIntToStr(paramLong, 3);
      if (!TextUtils.isEmpty(str))
        AppBrandLogger.d("AppbrandUtil", new Object[] { "localUpdateVersion ", str }); 
      return str;
    } catch (Exception exception) {
      AppBrandLogger.e("AppbrandUtil", new Object[] { exception });
      return "";
    } 
  }
  
  public static long convertVersionStrToCode(String paramString) {
    return !TextUtils.isEmpty(paramString) ? getIntFromStr(paramString.split("\\.")) : -1L;
  }
  
  public static File getAppServiceDir(Context paramContext) {
    return new File(getAppbrandBaseFile(paramContext), "base_bundle/");
  }
  
  public static File getAppbrandBaseFile(Context paramContext) {
    String str2 = HostDependManager.getInst().getPrefixPath();
    File file = paramContext.getFilesDir();
    boolean bool = TextUtils.isEmpty(str2);
    String str1 = "appbrand";
    if (!bool) {
      StringBuilder stringBuilder = new StringBuilder("appbrand");
      stringBuilder.append(File.separator);
      stringBuilder.append(str2);
      str1 = stringBuilder.toString();
    } 
    return new File(file, str1);
  }
  
  public static String getApplicationName(Context paramContext) {
    PackageManager packageManager;
    PackageManager.NameNotFoundException nameNotFoundException2 = null;
    try {
      packageManager = paramContext.getApplicationContext().getPackageManager();
      try {
        ApplicationInfo applicationInfo = _lancet.com_ss_android_ugc_aweme_lancet_launch_AwemeMetaDataLancet_getApplicationInfo(packageManager, paramContext.getPackageName(), 0);
      } catch (android.content.pm.PackageManager.NameNotFoundException null) {
        nameNotFoundException1 = nameNotFoundException2;
      } 
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException1) {
      packageManager = null;
      nameNotFoundException1 = nameNotFoundException2;
    } 
    return (String)packageManager.getApplicationLabel((ApplicationInfo)nameNotFoundException1);
  }
  
  public static String getCurrentDeviceFlag() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Build.BRAND);
    stringBuilder.append("-");
    stringBuilder.append(Build.MODEL);
    return stringBuilder.toString();
  }
  
  public static int getIntFromStr(String[] paramArrayOfString) {
    if (paramArrayOfString != null) {
      if (paramArrayOfString.length <= 0)
        return 0; 
      int k = paramArrayOfString.length;
      int i = 0;
      int j = 0;
      try {
        while (i < paramArrayOfString.length) {
          int m = Integer.parseInt(paramArrayOfString[i]) * (int)Math.pow(100.0D, (k - 1)) / (int)Math.pow(100.0D, i);
          j += m;
          i++;
        } 
        return j;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "AppbrandUtil", exception.getStackTrace());
      } 
    } 
    return 0;
  }
  
  public static File getOfflineUpdateDir(Context paramContext) {
    return new File(getAppbrandBaseFile(paramContext), "offline_update/");
  }
  
  public static File getOfflineZipDir(Context paramContext) {
    return new File(getAppbrandBaseFile(paramContext), "offline/");
  }
  
  public static void getUidByOpenId(Context paramContext, String paramString1, String paramString2, final GetUidByOpenIdListener listener) {
    UserInfoUtil.getUid(paramContext, paramString1, paramString2, new UserInfoUtil.GetUidListener() {
          public final void onFail(int param1Int, String param1String) {
            listener.onFail(param1Int, param1String);
          }
          
          public final void onSuccess(String param1String) {
            listener.onSuccess(param1String);
          }
        });
  }
  
  public static String getUpdateVersionStr(String paramString) {
    try {
      paramString = sdkVersionIntToStr(Long.valueOf(paramString).longValue(), 3);
      if (!TextUtils.isEmpty(paramString))
        AppBrandLogger.d("AppbrandUtil", new Object[] { "localUpdateVersion ", paramString }); 
      return paramString;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "AppbrandUtil", exception.getStackTrace());
      return "-1";
    } 
  }
  
  public static void handleAppbrandDisableState(Context paramContext, DisableStateEntity paramDisableStateEntity) {
    if (TextUtils.isEmpty(paramDisableStateEntity.getHintUrl())) {
      HostDependManager.getInst().showToast(paramContext, null, paramDisableStateEntity.getHintMessage(), 1L, null);
    } else {
      HostDependManager.getInst().jumpToWebView(paramContext, paramDisableStateEntity.getHintUrl(), "", true);
    } 
    StringBuilder stringBuilder = new StringBuilder("handleAppbrandDisableState: ");
    stringBuilder.append(paramDisableStateEntity.getHintMessage());
    AppBrandLogger.e("AppbrandUtil", new Object[] { stringBuilder.toString() });
  }
  
  public static boolean isAppBundleEnable() {
    if (!AppbrandContext.getInst().isInitParamsReady()) {
      AppBrandLogger.d("AppbrandUtil", new Object[] { "appbundle not ready" });
      return true;
    } 
    try {
      return HostDependManager.getInst().isEnableAppBundleMode();
    } finally {
      Exception exception = null;
      AppBrandLogger.eWithThrowable("AppbrandUtil", "isAppBundleEnable", exception);
    } 
  }
  
  public static boolean isBlockShareChannel(String paramString) {
    MiniAppContextWrapper miniAppContextWrapper = AppbrandApplicationImpl.getInst().getMiniAppContext();
    if (miniAppContextWrapper == null || miniAppContextWrapper.getAppInfo() == null)
      return false; 
    SuffixMetaEntity suffixMetaEntity = ((SuffixMetaServiceInterface)miniAppContextWrapper.getService(SuffixMetaServiceInterface.class)).get();
    return (suffixMetaEntity.shareChannelBlackList == null) ? HostDependManager.getInst().isBlockChanelDefault(paramString, miniAppContextWrapper.getAppInfo().isGame() ^ true) : suffixMetaEntity.shareChannelBlackList.contains(paramString);
  }
  
  public static String sdkVersionIntToStr(long paramLong, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    while (paramInt >= 0) {
      double d = paramInt;
      stringBuilder.append(String.valueOf(paramLong / (int)Math.pow(100.0D, d)));
      stringBuilder.append(".");
      paramLong %= (int)Math.pow(100.0D, d);
      paramInt--;
    } 
    if (!TextUtils.isEmpty(stringBuilder) && stringBuilder.length() > 0)
      stringBuilder.deleteCharAt(stringBuilder.length() - 1); 
    return stringBuilder.toString();
  }
  
  public static void setLanguage(Locale paramLocale) {
    if (paramLocale == null) {
      DebugUtil.logOrThrow("AppbrandUtil", new Object[] { "switch lang with null" });
      return;
    } 
    LocaleManager.getInst().notifyLangChange(paramLocale);
    InnerMiniAppProcessBridge.broadcastLangChangeEvnet(LocaleUtils.locale2String(paramLocale));
  }
  
  public static interface GetUidByOpenIdListener {
    void onFail(int param1Int, String param1String);
    
    void onSuccess(String param1String);
  }
  
  class AppbrandUtil {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\AppbrandUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */