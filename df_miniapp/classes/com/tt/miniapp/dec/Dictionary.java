package com.tt.miniapp.dec;

import java.nio.ByteBuffer;

public final class Dictionary {
  private static volatile ByteBuffer data;
  
  public static ByteBuffer getData() {
    if (data != null)
      return data; 
    if (DataLoader.OK)
      return data; 
    throw new BrotliRuntimeException("brotli dictionary is not set");
  }
  
  public static void setData(ByteBuffer paramByteBuffer) {
    if (paramByteBuffer.isDirect() && paramByteBuffer.isReadOnly()) {
      data = paramByteBuffer;
      return;
    } 
    throw new BrotliRuntimeException("data must be a direct read-only byte buffer");
  }
  
  static class DataLoader {
    static final boolean OK;
    
    static {
      boolean bool;
      try {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Dictionary.class.getPackage().getName());
        stringBuilder.append(".DictionaryData");
        Class.forName(stringBuilder.toString());
      } finally {
        Exception exception = null;
      } 
      OK = bool;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\Dictionary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */