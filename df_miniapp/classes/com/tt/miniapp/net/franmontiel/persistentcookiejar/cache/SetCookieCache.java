package com.tt.miniapp.net.franmontiel.persistentcookiejar.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import okhttp3.l;

public class SetCookieCache implements CookieCache {
  public Set<IdentifiableCookie> cookies = new HashSet<IdentifiableCookie>();
  
  public void addAll(Collection<l> paramCollection) {
    if (paramCollection != null) {
      Iterator<l> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        IdentifiableCookie identifiableCookie = new IdentifiableCookie(iterator.next());
        this.cookies.remove(identifiableCookie);
        this.cookies.add(identifiableCookie);
      } 
    } 
  }
  
  public void clear() {
    this.cookies.clear();
  }
  
  public Iterator<l> iterator() {
    return new SetCookieCacheIterator();
  }
  
  class SetCookieCacheIterator implements Iterator<l> {
    private Iterator<IdentifiableCookie> iterator = SetCookieCache.this.cookies.iterator();
    
    public boolean hasNext() {
      return this.iterator.hasNext();
    }
    
    public l next() {
      return ((IdentifiableCookie)this.iterator.next()).getCookie();
    }
    
    public void remove() {
      this.iterator.remove();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\franmontiel\persistentcookiejar\cache\SetCookieCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */