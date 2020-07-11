package com.tt.miniapp.service.hostevent;

import android.text.TextUtils;

public class HostEventUtils {
  public static String checkAndModifyEventNName(String paramString) {
    String str = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      str = paramString;
      if (!paramString.startsWith("host_event_")) {
        StringBuilder stringBuilder = new StringBuilder("host_event_");
        stringBuilder.append(paramString);
        str = stringBuilder.toString();
      } 
    } 
    return str;
  }
  
  public static String trimHostEventPrefix(String paramString) {
    String str = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      str = paramString;
      if (paramString.startsWith("host_event_"))
        str = paramString.replaceFirst("host_event_", ""); 
    } 
    return str;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\hostevent\HostEventUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */