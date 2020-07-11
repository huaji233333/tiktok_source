package com.facebook.react.common.network;

import okhttp3.e;
import okhttp3.y;

public class OkHttpCallUtil {
  public static void cancelTag(y paramy, Object paramObject) {
    for (e e : paramy.c.c()) {
      if (paramObject.equals((e.a()).e)) {
        e.c();
        return;
      } 
    } 
    for (e e : paramy.c.d()) {
      if (paramObject.equals((e.a()).e)) {
        e.c();
        break;
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\common\network\OkHttpCallUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */