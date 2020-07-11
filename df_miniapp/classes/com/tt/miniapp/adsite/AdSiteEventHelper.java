package com.tt.miniapp.adsite;

import com.tt.miniapp.event.Event;

public class AdSiteEventHelper {
  public static void reportGetLocalPhoneNumberEvent(boolean paramBoolean) {
    String str;
    Event.Builder builder2 = Event.builder("mp_get_local_phone_num_result");
    if (paramBoolean) {
      str = "success";
    } else {
      str = "fail";
    } 
    Event.Builder builder1 = builder2.kv("result", str);
    if (!paramBoolean)
      builder1.kv("err_msg", "can't complete this operation").kv("err_code", Integer.valueOf(4)); 
    builder1.flush();
  }
  
  public static void reportGetLocalPhoneNumberTokenEvent(boolean paramBoolean) {
    String str;
    Event.Builder builder2 = Event.builder("mp_get_local_phone_num_token_result");
    if (paramBoolean) {
      str = "success";
    } else {
      str = "fail";
    } 
    Event.Builder builder1 = builder2.kv("result", str);
    if (!paramBoolean)
      builder1.kv("err_msg", "can't complete this operation").kv("err_code", Integer.valueOf(4)); 
    builder1.flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\adsite\AdSiteEventHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */