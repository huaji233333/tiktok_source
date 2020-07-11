package com.tt.miniapp.net.franmontiel.persistentcookiejar.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import okhttp3.l;

class IdentifiableCookie {
  private l cookie;
  
  IdentifiableCookie(l paraml) {
    this.cookie = paraml;
  }
  
  static List<IdentifiableCookie> decorateAll(Collection<l> paramCollection) {
    ArrayList<IdentifiableCookie> arrayList = new ArrayList(paramCollection.size());
    Iterator<l> iterator = paramCollection.iterator();
    while (iterator.hasNext())
      arrayList.add(new IdentifiableCookie(iterator.next())); 
    return arrayList;
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof IdentifiableCookie))
      return false; 
    paramObject = paramObject;
    return (((IdentifiableCookie)paramObject).cookie.a.equals(this.cookie.a) && ((IdentifiableCookie)paramObject).cookie.d.equals(this.cookie.d) && ((IdentifiableCookie)paramObject).cookie.e.equals(this.cookie.e) && ((IdentifiableCookie)paramObject).cookie.f == this.cookie.f && ((IdentifiableCookie)paramObject).cookie.i == this.cookie.i);
  }
  
  l getCookie() {
    return this.cookie;
  }
  
  public int hashCode() {
    return ((((this.cookie.a.hashCode() + 527) * 31 + this.cookie.d.hashCode()) * 31 + this.cookie.e.hashCode()) * 31 + (this.cookie.f ^ true)) * 31 + (this.cookie.i ^ true);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\franmontiel\persistentcookiejar\cache\IdentifiableCookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */