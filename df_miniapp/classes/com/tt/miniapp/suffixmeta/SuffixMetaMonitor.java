package com.tt.miniapp.suffixmeta;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import org.json.JSONException;
import org.json.JSONObject;

class SuffixMetaMonitor {
  private static void monitor(int paramInt, JSONObject paramJSONObject) {
    try {
      AppBrandMonitor.statusRate("mp_suffix_meta", paramInt, paramJSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("SuffixMetaMonitor", new Object[] { exception });
      return;
    } 
  }
  
  static void removeCachePropertyFail(String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errorMsg", paramString1);
      jSONObject.put("propertyName", paramString2);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SuffixMetaMonitor", new Object[] { jSONException });
    } 
    monitor(1002, jSONObject);
  }
  
  static void requestSuffixMetaFail(String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errorMsg", paramString);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SuffixMetaMonitor", new Object[] { jSONException });
    } 
    monitor(1000, jSONObject);
  }
  
  static void saveSuffixMetaFail(String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errorMsg", paramString1);
      jSONObject.put("originData", paramString2);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SuffixMetaMonitor", new Object[] { jSONException });
    } 
    monitor(1001, jSONObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\suffixmeta\SuffixMetaMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */