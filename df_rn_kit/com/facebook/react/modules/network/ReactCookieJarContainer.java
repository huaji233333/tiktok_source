package com.facebook.react.modules.network;

import java.util.Collections;
import java.util.List;
import okhttp3.l;
import okhttp3.m;
import okhttp3.t;

public class ReactCookieJarContainer implements CookieJarContainer {
  private m cookieJar;
  
  public List<l> loadForRequest(t paramt) {
    m m1 = this.cookieJar;
    return (m1 != null) ? m1.loadForRequest(paramt) : Collections.emptyList();
  }
  
  public void removeCookieJar() {
    this.cookieJar = null;
  }
  
  public void saveFromResponse(t paramt, List<l> paramList) {
    m m1 = this.cookieJar;
    if (m1 != null)
      m1.saveFromResponse(paramt, paramList); 
  }
  
  public void setCookieJar(m paramm) {
    this.cookieJar = paramm;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\network\ReactCookieJarContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */