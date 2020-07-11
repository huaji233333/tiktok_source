package com.tt.miniapp.util.timeline;

import d.f.b.g;
import d.f.b.l;
import org.json.JSONObject;

public final class MpPoint {
  public static final Companion Companion = new Companion(null);
  
  private final long mCpuTime;
  
  private final JSONObject mExtra;
  
  private final String mName;
  
  private final boolean mSerializeCpuTime;
  
  private final long mTimestamp;
  
  public MpPoint(String paramString, long paramLong1, long paramLong2, JSONObject paramJSONObject, boolean paramBoolean) {
    this.mName = paramString;
    this.mTimestamp = paramLong1;
    this.mCpuTime = paramLong2;
    this.mSerializeCpuTime = paramBoolean;
    JSONObject jSONObject = paramJSONObject;
    if (paramJSONObject == null)
      jSONObject = new JSONObject(); 
    this.mExtra = jSONObject;
  }
  
  public final JSONObject toJSON() {
    // Byte code:
    //   0: new org/json/JSONObject
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_2
    //   8: aload_2
    //   9: ldc 'name'
    //   11: aload_0
    //   12: getfield mName : Ljava/lang/String;
    //   15: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   18: pop
    //   19: aload_2
    //   20: ldc 'timestamp'
    //   22: aload_0
    //   23: getfield mTimestamp : J
    //   26: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   29: pop
    //   30: aload_0
    //   31: getfield mSerializeCpuTime : Z
    //   34: istore_1
    //   35: iload_1
    //   36: ifeq -> 132
    //   39: aload_0
    //   40: getfield mExtra : Lorg/json/JSONObject;
    //   43: astore_3
    //   44: aload_0
    //   45: getfield mExtra : Lorg/json/JSONObject;
    //   48: invokevirtual keys : ()Ljava/util/Iterator;
    //   51: astore #4
    //   53: aload #4
    //   55: ldc 'mExtra.keys()'
    //   57: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   60: aload #4
    //   62: invokestatic a : (Ljava/util/Iterator;)Ld/l/g;
    //   65: invokestatic f : (Ld/l/g;)Ljava/util/List;
    //   68: checkcast java/util/Collection
    //   71: iconst_0
    //   72: anewarray java/lang/String
    //   75: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   80: astore #4
    //   82: aload #4
    //   84: ifnull -> 122
    //   87: new org/json/JSONObject
    //   90: dup
    //   91: aload_3
    //   92: aload #4
    //   94: checkcast [Ljava/lang/String;
    //   97: invokespecial <init> : (Lorg/json/JSONObject;[Ljava/lang/String;)V
    //   100: astore_3
    //   101: aload_3
    //   102: ldc 'cpu_time'
    //   104: aload_0
    //   105: getfield mCpuTime : J
    //   108: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   111: pop
    //   112: aload_2
    //   113: ldc 'extra'
    //   115: aload_3
    //   116: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   119: pop
    //   120: aload_2
    //   121: areturn
    //   122: new d/u
    //   125: dup
    //   126: ldc 'null cannot be cast to non-null type kotlin.Array<T>'
    //   128: invokespecial <init> : (Ljava/lang/String;)V
    //   131: athrow
    //   132: aload_2
    //   133: ldc 'extra'
    //   135: aload_0
    //   136: getfield mExtra : Lorg/json/JSONObject;
    //   139: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   142: pop
    //   143: aload_2
    //   144: areturn
    //   145: astore_3
    //   146: ldc 'MpPoint'
    //   148: ldc ''
    //   150: aload_3
    //   151: checkcast java/lang/Throwable
    //   154: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   157: aload_2
    //   158: areturn
    // Exception table:
    //   from	to	target	type
    //   8	35	145	org/json/JSONException
    //   39	82	145	org/json/JSONException
    //   87	120	145	org/json/JSONException
    //   122	132	145	org/json/JSONException
    //   132	143	145	org/json/JSONException
  }
  
  public final String toString() {
    String str = toJSON().toString();
    l.a(str, "toJSON().toString()");
    return str;
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\timeline\MpPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */