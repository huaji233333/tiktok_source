package com.ss.android.ugc.aweme.miniapp.a;

import android.content.Context;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.b.a.b;
import com.ss.android.ugc.aweme.miniapp_api.b.a.c;
import com.ss.android.ugc.aweme.miniapp_api.c;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
  public static final String a = a.class.getSimpleName();
  
  public static final String b;
  
  static {
    StringBuilder stringBuilder = new StringBuilder("snssdk");
    stringBuilder.append(MiniAppService.inst().getAid());
    stringBuilder.append("://mini_app");
    b = stringBuilder.toString();
  }
  
  private static JSONObject a(Context paramContext, String paramString) {
    try {
      JSONObject jSONObject = new JSONObject();
      try {
        if (!TextUtils.isEmpty(paramString))
          jSONObject.put("log_extra", paramString); 
        jSONObject.put("is_ad_event", "1");
        paramString = MiniAppService.inst().getNetWorkDepend().a(paramContext);
        JSONObject jSONObject1 = jSONObject;
        if (!TextUtils.isEmpty(paramString)) {
          jSONObject.put("nt", paramString);
          return jSONObject;
        } 
        return jSONObject1;
      } catch (JSONException null) {
        return jSONObject;
      } 
    } catch (JSONException jSONException) {
      jSONException = null;
    } 
    return (JSONObject)jSONException;
  }
  
  public static void a(Context paramContext, String paramString1, long paramLong, String paramString2) {
    if (paramContext == null)
      return; 
    MiniAppService.inst().getMonitorDepend().a(paramContext, "umeng", "landing_ad", paramString1, paramLong, 0L, a(paramContext, paramString2));
  }
  
  public static void a(Context paramContext, String paramString1, String paramString2) {
    if (paramContext == null)
      return; 
    MiniAppService.inst().getRouterDepend().b(paramContext, paramString1, paramString2);
  }
  
  public static interface a {
    void a(boolean param1Boolean);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */