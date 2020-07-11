package com.he.jsbinding;

import com.google.c.a.a.a.a.a;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class JsEngine {
  private static RuntimeException pending_exception;
  
  public static volatile Type type = Type.Unknown;
  
  final long vm;
  
  public JsEngine() {
    loadLibs();
    if (type != Type.Unknown) {
      this.vm = createVM();
      return;
    } 
    throw pending_exception;
  }
  
  JsEngine(long paramLong) {
    this.vm = paramLong;
  }
  
  private static void addPendingException(Throwable paramThrowable) {
    if (pending_exception == null)
      pending_exception = new RuntimeException("failed to create js engine"); 
    a.a(pending_exception, paramThrowable);
  }
  
  static native void callObject(int paramInt1, int paramInt2, int paramInt3);
  
  static native void callObjectMethod(int paramInt1, int paramInt2, String paramString, int paramInt3);
  
  static native int compileBytes(long paramLong, int paramInt, byte[] paramArrayOfbyte, String paramString, boolean paramBoolean);
  
  static native int createArray(long paramLong, int paramInt1, int paramInt2);
  
  static native int createArrayBuffer(long paramLong, int paramInt1, int paramInt2);
  
  static native int createContext(long paramLong);
  
  static native int createFunction(long paramLong, int paramInt, JsFunctionCallback paramJsFunctionCallback);
  
  static native int createObject(long paramLong, int paramInt);
  
  private static native long createVM();
  
  static native int deserialize(long paramLong, int paramInt, ByteBuffer paramByteBuffer);
  
  static native void disposeVM(long paramLong);
  
  static native void eval(long paramLong, int paramInt, String paramString1, String paramString2, boolean paramBoolean);
  
  static native void evalByteBuffer(long paramLong, int paramInt, ByteBuffer paramByteBuffer, String paramString, boolean paramBoolean);
  
  static native void evalBytes(long paramLong, int paramInt, byte[] paramArrayOfbyte, String paramString, boolean paramBoolean);
  
  static native void executeScript(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
  
  static native void getArrayField(int paramInt1, int paramInt2, int paramInt3);
  
  static native int getArrayLength(long paramLong, int paramInt1, int paramInt2);
  
  static native boolean getBooleanParam(long paramLong, int paramInt);
  
  static native boolean getBooleanResult();
  
  static native int getGlobal(long paramLong, int paramInt);
  
  static native int getIntParam(long paramLong, int paramInt);
  
  static native int getIntResult(int paramInt);
  
  static native double getNumberParam(long paramLong, int paramInt);
  
  static native double getNumberResult(int paramInt);
  
  static native int getObjectParam(long paramLong, int paramInt1, int paramInt2);
  
  static native void getObjectProp(int paramInt1, int paramInt2, String paramString);
  
  static native int getObjectResult(int paramInt);
  
  static native String getStringParam(long paramLong, int paramInt);
  
  static native String getStringResult(int paramInt);
  
  private static void loadLibs() {
    // Byte code:
    //   0: ldc com/he/jsbinding/JsEngine
    //   2: monitorenter
    //   3: getstatic com/he/jsbinding/JsEngine.type : Lcom/he/jsbinding/JsEngine$Type;
    //   6: astore_0
    //   7: getstatic com/he/jsbinding/JsEngine$Type.Unknown : Lcom/he/jsbinding/JsEngine$Type;
    //   10: astore_1
    //   11: aload_0
    //   12: aload_1
    //   13: if_acmpeq -> 20
    //   16: ldc com/he/jsbinding/JsEngine
    //   18: monitorexit
    //   19: return
    //   20: aconst_null
    //   21: putstatic com/he/jsbinding/JsEngine.pending_exception : Ljava/lang/RuntimeException;
    //   24: ldc 'c++_shared'
    //   26: invokestatic load : (Ljava/lang/String;)V
    //   29: goto -> 45
    //   32: astore_0
    //   33: ldc 'jsbinding'
    //   35: ldc 'library for c++_shared not loaded'
    //   37: aload_0
    //   38: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   41: aload_0
    //   42: invokestatic addPendingException : (Ljava/lang/Throwable;)V
    //   45: ldc 'v8_libbase.cr'
    //   47: invokestatic load : (Ljava/lang/String;)V
    //   50: ldc 'v8_libplatform.cr'
    //   52: invokestatic load : (Ljava/lang/String;)V
    //   55: ldc 'v8.cr'
    //   57: invokestatic load : (Ljava/lang/String;)V
    //   60: ldc 'skialite'
    //   62: invokestatic load : (Ljava/lang/String;)V
    //   65: goto -> 81
    //   68: astore_0
    //   69: ldc 'jsbinding'
    //   71: ldc 'load skialite failed'
    //   73: aload_0
    //   74: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   77: aload_0
    //   78: invokestatic addPendingException : (Ljava/lang/Throwable;)V
    //   81: ldc 'helium'
    //   83: invokestatic load : (Ljava/lang/String;)V
    //   86: getstatic com/he/jsbinding/JsEngine$Type.V8 : Lcom/he/jsbinding/JsEngine$Type;
    //   89: putstatic com/he/jsbinding/JsEngine.type : Lcom/he/jsbinding/JsEngine$Type;
    //   92: ldc 'jsbinding'
    //   94: iconst_1
    //   95: anewarray java/lang/Object
    //   98: dup
    //   99: iconst_0
    //   100: ldc 'using V8'
    //   102: aastore
    //   103: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   106: ldc com/he/jsbinding/JsEngine
    //   108: monitorexit
    //   109: return
    //   110: astore_0
    //   111: ldc 'jsbinding'
    //   113: ldc 'load V8 failed'
    //   115: aload_0
    //   116: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   119: aload_0
    //   120: invokestatic addPendingException : (Ljava/lang/Throwable;)V
    //   123: ldc 'gnustl_shared'
    //   125: invokestatic load : (Ljava/lang/String;)V
    //   128: goto -> 144
    //   131: astore_0
    //   132: ldc 'jsbinding'
    //   134: ldc 'library gnustl_shared not loaded'
    //   136: aload_0
    //   137: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   140: aload_0
    //   141: invokestatic addPendingException : (Ljava/lang/Throwable;)V
    //   144: ldc 'jsc'
    //   146: invokestatic load : (Ljava/lang/String;)V
    //   149: ldc 'jsbinding-jsc'
    //   151: invokestatic load : (Ljava/lang/String;)V
    //   154: getstatic com/he/jsbinding/JsEngine$Type.JSC : Lcom/he/jsbinding/JsEngine$Type;
    //   157: putstatic com/he/jsbinding/JsEngine.type : Lcom/he/jsbinding/JsEngine$Type;
    //   160: ldc 'jsbinding'
    //   162: iconst_1
    //   163: anewarray java/lang/Object
    //   166: dup
    //   167: iconst_0
    //   168: ldc 'using JSC'
    //   170: aastore
    //   171: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   174: ldc com/he/jsbinding/JsEngine
    //   176: monitorexit
    //   177: return
    //   178: astore_0
    //   179: ldc 'jsbinding'
    //   181: ldc 'load JSC failed'
    //   183: aload_0
    //   184: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   187: aload_0
    //   188: invokestatic addPendingException : (Ljava/lang/Throwable;)V
    //   191: ldc 'jsbinding'
    //   193: invokestatic load : (Ljava/lang/String;)V
    //   196: getstatic com/he/jsbinding/JsEngine$Type.QuickJS : Lcom/he/jsbinding/JsEngine$Type;
    //   199: putstatic com/he/jsbinding/JsEngine.type : Lcom/he/jsbinding/JsEngine$Type;
    //   202: ldc 'jsbinding'
    //   204: iconst_1
    //   205: anewarray java/lang/Object
    //   208: dup
    //   209: iconst_0
    //   210: ldc 'using QuickJS'
    //   212: aastore
    //   213: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   216: ldc com/he/jsbinding/JsEngine
    //   218: monitorexit
    //   219: return
    //   220: astore_0
    //   221: ldc 'jsbinding'
    //   223: ldc 'load QuickJS failed'
    //   225: aload_0
    //   226: invokestatic eWithThrowable : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   229: ldc com/he/jsbinding/JsEngine
    //   231: monitorexit
    //   232: return
    //   233: astore_0
    //   234: ldc com/he/jsbinding/JsEngine
    //   236: monitorexit
    //   237: aload_0
    //   238: athrow
    // Exception table:
    //   from	to	target	type
    //   3	11	233	finally
    //   20	24	233	finally
    //   24	29	32	finally
    //   33	45	233	finally
    //   45	60	110	finally
    //   60	65	68	finally
    //   69	81	110	finally
    //   81	106	110	finally
    //   111	123	233	finally
    //   123	128	131	finally
    //   132	144	233	finally
    //   144	174	178	finally
    //   179	191	233	finally
    //   191	216	220	finally
    //   221	229	233	finally
  }
  
  static native int makeArray(int paramInt1, int paramInt2);
  
  static native ByteBuffer makeDirectBuffer(long paramLong, int paramInt1, int paramInt2);
  
  private static void nativeOnInvoke(JsFunctionCallback paramJsFunctionCallback, long paramLong1, int paramInt1, long paramLong2, int paramInt2) {
    paramJsFunctionCallback.onCall(new JsFunctionContext(paramLong1, paramInt1, paramLong2, paramInt2));
  }
  
  private static void nativeOnRun(JsContext.ScopeCallback paramScopeCallback, long paramLong, int paramInt) {
    paramScopeCallback.run(new JsScopedContext(paramLong, paramInt));
  }
  
  static native int newMethodWrap(long paramLong1, int paramInt, Object paramObject, Method paramMethod, long paramLong2, byte paramByte);
  
  static native void objectToString(int paramInt1, int paramInt2);
  
  static native void parseJSON(String paramString);
  
  public static native void popResult();
  
  static native void pushBoolean(boolean paramBoolean);
  
  static native void pushDouble(double paramDouble);
  
  static native void pushInt(int paramInt);
  
  static native void pushNull();
  
  static native void pushObject(int paramInt1, int paramInt2);
  
  static native void pushString(String paramString);
  
  static native void pushUndefined();
  
  static native void releaseContext(long paramLong, int paramInt);
  
  public static native void releaseDeserializable(ByteBuffer paramByteBuffer);
  
  static native void releaseObject(long paramLong, int paramInt1, int paramInt2);
  
  static native void releaseScript(long paramLong, int paramInt1, int paramInt2);
  
  static native int retain(long paramLong, int paramInt1, int paramInt2, int paramInt3);
  
  static native void returns(long paramLong);
  
  static native void run(long paramLong, int paramInt, JsContext.ScopeCallback paramScopeCallback);
  
  public static native void runInCurrentContext(JsContext.ScopeCallback paramScopeCallback);
  
  static native ByteBuffer serialize(long paramLong, int paramInt1, int paramInt2);
  
  static native void setObjectProp(int paramInt1, int paramInt2, String paramString);
  
  static native String toJSON(long paramLong, int paramInt1, int paramInt2);
  
  public void dispose() {
    disposeVM(this.vm);
  }
  
  public enum Type {
    JSC, QuickJS, Unknown, V8;
    
    static {
      QuickJS = new Type("QuickJS", 3);
      $VALUES = new Type[] { Unknown, JSC, V8, QuickJS };
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\jsbinding\JsEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */