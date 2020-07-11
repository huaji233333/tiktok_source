package com.tt.miniapp.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.a;
import com.he.loader.Library;
import com.ss.android.ugc.aweme.keva.d;
import com.tencent.appbrand.mmkv.MMKV;
import com.tencent.appbrand.mmkv.MMKVHandler;
import com.tencent.appbrand.mmkv.c;
import com.tencent.appbrand.mmkv.d;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

@Deprecated
public class MMKVUtil {
  private static MMKVCachedMap mCachedMMKVMap;
  
  private static String sMMKVDir;
  
  static boolean getBoolean(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    return (mMKV == null) ? paramBoolean : mMKV.decodeBool(paramString2, paramBoolean);
  }
  
  private static SharedPreferences getBundleSp(Context paramContext) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(HostDependManager.getInst().getSpPrefixPath());
    stringBuilder.append("appbrand_file");
    return d.a(paramContext, stringBuilder.toString(), 0);
  }
  
  private static MMKV getMMKV(Context paramContext, String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   2: monitorenter
    //   3: invokestatic isInit : ()Z
    //   6: ifne -> 27
    //   9: aload_0
    //   10: invokestatic initDir : (Landroid/content/Context;)V
    //   13: getstatic com/tt/miniapp/mmkv/MMKVUtil.sMMKVDir : Ljava/lang/String;
    //   16: new com/tt/miniapp/mmkv/MMKVUtil$2
    //   19: dup
    //   20: invokespecial <init> : ()V
    //   23: invokestatic initialize : (Ljava/lang/String;Lcom/tencent/appbrand/mmkv/MMKV$a;)Ljava/lang/String;
    //   26: pop
    //   27: getstatic com/tt/miniapp/mmkv/MMKVUtil.mCachedMMKVMap : Lcom/tt/miniapp/mmkv/MMKVCachedMap;
    //   30: ifnonnull -> 67
    //   33: aload_0
    //   34: invokestatic isMainProcess : (Landroid/content/Context;)Z
    //   37: ifeq -> 55
    //   40: new com/tt/miniapp/mmkv/MMKVCachedMap
    //   43: dup
    //   44: bipush #20
    //   46: invokespecial <init> : (I)V
    //   49: putstatic com/tt/miniapp/mmkv/MMKVUtil.mCachedMMKVMap : Lcom/tt/miniapp/mmkv/MMKVCachedMap;
    //   52: goto -> 67
    //   55: new com/tt/miniapp/mmkv/MMKVCachedMap
    //   58: dup
    //   59: bipush #100
    //   61: invokespecial <init> : (I)V
    //   64: putstatic com/tt/miniapp/mmkv/MMKVUtil.mCachedMMKVMap : Lcom/tt/miniapp/mmkv/MMKVCachedMap;
    //   67: getstatic com/tt/miniapp/mmkv/MMKVUtil.mCachedMMKVMap : Lcom/tt/miniapp/mmkv/MMKVCachedMap;
    //   70: aload_1
    //   71: invokevirtual containsKey : (Ljava/lang/String;)Z
    //   74: ifeq -> 88
    //   77: getstatic com/tt/miniapp/mmkv/MMKVUtil.mCachedMMKVMap : Lcom/tt/miniapp/mmkv/MMKVCachedMap;
    //   80: aload_1
    //   81: invokevirtual get : (Ljava/lang/String;)Lcom/tencent/appbrand/mmkv/MMKV;
    //   84: astore_0
    //   85: goto -> 106
    //   88: aload_1
    //   89: iconst_2
    //   90: aconst_null
    //   91: getstatic com/tt/miniapp/mmkv/MMKVUtil.sMMKVDir : Ljava/lang/String;
    //   94: invokestatic mmkvWithID : (Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Lcom/tencent/appbrand/mmkv/MMKV;
    //   97: astore_0
    //   98: getstatic com/tt/miniapp/mmkv/MMKVUtil.mCachedMMKVMap : Lcom/tt/miniapp/mmkv/MMKVCachedMap;
    //   101: aload_1
    //   102: aload_0
    //   103: invokevirtual put : (Ljava/lang/String;Lcom/tencent/appbrand/mmkv/MMKV;)V
    //   106: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   108: monitorexit
    //   109: aload_0
    //   110: areturn
    //   111: astore_0
    //   112: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   114: monitorexit
    //   115: aload_0
    //   116: athrow
    // Exception table:
    //   from	to	target	type
    //   3	27	111	finally
    //   27	52	111	finally
    //   55	67	111	finally
    //   67	85	111	finally
    //   88	106	111	finally
  }
  
  static int getMMKVKeysLength(Context paramContext, String paramString) {
    MMKV mMKV = getMMKV(paramContext, paramString);
    return (mMKV == null) ? 0 : ((mMKV.allKeysMMKV() == null) ? 0 : (mMKV.allKeysMMKV()).length);
  }
  
  public static SharedPreferences getSPByName(Context paramContext, String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   2: monitorenter
    //   3: aload_0
    //   4: astore_2
    //   5: aload_0
    //   6: ifnonnull -> 19
    //   9: invokestatic getInst : ()Lcom/tt/miniapphost/AppbrandContext;
    //   12: invokevirtual getApplicationContext : ()Landroid/app/Application;
    //   15: astore_2
    //   16: goto -> 19
    //   19: aload_2
    //   20: ifnonnull -> 36
    //   23: new com/tt/miniapp/mmkv/NullPointerSafeSP
    //   26: dup
    //   27: invokespecial <init> : ()V
    //   30: astore_0
    //   31: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   33: monitorexit
    //   34: aload_0
    //   35: areturn
    //   36: new java/lang/StringBuilder
    //   39: dup
    //   40: invokespecial <init> : ()V
    //   43: astore_0
    //   44: aload_0
    //   45: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   48: invokevirtual getSpPrefixPath : ()Ljava/lang/String;
    //   51: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload_0
    //   56: aload_1
    //   57: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: pop
    //   61: aload_2
    //   62: aload_0
    //   63: invokevirtual toString : ()Ljava/lang/String;
    //   66: iconst_0
    //   67: invokestatic a : (Landroid/content/Context;Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   70: astore_0
    //   71: aload_2
    //   72: invokestatic isMMKVEnable : (Landroid/content/Context;)Z
    //   75: ifeq -> 146
    //   78: invokestatic getRootDir : ()Ljava/lang/String;
    //   81: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   84: ifeq -> 90
    //   87: goto -> 146
    //   90: aload_0
    //   91: invokeinterface getAll : ()Ljava/util/Map;
    //   96: ifnull -> 121
    //   99: aload_0
    //   100: invokeinterface getAll : ()Ljava/util/Map;
    //   105: invokeinterface size : ()I
    //   110: ifle -> 121
    //   113: aload_2
    //   114: aload_1
    //   115: invokestatic isMigrationSuccess : (Landroid/content/Context;Ljava/lang/String;)Z
    //   118: ifeq -> 141
    //   121: ldc 'MMKVUtil'
    //   123: iconst_1
    //   124: anewarray java/lang/Object
    //   127: dup
    //   128: iconst_0
    //   129: ldc 'use mmkv'
    //   131: aastore
    //   132: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   135: aload_2
    //   136: aload_1
    //   137: invokestatic getMMKV : (Landroid/content/Context;Ljava/lang/String;)Lcom/tencent/appbrand/mmkv/MMKV;
    //   140: astore_0
    //   141: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   143: monitorexit
    //   144: aload_0
    //   145: areturn
    //   146: ldc 'MMKVUtil'
    //   148: iconst_1
    //   149: anewarray java/lang/Object
    //   152: dup
    //   153: iconst_0
    //   154: ldc 'use sp'
    //   156: aastore
    //   157: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   160: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   162: monitorexit
    //   163: aload_0
    //   164: areturn
    //   165: ldc com/tt/miniapp/mmkv/MMKVUtil
    //   167: monitorexit
    //   168: aload_0
    //   169: athrow
    //   170: astore_0
    //   171: goto -> 165
    // Exception table:
    //   from	to	target	type
    //   9	16	170	finally
    //   23	31	170	finally
    //   36	87	170	finally
    //   90	113	170	finally
    //   113	121	170	finally
    //   121	141	170	finally
    //   146	160	170	finally
  }
  
  public static void init(Context paramContext) {
    if (paramContext != null)
      try {
        if (!isMMKVEnable(paramContext))
          return; 
        initDir(paramContext);
        MMKV.initialize(sMMKVDir, new MMKV.a() {
              public final void loadLibrary(String param1String) {
                try {
                  Library.load(param1String);
                  return;
                } catch (Exception exception) {
                  AppBrandLogger.e("MMKVUtil", new Object[] { exception });
                  return;
                } 
              }
            });
        return;
      } finally {
        Exception exception = null;
        setMMKVEnable(paramContext, 0);
      }  
  }
  
  private static void initDir(Context paramContext) {
    if (TextUtils.isEmpty(HostDependManager.getInst().getSpPrefixPath())) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramContext.getFilesDir().getAbsolutePath());
      stringBuilder1.append("/appbrand/mmkv");
      sMMKVDir = stringBuilder1.toString();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContext.getFilesDir().getAbsolutePath());
    stringBuilder.append("/");
    stringBuilder.append(HostDependManager.getInst().getSpPrefixPath());
    stringBuilder.append("/appbrand/mmkv");
    sMMKVDir = stringBuilder.toString();
  }
  
  static boolean isKeyValueExist(Context paramContext, String paramString1, String paramString2) {
    return getMMKV(paramContext, paramString1).contains(paramString2);
  }
  
  private static boolean isMMKVEnable(Context paramContext) {
    return (getBundleSp(paramContext).getInt("mmkv_enable", 1) != 0);
  }
  
  public static void preload(Context paramContext) {
    getSPByName(paramContext.getApplicationContext(), "openSchemaTime");
  }
  
  static void setBoolean(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramBoolean);
  }
  
  static void setBytes(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfbyte) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramArrayOfbyte);
  }
  
  static void setDouble(Context paramContext, String paramString1, String paramString2, double paramDouble) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramDouble);
  }
  
  static void setFloat(Context paramContext, String paramString1, String paramString2, float paramFloat) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramFloat);
  }
  
  static void setInt(Context paramContext, String paramString1, String paramString2, int paramInt) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramInt);
  }
  
  static void setLong(Context paramContext, String paramString1, String paramString2, long paramLong) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramLong);
  }
  
  public static void setMMKVEnable(Context paramContext, int paramInt) {
    getBundleSp(paramContext).edit().putInt("mmkv_enable", paramInt).apply();
  }
  
  static void setString(Context paramContext, String paramString1, String paramString2, String paramString3) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramString3);
  }
  
  static void setStringSet(Context paramContext, String paramString1, String paramString2, Set<String> paramSet) {
    MMKV mMKV = getMMKV(paramContext, paramString1);
    if (mMKV == null)
      return; 
    mMKV.encode(paramString2, paramSet);
  }
  
  static class MMKVCallback implements MMKVHandler {
    public void mmkvLog(c param1c, String param1String1, int param1Int, String param1String2, String param1String3) {
      if (param1c == c.LevelError)
        AppBrandLogger.e("MMKV", new Object[] { a.a(Locale.getDefault(), "file=%s, line=%d, function=%s, message=%s", new Object[] { param1String1, Integer.valueOf(param1Int), param1String2, param1String3 }) }); 
    }
    
    public d onMMKVCRCCheckFail(String param1String) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("mmapid", param1String);
      AppBrandLogger.e("MMKV", new Object[] { hashMap });
      return d.OnErrorRecover;
    }
    
    public d onMMKVFileLengthError(String param1String) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("mmapid", param1String);
      AppBrandLogger.e("MMKV", new Object[] { hashMap });
      return d.OnErrorRecover;
    }
    
    public boolean wantLogRedirecting() {
      return true;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mmkv\MMKVUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */