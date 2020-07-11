package com.tt.miniapphost.dynamic;

import java.util.Map;

public interface IStorageManager {
  boolean cleanAllMiniAppStorage();
  
  boolean cleanMiniAppStorage(String paramString);
  
  long clear();
  
  Map<String, Long> getCachePathAndSize();
  
  long getCacheSize();
  
  Map<String, Long> getPathAndSize();
  
  long getTotalSize();
  
  boolean removeMiniApp(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\dynamic\IStorageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */