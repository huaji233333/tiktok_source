package com.tt.miniapp.shortcut;

import com.tt.miniapp.event.Event;
import com.tt.miniapphost.AppBrandLogger;

public class ShortcutEventReporter {
  public static void reportClick(String paramString) {
    AppBrandLogger.d("ShortcutEventReporter", new Object[] { "mp_add_desktop_icon_click", "params", paramString });
    Event.builder("mp_add_desktop_icon_click").kv("trigger_by", paramString).flush();
  }
  
  public static void reportDialogOption(String paramString) {
    AppBrandLogger.d("ShortcutEventReporter", new Object[] { "mp_add_desktop_icon_select_option" });
    Event.builder("mp_add_desktop_icon_select_option").kv("select_option", paramString).flush();
  }
  
  public static void reportDialogShow() {
    AppBrandLogger.d("ShortcutEventReporter", new Object[] { "mp_add_desktop_icon_pop_up" });
    Event.builder("mp_add_desktop_icon_pop_up").flush();
  }
  
  public static void reportLearnMore() {
    AppBrandLogger.d("ShortcutEventReporter", new Object[] { "mp_add_desktop_icon_learn_more_show" });
    Event.builder("mp_add_desktop_icon_learn_more_show").flush();
  }
  
  public static void reportResult(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(":");
    AppBrandLogger.d("ShortcutEventReporter", new Object[] { "mp_add_desktop_icon_click_result", "params", stringBuilder.toString(), paramString2 });
    Event.builder("mp_add_desktop_icon_click_result").kv("add_icon_success", paramString1).kv("error_msg", paramString2).flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\ShortcutEventReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */