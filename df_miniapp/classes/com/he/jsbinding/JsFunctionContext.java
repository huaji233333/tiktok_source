package com.he.jsbinding;

public class JsFunctionContext extends JsScopedContext {
  final long info;
  
  public final int length;
  
  JsFunctionContext(long paramLong1, int paramInt1, long paramLong2, int paramInt2) {
    super(paramLong1, paramInt1);
    this.info = paramLong2;
    this.length = paramInt2;
  }
  
  public final boolean getBoolean(int paramInt) {
    return JsEngine.getBooleanParam(this.info, paramInt);
  }
  
  public final int getInt(int paramInt) {
    return JsEngine.getIntParam(this.info, paramInt);
  }
  
  public final double getNumber(int paramInt) {
    return JsEngine.getNumberParam(this.info, paramInt);
  }
  
  public final JsObject getObject(int paramInt) {
    paramInt = JsEngine.getObjectParam(this.info, this.id, paramInt);
    return (paramInt == -1) ? null : new JsObject(this.vm, this.id, paramInt);
  }
  
  public final String getString(int paramInt) {
    return JsEngine.getStringParam(this.info, paramInt);
  }
  
  public final void returns(double paramDouble) {
    JsEngine.pushDouble(paramDouble);
    JsEngine.returns(this.info);
  }
  
  public final void returns(int paramInt) {
    JsEngine.pushInt(paramInt);
    JsEngine.returns(this.info);
  }
  
  public final void returns(JsObject paramJsObject) {
    if (paramJsObject == null) {
      JsEngine.pushNull();
    } else {
      JsEngine.pushObject(paramJsObject.ctx_id, paramJsObject.id);
    } 
    JsEngine.returns(this.info);
  }
  
  public final void returns(String paramString) {
    if (paramString == null) {
      JsEngine.pushNull();
    } else {
      JsEngine.pushString(paramString);
    } 
    JsEngine.returns(this.info);
  }
  
  public final void returns(boolean paramBoolean) {
    JsEngine.pushBoolean(paramBoolean);
    JsEngine.returns(this.info);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\jsbinding\JsFunctionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */