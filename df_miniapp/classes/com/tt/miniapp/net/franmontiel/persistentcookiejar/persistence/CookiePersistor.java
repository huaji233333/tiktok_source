package com.tt.miniapp.net.franmontiel.persistentcookiejar.persistence;

import java.util.Collection;
import java.util.List;
import okhttp3.l;

public interface CookiePersistor {
  void clear();
  
  List<l> loadAll();
  
  void removeAll(Collection<l> paramCollection);
  
  void saveAll(Collection<l> paramCollection);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\franmontiel\persistentcookiejar\persistence\CookiePersistor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */