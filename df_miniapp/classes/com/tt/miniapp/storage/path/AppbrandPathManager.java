package com.tt.miniapp.storage.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppbrandPathManager {
  private List<AbsAppbrandPath> mCleanableList = new ArrayList<AbsAppbrandPath>();
  
  private AbsAppbrandPath mLaunchCachePath = new AppbrandLaunchCachePath();
  
  private AbsAppbrandPath mRootPath = new AppbrandRootPath();
  
  private AbsAppbrandPath mSpPath = new AppbrandCpSpPath();
  
  private AbsAppbrandPath mTempPath = new AppbrandCpTempPath();
  
  private AbsAppbrandPath mUserPath = new AppbrandCpUserPath();
  
  private AppbrandPathManager() {
    this.mCleanableList.add(this.mTempPath);
    this.mCleanableList.add(this.mLaunchCachePath);
  }
  
  public static AppbrandPathManager getInstance() {
    return Holder.mInstance;
  }
  
  public long clear() {
    List<AbsAppbrandPath> list = this.mCleanableList;
    long l2 = 0L;
    long l1 = l2;
    if (list != null) {
      l1 = l2;
      if (!list.isEmpty()) {
        Iterator<AbsAppbrandPath> iterator = this.mCleanableList.iterator();
        while (true) {
          l1 = l2;
          if (iterator.hasNext()) {
            AbsAppbrandPath absAppbrandPath1 = iterator.next();
            if (absAppbrandPath1.isCleanable())
              l2 += absAppbrandPath1.clear(); 
            continue;
          } 
          break;
        } 
      } 
    } 
    AbsAppbrandPath absAppbrandPath = this.mRootPath;
    l2 = l1;
    if (absAppbrandPath != null) {
      l2 = l1;
      if (absAppbrandPath.isCleanable())
        l2 = l1 + this.mRootPath.clear(); 
    } 
    return l2;
  }
  
  public Map<String, Long> getCachePathAndSize() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    List<AbsAppbrandPath> list = this.mCleanableList;
    if (list != null && !list.isEmpty())
      for (AbsAppbrandPath absAppbrandPath : this.mCleanableList)
        hashMap.put(absAppbrandPath.getAbsolutePath(), Long.valueOf(absAppbrandPath.getTotalSize()));  
    return (Map)hashMap;
  }
  
  public long getCacheSize() {
    Iterator<Long> iterator = getCachePathAndSize().values().iterator();
    long l;
    for (l = 0L; iterator.hasNext(); l += ((Long)iterator.next()).longValue());
    return l;
  }
  
  public AbsAppbrandPath getDownloadPath() {
    return this.mLaunchCachePath;
  }
  
  public Map<String, Long> getPathAndSize() {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    AbsAppbrandPath absAppbrandPath = this.mRootPath;
    if (absAppbrandPath != null)
      hashMap.put(absAppbrandPath.getAbsolutePath(), Long.valueOf(this.mRootPath.getTotalSize())); 
    absAppbrandPath = this.mTempPath;
    if (absAppbrandPath != null)
      hashMap.put(absAppbrandPath.getAbsolutePath(), Long.valueOf(this.mTempPath.getTotalSize())); 
    absAppbrandPath = this.mUserPath;
    if (absAppbrandPath != null)
      hashMap.put(absAppbrandPath.getAbsolutePath(), Long.valueOf(this.mUserPath.getTotalSize())); 
    absAppbrandPath = this.mSpPath;
    if (absAppbrandPath != null)
      hashMap.put(absAppbrandPath.getAbsolutePath(), Long.valueOf(this.mSpPath.getTotalSize())); 
    return (Map)hashMap;
  }
  
  public AbsAppbrandPath getRootPath() {
    return this.mRootPath;
  }
  
  public AbsAppbrandPath getSpPath() {
    return this.mSpPath;
  }
  
  public AbsAppbrandPath getTempPath() {
    return this.mTempPath;
  }
  
  public long getTotalSize() {
    Iterator<Long> iterator = getPathAndSize().values().iterator();
    long l;
    for (l = 0L; iterator.hasNext(); l += ((Long)iterator.next()).longValue());
    return l;
  }
  
  public AbsAppbrandPath getUserPath() {
    return this.mUserPath;
  }
  
  static class Holder {
    public static AppbrandPathManager mInstance = new AppbrandPathManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\path\AppbrandPathManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */