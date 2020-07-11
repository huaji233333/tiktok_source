package com.he.jsbinding;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;

public class JsContext {
  protected final int id;
  
  protected final boolean scoped;
  
  protected final long vm;
  
  JsContext(long paramLong, int paramInt) {
    boolean bool;
    if (getClass() == JsScopedContext.class) {
      bool = true;
    } else {
      bool = false;
    } 
    this.scoped = bool;
    this.vm = paramLong;
    this.id = paramInt;
  }
  
  public JsContext(JsEngine paramJsEngine) {
    boolean bool;
    if (getClass() == JsScopedContext.class) {
      bool = true;
    } else {
      bool = false;
    } 
    this.scoped = bool;
    this.vm = paramJsEngine.vm;
    this.id = JsEngine.createContext(this.vm);
  }
  
  private static byte getTypeCode(Class<?> paramClass) {
    if (paramClass == void.class)
      return 118; 
    if (paramClass == boolean.class)
      return 122; 
    if (paramClass == byte.class)
      return 98; 
    if (paramClass == char.class)
      return 99; 
    if (paramClass == short.class)
      return 115; 
    if (paramClass == int.class)
      return 105; 
    if (paramClass == float.class)
      return 102; 
    if (paramClass == double.class)
      return 100; 
    if (paramClass == long.class)
      return 106; 
    if (paramClass == String.class)
      return 36; 
    if (paramClass == ByteBuffer.class)
      return 66; 
    if (paramClass == JsObject.class)
      return 79; 
    if (paramClass == JsScopedContext.class)
      return 64; 
    StringBuilder stringBuilder = new StringBuilder("unsupported type ");
    stringBuilder.append(paramClass.getCanonicalName());
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public int compile(byte[] paramArrayOfbyte, String paramString, boolean paramBoolean) {
    return JsEngine.compileBytes(this.vm, this.id, paramArrayOfbyte, paramString, paramBoolean);
  }
  
  public JsObject createArray(int paramInt) {
    long l = this.vm;
    int i = this.id;
    return new JsObject(l, i, JsEngine.createArray(l, i, paramInt));
  }
  
  public JsObject createArrayBuffer(int paramInt) {
    long l = this.vm;
    int i = this.id;
    return new JsObject(l, i, JsEngine.createArrayBuffer(l, i, paramInt));
  }
  
  public JsObject createFunction(JsFunctionCallback paramJsFunctionCallback) {
    long l = this.vm;
    int i = this.id;
    return new JsObject(l, i, JsEngine.createFunction(l, i, paramJsFunctionCallback));
  }
  
  public JsObject createFunction(Object paramObject, String paramString, Class<?>... paramVarArgs) throws NoSuchMethodException {
    if (paramVarArgs.length <= 8) {
      Method method = paramObject.getClass().getMethod(paramString, paramVarArgs);
      int i = 0;
      long l1 = 0L;
      while (i < paramVarArgs.length) {
        l1 |= getTypeCode(paramVarArgs[i]) << i << 3;
        i++;
      } 
      long l2 = this.vm;
      i = this.id;
      return new JsObject(l2, i, JsEngine.newMethodWrap(l2, i, paramObject, method, l1, getTypeCode(method.getReturnType())));
    } 
    paramObject = new RuntimeException("we only support methods with no more than 8 arguments");
    throw paramObject;
  }
  
  public JsObject createFunction(Object paramObject, Method paramMethod) {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    if (arrayOfClass.length <= 8) {
      int i = 0;
      long l1 = 0L;
      while (i < arrayOfClass.length) {
        l1 |= getTypeCode(arrayOfClass[i]) << i << 3;
        i++;
      } 
      long l2 = this.vm;
      i = this.id;
      return new JsObject(l2, i, JsEngine.newMethodWrap(l2, i, paramObject, paramMethod, l1, getTypeCode(paramMethod.getReturnType())));
    } 
    paramObject = new RuntimeException("we only support methods with no more than 8 arguments");
    throw paramObject;
  }
  
  public JsObject createObject() {
    long l = this.vm;
    int i = this.id;
    return new JsObject(l, i, JsEngine.createObject(l, i));
  }
  
  public JsObject deserialize(ByteBuffer paramByteBuffer) {
    int i = JsEngine.deserialize(this.vm, this.id, paramByteBuffer);
    if (i != -1)
      return new JsObject(this.vm, this.id, i); 
    throw new RuntimeException("wrong buffer format");
  }
  
  public void eval(String paramString1, String paramString2) {
    if (paramString1 != null) {
      JsEngine.eval(this.vm, this.id, paramString1, paramString2, this.scoped);
      return;
    } 
    throw new NullPointerException("code is null");
  }
  
  public void eval(ByteBuffer paramByteBuffer, String paramString) {
    if (paramByteBuffer != null) {
      JsEngine.evalByteBuffer(this.vm, this.id, paramByteBuffer, paramString, this.scoped);
      return;
    } 
    throw new NullPointerException("code is null");
  }
  
  public void eval(byte[] paramArrayOfbyte, String paramString) {
    if (paramArrayOfbyte != null) {
      JsEngine.evalBytes(this.vm, this.id, paramArrayOfbyte, paramString, this.scoped);
      return;
    } 
    throw new NullPointerException("code is null");
  }
  
  public void execute(int paramInt, boolean paramBoolean) {
    JsEngine.executeScript(this.vm, this.id, paramInt, this.scoped, paramBoolean);
  }
  
  public final JsEngine getEngine() {
    return new JsEngine(this.vm);
  }
  
  public final JsObject global() {
    long l = this.vm;
    int i = this.id;
    return new JsObject(l, i, JsEngine.getGlobal(l, i));
  }
  
  public void release() {
    JsEngine.releaseContext(this.vm, this.id);
  }
  
  public void releaseCompiledScript(int paramInt) {
    JsEngine.releaseScript(this.vm, this.id, paramInt);
  }
  
  public JsObject retain(JsObject paramJsObject) {
    long l = this.vm;
    int i = this.id;
    return new JsObject(l, i, JsEngine.retain(l, i, paramJsObject.ctx_id, paramJsObject.id));
  }
  
  public void run(ScopeCallback paramScopeCallback) {
    JsEngine.run(this.vm, this.id, paramScopeCallback);
  }
  
  public static interface ScopeCallback {
    void run(JsScopedContext param1JsScopedContext);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\jsbinding\JsContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */