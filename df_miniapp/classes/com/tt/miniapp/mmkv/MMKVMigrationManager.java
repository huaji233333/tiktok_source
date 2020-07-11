package com.tt.miniapp.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import com.ss.android.ugc.aweme.keva.d;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Schedulers;
import com.tt.miniapp.manager.StorageManager;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapp.storage.Storage;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MMKVMigrationManager {
  private static List<File> getSPList(Context paramContext) {
    File file = new File(paramContext.getFilesDir().getParentFile(), "shared_prefs");
    ArrayList<File> arrayList = new ArrayList();
    FileUtil.scanFileInDir(file, arrayList);
    return arrayList;
  }
  
  private static void importFromSharedPreferences(Context paramContext, SharedPreferences paramSharedPreferences, String paramString) {
    if (paramSharedPreferences != null && paramSharedPreferences.getAll() != null) {
      if (paramSharedPreferences.getAll().size() <= 0)
        return; 
      Map map = paramSharedPreferences.getAll();
      if (map != null && map.size() > 0) {
        for (Map.Entry entry : map.entrySet()) {
          String str = (String)entry.getKey();
          entry = (Map.Entry)entry.getValue();
          if (MMKVUtil.isKeyValueExist(paramContext, paramString, str)) {
            AppBrandLogger.d("MMKVMigrationManager", new Object[] { "not import" });
            continue;
          } 
          AppBrandLogger.d("MMKVMigrationManager", new Object[] { "import" });
          if (str != null && entry != null) {
            if (entry instanceof Boolean) {
              MMKVUtil.setBoolean(paramContext, paramString, str, ((Boolean)entry).booleanValue());
              continue;
            } 
            if (entry instanceof Integer) {
              MMKVUtil.setInt(paramContext, paramString, str, ((Integer)entry).intValue());
              continue;
            } 
            if (entry instanceof Long) {
              MMKVUtil.setLong(paramContext, paramString, str, ((Long)entry).longValue());
              continue;
            } 
            if (entry instanceof Float) {
              MMKVUtil.setFloat(paramContext, paramString, str, ((Float)entry).floatValue());
              continue;
            } 
            if (entry instanceof Double) {
              MMKVUtil.setDouble(paramContext, paramString, str, ((Double)entry).doubleValue());
              continue;
            } 
            if (entry instanceof String) {
              MMKVUtil.setString(paramContext, paramString, str, (String)entry);
              continue;
            } 
            if (entry instanceof Set) {
              MMKVUtil.setStringSet(paramContext, paramString, str, (Set<String>)entry);
              continue;
            } 
            if (entry instanceof byte[]) {
              MMKVUtil.setBytes(paramContext, paramString, str, (byte[])entry);
              continue;
            } 
            StringBuilder stringBuilder = new StringBuilder("unknown type:");
            stringBuilder.append(entry.getClass());
            AppBrandLogger.e("MMKVMigrationManager", new Object[] { stringBuilder.toString() });
          } 
        } 
        if (map.size() == MMKVUtil.getMMKVKeysLength(paramContext, paramString))
          MMKVUtil.setBoolean(paramContext, paramString, "is_migration_success", true); 
      } 
    } 
  }
  
  static boolean isMigrationSuccess(Context paramContext, String paramString) {
    return MMKVUtil.getBoolean(paramContext, paramString, "is_migration_success", false);
  }
  
  static void migrationAllSp(final Context context) {
    Observable.create(new Action() {
          public final void act() {
            MMKVMigrationManager.migrationFromSpByName(context, "tma_jssdk_info");
            MMKVMigrationManager.migrationFromSpByName(context, "vconsole_config");
            MMKVMigrationManager.migrationFromSpByName(context, "tmaUser");
            MMKVMigrationManager.migrationFromSpByName(context, "feedback_config");
            MMKVMigrationManager.migrationFromSpByName(context, "HostOptionPermissionDependImpl");
            MMKVMigrationManager.migrationFromSpByName(context, "settings_config");
            MMKVMigrationManager.migrationFromSpByName(context, AppbrandConstants.SharePreferences.getCookieSp());
            MMKVMigrationManager.migrationFromSpByName(context, StorageManager.getSpName());
            MMKVMigrationManager.migrationFromSpByName(context, "TmaSession");
            MMKVMigrationManager.migrationFromSpByName(context, "appbrand_file");
            MMKVMigrationManager.migrationFromSpByName(context, "tma_launch_config");
            MMKVMigrationManager.migrationFromSpByName(context, "openSchemaTime");
            MMKVMigrationManager.migrationByPrefix(context, Storage.getStorageFilePrefix());
            MMKVMigrationManager.migrationByPrefix(context, BrandPermissionUtils.getSpNamePrefix());
          }
        }).schudleOn(Schedulers.longIO()).subscribeSimple();
  }
  
  public static void migrationByPrefix(Context paramContext, String paramString) {
    for (File file : getSPList(paramContext)) {
      if (file.getName().startsWith(paramString))
        migrationFromSpByName(paramContext, FileUtil.getPrefixName(file)); 
    } 
  }
  
  public static void migrationFromSpByName(Context paramContext, String paramString) {
    if (!isMigrationSuccess(paramContext, paramString))
      importFromSharedPreferences(paramContext, d.a(paramContext, paramString, 0), paramString); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mmkv\MMKVMigrationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */