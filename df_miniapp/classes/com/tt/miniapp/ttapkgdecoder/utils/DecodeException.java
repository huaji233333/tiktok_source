package com.tt.miniapp.ttapkgdecoder.utils;

import java.io.IOException;

public class DecodeException extends IOException {
  private final int mErrorCode;
  
  public DecodeException(int paramInt) {
    super(stringBuilder.toString());
    this.mErrorCode = paramInt;
  }
  
  public DecodeException(Throwable paramThrowable, int paramInt) {
    super(stringBuilder.toString(), paramThrowable);
    this.mErrorCode = paramInt;
  }
  
  public int getErrorCode() {
    return this.mErrorCode;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecode\\utils\DecodeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */