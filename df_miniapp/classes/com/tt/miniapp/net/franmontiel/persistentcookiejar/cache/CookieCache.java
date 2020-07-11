package com.tt.miniapp.net.franmontiel.persistentcookiejar.cache;

import java.util.Collection;
import okhttp3.l;

public interface CookieCache extends Iterable<l> {
  void addAll(Collection<l> paramCollection);
  
  void clear();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\franmontiel\persistentcookiejar\cache\CookieCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */