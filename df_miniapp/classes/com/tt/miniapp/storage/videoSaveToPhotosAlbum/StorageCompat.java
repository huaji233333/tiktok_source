package com.tt.miniapp.storage.videoSaveToPhotosAlbum;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

public final class StorageCompat {
  static final StorageCompatBaseImpl IMPL = new StorageCompatBaseImpl();
  
  public static String getSystemCameraDir(Context paramContext) {
    return IMPL.getSystemCameraDir(paramContext);
  }
  
  static {
    if ("VIVO".equals(Build.BRAND.toUpperCase())) {
      IMPL = new StorageCompatVivoImpl();
      return;
    } 
  }
  
  static class StorageCompatBaseImpl {
    public String getSystemCameraDir(Context param1Context) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Environment.getExternalStorageDirectory());
      stringBuilder.append("/DCIM/Camera/");
      return stringBuilder.toString();
    }
  }
  
  static class StorageCompatVivoImpl extends StorageCompatBaseImpl {
    public String getSystemCameraDir(Context param1Context) {
      if (Util.isEngPath())
        return super.getSystemCameraDir(param1Context); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Environment.getExternalStorageDirectory());
      stringBuilder.append("/相机/");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\videoSaveToPhotosAlbum\StorageCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */