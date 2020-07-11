package com.tt.miniapp.ttapkgdecoder.source;

import com.tt.miniapp.util.StringUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseSource implements ISource {
  public final int read(byte[] paramArrayOfbyte) throws IOException {
    return read(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  public final int readInt() throws IOException {
    byte[] arrayOfByte = new byte[4];
    readFully(arrayOfByte);
    byte b1 = arrayOfByte[0];
    byte b2 = arrayOfByte[1];
    byte b3 = arrayOfByte[2];
    return (arrayOfByte[3] & 0xFF) << 24 | b1 & 0xFF | (b2 & 0xFF) << 8 | (b3 & 0xFF) << 16;
  }
  
  public final String readUtf8(long paramLong) throws IOException {
    byte[] arrayOfByte = new byte[(int)paramLong];
    readFully(arrayOfByte);
    return StringUtils.newString(arrayOfByte, StandardCharsets.UTF_8);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\ttapkgdecoder\source\BaseSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */