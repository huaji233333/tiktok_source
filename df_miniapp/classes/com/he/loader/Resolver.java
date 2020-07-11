package com.he.loader;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public interface Resolver {
  void reject(IOException paramIOException);
  
  void resolve(File paramFile);
  
  void resolve(ByteBuffer paramByteBuffer);
  
  void resolve(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\loader\Resolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */