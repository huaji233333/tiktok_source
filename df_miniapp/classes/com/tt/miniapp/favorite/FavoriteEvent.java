package com.tt.miniapp.favorite;

import com.tt.miniapp.event.Event;

public class FavoriteEvent {
  public static void onGuideClick(long paramLong) {
    Event.builder("mp_collect_guide_click").kv("duration", Long.valueOf(paramLong)).flush();
  }
  
  public static void onGuideClickResult(boolean paramBoolean) {
    String str;
    Event.Builder builder = Event.builder("mp_collect_guide_click_result");
    if (paramBoolean) {
      str = "success";
    } else {
      str = "fail";
    } 
    builder.kv("result_type", str).flush();
  }
  
  public static void onGuideClose(boolean paramBoolean1, boolean paramBoolean2, long paramLong, String paramString) {
    String str;
    Event.Builder builder = Event.builder("mp_collect_guide_close");
    if (paramBoolean1) {
      str = "user";
    } else {
      str = "system";
    } 
    builder = builder.kv("closed_by", str);
    if (paramBoolean2) {
      str = "bubble";
    } else {
      str = "float";
    } 
    builder.kv("closed_at", str).kv("duration", Long.valueOf(paramLong)).kv("title", paramString).flush();
  }
  
  public static void onGuideShow(boolean paramBoolean, String paramString) {
    String str;
    Event.Builder builder = Event.builder("mp_collect_guide_show");
    if (paramBoolean) {
      str = "bubble";
    } else {
      str = "float";
    } 
    builder.kv("type", str).kv("title", paramString).flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\favorite\FavoriteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */