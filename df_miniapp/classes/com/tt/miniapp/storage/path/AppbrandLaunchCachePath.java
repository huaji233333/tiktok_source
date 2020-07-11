package com.tt.miniapp.storage.path;

import android.content.Context;
import com.tt.miniapp.launchcache.LaunchCacheCleanDataManager;
import com.tt.miniapphost.AppbrandContext;

public class AppbrandLaunchCachePath extends AbsAppbrandPath {
  public boolean canRead() {
    return false;
  }
  
  public boolean canWrite() {
    return false;
  }
  
  public long clear() {
    return LaunchCacheCleanDataManager.INSTANCE.clearUnused((Context)AppbrandContext.getInst().getApplicationContext());
  }
  
  public boolean isCleanable() {
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\path\AppbrandLaunchCachePath.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */