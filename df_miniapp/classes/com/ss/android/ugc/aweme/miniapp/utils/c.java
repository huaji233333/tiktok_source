package com.ss.android.ugc.aweme.miniapp.utils;

import com.google.gson.f;

public final class c {
  private static f a;
  
  private static f a() {
    if (a == null)
      a = new f(); 
    return a;
  }
  
  public static <T> T a(String paramString, Class<T> paramClass) {
    return (T)a().a(paramString, paramClass);
  }
  
  public static String a(Object paramObject) {
    return a().b(paramObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniap\\utils\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */