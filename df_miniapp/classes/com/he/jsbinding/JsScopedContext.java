package com.he.jsbinding;

public class JsScopedContext extends JsContext {
  JsScopedContext(long paramLong, int paramInt) {
    super(paramLong, paramInt);
  }
  
  public JsObject makeArray(int paramInt) {
    return new JsObject(this.vm, this.id, JsEngine.makeArray(this.id, paramInt));
  }
  
  public final void pop() {
    JsEngine.popResult();
  }
  
  public final boolean popBoolean() {
    return JsEngine.getBooleanResult();
  }
  
  public final int popInt() {
    return JsEngine.getIntResult(this.id);
  }
  
  public final double popNumber() {
    return JsEngine.getNumberResult(this.id);
  }
  
  public final JsObject popObject() {
    int i = JsEngine.getObjectResult(this.id);
    if (i >= 0)
      return new JsObject(this.vm, this.id, i); 
    throw new RuntimeException("not an object");
  }
  
  public final String popString() {
    return JsEngine.getStringResult(this.id);
  }
  
  public final void push(double paramDouble) {
    JsEngine.pushDouble(paramDouble);
  }
  
  public final void push(int paramInt) {
    JsEngine.pushInt(paramInt);
  }
  
  public final void push(JsObject paramJsObject) {
    JsEngine.pushObject(paramJsObject.ctx_id, paramJsObject.id);
  }
  
  public final void push(String paramString) {
    JsEngine.pushString(paramString);
  }
  
  public final void push(boolean paramBoolean) {
    JsEngine.pushBoolean(paramBoolean);
  }
  
  public final void pushJSON(String paramString) {
    JsEngine.parseJSON(paramString);
  }
  
  public final void pushNull() {
    JsEngine.pushNull();
  }
  
  public final void pushUndefined() {
    JsEngine.pushUndefined();
  }
  
  public void release() {
    throw new RuntimeException("JsScopedContext should not be disposed");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\jsbinding\JsScopedContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */