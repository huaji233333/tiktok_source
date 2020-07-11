package com.tt.miniapp.business.frontendapihandle.handler.subscribe.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.data.TemplateMsgInfo;
import com.tt.miniapp.business.frontendapihandle.handler.subscribe.data.TemplateMsgLimitInfo;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.JsonBuilder;

public class TemplateMsgCacheUtil {
  public static TemplateMsgInfo getSavedTemplateMsgInfo(String paramString) {
    String str = getSharedPreference().getString(getTemplateKey(paramString), "");
    return TextUtils.isEmpty(str) ? null : new TemplateMsgInfo(paramString, (new JsonBuilder(str)).build());
  }
  
  public static TemplateMsgLimitInfo getSavedTotalLimit() {
    String str = getSharedPreference().getString(getTemplateKey("total_limit"), "");
    return (TextUtils.isEmpty(str) || TextUtils.equals("{}", str)) ? null : new TemplateMsgLimitInfo((new JsonBuilder(str)).build());
  }
  
  private static SharedPreferences getSharedPreference() {
    return KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "Subscribe_Message");
  }
  
  private static String getTemplateKey(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("template_");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static void saveTemplateMsgInfo(TemplateMsgInfo paramTemplateMsgInfo) {
    if (paramTemplateMsgInfo == null)
      return; 
    getSharedPreference().edit().putString(getTemplateKey(paramTemplateMsgInfo.getId()), paramTemplateMsgInfo.toString()).apply();
  }
  
  public static void saveTotalLimit(TemplateMsgLimitInfo paramTemplateMsgLimitInfo) {
    if (paramTemplateMsgLimitInfo == null)
      return; 
    getSharedPreference().edit().putString(getTemplateKey("total_limit"), paramTemplateMsgLimitInfo.toString()).apply();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\subscrib\\util\TemplateMsgCacheUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */