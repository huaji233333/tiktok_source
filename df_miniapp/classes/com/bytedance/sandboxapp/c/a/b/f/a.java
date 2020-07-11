package com.bytedance.sandboxapp.c.a.b.f;

import com.bytedance.sandboxapp.a.a.c.c;
import com.bytedance.sandboxapp.c.a.a.f;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import d.f.b.l;

public final class a extends c {
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final ApiCallbackData a(c.b paramb, com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'paramParser'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 'apiInvokeInfo'
    //   9: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_0
    //   13: getfield apiName : Ljava/lang/String;
    //   16: checkcast java/lang/CharSequence
    //   19: ldc 'createInnerDownloadTask'
    //   21: checkcast java/lang/CharSequence
    //   24: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   27: istore #4
    //   29: aload_1
    //   30: getfield b : Ljava/lang/String;
    //   33: astore #5
    //   35: aload #5
    //   37: ldc 'paramParser.url'
    //   39: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   42: iload #4
    //   44: ifne -> 118
    //   47: aload #5
    //   49: checkcast java/lang/CharSequence
    //   52: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   55: ifne -> 118
    //   58: aload_0
    //   59: getfield context : Lcom/bytedance/sandboxapp/b/a;
    //   62: ldc com/bytedance/sandboxapp/protocol/service/l/a
    //   64: invokeinterface getService : (Ljava/lang/Class;)Lcom/bytedance/sandboxapp/b/b;
    //   69: checkcast com/bytedance/sandboxapp/protocol/service/l/a
    //   72: astore #5
    //   74: aload_1
    //   75: getfield b : Ljava/lang/String;
    //   78: astore #6
    //   80: aload #6
    //   82: ldc 'paramParser.url'
    //   84: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   87: aload #5
    //   89: ldc 'download'
    //   91: aload #6
    //   93: invokeinterface isSafeDomain : (Ljava/lang/String;Ljava/lang/String;)Z
    //   98: ifne -> 118
    //   101: aload_0
    //   102: aload_1
    //   103: getfield b : Ljava/lang/String;
    //   106: invokevirtual a : (Ljava/lang/String;)Lcom/bytedance/sandboxapp/protocol/service/api/entity/ApiCallbackData;
    //   109: astore_1
    //   110: aload_1
    //   111: ldc 'buildInvalidDomain(paramParser.url)'
    //   113: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   116: aload_1
    //   117: areturn
    //   118: aload_1
    //   119: getfield d : Ljava/lang/String;
    //   122: astore #6
    //   124: aload #6
    //   126: ifnull -> 244
    //   129: aload #6
    //   131: checkcast java/lang/CharSequence
    //   134: astore #5
    //   136: aload #5
    //   138: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   141: ifne -> 244
    //   144: aload #5
    //   146: ldc 'null'
    //   148: checkcast java/lang/CharSequence
    //   151: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   154: ifne -> 244
    //   157: aload #5
    //   159: ldc 'undefined'
    //   161: checkcast java/lang/CharSequence
    //   164: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   167: ifne -> 244
    //   170: aload_0
    //   171: getfield context : Lcom/bytedance/sandboxapp/b/a;
    //   174: ldc com/bytedance/sandboxapp/protocol/service/f/a
    //   176: invokeinterface getService : (Ljava/lang/Class;)Lcom/bytedance/sandboxapp/b/b;
    //   181: checkcast com/bytedance/sandboxapp/protocol/service/f/a
    //   184: astore #7
    //   186: aload #7
    //   188: aload #6
    //   190: invokeinterface canWrite : (Ljava/lang/String;)Z
    //   195: ifne -> 213
    //   198: aload_0
    //   199: aload #6
    //   201: invokevirtual b : (Ljava/lang/String;)Lcom/bytedance/sandboxapp/protocol/service/api/entity/ApiCallbackData;
    //   204: astore_1
    //   205: aload_1
    //   206: ldc 'buildPermissionDenied(filePath)'
    //   208: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   211: aload_1
    //   212: areturn
    //   213: aload #6
    //   215: astore #5
    //   217: aload #7
    //   219: aload #6
    //   221: invokeinterface isParentDirExists : (Ljava/lang/String;)Z
    //   226: ifne -> 247
    //   229: aload_0
    //   230: aload #6
    //   232: invokevirtual c : (Ljava/lang/String;)Lcom/bytedance/sandboxapp/protocol/service/api/entity/ApiCallbackData;
    //   235: astore_1
    //   236: aload_1
    //   237: ldc 'buildNoDirectory(filePath)'
    //   239: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   242: aload_1
    //   243: areturn
    //   244: aconst_null
    //   245: astore #5
    //   247: iload #4
    //   249: ifeq -> 259
    //   252: ldc 'onInnerDownloadTaskStateChange'
    //   254: astore #6
    //   256: goto -> 263
    //   259: ldc 'onDownloadTaskStateChange'
    //   261: astore #6
    //   263: aload_0
    //   264: getfield context : Lcom/bytedance/sandboxapp/b/a;
    //   267: ldc com/bytedance/sandboxapp/protocol/service/i/a
    //   269: invokeinterface getService : (Ljava/lang/Class;)Lcom/bytedance/sandboxapp/b/b;
    //   274: checkcast com/bytedance/sandboxapp/protocol/service/i/a
    //   277: invokeinterface getRequestIdentifyId : ()I
    //   282: istore_3
    //   283: aload_0
    //   284: getfield context : Lcom/bytedance/sandboxapp/b/a;
    //   287: ldc com/bytedance/sandboxapp/protocol/service/request/a
    //   289: invokeinterface getService : (Ljava/lang/Class;)Lcom/bytedance/sandboxapp/b/b;
    //   294: checkcast com/bytedance/sandboxapp/protocol/service/request/a
    //   297: astore #7
    //   299: aload_1
    //   300: getfield b : Ljava/lang/String;
    //   303: astore #8
    //   305: aload #8
    //   307: ldc 'paramParser.url'
    //   309: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   312: aload_1
    //   313: getfield c : Lorg/json/JSONObject;
    //   316: astore #9
    //   318: aload_1
    //   319: getfield e : Ljava/lang/Boolean;
    //   322: astore_1
    //   323: aload_1
    //   324: ldc 'paramParser.useCloud'
    //   326: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   329: aload #7
    //   331: new com/bytedance/sandboxapp/protocol/service/request/entity/a$d
    //   334: dup
    //   335: iload_3
    //   336: aload #8
    //   338: aload #9
    //   340: aload #5
    //   342: aload_1
    //   343: invokevirtual booleanValue : ()Z
    //   346: invokespecial <init> : (ILjava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Z)V
    //   349: new com/bytedance/sandboxapp/c/a/b/f/a$a
    //   352: dup
    //   353: aload_0
    //   354: aload_2
    //   355: aload #6
    //   357: invokespecial <init> : (Lcom/bytedance/sandboxapp/c/a/b/f/a;Lcom/bytedance/sandboxapp/protocol/service/api/entity/a;Ljava/lang/String;)V
    //   360: checkcast com/bytedance/sandboxapp/protocol/service/request/entity/a$a
    //   363: invokeinterface addDownloadRequest : (Lcom/bytedance/sandboxapp/protocol/service/request/entity/a$d;Lcom/bytedance/sandboxapp/protocol/service/request/entity/a$a;)V
    //   368: aload_0
    //   369: invokestatic a : ()Lcom/bytedance/sandboxapp/a/a/c/c$a;
    //   372: iload_3
    //   373: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   376: invokevirtual a : (Ljava/lang/Integer;)Lcom/bytedance/sandboxapp/a/a/c/c$a;
    //   379: invokevirtual b : ()Lcom/bytedance/sandboxapp/b/b/a;
    //   382: invokevirtual a : (Lcom/bytedance/sandboxapp/b/b/a;)Lcom/bytedance/sandboxapp/protocol/service/api/entity/ApiCallbackData;
    //   385: areturn
  }
  
  public static final class a implements com.bytedance.sandboxapp.protocol.service.request.entity.a.a {
    a(a param1a, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a1, String param1String) {}
    
    public final void a(com.bytedance.sandboxapp.protocol.service.request.entity.a.b param1b) {
      String str1;
      String str2;
      l.b(param1b, "requestResult");
      if (param1b.g != null) {
        str1 = com.bytedance.sandboxapp.c.a.a.a.a.a(param1b.g);
      } else {
        str1 = param1b.f;
      } 
      com.bytedance.sandboxapp.protocol.service.api.a.a a1 = this.b.c;
      com.bytedance.sandboxapp.protocol.service.api.a.a a2 = (com.bytedance.sandboxapp.protocol.service.api.a.a)((com.bytedance.sandboxapp.c.a.a.a)this.a).sandboxAppApiRuntime;
      String str3 = this.c;
      com.bytedance.sandboxapp.a.a.a.a a3 = com.bytedance.sandboxapp.a.a.a.a.a().a(Integer.valueOf(param1b.b));
      if (param1b.a) {
        str2 = "success";
      } else {
        str2 = "fail";
      } 
      f f = a3.a(str2).b(String.valueOf(param1b.c)).c(param1b.d).d(param1b.e).e(str1).b();
      l.a(f, "OnDownloadTaskStateChang…                 .build()");
      a1.handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a.b.a.a(a2, str3, (com.bytedance.sandboxapp.protocol.service.api.b.a)f).a());
    }
    
    public final void a(com.bytedance.sandboxapp.protocol.service.request.entity.a.c param1c) {
      l.b(param1c, "requestState");
      com.bytedance.sandboxapp.protocol.service.api.a.a a1 = this.b.c;
      com.bytedance.sandboxapp.protocol.service.api.a.a a2 = (com.bytedance.sandboxapp.protocol.service.api.a.a)((com.bytedance.sandboxapp.c.a.a.a)this.a).sandboxAppApiRuntime;
      String str = this.c;
      f f = com.bytedance.sandboxapp.a.a.a.a.a().a("progressUpdate").a(Integer.valueOf(param1c.a)).b(Integer.valueOf(param1c.b)).a(Long.valueOf(param1c.c)).b(Long.valueOf(param1c.d)).b();
      l.a(f, "OnDownloadTaskStateChang…sExpectedToWrite).build()");
      a1.handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a.b.a.a(a2, str, (com.bytedance.sandboxapp.protocol.service.api.b.a)f).a());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\f\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */