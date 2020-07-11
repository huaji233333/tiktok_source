package com.he.jsbinding;

import java.nio.ByteBuffer;

public class JsObject {
  final int ctx_id;
  
  final int id;
  
  private final long vm;
  
  JsObject(long paramLong, int paramInt1, int paramInt2) {
    this.vm = paramLong;
    this.ctx_id = paramInt1;
    this.id = paramInt2;
  }
  
  public int arrayGetLength() {
    return JsEngine.getArrayLength(this.vm, this.ctx_id, this.id);
  }
  
  public final ByteBuffer asArrayBuffer() {
    return JsEngine.makeDirectBuffer(this.vm, this.ctx_id, this.id);
  }
  
  public final void call(int paramInt) {
    JsEngine.callObject(this.ctx_id, this.id, paramInt);
  }
  
  public final void callMethod(String paramString, int paramInt) {
    JsEngine.callObjectMethod(this.ctx_id, this.id, paramString, paramInt);
  }
  
  public final boolean getBoolean(int paramInt) {
    JsEngine.getArrayField(this.ctx_id, this.id, paramInt);
    return JsEngine.getBooleanResult();
  }
  
  public final boolean getBoolean(String paramString) {
    JsEngine.getObjectProp(this.ctx_id, this.id, paramString);
    return JsEngine.getBooleanResult();
  }
  
  public final JsEngine getEngine() {
    return new JsEngine(this.vm);
  }
  
  public final int getInt(int paramInt) {
    JsEngine.getArrayField(this.ctx_id, this.id, paramInt);
    return JsEngine.getIntResult(this.ctx_id);
  }
  
  public final int getInt(String paramString) {
    JsEngine.getObjectProp(this.ctx_id, this.id, paramString);
    return JsEngine.getIntResult(this.ctx_id);
  }
  
  public final double getNumber(int paramInt) {
    JsEngine.getArrayField(this.ctx_id, this.id, paramInt);
    return JsEngine.getNumberResult(this.ctx_id);
  }
  
  public final double getNumber(String paramString) {
    JsEngine.getObjectProp(this.ctx_id, this.id, paramString);
    return JsEngine.getNumberResult(this.ctx_id);
  }
  
  public final JsObject getObject(int paramInt) {
    JsEngine.getArrayField(this.ctx_id, this.id, paramInt);
    return (new JsScopedContext(this.vm, this.ctx_id)).popObject();
  }
  
  public final JsObject getObject(String paramString) {
    JsEngine.getObjectProp(this.ctx_id, this.id, paramString);
    return (new JsScopedContext(this.vm, this.ctx_id)).popObject();
  }
  
  public JsScopedContext getScopedContext() {
    return new JsScopedContext(this.vm, this.ctx_id);
  }
  
  public final String getString(int paramInt) {
    JsEngine.getArrayField(this.ctx_id, this.id, paramInt);
    return JsEngine.getStringResult(this.ctx_id);
  }
  
  public final String getString(String paramString) {
    JsEngine.getObjectProp(this.ctx_id, this.id, paramString);
    return JsEngine.getStringResult(this.ctx_id);
  }
  
  public final void release() {
    JsEngine.releaseObject(this.vm, this.ctx_id, this.id);
  }
  
  public final ByteBuffer serialize() {
    return JsEngine.serialize(this.vm, this.ctx_id, this.id);
  }
  
  public final void set(String paramString) {
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString);
  }
  
  public final void set(String paramString, double paramDouble) {
    JsEngine.pushDouble(paramDouble);
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString);
  }
  
  public final void set(String paramString, int paramInt) {
    JsEngine.pushInt(paramInt);
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString);
  }
  
  public final void set(String paramString, JsObject paramJsObject) {
    JsEngine.pushObject(paramJsObject.ctx_id, paramJsObject.id);
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString);
  }
  
  public final void set(String paramString1, String paramString2) {
    JsEngine.pushString(paramString2);
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString1);
  }
  
  public final void set(String paramString, boolean paramBoolean) {
    JsEngine.pushBoolean(paramBoolean);
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString);
  }
  
  public final void setNull(String paramString) {
    JsEngine.pushNull();
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString);
  }
  
  public final void setUndefined(String paramString) {
    JsEngine.pushUndefined();
    JsEngine.setObjectProp(this.ctx_id, this.id, paramString);
  }
  
  public final String toJSON() {
    return JsEngine.toJSON(this.vm, this.ctx_id, this.id);
  }
  
  public String toString() {
    JsEngine.objectToString(this.ctx_id, this.id);
    return JsEngine.getStringResult(this.ctx_id);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\jsbinding\JsObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */