package com.tt.miniapp.settings.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsDAO {
  private static JSONObject sSettingsJSONObject;
  
  private static JSONObject sVidJSONObject;
  
  public static boolean getBoolean(Context paramContext, boolean paramBoolean, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject != null) {
      if (paramVarArgs.length <= 0)
        return paramBoolean; 
      ABTestDAO.getInstance().markExposed(paramVarArgs[0].toString());
      return jSONObject.optBoolean(paramVarArgs[paramVarArgs.length - 1].toString(), paramBoolean);
    } 
    return paramBoolean;
  }
  
  public static String getCtxInfo(Context paramContext, String paramString) {
    return getSettingsConfigSP(paramContext).getString("ctx_info", paramString);
  }
  
  public static double getDouble(Context paramContext, double paramDouble, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject != null) {
      if (paramVarArgs.length <= 0)
        return paramDouble; 
      ABTestDAO.getInstance().markExposed(paramVarArgs[0].toString());
      return jSONObject.optDouble(paramVarArgs[paramVarArgs.length - 1].toString(), paramDouble);
    } 
    return paramDouble;
  }
  
  public static int getInt(Context paramContext, int paramInt, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject != null) {
      if (paramVarArgs.length <= 0)
        return paramInt; 
      ABTestDAO.getInstance().markExposed(paramVarArgs[0].toString());
      return jSONObject.optInt(paramVarArgs[paramVarArgs.length - 1].toString(), paramInt);
    } 
    return paramInt;
  }
  
  public static JSONObject getJSONObject(Context paramContext, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject == null || paramVarArgs.length <= 0)
      return new JSONObject(); 
    ABTestDAO.getInstance().markExposed(paramVarArgs[0].toString());
    return jSONObject.optJSONObject(paramVarArgs[paramVarArgs.length - 1].toString());
  }
  
  private static JSONObject getLastJSONObject(Context paramContext, Enum... paramVarArgs) {
    JSONObject jSONObject = getSettingsJSONObject(paramContext);
    if (jSONObject != null) {
      if (paramVarArgs.length <= 0)
        return null; 
      int j = paramVarArgs.length;
      for (int i = 0; i < j - 1; i++) {
        if (jSONObject == null)
          return null; 
        jSONObject = jSONObject.optJSONObject(paramVarArgs[i].toString());
      } 
      return jSONObject;
    } 
    return null;
  }
  
  public static List<String> getListString(Context paramContext, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject == null || paramVarArgs.length <= 0)
      return new ArrayList<String>(); 
    ABTestDAO aBTestDAO = ABTestDAO.getInstance();
    int i = 0;
    aBTestDAO.markExposed(paramVarArgs[0].toString());
    JSONArray jSONArray = jSONObject.optJSONArray(paramVarArgs[paramVarArgs.length - 1].toString());
    ArrayList<String> arrayList = new ArrayList(jSONArray.length());
    while (i < jSONArray.length()) {
      arrayList.add(jSONArray.optString(i));
      i++;
    } 
    return arrayList;
  }
  
  public static long getLong(Context paramContext, long paramLong, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject != null) {
      if (paramVarArgs.length <= 0)
        return paramLong; 
      ABTestDAO.getInstance().markExposed(paramVarArgs[0].toString());
      return jSONObject.optLong(paramVarArgs[paramVarArgs.length - 1].toString(), paramLong);
    } 
    return paramLong;
  }
  
  public static Set<String> getSetString(Context paramContext, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject == null || paramVarArgs.length <= 0)
      return new HashSet<String>(); 
    ABTestDAO aBTestDAO = ABTestDAO.getInstance();
    int i = 0;
    aBTestDAO.markExposed(paramVarArgs[0].toString());
    JSONArray jSONArray = jSONObject.optJSONArray(paramVarArgs[paramVarArgs.length - 1].toString());
    HashSet<String> hashSet = new HashSet();
    while (i < jSONArray.length()) {
      hashSet.add(jSONArray.optString(i));
      i++;
    } 
    return hashSet;
  }
  
  private static SharedPreferences getSettingsConfigSP(Context paramContext) {
    return KVUtil.getSharedPreferences(paramContext, "settings_config");
  }
  
  public static JSONObject getSettingsJSONObject(Context paramContext) {
    if (sSettingsJSONObject == null && getSettingsConfigSP(paramContext).contains("SETTINGS_JSON")) {
      String str = getSettingsConfigSP(paramContext).getString("SETTINGS_JSON", "");
      if (TextUtils.isEmpty(str))
        return null; 
      try {
        return new JSONObject(str);
      } catch (JSONException jSONException) {
        AppBrandLogger.e("SettingsDAO", new Object[] { jSONException });
        return null;
      } 
    } 
    return sSettingsJSONObject;
  }
  
  public static String getString(Context paramContext, String paramString, Enum... paramVarArgs) {
    JSONObject jSONObject = getLastJSONObject(paramContext, paramVarArgs);
    if (jSONObject != null) {
      if (paramVarArgs.length <= 0)
        return paramString; 
      ABTestDAO.getInstance().markExposed(paramVarArgs[0].toString());
      return jSONObject.optString(paramVarArgs[paramVarArgs.length - 1].toString(), paramString);
    } 
    return paramString;
  }
  
  static JSONObject getVidInfo(Context paramContext) {
    if (paramContext == null)
      return null; 
    String str = getSettingsConfigSP(paramContext).getString("vid_info", "");
    try {
      if (!TextUtils.isEmpty(str))
        sVidJSONObject = new JSONObject(str); 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("SettingsDAO", new Object[] { jSONException });
    } 
    return sVidJSONObject;
  }
  
  public static boolean isSettingsDAOReady(Context paramContext) {
    return !TextUtils.isEmpty(getSettingsConfigSP(paramContext).getString("SETTINGS_JSON", ""));
  }
  
  static void setCtxInfo(Context paramContext, String paramString) {
    getSettingsConfigSP(paramContext).edit().putString("ctx_info", paramString).apply();
  }
  
  public static void setSettingsJSONObject(Context paramContext, JSONObject paramJSONObject) {
    sSettingsJSONObject = paramJSONObject;
    getSettingsConfigSP(paramContext).edit().putString("SETTINGS_JSON", sSettingsJSONObject.toString()).apply();
    AppBrandLogger.d("SettingsDAO", new Object[] { sSettingsJSONObject });
  }
  
  public static void setVidInfo(Context paramContext, JSONObject paramJSONObject) {
    sVidJSONObject = paramJSONObject;
    getSettingsConfigSP(paramContext).edit().putString("vid_info", paramJSONObject.toString()).apply();
    AppBrandLogger.d("SettingsDAO", new Object[] { sVidJSONObject });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\data\SettingsDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */