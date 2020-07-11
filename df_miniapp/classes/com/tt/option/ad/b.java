package com.tt.option.ad;

import com.tt.miniapp.event.Event;

public final class b {
  public static void a(String paramString1, String paramString2, int paramInt, String paramString3) {
    Event.Builder builder2 = Event.builder("mp_app_ad_create_result");
    if (paramString1 == null)
      paramString1 = ""; 
    Event.Builder builder1 = builder2.kv("ad_type", paramString1);
    if (paramString2 == null)
      paramString2 = ""; 
    builder1 = builder1.kv("ad_unit_id", paramString2).kv("result_type", "fail").kv("err_code", Integer.valueOf(paramInt));
    if (paramString3 == null)
      paramString3 = ""; 
    builder1.kv("err_msg", paramString3).flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\ad\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */