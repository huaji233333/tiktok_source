package com.tt.miniapp.service.codecache;

import android.content.Context;
import android.text.TextUtils;
import com.he.SettingsProvider;
import com.he.loader.TTAppCompiler;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.ttapkgdecoder.reader.TTAPkgReader;
import com.tt.miniapp.ttapkgdecoder.source.ISource;
import com.tt.miniapp.ttapkgdecoder.source.MappedByteBufferDiskSource;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CodeCacheServiceImpl implements SettingsProvider, TTAppCompiler.Callback, CodeCacheService {
  public volatile TTAppCompiler sCompiler;
  
  private boolean checkTypeRule(AppInfoEntity paramAppInfoEntity, JSONObject paramJSONObject) {
    if (paramJSONObject != null && paramAppInfoEntity != null) {
      boolean bool = paramAppInfoEntity.isGame();
      String str = paramJSONObject.optString("type");
      if (!TextUtils.isEmpty(str)) {
        byte b = -1;
        int i = str.hashCode();
        if (i != -94837210) {
          if (i != 96673) {
            if (i == 1355178125 && str.equals("micro_game"))
              b = 2; 
          } else if (str.equals("all")) {
            b = 0;
          } 
        } else if (str.equals("micro_app")) {
          b = 1;
        } 
        return (b != 0) ? ((b != 1) ? ((b != 2) ? false : bool) : (!bool)) : true;
      } 
    } 
    return false;
  }
  
  private TTAppCompiler getCompiler() {
    // Byte code:
    //   0: aload_0
    //   1: getfield sCompiler : Lcom/he/loader/TTAppCompiler;
    //   4: ifnonnull -> 73
    //   7: ldc com/tt/miniapp/service/codecache/CodeCacheServiceImpl
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield sCompiler : Lcom/he/loader/TTAppCompiler;
    //   14: ifnonnull -> 61
    //   17: aload_0
    //   18: new com/he/loader/TTAppCompiler
    //   21: dup
    //   22: aload_0
    //   23: invokespecial <init> : (Lcom/he/loader/TTAppCompiler$Callback;)V
    //   26: putfield sCompiler : Lcom/he/loader/TTAppCompiler;
    //   29: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   32: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   35: astore_1
    //   36: aload_0
    //   37: getfield sCompiler : Lcom/he/loader/TTAppCompiler;
    //   40: aload_1
    //   41: aload_0
    //   42: invokevirtual setup : (Landroid/content/ContextWrapper;Lcom/he/SettingsProvider;)V
    //   45: new com/tt/miniapp/service/codecache/CodeCacheServiceImpl$1
    //   48: dup
    //   49: aload_0
    //   50: invokespecial <init> : (Lcom/tt/miniapp/service/codecache/CodeCacheServiceImpl;)V
    //   53: ldc 'tma-codecache'
    //   55: invokestatic getThread : (Ljava/lang/Runnable;Ljava/lang/String;)Ljava/lang/Thread;
    //   58: invokevirtual start : ()V
    //   61: ldc com/tt/miniapp/service/codecache/CodeCacheServiceImpl
    //   63: monitorexit
    //   64: goto -> 73
    //   67: astore_1
    //   68: ldc com/tt/miniapp/service/codecache/CodeCacheServiceImpl
    //   70: monitorexit
    //   71: aload_1
    //   72: athrow
    //   73: aload_0
    //   74: getfield sCompiler : Lcom/he/loader/TTAppCompiler;
    //   77: areturn
    // Exception table:
    //   from	to	target	type
    //   10	61	67	finally
    //   61	64	67	finally
    //   68	71	67	finally
  }
  
  public void codeCacheMiniAppPkg(AppInfoEntity paramAppInfoEntity, File paramFile) {
    AppBrandLogger.d("CodeCacheServiceImpl", new Object[] { "pre caching", paramFile, Boolean.valueOf(paramFile.exists()), Boolean.valueOf(isCodeCacheEnabled(paramAppInfoEntity)) });
    if (isCodeCacheEnabled(paramAppInfoEntity) && paramFile.exists()) {
      AppBrandLogger.d("CodeCacheServiceImpl", new Object[] { "caching", paramFile, Boolean.valueOf(paramFile.exists()) });
      null = new MappedByteBufferDiskSource(paramFile);
      try {
        TTAPkgReader tTAPkgReader = new TTAPkgReader((ISource)null);
      } finally {
        null = null;
      } 
      try {
        AppBrandLogger.e("CodeCacheServiceImpl", new Object[] { null });
      } finally {
        if (exception != null)
          try {
            exception.release();
          } catch (Exception exception1) {} 
      } 
    } 
  }
  
  public int getSetting(Context paramContext, Enum<?> paramEnum, int paramInt) {
    return SettingsDAO.getInt(paramContext, paramInt, new Enum[] { paramEnum });
  }
  
  public String getSetting(Context paramContext, Enum<?> paramEnum, String paramString) {
    return SettingsDAO.getString(paramContext, paramString, new Enum[] { paramEnum });
  }
  
  public boolean getSetting(Context paramContext, Enum<?> paramEnum, boolean paramBoolean) {
    return SettingsDAO.getBoolean(paramContext, paramBoolean, new Enum[] { paramEnum });
  }
  
  public boolean isCodeCacheEnabled(AppInfoEntity paramAppInfoEntity) {
    JSONArray jSONArray;
    JSONObject jSONObject = SettingsDAO.getJSONObject((Context)AppbrandContext.getInst().getApplicationContext(), new Enum[] { (Enum)Settings.BDP_CODECACHE_CONFIG });
    if (jSONObject != null) {
      try {
        jSONArray = jSONObject.getJSONArray("enable_codecache_rules");
      } catch (JSONException jSONException) {
        AppBrandLogger.w("CodeCacheServiceImpl", new Object[] { "parse codecache rules failed" });
        jSONArray = new JSONArray();
      } 
    } else {
      jSONArray = new JSONArray();
    } 
    for (int i = 0; i < jSONArray.length(); i++) {
      if (checkTypeRule(paramAppInfoEntity, jSONArray.optJSONObject(i)))
        return true; 
    } 
    return false;
  }
  
  public void onCompiled(int paramInt1, int paramInt2, int paramInt3) {
    AppBrandLogger.d("CodeCacheServiceImpl", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\codecache\CodeCacheServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */