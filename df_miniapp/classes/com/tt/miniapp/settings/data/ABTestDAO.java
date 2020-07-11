package com.tt.miniapp.settings.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.appbrand.mmkv.MMKV;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.host.HostDependManager;
import org.json.JSONObject;

public class ABTestDAO {
  private ABTestDAO() {}
  
  private SharedPreferences getExposedVidSP(Context paramContext) {
    return KVUtil.getSharedPreferences(paramContext, "appbrand_exposed_vid_list");
  }
  
  public static ABTestDAO getInstance() {
    return HOLDER.INSTANCE;
  }
  
  public static void registerVidUploader(final Context context) {
    IUploadVids iUploadVids = HostDependManager.getInst().uploadVid();
    if (iUploadVids == null)
      return; 
    iUploadVids.uploadAppbrandVidList(new UploadVidCallback() {
          public final String getAppBrandVids(String param1String) {
            return ABTestDAO.getInstance().getExposedVids(context, param1String);
          }
        });
  }
  
  public String getExposedVids(Context paramContext, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    if (!TextUtils.isEmpty(paramString))
      stringBuilder.append(paramString); 
    SharedPreferences sharedPreferences = getExposedVidSP(paramContext);
    if (sharedPreferences instanceof MMKV) {
      MMKV mMKV = (MMKV)sharedPreferences;
      try {
        if (mMKV.allKeysMMKV() == null)
          return stringBuilder.toString(); 
        String[] arrayOfString = mMKV.allKeysMMKV();
        int j = arrayOfString.length;
        for (int i = 0; i < j; i++) {
          String str = mMKV.getString(arrayOfString[i], "");
          if (stringBuilder.length() > 1)
            stringBuilder.append(","); 
          stringBuilder.append(str);
        } 
      } catch (Exception exception) {}
    } else {
      for (String paramString : exception.getAll().values()) {
        if (stringBuilder.length() > 1)
          stringBuilder.append(","); 
        stringBuilder.append(paramString);
      } 
    } 
    return stringBuilder.toString();
  }
  
  void markExposed(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   5: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   8: astore_2
    //   9: aload_2
    //   10: invokestatic getVidInfo : (Landroid/content/Context;)Lorg/json/JSONObject;
    //   13: astore_3
    //   14: aload_3
    //   15: ifnonnull -> 21
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: aload_3
    //   22: aload_1
    //   23: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   26: astore_3
    //   27: aload_3
    //   28: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   31: ifne -> 56
    //   34: aload_0
    //   35: aload_2
    //   36: invokespecial getExposedVidSP : (Landroid/content/Context;)Landroid/content/SharedPreferences;
    //   39: invokeinterface edit : ()Landroid/content/SharedPreferences$Editor;
    //   44: aload_1
    //   45: aload_3
    //   46: invokeinterface putString : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;
    //   51: invokeinterface apply : ()V
    //   56: aload_0
    //   57: monitorexit
    //   58: return
    //   59: astore_1
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_1
    //   63: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	59	finally
    //   21	56	59	finally
  }
  
  void updateVidInfo(Context paramContext, JSONObject paramJSONObject) {
    SettingsDAO.setVidInfo(paramContext, paramJSONObject);
    SharedPreferences sharedPreferences = getExposedVidSP(paramContext);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    if (sharedPreferences instanceof MMKV) {
      MMKV mMKV = (MMKV)sharedPreferences;
      if (mMKV.allKeysMMKV() == null)
        return; 
      for (String str : mMKV.allKeysMMKV()) {
        if (!paramJSONObject.has(str))
          editor.remove(str); 
      } 
    } else {
      if (sharedPreferences.getAll() == null)
        return; 
      for (String str : sharedPreferences.getAll().keySet()) {
        if (!paramJSONObject.has(str))
          editor.remove(str); 
      } 
    } 
    editor.apply();
  }
  
  static class HOLDER {
    public static ABTestDAO INSTANCE = new ABTestDAO();
  }
  
  public static interface IUploadVids {
    void uploadAppbrandVidList(UploadVidCallback param1UploadVidCallback);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\data\ABTestDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */