package com.facebook.react.devsupport;

import android.util.JsonReader;
import android.util.JsonToken;
import g.h;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class BundleDeltaClient {
  String mDeltaId;
  
  final LinkedHashMap<Number, byte[]> mDeltaModules = (LinkedHashMap)new LinkedHashMap<Number, byte>();
  
  final LinkedHashMap<Number, byte[]> mPostModules = (LinkedHashMap)new LinkedHashMap<Number, byte>();
  
  final LinkedHashMap<Number, byte[]> mPreModules = (LinkedHashMap)new LinkedHashMap<Number, byte>();
  
  static boolean isDeltaUrl(String paramString) {
    return (paramString.indexOf(".delta?") != -1);
  }
  
  private static int patchDelta(JsonReader paramJsonReader, LinkedHashMap<Number, byte[]> paramLinkedHashMap) throws IOException {
    paramJsonReader.beginArray();
    int i;
    for (i = 0; paramJsonReader.hasNext(); i++) {
      paramJsonReader.beginArray();
      int j = paramJsonReader.nextInt();
      if (paramJsonReader.peek() == JsonToken.NULL) {
        paramJsonReader.skipValue();
        paramLinkedHashMap.remove(Integer.valueOf(j));
      } else {
        paramLinkedHashMap.put(Integer.valueOf(j), paramJsonReader.nextString().getBytes());
      } 
      paramJsonReader.endArray();
    } 
    paramJsonReader.endArray();
    return i;
  }
  
  public void reset() {
    this.mDeltaId = null;
    this.mDeltaModules.clear();
    this.mPreModules.clear();
    this.mPostModules.clear();
  }
  
  public boolean storeDeltaInFile(h paramh, File paramFile) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new android/util/JsonReader
    //   5: dup
    //   6: new java/io/InputStreamReader
    //   9: dup
    //   10: aload_1
    //   11: invokeinterface f : ()Ljava/io/InputStream;
    //   16: invokespecial <init> : (Ljava/io/InputStream;)V
    //   19: invokespecial <init> : (Ljava/io/Reader;)V
    //   22: astore_1
    //   23: aload_1
    //   24: invokevirtual beginObject : ()V
    //   27: iconst_0
    //   28: istore #4
    //   30: aload_1
    //   31: invokevirtual hasNext : ()Z
    //   34: ifeq -> 137
    //   37: aload_1
    //   38: invokevirtual nextName : ()Ljava/lang/String;
    //   41: astore #5
    //   43: aload #5
    //   45: ldc 'id'
    //   47: invokevirtual equals : (Ljava/lang/Object;)Z
    //   50: ifeq -> 64
    //   53: aload_0
    //   54: aload_1
    //   55: invokevirtual nextString : ()Ljava/lang/String;
    //   58: putfield mDeltaId : Ljava/lang/String;
    //   61: goto -> 30
    //   64: aload #5
    //   66: ldc 'pre'
    //   68: invokevirtual equals : (Ljava/lang/Object;)Z
    //   71: ifeq -> 86
    //   74: aload_1
    //   75: aload_0
    //   76: getfield mPreModules : Ljava/util/LinkedHashMap;
    //   79: invokestatic patchDelta : (Landroid/util/JsonReader;Ljava/util/LinkedHashMap;)I
    //   82: istore_3
    //   83: goto -> 329
    //   86: aload #5
    //   88: ldc 'post'
    //   90: invokevirtual equals : (Ljava/lang/Object;)Z
    //   93: ifeq -> 108
    //   96: aload_1
    //   97: aload_0
    //   98: getfield mPostModules : Ljava/util/LinkedHashMap;
    //   101: invokestatic patchDelta : (Landroid/util/JsonReader;Ljava/util/LinkedHashMap;)I
    //   104: istore_3
    //   105: goto -> 329
    //   108: aload #5
    //   110: ldc 'delta'
    //   112: invokevirtual equals : (Ljava/lang/Object;)Z
    //   115: ifeq -> 130
    //   118: aload_1
    //   119: aload_0
    //   120: getfield mDeltaModules : Ljava/util/LinkedHashMap;
    //   123: invokestatic patchDelta : (Landroid/util/JsonReader;Ljava/util/LinkedHashMap;)I
    //   126: istore_3
    //   127: goto -> 329
    //   130: aload_1
    //   131: invokevirtual skipValue : ()V
    //   134: goto -> 30
    //   137: aload_1
    //   138: invokevirtual endObject : ()V
    //   141: aload_1
    //   142: invokevirtual close : ()V
    //   145: iload #4
    //   147: ifne -> 154
    //   150: aload_0
    //   151: monitorexit
    //   152: iconst_0
    //   153: ireturn
    //   154: new java/io/FileOutputStream
    //   157: dup
    //   158: aload_2
    //   159: invokespecial <init> : (Ljava/io/File;)V
    //   162: astore_1
    //   163: aload_0
    //   164: getfield mPreModules : Ljava/util/LinkedHashMap;
    //   167: invokevirtual values : ()Ljava/util/Collection;
    //   170: invokeinterface iterator : ()Ljava/util/Iterator;
    //   175: astore_2
    //   176: aload_2
    //   177: invokeinterface hasNext : ()Z
    //   182: ifeq -> 207
    //   185: aload_1
    //   186: aload_2
    //   187: invokeinterface next : ()Ljava/lang/Object;
    //   192: checkcast [B
    //   195: invokevirtual write : ([B)V
    //   198: aload_1
    //   199: bipush #10
    //   201: invokevirtual write : (I)V
    //   204: goto -> 176
    //   207: aload_0
    //   208: getfield mDeltaModules : Ljava/util/LinkedHashMap;
    //   211: invokevirtual values : ()Ljava/util/Collection;
    //   214: invokeinterface iterator : ()Ljava/util/Iterator;
    //   219: astore_2
    //   220: aload_2
    //   221: invokeinterface hasNext : ()Z
    //   226: ifeq -> 251
    //   229: aload_1
    //   230: aload_2
    //   231: invokeinterface next : ()Ljava/lang/Object;
    //   236: checkcast [B
    //   239: invokevirtual write : ([B)V
    //   242: aload_1
    //   243: bipush #10
    //   245: invokevirtual write : (I)V
    //   248: goto -> 220
    //   251: aload_0
    //   252: getfield mPostModules : Ljava/util/LinkedHashMap;
    //   255: invokevirtual values : ()Ljava/util/Collection;
    //   258: invokeinterface iterator : ()Ljava/util/Iterator;
    //   263: astore_2
    //   264: aload_2
    //   265: invokeinterface hasNext : ()Z
    //   270: ifeq -> 295
    //   273: aload_1
    //   274: aload_2
    //   275: invokeinterface next : ()Ljava/lang/Object;
    //   280: checkcast [B
    //   283: invokevirtual write : ([B)V
    //   286: aload_1
    //   287: bipush #10
    //   289: invokevirtual write : (I)V
    //   292: goto -> 264
    //   295: aload_1
    //   296: invokevirtual flush : ()V
    //   299: aload_1
    //   300: invokevirtual close : ()V
    //   303: aload_0
    //   304: monitorexit
    //   305: iconst_1
    //   306: ireturn
    //   307: astore_2
    //   308: aload_1
    //   309: invokevirtual flush : ()V
    //   312: aload_1
    //   313: invokevirtual close : ()V
    //   316: aload_2
    //   317: athrow
    //   318: astore_1
    //   319: aload_0
    //   320: monitorexit
    //   321: goto -> 326
    //   324: aload_1
    //   325: athrow
    //   326: goto -> 324
    //   329: iload #4
    //   331: iload_3
    //   332: iadd
    //   333: istore #4
    //   335: goto -> 30
    // Exception table:
    //   from	to	target	type
    //   2	27	318	finally
    //   30	61	318	finally
    //   64	83	318	finally
    //   86	105	318	finally
    //   108	127	318	finally
    //   130	134	318	finally
    //   137	145	318	finally
    //   154	163	318	finally
    //   163	176	307	finally
    //   176	204	307	finally
    //   207	220	307	finally
    //   220	248	307	finally
    //   251	264	307	finally
    //   264	292	307	finally
    //   295	303	318	finally
    //   308	318	318	finally
  }
  
  public String toDeltaUrl(String paramString) {
    String str = paramString;
    if (isDeltaUrl(paramString)) {
      str = paramString;
      if (this.mDeltaId != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append("&deltaBundleId=");
        stringBuilder.append(this.mDeltaId);
        str = stringBuilder.toString();
      } 
    } 
    return str;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\BundleDeltaClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */