package com.tt.miniapp.navigate2;

import android.text.TextUtils;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.JsonBuilder;
import org.json.JSONObject;

public class Nav2Util {
  private static JSONObject buildReferer(AppInfoEntity paramAppInfoEntity) {
    String str;
    if (paramAppInfoEntity == null) {
      paramAppInfoEntity = null;
    } else {
      str = paramAppInfoEntity.refererInfo;
    } 
    return (new JsonBuilder(str)).build();
  }
  
  public static JSONObject getReferer(AppInfoEntity paramAppInfoEntity) {
    JSONObject jSONObject = buildReferer(paramAppInfoEntity);
    if (jSONObject.has("__origin_wg_or_app"))
      jSONObject.remove("__origin_wg_or_app"); 
    return jSONObject;
  }
  
  public static void initRefererInfo(AppInfoEntity paramAppInfoEntity, String paramString) {
    boolean bool = TextUtils.equals(paramAppInfoEntity.launchFrom, "in_mp");
    JSONObject jSONObject = null;
    if (!bool && !TextUtils.equals(paramAppInfoEntity.launchFrom, "back_mp")) {
      paramAppInfoEntity.refererInfo = null;
      return;
    } 
    paramAppInfoEntity.refererInfo = paramString;
    if (!TextUtils.isEmpty(paramString)) {
      String str1;
      JSONObject jSONObject1 = (new JsonBuilder(paramString)).build();
      String str2 = jSONObject1.optString("appId");
      if (!TextUtils.isEmpty(str2))
        paramAppInfoEntity.bizLocation = str2; 
      JSONObject jSONObject2 = jSONObject1.optJSONObject("extraData");
      jSONObject1 = jSONObject;
      if (jSONObject2 != null)
        str1 = jSONObject2.optString("location"); 
      if (!TextUtils.isEmpty(str1))
        paramAppInfoEntity.location = str1; 
    } 
  }
  
  public static boolean isOriginWhiteGame(AppInfoEntity paramAppInfoEntity) {
    return (paramAppInfoEntity == null) ? false : buildReferer(paramAppInfoEntity).optBoolean("__origin_wg_or_app", false);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\navigate2\Nav2Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */