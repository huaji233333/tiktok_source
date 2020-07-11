package com.tt.miniapp.streamloader;

import android.text.TextUtils;
import com.tt.miniapphost.entity.AppInfoEntity;

public class PkgDownloadEntity {
  private AppInfoEntity mAppInfo;
  
  private String mPkgCompressType;
  
  private int mUrlIndex = -1;
  
  public PkgDownloadEntity(AppInfoEntity paramAppInfoEntity) {
    this.mAppInfo = paramAppInfoEntity;
    this.mPkgCompressType = this.mAppInfo.pkgCompressType;
  }
  
  private String appendPkgCompressTypeToUrl(String paramString1, String paramString2) {
    StringBuilder stringBuilder;
    if (!TextUtils.isEmpty(paramString1) && !TextUtils.isEmpty(paramString2)) {
      stringBuilder = new StringBuilder(paramString2);
      stringBuilder.append(".");
      stringBuilder.append(paramString1);
      return stringBuilder.toString();
    } 
    return (String)stringBuilder;
  }
  
  private boolean hasPkgUrls() {
    return (this.mAppInfo.appUrls != null && this.mAppInfo.appUrls.size() > 0);
  }
  
  public boolean enableFallback() {
    return (!TextUtils.isEmpty(this.mPkgCompressType) && hasPkgUrls());
  }
  
  public AppInfoEntity getAppInfo() {
    return this.mAppInfo;
  }
  
  public String getCompressType() {
    return this.mPkgCompressType;
  }
  
  public String getOriginPkgUrl() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mUrlIndex : I
    //   6: iflt -> 62
    //   9: aload_0
    //   10: getfield mAppInfo : Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   13: getfield appUrls : Ljava/util/List;
    //   16: ifnull -> 62
    //   19: aload_0
    //   20: getfield mUrlIndex : I
    //   23: aload_0
    //   24: getfield mAppInfo : Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   27: getfield appUrls : Ljava/util/List;
    //   30: invokeinterface size : ()I
    //   35: if_icmpge -> 62
    //   38: aload_0
    //   39: getfield mAppInfo : Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   42: getfield appUrls : Ljava/util/List;
    //   45: aload_0
    //   46: getfield mUrlIndex : I
    //   49: invokeinterface get : (I)Ljava/lang/Object;
    //   54: checkcast java/lang/String
    //   57: astore_1
    //   58: aload_0
    //   59: monitorexit
    //   60: aload_1
    //   61: areturn
    //   62: aload_0
    //   63: monitorexit
    //   64: aconst_null
    //   65: areturn
    //   66: astore_1
    //   67: aload_0
    //   68: monitorexit
    //   69: aload_1
    //   70: athrow
    // Exception table:
    //   from	to	target	type
    //   2	58	66	finally
  }
  
  public String getPkgUrl() {
    String str = getOriginPkgUrl();
    return TextUtils.isEmpty(this.mPkgCompressType) ? str : appendPkgCompressTypeToUrl(this.mPkgCompressType, str);
  }
  
  public boolean isBrPkg() {
    return TextUtils.equals(this.mPkgCompressType, "br");
  }
  
  public String nextOriginPkgUrl() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_0
    //   4: getfield mUrlIndex : I
    //   7: iconst_1
    //   8: iadd
    //   9: putfield mUrlIndex : I
    //   12: aload_0
    //   13: invokevirtual getOriginPkgUrl : ()Ljava/lang/String;
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: areturn
    //   21: astore_1
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_1
    //   25: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	21	finally
  }
  
  public String nextPkgUrl() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_0
    //   4: getfield mUrlIndex : I
    //   7: iconst_1
    //   8: iadd
    //   9: putfield mUrlIndex : I
    //   12: aload_0
    //   13: invokevirtual getPkgUrl : ()Ljava/lang/String;
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: areturn
    //   21: astore_1
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_1
    //   25: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	21	finally
  }
  
  public void reset() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_0
    //   4: putfield mUrlIndex : I
    //   7: aload_0
    //   8: ldc ''
    //   10: putfield mPkgCompressType : Ljava/lang/String;
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\PkgDownloadEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */