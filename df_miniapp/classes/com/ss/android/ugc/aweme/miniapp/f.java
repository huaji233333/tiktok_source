package com.ss.android.ugc.aweme.miniapp;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

public final class f {
  public Map<String, String> a = new HashMap<String, String>();
  
  public static f a() {
    return new f();
  }
  
  private f a(String paramString1, String paramString2, a parama) {
    this.a.put(paramString1, parama.a(paramString2));
    return this;
  }
  
  public final f a(String paramString1, String paramString2) {
    return a(paramString1, paramString2, a.a);
  }
  
  public static interface a {
    public static final a a = new a() {
        public final String a(String param2String) {
          return (TextUtils.isEmpty(param2String) || "null".equals(param2String)) ? "" : param2String;
        }
      };
    
    public static final a b = new a() {
        public final String a(String param2String) {
          return (TextUtils.isEmpty(param2String) || "null".equals(param2String) || "0".equals(param2String)) ? "" : param2String;
        }
      };
    
    String a(String param1String);
  }
  
  static final class null implements a {
    public final String a(String param1String) {
      return (TextUtils.isEmpty(param1String) || "null".equals(param1String)) ? "" : param1String;
    }
  }
  
  static final class null implements a {
    public final String a(String param1String) {
      return (TextUtils.isEmpty(param1String) || "null".equals(param1String) || "0".equals(param1String)) ? "" : param1String;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */