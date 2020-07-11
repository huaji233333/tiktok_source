package com.tt.miniapp.suffixmeta;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import org.json.JSONException;
import org.json.JSONObject;

class SuffixMetaStorage {
  static String getLocalCache(Context paramContext, String paramString) {
    SharedPreferences sharedPreferences = KVUtil.getSharedPreferences(paramContext, "appbrand_suffix_meta");
    if (sharedPreferences != null)
      try {
        return sharedPreferences.getString(paramString, "");
      } catch (Exception exception) {
        AppBrandLogger.e("SuffixMetaStorage", new Object[] { exception });
      }  
    return "";
  }
  
  static void removeOldVersionFile(Context paramContext, String paramString) {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(HostDependManager.getInst().getSpPrefixPath());
      stringBuilder.append("tma_block_page");
      stringBuilder.append(paramString);
      return;
    } finally {
      paramContext = null;
      AppBrandLogger.e("SuffixMetaStorage", new Object[] { paramContext });
    } 
  }
  
  static void removeProperty(Context paramContext, String paramString1, String paramString2) {
    try {
      SharedPreferences sharedPreferences = KVUtil.getSharedPreferences(paramContext, "appbrand_suffix_meta");
      JSONObject jSONObject = new JSONObject(sharedPreferences.getString(paramString1, ""));
      jSONObject.remove(paramString2);
      sharedPreferences.edit().putString(paramString1, jSONObject.toString()).apply();
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SuffixMetaStorage", new Object[] { jSONException });
      SuffixMetaMonitor.removeCachePropertyFail(Log.getStackTraceString((Throwable)jSONException), paramString2);
      return;
    } 
  }
  
  static void saveSuffixMeta(Context paramContext, String paramString1, String paramString2) {
    try {
      SharedPreferences sharedPreferences = KVUtil.getSharedPreferences(paramContext, "appbrand_suffix_meta");
      if (sharedPreferences != null)
        sharedPreferences.edit().putString(paramString1, paramString2).apply(); 
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("SuffixMetaStorage", new Object[] { exception });
      SuffixMetaMonitor.saveSuffixMetaFail(Log.getStackTraceString(exception), paramString2);
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\suffixmeta\SuffixMetaStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */