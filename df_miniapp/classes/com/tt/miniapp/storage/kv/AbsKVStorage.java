package com.tt.miniapp.storage.kv;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.appbrand.mmkv.MMKV;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import java.io.IOException;
import java.util.Set;
import org.json.JSONArray;

public abstract class AbsKVStorage {
  private final long DATA_PER_LIMIT;
  
  String EXCEPTION_MSG_EXCEED = "exceed storage max size 10Mb";
  
  String EXCEPTION_MSG_OVERFLOW = "exceed storage item max length";
  
  private final String SP_FILE_PREFIX;
  
  private final long STORAGE_LIMIT;
  
  public AbsKVStorage(String paramString, long paramLong1, long paramLong2) {
    this.SP_FILE_PREFIX = paramString;
    this.STORAGE_LIMIT = paramLong1;
    this.DATA_PER_LIMIT = paramLong2;
  }
  
  private String getFileName() {
    if (AppbrandApplication.getInst().getAppInfo() == null)
      return getFilePrefix(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getFilePrefix());
    stringBuilder.append((AppbrandApplication.getInst().getAppInfo()).appId);
    return stringBuilder.toString();
  }
  
  private long getFileSizeByMMKV() {
    MMKV mMKV = (MMKV)getSP();
    String[] arrayOfString = mMKV.allKeysMMKV();
    if (arrayOfString == null)
      return 0L; 
    int k = arrayOfString.length;
    int i = 0;
    int j = 0;
    while (i < k) {
      j += mMKV.getValueActualSize(arrayOfString[i]);
      i++;
    } 
    return j;
  }
  
  private long getFileSizeBySP() {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial getSP : ()Landroid/content/SharedPreferences;
    //   4: invokeinterface getAll : ()Ljava/util/Map;
    //   9: invokeinterface keySet : ()Ljava/util/Set;
    //   14: astore #6
    //   16: aload #6
    //   18: invokeinterface size : ()I
    //   23: anewarray java/lang/String
    //   26: astore #7
    //   28: aload #6
    //   30: aload #7
    //   32: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   37: pop
    //   38: lconst_0
    //   39: lstore_2
    //   40: iconst_0
    //   41: istore_1
    //   42: iload_1
    //   43: aload #6
    //   45: invokeinterface size : ()I
    //   50: if_icmpge -> 141
    //   53: aload #7
    //   55: iload_1
    //   56: aaload
    //   57: astore #8
    //   59: iload_1
    //   60: ifne -> 86
    //   63: aload #8
    //   65: ldc '__'
    //   67: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   70: ifeq -> 86
    //   73: lload_2
    //   74: lstore #4
    //   76: aload #8
    //   78: ldc '__'
    //   80: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   83: ifne -> 131
    //   86: lload_2
    //   87: lstore #4
    //   89: aload #8
    //   91: ldc '__type__'
    //   93: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   96: ifne -> 131
    //   99: aload_0
    //   100: invokespecial getSP : ()Landroid/content/SharedPreferences;
    //   103: aload #8
    //   105: aconst_null
    //   106: invokeinterface getString : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   111: astore #8
    //   113: lload_2
    //   114: lstore #4
    //   116: aload #8
    //   118: ifnull -> 131
    //   121: lload_2
    //   122: aload #8
    //   124: invokevirtual length : ()I
    //   127: i2l
    //   128: ladd
    //   129: lstore #4
    //   131: iload_1
    //   132: iconst_1
    //   133: iadd
    //   134: istore_1
    //   135: lload #4
    //   137: lstore_2
    //   138: goto -> 42
    //   141: lload_2
    //   142: lreturn
  }
  
  private JSONArray getKeysByMMKV() {
    MMKV mMKV = (MMKV)getSP();
    JSONArray jSONArray = new JSONArray();
    String[] arrayOfString = mMKV.allKeysMMKV();
    if (arrayOfString == null)
      return null; 
    int j = arrayOfString.length;
    for (int i = 0; i < j; i++) {
      String str = arrayOfString[i];
      if (!str.endsWith("__type__") && !str.endsWith("is_migration_success"))
        jSONArray.put(str); 
    } 
    return jSONArray;
  }
  
  private JSONArray getKeysBySP() {
    Set set = getSP().getAll().keySet();
    JSONArray jSONArray = new JSONArray();
    if (set != null)
      for (String str : set) {
        if (!str.endsWith("__type__"))
          jSONArray.put(str); 
      }  
    return jSONArray;
  }
  
  private SharedPreferences getSP() {
    return KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), getFileName());
  }
  
  public boolean clear() {
    return getSP().edit().clear().commit();
  }
  
  public String getDataType(String paramString) {
    SharedPreferences sharedPreferences = getSP();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("__type__");
    return sharedPreferences.getString(stringBuilder.toString(), null);
  }
  
  public String getFilePrefix() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(HostDependManager.getInst().getSpPrefixPath());
    stringBuilder.append(this.SP_FILE_PREFIX);
    return stringBuilder.toString();
  }
  
  public long getFileSize() {
    return (getSP() instanceof MMKV) ? getFileSizeByMMKV() : getFileSizeBySP();
  }
  
  public JSONArray getKeys() {
    return (getSP() instanceof MMKV) ? getKeysByMMKV() : getKeysBySP();
  }
  
  public long getLimitSize() {
    return this.STORAGE_LIMIT;
  }
  
  public String getValue(String paramString) {
    return getSP().getString(paramString, null);
  }
  
  public boolean remove(String paramString) {
    SharedPreferences.Editor editor = getSP().edit().remove(paramString);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("__type__");
    return editor.remove(stringBuilder.toString()).commit();
  }
  
  public boolean setValue(String paramString1, String paramString2, String paramString3) throws IOException {
    SharedPreferences sharedPreferences = getSP();
    long l = (paramString2.getBytes()).length;
    if (l <= this.DATA_PER_LIMIT) {
      if (l + getFileSize() <= this.STORAGE_LIMIT) {
        SharedPreferences.Editor editor = sharedPreferences.edit().putString(paramString1, paramString2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString1);
        stringBuilder.append("__type__");
        return editor.putString(stringBuilder.toString(), paramString3).commit();
      } 
      throw new IOException(this.EXCEPTION_MSG_EXCEED);
    } 
    throw new IOException(this.EXCEPTION_MSG_OVERFLOW);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\storage\kv\AbsKVStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */