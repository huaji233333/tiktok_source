package com.tt.miniapp.storage.path;

import com.tt.miniapphost.util.IOUtils;
import java.io.File;
import java.util.HashMap;

public class AppbrandRootPath extends AbsAppbrandPath {
  private String[] mFestivalActivitys = new String[] { "tt1a1e6ca38534a23c", "tt7a180ca5e532c238", "ttec4d9af07367551a" };
  
  public boolean canRead() {
    return false;
  }
  
  public boolean canWrite() {
    return false;
  }
  
  public long clear() {
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
          HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
          int j = arrayOfFile.length;
          boolean bool = false;
          int i;
          for (i = 0; i < j; i++) {
            File file1 = arrayOfFile[i];
            if (file1.isDirectory() && file1.getName().startsWith("tt"))
              hashMap.put(file1.getName(), file1); 
          } 
          l2 = l1;
          if (!hashMap.isEmpty()) {
            String[] arrayOfString = this.mFestivalActivitys;
            j = arrayOfString.length;
            i = bool;
            while (true) {
              l2 = l1;
              if (i < j) {
                File file1 = (File)hashMap.get(arrayOfString[i]);
                l2 = l1;
                if (file1 != null) {
                  l2 = l1;
                  if (file1.exists()) {
                    l2 = l1 + file1.length();
                    IOUtils.clearDir(file1);
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
    } 
    return l2;
  }
  
  public boolean isCleanable() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\path\AppbrandRootPath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */