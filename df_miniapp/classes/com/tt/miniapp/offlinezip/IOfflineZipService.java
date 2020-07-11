package com.tt.miniapp.offlinezip;

import android.content.Context;
import java.io.File;

public interface IOfflineZipService {
  String getDEBUG_FLAG();
  
  String getEXTERNAL_OFFLINE_PATH();
  
  int getInternalOfflineZipVersion(Context paramContext);
  
  String getMD5_FILE_SUFFIX();
  
  String getSpecifiedOfflineModuleVersion(Context paramContext, File paramFile, String paramString);
  
  String getSpecifiedOfflineModuleVersion(Context paramContext, String paramString);
  
  String getZIP_FILE_SUFFIX();
  
  boolean isOfflineModuleNeedUpdate(Context paramContext, String paramString1, String paramString2);
  
  void setInternalOfflineZipVersion(Context paramContext, int paramInt);
  
  void setSpecifiedOfflineModuleVersion(File paramFile, String paramString);
  
  public static final class DefaultImpls {
    public static String getDEBUG_FLAG(IOfflineZipService param1IOfflineZipService) {
      return "debug_flag";
    }
    
    public static String getEXTERNAL_OFFLINE_PATH(IOfflineZipService param1IOfflineZipService) {
      return "/offline";
    }
    
    public static String getMD5_FILE_SUFFIX(IOfflineZipService param1IOfflineZipService) {
      return ".md5";
    }
    
    public static String getZIP_FILE_SUFFIX(IOfflineZipService param1IOfflineZipService) {
      return ".zip";
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\offlinezip\IOfflineZipService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */