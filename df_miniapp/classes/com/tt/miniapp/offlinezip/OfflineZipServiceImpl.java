package com.tt.miniapp.offlinezip;

import android.content.Context;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.AppbrandUtil;
import d.f.b.g;
import d.f.b.l;
import d.m.p;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public final class OfflineZipServiceImpl implements IOfflineZipService {
  public static final Companion Companion = new Companion(null);
  
  public final String getDEBUG_FLAG() {
    return IOfflineZipService.DefaultImpls.getDEBUG_FLAG(this);
  }
  
  public final String getEXTERNAL_OFFLINE_PATH() {
    return IOfflineZipService.DefaultImpls.getEXTERNAL_OFFLINE_PATH(this);
  }
  
  public final int getInternalOfflineZipVersion(Context paramContext) {
    l.b(paramContext, "context");
    return KVUtil.getSharedPreferences(paramContext, "offline_zip").getInt("offline_zip_version", 0);
  }
  
  public final String getMD5_FILE_SUFFIX() {
    return IOfflineZipService.DefaultImpls.getMD5_FILE_SUFFIX(this);
  }
  
  public final String getSpecifiedOfflineModuleVersion(Context paramContext, File paramFile, String paramString) {
    l.b(paramContext, "context");
    l.b(paramFile, "rootPath");
    l.b(paramString, "moduleName");
    File file = new File(paramFile, paramString);
    if (file.exists()) {
      String[] arrayOfString = file.list(new OfflineZipServiceImpl$getSpecifiedOfflineModuleVersion$md5Files$1());
      if (arrayOfString != null) {
        boolean bool;
        if (arrayOfString.length == 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if ((bool ^ true) != 0) {
          String str = arrayOfString[0];
          l.a(str, "md5Files[0]");
          return p.a(str, getMD5_FILE_SUFFIX(), "", false, 4, null);
        } 
      } 
    } 
    return "";
  }
  
  public final String getSpecifiedOfflineModuleVersion(Context paramContext, String paramString) {
    l.b(paramContext, "context");
    l.b(paramString, "moduleName");
    File file = AppbrandUtil.getOfflineZipDir(paramContext);
    l.a(file, "offlineDir");
    return getSpecifiedOfflineModuleVersion(paramContext, file, paramString);
  }
  
  public final String getZIP_FILE_SUFFIX() {
    return IOfflineZipService.DefaultImpls.getZIP_FILE_SUFFIX(this);
  }
  
  public final boolean isOfflineModuleNeedUpdate(Context paramContext, String paramString1, String paramString2) {
    l.b(paramContext, "context");
    l.b(paramString1, "moduleName");
    l.b(paramString2, "md5");
    String str = getSpecifiedOfflineModuleVersion(paramContext, paramString1);
    return ((l.a(paramString2, str) ^ true) != 0 && (l.a(getDEBUG_FLAG(), str) ^ true) != 0);
  }
  
  public final void setInternalOfflineZipVersion(Context paramContext, int paramInt) {
    l.b(paramContext, "context");
    KVUtil.getSharedPreferences(paramContext, "offline_zip").edit().putInt("offline_zip_version", paramInt).apply();
  }
  
  public final void setSpecifiedOfflineModuleVersion(File paramFile, String paramString) {
    l.b(paramFile, "moduleDir");
    l.b(paramString, "md5");
    if (paramFile.exists()) {
      File[] arrayOfFile = paramFile.listFiles(new OfflineZipServiceImpl$setSpecifiedOfflineModuleVersion$md5Files$1());
      if (arrayOfFile != null) {
        int j = arrayOfFile.length;
        for (int i = 0; i < j; i++)
          arrayOfFile[i].delete(); 
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(getMD5_FILE_SUFFIX());
      paramFile = new File(paramFile, stringBuilder.toString());
      try {
        paramFile.createNewFile();
        return;
      } catch (IOException iOException) {
        AppBrandLogger.e("tma_OfflineZipServiceImpl", new Object[] { "setSpecifiedOfflineModuleVersion", iOException });
      } 
    } 
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class OfflineZipServiceImpl$getSpecifiedOfflineModuleVersion$md5Files$1 implements FilenameFilter {
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "s");
      return p.c(param1String, OfflineZipServiceImpl.this.getMD5_FILE_SUFFIX(), false, 2, null);
    }
  }
  
  static final class OfflineZipServiceImpl$setSpecifiedOfflineModuleVersion$md5Files$1 implements FilenameFilter {
    public final boolean accept(File param1File, String param1String) {
      l.a(param1String, "s");
      return p.c(param1String, OfflineZipServiceImpl.this.getMD5_FILE_SUFFIX(), false, 2, null);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\offlinezip\OfflineZipServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */