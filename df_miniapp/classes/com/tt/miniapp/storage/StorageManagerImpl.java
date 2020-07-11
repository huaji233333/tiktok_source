package com.tt.miniapp.storage;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.launchcache.LaunchCacheCleanDataManager;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.storage.path.AppbrandPathManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.dynamic.IStorageManager;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.StorageUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StorageManagerImpl implements IStorageManager {
  @Deprecated
  private void cleanAllSession(Context paramContext) {
    KVUtil.getSharedPreferences(paramContext, "TmaSession").edit().clear().commit();
  }
  
  private boolean cleanById(Context paramContext, File paramFile, String paramString) {
    try {
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean start, id: ", paramString });
      IOUtils.clearDir(getTempDir(paramFile, paramString));
      IOUtils.clearDir(getUserDir(paramFile, paramString));
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean end,id: ", paramString });
      cleanPermissionConfig(paramContext, paramString);
      cleanStorageInfo(paramContext, paramString);
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean StorageInfo end, id: ", paramString });
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("StorageManagerImpl", new Object[] { exception });
      return false;
    } 
  }
  
  private void cleanCookie(Context paramContext) {
    KVUtil.getSharedPreferences(paramContext, AppbrandConstants.SharePreferences.getCookieSp()).edit().clear().commit();
  }
  
  private void cleanFavoriteMiniAppList() {
    InnerHostProcessBridge.updateFavoriteSet(null);
  }
  
  private void cleanLaunchConfig(Context paramContext) {
    KVUtil.getSharedPreferences(paramContext, "tma_launch_config").edit().clear().commit();
  }
  
  private void cleanPermissionConfig(Context paramContext, String paramString) {
    KVUtil.getSharedPreferences(paramContext, getPermissionSpName(paramString)).edit().clear().commit();
  }
  
  private void cleanStorageInfo(Context paramContext, String paramString) {
    KVUtil.getSharedPreferences(paramContext, getStorageFileName(paramString)).edit().clear().commit();
  }
  
  private void ensureDir(File paramFile) {
    if (!paramFile.exists())
      paramFile.mkdirs(); 
  }
  
  private String getPermissionSpName(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(BrandPermissionUtils.getSpNamePrefix());
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  private String getStorageFileName(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Storage.getStorageFilePrefix());
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  private File getTempDir(File paramFile, String paramString) {
    StringBuilder stringBuilder = new StringBuilder("temp/");
    stringBuilder.append(paramString);
    paramFile = new File(paramFile, stringBuilder.toString());
    ensureDir(paramFile);
    return paramFile;
  }
  
  private File getUserDir(File paramFile, String paramString) {
    StringBuilder stringBuilder = new StringBuilder("user/");
    stringBuilder.append(paramString);
    paramFile = new File(paramFile, stringBuilder.toString());
    ensureDir(paramFile);
    return paramFile;
  }
  
  public boolean cleanAllMiniAppStorage() {
    AppBrandLogger.d("StorageManagerImpl", new Object[] { "not login" });
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null) {
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "context == null" });
      return false;
    } 
    File file = AppbrandConstant.getMiniAppRootDir((Context)application);
    if (file != null && file.exists()) {
      if (!file.isDirectory())
        return false; 
      file = new File(StorageUtil.getExternalCacheDir((Context)application), "TT/sandbox");
      for (String str : getAllInstalledApp()) {
        cleanById((Context)application, file, str);
        StorageUtil.saveSession((Context)application, str, "");
      } 
      cleanLaunchConfig((Context)application);
      cleanCookie((Context)application);
      cleanAllSession((Context)application);
      cleanFavoriteMiniAppList();
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean cleanAllSession end" });
      return true;
    } 
    return false;
  }
  
  public boolean cleanMiniAppStorage(String paramString) {
    if (TextUtils.isEmpty(paramString))
      AppBrandLogger.e("StorageManagerImpl", new Object[] { "cleanMiniAppStorage appId is null" }); 
    AppBrandLogger.d("StorageManagerImpl", new Object[] { "not login" });
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null) {
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "context == null" });
      return false;
    } 
    if (getAllInstalledApp().contains(paramString)) {
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean start, id: ", paramString });
      File file = new File(StorageUtil.getExternalCacheDir((Context)application), "TT/sandbox");
      IOUtils.clearDir(getTempDir(file, paramString));
      IOUtils.clearDir(getUserDir(file, paramString));
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean end,id: ", paramString });
      cleanPermissionConfig((Context)application, paramString);
      cleanStorageInfo((Context)application, paramString);
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean StorageInfo end, id: ", paramString });
      return true;
    } 
    return false;
  }
  
  public long clear() {
    return AppbrandPathManager.getInstance().clear();
  }
  
  public List<String> getAllInstalledApp() {
    ArrayList<String> arrayList = new ArrayList();
    Application application = AppbrandContext.getInst().getApplicationContext();
    int i = 0;
    if (application == null) {
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "context == null" });
      return arrayList;
    } 
    File file = AppbrandConstant.getMiniAppRootDir((Context)application);
    if (file != null && file.exists() && file.isDirectory()) {
      String[] arrayOfString = file.list();
      if (arrayOfString != null) {
        int j = arrayOfString.length;
        while (i < j) {
          String str = arrayOfString[i];
          if (!TextUtils.isEmpty(str) && str.startsWith("tt"))
            arrayList.add(str); 
          i++;
        } 
      } 
    } 
    Iterator<LaunchCacheDAO.CacheAppIdDir> iterator = LaunchCacheDAO.INSTANCE.listCacheAppIdDirs((Context)application).iterator();
    while (iterator.hasNext())
      arrayList.add(((LaunchCacheDAO.CacheAppIdDir)iterator.next()).getAppId()); 
    return arrayList;
  }
  
  public Map<String, Long> getCachePathAndSize() {
    return AppbrandPathManager.getInstance().getCachePathAndSize();
  }
  
  public long getCacheSize() {
    return AppbrandPathManager.getInstance().getCacheSize();
  }
  
  public Map<String, Long> getPathAndSize() {
    return AppbrandPathManager.getInstance().getPathAndSize();
  }
  
  public long getTotalSize() {
    return AppbrandPathManager.getInstance().getTotalSize();
  }
  
  public boolean removeMiniApp(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("removeMiniApp");
    stringBuilder.append(paramString);
    AppBrandLogger.d("StorageManagerImpl", new Object[] { stringBuilder.toString() });
    try {
      Application application = AppbrandContext.getInst().getApplicationContext();
      if (AppProcessManager.isAppProcessExist((Context)application, paramString)) {
        AppBrandLogger.d("StorageManagerImpl", new Object[] { "app process exit" });
        AppProcessManager.killProcess(paramString);
        AppBrandLogger.d("StorageManagerImpl", new Object[] { "killProcess success" });
      } 
      cleanMiniAppStorage(paramString);
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "cleanMiniAppStorage" });
      LaunchCacheCleanDataManager.INSTANCE.cleanMiniAppCache((Context)application, paramString);
      AppBrandLogger.d("StorageManagerImpl", new Object[] { "clean pkg" });
      return true;
    } catch (Exception exception) {
      AppBrandLogger.e("StorageManagerImpl", new Object[] { exception });
      return false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\StorageManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */