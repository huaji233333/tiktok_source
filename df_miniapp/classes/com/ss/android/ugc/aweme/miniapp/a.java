package com.ss.android.ugc.aweme.miniapp;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.a.a;
import com.ss.android.ugc.aweme.miniapp.f.a.c;
import com.ss.android.ugc.aweme.miniapp_api.d;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import java.net.URLDecoder;
import org.json.JSONObject;

public final class a implements g {
  private String a;
  
  private Uri b;
  
  private static void a(String paramString, long paramLong, JSONObject paramJSONObject) {
    if (TextUtils.equals(paramString, "open_url")) {
      c.a("umeng", "embeded_ad", "open_url_microapp_h5", paramLong, 0L, paramJSONObject);
      return;
    } 
    c.a("umeng", "embeded_ad", "micro_app_h5", paramLong, 0L, paramJSONObject);
  }
  
  public final boolean a(Context paramContext, String paramString) {
    try {
      JSONObject jSONObject1 = new JSONObject(this.a);
      String str1 = jSONObject1.optString("web_url");
      String str3 = jSONObject1.optString("cid");
      String str4 = jSONObject1.optString("log_extra");
      String str2 = jSONObject1.optString("web_title");
      boolean bool = jSONObject1.optBoolean("is_half_page");
      String str5 = this.b.getQueryParameter("from");
      JSONObject jSONObject2 = new JSONObject();
      jSONObject2.put("log_extra", str4);
      jSONObject2.put("is_ad_event", 1);
      long l = Long.parseLong(str3);
      if (bool) {
        if (!TextUtils.isEmpty(str1))
          a(str5, l, jSONObject2); 
        HostProcessBridge.hostActionSync("live_ad_web_url", CrossProcessDataEntity.Builder.create().put("hostActionData", this.a).build());
        return true;
      } 
      if (!TextUtils.isEmpty(str4) && !TextUtils.isEmpty(str1)) {
        a(str5, l, jSONObject2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str1);
        stringBuilder.append("&launch_mode=standard");
        a.a(paramContext, stringBuilder.toString(), str2);
      } 
      return true;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getMessage());
      stringBuilder.append("schema is");
      stringBuilder.append(paramString);
      String str = stringBuilder.toString();
      MiniAppService.inst().getBaseLibDepend().a(new Exception(str));
      return false;
    } 
  }
  
  public final boolean a(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return false; 
    this.b = Uri.parse(paramString);
    String str = paramString;
    try {
      paramString = URLDecoder.decode(paramString, "UTF-8");
      str = paramString;
      boolean bool = d.c(paramString);
      if (bool) {
        str = paramString;
        String str1 = this.b.getQueryParameter("start_page");
        str = paramString;
        StringBuilder stringBuilder = new StringBuilder("start_page://");
        str = paramString;
        stringBuilder.append(str1);
        str = paramString;
        this.a = Uri.parse(stringBuilder.toString()).getQueryParameter("ad_params");
      } else {
        str = paramString;
        if (d.e(paramString)) {
          str = paramString;
          String str1 = this.b.getQueryParameter("query");
          str = paramString;
          if (!TextUtils.isEmpty(str1)) {
            str = paramString;
            this.a = (new JSONObject(str1)).optString("ad_params");
            str = paramString;
            this.a = URLDecoder.decode(this.a, "UTF-8");
          } 
        } 
      } 
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(exception.getMessage());
      stringBuilder.append("schema is");
      stringBuilder.append(str);
      String str1 = stringBuilder.toString();
      MiniAppService.inst().getBaseLibDepend().a(new Exception(str1));
    } 
    return !TextUtils.isEmpty(this.a);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */