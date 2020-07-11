package com.tt.miniapp.storage.path;

import android.app.Application;
import android.text.TextUtils;
import com.tt.miniapphost.AppbrandContext;
import java.io.File;

public class AppbrandCpSpPath extends AbsAppbrandPath {
  public AppbrandCpSpPath() {
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application != null)
      this.mFile = new File(application.getFilesDir().getParentFile().getAbsolutePath(), "/shared_prefs"); 
  }
  
  public boolean canRead() {
    return false;
  }
  
  public boolean canWrite() {
    return false;
  }
  
  public long getTotalSize() {
    File file = this.mFile;
    long l1 = 0L;
    long l2 = l1;
    if (file != null) {
      l2 = l1;
      if (this.mFile.exists()) {
        l2 = l1;
        if (this.mFile.isDirectory()) {
          File[] arrayOfFile = this.mFile.listFiles();
          if (arrayOfFile == null)
            return 0L; 
          int j = arrayOfFile.length;
          int i = 0;
          while (true) {
            l2 = l1;
            if (i < j) {
              File file1 = arrayOfFile[i];
              l2 = l1;
              if (file1 != null) {
                String str = file1.getName();
                l2 = l1;
                if (!TextUtils.isEmpty(str)) {
                  l2 = l1;
                  if (str.startsWith("tma_storage_"))
                    l2 = l1 + file1.length(); 
                } 
              } 
              i++;
              l1 = l2;
              continue;
            } 
            break;
          } 
        } 
      } 
    } 
    return l2;
  }
  
  public boolean isCleanable() {
    return false;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\path\AppbrandCpSpPath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */