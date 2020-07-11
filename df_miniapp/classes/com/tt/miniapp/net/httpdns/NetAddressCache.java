package com.tt.miniapp.net.httpdns;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class NetAddressCache {
  private ConcurrentHashMap<String, CacheEntry> mCache = new ConcurrentHashMap<String, CacheEntry>();
  
  private NetAddressCache() {}
  
  public static NetAddressCache getIns() {
    return Holder.instance;
  }
  
  public List<InetAddress> get(String paramString) {
    CacheEntry cacheEntry = this.mCache.get(paramString);
    return (cacheEntry != null) ? cacheEntry.mAddresses : null;
  }
  
  public boolean isExist(String paramString) {
    return this.mCache.containsKey(paramString);
  }
  
  public void put(String paramString, List<InetAddress> paramList) {
    CacheEntry cacheEntry = new CacheEntry(paramList, -1L);
    this.mCache.put(paramString, cacheEntry);
  }
  
  static final class CacheEntry {
    List<InetAddress> mAddresses;
    
    long mExpiration;
    
    CacheEntry(List<InetAddress> param1List, long param1Long) {
      this.mAddresses = param1List;
      this.mExpiration = param1Long;
    }
  }
  
  static class Holder {
    public static NetAddressCache instance = new NetAddressCache();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\httpdns\NetAddressCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */