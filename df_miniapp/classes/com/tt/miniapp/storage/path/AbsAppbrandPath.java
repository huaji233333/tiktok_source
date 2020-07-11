package com.tt.miniapp.storage.path;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.FileUtil;
import com.tt.miniapphost.util.IOUtils;
import java.io.File;

public abstract class AbsAppbrandPath {
  protected File mFile;
  
  public abstract boolean canRead();
  
  public abstract boolean canWrite();
  
  public long clear() {
    if (isCleanable()) {
      long l = getTotalSize();
      try {
        IOUtils.clearDir(this.mFile);
        return l;
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "AbsAppbrandPath", exception.getStackTrace());
        return l;
      } 
    } 
    return 0L;
  }
  
  public String getAbsolutePath() {
    File file = this.mFile;
    return (file == null) ? "" : file.getAbsolutePath();
  }
  
  public long getTotalSize() {
    File file = this.mFile;
    if (file != null && file.exists())
      try {
        return FileUtil.getFileSize(this.mFile);
      } catch (Exception exception) {
        AppBrandLogger.stacktrace(6, "AbsAppbrandPath", exception.getStackTrace());
      }  
    return 0L;
  }
  
  public abstract boolean isCleanable();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\path\AbsAppbrandPath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */