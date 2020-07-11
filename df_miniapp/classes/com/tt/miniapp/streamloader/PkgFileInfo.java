package com.tt.miniapp.streamloader;

import android.text.TextUtils;
import com.tt.miniapphost.entity.AppInfoEntity;
import g.g;
import g.q;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class PkgFileInfo {
  public boolean isLocalTest;
  
  public String version;
  
  private static PkgFileInfo fromJson(String paramString) {
    if (TextUtils.isEmpty(paramString))
      return null; 
    PkgFileInfo pkgFileInfo = new PkgFileInfo();
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      pkgFileInfo.version = jSONObject.optString("version");
      pkgFileInfo.isLocalTest = jSONObject.optBoolean("is_local_test");
      return pkgFileInfo;
    } catch (JSONException jSONException) {
      return pkgFileInfo;
    } 
  }
  
  public static PkgFileInfo fromPkgFile(File paramFile) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: ifnonnull -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: aload_0
    //   9: invokevirtual exists : ()Z
    //   12: ifeq -> 149
    //   15: aload_0
    //   16: invokevirtual isFile : ()Z
    //   19: ifeq -> 149
    //   22: aload_0
    //   23: invokevirtual getName : ()Ljava/lang/String;
    //   26: astore_1
    //   27: aload_0
    //   28: invokevirtual getParent : ()Ljava/lang/String;
    //   31: astore_0
    //   32: new java/lang/StringBuilder
    //   35: dup
    //   36: invokespecial <init> : ()V
    //   39: astore_3
    //   40: aload_3
    //   41: aload_1
    //   42: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_3
    //   47: ldc '.info'
    //   49: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: new java/io/File
    //   56: dup
    //   57: aload_0
    //   58: aload_3
    //   59: invokevirtual toString : ()Ljava/lang/String;
    //   62: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   65: astore_0
    //   66: aload_0
    //   67: invokevirtual exists : ()Z
    //   70: ifeq -> 149
    //   73: aload_0
    //   74: invokestatic c : (Ljava/io/File;)Lg/z;
    //   77: invokestatic a : (Lg/z;)Lg/h;
    //   80: astore_0
    //   81: aload_0
    //   82: invokeinterface r : ()Ljava/lang/String;
    //   87: invokestatic fromJson : (Ljava/lang/String;)Lcom/tt/miniapp/streamloader/PkgFileInfo;
    //   90: astore_1
    //   91: aload_0
    //   92: invokeinterface close : ()V
    //   97: aload_1
    //   98: areturn
    //   99: astore_1
    //   100: aload_0
    //   101: astore_2
    //   102: aload_1
    //   103: astore_0
    //   104: goto -> 114
    //   107: goto -> 128
    //   110: goto -> 142
    //   113: astore_0
    //   114: aload_2
    //   115: ifnull -> 124
    //   118: aload_2
    //   119: invokeinterface close : ()V
    //   124: aload_0
    //   125: athrow
    //   126: aconst_null
    //   127: astore_0
    //   128: aload_0
    //   129: ifnull -> 149
    //   132: aload_0
    //   133: invokeinterface close : ()V
    //   138: aconst_null
    //   139: areturn
    //   140: aconst_null
    //   141: astore_0
    //   142: aload_0
    //   143: ifnull -> 149
    //   146: goto -> 132
    //   149: aconst_null
    //   150: areturn
    //   151: astore_0
    //   152: goto -> 140
    //   155: astore_0
    //   156: goto -> 126
    //   159: astore_1
    //   160: goto -> 110
    //   163: astore_1
    //   164: goto -> 107
    //   167: astore_0
    //   168: aload_1
    //   169: areturn
    //   170: astore_1
    //   171: goto -> 124
    //   174: astore_0
    //   175: aconst_null
    //   176: areturn
    // Exception table:
    //   from	to	target	type
    //   73	81	151	java/io/FileNotFoundException
    //   73	81	155	java/io/IOException
    //   73	81	113	finally
    //   81	91	159	java/io/FileNotFoundException
    //   81	91	163	java/io/IOException
    //   81	91	99	finally
    //   91	97	167	java/io/IOException
    //   118	124	170	java/io/IOException
    //   132	138	174	java/io/IOException
  }
  
  public static File getPkgInfoFile(File paramFile) {
    if (paramFile == null)
      return null; 
    String str2 = paramFile.getName();
    String str1 = paramFile.getParent();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str2);
    stringBuilder.append(".info");
    return new File(str1, stringBuilder.toString());
  }
  
  public static void savePkgInfo(AppInfoEntity paramAppInfoEntity, File paramFile) {
    g g1;
    File file2 = getPkgInfoFile(paramFile);
    if (file2 == null)
      return; 
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("is_local_test", paramAppInfoEntity.isLocalTest());
      jSONObject.put("version", paramAppInfoEntity.version);
    } catch (JSONException jSONException) {}
    if (file2.exists())
      file2.delete(); 
    File file1 = null;
    g g3 = null;
    paramAppInfoEntity = null;
    paramFile = file1;
    g g2 = g3;
    try {
      g g;
      if (file2.createNewFile()) {
        paramFile = file1;
        g2 = g3;
        g = q.a(q.a(file2));
        g1 = g;
        g2 = g;
        g.b(jSONObject.toString());
        g1 = g;
        g2 = g;
        g.flush();
      } 
    } catch (IOException iOException) {
    
    } finally {
      paramAppInfoEntity = null;
    } 
    throw paramAppInfoEntity;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\PkgFileInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */