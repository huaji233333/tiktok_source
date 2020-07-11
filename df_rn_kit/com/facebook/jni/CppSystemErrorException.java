package com.facebook.jni;

public class CppSystemErrorException extends CppException {
  int errorCode;
  
  public CppSystemErrorException(String paramString, int paramInt) {
    super(paramString);
    this.errorCode = paramInt;
  }
  
  public int getErrorCode() {
    return this.errorCode;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\CppSystemErrorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */