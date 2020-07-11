package com.tt.miniapp.business.cloud;

import android.text.TextUtils;
import android.util.Base64;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.d.a;
import com.tt.miniapp.base.MiniAppContext;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import d.a.ac;
import d.f.a.b;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.n;
import d.t;
import java.util.Map;

public final class LarkCloudService implements a {
  public static final Companion Companion = new Companion(null);
  
  private final MiniAppContext context;
  
  public LarkCloudService(MiniAppContext paramMiniAppContext) {
    this.context = paramMiniAppContext;
  }
  
  private final String encodeDigestToBase64(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null)
      return "-1"; 
    String str = Base64.encodeToString(paramArrayOfbyte, 11);
    l.a(str, "Base64.encodeToString(di…RAP or Base64.NO_PADDING)");
    return str;
  }
  
  private final String generateSign(Map<String, String> paramMap) {
    // Byte code:
    //   0: aload_1
    //   1: invokeinterface keySet : ()Ljava/util/Set;
    //   6: checkcast java/lang/Iterable
    //   9: astore_2
    //   10: aload_2
    //   11: ldc '$this$sorted'
    //   13: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   16: aload_2
    //   17: instanceof java/util/Collection
    //   20: ifeq -> 133
    //   23: aload_2
    //   24: checkcast java/util/Collection
    //   27: astore_3
    //   28: aload_3
    //   29: invokeinterface size : ()I
    //   34: iconst_1
    //   35: if_icmpgt -> 46
    //   38: aload_2
    //   39: invokestatic f : (Ljava/lang/Iterable;)Ljava/util/List;
    //   42: astore_2
    //   43: goto -> 142
    //   46: aload_3
    //   47: iconst_0
    //   48: anewarray java/lang/Comparable
    //   51: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   56: astore_2
    //   57: aload_2
    //   58: ifnull -> 123
    //   61: aload_2
    //   62: ifnull -> 113
    //   65: aload_2
    //   66: checkcast [Ljava/lang/Comparable;
    //   69: astore_2
    //   70: aload_2
    //   71: ifnull -> 103
    //   74: aload_2
    //   75: checkcast [Ljava/lang/Object;
    //   78: astore_2
    //   79: aload_2
    //   80: ldc '$this$sort'
    //   82: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   85: aload_2
    //   86: arraylength
    //   87: iconst_1
    //   88: if_icmple -> 95
    //   91: aload_2
    //   92: invokestatic sort : ([Ljava/lang/Object;)V
    //   95: aload_2
    //   96: invokestatic a : ([Ljava/lang/Object;)Ljava/util/List;
    //   99: astore_2
    //   100: goto -> 142
    //   103: new d/u
    //   106: dup
    //   107: ldc 'null cannot be cast to non-null type kotlin.Array<kotlin.Any?>'
    //   109: invokespecial <init> : (Ljava/lang/String;)V
    //   112: athrow
    //   113: new d/u
    //   116: dup
    //   117: ldc 'null cannot be cast to non-null type kotlin.Array<T>'
    //   119: invokespecial <init> : (Ljava/lang/String;)V
    //   122: athrow
    //   123: new d/u
    //   126: dup
    //   127: ldc 'null cannot be cast to non-null type kotlin.Array<T>'
    //   129: invokespecial <init> : (Ljava/lang/String;)V
    //   132: athrow
    //   133: aload_2
    //   134: invokestatic g : (Ljava/lang/Iterable;)Ljava/util/List;
    //   137: astore_2
    //   138: aload_2
    //   139: invokestatic c : (Ljava/util/List;)V
    //   142: aload_0
    //   143: ldc 'c9ba4701efd919cd748668a821abe126d147afb6d35a439112df25f01dcab26e'
    //   145: aload_2
    //   146: checkcast java/lang/Iterable
    //   149: ldc '&'
    //   151: checkcast java/lang/CharSequence
    //   154: aconst_null
    //   155: aconst_null
    //   156: iconst_0
    //   157: aconst_null
    //   158: new com/tt/miniapp/business/cloud/LarkCloudService$generateSign$signStr$1
    //   161: dup
    //   162: aload_1
    //   163: invokespecial <init> : (Ljava/util/Map;)V
    //   166: checkcast d/f/a/b
    //   169: bipush #30
    //   171: aconst_null
    //   172: invokestatic a : (Ljava/lang/Iterable;Ljava/lang/CharSequence;Ljava/lang/CharSequence;Ljava/lang/CharSequence;ILjava/lang/CharSequence;Ld/f/a/b;ILjava/lang/Object;)Ljava/lang/String;
    //   175: invokestatic encryptHmacSHA1 : (Ljava/lang/String;Ljava/lang/String;)[B
    //   178: invokespecial encodeDigestToBase64 : ([B)Ljava/lang/String;
    //   181: areturn
  }
  
  private final String getAid() {
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    InitParamsEntity initParamsEntity = appbrandContext.getInitParams();
    if (initParamsEntity != null) {
      String str2 = initParamsEntity.getAppId();
      String str1 = str2;
      return (str2 == null) ? "-1" : str1;
    } 
    return "-1";
  }
  
  private final String getAnonymousId() {
    String str = UserInfoManager.getLocalTmpId();
    if (!TextUtils.isEmpty(str)) {
      l.a(str, "anonymousId");
      return str;
    } 
    return "-1";
  }
  
  private final String getAppId() {
    AppInfoEntity appInfoEntity = getContext().getAppInfo();
    if (appInfoEntity != null) {
      String str2 = appInfoEntity.appId;
      String str1 = str2;
      return (str2 == null) ? "-1" : str1;
    } 
    return "-1";
  }
  
  private final String getNonce() {
    return String.valueOf(Math.random());
  }
  
  private final String getSessionId() {
    String str = (UserInfoManager.getHostClientUserInfo()).sessionId;
    if (!TextUtils.isEmpty(str)) {
      l.a(str, "sessionId");
      return str;
    } 
    return "-1";
  }
  
  private final String getTimeStamp() {
    return String.valueOf(System.currentTimeMillis());
  }
  
  public final MiniAppContext getContext() {
    return this.context;
  }
  
  public final Map<String, String> getRequestHeader() {
    String str1 = getTimeStamp();
    String str2 = getNonce();
    String str3 = getAppId();
    String str4 = getAid();
    String str5 = getSessionId();
    String str6 = getAnonymousId();
    Map<String, String> map = ac.b(new n[] { t.a("x-lc-mp-timestamp", str1), t.a("x-lc-mp-nonce", str2), t.a("x-lc-mp-appid", str3), t.a("x-lc-mp-aid", str4), t.a("x-lc-mp-sessionid", str5), t.a("x-lc-mp-anonymousid", str6) });
    map.put("x-lc-mp-sign", generateSign(ac.b(new n[] { t.a("timestamp", str1), t.a("nonce", str2), t.a("appId", str3), t.a("aId", str4), t.a("sessionId", str5), t.a("anonymousId", str6) })));
    return map;
  }
  
  public final void onDestroy() {}
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class LarkCloudService$generateSign$signStr$1 extends m implements b<String, String> {
    LarkCloudService$generateSign$signStr$1(Map param1Map) {
      super(1);
    }
    
    public final String invoke(String param1String) {
      l.b(param1String, "it");
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1String);
      stringBuilder.append('=');
      stringBuilder.append((String)this.$signMap.get(param1String));
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\cloud\LarkCloudService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */