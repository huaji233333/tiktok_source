package com.bytedance.ies.bullet.kit.rn.internal;

import d.a.ab;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.j.d;
import d.m.p;
import d.o;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class b {
  public static final a d = new a(null);
  
  public List<String> a = new ArrayList<String>();
  
  public String b = "";
  
  public String c = "";
  
  public static final class a {
    private a() {}
    
    public static b a(File param1File) {
      // Byte code:
      //   0: aload_0
      //   1: ifnull -> 134
      //   4: aload_0
      //   5: invokevirtual getName : ()Ljava/lang/String;
      //   8: astore_0
      //   9: goto -> 12
      //   12: ldc 'bundle_info.json'
      //   14: checkcast java/lang/CharSequence
      //   17: aload_0
      //   18: checkcast java/lang/CharSequence
      //   21: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
      //   24: ifeq -> 139
      //   27: new java/io/File
      //   30: dup
      //   31: aload_0
      //   32: invokespecial <init> : (Ljava/lang/String;)V
      //   35: invokevirtual exists : ()Z
      //   38: ifeq -> 139
      //   41: iconst_1
      //   42: istore_1
      //   43: goto -> 141
      //   46: aload_0
      //   47: ifnull -> 129
      //   50: new java/io/BufferedReader
      //   53: dup
      //   54: new java/io/InputStreamReader
      //   57: dup
      //   58: new java/io/FileInputStream
      //   61: dup
      //   62: aload_0
      //   63: invokespecial <init> : (Ljava/lang/String;)V
      //   66: checkcast java/io/InputStream
      //   69: getstatic d/m/d.a : Ljava/nio/charset/Charset;
      //   72: invokespecial <init> : (Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
      //   75: checkcast java/io/Reader
      //   78: sipush #8192
      //   81: invokespecial <init> : (Ljava/io/Reader;I)V
      //   84: checkcast java/io/Closeable
      //   87: astore_0
      //   88: aload_0
      //   89: checkcast java/io/BufferedReader
      //   92: checkcast java/io/Reader
      //   95: invokestatic b : (Ljava/io/Reader;)Ljava/lang/String;
      //   98: astore_2
      //   99: aload_0
      //   100: aconst_null
      //   101: invokestatic a : (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      //   104: aload_2
      //   105: ifnull -> 129
      //   108: getstatic com/bytedance/ies/bullet/kit/rn/internal/b.d : Lcom/bytedance/ies/bullet/kit/rn/internal/b$a;
      //   111: aload_2
      //   112: invokespecial a : (Ljava/lang/String;)Lcom/bytedance/ies/bullet/kit/rn/internal/b;
      //   115: astore_0
      //   116: aload_0
      //   117: areturn
      //   118: astore_2
      //   119: aload_2
      //   120: athrow
      //   121: astore_3
      //   122: aload_0
      //   123: aload_2
      //   124: invokestatic a : (Ljava/io/Closeable;Ljava/lang/Throwable;)V
      //   127: aload_3
      //   128: athrow
      //   129: aconst_null
      //   130: areturn
      //   131: astore_0
      //   132: aconst_null
      //   133: areturn
      //   134: aconst_null
      //   135: astore_0
      //   136: goto -> 12
      //   139: iconst_0
      //   140: istore_1
      //   141: iload_1
      //   142: ifeq -> 148
      //   145: goto -> 46
      //   148: aconst_null
      //   149: astore_0
      //   150: goto -> 46
      // Exception table:
      //   from	to	target	type
      //   4	9	131	finally
      //   12	41	131	finally
      //   50	88	131	finally
      //   88	99	118	finally
      //   99	104	131	finally
      //   108	116	131	finally
      //   119	121	121	finally
      //   122	129	131	finally
    }
    
    private b a(String param1String) {
      Object object1;
      l.b(param1String, "json");
      try {
        JSONObject jSONObject = new JSONObject(param1String);
        b b = new b();
        JSONArray jSONArray = jSONObject.optJSONArray("modules");
        if (jSONArray != null) {
          ArrayList<String> arrayList = new ArrayList();
          Iterator iterator = ((Iterable)d.b(0, jSONArray.length())).iterator();
          while (iterator.hasNext()) {
            String str = jSONArray.optString(((ab)iterator).a());
            if (str != null)
              arrayList.add(str); 
          } 
          l.b(arrayList, "<set-?>");
          b.a = arrayList;
        } 
        String str2 = jSONObject.optString("version");
        if (str2 != null) {
          l.b(str2, "<set-?>");
          b.b = str2;
        } 
        String str1 = jSONObject.optString("base_version");
        if (str1 != null) {
          l.b(str1, "<set-?>");
          b.c = str1;
        } 
      } finally {
        param1String = null;
      } 
      Object object2 = object1;
      if (o.isFailure-impl(object1))
        object2 = null; 
      return (b)object2;
    }
    
    public static String a(File param1File, String param1String) {
      l.b(param1File, "dir");
      param1File = com.bytedance.ies.bullet.b.a.b.a(param1File, new a(param1String));
      if (param1File != null) {
        if (!param1File.exists())
          param1File = null; 
        if (param1File != null)
          return param1File.getPath(); 
      } 
      return null;
    }
    
    static final class a extends m implements d.f.a.b<File, Boolean> {
      a(String param2String) {
        super(1);
      }
    }
  }
  
  static final class a extends m implements d.f.a.b<File, Boolean> {
    a(String param1String) {
      super(1);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */