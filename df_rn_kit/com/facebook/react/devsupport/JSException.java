package com.facebook.react.devsupport;

public class JSException extends Exception {
  private final String mStack;
  
  public JSException(String paramString1, String paramString2) {
    super(paramString1);
    this.mStack = paramString2;
  }
  
  public JSException(String paramString1, String paramString2, Throwable paramThrowable) {
    super(paramString1, paramThrowable);
    this.mStack = paramString2;
  }
  
  public String getStack() {
    return this.mStack;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\JSException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */